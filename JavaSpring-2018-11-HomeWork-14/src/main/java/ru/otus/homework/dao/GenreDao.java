package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.mongo.Genre;

import java.math.BigInteger;
import java.util.Optional;

public interface GenreDao extends MongoRepository<Genre, BigInteger>
{
    Optional<Genre> findByValue(String value);
}
