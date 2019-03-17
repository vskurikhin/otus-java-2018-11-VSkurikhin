package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookExtendRepository
{
    List<Book> findAll();
}
