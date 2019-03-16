package ru.otus.homework.repository;

import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;

import java.util.List;
import java.util.Map;

public interface BookRepository extends Repository<Book>
{
    Book findByIsbn(String isbn);

    List<Book> findByTitle(String title);

    List<Book> findAllBooksAndTheirAuthors();
}
