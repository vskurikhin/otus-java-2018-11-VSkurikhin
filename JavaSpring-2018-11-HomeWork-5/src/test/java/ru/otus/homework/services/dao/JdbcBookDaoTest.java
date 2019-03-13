package ru.otus.homework.services.dao;

import org.junit.jupiter.api.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Book;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class JdbcBookDao")
class JdbcBookDaoTest
{
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbc;

    private JdbcBookDao dao;

    @Test
    @DisplayName("is instantiated with new JdbcBookDao()")
    void isInstantiatedWithNew()
    {
        DataSource dataSource = injectTestDataSource();
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        new JdbcBookDao(jdbc);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + dao.findAll());
    }

    private JdbcBookDao createDao()
    {
        dataSource = injectTestDataSource();
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        return new JdbcBookDao(jdbc);
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
        @DisplayName("injected values in JdbcPublisherDao()")
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
            inserToTables(dataSource);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
        }

        @Test
        @DisplayName("find ALL")
        void testFindAll()
        {
            List<Book> expected = Collections.singletonList(createTestBook13());
            assertEquals(expected, dao.findAll());
        }

        @Test
        @DisplayName("find by id")
        void testFindById()
        {
            assertEquals(createTestBook13(), dao.findById(TEST_ID));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findById(Long.MAX_VALUE));
        }

        @Test
        void findByIsbn()
        {
            Book expected = createTestBook13();
            assertEquals(expected, dao.findByIsbn(TEST_ISBN));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findByIsbn(null));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findByIsbn(""));
        }

        @Test
        void findByTitle()
        {
            List<Book> expected = Collections.singletonList(createTestBook13());
            assertEquals(expected, dao.findByTitle(TEST_TITLE));
            List<Book> empty = new LinkedList<>();
            assertEquals(empty, dao.findByTitle(null));
            List<Book> emptyArrayList = new ArrayList<>();
            assertEquals(emptyArrayList, dao.findByTitle(null));
        }


        @Test
        void findAllBooksAndTheirAuthors() throws SQLException
        {
            assertEquals(1, dao.findAllBooksAndTheirAuthors().size());
        }

        @Test
        @DisplayName("insert")
        void testInsert() throws SQLException
        {
            Book book = createTestBookAnother();
            assertEquals(0L, book.getId());

            boolean autoCommit = autoCommitOn(dataSource);
            dao.insert(book);
            autoCommitRestore(dataSource, autoCommit);

            assertTrue(book.getId() > 0);
            assertEquals(2, dao.findAll().size());
            assertEquals(book, dao.findById(book.getId()));
        }

        @Test
        @DisplayName("update")
        void testUpdate() throws SQLException
        {
            Book bookModify = dao.findById(TEST_ID);
            bookModify.setTitle(TEST_GENRE_NAME + TEST);

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.update(bookModify);
            assertEquals(bookModify, dao.findById(TEST_ID));
            dataSource.getConnection().setAutoCommit(autoCommit);
        }

        @SuppressWarnings("Duplicates")
        @Test
        @DisplayName("delete")
        void testDelete() throws SQLException
        {
            assertEquals(1, dao.findAll().size());

            boolean autoCommit = autoCommitOn(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            assertThrows(DataIntegrityViolationException.class, () -> dao.delete(TEST_ID));
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            dao.delete(TEST_ID);
            assertEquals(0, dao.findAll().size());
            dao.delete(Long.MAX_VALUE);
            autoCommitRestore(dataSource, autoCommit);
        }
    }
}