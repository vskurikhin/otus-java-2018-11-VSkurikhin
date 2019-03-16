package ru.otus.homework.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AuthorRepositoryJpa implements AuthorRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepositoryJpa.class);

    @PersistenceContext
    private EntityManager em;

    public AuthorRepositoryJpa() {}

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findByFirstName(String firstName)
    {
        return em
            .createQuery("SELECT a FROM Author a WHERE a.firstName LIKE :name", Author.class)
            .setParameter("name", firstName)
            .getResultList();
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        return em
            .createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :name", Author.class)
            .setParameter("name", lastName)
            .getResultList();
    }

    @Override
    public List<Author> findAll()
    {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Override
    public Author findById(long id)
    {
        return em.find(Author.class, id);
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Author entity)
    {
        if (entity.getId() == 0) {
            em.persist(entity);
            em.flush();
        }
        else {
            em.merge(entity);
        }
        LOGGER.info("Save book author id: {}", entity.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id)
    {
        Author mergedAuthor = em.merge(findById(id));
        em.remove(mergedAuthor);
        LOGGER.info("Delete book author id: {}", mergedAuthor.getId());
    }
}
