package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.createReview0;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Class ReviewRepository")
class ReviewRepositoryTest
{
    @Autowired
    protected ReviewRepository repository;

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected GenreRepository genreRepository;

    @Autowired
    protected PublisherRepository publisherRepository;

    public void saveNewBook(Book book)
    {
        genreRepository.save(book.getGenre());
        publisherRepository.save(book.getPublisher());
        bookRepository.save(book);
    }

    public void saveNewReview(Review review)
    {
        saveNewBook(review.getBook());
        repository.save(review);
    }

    @DisplayName("persists new when save")
    @Test
    void testCreate() throws Exception
    {
        Review expected = createReview0();
        saveNewReview(expected);
        Review test = repository.findById(expected.getId()).orElse(null);
        assertNotNull(test);
        assertEquals(expected, test);
    }

    @DisplayName("merge detached object when save")
    @Test
    void testUpdate() throws Exception
    {
        Review expected = createReview0();
        saveNewReview(expected);
        expected.setReview(expected.getReview() + "_test");
        repository.save(expected);
        Review test = repository.findById(expected.getId()).orElse(null);
        assertNotNull(test);
        assertEquals(expected, test);
    }

    @Test
    void delete() throws Exception
    {
        Review expected = createReview0();
        saveNewReview(expected);
        List<Review> list = repository.findAll();
        assertFalse(list.isEmpty());
        repository.delete(expected);
        Review nullResult = repository.findById(expected.getId()).orElse(null);
        assertNull(nullResult);
    }
}