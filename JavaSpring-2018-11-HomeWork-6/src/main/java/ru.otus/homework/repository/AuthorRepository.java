package ru.otus.homework.repository;

import ru.otus.homework.models.Author;

import java.util.List;

public interface AuthorRepository extends Repository<Author>
{
    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);
}
