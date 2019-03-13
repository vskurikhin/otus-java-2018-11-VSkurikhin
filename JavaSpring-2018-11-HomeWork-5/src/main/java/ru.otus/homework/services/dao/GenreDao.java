package ru.otus.homework.services.dao;

import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenreDao extends Dao<Genre>
{
    List<Genre> findByGenre(String genre);
}

