package ru.otus.homework.services.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework.models.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.outside.utils.JdbcHelper.getStringOrNull;

public class PublisherMapper implements RowMapper<Publisher>
{
    public static Publisher map(ResultSet resultSet)
    {
        Publisher publisher = new Publisher();
        try {
            publisher.setId(resultSet.getLong("publisher_id"));
            publisher.setPublisherName(getStringOrNull(resultSet, "publisher_name"));

            return publisher;
        }
        catch (SQLException e) {
            // TODO LOG
            return null;
        }
    }

    @Override
    public Publisher mapRow(ResultSet resultSet, int i) throws SQLException
    {
        return map(resultSet);
    }
}
