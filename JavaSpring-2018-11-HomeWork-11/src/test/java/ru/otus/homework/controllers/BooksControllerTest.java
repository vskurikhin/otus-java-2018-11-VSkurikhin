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
import ru.otus.homework.models.Book;
import ru.otus.homework.services.DatabaseService;

import java.math.BigInteger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.outside.TestData.createBook0;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BooksController.class)
@DisplayName("Integration tests for BookController")
class BooksControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DatabaseService databaseService;

    Book book1;

    @BeforeEach
    void setUp()
    {
        book1 = createBook0();
    }

    @Test
    void booksList() throws Exception
    {
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOKS_LIST))
            .andExpect(model().attributeExists(MODEL_BOOKS))
            .andExpect(model().attributeExists(MODEL_AUTHORS))
            .andExpect(model().attributeExists(MODEL_REVIEWS));
    }

    @Test
    void toCreateAuthor() throws Exception
    {
        mvc.perform(get(REQUEST_BOOK_CREATE))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOK_EDIT))
            .andExpect(model().attributeExists(MODEL_BOOK))
            .andExpect(model().attributeExists(MODEL_BOOKS));
    }

    @Test
    void toEditBook() throws Exception
    {
        when(databaseService.getBookById(BigInteger.ONE)).thenReturn(Mono.just(book1));
        mvc.perform(get(REQUEST_BOOK_EDIT + "?bookId=1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_BOOK_EDIT))
            .andExpect(model().attributeExists(MODEL_BOOK))
            .andExpect(model().attributeExists(MODEL_BOOKS));
    }
}