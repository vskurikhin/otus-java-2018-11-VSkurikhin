package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.dao.JdbcGenreDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class GenreServiceImpl")
class GenreServiceImplTest
{
    private DataSource dataSource;

    private JdbcGenreDao dao;

    private GenreServiceImpl service;

    @Test
    @DisplayName("is instantiated with new GenreServiceImpl()")
    void isInstantiatedWithNew()
    {
        new GenreServiceImpl(null);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + service.findAll());
    }

    private GenreServiceImpl createService()
    {
        dataSource = injectTestDataSource();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        dao = new JdbcGenreDao(jdbc);
        return new GenreServiceImpl(dao);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            service = createService();
        }

        @Test
        @DisplayName("injected values in GenreServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("genreDao", dao);
        }
    }

    @Nested
    @DisplayName("GenreServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_RECORD = new String[]{Long.toString(0L), TEST_GENRE_NAME};
        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(INSERT_INTO_GENRE);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_GENRE);
        }

        @Test
        void findAll()
        {
            List<Genre> expected = Collections.singletonList(createTestGenre());
            List<Genre> testList = service.findAll();
            assertEquals(expected, testList);
            // TODO LOG
        }

        @Test
        void findById()
        {
            assertEquals(createTestGenre(), dao.findById(0L));
            // TODO LOG
        }

        @Test
        void findByGenre()
        {
            assertEquals(Collections.singletonList(createTestGenre()), dao.findByGenre(TEST_GENRE_NAME));
        }

        @Test
        void insert()
        {
            assertTrue(service.insert(TEST_GENRE_NAME + TEST) > 0L);
        }

        @Test
        void update()
        {
            Genre expected = new Genre();
            expected.setId(0L);
            expected.setGenre(TEST_GENRE_NAME + TEST);

            long id = service.update(expected.getId(), expected.getGenre());
            assertEquals(0, id);
            assertEquals(expected, service.findById(id));
        }

        @Test
        void delete() throws SQLException
        {
            assertEquals(1, service.findAll().size());
            boolean autoCommit = autoCommitOn(dataSource);
            service.delete(0L);
            assertEquals(0, service.findAll().size());
            autoCommitRestore(dataSource, autoCommit);
        }
    }
}