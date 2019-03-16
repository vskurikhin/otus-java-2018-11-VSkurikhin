package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.ReviewRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReviewsServiceImplTest
{
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private BookRepository bookRepository;

    private ReviewsServiceImpl service;

    @Test
    @DisplayName("is instantiated with new ReviewsServiceImpl()")
    void isInstantiatedWithNew()
    {
        new ReviewsServiceImpl(null, null);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = new ReviewsServiceImpl(repository, bookRepository);
        }

        @Test
        @DisplayName("injected values in ReviewsServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("repository", repository);
            assertThat(service).hasFieldOrPropertyWithValue("bookRepository", bookRepository);
        }

        @Test
        @DisplayName("ReviewsServiceImpl()")
        void findAll()
        {
            assertFalse(service.findAll().isEmpty());
        }

        @Test
        @DisplayName("ReviewsServiceImpl()")
        void findById()
        {
            assertNull(service.findById(Integer.MAX_VALUE));
            assertEquals(11L, service.findById(11L).getId());
        }

        @Test
        @DisplayName("ReviewsServiceImpl()")
        void findByTitle()
        {
            assertFalse(service.findByReview("test_review_11").isEmpty());
        }

        @Test
        @DisplayName("ReviewsServiceImpl()")
        void insertUpdateDelete()
        {
            int expected = service.findAll().size();
            long id = service.insert("testReview", 1);
            assertTrue(id > 0);
            assertTrue(expected < service.findAll().size());
            service.update(id, "reviewTest", 1);
            service.delete(id);
            assertEquals(expected, service.findAll().size());
        }
    }
}

