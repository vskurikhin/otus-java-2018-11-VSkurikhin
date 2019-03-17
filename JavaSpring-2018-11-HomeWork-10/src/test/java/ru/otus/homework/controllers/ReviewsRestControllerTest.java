package ru.otus.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.dto.ReviewDto;
import ru.otus.homework.services.DatabaseService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static ru.otus.homework.controllers.Constants.REST_API;
import static ru.otus.homework.controllers.Constants.REST_V1_REVIEWS;
import static ru.otus.outside.utils.TestData.*;
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
    private Review review1WithBook;
    private Review review2WithBook;

    @BeforeEach
    void setUp()
    {
        book1 = createBook1();
        review1WithBook = createReview1(book1);
        review2WithBook = createReview2(book1);
    }

    @Test
    void getReviews() throws Exception
    {
        when(databaseService.getAllReviewsForBookById(1L))
            .thenReturn(Arrays.asList(review1WithBook, review2WithBook));

        mvc.perform(get(REST_API + REST_V1_REVIEWS + "/{id}", 1L))
           .andExpect(status().isOk())
           .andExpect(content().contentType(APPLICATION_JSON_UTF8))
           .andExpect(jsonPath("$", hasSize(2)))
           .andExpect(jsonPath("$[0].id", is(1)))
           .andExpect(jsonPath("$[0].bookId", is(1)))
           .andExpect(jsonPath("$[0].review", is(review1WithBook.getReview())))
           .andExpect(jsonPath("$[1].id", is(2)))
           .andExpect(jsonPath("$[1].bookId", is(1)))
           .andExpect(jsonPath("$[1].review", is(review2WithBook.getReview())));
    }

    @Test
    void countReviewsByBookId() throws Exception
    {
        when(databaseService.countReviewsByBookId(1L))
            .thenReturn(1L);

        mvc.perform(get(REST_API + REST_V1_REVIEWS + "/count/by-book/{id}", 1L))
           .andExpect(jsonPath("$.count", is(1)));
    }

    @Test
    void createReview() throws Exception
    {
        ReviewDto dto = new ReviewDto(createReview0());
        dto.setBookId(1L);
        when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));

        mvc.perform(post(REST_API + REST_V1_REVIEWS)
           .contentType(APPLICATION_JSON_UTF8)
           .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void updateReview() throws Exception
    {
        ReviewDto dto = new ReviewDto(review1WithBook);
        when(databaseService.getReviewById(1L)).thenReturn(Optional.of(review1WithBook));
        when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));

        mvc.perform(put(REST_API + REST_V1_REVIEWS)
           .contentType(APPLICATION_JSON_UTF8)
           .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void deleteReview() throws Exception
    {
        mvc.perform(delete(REST_API + REST_V1_REVIEWS + "/{id}", 1))
           .andExpect(status().isNoContent());
    }
}