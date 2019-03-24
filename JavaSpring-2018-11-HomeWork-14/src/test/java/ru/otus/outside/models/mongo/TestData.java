package ru.otus.outside.models.mongo;

import ru.otus.homework.models.mongo.*;

import java.math.BigInteger;
import java.util.Arrays;

import static ru.otus.outside.models.TestConstants.*;

@SuppressWarnings("Duplicates")
public class TestData
{
    public static Author createMongoAuthor0()
    {
        Author result = new Author();
        result.setId(BigInteger.ZERO);
        result.setFirstName(TEST_FIRST_NAME_0);
        result.setLastName(TEST_LAST_NAME_0);

        return result;
    }

    public static Author createMongoAuthor1()
    {
        Author result = new Author();
        result.setId(BigInteger.ONE);
        result.setFirstName(TEST_FIRST_NAME_1);
        result.setLastName(TEST_LAST_NAME_1);

        return result;
    }

    public static Author createMongoAuthor2()
    {
        Author result = new Author();
        result.setId(TEST_BID_2);
        result.setFirstName(TEST_FIRST_NAME_2);
        result.setLastName(TEST_LAST_NAME_2);

        return result;
    }

    public static Genre createMongoGenre0()
    {
        Genre result = new Genre();
        result.setId(BigInteger.ZERO);
        result.setValue(TEST_GENRE_0);

        return result;
    }

    public static Genre createMongoGenre1()
    {
        Genre result = new Genre();
        result.setId(BigInteger.ONE);
        result.setValue(TEST_GENRE_1);

        return result;
    }

    public static Genre createMongoGenre2()
    {
        Genre result = new Genre();
        result.setId(TEST_BID_2);
        result.setValue(TEST_GENRE_2);

        return result;
    }

    public static Book createMongoBook0()
    {
        Book result = new Book();
        result.setId(BigInteger.ZERO);
        result.setIsbn(TEST_ISBN_0);
        result.setTitle(TEST_TITLE_0);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_0);
        result.setGenre(createMongoGenre0());

        return result;
    }

    public static Book createMongoBook0(Genre genre)
    {
        Book result = new Book();
        result.setId(BigInteger.ZERO);
        result.setId(BigInteger.ZERO);
        result.setIsbn(TEST_ISBN_0);
        result.setTitle(TEST_TITLE_0);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_0);
        result.setGenre(genre);

        return result;
    }

    public static Book createMongoBook0(Genre genre, Author... authors)
    {
        Book result = new Book();
        result.setId(BigInteger.ZERO);
        result.setIsbn(TEST_ISBN_0);
        result.setTitle(TEST_TITLE_0);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_0);
        Arrays.stream(authors).forEach(a -> result.getAuthors().add(a));
        result.setGenre(genre);

        return result;
    }

    public static Book createMongoBook1()
    {
        Book result = new Book();
        result.setId(BigInteger.ONE);
        result.setIsbn(TEST_ISBN_1);
        result.setTitle(TEST_TITLE_1);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_1);
        result.setGenre(createMongoGenre1());

        return result;
    }

    public static Book createMongoBook2()
    {
        Book result = new Book();
        result.setId(TEST_BID_2);
        result.setIsbn(TEST_ISBN_2);
        result.setTitle(TEST_TITLE_2);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_2);
        result.setGenre(createMongoGenre2());

        return result;
    }

    public static Review createMongoReview0()
    {
        Review result = new Review();
        result.setId(BigInteger.ZERO);
        result.setReview(TEST_REVIEW_0);
        result.setBook(createMongoBook0());

        return result;
    }

    public static Review createMongoReview0(Book book)
    {
        Review result = new Review();
        result.setId(BigInteger.ZERO);
        result.setReview(TEST_REVIEW_0);
        result.setBook(book);

        return result;
    }

    public static Review createMongoReview1()
    {
        Review result = new Review();
        result.setId(BigInteger.ONE);
        result.setReview(TEST_REVIEW_1);
        result.setBook(createMongoBook1());

        return result;
    }

    public static Review createMongoReview1(Book book)
    {
        Review result = new Review();
        result.setId(BigInteger.ONE);
        result.setReview(TEST_REVIEW_1);
        result.setBook(book);

        return result;
    }

    public static Review createMongoReview2()
    {
        Review result = new Review();
        result.setId(TEST_BID_2);
        result.setReview(TEST_REVIEW_2);
        result.setBook(createMongoBook2());

        return result;
    }

    public static Review createMongoReview2(Book book)
    {
        Review result = new Review();
        result.setId(TEST_BID_2);
        result.setReview(TEST_REVIEW_2);
        result.setBook(book);

        return result;
    }
}
