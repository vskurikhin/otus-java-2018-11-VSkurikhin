package ru.otus.homework.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Genre;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface GenreDao extends CrudRepository<Genre, Long>
{
    Optional<Genre> findByValue(String value);

    List<Genre> findAll();

    @Modifying
    @Transactional
    void deleteById(long id);
}
