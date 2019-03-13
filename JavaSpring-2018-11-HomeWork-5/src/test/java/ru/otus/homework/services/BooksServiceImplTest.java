package ru.otus.homework.services;

import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Book;
import ru.otus.homework.services.dao.JdbcBookDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class BooksServiceImpl")
class BooksServiceImplTest
{
    private DataSource dataSource;

    private JdbcBookDao dao;

    private BooksServiceImpl service;

    @Test
    @DisplayName("is instantiated with new BooksServiceImpl()")
    void isInstantiatedWithNew()
    {
        new BooksServiceImpl(null);
    }

    private void printFindAll()
    {
        System.out.println("transformList = " + service.findAll());
    }

    private BooksServiceImpl createService()
    {
        dataSource = injectTestDataSource();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        dao = new JdbcBookDao(jdbc);
        return new BooksServiceImpl(dao);
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
        @DisplayName("injected values in BooksServiceImpl()")
        void defaults()
        {
            assertThat(service).hasFieldOrPropertyWithValue("bookDao", dao);
        }
    }

    @Nested
    @DisplayName("BooksServiceImpl methods")
    class ServiceMethods
    {
        private final String[] TEST_RECORD = new String[]{
            Long.toString(TEST_ID),
            TEST_ISBN,
            TEST_TITLE,
            Integer.toString(TEST_NUM),
            TEST_COPYRIGHT,
            Long.toString(TEST_ID),
            Long.toString(0L),
        };
        private final String[] TEST_RECORD_BOOKS_AUTHORS = new String[]{
            Long.toString(TEST_ID),
            TEST_ISBN,
            TEST_TITLE,
            Integer.toString(TEST_NUM),
            TEST_COPYRIGHT,
            TEST_PUBLISHER_NAME,
            TEST_GENRE_NAME,
            TEST_FIRST_NAME,
            TEST_LAST_NAME
        };

        @BeforeEach
        void createNewService() throws SQLException
        {
            service = createService();
            inserToTables(dataSource);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
        }

        @Test
        void findAll()
        {
            List<Book> expected = Collections.singletonList(createTestBook13());
            List<Book> testList = service.findAll();
            assertEquals(expected, testList);
        }

        @Test
        void findById()
        {
            assertEquals(createTestBook13(), service.findById(13L));
            // TODO LOG
        }

        @Test
        void findByIsbn()
        {
            assertEquals(createTestBook13(), service.findByIsbn(TEST_ISBN));
            // TODO LOG
        }

        @Test
        void findByTitle()
        {
            assertEquals(Collections.singletonList(createTestBook13()), dao.findByTitle(TEST_TITLE));
        }

        @Test
        void insert()
        {
            assertTrue(service.insert(
                TEST_ISBN + TEST, TEST_TITLE + TEST, TEST_NUM + 1, TEST_COPYRIGHT, TEST_ID, 0L
            ) > 0);
        }

        @Test
        void update() throws SQLException
        {
            Book expected = createTestBook13();
            expected.setIsbn(expected.getIsbn() + TEST);
            expected.setTitle(expected.getTitle() + TEST);
            expected.setEditionNumber(expected.getEditionNumber() + 1);
            expected.setCopyright(expected.getCopyright() + TEST);

            expected.setTitle(TEST_TITLE + TEST);
            long id = service.update(
                expected.getId(),
                expected.getIsbn(),
                expected.getTitle(),
                expected.getEditionNumber(),
                expected.getCopyright(),
                expected.getPublisher().getId(),
                expected.getGenre().getId()
            );

            assertEquals(expected, service.findById(id));
        }

        @Test
        void delete() throws SQLException
        {
            assertEquals(1, service.findAll().size());
            boolean autoCommit = autoCommitOn(dataSource);
            Statement statement = dataSource.getConnection().createStatement();
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            service.delete(TEST_ID);
            assertEquals(0, service.findAll().size());
            autoCommitRestore(dataSource, autoCommit);
        }
    }
}