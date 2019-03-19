package ru.otus.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.dao.ReviewDao;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;
import ru.otus.homework.services.DatabaseService;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@DisplayName("Integration tests for data layer")
class DataLayerIntegrationTest
{
    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private DatabaseService databaseService;

    private boolean hasAuthor(Author author)
    {
        assertNotNull(author);
        assertNotNull(author.getId());

        Boolean result;
        Mono<Boolean> mayBe = authorDao.findById(author.getId()).hasElement();

        assertFalse(Objects.isNull(mayBe));
        assertNotNull(result = mayBe.block());

        return result;
    }

    private boolean hasBook(Book book)
    {
        assertNotNull(book);
        assertNotNull(book.getId());

        Boolean result;
        Mono<Boolean> mayBe = bookDao.findById(book.getId()).hasElement();

        assertFalse(Objects.isNull(mayBe));
        assertNotNull(result = mayBe.block());

        return result;
    }

    private boolean hasGenre(Genre genre)
    {
        assertNotNull(genre);
        assertNotNull(genre.getId());

        Boolean result;
        Mono<Boolean> mayBe = genreDao.findById(genre.getId()).hasElement();

        assertFalse(Objects.isNull(mayBe));
        assertNotNull(result = mayBe.block());

        return result;
    }

    private boolean hasReview(Review review)
    {
        assertNotNull(review);
        assertNotNull(review.getId());

        Boolean result;
        Mono<Boolean> mayBe = reviewDao.findById(review.getId()).hasElement();

        assertFalse(Objects.isNull(mayBe));
        assertNotNull(result = mayBe.block());

        return result;
    }

    private Boolean reverse(Boolean value)
    {
        assertFalse(Objects.isNull(value));

        return ! value;
    }

    @Nested
    @DisplayName("Class AuthorDao")
    class AuthorDaoTest
    {
        Author author1;

        @BeforeEach
        void setUp()
        {
            mongo.getDb().drop();
            author1 = createAuthor1();
        }

        private Author saveAuthor(Author author1)
        {
            return authorDao.save(author1).block();
        }

        @Test
        void insert()
        {
            Author saved = saveAuthor(author1);
            assertEquals(author1, saved);
        }

        @Test
        void update()
        {
            Author saved = saveAuthor(author1);
            assertTrue(hasAuthor(saved));

            saved.setFirstName(saved.getFirstName() + "_test");
            saved.setLastName(saved.getLastName() + "_test");

            Author updated = saveAuthor(saved);
            assertEquals(saved, updated);
        }

        @Test
        void remove()
        {
            Author saved = saveAuthor(author1);
            assertTrue(hasAuthor(saved));

            authorDao.deleteById(saved.getId()).block();
            Boolean doesNotExist = reverse(hasAuthor(saved));
            assertTrue(doesNotExist);
        }
    }

    @Nested
    @DisplayName("Class GenreDao")
    class GenreDaoTest
    {
        Genre genre1;

        @BeforeEach
        void setUp()
        {
            mongo.getDb().drop();
            genre1 = createGenre1();
        }

        private Genre saveGenre(Genre genre1)
        {
            return genreDao.save(genre1).block();
        }

        @Test
        void insert()
        {
            Genre saved = saveGenre(genre1);
            assertTrue(hasGenre(saved));
            assertEquals(genre1, saved);
        }

        @Test
        void update()
        {
            Genre saved = saveGenre(genre1);
            assertTrue(hasGenre(saved));

            Genre genre = genreDao.findById(saved.getId()).block();
            assertNotNull(genre);

            genre.setValue(genre.getValue() + "_test");

            Genre updated = saveGenre(genre);
            assertEquals(genre, updated);
        }

        @Test
        void remove()
        {
            Genre saved = saveGenre(genre1);
            assertTrue(hasGenre(saved));

            genreDao.deleteById(saved.getId()).block();
            Boolean doesNotExist = reverse(hasGenre(saved));
            assertTrue(doesNotExist);
        }
    }

    @Nested
    @DisplayName("Class BookDao")
    class BookDaoTest
    {
        Book book1;

        @BeforeEach
        void setUp()
        {
            mongo.getDb().drop();
            book1 = createBook1();
        }

        private Book saveBook(Book book1)
        {
            return bookDao.save(book1).block();
        }

        @Test
        void insert()
        {
            Book saved = saveBook(book1);
            assertTrue(hasBook(saved));
            assertEquals(book1, saved);
        }

        @Test
        void update()
        {
            Book saved = saveBook(book1);
            assertTrue(hasBook(saved));

            Book book = bookDao.findById(saved.getId()).block();
            assertNotNull(book);

            book.setTitle(book.getTitle() + "_test");

            Book updated = saveBook(book);
            assertEquals(book, updated);
        }

        @Test
        void remove()
        {
            Book saved = saveBook(book1);
            assertTrue(hasBook(saved));

            bookDao.deleteById(saved.getId()).block();
            Boolean doesNotExist = reverse(hasBook(saved));
            assertTrue(doesNotExist);
        }
    }

    @Nested
    @DisplayName("Class ReviewDao")
    class ReviewDaoTest
    {
        Review review1;

        @BeforeEach
        void setUp()
        {
            mongo.getDb().drop();
            review1 = createReview1();
        }

        private Review saveReview(Review review1)
        {
            return reviewDao.save(review1).block();
        }

        private Review findById(BigInteger id)
        {
            return reviewDao.findById(id).block();
        }

        @Test
        void insert()
        {
            Review saved = saveReview(review1);
            assertTrue(hasReview(saved));
            assertEquals(review1, saved);
        }

        @Test
        void update()
        {
            Review saved = saveReview(review1);
            assertTrue(hasReview(saved));

            Review review = findById(saved.getId());
            assertNotNull(review);

            review.setReview(review.getReview() + "_test");

            Review updated = saveReview(review);
            assertEquals(review, updated);
        }

        private Boolean reverse(Boolean value)
        {
            assertFalse(Objects.isNull(value));

            return ! value;
        }

        @Test
        void remove()
        {
            Review saved = saveReview(review1);
            assertTrue(hasReview(saved));

            reviewDao.deleteById(saved.getId()).block();
            Boolean doesNotExist = reverse(hasReview(saved));
            assertTrue(doesNotExist);
        }
    }

    @Nested
    @DisplayName("Class DatabaseService")
    class DatabaseServiceTest
    {
        Author author1, author2;

        Book book0, book1, book2;

        Review review1, review2;

        @BeforeEach
        void setUp()
        {
            mongo.getDb().drop();
            book0 = createBook0();
            author1 = createAuthor1();
            author1.setId(null);
            author2 = createAuthor2();
            author2.setId(null);
            book1 = createBook1();
            book1.setId(null);
            book2 = createBook2();
            book2.setId(null);
            review1 = createReview1(book1);
            review2 = createReview2(book1);
        }

        @Test
        void getAuthorById()
        {
            Author saved = databaseService.saveAuthor(author1).block();
            assertTrue(hasAuthor(saved));
            Boolean exists = databaseService.getAuthorById(saved.getId()).hasElement().block();
            assertNotNull(exists);
            assertTrue(exists);
        }

        @Test
        void getAuthorByFirstNameAndLastName()
        {
            Author saved = databaseService.saveAuthor(author1).block();
            assertNotNull(saved);
            assertNotNull(saved.getId());
            Boolean exists = databaseService.getAuthorByFirstNameAndLastName(
                saved.getFirstName(), saved.getLastName()
            ).hasElement().block();
            assertNotNull(exists);
            assertTrue(exists);
        }

        @Test
        void getAuthorsForBookId()
        {
            book1.getAuthors().add(author1);
            book1.getAuthors().add(author2);
            Book saved = databaseService.saveBookWithAuthorsAndGenre(book1);
            assertTrue(hasBook(saved));
            assertEquals(book1, saved);
            List<Author> authors =  databaseService.getAuthorsForBookId(book1.getId()).collectList().block();
            assertNotNull(authors);
            assertTrue(authors.contains(author1));
            assertTrue(authors.contains(author2));
        }

        @Test
        void getAllAuthors()
        {
            Author saved1 = databaseService.saveAuthor(author1).block();
            assertTrue(hasAuthor(saved1));
            Author saved2 = databaseService.saveAuthor(author2).block();
            assertTrue(hasAuthor(saved2));
            List<Author> authors =  databaseService.getAllAuthors().collectList().block();
            assertNotNull(authors);
            assertTrue(authors.contains(saved1));
            assertTrue(authors.contains(saved2));
        }

        @Test
        void removeAuthor() throws InterruptedException
        {
            Author saved = databaseService.saveAuthor(author1).block();
            assertTrue(hasAuthor(saved));
            databaseService.removeAuthor(saved.getId());
            assertFalse(hasAuthor(saved));
        }

        @Test
        void addAuthorToBook()
        {
            Author author = databaseService.saveAuthor(author1).block();
            assertTrue(hasAuthor(author));
            Genre genre = databaseService.saveGenre(book0.getGenre()).block();
            assertTrue(hasGenre(genre));
            Book book = databaseService.saveBook(book0).block();
            assertTrue(hasBook(book));

            Author added = databaseService.addAuthorToBook(book, author).block();
            assertTrue(hasAuthor(added));
        }


        @Test
        void getBookById()
        {
            Genre genre = databaseService.saveGenre(book0.getGenre()).block();
            assertTrue(hasGenre(genre));
            Book saved = databaseService.saveBook(book0).block();
            assertTrue(hasBook(saved));
            Boolean exists = databaseService.getBookById(saved.getId()).hasElement().block();
            assertNotNull(exists);
            assertTrue(exists);
        }

        @Test
        void getAllBooks()
        {
            Genre genre1 = databaseService.saveGenre(book1.getGenre()).block();
            assertTrue(hasGenre(genre1));
            Genre genre2 = databaseService.saveGenre(book2.getGenre()).block();
            assertTrue(hasGenre(genre2));
            Book saved1 = databaseService.saveBook(book1).block();
            assertTrue(hasBook(saved1));
            Book saved2 = databaseService.saveBook(book2).block();
            assertTrue(hasBook(saved2));
            List<Book> books =  databaseService.getAllBooks().collectList().block();
            assertNotNull(books);
            assertTrue(books.contains(saved1));
            assertTrue(books.contains(saved2));
        }

        @Test
        void removeBook() throws InterruptedException
        {
            Genre genre = databaseService.saveGenre(book0.getGenre()).block();
            assertTrue(hasGenre(genre));
            Book saved = databaseService.saveBook(book0).block();
            assertTrue(hasBook(saved));
            databaseService.removeBook(saved.getId());
            assertFalse(hasBook(saved));
        }


        //////////////////////////////////////////////////////////////////////////////////////////////////////
        @Test
        void saveBookNewGenre()
        {
            Book saved = databaseService.saveBookNewGenre(book0, TEST_GENRE_NAME).block();
            assertTrue(hasBook(saved));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////

        @Test
        void countReviewsByBookId()
        {
            Book book = databaseService.saveBookWithAuthorsAndGenre(review1.getBook());
            assertTrue(hasBook(book));
            Review saved1 = databaseService.saveReview(review1).block();
            assertTrue(hasReview(saved1));
            Review saved2 = databaseService.saveReview(review2).block();
            assertTrue(hasReview(saved2));

            Long count = databaseService.countReviewsByBookId(book.getId()).block();
            assertNotNull(count);
            assertEquals(2, count.longValue());
        }

        @Test
        void getReviewById()
        {
            Book book = databaseService.saveBookWithAuthorsAndGenre(review1.getBook());
            assertTrue(hasBook(book));
            Review saved = databaseService.saveReview(review1).block();
            assertTrue(hasReview(saved));
            Boolean exists = databaseService.getReviewById(saved.getId()).hasElement().block();
            assertNotNull(exists);
            assertTrue(exists);
        }

        @Test
        void getAllReviews()
        {
            Book book = databaseService.saveBookWithAuthorsAndGenre(review1.getBook());
            assertTrue(hasBook(book));
            Review saved1 = databaseService.saveReview(review1).block();
            assertTrue(hasReview(saved1));
            Review saved2 = databaseService.saveReview(review2).block();
            assertTrue(hasReview(saved2));
            List<Review> reviews =  databaseService.getAllReviews().collectList().block();
            assertNotNull(reviews);
            assertTrue(reviews.contains(saved1));
            assertTrue(reviews.contains(saved2));
        }
    }
}