package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Author;

import java.math.BigInteger;

public interface AuthorDao extends ReactiveMongoRepository<Author, BigInteger>
{
    Mono<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
