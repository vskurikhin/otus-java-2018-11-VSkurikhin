package ru.otus.homework.services;

import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;

import java.util.List;

public interface ReviewsService extends FindService<Review>
{
    String[] mergeReviewAndBook(Review r, Book b);

    List<Review> findAllWithBook();

    String[] getHeaderReviewAndBook();

    List<Review> findByReview(String review);

    long insert(String genre, long BookId);

    long update(long id, String genre, long bookId);

    void delete(long id);
}
