package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("BookRepositoryImplTest")
class BookRepositoryImplTest
{
    @Autowired
    MongoTemplate mongo;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Book testBook;

    @BeforeEach
    public void setUp()
    {
        mongo.getDb().drop();
        testBook = createBook0();
    }

    Book saveTestBook()
    {
        return bookRepository.saveWithAuthorsAndGenres(testBook);
    }

    @Test
    public void insert()
    {
        Book saved = saveTestBook();
        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    Book finfById(String id)
    {
        Optional<Book> optionalBook = bookRepository.findById(id);
        assertTrue(optionalBook.isPresent());

        return optionalBook.get();
    }

    @Test
    public void update()
    {
        Book saved = saveTestBook();
        Book book = finfById(saved.getId());
        book.getAuthors().add(createAuthor2());
        book.getGenres().add(createGenre2());
        Book updated = bookRepository.saveWithAuthorsAndGenres(book);

        assertTrue(updated.getAuthors().containsAll(book.getAuthors()));
        assertTrue(updated.getGenres().containsAll(book.getGenres()));
    }

    Review setBookAndSave(Review review, Book book)
    {
        review.setBook(book);
        return reviewRepository.save(review);
    }

    private void isNotPresent(Review review0)
    {
        Optional<Review> optionalReview0 = reviewRepository.findById(review0.getId());
        assertFalse(optionalReview0.isPresent());
    }

    @Test
    public void remove()
    {
        Book saved = saveTestBook();
        Book book = finfById(saved.getId());

        Review review0 = setBookAndSave(createReview0(), book);
        Review review1 = setBookAndSave(createReview1(), book);
        Review review2 = setBookAndSave(createReview2(), book);

        bookRepository.deleteByIdWithReviews(book.getId());

        isNotPresent(review0);
        isNotPresent(review1);
        isNotPresent(review2);
    }

    @Test
    public void getById()
    {
        testBook.getAuthors().clear();
        testBook.getGenres().clear();
        testBook = bookRepository.saveWithAuthorsAndGenres(testBook);

        Book saved = bookRepository.findById(testBook.getId()).orElse(null);
        assertEquals(testBook , saved);
    }

    @Test
    public void getAll()
    {
        List<Book> expected = new ArrayList<>();
        expected.add(saveTestBook());

        Book book1 = createBook1();
        expected.add(bookRepository.saveWithAuthorsAndGenres(book1));

        List<Book> books = bookRepository.findAll();
        assertEquals(expected, books);
    }
}