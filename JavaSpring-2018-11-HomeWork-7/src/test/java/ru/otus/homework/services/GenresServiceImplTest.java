package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class GenresServiceImplTest
{
    @Autowired
    private GenreRepository repository;

    private GenresServiceImpl service;

    @Test
    @DisplayName("is instantiated with new GenresServiceImpl()")
    void isInstantiatedWithNew()
    {
        new GenresServiceImpl(null);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = new GenresServiceImpl(repository);
        }

        @Test
        @DisplayName("injected values in GenresServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("repository", repository);
        }

        @Test
        @DisplayName("GenresServiceImpl()")
        void findAll()
        {
            List<Genre> list = service.findAll();
            System.out.println("list = " + list);
            assertFalse(service.findAll().isEmpty());
        }

        @Test
        @DisplayName("GenresServiceImpl()")
        void findById()
        {
            assertNull(service.findById(Integer.MAX_VALUE));
            assertEquals(1L, service.findById(1L).getId());
        }

        @Test
        @DisplayName("GenresServiceImpl()")
        void findByFirstName()
        {
            assertTrue(service.findByGenre("").isEmpty());
            assertEquals(1, service.findByGenre("Information Technology").size());
        }

        @Test
        @DisplayName("GenresServiceImpl()")
        void insertUpdateDelete()
        {
            int expected = service.findAll().size();
            long id = service.insert("testGenre");
            assertTrue(id > 0);
            assertTrue(expected < service.findAll().size());
            service.update(id, "genreTest");
            service.delete(id);
            assertEquals(expected, service.findAll().size());
        }
    }
}

