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
import ru.otus.homework.models.Book;
import ru.otus.homework.services.DatabaseService;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.outside.utils.TestData.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@DisplayName("Integration tests for BookController")
class BookControllerTest
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
    void viewBooksList() throws Exception
    {
        when(databaseService.getAllBooks()).thenReturn(Collections.singletonList(testBook));
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOKS_LIST))
            .andExpect(model().attributeExists(MODEL_BOOKS))
            .andExpect(content().string(containsString(testBook.getIsbn())))
            .andExpect(content().string(containsString(testBook.getTitle())))
            .andExpect(content().string(containsString(testBook.getCopyright())))
            .andExpect(content().string(containsString(testBook.getGenre().getValue())));
    }

    @Test
    public void saveBookNewGenre() throws Exception {
        when(databaseService.getBookById(0L)).thenReturn(Optional.of(testBook));
        mvc.perform(post("/book-edit?bookId=0").
            param("isbn", testBook.getIsbn()).
            param("title", testBook.getTitle()).
            param("editionNumber", Integer.toString(testBook.getEditionNumber())).
            param("copyright", testBook.getCopyright()).
            param("genre", testBook.getGenre().getValue())
        ).andExpect(status().isFound());
        verify(databaseService).saveBookNewGenre(testBook, testBook.getGenre().getValue());
    }

    @Test
    public void saveBook() throws Exception {
        when(databaseService.getBookById(0L)).thenReturn(Optional.of(testBook));
        when(databaseService.getGenreByValue(testBook.getGenre().getValue()))
            .thenReturn(Optional.of(testBook.getGenre()));
        mvc.perform(post("/book-edit?bookId=0").
            param("isbn", testBook.getIsbn()).
            param("title", testBook.getTitle()).
            param("editionNumber", Integer.toString(testBook.getEditionNumber())).
            param("copyright", testBook.getCopyright()).
            param("genre", testBook.getGenre().getValue())
        ).andExpect(status().isFound());
        verify(databaseService).saveBook(testBook);
    }
}