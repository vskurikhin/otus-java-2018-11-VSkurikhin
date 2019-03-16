package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.repository.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PublishersServiceImpl implements PublishersService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PublishersServiceImpl.class);

    public static String[] FIND_ALL_HEADER = {"genre_id", "publisher_name"};

    private PublisherRepository repository;

    public PublishersServiceImpl(PublisherRepository repository)
    {
        this.repository = repository;
    }

    static String[] unfoldPublisher(Publisher p)
    {
        if (null == p) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{ Long.toString(p.getId()), p.getPublisherName() };
    }

    @Override
    public String[] unfold(Publisher a)
    {
        return unfoldPublisher(a);
    }

    @Override
    public List<Publisher> findAll()
    {
        List<Publisher> result = repository.findAll();
        LOGGER.info("found {} authors", result.size());

        return result;
    }

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public Publisher findById(long id)
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
    public List<Publisher> findByPublisher(String value)
    {
        List<Publisher> result = repository.findByPublisherName(value);
        LOGGER.info("found {} authors", result.size());

        return result;
    }

    @Override
    public long insert(String publisher)
    {
        Publisher entity = new Publisher();
        entity.setPublisherName(publisher);
        repository.save(entity);

        return entity.getId();
    }

    @Override
    public long update(long id, String publisher)
    {
        Publisher entity = new Publisher();
        entity.setId(id);
        entity.setPublisherName(publisher);
        repository.save(entity);

        return entity.getId();
    }

    @Override
    public void delete(long id)
    {
        Publisher author = repository.findById(id).get();
        repository.delete(author);
    }
}
