package ru.otus.homework.services.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.outside.utils.JdbcHelper.getIntegerOrNull;
import static ru.otus.outside.utils.JdbcHelper.getStringOrNull;

public class BookMapper implements RowMapper<Book>
{
    public static Book map(ResultSet resultSet)
    {
        Book book = new Book();
        try {
            book.setId(resultSet.getLong("book_id"));
            book.setIsbn(getStringOrNull(resultSet, "isbn"));
            book.setTitle(getStringOrNull(resultSet, "title"));
            Integer editionNumber = getIntegerOrNull(resultSet, "edition_number");
            book.setEditionNumber(editionNumber == null ? 0 : editionNumber);
            book.setCopyright(getStringOrNull(resultSet, "copyright"));
            book.setPublisher(PublisherMapper.map(resultSet));
            book.setGenre(GenreMapper.map(resultSet));

            return book;
        }
        catch (SQLException e) {
            // TODO LOG
            return null;
        }
    }

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException
    {
        return map(resultSet);
    }
}
