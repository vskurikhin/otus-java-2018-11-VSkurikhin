package ru.otus.homework.services;

import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.util.List;
import java.util.Optional;

public interface DataStorageService
{
    Optional<Book> getBookById(String id);

    List<Book> getAllBooks();

    Book saveBook(Book book);

    void removeBook(String id);


    Optional<Review> getBookReviewById(String id);

    List<Review> getAllBookReviewsByBookId(String bookId);

    Review saveBookWithReviews(Review review);

    void removeBookReview(String id);

    Optional<Genre> getGenre(String genre);
}
