package ru.otus.homework.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.math.BigInteger;

public interface DatabaseService
{
    Mono<Long> countBooksByAuthorId(BigInteger id);

    Mono<Author> getAuthorById(BigInteger id);

    Mono<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName);

    Flux<Author> getAllAuthors();

    Flux<Author> getAuthorsForBookId(BigInteger id);

    Mono<Author> saveAuthor(Author author);

    void removeAuthor(BigInteger id);


    Mono<Void> removeAuthorAsync(BigInteger id);

    Mono<Author> addAuthorToBook(Book book, Author author);

    Mono<Book> getBookById(BigInteger id);

    Flux<Book> getAllBooks();

    Mono<Book> saveBook(Book book);

    Mono<Book> saveBookNewGenre(Book book, String genre);

    Book saveBookWithAuthorsAndGenre(Book book);

    void removeBook(BigInteger id);


    Mono<Long> countReviewsByBookId(BigInteger id);

    Mono<Genre> getGenreById(BigInteger id);

    Mono<Genre> getGenreByValue(String genre);

    Flux<Genre> getAllGenres();

    Flux<Review> getAllReviewsForBookById(BigInteger id);

    Mono<Genre> saveGenre(Genre genre);

    void removeGenre(BigInteger id);

    Mono<Review> getReviewById(BigInteger id);

    Flux<Review> getAllReviews();

    Mono<Review> saveReview(Review review);

    // void removeReview(BigInteger id);

    Mono<Void> removeReview(BigInteger id);
}
