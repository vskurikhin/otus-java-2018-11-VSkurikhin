package ru.otus.homework.services;

import ru.otus.homework.models.mongo.Author;

public class AuthorJpa2MongoTransformer
{
    public static Author transform(ru.otus.homework.models.jpa.Author author)
    {
        return new Author(author.getId(), author.getFirstName(), author.getLastName());
    }
}
