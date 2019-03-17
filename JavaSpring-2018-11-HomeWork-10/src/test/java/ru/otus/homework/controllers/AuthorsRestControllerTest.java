package ru.otus.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.dto.AuthorBookIdDto;
import ru.otus.homework.services.DatabaseService;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.controllers.Constants.REST_API;
import static ru.otus.homework.controllers.Constants.REST_V1_AUTHORS;
import static ru.otus.outside.utils.TestData.*;
import static ru.otus.outside.utils.TestUtil.convertObjectToJsonBytes;


@RunWith(SpringRunner.class)
@WebMvcTest(AuthorsRestController.class)
class AuthorsRestControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    DatabaseService databaseService;

    private Author author1 = createAuthor1();

    private Author author2 = createAuthor2();

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
    void getAuthorsForBookId() throws Exception
    {
        when(databaseService.getAuthorsForBookId(1L))
            .thenReturn(Arrays.asList(author1, author2));

        mvc.perform(get(REST_API + REST_V1_AUTHORS + "/{bookId}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].bookId", is(1)))
            .andExpect(jsonPath("$[0].firstName", is(author1.getFirstName())))
            .andExpect(jsonPath("$[0].lastName", is(author1.getLastName())))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].bookId", is(1)))
            .andExpect(jsonPath("$[1].firstName", is(author2.getFirstName())))
            .andExpect(jsonPath("$[1].lastName", is(author2.getLastName())));
    }

    @Test
    void updateAuthor() throws Exception
    {
        AuthorBookIdDto dto = new AuthorBookIdDto(author1);
        when(databaseService.getAuthorById(1L)).thenReturn(Optional.of(author1));

        mvc.perform(put(REST_API + REST_V1_AUTHORS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void createAuthor() throws Exception
    {
        AuthorBookIdDto dto = new AuthorBookIdDto(createAuthor0());
        dto.setBookId(1L);
        when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));
        when(databaseService.getAuthorByFirstNameAndLastName(dto.getFirstName(), dto.getFirstName()))
            .thenReturn(Optional.empty());

        mvc.perform(post(REST_API + REST_V1_AUTHORS)
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(dto))
        ).andExpect(status().isCreated());
    }

    @Test
    void deleteAuthorFromBook() throws Exception
    {
        when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));
        when(databaseService.getAuthorById(1L)).thenReturn(Optional.of(author1));
        mvc.perform(delete(REST_API + REST_V1_AUTHORS + "/{authorId}/from-book/{bookId}" , 1, 1))
            .andExpect(status().isNoContent());
    }
}