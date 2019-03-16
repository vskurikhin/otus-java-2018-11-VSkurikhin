package ru.otus.homework.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;
import ru.otus.homework.repository.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static ru.otus.outside.utils.StringHelper.stringOrNULL;

@Service
public class BooksServiceImpl implements BooksService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BooksServiceImpl.class);

    public static String[] FIND_ALL_HEADER = {
        "book_id", "isbn", "title", "edition_number", "copyright", "publisher_id", "genre_id"
    };

    public static String[] FIND_ALL_HEADER_BOOKS_AUTHORS = {
        "book_id", "isbn", "title", "edition_number", "copyright",
        "publisher_name", "genre", "first_name", "last_name"
    };

    private BookRepository repository;

    private PublisherRepository publisherRepository;

    private GenreRepository genreRepository;

    public BooksServiceImpl(BookRepository repository, PublisherRepository publisherRepository,
                            GenreRepository genreRepository)
    {
        this.repository = repository;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
    }


    private String[] unfoldBook(Book b)
    {
        if (null == b) {
            return new String[]{"NULL", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"};
        }

        return new String[]{
            Long.toString(b.getId()),
            b.getIsbn(),
            stringOrNULL(b.getTitle()),
            Integer.toString(b.getEditionNumber()),
            stringOrNULL(b.getCopyright()),
            b.getPublisher() == null ? "NULL" : stringOrNULL(b.getPublisher().getPublisherName()),
            b.getGenre() == null ? "NULL" : stringOrNULL(b.getGenre().getGenre()),
        };
    }

    @Override
    public Book findByIsbn(String isbn)
    {
        return repository.findByIsbn(isbn);
    }

    @Override
    public List<Book> findByTitle(String title)
    {
        return repository.findByTitle(title);
    }

    @Override
    public List<Book> findAllBooksAndTheirAuthors()
    {
        return repository.findAll();
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
        LOGGER.info("found {} authors", result.size());

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
            return repository.findById(id).get();
        }
        catch (EmptyResultDataAccessException | NoSuchElementException e) {
            LOGGER.info("author with id: {} not found", id);
            return null;
        }
    }

    @Override
    public long insert(String isbn, String title, int editionNumber, String copyright, long publisherId, long genreId)
    {
        Book entity = new Book();
        entity.setId(0L);
        entity.setIsbn(isbn);
        entity.setTitle(title);
        entity.setEditionNumber(editionNumber);
        entity.setCopyright(copyright);
        entity.setPublisher(publisherRepository.findById(publisherId).get());
        entity.setGenre(genreRepository.findById(genreId).get());
        repository.save(entity);

        return entity.getId();
    }

    public long update(long id, String isbn, String title, int editionNumber, String copyright, long publisherId, long genreId)
    {
        Book entity = new Book();
        entity.setId(id);
        entity.setIsbn(isbn);
        entity.setTitle(title);
        entity.setEditionNumber(editionNumber);
        entity.setCopyright(copyright);
        entity.setPublisher(publisherRepository.findById(publisherId).get());
        entity.setGenre(genreRepository.findById(genreId).get());
        repository.save(entity);

        return entity.getId();
    }

    @Override
    public void delete(long id)
    {
        Book author = repository.findById(id).get();
        repository.delete(author);
    }
}
