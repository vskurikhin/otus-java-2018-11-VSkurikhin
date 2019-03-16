package ru.otus.homework.services;

import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenresService extends FindService<Genre>
{
    List<Genre> findByGenre(String firstName);

    long insert(String genre);

    long update(long id, String genre);

    void delete(long id);
}
