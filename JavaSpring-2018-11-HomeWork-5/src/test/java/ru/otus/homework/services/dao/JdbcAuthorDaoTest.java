package ru.otus.homework.services.dao;

import org.junit.jupiter.api.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Author;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class JdbcAuthorDao")
class JdbcAuthorDaoTest
{
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbc;

    private JdbcAuthorDao dao;

    @Test
    @DisplayName("is instantiated with new JdbcAuthorDao()")
    void isInstantiatedWithNew()
    {
        new JdbcAuthorDao(null);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + dao.findAll());
    }

    private JdbcAuthorDao createDao()
    {
        dataSource = injectTestDataSource();
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        return new JdbcAuthorDao(jdbc);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            dao = createDao();
        }

        @Test
        @DisplayName("injected values in JdbcAuthorDao()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("jdbc", jdbc);
        }
    }

    @Nested
    @DisplayName("when new DAO operation")
    class WhenNewDAOoperation
    {
        @BeforeEach
        void createNewDAO() throws SQLException
        {
            dao = createDao();
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(INSERT_INTO_AUTHOR);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR);
        }

        @Test
        @DisplayName("find ALL")
        void testFindAll()
        {
            List<Author> expected = Collections.singletonList(createTestAuthor13());
            assertEquals(expected, dao.findAll());
        }

        @Test
        @DisplayName("find by id")
        void testFindById()
        {
            assertEquals(createTestAuthor13(), dao.findById(TEST_ID));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("find by first name")
        void testFindByFirstName()
        {
            List<Author> expected = Collections.singletonList(createTestAuthor13());
            assertEquals(expected, dao.findByFirstName(TEST_FIRST_NAME));
            assertTrue(dao.findByFirstName("__NONE__").isEmpty());
        }

        @Test
        @DisplayName("find by last name")
        void testFindByLastName()
        {
            List<Author> expected = Collections.singletonList(createTestAuthor13());
            assertEquals(expected, dao.findByLastName(TEST_LAST_NAME));
            assertTrue(dao.findByLastName("__NONE__").isEmpty());
        }

        @Test
        @DisplayName("insert")
        void testInsert() throws SQLException
        {
            Author author = createTestAuthorAnother();
            assertEquals(0L, author.getId());

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.insert(author);
            dataSource.getConnection().setAutoCommit(autoCommit);

            assertTrue(author.getId() > 0);
            assertEquals(2, dao.findAll().size());
            assertEquals(author, dao.findById(author.getId()));
        }

        @Test
        @DisplayName("update")
        void testUpdate() throws SQLException
        {
            Author authorModify = dao.findById(TEST_ID);
            authorModify.setFirstName(TEST_FIRST_NAME + TEST);
            authorModify.setLastName(TEST_LAST_NAME + TEST);

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.update(authorModify);
            assertEquals(authorModify, dao.findById(TEST_ID));
            dataSource.getConnection().setAutoCommit(autoCommit);
        }

        @SuppressWarnings("Duplicates")
        @Test
        @DisplayName("delete")
        void testDelete() throws SQLException
        {
            assertEquals(1, dao.findAll().size());
            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.delete(TEST_ID);
            assertEquals(0, dao.findAll().size());
            dao.delete(Long.MAX_VALUE);
            dataSource.getConnection().setAutoCommit(autoCommit);
        }
    }
}