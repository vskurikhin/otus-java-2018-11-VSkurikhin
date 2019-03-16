package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.repository.PublisherRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PublishersServiceImplTest
{
    @Autowired
    private PublisherRepository repository;

    private PublishersServiceImpl service;

    @Test
    @DisplayName("is instantiated with new PublishersServiceImpl()")
    void isInstantiatedWithNew()
    {
        new PublishersServiceImpl(null);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = new PublishersServiceImpl(repository);
        }

        @Test
        @DisplayName("injected values in PublishersServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("repository", repository);
        }

        @Test
        @DisplayName("PublishersServiceImpl()")
        void findAll()
        {
            List<Publisher> list = service.findAll();
            assertFalse(service.findAll().isEmpty());
        }

        @Test
        @DisplayName("PublishersServiceImpl()")
        void findById()
        {
            assertNull(service.findById(Integer.MAX_VALUE));
            assertEquals(1L, service.findById(1L).getId());
        }

        @Test
        @DisplayName("PublishersServiceImpl()")
        void findByFirstName()
        {
            assertTrue(service.findByPublisher("").isEmpty());
            assertEquals(1, service.findByPublisher("Prentice Hall PTR Upper Saddle River, NJ, USA").size());
        }

        @Test
        @DisplayName("PublishersServiceImpl()")
        void insertUpdateDelete()
        {
            int expected = service.findAll().size();
            long id = service.insert("testPublisher");
            assertTrue(id > 0);
            assertTrue(expected < service.findAll().size());
            service.update(id, "genreTest");
            service.delete(id);
            assertEquals(expected, service.findAll().size());
        }
    }
}

