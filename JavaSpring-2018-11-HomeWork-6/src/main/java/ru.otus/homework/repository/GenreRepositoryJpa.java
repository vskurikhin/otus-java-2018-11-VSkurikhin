package ru.otus.homework.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class GenreRepositoryJpa implements GenreRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreRepositoryJpa.class);

    @PersistenceContext
    private EntityManager em;

    public GenreRepositoryJpa() {}

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Genre> findAll()
    {
        return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
    }

    @Override
    public Genre findById(long id)
    {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> findByGenre(String genre)
    {
        return em
            .createQuery("SELECT g FROM Genre g WHERE g.genre LIKE :name", Genre.class)
            .setParameter("name", genre)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Genre entity)
    {
        if (entity.getId() == 0) {
            em.persist(entity);
            em.flush();
        }
        else {
            em.merge(entity);
        }
        LOGGER.info("Save genre id: {}", entity.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id)
    {
        Genre mergedGenre = em.merge(findById(id));
        em.remove(mergedGenre);
        LOGGER.info("Delete genre id: {}", mergedGenre.getId());
    }
}
