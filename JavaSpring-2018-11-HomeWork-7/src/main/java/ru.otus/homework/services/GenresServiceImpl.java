package ru.otus.homework.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GenresServiceImpl implements GenresService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GenresServiceImpl.class);

    public static String[] FIND_ALL_HEADER = {"genre_id", "genre"};

    private GenreRepository repository;

    public GenresServiceImpl(GenreRepository repository)
    {
        this.repository = repository;
    }

    static String[] unfoldGenre(Genre g)
    {
        if (null == g) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{ Long.toString(g.getId()), g.getGenre() };
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
        LOGGER.info("found {} authors", result.size());

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
            return repository.findById(id).get();
        }
        catch (EmptyResultDataAccessException | NoSuchElementException e) {
            LOGGER.info("author with id: {} not found", id);
            return null;
        }
    }

    @Override
    public List<Genre> findByGenre(String value)
    {
        List<Genre> result = repository.findByGenre(value);
        LOGGER.info("found {} authors", result.size());

        return result;
    }

    @Override
    public long insert(String genre)
    {
        Genre entity = new Genre();
        entity.setGenre(genre);
        repository.save(entity);

        return entity.getId();
    }

    @Override
    public long update(long id, String genre)
    {
        Genre entity = new Genre();
        entity.setId(id);
        entity.setGenre(genre);
        repository.save(entity);

        return entity.getId();
    }

    @Override
    public void delete(long id)
    {
        Genre author = repository.findById(id).get();
        repository.delete(author);
    }
}
