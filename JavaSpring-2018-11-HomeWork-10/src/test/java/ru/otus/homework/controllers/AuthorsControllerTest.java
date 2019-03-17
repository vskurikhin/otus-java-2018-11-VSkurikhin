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
@WebMvcTest(AuthorsController.class)
@DisplayName("Integration tests for AuthorController")
class AuthorsControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DatabaseService databaseService;

    Author author1;

    @BeforeEach
    void setUp()
    {
        author1 = createAuthor1();
    }

    @Test
    void bookAuthorsList() throws Exception
    {
        mvc.perform(get(REQUEST_BOOK_AUTHORS_LIST + "?bookId=1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOK_AUTHORS_LIST))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_AUTHORS));
    }

    @Test
    void toCreateAuthor() throws Exception
    {
        mvc.perform(get(REQUEST_AUTHOR_CREATE + "?bookId=1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOK_AUTHOR_EDIT))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_AUTHORS));
    }

    @Test
    void toEditAuthor() throws Exception
    {
        when(databaseService.getAuthorById(1L)).thenReturn(Optional.of(author1));
        mvc.perform(get(REQUEST_AUTHOR_EDIT + "?authorId=1&bookId=1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOK_AUTHOR_EDIT))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_AUTHORS));
    }
}