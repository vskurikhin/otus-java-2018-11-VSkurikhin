package ru.otus.homework.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.dao.ReviewDao;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.util.*;

@Service
public class DataJpaService implements DatabaseService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataJpaService.class);

    private AuthorDao authorDao;

    private BookDao bookDao;

    private GenreDao genreDao;

    private ReviewDao reviewDao;

    @Autowired
    public DataJpaService(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao, ReviewDao reviewDao)
    {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public Optional<Author> getAuthorById(long id)
    {
        return authorDao.findById(id);
    }

    @Override
    public Optional<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName)
    {
        return authorDao.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<Author> getDefaultAuthors()
    {
        LOGGER.error("Service is down!!! fallback route enabled...");

        return Collections.emptyList();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultAuthors", groupKey = "AuthorsService", commandKey = "findAll")
    public List<Author> getAllAuthors()
    {
        return authorDao.findAll();
    }

    @Override
    public List<Author> getAuthorsForBookId(long id)
    {
        Optional<Book> optionalBook = bookDao.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get().getAuthors();
        }

        return Collections.emptyList();
    }

    @Override
    public Author saveAuthor(Author author)
    {
        assert author != null;
        return authorDao.save(author);
    }

    @Override
    @Transactional
    public void removeAuthor(long id)
    {
        authorDao.deleteById(id);
    }

    @Override
    @Transactional
    public void addAuthorToBook(Author author, Book book)
    {
        author = authorDao.save(author);
        book.getAuthors().add(author);
        bookDao.save(book);
    }

    @Override
    public long countBooksByAuthorId(long id)
    {
        return bookDao.countByAuthorId(id);
    }

    @Override
    public Optional<Book> getBookById(long id)
    {
        return bookDao.findById(id);
    }

    public List<Book> getDefaultBooks()
    {
        LOGGER.error("Service is down!!! fallback route enabled...");

        return Collections.emptyList();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultBooks", groupKey = "BookService", commandKey = "findAll")
    public List<Book> getAllBooks()
    {
        return bookDao.findAll();
    }

    @Override
    public List<Book> getAllBooksByAuthorId(long id)
    {
        return bookDao.findAllByAuthorId(id);
    }

    private Optional<Author> findByFirstNameAndLastName(Author a)
    {
        return authorDao.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
    }

    @Override
    @Transactional
    public Book saveBook(Book book)
    {
        assert book != null;
        book.getAuthors().forEach(author -> {
            if (author.getId() == 0L) {
                findByFirstNameAndLastName(author).ifPresent(a -> author.setId(a.getId()));
            }
        });

        if ( ! Objects.isNull(book.getGenre()) && book.getId() == 0) {
            getGenreByValue(book.getGenre().getValue()).ifPresent(book::setGenre);
        }

        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book saveBookNewGenre(Book book, String genre)
    {
        book.setGenre(saveGenre(new Genre(0L, genre)));

        return bookDao.save(book);
    }

    @Override
    @Transactional
    public void removeBook(long id)
    {
        bookDao.deleteById(id);
    }


    @Override
    public Optional<Genre> getGenreById(long id)
    {
        return genreDao.findById(id);
    }

    @Override
    public Optional<Genre> getGenreByValue(String genre)
    {
        return genreDao.findByValue(genre);
    }

    public List<Genre> getDefaultGenres()
    {
        LOGGER.error("Service is down!!! fallback route enabled...");

        return Collections.emptyList();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultGenres", groupKey = "GenresService", commandKey = "findAll")
    public List<Genre> getAllGenres()
    {
        return genreDao.findAll();
    }

    @Override
    public Genre saveGenre(Genre genre)
    {
        assert genre != null;
        return genreDao.save(genre);
    }

    @Override
    @Transactional
    public void removeGenre(long id)
    {
        genreDao.deleteById(id);
    }


    @Override
    public long countReviewsByBookId(long id)
    {
        return reviewDao.countByBookId(id);
    }

    @Override
    public Optional<Review> getReviewById(long id)
    {
        return reviewDao.findById(id);
    }

    public List<Review> getDefaultReviews()
    {
        LOGGER.error("Service is down!!! fallback route enabled...");

        return Collections.emptyList();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultReviews", groupKey = "ReviewsService", commandKey = "findAll")
    public List<Review> getAllReviews()
    {
        return reviewDao.findAll();
    }

    @Override
    public List<Review> getAllReviewsForBookById(long id)
    {
        return reviewDao.findAllByBookId(id);
    }

    @Override
    public Review saveReview(Review review)
    {
        assert review != null;
        return reviewDao.save(review);
    }

    @Override
    @Transactional
    public void removeReview(long id)
    {
        reviewDao.deleteById(id);
    }
}
