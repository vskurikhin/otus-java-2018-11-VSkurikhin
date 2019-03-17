package ru.otus.homework.repository;

import ru.otus.homework.models.Book;

public interface BookExtendRepository
{
    Book saveWithAuthorsAndGenres(Book book);

    void deleteByIdWithReviews(String id);
}
