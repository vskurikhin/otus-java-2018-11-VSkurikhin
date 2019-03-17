package ru.otus.homework.services;

import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.*;

import static ru.otus.outside.utils.StringHelper.stringOrNULL;

public class BooksTransformer extends DataTransformer<Book>
{
    public static String[] FIND_ALL_HEADER = {
        "id", "isbn", "title", "edition_number", "copyright", "author_first_name", "author_last_name",  "genre"
    };

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public List<String[]> unfold(Book book)
    {
        if (Objects.isNull(book)) {
            return Collections.emptyList();
        }

        List<String[]> result = new ArrayList<>();

        String id = stringOrNULL(book.getId());
        String isbn = stringOrNULL(book.getIsbn());
        String title = stringOrNULL(book.getTitle());
        String editionNumber = Integer.toString(book.getEditionNumber());
        String copyright = stringOrNULL(book.getCopyright());
        String authorFirstName = "NULL";
        String authorLastName = "NULL";
        String genre = "NULL";

        Iterator<Author> authorIterator = Objects.isNull(book.getAuthors())
                                        ? Collections.emptyIterator()
                                        : book.getAuthors().iterator();
        Iterator<Genre> genreIterator = Objects.isNull(book.getGenres())
                                      ? Collections.emptyIterator()
                                      : book.getGenres().iterator();

        do {
            if (authorIterator.hasNext()) {
                Author author = authorIterator.next();
                authorFirstName = author.getFirstName();
                authorLastName = author.getLastName();
            }
            else {
                authorFirstName = "NULL";
                authorLastName = "NULL";
            }
            if (genreIterator.hasNext()) {
                Genre g = genreIterator.next();
                genre = g.getGenre();
            }
            else {
                genre = "NULL";
            }
            result.add(new String[]{id, isbn, title, editionNumber, copyright, authorFirstName, authorLastName, genre});
        }
        while (authorIterator.hasNext() || genreIterator.hasNext());

        return result;
    }
}
