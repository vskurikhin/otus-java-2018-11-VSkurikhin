package ru.otus.outside.models.jpa;

import ru.otus.homework.models.jpa.*;

import java.math.BigInteger;
import java.util.Arrays;

import static ru.otus.outside.models.TestConstants.*;

@SuppressWarnings("Duplicates")
public class TestData
{

    public static Author createJpaAuthor0()
    {
        Author result = new Author();
        result.setId(BigInteger.ZERO);
        result.setFirstName(TEST_FIRST_NAME_0);
        result.setLastName(TEST_LAST_NAME_0);

        return result;
    }

    public static Author createJpaAuthor1()
    {
        Author result = new Author();
        result.setId(BigInteger.ONE);
        result.setFirstName(TEST_FIRST_NAME_1);
        result.setLastName(TEST_LAST_NAME_1);

        return result;
    }

    public static Author createJpaAuthor2()
    {
        Author result = new Author();
        result.setId(TEST_BID_2);
        result.setFirstName(TEST_FIRST_NAME_2);
        result.setLastName(TEST_LAST_NAME_2);

        return result;
    }

    public static Genre createJpaGenre0()
    {
        Genre result = new Genre();
        result.setId(BigInteger.ZERO);
        result.setValue(TEST_GENRE_0);

        return result;
    }

    public static Genre createJpaGenre1()
    {
        Genre result = new Genre();
        result.setId(BigInteger.ONE);
        result.setValue(TEST_GENRE_1);

        return result;
    }

    public static Genre createJpaGenre2()
    {
        Genre result = new Genre();
        result.setId(TEST_BID_2);
        result.setValue(TEST_GENRE_2);

        return result;
    }

    public static Book createJpaBook0()
    {
        Book result = new Book();
        result.setId(BigInteger.ZERO);
        result.setIsbn(TEST_ISBN_0);
        result.setTitle(TEST_TITLE_0);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_0);
        result.setGenre(createJpaGenre0());

        return result;
    }

    public static Book createJpaBook0(Genre genre)
    {
        Book result = new Book();
        result.setId(BigInteger.ZERO);
        result.setIsbn(TEST_ISBN_0);
        result.setTitle(TEST_TITLE_0);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_0);
        result.setGenre(genre);

        return result;
    }

    public static Book createJpaBook0(Genre genre, Author... authors)
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

    public static Book createJpaBook1()
    {
        Book result = new Book();
        result.setId(BigInteger.ONE);
        result.setIsbn(TEST_ISBN_1);
        result.setTitle(TEST_TITLE_1);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_1);
        result.setGenre(createJpaGenre1());

        return result;
    }

    public static Book createJpaBook2()
    {
        Book result = new Book();
        result.setId(TEST_BID_2);
        result.setIsbn(TEST_ISBN_2);
        result.setTitle(TEST_TITLE_2);
        result.setEditionNumber(TEST_NUM_3);
        result.setCopyright(TEST_COPYRIGHT_2);
        result.setGenre(createJpaGenre2());

        return result;
    }

    public static Review createJpaReview0()
    {
        Review result = new Review();
        result.setId(BigInteger.ZERO);
        result.setReview(TEST_REVIEW_0);
        result.setBook(createJpaBook0());

        return result;
    }

    public static Review createJpaReview0(Book book)
    {
        Review result = new Review();
        result.setId(BigInteger.ZERO);
        result.setReview(TEST_REVIEW_0);
        result.setBook(book);

        return result;
    }

    public static Review createJpaReview1()
    {
        Review result = new Review();
        result.setId(BigInteger.ONE);
        result.setReview(TEST_REVIEW_1);
        result.setBook(createJpaBook1());

        return result;
    }

    public static Review createJpaReview1(Book book)
    {
        Review result = new Review();
        result.setId(BigInteger.ONE);
        result.setReview(TEST_REVIEW_1);
        result.setBook(book);

        return result;
    }

    public static Review createJpaReview2()
    {
        Review result = new Review();
        result.setId(TEST_BID_2);
        result.setReview(TEST_REVIEW_2);
        result.setBook(createJpaBook2());

        return result;
    }

    public static Review createJpaReview2(Book book)
    {
        Review result = new Review();
        result.setId(TEST_BID_2);
        result.setReview(TEST_REVIEW_2);
        result.setBook(book);

        return result;
    }
}
