package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.ReviewRepository;

import java.util.List;

import static ru.otus.outside.utils.StringHelper.stringOrNULL;

@Service
public class ReviewServiceImpl implements ReviewService
{
    public static String[] FIND_ALL_HEADER = {"review_id", "genre", "book_id"};

    public static String[] FIND_ALL_HEADER_REVIEW_BOOKS = {"review_id", "genre", "book_title"};

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private BookRepository bookRepository;

    private ReviewRepository repository;

    public ReviewServiceImpl(ReviewRepository repository, BookRepository bookRepository)
    {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    static String[] unfoldReview(Review r)
    {
        if (null == r) {
            return new String[]{"NULL", "NULL", "NULL"};
        }

        return new String[]{
            Long.toString(r.getId()),
            r.getReview(),
            r.getBook() == null ? "NULL" : Long.toString(r.getBook().getId()),
        };
    }

    @Override
    public String[] unfold(Review a)
    {
        return unfoldReview(a);
    }

    @Override
    public String[] mergeReviewAndBook(Review r, Book b)
    {
        if (null == r) {
            LOGGER.error("Review is null.");
            throw new RuntimeException();
        }

        return new String[]{
            Long.toString(r.getId()),
            stringOrNULL(r.getReview()),
            stringOrNULL(b.getTitle()),
        };
    }

    @Override
    public List<Review> findAll()
    {
        List<Review> result = repository.findAll();
        LOGGER.info("found {} reviews", result.size());

        return result;
    }

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public List<Review> findAllWithBook()
    {
        List<Review> result = repository.findAllWithBook();
        LOGGER.info("found {} reviews", result.size());

        return result;
    }

    @Override
    public String[] getHeaderReviewAndBook()
    {
        return FIND_ALL_HEADER_REVIEW_BOOKS;
    }

    @Override
    public Review findById(long id)
    {
        try {
            return repository.findById(id);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.info("genre with id: {} not found", id);
            return null;
        }
    }

    @Override
    public List<Review> findByReview(String review)
    {
        List<Review> result = repository.findByReview(review);
        LOGGER.info("found {} genres", result);

        return result;
    }

    @Override
    public long insert(String review, long bookId)
    {
        Review r = new Review();
        r.setReview(review);
        r.setBook(bookRepository.findById(bookId));
        repository.save(r);

        return r.getId();
    }

    @Override
    public long update(long id, String review, long bookId)
    {
        Review r = new Review();
        r.setId(id);
        r.setReview(review);
        r.setBook(bookRepository.findById(bookId));
        repository.save(r);

        return r.getId();
    }

    @Override
    public void delete(long id)
    {
        repository.delete(id);
    }
}
