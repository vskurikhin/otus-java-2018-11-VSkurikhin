package ru.otus.homework.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.repository.*;
import ru.otus.homework.repository.ReviewRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static ru.otus.outside.utils.StringHelper.stringOrNULL;

@Service
public class ReviewsServiceImpl implements ReviewsService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewsServiceImpl.class);

    public static String[] FIND_ALL_HEADER = {"review_id", "genre", "book_id"};

    public static String[] FIND_ALL_HEADER_REVIEW_BOOKS = {"review_id", "genre", "book_title"};

    private ReviewRepository repository;

    private BookRepository bookRepository;

    public ReviewsServiceImpl(ReviewRepository repository, BookRepository bookRepository)
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
            r.getReview() == null ? "NULL" : Long.toString(r.getBook().getId()),
        };
    }

    @Override
    public String[] unfold(Review a)
    {
        return unfoldReview(a);
    }

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public String[] getHeaderReviewAndBook()
    {
        return FIND_ALL_HEADER_REVIEW_BOOKS;
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
    public List<Review> findAllWithBook()
    {
        return repository.findAll();
    }


    @Override
    public List<Review> findByReview(String review)
    {
        return repository.findByReview(review);
    }

    @Override
    public List<Review> findAll()
    {
        List<Review> result = repository.findAll();
        LOGGER.info("found {} authors", result.size());

        return result;
    }

    @Override
    public Review findById(long id)
    {
        try {
            return repository.findById(id).get();
        }
        catch (EmptyResultDataAccessException | NoSuchElementException e) {
            LOGGER.info("author with id: {} not found", id);
            return null;
        }
    }

    @Override
    public long insert(String review, long bookId)
    {
        Review r = new Review();
        r.setReview(review);
        r.setBook(bookRepository.findById(bookId).get());
        repository.save(r);

        return r.getId();
    }

    public long update(long id, String review, long bookId)
    {
        Review r = new Review();
        r.setId(id);
        r.setReview(review);
        r.setBook(bookRepository.findById(bookId).get());
        repository.save(r);

        return r.getId();
    }

    @Override
    public void delete(long id)
    {
        Review author = repository.findById(id).get();
        repository.delete(author);
    }
}
