package ru.otus.outside.utils;

import ru.otus.homework.models.*;

import java.nio.charset.Charset;
import java.util.Arrays;

public class TestData
{
    public static String TEST = "test";

    public static long TEST_ID = 13L;

    public static Long TEST_LID = 13L;

    public static String TEST_SID = "13";

    public static int TEST_NUM = 3;

    public static String TEST_FIRST_NAME = "test_first_name_13";

    public static String TEST_LAST_NAME = "test_last_name_13";

    public static String TEST_ISBN = "test_isbn_name_13";

    public static String TEST_TITLE = "test_title_name_13";

    public static String TEST_COPYRIGHT= "test_copyright_13";

    public static String TEST_GENRE_NAME = "test_genre_13";

    public static String TEST_COMMENT_NAME = "test_comment_13";

    public static Author createAuthor0()
    {
        Author result = new Author();
        result.setId(0L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");

        return result;
    }

    public static Author createAuthor1()
    {
        Author result = new Author();
        result.setId(1L);
        result.setFirstName("test_first_name_1");
        result.setLastName("test_last_name_1");

        return result;
    }

    public static Author createAuthor2()
    {
        Author result = new Author();
        result.setId(2L);
        result.setFirstName("test_first_name_2");
        result.setLastName("test_last_name_2");

        return result;
    }

    public static Genre createGenre0()
    {
        Genre result = new Genre();
        result.setId(0L);
        result.setValue("test_genre_0");

        return result;
    }

    public static Genre createGenre1()
    {
        Genre result = new Genre();
        result.setId(1L);
        result.setValue("test_genre_1");

        return result;
    }

    public static Genre createGenre2()
    {
        Genre result = new Genre();
        result.setId(2L);
        result.setValue("test_genre_2");

        return result;
    }

    public static Book createBook0()
    {
        Book result = new Book();
        result.setId(0L);
        result.setIsbn("test_isbn_0");
        result.setTitle("test_title_0");
        result.setEditionNumber(TEST_NUM);
        result.setCopyright("test_copyright_0");
        result.setGenre(createGenre0());

        return result;
    }

    public static Book createBook0(Genre genre)
    {
        Book result = new Book();
        result.setId(0L);
        result.setIsbn("test_isbn_0");
        result.setTitle("test_title_0");
        result.setEditionNumber(TEST_NUM);
        result.setCopyright("test_copyright_0");
        result.setGenre(genre);

        return result;
    }

    public static Book createBook0(Genre genre, Author... authors)
    {
        Book result = new Book();
        result.setId(0L);
        result.setIsbn("test_isbn_0");
        result.setTitle("test_title_0");
        result.setEditionNumber(TEST_NUM);
        result.setCopyright("test_copyright_0");
        Arrays.stream(authors).forEach(a -> result.getAuthors().add(a));
        result.setGenre(genre);

        return result;
    }

    public static Book createBook1()
    {
        Book result = new Book();
        result.setId(1L);
        result.setIsbn("test_isbn_1");
        result.setTitle("test_title_1");
        result.setEditionNumber(TEST_NUM);
        result.setCopyright("test_copyright_1");
        result.setGenre(createGenre1());

        return result;
    }

    public static Book createBook2()
    {
        Book result = new Book();
        result.setId(2L);
        result.setIsbn("test_isbn_2");
        result.setTitle("test_title_2");
        result.setEditionNumber(TEST_NUM);
        result.setCopyright("test_copyright_2");
        result.setGenre(createGenre2());

        return result;
    }

    public static Review createReview0()
    {
        Review result = new Review();
        result.setId(0L);
        result.setReview("test_review_0");
        result.setBook(createBook0());

        return result;
    }

    public static Review createReview0(Book book)
    {
        Review result = new Review();
        result.setId(0L);
        result.setReview("test_review_0");
        result.setBook(book);

        return result;
    }

    public static Review createReview1()
    {
        Review result = new Review();
        result.setId(1L);
        result.setReview("test_review_1");
        result.setBook(createBook1());

        return result;
    }

    public static Review createReview1(Book book)
    {
        Review result = new Review();
        result.setId(1L);
        result.setReview("test_review_1");
        result.setBook(book);

        return result;
    }

    public static Review createReview2()
    {
        Review result = new Review();
        result.setId(2L);
        result.setReview("test_review_2");
        result.setBook(createBook2());

        return result;
    }

    public static Review createReview2(Book book)
    {
        Review result = new Review();
        result.setId(2L);
        result.setReview("test_review_2");
        result.setBook(book);

        return result;
    }
}
