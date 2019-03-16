package ru.otus.outside.utils;

import ru.otus.homework.models.*;

import java.util.ArrayList;

public class TestData
{
    public static String TEST = "test";

    public static long TEST_ID = 1L;

    public static int TEST_NUM = 3;

    public static String TEST_AUTHOR6_FIRST_NAME = "test_first_name_6";

    public static String TEST_AUTHOR6_LAST_NAME = "test_last_name_6";

    public static String TEST_GENRE2_NAME = "test_genre_2";

    public static String TEST_PUBLISHER1_NAME = "test_publisher_name_1";

    public static String TEST_REVIEW31_NAME = "test_review_31";

    public static String TEST_FIRST_NAME = "test_first_name_1";

    public static String TEST_LAST_NAME = "test_last_name_1";

    public static String TEST_ISBN = "test_isbn_name_1";

    public static String TEST_TITLE = "test_title_name_1";

    public static String TEST_COPYRIGHT= "test_copyright_1";

    public static String TEST_GENRE_NAME = "test_genre_1";

    public static String TEST_PUBLISHER_NAME = "test_publisher_1";

    public static String TEST_COMMENT_NAME = "test_comment_1";

    public static String DELETE_FROM_AUTHOR_ISBN = "DELETE FROM author_isbn";

    public static String DELETE_FROM_BOOK = "DELETE FROM book";

    public static String DELETE_FROM_AUTHOR = "DELETE FROM author";

    public static String DELETE_FROM_GENRE = "DELETE FROM genre";

    public static String DELETE_FROM_PUBLISHER = "DELETE FROM publisher";

    public static String DELETE_FROM_BOOK_REVIEW = "DELETE FROM book_review";

    public static Publisher createPublisher0()
    {
        Publisher result = new Publisher();
        result.setId(0L);
        result.setPublisherName("test_publisher_name_0");

        return result;
    }

    public static Publisher createPublisher1()
    {
        Publisher result = new Publisher();
        result.setId(1L);
        result.setPublisherName("test_publisher_name_1");

        return result;
    }

    public static Genre createGenre0()
    {
        Genre result = new Genre();
        result.setId(0L);
        result.setGenre("test_genre_0");

        return result;
    }

    public static Genre createGenre2()
    {
        Genre result = new Genre();
        result.setId(2L);
        result.setGenre("test_genre_2");

        return result;
    }

    public static Book createBook3()
    {
        Book result = new Book();
        result.setId(3L);
        result.setIsbn("9999999993");
        result.setTitle("test_title_3");
        result.setEditionNumber(3);
        result.setCopyright("2003");
        result.setPublisher(createPublisher1());
        result.setGenre(createGenre2());

        return result;
    }

    public static Book attachCommentBook3(Book result)
    {
        ArrayList<Review> reviews = new ArrayList<>();
        Review review31 = createReview31();
        // ??? review31.setBook(result);
        reviews.add(review31);

        Review review32 = createReview32();
        // ??? review32.setBook(result);
        reviews.add(review32);

        // result.setReviews(reviews);

        return result;
    }

    public static Book authorOfBook(Book result)
    {
        Author author6 = createAuthor6();
        result.getAuthors().add(author6);

        Author author7 = createAuthor7();
        result.getAuthors().add(author7);

        Author author8 = createAuthor8();
        result.getAuthors().add(author8);


        return result;
    }

    public static Book createBook4()
    {
        Book result = new Book();
        result.setId(4L);
        result.setIsbn("9999999994");
        result.setTitle("test_title_4");
        result.setEditionNumber(4);
        result.setCopyright("2004");
        result.setPublisher(createPublisher1());
        result.setGenre(createGenre2());

        return result;
    }

    public static Book attachCommentBook4(Book result)
    {
        ArrayList<Review> reviews = new ArrayList<>();
        Review review41 = createReview41();
        // ??? review41.setBook(result);
        reviews.add(review41);
        // result.setReviews(reviews);

        return result;
    }

    public static Book createBook0()
    {
        Book result = new Book();
        result.setId(0L);
        result.setIsbn("0000000000");
        result.setTitle("test_title_0");
        result.setEditionNumber(1);
        result.setCopyright("2000");
        result.setPublisher(createPublisher0());
        result.setGenre(createGenre0());

        return result;
    }

    public static Book createBook5()
    {
        Book result = new Book();
        result.setId(5L);
        result.setIsbn("9999999995");
        result.setTitle("test_title_5");
        result.setEditionNumber(5);
        result.setCopyright("2005");
        result.setPublisher(createPublisher1());
        result.setGenre(createGenre2());

        return result;
    }

    public static Author createAuthor0()
    {
        Author result = new Author();
        result.setId(0L);
        result.setFirstName("test_first_name_0");
        result.setLastName("test_last_name_0");

        return result;
    }

    public static Author createAuthor6()
    {
        Author result = new Author();
        result.setId(6L);
        result.setFirstName("test_first_name_6");
        result.setLastName("test_last_name_6");

        return result;
    }

    public static Author createAuthor7()
    {
        Author result = new Author();
        result.setId(7L);
        result.setFirstName("test_first_name_7");
        result.setLastName("test_last_name_7");

        return result;
    }

    public static Author createAuthor8()
    {
        Author result = new Author();
        result.setId(8L);
        result.setFirstName("test_first_name_8");
        result.setLastName("test_last_name_8");

        return result;
    }

    public static Author createAuthor9()
    {
        Author result = new Author();
        result.setId(9L);
        result.setFirstName("test_first_name_9");
        result.setLastName("test_last_name_9");

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

    public static Review createReview31()
    {
        Review result = new Review();
        result.setId(31L);
        result.setReview("test_review_31");
        // result.setBook(null);

        return result;
    }

    public static Review createReview32()

    {
        Review result = new Review();
        result.setId(32L);
        result.setReview("test_review_32");
        // result.setBook(null);

        return result;
    }

    public static Review createReview41()
    {
        Review result = new Review();
        result.setId(41L);
        result.setReview("test_review_41");
        // result.setBook(null);

        return result;
    }
}
