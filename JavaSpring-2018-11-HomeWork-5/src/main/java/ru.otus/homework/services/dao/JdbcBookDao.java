package ru.otus.homework.services.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.services.mappers.AuthorMapper;
import ru.otus.homework.services.mappers.BookMapper;

import java.util.*;

@Repository("bookDao")
public class JdbcBookDao implements BookDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBookDao.class);

    private NamedParameterJdbcTemplate jdbc;

    public JdbcBookDao(NamedParameterJdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> findAll()
    {
        return jdbc.query(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id",
            new BookMapper()
        );
    }

    @Override
    public Book findById(long id)
    {
        return jdbc.queryForObject(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " WHERE b.book_id = :id",
            new MapSqlParameterSource("id", id),
            new BookMapper()
        );
    }

    @Override
    public Book findByIsbn(String isbn)
    {
        return jdbc.queryForObject(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " WHERE b.isbn = :isbn",
            new MapSqlParameterSource("isbn", isbn),
            new BookMapper()
        );
    }

    @Override
    public List<Book> findByTitle(String title)
    {
        return jdbc.query(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " WHERE b.title LIKE :title",
            new MapSqlParameterSource("title", title),
            new BookMapper()
        );
    }

    @Override
    public List<Book> findAllBooksAndTheirAuthors()
    {
        return jdbc.query(
            "SELECT b.book_id, b.isbn, b.title, b.edition_number, b.copyright, b.publisher_id"
            + ", p.publisher_name, b.genre_id, g.genre, a.author_id, a.first_name, a.last_name"
            + " FROM book b"
            + " LEFT JOIN publisher p ON b.publisher_id = p.publisher_id"
            + " LEFT JOIN genre g ON b.genre_id = g.genre_id"
            + " LEFT OUTER JOIN author_isbn ai ON b.book_id = ai.book_id"
            + " LEFT OUTER JOIN author a ON ai.author_id = a.author_id"
            , rs -> {
                Map<Long, Book> map = new TreeMap<>();

                while (rs.next()) {
                    Book book = BookMapper.map(rs);
                    Author author = AuthorMapper.map(rs);
                    book = map.getOrDefault(book.getId(), book);
                    assert book != null;
                    book.getAuthors().add(author);
                    map.put(book.getId(), book);
                }

                // return map.values();
                return new ArrayList<>(map.values());
            }

        );
    }

    @Override
    public void insert(Book entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", entity.getId());
        namedParameters.addValue("isbn", entity.getIsbn());
        namedParameters.addValue("title", entity.getTitle());
        namedParameters.addValue("edition_number", entity.getEditionNumber());
        namedParameters.addValue("copyright", entity.getCopyright());
        namedParameters.addValue("publisher_id", entity.getPublisher().getId());
        namedParameters.addValue("genre_id", entity.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
            "INSERT INTO book (isbn, title, edition_number, copyright, publisher_id, genre_id)"
            + " VALUES (:isbn, :title, :edition_number, :copyright, :publisher_id, :genre_id)"
            , namedParameters, keyHolder
        );
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        LOGGER.info("Insert new book with id: {}", id);
        entity.setId(id);
    }

    @Override
    public void update(Book entity)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("isbn", entity.getIsbn());
        namedParameters.addValue("title", entity.getTitle());
        namedParameters.addValue("edition_number", entity.getEditionNumber());
        namedParameters.addValue("copyright", entity.getCopyright());
        namedParameters.addValue("publisher_id", entity.getPublisher().getId());
        namedParameters.addValue("genre_id", entity.getGenre().getId());
        namedParameters.addValue("id", entity.getId());
        int count = jdbc.update(
            "UPDATE book"
            + " SET isbn = :isbn, title = :title, edition_number = :edition_number, copyright = :copyright"
            + ", publisher_id = :publisher_id, genre_id = :genre_id"
            + " WHERE book_id = :id",
            namedParameters
        );
        LOGGER.info("Update {} record[s].", count);
    }

    @Override
    public void delete(long id)
    {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        int count = jdbc.update("DELETE FROM book WHERE book_id = :id", namedParameters);
        LOGGER.info("Delete {} record[s].", count);
    }
}
