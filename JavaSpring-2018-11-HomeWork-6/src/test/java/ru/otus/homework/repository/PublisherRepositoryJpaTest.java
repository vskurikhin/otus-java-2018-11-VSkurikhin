package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Publisher;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;
import ru.otus.outside.db.JpaSharedEntityManagerTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.createPublisher1;

@DisplayName("Class PublisherRepositoryJpa")
class PublisherRepositoryJpaTest
{
    private PublisherRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new PublisherRepositoryJpa();
        }

        @Test
        @DisplayName("default values in PublisherRepositoryJpa()")
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
            repository = new PublisherRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in PublisherRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table publisher success")
        @Test
        void findById_success()
        {
            Publisher expected = new Publisher();
            expected.setPublisherName("testPublisherName");

            when(entityManager.find(Publisher.class,1L)).thenReturn(expected);

            Publisher publisher = repository.findById(1L);
            assertEquals(expected, publisher);
        }

        @DisplayName("find by id from table publisher return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Publisher.class,1L)).thenReturn(null);

            Publisher publisher = repository.findById(1L);
            assertNull(publisher);
        }
    }
}