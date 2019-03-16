package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService
{
    public static String[] FIND_ALL_HEADER = {"genre_id", "genre"};

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreServiceImpl.class);

    private GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository)
    {
        this.repository = repository;
    }

    static String[] unfoldGenre(Genre a)
    {
        if (null == a) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{Long.toString(a.getId()), a.getGenre()};
    }

    @Override
    public String[] unfold(Genre a)
    {
        return unfoldGenre(a);
    }

    @Override
    public List<Genre> findAll()
    {
        List<Genre> result = repository.findAll();
        LOGGER.info("found {} genres", result);

        return result;
    }

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public Genre findById(long id)
    {
        try {
            return repository.findById(id);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.info("genre with id: {} not found", id);
            return null;
        }
    }

    @Override
    public List<Genre> findByGenre(String genre)
    {
        List<Genre> result = repository.findByGenre(genre);
        LOGGER.info("found {} genres", result);

        return result;
    }

    @Override
    public long insert(String genre)
    {
        Genre g = new Genre();
        g.setGenre(genre);
        repository.save(g);

        return g.getId();
    }

    @Override
    public long update(long id, String genre)
    {
        Genre g = new Genre();
        g.setId(id);
        g.setGenre(genre);
        repository.save(g);

        return g.getId();
    }

    @Override
    public void delete(long id)
    {
        repository.delete(id);
    }
}
