package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;

import java.math.BigInteger;

public interface ReviewDao extends ReactiveMongoRepository<Review, BigInteger>
{
    Mono<Long> countByBook(@Param("book") Book book);

    Flux<Review> findAllByBook(@Param("book") Book book);
}
