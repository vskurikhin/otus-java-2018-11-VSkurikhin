package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long>
{
    List<Genre> findByGenre(String genre);

    List<Genre> findAll();
}
