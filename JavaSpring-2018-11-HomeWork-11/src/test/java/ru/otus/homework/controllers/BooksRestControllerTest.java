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
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.dto.BookDto;
import ru.otus.homework.services.DatabaseService;

import java.math.BigInteger;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.synchronoss.cloud.nio.multipart.MultipartUtils.CONTENT_TYPE;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.outside.TestData.*;
import static ru.otus.outside.utils.TestUtil.convertObjectToJsonBytes;

@RunWith(SpringRunner.class)
@WebMvcTest(BooksRestController.class)
class BooksRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    DatabaseService databaseService;

    private Book book1;

    private Book book2;

    @BeforeEach
    void setUp()
    {
        book1 = createBook1();
        book2 = createBook2();
    }

    @Test
    void createBookWithNewGenre() throws Exception
    {
        Book book0 = createBook0();
        Genre genre0 = createGenre0();
        book0.setId(null);
        BookDto dto = new BookDto(book0);
        book0.setGenre(genre0);

        when(databaseService.getGenreByValue(book0.getGenre().getValue()))
            .thenReturn(Mono.empty());

        Book book01 = createBook0();
        book01.setId(null);
        book01.setGenre(null);
        when(databaseService.saveBookNewGenre(any(), any()))
            .thenReturn(Mono.just(book0));

        mvc.perform(post(REST_API + REST_V1_BOOKS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void createBook() throws Exception
    {
        Book book0 = createBook0();
        Genre genre0 = createGenre0();
        book0.setId(null);
        BookDto dto = new BookDto(book0);
        book0.setGenre(genre0);

        when(databaseService.getGenreByValue(book0.getGenre().getValue()))
            .thenReturn(Mono.just(genre0));

        when(databaseService.saveBookNewGenre(any(), any()))
            .thenReturn(Mono.just(book0));

        mvc.perform(post(REST_API + REST_V1_BOOKS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void readBooks() throws Exception
    {
        when(databaseService.getAllBooks())
            .thenReturn(Flux.just(book1, book2));

        MvcResult result = mvc.perform(
            get(REST_API + REST_V1_BOOKS)
                .contentType(APPLICATION_JSON)
        ).andReturn();

        mvc.perform(asyncDispatch(result))
        //  .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(book1.getId().toString())))
            .andExpect(jsonPath("$[0].isbn", is(book1.getIsbn())))
            .andExpect(jsonPath("$[0].title", is(book1.getTitle())))
            .andExpect(jsonPath("$[0].editionNumber", is(Integer.toString(book1.getEditionNumber()))))
            .andExpect(jsonPath("$[0].copyright", is(book1.getCopyright())))
            .andExpect(jsonPath("$[0].genre", is(book1.getGenre().getValue())))
            .andExpect(jsonPath("$[1].id", is(book2.getId().toString())))
            .andExpect(jsonPath("$[1].isbn", is(book2.getIsbn())))
            .andExpect(jsonPath("$[1].title", is(book2.getTitle())))
            .andExpect(jsonPath("$[1].editionNumber", is(Integer.toString(book2.getEditionNumber()))))
            .andExpect(jsonPath("$[1].copyright", is(book2.getCopyright())))
            .andExpect(jsonPath("$[1].genre", is(book2.getGenre().getValue())))
        ;
    }

    @Test
    void updateBookWithNewGenre() throws Exception
    {
        BookDto dto = new BookDto(book1);
        when(databaseService.getBookById(BigInteger.ONE)).thenReturn(Mono.just(book1));
        when(databaseService.getGenreByValue(book1.getGenre().getValue()))
            .thenReturn(Mono.empty());
        when(databaseService.saveBookNewGenre(book1, dto.getGenre())).thenReturn(Mono.just(book1));

        mvc.perform(put(REST_API + REST_V1_BOOKS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void updateBook() throws Exception
    {
        BookDto dto = new BookDto(book1);
        when(databaseService.getBookById(BigInteger.ONE)).thenReturn(Mono.just(book1));
        when(databaseService.getGenreByValue(book1.getGenre().getValue()))
            .thenReturn(Mono.just(book1.getGenre()));
        when(databaseService.saveBook(book1)).thenReturn(Mono.just(book1));

        mvc.perform(put(REST_API + REST_V1_BOOKS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception
    {
        when(databaseService.getBookById(BigInteger.ONE))
            .thenReturn(Mono.just(book1));
        doNothing().when(databaseService).removeBook(BigInteger.ONE);
        mvc.perform(delete(REST_API + REST_V1_BOOKS + "/{bookId}" , 1))
            .andExpect(status().isNoContent());
    }
}