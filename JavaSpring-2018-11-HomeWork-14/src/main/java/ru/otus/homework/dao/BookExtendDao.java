package ru.otus.homework.dao;

import ru.otus.homework.models.mongo.Book;

import java.math.BigInteger;

public interface BookExtendDao
{
    Book saveWithGenreAndAuthors(Book book);

    void deleteByIdWithReviews(BigInteger id);
}
