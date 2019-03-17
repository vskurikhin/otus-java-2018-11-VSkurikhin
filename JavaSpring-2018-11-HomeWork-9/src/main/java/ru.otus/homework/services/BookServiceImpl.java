package ru.otus.homework.services;

import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService
{

    private void updateAuthor(Book book, String author)
    {
        if ( ! Objects.isNull(author)) {
            String authorAttrs[] = author.split(",");
            Author a = new Author();
            a.setFirstName(authorAttrs[0]);
            a.setLastName(authorAttrs[1]);
            book.getAuthors().add(a);
        }
    }

    @Override
    public Book create(String isbn, String title, int editionNumber, String copyright, String author, String genre)
    {
        Book book = new Book();
        assert isbn != null;
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(editionNumber);
        book.setCopyright(copyright);

        updateAuthor(book, author);
        updateGenre(book, genre);

        return book;
    }

    @Override
    public Book update(Book book, String isbn, String title, int editionNumber, String copyright)
    {
        if ( ! Objects.isNull(isbn)) {
            book.setIsbn(isbn);
        }
        if ( ! Objects.isNull(title)) {
            book.setTitle(title);
        }
        if ( editionNumber > -1) {
            book.setEditionNumber(editionNumber);
        }
        if ( ! Objects.isNull(copyright)) {
            book.setCopyright(copyright);
        }

        return book;
    }

    @Override
    public Book addAuthor(Book book, String author)
    {
        updateAuthor(book, author);

        return book;
    }

    @Override
    public Book addGenre(Book book, String genre)
    {
        updateGenre(book, genre);

        return book;
    }

    @Override
    public Book removeGenre(Book book, Genre genre)
    {
        List<Genre> genres = book.getGenres();
        while(genres.remove(genre)) { /* remove */ };
        book.setGenres(genres);

        return book;
    }

    private void updateGenre(Book book, String genre)
    {
        if (!Objects.isNull(genre)) {
            Genre g = new Genre();
            g.setGenre(genre);
            book.getGenres().add(g);
        }
    }
}
