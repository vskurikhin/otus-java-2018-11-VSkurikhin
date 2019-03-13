package ru.otus.homework.services.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.mappers.AuthorMapper;

import java.util.List;
import java.util.Objects;

@Repository("authorDao")
public class JdbcAuthorDao implements AuthorDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcAuthorDao.class);

    private NamedParameterJdbcTemplate jdbc;

    public JdbcAuthorDao(NamedParameterJdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    @Override
    public List<Author> findAll()
    {
        return jdbc.query("SELECT author_id, first_name, last_name FROM author", new AuthorMapper());
    }

    @Override
    public Author findById(long id)
    {
        return jdbc.queryForObject(
            "SELECT author_id, first_name, last_name FROM author WHERE author_id = :id",
            new MapSqlParameterSource("id", id), new AuthorMapper()
        );
    }

    @Override
    public List<Author> findByFirstName(String firstName)
    {
        return jdbc.query(
            "SELECT author_id, first_name, last_name FROM author WHERE first_name LIKE :name",
            new MapSqlParameterSource("name", firstName), new AuthorMapper()
        );
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        return jdbc.query(
            "SELECT author_id, first_name, last_name FROM author WHERE last_name LIKE :name",
            new MapSqlParameterSource("name", lastName), new AuthorMapper()
        );
    }

    @Override
    public void insert(Author entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("first_name", entity.getFirstName());
        namedParameters.addValue("last_name", entity.getLastName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
            "INSERT INTO author (first_name, last_name) VALUES (:first_name, :last_name)",
            namedParameters, keyHolder
        );
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        LOGGER.info("Insert new author with id: {}", id);
        entity.setId(id);
    }

    @Override
    public void update(Author entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("first_name", entity.getFirstName());
        namedParameters.addValue("last_name", entity.getLastName());
        namedParameters.addValue("id", entity.getId());
        int count = jdbc.update(
            "UPDATE author SET first_name = :first_name, last_name = :last_name WHERE author_id = :id",
            namedParameters
        );
        LOGGER.info("Update {} record[s].", count);
    }

    @Override
    public void delete(long id)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        int count = jdbc.update("DELETE FROM author WHERE author_id = :id", namedParameters);
        LOGGER.info("Delete {} record[s].", count);
    }
}
