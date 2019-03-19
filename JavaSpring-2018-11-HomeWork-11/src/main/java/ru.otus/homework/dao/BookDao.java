package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;

import java.math.BigInteger;

public interface BookDao extends ReactiveMongoRepository<Book, BigInteger>
{
    @Query(value = "{'authors' :{'$ref' : 'author' , '$id' : ?0}}", count = true)
    Mono<Long> countByAuthorId(BigInteger id);
}
