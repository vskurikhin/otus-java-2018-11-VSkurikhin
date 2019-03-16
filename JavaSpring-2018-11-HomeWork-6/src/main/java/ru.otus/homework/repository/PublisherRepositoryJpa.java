package ru.otus.homework.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Publisher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PublisherRepositoryJpa implements PublisherRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherRepositoryJpa.class);

    @PersistenceContext
    private EntityManager em;

    public PublisherRepositoryJpa() {}

    public PublisherRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Publisher> findAll()
    {
        return em.createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
    }

    @Override
    public Publisher findById(long id)
    {
        return em.find(Publisher.class, id);
    }

    @Override
    public List<Publisher> findByPublisher(String publisher)
    {
        return em
            .createQuery("SELECT p FROM Publisher p WHERE p.publisherName LIKE :name", Publisher.class)
            .setParameter("name", publisher)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Publisher entity)
    {
        if (entity.getId() == 0) {
            em.persist(entity);
            em.flush();
        }
        else {
            em.merge(entity);
        }
        LOGGER.info("Save publisher id: {}", entity.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id)
    {
        Publisher mergedPublisher = em.merge(findById(id));
        em.remove(mergedPublisher);
        LOGGER.info("Delete publisher id: {}", mergedPublisher.getId());
    }
}
