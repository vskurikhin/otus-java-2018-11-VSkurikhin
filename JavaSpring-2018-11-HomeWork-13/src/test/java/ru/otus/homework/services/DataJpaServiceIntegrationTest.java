package ru.otus.homework.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.Main;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@WithMockUser(username = "user", authorities = "ROLE_USERS")
@DisplayName("Integration tests for data layer with class DataJpaService")
class DataJpaServiceIntegrationTest
{
    @Autowired
    private DatabaseService databaseService;

    @Nested
    @DisplayName("authors methods")
    class AuthorsMethods
    {
        @Test
        @WithMockUser(username = "user", authorities = "ROLE_USERS")
        @DisplayName("persists when save then update, however disappears when delete")
        void testCreateUpdateDelete() throws Exception
        {
            List<Author> expectedState = databaseService.getAllAuthors();

            // Create
            Author expected = createAuthor0();
            databaseService.saveAuthor(expected);

            List<Author> testList = databaseService.getAllAuthors();
            assertFalse(testList.isEmpty());
            assertTrue(testList.size() > expectedState.size());

            Author createdTest = databaseService.getAuthorById(expected.getId()).orElse(null);
            assertNotNull(createdTest);
            assertEquals(expected, createdTest);

            // Update
            expected.setFirstName(expected.getFirstName() + "_test");
            expected.setLastName(expected.getLastName() + "_test");
            databaseService.saveAuthor(expected);
            Author updatedTest = databaseService.getAuthorById(expected.getId()).orElse(null);
            assertNotNull(updatedTest);
            assertEquals(expected, updatedTest);

            // Delete
            databaseService.removeAuthor(updatedTest.getId());

            Author nullResult = databaseService.getAuthorById(updatedTest.getId()).orElse(null);
            assertNull(nullResult);

            // check State
            List<Author> finishList = databaseService.getAllAuthors();
            assertEquals(expectedState, finishList);
        }

        @Test
        @WithMockUser(username = "user", authorities = "ROLE_USERS")
        @DisplayName("test author unique constraint by first_name and last_name")
        void testUniqueConstraint()
        {
            List<Author> expectedState = databaseService.getAllAuthors();
            Author saved = createAuthor0();
            databaseService.saveAuthor(saved);
            Author failed = createAuthor0();
            assertThrows(DataIntegrityViolationException.class, () -> databaseService.saveAuthor(failed));

            databaseService.removeAuthor(saved.getId());
            List<Author> finishList = databaseService.getAllAuthors();
            assertEquals(expectedState, finishList);
        }
    }

    @Nested
    @DisplayName("genres methods")
    class GenresMethods
    {
        @Test
        @WithMockUser(username = "user", authorities = "ROLE_USERS")
        @DisplayName("persists when save then update, however disappears when delete")
        void testCreateUpdateDelete() throws Exception
        {
            List<Genre> expectedState = databaseService.getAllGenres();

            // Create
            Genre expected = createGenre0();
            databaseService.saveGenre(expected);

            List<Genre> testList = databaseService.getAllGenres();
            assertFalse(testList.isEmpty());
            assertTrue(testList.size() > expectedState.size());

            Genre createdTest = databaseService.getGenreById(expected.getId()).orElse(null);
            assertNotNull(createdTest);
            assertEquals(expected, createdTest);

            // Update
            expected.setValue(expected.getValue() + "_test");
            databaseService.saveGenre(expected);
            Genre updatedTest = databaseService.getGenreById(expected.getId()).orElse(null);
            assertNotNull(updatedTest);
            assertEquals(expected, updatedTest);

            // Delete
            databaseService.removeGenre(updatedTest.getId());

            Genre nullResult = databaseService.getGenreById(updatedTest.getId()).orElse(null);
            assertNull(nullResult);

            // check State
            List<Genre> finishList = databaseService.getAllGenres();
            assertEquals(expectedState, finishList);
        }

        @Test
        @WithMockUser(username = "user", authorities = "ROLE_USERS")
        @DisplayName("test genre unique constraint by first_name and last_name")
        void testUniqueConstraint()
        {
            List<Genre> expectedState = databaseService.getAllGenres();
            Genre saved = createGenre0();
            databaseService.saveGenre(saved);
            Genre failed = createGenre0();
            assertThrows(DataIntegrityViolationException.class, () -> databaseService.saveGenre(failed));

            databaseService.removeGenre(saved.getId());
            List<Genre> finishList = databaseService.getAllGenres();
            assertEquals(expectedState, finishList);
        }
    }

    @Nested
    @DisplayName("books methods")
    class BooksMethods
    {
        @Test
        @WithMockUser(username = "user", authorities = "ROLE_USERS")
        @DisplayName("persists when save then update, however disappears when delete")
        void testCreateUpdateDelete() throws Exception
        {
            List<Book> expectedBooksState = databaseService.getAllBooks();
            List<Genre> expectedGenresState = databaseService.getAllGenres();

            // Create
            Genre expectedGenre = createGenre0();

            Book expectedBook = createBook0(expectedGenre);
            databaseService.saveBook(expectedBook);

            List<Book> testList = databaseService.getAllBooks();
            assertFalse(testList.isEmpty());
            assertTrue(testList.size() > expectedBooksState.size());

            Book createdBookTest = databaseService.getBookById(expectedBook.getId()).orElse(null);
            assertNotNull(createdBookTest);

            assertTrue(expectedBook.getAuthors().isEmpty());
            assertTrue(createdBookTest.getAuthors().isEmpty());
            // createdBookTest.setAuthors(expectedBook.getAuthors());
            assertEquals(expectedBook, createdBookTest);


            // Update
            expectedBook.setIsbn(expectedBook.getIsbn() + "_test");
            expectedBook.setTitle(expectedBook.getTitle() + "_test");
            expectedBook.setEditionNumber(expectedBook.getEditionNumber() + 1);
            expectedBook.setCopyright(expectedBook.getCopyright() + "_test");
            databaseService.saveBook(expectedBook);
            Book updatedTest = databaseService.getBookById(expectedBook.getId()).orElse(null);
            assertNotNull(updatedTest);

            assertTrue(expectedBook.getAuthors().isEmpty());
            assertTrue(updatedTest.getAuthors().isEmpty());
            updatedTest.setAuthors(expectedBook.getAuthors());

            assertEquals(expectedBook, updatedTest);

            // Delete
            databaseService.removeBook(updatedTest.getId());
            databaseService.removeGenre(expectedGenre.getId());

            Book nullResult = databaseService.getBookById(updatedTest.getId()).orElse(null);
            assertNull(nullResult);

            // check State
            List<Genre> finishGenresList = databaseService.getAllGenres();
            assertEquals(expectedGenresState, finishGenresList);
            List<Book> finishList = databaseService.getAllBooks();
            assertEquals(expectedBooksState, finishList);
        }
    }

    @Nested
    @DisplayName("Security ACL tests")
    class ACLTest
    {
        @Test
        @WithMockUser(username = "user", authorities = "ROLE_USERS")
        public void getAllBooks() throws Exception
        {
            List<Book> books = databaseService.getAllBooks();
            assertEquals(0, books.size());
        }
    }
}
