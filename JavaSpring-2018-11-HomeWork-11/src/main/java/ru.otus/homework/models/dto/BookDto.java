package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto
{
    private String id;

    private String isbn;

    private String title;

    private String editionNumber;

    private String copyright;

    private List<String> authors;

    private String genre;

    public BookDto(Book book)
    {
        assert book != null;

        id = book.getId() == null ? null : book.getId().toString();
        isbn = book.getIsbn();
        title = book.getTitle();
        editionNumber = Integer.toString(book.getEditionNumber());
        copyright = book.getCopyright();

        if ( ! Objects.isNull(book.getAuthors())) {
            authors = book.getAuthors()
                .stream()
                .map(author -> author.getFirstName() + " " + author.getLastName())
                .collect(Collectors.toList());
        }
        genre = book.getGenre().getValue();
    }

    public Book updateBook(Book book)
    {
        assert book != null;
        assert isbn != null;

        book.setId(id == null ? null : new BigInteger(id));
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(Integer.parseInt(editionNumber));
        book.setCopyright(copyright);

        if ( ! Objects.isNull(authors)) {
            book.setAuthors(authors.stream().map(s -> {
                String[] ss = s.split(" ");
                Author author = new Author();

                if (ss.length > 0) {
                    author.setFirstName(ss[0]);
                }
                if (ss.length > 1) {
                    author.setLastName(ss[1]);
                }

                return author;
            }).collect(Collectors.toList()));
        }

        return book;
    }

    public Book createBook()
    {
        Book book = new Book();

        return updateBook(book);
    }
}
