package ru.otus.homework.services.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.homework.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.outside.utils.JdbcHelper.getStringOrNull;

public class GenreMapper implements RowMapper<Genre>
{
    public static Genre map(ResultSet resultSet)
    {
        Genre genre = new Genre();
        try {
            genre.setId(resultSet.getLong("genre_id"));
            genre.setGenre(getStringOrNull(resultSet, "genre"));

            return genre;
        }
        catch (SQLException e) {
            // TODO LOG
            return null;
        }
    }

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException
    {
        return map(resultSet);
    }
}
