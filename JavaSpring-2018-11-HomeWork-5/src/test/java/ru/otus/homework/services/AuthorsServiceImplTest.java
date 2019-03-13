package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.dao.JdbcAuthorDao;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.homework.services.AuthorsServiceImpl.FIND_ALL_HEADER;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class AuthorsServiceImpl")
class AuthorsServiceImplTest
{
    private DataSource dataSource;

    private JdbcAuthorDao dao;

    private AuthorsServiceImpl service;

    @Test
    @DisplayName("is instantiated with new AuthorsServiceImpl()")
    void isInstantiatedWithNew()
    {
        new AuthorsServiceImpl(null);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + service.findAll());
    }

    private AuthorsServiceImpl createService()
    {
        dataSource = injectTestDataSource();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        dao = new JdbcAuthorDao(jdbc);
        return new AuthorsServiceImpl(dao);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            service = createService();
        }

        @Test
        @DisplayName("injected values in AuthorsServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("authorDao", dao);
        }
    }

    @Nested
    @DisplayName("when mock JdbcAuthorDao")
    class WhenMock
    {
        private JdbcAuthorDao authorDao;

        @BeforeEach
        void mockEntityManager()
        {
            authorDao = mock(JdbcAuthorDao.class);
            service = new AuthorsServiceImpl(authorDao);
        }

        @DisplayName("find by id from table author return null")
        @Test
        void findById_null()
        {
            when(authorDao.findById(1L)).thenThrow(new EmptyResultDataAccessException(0));

            Author author = service.findById(1L);
            assertNull(author);
        }

        @DisplayName("find by id from table author success")
        @Test
        void findById_success()
        {
            Author expected = createTestAuthor13();
            expected.setFirstName("testFirstName");
            expected.setLastName("testLastName");

            when(authorDao.findById(expected.getId())).thenReturn(expected);

            Author author = service.findById(expected.getId());
            assertEquals(expected, author);
        }

        @DisplayName("find by id from table author return null")
        @Test
        void findAll_empty()
        {
            List<Author> expected = new LinkedList<>();

            when(authorDao.findAll()).thenReturn(expected);

            List<Author> authors = service.findAll();
            assertEquals(expected, authors);
        }

        @DisplayName("find by id from table author return null")
        @Test
        void findAll_success()
        {
            Author author = createTestAuthor13();
            List<Author> expected = new LinkedList<>();
            expected.add(author);

            when(authorDao.findAll()).thenReturn(expected);

            List<Author> authors = service.findAll();
            authors.add(author);
            assertEquals(expected, authors);
        }
    }

    @Nested
    @DisplayName("AuthorsServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_RECORD = new String[]{Long.toString(TEST_ID), TEST_FIRST_NAME, TEST_LAST_NAME};

        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(INSERT_INTO_AUTHOR);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR);
        }

        @Test
        void findByFirstName()
        {
            List<Author> expected = new ArrayList<>();
            expected.add(createTestAuthor13());

            List<Author> testList = service.findByFirstName(TEST_FIRST_NAME);
            assertEquals(expected, testList);
        }

        @Test
        void findByLastName()
        {
            List<Author> expected = new ArrayList<>();
            expected.add(createTestAuthor13());

            List<Author> testList = service.findByLastName(TEST_LAST_NAME);
            assertEquals(expected, testList);
        }

        @Test
        void insert()
        {
            assertTrue(service.insert(TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST) > 0);
        }

        @Test
        void update()
        {
            long id = service.update(TEST_ID, TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST);
            assertTrue(id > 0);

            List<String[]> expected = new ArrayList<>();
            expected.add(FIND_ALL_HEADER);
            expected.add(new String[]{Long.toString(id), TEST_FIRST_NAME + TEST, TEST_LAST_NAME + TEST});
        }

        @SuppressWarnings("Duplicates")
        @Test
        void delete() throws SQLException
        {
            assertEquals(1, service.findAll().size());
            boolean autoCommit = autoCommitOn(dataSource);
            service.delete(TEST_ID);
            assertEquals(0, service.findAll().size());
            autoCommitRestore(dataSource, autoCommit);
        }
    }
}