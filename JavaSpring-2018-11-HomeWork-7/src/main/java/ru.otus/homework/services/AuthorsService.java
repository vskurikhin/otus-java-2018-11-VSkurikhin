package ru.otus.homework.services;

import ru.otus.homework.models.Author;

import java.util.List;

public interface AuthorsService extends FindService<Author>
{
    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);

    long insert(String firstName, String lastName);

    long update(long id, String firstName, String lastName);

    void delete(long id);
}
