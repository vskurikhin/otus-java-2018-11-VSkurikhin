package ru.otus.homework.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class BookRepositoryJpa implements BookRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryJpa.class);

    @PersistenceContext
    private EntityManager em;

    public BookRepositoryJpa() {}

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findAll()
    {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public Book findById(long id)
    {
        return em.find(Book.class, id);
    }

    @Override
    public Book findByIsbn(String isbn)
    {
        return em
            .createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
            .setParameter("isbn", isbn)
            .getSingleResult();
    }

    @Override
    public List<Book> findByTitle(String title)
    {
        return em
            .createQuery("SELECT b FROM Book b WHERE b.title LIKE :name", Book.class)
            .setParameter("name", title)
            .getResultList();
    }

    @Override
    public List<Book> findAllBooksAndTheirAuthors()
    {
        return em
            .createNamedQuery("Book.findAllWithDetail", Book.class)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Book entity)
    {
        if (entity.getId() == 0) {
            em.persist(entity);
            em.flush();
        }
        else {
            em.merge(entity);
        }
        LOGGER.info("Save book id: {}", entity.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id)
    {
        Book mergedBook = em.merge(findById(id));
        em.remove(mergedBook);
        LOGGER.info("Delete book id: {}", mergedBook.getId());
    }
}
