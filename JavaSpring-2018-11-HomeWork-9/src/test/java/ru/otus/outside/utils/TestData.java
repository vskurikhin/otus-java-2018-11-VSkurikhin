package ru.otus.outside.utils;

import ru.otus.homework.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static ru.otus.outside.utils.StringHelper.stringOrNULL;

public class TestData
{
    public static String TEST = "test";

    public static String TEST_ID = "id";

    public static int TEST_NUM = 3;

    public static String TEST_FIRST_NAME = "test_first_name_13";

    public static String TEST_LAST_NAME = "test_last_name_13";

    public static String TEST_ISBN = "test_isbn_name_13";

    public static String TEST_TITLE = "test_title_name_13";

    public static String TEST_COPYRIGHT= "test_copyright_13";

    public static String TEST_GENRE_NAME = "test_genre_13";

    public static String TEST_PUBLISHER_NAME = "test_publisher_13";

    public static String TEST_COMMENT_NAME = "test_comment_13";

    public static Author createAuthor0()
    {
        Author result = new Author();
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");

        return result;
    }

    public static Author createAuthor1()
    {
        Author result = new Author();
        result.setFirstName("test_first_name_1");
        result.setLastName("test_last_name_1");

        return result;
    }

    public static Author createAuthor2()
    {
        Author result = new Author();
        result.setFirstName("test_first_name_2");
        result.setLastName("test_last_name_2");

        return result;
    }

    public static Genre createGenre0()
    {
        Genre result = new Genre();
        result.setGenre("test_genre_0");

        return result;
    }

    public static Genre createGenre1()
    {
        Genre result = new Genre();
        result.setGenre("test_genre_1");

        return result;
    }

    public static Genre createGenre2()
    {
        Genre result = new Genre();
        result.setGenre("test_genre_2");

        return result;
    }

    public static Book createBook0()
    {
        Book result = new Book();
        result.setIsbn("test_isbn_0");
        result.setTitle("test_title_0");
        result.setEditionNumber(TEST_NUM);
        result.setCopyright("test_copyright_0");
        result.getAuthors().add(createAuthor0());
        result.getAuthors().add(createAuthor1());
        result.getGenres().add(createGenre0());
        result.getGenres().add(createGenre1());

        return result;
    }

    public static Book createBook1()
    {
        Book result = new Book();
        result.setIsbn("test_isbn_1");
        result.setTitle("test_title_1");
        result.setEditionNumber(TEST_NUM + 1);
        result.setCopyright("test_copyright_1");
        result.getAuthors().add(createAuthor1());
        result.getGenres().add(createGenre1());

        return result;
    }

    public static Review createReview0()
    {
        Review result = new Review();
        result.setReview("test_review_0");

        return result;
    }

    public static Review createReview1()
    {
        Review result = new Review();
        result.setReview("test_review_1");

        return result;
    }

    public static Review createReview2()
    {
        Review result = new Review();
        result.setReview("test_review_2");

        return result;
    }

    public static List<String[]> createListOfStringFromBook0()
    {

        List<String[]> expected = new ArrayList<>();
        Book book0 = createBook0();
        Author author0 = createAuthor0();
        Author author1 = createAuthor1();
        Genre genre0 = createGenre0();
        Genre genre1 = createGenre1();

        expected.add(new String[]{
            stringOrNULL(book0.getId()), book0.getIsbn(), book0.getTitle(), Integer.toString(book0.getEditionNumber()),
            book0.getCopyright(), author0.getFirstName(), author0.getLastName(), genre0.getGenre()
        });
        expected.add(new String[]{
            stringOrNULL(book0.getId()), book0.getIsbn(), book0.getTitle(), Integer.toString(book0.getEditionNumber()),
            book0.getCopyright(), author1.getFirstName(), author1.getLastName(), genre1.getGenre()
        });

        return expected;

    }
}
