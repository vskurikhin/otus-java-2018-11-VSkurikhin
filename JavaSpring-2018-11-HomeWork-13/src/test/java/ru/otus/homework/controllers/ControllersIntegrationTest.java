package ru.otus.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.homework.configs.ApplicationConfig;
import ru.otus.homework.configs.YamlApplicationProperties;
import ru.otus.homework.dao.UserProfileDao;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.UserProfile;
import ru.otus.homework.models.dto.AuthorBookIdDto;
import ru.otus.homework.models.dto.BookDto;
import ru.otus.homework.models.dto.ReviewDto;
import ru.otus.homework.services.DatabaseService;
import ru.otus.homework.security.UserProfileDetailsService;

import javax.servlet.Filter;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.homework.controllers.Constants.*;
import static ru.otus.outside.utils.TestData.*;
import static ru.otus.outside.utils.TestUtil.convertObjectToJsonBytes;

@ExtendWith(SpringExtension.class)
@WebMvcTest({
    AuthorsController.class, AuthorsRestController.class,
    BooksController.class, BooksRestController.class,
    ReviewsController.class, ReviewsRestController.class,
    LoginController.class
})
@DisplayName("Integration tests for controllers")
public class ControllersIntegrationTest
{
    public static final String TEST_USER = "user";

    public static final String TEST_USER_PASSWORD_PLAIN = "123456";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @MockBean
    private UserProfileDetailsService userProfileDetailsService;

    private MockMvc mvc;

    @MockBean
    private DatabaseService databaseService;

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    YamlApplicationProperties yamlApplicationProperties;

    @MockBean
    UserProfileDao userProfileDao;

    private String userPasswordEncoded()
    {
        int strength = yamlApplicationProperties.getStrength();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        return passwordEncoder.encode(TEST_USER_PASSWORD_PLAIN);
    }

    void mockMvcAndUserProfileDao()
    {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilters(springSecurityFilterChain)
            .apply(springSecurity())
            .build();
        when(userProfileDao.findByLogin(TEST_USER))
            .thenReturn(Optional.of(new UserProfile(1L, TEST_USER, userPasswordEncoded(), false, false)));
    }

    @Nested
    @DisplayName("class AuthorsRestController")
    @WithMockUser
    class AuthorsControllerTest
    {
        Author author1;

        @BeforeEach
        void setUp()
        {
            author1 = createAuthor1();
            mockMvcAndUserProfileDao();
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

    @Nested
    @DisplayName("class AuthorsRestController")
    @WithMockUser
    class AuthorsRestControllerTest
    {
        private Author author1;

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
            mockMvcAndUserProfileDao();
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
            mvc.perform(delete(REST_API + REST_V1_AUTHORS + "/{authorId}/from-book/{bookId}", 1, 1))
                .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("class BookController")
    @WithMockUser
    class BooksControllerTest
    {
        Book book1;

        @BeforeEach
        void setUp()
        {
            book1 = createBook0();
            mockMvcAndUserProfileDao();
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
            when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));
            mvc.perform(get(REQUEST_BOOK_EDIT + "?bookId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_BOOK_EDIT))
                .andExpect(model().attributeExists(MODEL_BOOK))
                .andExpect(model().attributeExists(MODEL_BOOKS));
        }
    }

    @Nested
    @DisplayName("class BooksRestController")
    @WithMockUser
    class BooksRestControllerTest
    {
        private Book book0;

        private Book book1;

        private Book book2;

        @BeforeEach
        void setUp()
        {
            book0 = createBook0();
            book1 = createBook1();
            book2 = createBook2();
            mockMvcAndUserProfileDao();
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

    @Nested
    @DisplayName("class ReviewsController")
    @WithMockUser
    class ReviewsControllerTest
    {
        Review review1;

        @BeforeEach
        void setUp()
        {
            review1 = createReview1();
            mockMvcAndUserProfileDao();
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
            when(databaseService.getReviewById(1L)).thenReturn(Optional.of(review1));
            mvc.perform(get(REQUEST_REVIEW_EDIT + "?reviewId=1&bookId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_REVIEW_EDIT))
                .andExpect(model().attributeExists(MODEL_BOOK_ID))
                .andExpect(model().attributeExists(MODEL_REVIEW))
                .andExpect(model().attributeExists(MODEL_REVIEWS));
        }
    }

    @Nested
    @DisplayName("class ReviewsRestControllerTest")
    @WithMockUser
    class ReviewsRestControllerTest
    {
        private Book book1;

        private Review review1WithBook;

        private Review review2WithBook;

        @BeforeEach
        void setUp()
        {
            book1 = createBook1();
            review1WithBook = createReview1(book1);
            review2WithBook = createReview2(book1);
            mockMvcAndUserProfileDao();
        }

        @Test
        void getReviews() throws Exception
        {
            when(databaseService.getAllReviewsForBookById(1L))
                .thenReturn(Arrays.asList(review1WithBook, review2WithBook));

            mvc.perform(get(REST_API + REST_V1_REVIEWS + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bookId", is(1)))
                .andExpect(jsonPath("$[0].review", is(review1WithBook.getReview())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].bookId", is(1)))
                .andExpect(jsonPath("$[1].review", is(review2WithBook.getReview())));
        }

        @Test
        void countReviewsByBookId() throws Exception
        {
            when(databaseService.countReviewsByBookId(1L))
                .thenReturn(1L);

            mvc.perform(get(REST_API + REST_V1_REVIEWS + "/count/by-book/{id}", 1L))
                .andExpect(jsonPath("$.count", is(1)));
        }

        @Test
        void createReview() throws Exception
        {
            ReviewDto dto = new ReviewDto(createReview0());
            dto.setBookId(1L);
            when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));

            mvc.perform(post(REST_API + REST_V1_REVIEWS)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto))
            ).andExpect(status().isCreated());
        }

        @Test
        void updateReview() throws Exception
        {
            ReviewDto dto = new ReviewDto(review1WithBook);
            when(databaseService.getReviewById(1L)).thenReturn(Optional.of(review1WithBook));
            when(databaseService.getBookById(1L)).thenReturn(Optional.of(book1));

            mvc.perform(put(REST_API + REST_V1_REVIEWS)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto))
            ).andExpect(status().isOk());
        }

        @Test
        void deleteReview() throws Exception
        {
            mvc.perform(delete(REST_API + REST_V1_REVIEWS + "/{id}", 1))
                .andExpect(status().isNoContent());
        }
    }
}
