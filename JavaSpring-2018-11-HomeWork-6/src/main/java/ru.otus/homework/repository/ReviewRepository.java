package ru.otus.homework.repository;

import ru.otus.homework.models.Review;

import java.util.List;

public interface ReviewRepository extends Repository<Review>
{
    List<Review> findAllWithBook();

    List<Review> findByReview(String value);
}
