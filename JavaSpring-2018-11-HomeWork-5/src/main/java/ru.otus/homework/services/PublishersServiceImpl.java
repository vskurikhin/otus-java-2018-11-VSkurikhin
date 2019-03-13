package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.services.dao.JdbcPublisherDao;

import java.util.List;

@Service
public class PublishersServiceImpl implements PublishersService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreServiceImpl.class);

    public static String[] FIND_ALL_HEADER = {"publisher_id", "publisher_name"};

    private JdbcPublisherDao publisherDao;

    public PublishersServiceImpl(JdbcPublisherDao publisherDao)
    {
        this.publisherDao = publisherDao;
    }

    static String[] unfoldPublisher(Publisher p)
    {
        if (null == p) {
            return new String[]{"NULL", "NULL"};
        }

        return new String[]{Long.toString(p.getId()), p.getPublisherName()};
    }

    @Override
    public String[] unfold(Publisher a)
    {
        return unfoldPublisher(a);
    }

    @Override
    public List<Publisher> findAll()
    {
        List<Publisher> result = publisherDao.findAll();
        LOGGER.info("found {} publishers", result);

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
            return publisherDao.findById(id);
        }
        catch (EmptyResultDataAccessException e) {
            LOGGER.info("publisher with id: {} not found", id);
            return null;
        }
    }

    @Override
    public List<Publisher> findByPublisherName(String publisherName)
    {
        List<Publisher> result = publisherDao.findByName(publisherName);
        LOGGER.info("found {} publishers", result);

        return result;
    }

    @Override
    public long insert(String publisherName)
    {
        Publisher publisher = new Publisher();
        publisher.setPublisherName(publisherName);
        publisherDao.insert(publisher);

        return publisher.getId();
    }

    @Override
    public long update(long id, String publisherName)
    {
        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setPublisherName(publisherName);
        publisherDao.update(publisher);

        return publisher.getId();
    }

    @Override
    public void delete(long id)
    {
        publisherDao.delete(id);
    }
}
