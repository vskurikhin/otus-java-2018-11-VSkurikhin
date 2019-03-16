package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.repository.BookRepository;

import java.util.List;

import static ru.otus.outside.utils.StringHelper.stringOrNULL;

@Service
public class BooksServiceImpl implements BooksService
{
    public static String[] FIND_ALL_HEADER = {
        "book_id", "isbn", "title", "edition_number", "copyright", "publisher_id", "genre_id"
    };

    public static String[] FIND_ALL_HEADER_BOOKS_AUTHORS = {
        "book_id", "isbn", "title", "edition_number", "copyright",
        "publisher_name", "genre", "first_name", "last_name"
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(BooksServiceImpl.class);

    private BookRepository repository;

    public BooksServiceImpl(BookRepository repository)
    {
        this.repository = repository;
    }

    private String[] unfoldBook(Book b)
    {
        if (null == b) {
            return new String[]{"NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"};
        }

        return new String[]{
            Long.toString(b.getId()),
            b.getIsbn(),
            b.getTitle() == null ? "NULL" : b.getTitle(),
            Integer.toString(b.getEditionNumber()),
            stringOrNULL(b.getCopyright()),
            b.getPublisher() == null ? "NULL" : Long.toString(b.getPublisher().getId()),
            b.getGenre() == null ? "NULL" : Long.toString(b.getGenre().getId()),
        };
    }

    @Override
    public String[] mergeBookAndAuthor(Book b, Author a)
    {
        if (null == b) {
            LOGGER.error("Book is null.");
            throw new RuntimeException();
        }

        if (null == a) {
            return new String[]{
                Long.toString(b.getId()),
                b.getIsbn(),
                stringOrNULL(b.getTitle()),
                Integer.toString(b.getEditionNumber()),
                stringOrNULL(b.getCopyright()),
                b.getPublisher() == null ? "NULL" : stringOrNULL(b.getPublisher().getPublisherName()),
                b.getGenre() == null ? "NULL" : stringOrNULL(b.getGenre().getGenre()),
                "NULL", "NULL"
            };
        }

        return new String[]{
            Long.toString(b.getId()),
            b.getIsbn(),
            stringOrNULL(b.getTitle()),
            Integer.toString(b.getEditionNumber()),
            stringOrNULL(b.getCopyright()),
            b.getPublisher() == null ? "NULL" : stringOrNULL(b.getPublisher().getPublisherName()),
            b.getGenre() == null ? "NULL" : stringOrNULL(b.getGenre().getGenre()),
            stringOrNULL(a.getFirstName()),
            stringOrNULL(a.getLastName()),
        };
    }

    @Override
    public String[] getHeaderBookAndAuthor()
    {
        return FIND_ALL_HEADER_BOOKS_AUTHORS;
    }

    @Override
    public String[] unfold(Book a)
    {
        return unfoldBook(a);
    }

    @Override
    public List<Book> findAll()
    {
        List<Book> result = repository.findAll();
        // LOGGER.info("found {} books", result);

        return result;
    }

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public Book findById(long id)
    {
        try {
            return repository.findById(id);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.info("book with id: {} not found", id);
            return null;
        }
    }

    @Override
    public Book findByIsbn(String isbn)
    {
        try {
            return repository.findByIsbn(isbn);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.info("book with ISBN: {} not found", isbn);
            return null;
        }
    }

    @Override
    public List<Book> findByTitle(String title)
    {
        List<Book> result = repository.findByTitle(title);
        LOGGER.info("found {} books", result);

        return result;
    }

    @Override
    public List<Book> findAllBooksAndTheirAuthors()
    {
        List<Book> result = repository.findAllBooksAndTheirAuthors();
        LOGGER.info("found {} books", result);

        return result;
    }

    @Override
    public long insert(String isbn, String title, int edition_number, String copyright, long publisherId, long genreId)
    {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(edition_number);
        book.setCopyright(copyright);
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        book.setPublisher(publisher);
        Genre genre = new Genre();
        genre.setId(genreId);
        book.setGenre(genre);
        repository.save(book);

        return book.getId();
    }

    @Override
    public long update(long id, String isbn, String title, int editionNum, String copyright, long pblshrId, long genreId)
    {
        Book book = new Book();
        book.setId(id);
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setEditionNumber(editionNum);
        book.setCopyright(copyright);
        Publisher publisher = new Publisher();
        publisher.setId(pblshrId);
        book.setPublisher(publisher);
        Genre genre = new Genre();
        genre.setId(genreId);
        book.setGenre(genre);
        repository.save(book);

        return book.getId();
    }

    @Override
    public void delete(long id)
    {
        repository.delete(id);
    }
}
