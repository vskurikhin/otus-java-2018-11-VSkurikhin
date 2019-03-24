package ru.otus.homework.services;

import ru.otus.homework.models.mongo.Author;
import ru.otus.homework.models.mongo.Book;
import ru.otus.homework.models.mongo.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class BookJpa2MongoTransformer
{
    public static Book transform(ru.otus.homework.models.jpa.Book book)
    {
        Genre genre = GenreJpa2MongoTransformer.transform(book.getGenre());
        List<Author> authors = book.getAuthors()
            .stream()
            .map(AuthorJpa2MongoTransformer::transform)
            .collect(Collectors.toList());

        return new Book(
            book.getId(), book.getIsbn(), book.getTitle(), book.getEditionNumber(), book.getCopyright(),
            authors, genre
        );
    }
    /* None */
}
