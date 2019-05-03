package ru.otus.homework.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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

        @Test
        @DisplayName("save book with authors")
        void testCreateDeleteWithAuthors() throws Exception
        {
            List<Author> expectedAuthorsState = databaseService.getAllAuthors();
            List<Book> expectedBooksState = databaseService.getAllBooks();
            List<Genre> expectedGenresState = databaseService.getAllGenres();

            // Create
            Genre expectedGenre = createGenre0();
            Author author0 = createAuthor0();
            Author author1 = createAuthor1();
            author1.setId(0L);
            Book expectedBook = createBook0(expectedGenre, author0, author1);
            databaseService.saveBook(expectedBook);

            List<Book> testList = databaseService.getAllBooks();
            assertFalse(testList.isEmpty());
            assertTrue(testList.size() > expectedBooksState.size());

            Book createdBookTest = databaseService.getBookById(expectedBook.getId()).orElse(null);
            assertNotNull(createdBookTest);
            assertEquals(expectedBook, createdBookTest);

            // Update
            expectedBook.setIsbn(expectedBook.getIsbn() + "_test");
            expectedBook.setTitle(expectedBook.getTitle() + "_test");
            expectedBook.setEditionNumber(expectedBook.getEditionNumber() + 1);
            expectedBook.setCopyright(expectedBook.getCopyright() + "_test");
            databaseService.saveBook(expectedBook);
            Book updatedTest = databaseService.getBookById(expectedBook.getId()).orElse(null);
            assertNotNull(updatedTest);
            assertEquals(expectedBook, updatedTest);

            List<Book> bookList = databaseService.getAllBooksByAuthorId(author0.getId());
            assertTrue(bookList.contains(expectedBook));
            long count = databaseService.countBooksByAuthorId(author0.getId());
            assertTrue(count > 0);

            // Delete
            List<Author> authors = updatedTest.getAuthors();
            databaseService.removeBook(createdBookTest.getId());
            authors.forEach(author -> databaseService.removeAuthor(author.getId()));
            databaseService.removeGenre(expectedGenre.getId());

            // check State
            List<Genre> finishGenresList = databaseService.getAllGenres();
            assertEquals(expectedGenresState, finishGenresList);
            List<Book> finishList = databaseService.getAllBooks();
            assertEquals(expectedBooksState, finishList);
            List<Author> finishAuthorsList = databaseService.getAllAuthors();
            assertEquals(expectedAuthorsState, finishAuthorsList);
        }

        @Test
        @DisplayName("save book")
        void testSave() throws Exception
        {
            List<Book> expectedBooksState = databaseService.getAllBooks();
            List<Genre> expectedGenresState = databaseService.getAllGenres();

            // Create
            Genre genre0 = createGenre0();

            Book book0 = createBook0(genre0);
            databaseService.saveBook(book0);
            List<Book> booksList1 = databaseService.getAllBooks();

            // Genre genre1 = createGenre1();
            Genre savedGenre = databaseService.getGenreByValue(genre0.getValue()).get();
            savedGenre.setValue(savedGenre.getValue() + "_test");
            book0.setGenre(savedGenre);

            databaseService.saveBook(book0);
            Book updatedTest = databaseService.getBookById(book0.getId()).orElse(null);
            assertNotNull(updatedTest);
            book0.getGenre().setId(updatedTest.getGenre().getId());
            assertEquals(book0, updatedTest);

            // Delete
            databaseService.removeBook(book0.getId());
            List<Genre> list1 = databaseService.getAllGenres();
            List<Book> list2 = databaseService.getAllBooks();
            databaseService.removeGenre(savedGenre.getId());

            // check State
            List<Genre> finishGenresList = databaseService.getAllGenres();
            assertEquals(expectedGenresState, finishGenresList);
            List<Book> finishList = databaseService.getAllBooks();
            assertEquals(expectedBooksState, finishList);
        }

        @Test
        @DisplayName("save book with reviews")
        void testCreateDeleteWithReviews() throws Exception
        {
            List<Review> expectedReviewsState = databaseService.getAllReviews();
            List<Book> expectedBooksState = databaseService.getAllBooks();
            List<Genre> expectedGenresState = databaseService.getAllGenres();

            // Create
            Genre expectedGenre = createGenre0();

            Book expectedBook = createBook0(expectedGenre);
            databaseService.saveBook(expectedBook);
            List<Book> testBooksList = databaseService.getAllBooks();
            assertFalse(testBooksList.isEmpty());
            assertTrue(testBooksList.size() > expectedBooksState.size());
            Book createdBookTest = databaseService.getBookById(expectedBook.getId()).orElse(null);
            assertNotNull(createdBookTest);
            assertEquals(expectedBook, createdBookTest);

            Review review0 = createReview0(expectedBook);
            databaseService.saveReview(review0);
            Review review1 = createReview0(expectedBook);
            databaseService.saveReview(review1);

            List<Review> testReviewsList = databaseService.getAllReviews();
            assertFalse(testReviewsList.isEmpty());
            assertTrue(testReviewsList.size() > expectedReviewsState.size());

            Review createdReview0 = databaseService.getReviewById(review0.getId()).orElse(null);
            assertNotNull(createdReview0);
            assertEquals(review0.getBook(), createdReview0.getBook());
            Review createdReview1 = databaseService.getReviewById(review1.getId()).orElse(null);
            assertNotNull(createdReview1);
            assertEquals(review1, createdReview1);

            // Update
            review0.setReview(review0.getReview() + "_test");
            databaseService.saveReview(review0);
            Review updatedReview0 = databaseService.getReviewById(review0.getId()).orElse(null);
            assertNotNull(updatedReview0);
            assertEquals(review0, updatedReview0);

            // find by book id
            List<Review> testList = databaseService.getAllReviewsForBookById(createdBookTest.getId());
            assertEquals(2, testList.size());
            assertTrue(testList.contains(updatedReview0));
            assertTrue(testList.contains(createdReview1));

            // Delete
            databaseService.removeReview(updatedReview0.getId());
            databaseService.removeReview(createdReview1.getId());
            databaseService.removeBook(createdBookTest.getId());
            databaseService.removeGenre(expectedGenre.getId());

            // check State
            List<Genre> finishGenresList = databaseService.getAllGenres();
            assertEquals(expectedGenresState, finishGenresList);
            List<Book> finishList = databaseService.getAllBooks();
            assertEquals(expectedBooksState, finishList);
            List<Review> finishReviewsList = databaseService.getAllReviews();
            assertEquals(expectedReviewsState, finishReviewsList);
        }
    }
}
