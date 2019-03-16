package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Book;
import ru.otus.outside.db.JpaDedicatedEntityManagerTest;
import ru.otus.outside.db.JpaSharedEntityManagerTest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class BookRepositoryJpa")
class BookRepositoryJpaTest
{
    private BookRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new BookRepositoryJpa();
        }

        @Test
        @DisplayName("default values in BookRepositoryJpa()")
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
            repository = new BookRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in BookRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table book success")
        @Test
        void findById_success()
        {
            Book expected = new Book();
            expected.setIsbn("testIsbn");

            when(entityManager.find(Book.class,1L)).thenReturn(expected);

            Book book = repository.findById(1L);
            assertEquals(expected, book);
        }

        @DisplayName("find by id from table book return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Book.class,1L)).thenReturn(null);

            Book book = repository.findById(1L);
            assertNull(book);
        }
    }
}
