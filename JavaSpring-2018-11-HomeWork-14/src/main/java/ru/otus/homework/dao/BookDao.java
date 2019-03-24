package ru.otus.homework.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.homework.models.mongo.Book;

import java.math.BigInteger;
import java.util.Optional;

public interface BookDao extends MongoRepository<Book, BigInteger>
{
    @Query(value = "{'authors' :{'$ref' : 'author' , '$id' : ?0}}", count = true)
    Optional<Long> countByAuthorId(BigInteger id);
}
