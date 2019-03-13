package ru.otus.homework.services.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.services.mappers.PublisherMapper;

import java.util.List;
import java.util.Objects;

@Repository("publisherDao")
public class JdbcPublisherDao implements PublisherDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPublisherDao.class);

    // public static String[] FIND_ALL_HEADER = {"publisher_id", "publisher_name"};

    private NamedParameterJdbcTemplate jdbc;

    public JdbcPublisherDao(NamedParameterJdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    @Override
    public List<Publisher> findAll()
    {
        return jdbc.query("SELECT publisher_id, publisher_name FROM publisher", new PublisherMapper());
    }

    @Override
    public Publisher findById(long id)
    {
        return jdbc.queryForObject(
            "SELECT publisher_id, publisher_name FROM publisher WHERE publisher_id = :id",
            new MapSqlParameterSource("id", id),
            new PublisherMapper()
        );
    }

    @Override
    public List<Publisher> findByName(String publisher)
    {
        return jdbc.query(
            "SELECT publisher_id, publisher_name FROM publisher WHERE publisher_name = :publisher",
            new MapSqlParameterSource("publisher", publisher),
            new PublisherMapper()
        );
    }

    @Override
    public void insert(Publisher entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("publisher", entity.getPublisherName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("INSERT INTO publisher (publisher_name) VALUES (:publisher)", namedParameters, keyHolder);
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Publisher entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("publisher", entity.getPublisherName());
        namedParameters.addValue("id", entity.getId());
        jdbc.update("UPDATE publisher SET publisher_name = :publisher WHERE publisher_id = :id", namedParameters);
    }

    @Override
    public void delete(long id)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        jdbc.update("DELETE FROM publisher WHERE publisher_id = :id", namedParameters);
    }
}
