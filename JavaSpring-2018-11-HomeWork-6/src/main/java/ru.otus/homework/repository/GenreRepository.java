package ru.otus.homework.repository;

import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenreRepository extends Repository<Genre>
{
    List<Genre> findByGenre(String genre);
}
