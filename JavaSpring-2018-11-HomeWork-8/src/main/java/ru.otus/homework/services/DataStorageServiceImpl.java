package ru.otus.homework.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;
import ru.otus.homework.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DataStorageServiceImpl implements DataStorageService
{
    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final ReviewRepository reviewRepository;

    public DataStorageServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository,
                                  GenreRepository genreRepository, ReviewRepository reviewRepository)
    {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book book)
    {
        return bookRepository.saveWithAuthorsAndGenres(book);
    }

    @Override
    @Transactional
    public void removeBook(String id) {
        bookRepository.deleteByIdWithReviews(id);
    }

    @Override
    public Optional<Review> getBookReviewById(String id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> getAllBookReviewsByBookId(String bookId) {
        return reviewRepository.findAllByBookId(bookId);
    }

    @Override
    public Review saveBookWithReviews(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void removeBookReview(String id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Optional<Genre> getGenre(String genre)
    {
        List<Genre> genres = genreRepository.findAllByGenre(genre);
        return genre.isEmpty() ? Optional.empty() : Optional.of(genres.get(0));
    }
}
