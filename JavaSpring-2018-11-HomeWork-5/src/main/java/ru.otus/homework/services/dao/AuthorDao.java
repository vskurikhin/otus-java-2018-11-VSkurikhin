package ru.otus.homework.services.dao;

import ru.otus.homework.models.Author;

import java.util.List;

public interface AuthorDao extends Dao<Author>
{
    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);
}
