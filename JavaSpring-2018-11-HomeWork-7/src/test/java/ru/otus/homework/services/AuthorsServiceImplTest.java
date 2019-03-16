package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.repository.AuthorRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AuthorsServiceImplTest
{
    @Autowired
    private AuthorRepository repository;

    private AuthorsServiceImpl service;

    @Test
    @DisplayName("is instantiated with new AuthorsServiceImpl()")
    void isInstantiatedWithNew()
    {
        new AuthorsServiceImpl(null);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = new AuthorsServiceImpl(repository);
        }

        @Test
        @DisplayName("injected values in AuthorsServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("repository", repository);
        }

        @Test
        @DisplayName("AuthorsServiceImpl()")
        void findAll()
        {
            assertFalse(service.findAll().isEmpty());
        }

        @Test
        @DisplayName("AuthorsServiceImpl()")
        void findById()
        {
            assertNull(service.findById(Integer.MAX_VALUE));
            assertEquals(1L, service.findById(1L).getId());
        }

        @Test
        @DisplayName("AuthorsServiceImpl()")
        void findByFirstName()
        {
            assertTrue(service.findByFirstName("").isEmpty());
            assertEquals(1, service.findByFirstName("Harvey").size());
        }

        @Test
        @DisplayName("AuthorsServiceImpl()")
        void findByLastName()
        {
            assertTrue(service.findByLastName("").isEmpty());
            assertEquals(1, service.findByLastName("Nieto").size());
        }

        @Test
        @DisplayName("AuthorsServiceImpl()")
        void insertUpdateDelete()
        {
            int expected = service.findAll().size();
            long id = service.insert("testFirstName", "testLastName");
            assertTrue(id > 0);
            assertTrue(expected < service.findAll().size());
            service.update(id, "firstNameTest", "lastNameTest");
            assertEquals(1, service.findByFirstName("firstNameTest").size());
            assertEquals(1, service.findByLastName("lastNameTest").size());
            service.delete(id);
            assertEquals(expected, service.findAll().size());
        }
    }
}

