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
import ru.otus.homework.models.dto.BookDto;
import ru.otus.homework.services.DatabaseService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.controllers.Constants.REST_API;
import static ru.otus.homework.controllers.Constants.REST_V1_BOOKS;
import static ru.otus.outside.utils.TestData.*;
import static ru.otus.outside.utils.TestUtil.convertObjectToJsonBytes;

@RunWith(SpringRunner.class)
@WebMvcTest(BooksRestController.class)
class BooksRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    DatabaseService databaseService;

    private Book book0;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp()
    {
        book0 = createBook0();
        book1 = createBook1();
        book2 = createBook2();
    }

    @Test
    void getBooks() throws Exception
    {
        when(databaseService.getAllBooks())
            .thenReturn(Arrays.asList(book1, book2));

        mvc.perform(get(REST_API + REST_V1_BOOKS))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].isbn", is(book1.getIsbn())))
            .andExpect(jsonPath("$[0].title", is(book1.getTitle())))
            .andExpect(jsonPath("$[0].editionNumber", is(Integer.toString(book1.getEditionNumber()))))
            .andExpect(jsonPath("$[0].copyright", is(book1.getCopyright())))
            .andExpect(jsonPath("$[0].genre", is(book1.getGenre().getValue())))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].isbn", is(book2.getIsbn())))
            .andExpect(jsonPath("$[1].title", is(book2.getTitle())))
            .andExpect(jsonPath("$[1].editionNumber", is(Integer.toString(book2.getEditionNumber()))))
            .andExpect(jsonPath("$[1].copyright", is(book2.getCopyright())))
            .andExpect(jsonPath("$[1].genre", is(book2.getGenre().getValue())))
        ;
    }

    @Test
    void updateBook() throws Exception
    {
        BookDto dto = new BookDto(book1);
        when(databaseService.getGenreByValue(book1.getGenre().getValue())).thenReturn(Optional.of(book1.getGenre()));
        when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));

        mvc.perform(put(REST_API + REST_V1_BOOKS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void createBook() throws Exception
    {
        BookDto dto = new BookDto(book0);
        when(databaseService.getGenreByValue(book1.getGenre().getValue())).thenReturn(Optional.empty());
        when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));

        mvc.perform(post(REST_API + REST_V1_BOOKS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void deleteBook() throws Exception
    {
        mvc.perform(delete(REST_API + REST_V1_BOOKS + "/{id}", 1))
            .andExpect(status().isNoContent());
    }
}