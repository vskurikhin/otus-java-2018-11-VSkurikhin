package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.models.mongo.Book;
import ru.otus.homework.models.mongo.Review;

import java.math.BigInteger;
import java.util.Optional;

public interface ReviewDao extends MongoRepository<Review, BigInteger>
{
    Optional<Long> countByBook(@Param("book") Book book);

    Optional<Review> findAllByBook(@Param("book") Book book);
}
