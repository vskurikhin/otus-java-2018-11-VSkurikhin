package ru.otus.outside.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcHelper
{
    public static Integer getIntegerOrNull(ResultSet resultSet, String fieldName)
    {
        try {
            return resultSet.getInt(fieldName);
        } catch (SQLException e) {
            return null;
        }
    }

    public static Long getLongOrNull(ResultSet resultSet, String fieldName)
    {
        try {
            return resultSet.getLong(fieldName);
        } catch (SQLException e) {
            return null;
        }
    }

    public static String getStringOrNull(ResultSet resultSet, String fieldName)
    {
        try {
            return resultSet.getString(fieldName);
        } catch (SQLException e) {
            return null;
        }
    }

    public static <T> T getObjectOrNull(ResultSet resultSet, String fieldName)
    {
        try {
            //noinspection unchecked
            return (T) resultSet.getObject(fieldName);
        } catch (SQLException e) {
            return null;
        }
    }
}
