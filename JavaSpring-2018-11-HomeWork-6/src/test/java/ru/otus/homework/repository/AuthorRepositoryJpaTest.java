package ru.otus.homework.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Author;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class AuthorRepositoryJpa")
class AuthorRepositoryJpaTest
{
    private AuthorRepositoryJpa repository;

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            repository = new AuthorRepositoryJpa();
        }

        @Test
        @DisplayName("default values in AuthorRepositoryJpa()")
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
            repository = new AuthorRepositoryJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in AuthorRepositoryJpa()")
        void defaults()
        {
            assertThat(repository).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table author success")
        @Test
        void findById_success()
        {
            Author expected = new Author();
            expected.setFirstName("testFirstName");
            expected.setLastName("testLastName");

            when(entityManager.find(Author.class,1L)).thenReturn(expected);

            Author author = repository.findById(1L);
            assertEquals(expected, author);
        }

        @DisplayName("find by id from table author return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Author.class,1L)).thenReturn(null);

            Author author = repository.findById(1L);
            assertNull(author);
        }
    }

    @Nested
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @ExtendWith(SpringExtension.class)
    @SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
    })
    @DisplayName("JPA H2 create/update tests for AuthorRepository")
    class WhenSprintBootTest
    {
        @Autowired
        private AuthorRepository repository;

        @DisplayName("persists new when save")
        @Test
        void testCreate() throws Exception
        {
            Author expected = createAuthor0();
            repository.save(expected);
            Author test = repository.findById(expected.getId());
            assertEquals(expected, test);
        }

        @DisplayName("merge detached object when save")
        @Test
        void testUpdate() throws Exception
        {
            Author expected = createAuthor0();
            repository.save(expected);
            expected.setFirstName(expected.getFirstName() + "_test");
            expected.setLastName(expected.getLastName() + "_test");
            repository.save(expected);
            Author test = repository.findById(expected.getId());
            assertEquals(expected, test);
        }

        @Test
        void delete() throws Exception
        {
            Author expected = createAuthor0();
            repository.save(expected);
            List<Author> list = repository.findAll();
            assertFalse(list.isEmpty());
            repository.delete(expected.getId());
            Author nullResult = repository.findById(expected.getId());
            assertNull(nullResult);
        }
    }
}