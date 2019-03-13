package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.dao.JdbcAuthorDao;

import java.util.List;

@Service
public class AuthorsServiceImpl implements AuthorsService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorsServiceImpl.class);

    public static String[] FIND_ALL_HEADER = {"author_id", "first_name", "last_name"};

    private JdbcAuthorDao authorDao;

    public AuthorsServiceImpl(JdbcAuthorDao authorDao)
    {
        this.authorDao = authorDao;
    }

    static String[] unfoldAuthor(Author a)
    {
        if (null == a) {
            return new String[]{"NULL", "NULL", "NULL"};
        }

        return new String[]{Long.toString(a.getId()), a.getFirstName(), a.getLastName()};
    }

    @Override
    public String[] unfold(Author a)
    {
        return unfoldAuthor(a);
    }

    @Override
    public List<Author> findAll()
    {
        List<Author> result = authorDao.findAll();
        LOGGER.info("found {} authors", result);

        return result;
    }

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public Author findById(long id)
    {
        try {
            return authorDao.findById(id);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.info("author with id: {} not found", id);
            return null;
        }
    }

    @Override
    public List<Author> findByFirstName(String firstName)
    {
        List<Author> result = authorDao.findByFirstName(firstName);
        LOGGER.info("found {} authors", result);

        return result;
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        List<Author> result = authorDao.findByLastName(lastName);
        LOGGER.info("found {} authors", result);

        return result;
    }

    @Override
    public long insert(String firstName, String lastName)
    {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.insert(author);

        return author.getId();
    }

    @Override
    public long update(long id, String firstName, String lastName)
    {
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.update(author);

        return author.getId();
    }

    @Override
    public void delete(long id)
    {
        authorDao.delete(id);
    }
}
