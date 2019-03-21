package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Genre;

import java.math.BigInteger;

public interface GenreDao extends ReactiveMongoRepository<Genre, BigInteger>
{
    Mono<Genre> findByValue(String value);
}
