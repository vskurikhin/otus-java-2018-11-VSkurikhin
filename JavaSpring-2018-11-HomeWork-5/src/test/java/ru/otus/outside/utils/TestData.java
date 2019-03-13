package ru.otus.outside.utils;

import org.h2.jdbcx.JdbcDataSource;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Publisher;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class TestData
{
    public static final int TEST_NUM = 3;
    public static final long TEST_ID = 13L;
    public static final String TEST = "test";
    public static final String TEST_FIRST_NAME = "testFirstName";
    public static final String TEST_LAST_NAME = "testLastName";
    public static final String TEST_ISBN = "testIsbn";
    public static final String TEST_TITLE = "testTitle";
    public static final String TEST_COPYRIGHT = "testCopyright";
    public static final String TEST_PUBLISHER_NAME = "testPublisherName";
    public static final String TEST_GENRE_NAME = "testGenre";
    public static final String JDBC_URL = "jdbc:h2:mem:test;INIT=runscript from 'classpath:createTestDB.sql'";

    public static final String INSERT_INTO_AUTHOR = "INSERT INTO author (author_id, first_name, last_name) VALUES" +
        " (13, 'testFirstName', 'testLastName')";
    public static final String DELETE_FROM_AUTHOR = "DELETE FROM author";

    public static final String INSERT_INTO_GENRE = "INSERT INTO genre (genre_id, genre)  VALUES ( 0, 'testGenre')";
    public static final String DELETE_FROM_GENRE = "DELETE FROM genre";

    public static final String INSERT_INTO_PUBLISHER = "INSERT INTO publisher (publisher_id, publisher_name) VALUES" +
        " (13, 'testPublisherName')";
    public static final String DELETE_FROM_PUBLISHER = "DELETE FROM publisher";

    public static final String INSERT_INTO_BOOK =
        "INSERT INTO book (book_id, isbn, title, edition_number, copyright, publisher_id, genre_id)" +
        " VALUES (13, 'testIsbn', 'testTitle', 3, 'testCopyright', 13, 0)";
    public static final String DELETE_FROM_BOOK = "DELETE FROM book";

    public static final String INSERT_INTO_AUTHOR_ISBN = " INSERT INTO author_isbn (author_id, book_id)" +
        " VALUES (13, 13)";
    public static final String DELETE_FROM_AUTHOR_ISBN = "DELETE FROM author_isbn";

    public static final String[] TEST_STRING_ARRAY_WITH_NULL = new String[]{"NULL"};

    public static final String[] TEST_STRING_ARRAY_TEST_ID = new String[]{"test_id"};

    public static DataSource injectTestDataSource()
    {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);

        return dataSource;
    }

    public static Author createTestAuthor13()
    {
        return new Author(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME);
    }

    public static Author createTestAuthorAnother()
    {
        Author author = new Author();
        author.setFirstName(TEST_FIRST_NAME + TEST);
        author.setLastName(TEST_LAST_NAME + TEST);

        return author;
    }

    public static Genre createTestGenre()
    {
        return new Genre(0, TEST_GENRE_NAME);
    }

    public static Genre createTestGenreAnother()
    {
        Genre genre = new Genre();
        genre.setGenre(TEST_GENRE_NAME + TEST);

        return genre;
    }

    public static Publisher createTestPublisher13()
    {
        return new Publisher(TEST_ID, TEST_PUBLISHER_NAME);
    }

    public static Publisher createTestPublisherAnother()
    {
        Publisher publisher = new Publisher();
        publisher.setPublisherName(TEST_PUBLISHER_NAME + TEST);

        return publisher;
    }

    public static Book createTestBook13()
    {
        return new Book(TEST_ID, TEST_ISBN, TEST_TITLE, TEST_NUM, TEST_COPYRIGHT, createTestPublisher13(), createTestGenre(), new LinkedList<>());
    }

    public static Book createTestBookAnother()
    {
        Book book = new Book();
        book.setIsbn(TEST_ISBN + TEST);
        book.setTitle(TEST_TITLE + TEST);
        book.setCopyright(TEST_COPYRIGHT + TEST);
        book.setPublisher(createTestPublisher13());
        book.setGenre(createTestGenre());

        return book;
    }


    public static void inserToTables(DataSource dataSource)
    {
        //noinspection Duplicates
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(INSERT_INTO_PUBLISHER);
            statement.execute(INSERT_INTO_GENRE);
            statement.execute(INSERT_INTO_BOOK);
            statement.execute(INSERT_INTO_AUTHOR);
            statement.execute(INSERT_INTO_AUTHOR_ISBN);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearTables(DataSource dataSource)
    {
        //noinspection Duplicates
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.execute(DELETE_FROM_AUTHOR_ISBN);
            statement.execute(DELETE_FROM_AUTHOR);
            statement.execute(DELETE_FROM_BOOK);
            statement.execute(DELETE_FROM_GENRE);
            statement.execute(DELETE_FROM_PUBLISHER);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean autoCommitOn(DataSource dataSource) throws SQLException
    {
        boolean result = dataSource.getConnection().getAutoCommit();
        dataSource.getConnection().setAutoCommit(true);

        return result;
    }

    public static void autoCommitRestore(DataSource dataSource, boolean state) throws SQLException
    {
        dataSource.getConnection().setAutoCommit(state);
    }
}
