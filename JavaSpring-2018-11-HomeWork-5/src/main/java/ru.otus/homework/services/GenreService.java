package ru.otus.homework.services;

import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenreService extends FindService<Genre>
{
    List<Genre> findByGenre(String genre);

    long insert(String genre);

    long update(long id, String genre);

    void delete(long id);
}
