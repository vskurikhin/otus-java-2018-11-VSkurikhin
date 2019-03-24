package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.mongo.Author;

import java.math.BigInteger;
import java.util.Optional;

public interface AuthorDao extends MongoRepository<Author, BigInteger>
{
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
