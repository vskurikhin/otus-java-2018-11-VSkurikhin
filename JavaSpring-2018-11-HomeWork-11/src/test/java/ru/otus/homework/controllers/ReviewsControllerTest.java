package ru.otus.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Review;
import ru.otus.homework.services.DatabaseService;

import java.math.BigInteger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.outside.TestData.createReview1;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewsController.class)
@DisplayName("Integration tests for ReviewController")
class ReviewsControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DatabaseService databaseService;

    Review review1;

    @BeforeEach
    void setUp()
    {
        review1 = createReview1();
    }

    @Test
    void booksList() throws Exception
    {
        mvc.perform(get(REQUEST_REVIEWS_LIST + "?bookId=1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_REVIEWS_LIST))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_REVIEWS));
    }

    @Test
    void toCreateReview() throws Exception
    {
        mvc.perform(get(REQUEST_REVIEW_CREATE + "?bookId=1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_REVIEW_EDIT))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_REVIEW))
            .andExpect(model().attributeExists(MODEL_REVIEWS));
    }

    @Test
    void toEditReview() throws Exception
    {
        when(databaseService.getReviewById(BigInteger.ONE)).thenReturn(Mono.just(review1));
        mvc.perform(get(REQUEST_REVIEW_EDIT + "?reviewId=1&bookId=1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_REVIEW_EDIT))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_REVIEW))
            .andExpect(model().attributeExists(MODEL_REVIEWS));
    }
}