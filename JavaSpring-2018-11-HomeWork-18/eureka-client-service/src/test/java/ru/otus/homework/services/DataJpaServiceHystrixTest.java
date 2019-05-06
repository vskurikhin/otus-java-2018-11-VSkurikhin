package ru.otus.homework.services;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.Main;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.dao.ReviewDao;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static ru.otus.outside.utils.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@DisplayName("Integration tests for data layer with class DataJpaService")
class DataJpaServiceHystrixTest
{
    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private GenreDao genreDao;

    @MockBean
    private ReviewDao reviewDao;

    @Autowired
    private DatabaseService databaseService;

    public static HystrixCircuitBreaker getCircuitBreaker()
    {
        return HystrixCircuitBreaker.Factory.getInstance(getCommandKey());
    }

    private static HystrixCommandKey getCommandKey()
    {
        return HystrixCommandKey.Factory.asKey("findAll");
    }

    @BeforeEach
    public void setup()
    {
        Hystrix.reset();
        ConfigurationManager
            .getConfigInstance()
            .setProperty("hystrix.command.findAll.circuitBreaker.requestVolumeThreshold", 5000);
    }

    private void waitUntilCircuitBreakerOpens() throws InterruptedException
    {
        Thread.sleep(1000);
    }

    private void warmUpCircuitBreaker()
    {
        databaseService.getAllAuthors();
    }

    @Test
    void getAllAuthorsOnSuccess()
    {
        when(authorDao.findAll()).thenReturn(Collections.singletonList(createAuthor0()));

        databaseService.getAllAuthors();
        HystrixCircuitBreaker circuitBreaker = getCircuitBreaker();
        Assert.assertTrue(circuitBreaker.allowRequest());

        verify(authorDao, times(1)).findAll();
    }

    @Test
    void getAllAuthorsOnException() throws IOException, InterruptedException
    {
        when(authorDao.findAll()).thenThrow(new RuntimeException());

        List<Author> expected = Collections.emptyList();
        List<Author> test = databaseService.getAllAuthors();

        assertEquals(expected, test);


        verify(authorDao, times(1)).findAll();
    }

    @Test
    void getAllBooksOnSuccess()
    {
        when(bookDao.findAll()).thenReturn(Collections.singletonList(createBook0()));

        databaseService.getAllBooks();
        HystrixCircuitBreaker circuitBreaker = getCircuitBreaker();
        Assert.assertTrue(circuitBreaker.allowRequest());

        verify(bookDao, times(1)).findAll();
    }

    @Test
    void getAllBooksOnException() throws IOException, InterruptedException
    {
        when(bookDao.findAll()).thenThrow(new RuntimeException());

        List<Book> expected = Collections.emptyList();
        List<Book> test = databaseService.getAllBooks();
        assertEquals(expected, test);

        verify(bookDao, times(1)).findAll();
    }

    @Test
    void getAllGenresOnSuccess()
    {
        when(genreDao.findAll()).thenReturn(Collections.singletonList(createGenre0()));

        databaseService.getAllGenres();
        HystrixCircuitBreaker circuitBreaker = getCircuitBreaker();
        Assert.assertTrue(circuitBreaker.allowRequest());

        verify(genreDao, times(1)).findAll();
    }

    @Test
    void getAllGenresOnException() throws IOException, InterruptedException
    {
        when(genreDao.findAll()).thenThrow(new RuntimeException());

        List<Genre> expected = Collections.emptyList();
        List<Genre> test = databaseService.getAllGenres();
        assertEquals(expected, test);

        verify(genreDao, times(1)).findAll();
    }

    @Test
    void getAllReviewsOnSuccess()
    {
        when(reviewDao.findAll()).thenReturn(Collections.singletonList(createReview0()));

        databaseService.getAllReviews();
        HystrixCircuitBreaker circuitBreaker = getCircuitBreaker();
        Assert.assertTrue(circuitBreaker.allowRequest());

        verify(reviewDao, times(1)).findAll();
    }

    @Test
    void getAllReviewsOnException() throws IOException, InterruptedException
    {
        when(reviewDao.findAll()).thenThrow(new RuntimeException());

        List<Review> expected = Collections.emptyList();
        List<Review> test = databaseService.getAllReviews();
        assertEquals(expected, test);

        verify(reviewDao, times(1)).findAll();
    }
}
