package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Genre;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Class GenreRepositoryJpa")
class GenreRepositoryJpaTest
{
    private GenreRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new GenreRepositoryJpa();
        }

        @Test
        @DisplayName("default values in GenreRepositoryJpa()")
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
            repository = new GenreRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in GenreRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table genre success")
        @Test
        void findById_success()
        {
            Genre expected = new Genre();
            expected.setGenre("testGenre");

            when(entityManager.find(Genre.class,1L)).thenReturn(expected);

            Genre genre = repository.findById(1L);
            assertEquals(expected, genre);
        }

        @DisplayName("find by id from table genre return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Genre.class,1L)).thenReturn(null);

            Genre genre = repository.findById(1L);
            assertNull(genre);
        }
    }
}