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
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.dto.AuthorBookIdDto;
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
import static ru.otus.homework.controllers.Constants.REST_API;
import static ru.otus.homework.controllers.Constants.REST_V1_AUTHORS;
import static ru.otus.outside.TestData.*;
import static ru.otus.outside.utils.TestUtil.convertObjectToJsonBytes;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorsRestController.class)
@DisplayName("Integration tests for AuthorController")
class AuthorsRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    DatabaseService databaseService;

    private Author author1 ;

    private Author author2;

    private Book book1;

    @BeforeEach
    void setUp()
    {
        author1 = createAuthor1();
        author2 = createAuthor2();
        book1 = createBook1();
        book1.getAuthors().add(author1);
        book1.getAuthors().add(author2);
    }

    @Test
    void createAuthor() throws Exception
    {
        Author author0 = createAuthor0();
        author0.setId(null);
        AuthorBookIdDto dto = new AuthorBookIdDto(author0);
        dto.setBookId("1");
        when(databaseService.getBookById(BigInteger.ONE)).thenReturn(Mono.just(book1));
        when(databaseService.addAuthorToBook(book1, author0)).thenReturn(Mono.just(author0));

        mvc.perform(post(REST_API + REST_V1_AUTHORS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void readAuthorsForBookId() throws Exception
    {
        when(databaseService.getAuthorsForBookId(BigInteger.ONE))
            .thenReturn(Flux.just(author1, author2));

        MvcResult result = mvc.perform(
            get(REST_API + REST_V1_AUTHORS + "/{bookId}", 1L)
                .contentType(APPLICATION_JSON)
        ).andReturn();

        mvc.perform(asyncDispatch(result))
        //  .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(author1.getId().toString())))
            .andExpect(jsonPath("$[0].bookId", is(book1.getId().toString())))
            .andExpect(jsonPath("$[0].firstName", is(author1.getFirstName())))
            .andExpect(jsonPath("$[0].lastName", is(author1.getLastName())))
            .andExpect(jsonPath("$[1].id", is(author2.getId().toString())))
            .andExpect(jsonPath("$[1].bookId", is(book1.getId().toString())))
            .andExpect(jsonPath("$[1].firstName", is(author2.getFirstName())))
            .andExpect(jsonPath("$[1].lastName", is(author2.getLastName())));
        ;
    }

    @Test
    void updateAuthor() throws Exception
    {
        AuthorBookIdDto dto = new AuthorBookIdDto(author1);
        when(databaseService.getAuthorById(BigInteger.ONE)).thenReturn(Mono.just(author1));
        when(databaseService.saveAuthor(author1)).thenReturn(Mono.just(author1));

        mvc.perform(put(REST_API + REST_V1_AUTHORS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void deleteAuthorFromBook() throws Exception
    {
        when(databaseService.getBookById(BigInteger.ONE)).thenReturn(Mono.just(book1));
        when(databaseService.getAuthorById(BigInteger.ONE)).thenReturn(Mono.just(author1));
        when(databaseService.saveBook(book1)).thenReturn(Mono.just(book1));
        when(databaseService.removeAuthorAsync(author1.getId())).thenReturn(Mono.empty());
        doNothing().when(databaseService).removeAuthor(BigInteger.ONE);
        mvc.perform(delete(REST_API + REST_V1_AUTHORS + "/{authorId}/from-book/{bookId}" , 1, 1))
            .andExpect(status().isNoContent());
    }
}