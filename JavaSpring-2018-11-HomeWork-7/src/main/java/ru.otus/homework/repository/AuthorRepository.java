package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long>
{
    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);

    List<Author> findAll();
}
