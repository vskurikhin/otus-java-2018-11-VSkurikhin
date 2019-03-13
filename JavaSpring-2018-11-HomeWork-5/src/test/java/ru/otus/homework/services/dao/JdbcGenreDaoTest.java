package ru.otus.homework.services.dao;

import org.junit.jupiter.api.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Genre;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class JdbcGenreDao")
class JdbcGenreDaoTest
{
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbc;

    private JdbcGenreDao dao;

    @Test
    @DisplayName("is instantiated with new JdbcGenreDao()")
    void isInstantiatedWithNew()
    {
        DataSource dataSource = injectTestDataSource();
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        new JdbcGenreDao(jdbc);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + dao.findAll());
    }

    private JdbcGenreDao createDao()
    {
        dataSource = injectTestDataSource();
        jdbc = new NamedParameterJdbcTemplate(dataSource);
        return new JdbcGenreDao(jdbc);
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
            List<Genre> expected = Collections.singletonList(createTestGenre());
            assertEquals(expected, dao.findAll());
        }

        @Test
        @DisplayName("find by id")
        void testFindById()
        {
            assertEquals(createTestGenre(), dao.findById(0L));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("find by id")
        void testFindByGenre()
        {
            assertEquals(Collections.singletonList(createTestGenre()), dao.findByGenre(TEST_GENRE_NAME));
            assertEquals(new ArrayList<Genre>(), dao.findByGenre(""));
        }

        @Test
        @DisplayName("insert")
        void testInsert() throws SQLException
        {
            Genre genre = createTestGenre();
            assertEquals(0L, genre.getId());

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.insert(genre);
            dataSource.getConnection().setAutoCommit(autoCommit);

            assertTrue(genre.getId() > 0);
            assertEquals(2, dao.findAll().size());
            assertEquals(genre, dao.findById(genre.getId()));
        }


        @Test
        @DisplayName("update")
        void testUpdate() throws SQLException
        {
            Genre genreModify = dao.findById(0L);
            genreModify.setGenre(TEST_GENRE_NAME + TEST);

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.update(genreModify);
            assertEquals(genreModify, dao.findById(0L));
            dataSource.getConnection().setAutoCommit(autoCommit);
        }

        @SuppressWarnings("Duplicates")
        @Test
        @DisplayName("delete")
        void testDelete() throws SQLException
        {
            assertEquals(1, dao.findAll().size());

            boolean autoCommit = autoCommitOn(dataSource);
            assertThrows(DataIntegrityViolationException.class, () -> dao.delete(0));
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            statement.execute(DELETE_FROM_BOOK);
            dao.delete(0);
            assertEquals(0, dao.findAll().size());
            dao.delete(Long.MAX_VALUE);
            autoCommitRestore(dataSource, autoCommit);
        }
    }
}