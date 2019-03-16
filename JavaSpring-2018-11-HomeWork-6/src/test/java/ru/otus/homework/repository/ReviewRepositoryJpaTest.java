package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Review;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.createReview0;

@DisplayName("Class ReviewRepositoryJpa")
class ReviewRepositoryJpaTest
{
    private ReviewRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new ReviewRepositoryJpa();
        }

        @Test
        @DisplayName("default values in ReviewRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", null);
        }
    }

    @Nested
    @DisplayName("when mock EntityManager")
    class WhenMock
    {
        private EntityManager entityManager;

        @BeforeEach
        void mockEntityManager()
        {
            entityManager = mock(EntityManager.class);
            repository = new ReviewRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in ReviewRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table review success")
        @Test
        void findById_success()
        {
            Review expected = new Review();
            expected.setReview("testReview");

            when(entityManager.find(Review.class,1L)).thenReturn(expected);

            Review review = repository.findById(1L);
            assertEquals(expected, review);
        }

        @DisplayName("find by id from table review return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Review.class,1L)).thenReturn(null);

            Review review = repository.findById(1L);
            assertNull(review);
        }
    }

    @Nested
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @ExtendWith(SpringExtension.class)
    @SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
    })
    @DisplayName("JPA H2 create/update tests for ReviewRepositoryJpa")
    class WhenSprintBootTest
    {
        @Autowired
        private BookRepository bookRepository;

        @Autowired
        private GenreRepository genreRepository;

        @Autowired
        private PublisherRepository publisherRepository;

        @Autowired
        private ReviewRepository repository;

        private void saveReview(Review review)
        {
            genreRepository.save(review.getBook().getGenre());
            publisherRepository.save(review.getBook().getPublisher());
            bookRepository.save(review.getBook());
            repository.save(review);
        }

        @DisplayName("persists new when save")
        @Test
        void testCreate() throws Exception
        {
            Review expected = createReview0();
            saveReview(expected);
            Review test = repository.findById(expected.getId());
            expected.getBook().setAuthors(null);
            test.getBook().setAuthors(null);
            assertEquals(expected, test);
        }

        @DisplayName("merge detached object when save")
        @Test
        void testUpdate() throws Exception
        {
            Review expected = createReview0();
            saveReview(expected);
            expected.setReview(expected.getReview() + "_test");
            repository.save(expected);
            Review test = repository.findById(expected.getId());
            expected.getBook().setAuthors(null);
            test.getBook().setAuthors(null);
            assertEquals(expected, test);
        }

        @Test
        void delete() throws Exception
        {
            Review expected = createReview0();
            saveReview(expected);
            List<Review> list1 = repository.findAll();
            assertFalse(list1.isEmpty());
            repository.delete(expected.getId());
            Review nullResult = repository.findById(expected.getId());
            assertNull(nullResult);
        }
    }
}