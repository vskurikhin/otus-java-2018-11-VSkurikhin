package ru.otus.homework.services.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.outside.utils.JdbcHelper.getStringOrNull;

public class AuthorMapper implements RowMapper<Author>
{
    public static Author map(ResultSet resultSet)
    {
        Author author = new Author();
        try {
            author.setId(resultSet.getLong("author_id"));
            author.setFirstName(getStringOrNull(resultSet, "first_name"));
            author.setLastName(getStringOrNull(resultSet, "last_name"));

            return author;
        }
        catch (SQLException e) {
            // TODO LOG
            return null;
        }
    }

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException
    {
        return map(resultSet);
    }
}
