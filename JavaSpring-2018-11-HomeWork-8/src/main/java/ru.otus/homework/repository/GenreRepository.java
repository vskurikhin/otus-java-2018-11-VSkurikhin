package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String>
{
    List<Genre> findAllByGenre(String genre);
}
