package ru.otus.homework.services.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.mappers.GenreMapper;

import java.util.*;

import static ru.otus.outside.utils.JdbcHelper.getStringOrNull;

@Repository("genreDao")
public class JdbcGenreDao implements GenreDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcGenreDao.class);

    // public static String[] FIND_ALL_HEADER = {"genre_id", "genre"};

    private NamedParameterJdbcTemplate jdbc;

    public JdbcGenreDao(NamedParameterJdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> findByGenre(String genre)
    {
        return jdbc.query(
            "SELECT genre_id, genre FROM genre WHERE genre = :genre",
            new MapSqlParameterSource("genre", genre),
            new GenreMapper()
        );
    }

    @Override
    public List<Genre> findAll()
    {
        return jdbc.query("SELECT genre_id, genre FROM genre", new GenreMapper());
    }

    @Override
    public Genre findById(long id)
    {
        return jdbc.queryForObject(
            "SELECT genre_id, genre FROM genre WHERE genre_id = :id",
            new MapSqlParameterSource("id", id),
            new GenreMapper()
        );
    }

    @Override
    public void insert(Genre entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genre", entity.getGenre());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO genre (genre) VALUES (:genre)", namedParameters, keyHolder);
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        LOGGER.info("Insert new genre with id: {}", id);
        entity.setId(id);
    }

    @Override
    public void update(Genre entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genre", entity.getGenre());
        namedParameters.addValue("genre_id", entity.getId());
        jdbc.update("UPDATE genre SET genre = :genre WHERE genre_id = :genre_id", namedParameters);
    }

    @Override
    public void delete(long id)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genre_id", id);
        jdbc.update("DELETE FROM genre WHERE genre_id = :genre_id", namedParameters);
    }
}
