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
@WebMvcTest(AuthorController.class)
@DisplayName("Integration tests for AuthorController")
class AuthorControllerTest
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
    void viewBookAuthorsList() throws Exception
    {
        Author author = createAuthor0();
        testBook.getAuthors().add(author);
        when(databaseService.getBookById(0L)).thenReturn(Optional.of(testBook));
        mvc.perform(get(REQUEST_BOOK_AUTHORS_LIST + "?bookId=0"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOK_AUTHORS_LIST))
            .andExpect(model().attributeExists(MODEL_BOOK_ID))
            .andExpect(model().attributeExists(MODEL_AUTHORS))
            .andExpect(content().string(containsString(author.getFirstName())))
            .andExpect(content().string(containsString(author.getLastName())));
    }

    @Test
    public void saveAuthor() throws Exception {
        Author author0 = createAuthor0();
        testBook.getAuthors().add(author0);
        assertEquals(author0, testBook.getAuthors().get(0));
        when(databaseService.getAuthorById(0L)).thenReturn(Optional.of(author0));
        when(databaseService.getBookById(0L)).thenReturn(Optional.of(testBook));
        mvc.perform(
            post(REQUEST_AUTHOR_EDIT + "?authorId=0&bookId=0").
            param("firstName", author0.getFirstName()).
            param("lastName", author0.getLastName()).
            param("id", "0")
        ).andExpect(status().isFound());
        verify(databaseService).addAuthorToBook(author0, testBook);
    }
}