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
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.services.DatabaseService;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.outside.utils.TestData.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
@DisplayName("Integration tests for ReviewController")
class ReviewControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DatabaseService databaseService;

    Book testBook;

    @BeforeEach
    void setUp()
    {
        testBook = createBook0();
    }

    @Test
    void viewBookReviewList() throws Exception
    {
        Review review0 = createReview0(testBook);
        when(databaseService.getAllReviewsForBookById(0L)).thenReturn(Collections.singletonList(review0));
        mvc.perform(get(REQUEST_REVIEWS_LIST + "?bookId=0"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_REVIEWS_LIST))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_REVIEWS))
            .andExpect(content().string(containsString(review0.getReview())));
    }
}