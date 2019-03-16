package ru.otus.homework.services;

import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

public interface BookService
{
    Book create(String isbn, String title, int editionNumber, String copyright, String author, String genre);

    Book update(Book book, String isbn, String title, int editionNumber, String copyright);

    Book addAuthor(Book book, String author);

    Book addGenre(Book book, String genre);

    Book removeGenre(Book book, Genre genre);
}
