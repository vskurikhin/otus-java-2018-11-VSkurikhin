package ru.otus.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.dto.ReviewDto;
import ru.otus.homework.services.DatabaseService;

import java.math.BigInteger;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.synchronoss.cloud.nio.multipart.MultipartUtils.CONTENT_TYPE;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.outside.TestData.*;
import static ru.otus.outside.utils.TestUtil.convertObjectToJsonBytes;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewsRestController.class)
class ReviewsRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    DatabaseService databaseService;

    private Book book1;

    private Review review1;

    private Review review2;

    @BeforeEach
    void setUp()
    {
        book1 = createBook1();
        review1 = createReview1(book1);
        review2 = createReview2(book1);
    }

    @Test
    void createReview() throws Exception
    {
        Review review0 = createReview0();
        ReviewDto dto = new ReviewDto(review0);
        dto.setId(null);

        when(databaseService.getBookById(review0.getBook().getId()))
            .thenReturn(Mono.just(review0.getBook()));
        when(databaseService.saveReview(any())).thenReturn(Mono.just(review0));

        mvc.perform(post(REST_API + REST_V1_REVIEWS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void readReviews() throws Exception
    {
        when(databaseService.getAllReviewsForBookById(BigInteger.ONE))
            .thenReturn(Flux.just(review1, review2));

        MvcResult result = mvc.perform(
            get(REST_API + REST_V1_REVIEWS + "/1")
                .contentType(APPLICATION_JSON)
        ).andReturn();

        mvc.perform(asyncDispatch(result))
        //  .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(review1.getId().toString())))
            .andExpect(jsonPath("$[0].bookId", is(review1.getBook().getId().toString())))
            .andExpect(jsonPath("$[0].review", is(review1.getReview())))
            .andExpect(jsonPath("$[1].id", is(review2.getId().toString())))
            .andExpect(jsonPath("$[1].bookId", is(review2.getBook().getId().toString())))
            .andExpect(jsonPath("$[1].review", is(review2.getReview())))
        ;
    }

    @Test
    void updateReview() throws Exception
    {
        ReviewDto dto = new ReviewDto(review1);
        when(databaseService.getBookById(BigInteger.ONE)).thenReturn(Mono.just(book1));
        when(databaseService.saveReview(review1)).thenReturn(Mono.just(review1));

        mvc.perform(put(REST_API + REST_V1_REVIEWS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception
    {
        when(databaseService.getReviewById(BigInteger.ONE))
            .thenReturn(Mono.just(review1));
        when(databaseService.removeReview(BigInteger.ONE)).thenReturn(Mono.empty());
        mvc.perform(delete(REST_API + REST_V1_REVIEWS + "/{reviewId}" , 1))
            .andExpect(status().isNoContent());
    }
}