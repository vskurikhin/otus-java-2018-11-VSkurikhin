package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Book;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;
import ru.otus.homework.repository.PublisherRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BooksServiceImplTest
{
    @Autowired
    private BookRepository repository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private GenreRepository genreRepository;

    private BooksServiceImpl service;

    @Test
    @DisplayName("is instantiated with new BooksServiceImpl()")
    void isInstantiatedWithNew()
    {
        new BooksServiceImpl(null, null, null);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = new BooksServiceImpl(repository, publisherRepository, genreRepository);
        }

        @Test
        @DisplayName("injected values in BooksServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("repository", repository);
        }

        @Test
        @DisplayName("BooksServiceImpl()")
        void findAll()
        {
            assertFalse(service.findAll().isEmpty());
        }

        @Test
        @DisplayName("BooksServiceImpl()")
        void findById()
        {
            assertNull(service.findById(Integer.MAX_VALUE));
            assertEquals(1L, service.findById(1L).getId());
        }

        @Test
        @DisplayName("BooksServiceImpl()")
        void findByIsbn()
        {
            assertNotNull(service.findByIsbn("0130895601"));
        }

        @Test
        @DisplayName("BooksServiceImpl()")
        void findByTitle()
        {
            assertFalse(service.findByTitle("Advanced Java 2 Platform How to Program").isEmpty());
        }

        @Test
        @DisplayName("BooksServiceImpl()")
        void insertUpdateDelete()
        {
            int expected = service.findAll().size();
            long id = service.insert("0000", "testTitle", 1, "2000", 1, 1);
            assertTrue(id > 0);
            assertTrue(expected < service.findAll().size());
            service.update(id, "0000", "testTitle", 1, "2000", 1, 1);
            service.delete(id);
            assertEquals(expected, service.findAll().size());
        }
    }
}

