package ru.otus.homework.services.dao;

import ru.otus.homework.models.Book;

import java.util.List;

public interface BookDao extends Dao<Book>
{
    Book findByIsbn(String isbn);

    List<Book> findByTitle(String title);

    List<Book> findAllBooksAndTheirAuthors();
}
