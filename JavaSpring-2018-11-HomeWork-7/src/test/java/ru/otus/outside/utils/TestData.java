package ru.otus.outside.utils;

import ru.otus.homework.models.*;

import java.util.Collections;
import java.util.LinkedList;

public class TestData
{
    public static String TEST = "test";

    public static long TEST_ID = 13L;

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
        result.setId(0L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");

        return result;
    }

    public static Genre createGenre0()
    {
        Genre result = new Genre();
        result.setId(0L);
        result.setGenre("test_genre_0");

        return result;
    }

    public static Publisher createPublisher0()
    {
        Publisher result = new Publisher();
        result.setId(0L);
        result.setPublisherName("test_publisher_0");

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
        result.setPublisher(createPublisher0());
        result.setAuthors(Collections.emptyList());
        result.setGenre(createGenre0());

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
}
