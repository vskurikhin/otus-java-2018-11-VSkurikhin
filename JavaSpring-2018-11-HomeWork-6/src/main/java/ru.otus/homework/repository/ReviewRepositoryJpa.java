package ru.otus.homework.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ReviewRepositoryJpa implements ReviewRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRepositoryJpa.class);

    @PersistenceContext
    private EntityManager em;

    public ReviewRepositoryJpa() {}

    public ReviewRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Review> findAll()
    {
        return em.createQuery("SELECT r FROM Review r", Review.class).getResultList();
    }

    @Override
    public Review findById(long id)
    {
        return em.find(Review.class, id);
    }

    @Override
    public List<Review> findAllWithBook()
    {
        return em
            .createNamedQuery("Review.findAllWithBook", Review.class)
            .getResultList();
    }

    @Override
    public List<Review> findByReview(String review)
    {
        return em
            .createQuery("SELECT r FROM Review r WHERE r.review LIKE :name", Review.class)
            .setParameter("name", review)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Review entity)
    {
        if (entity.getId() == 0) {
            em.persist(entity);
            em.flush();
        }
        else {
            em.merge(entity);
        }
        LOGGER.info("Save review id: {}", entity.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id)
    {
        Review mergedReview = em.merge(findById(id));
        em.remove(mergedReview);
        LOGGER.info("Delete review id: {}", mergedReview.getId());
    }
}
