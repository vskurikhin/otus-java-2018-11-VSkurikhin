package ru.otus.homework.services;

import ru.otus.homework.models.mongo.Genre;

public class GenreJpa2MongoTransformer
{
    public static Genre transform(ru.otus.homework.models.jpa.Genre genre)
    {
        return new Genre(genre.getId(), genre.getValue());
    }
}
