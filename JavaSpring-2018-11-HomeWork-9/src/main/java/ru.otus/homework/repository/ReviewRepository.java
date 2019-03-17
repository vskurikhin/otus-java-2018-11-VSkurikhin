package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Review;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String>
{
    List<Review> findAllByBookId(String bookId);

    void deleteByBookId(String bookId);
}
