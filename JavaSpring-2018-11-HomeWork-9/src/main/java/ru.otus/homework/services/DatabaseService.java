package ru.otus.homework.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.util.List;
import java.util.Optional;

public interface DatabaseService
{
    Optional<Author> getAuthorById(long id);

    Optional<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName);

    List<Author> getAllAuthors();

    Author saveAuthor(Author author);

    void removeAuthor(long id);

    void addAuthorToBook(Author author, Book book);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    Book saveBook(Book book);

    Book saveBookNewGenre(Book book, String genre);

    void removeBook(long id);

    Optional<Genre> getGenreById(long id);

    Optional<Genre> getGenreByValue(String genre);

    List<Genre> getAllGenres();

    Genre saveGenre(Genre genre);

    void removeGenre(long id);

    Optional<Review> getReviewById(long id);

    List<Review> getAllReviews();

    List<Review> getAllReviewsForBookById(long id);

    Review saveReview(Review review);

    void removeReview(long id);
}
