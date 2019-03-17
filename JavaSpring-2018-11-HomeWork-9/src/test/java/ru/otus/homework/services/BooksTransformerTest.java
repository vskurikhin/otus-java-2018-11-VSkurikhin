package ru.otus.homework.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.ListStringsHelper.assertListStringsEquals;
import static ru.otus.outside.utils.StringHelper.stringOrNULL;
import static ru.otus.outside.utils.TestData.*;

class BooksTransformerTest
{
    BooksTransformer transformer;

    @BeforeEach
    void setUp()
    {
        transformer = new BooksTransformer();
    }

    @AfterEach
    void tearDown()
    {
        transformer = null;
    }

    @Test
    void getHeader()
    {
        assertEquals(BooksTransformer.FIND_ALL_HEADER, transformer.getHeader());
    }

    @Test
    void unfold_null()
    {
        List<String[]> unfold = transformer.unfold(null);
        assertEquals(Collections.emptyList(), unfold);
    }

    @Test
    void unfold0()
    {
        Book book0 = new Book(null, "test", "test", 0, "test", null, null);

        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{ "NULL", "test", "test", "0", "test", "NULL", "NULL", "NULL" });

        List<String[]> unfold = transformer.unfold(book0);

        unfold.forEach(strings -> {
            for (String s : strings) {
                assertNotNull(s);
            }
        });
        assertListStringsEquals(expected, unfold);
        // printListStrings(System.err, unfold);
    }

    @Test
    void unfold1()
    {
        Book book0 = createBook0();
        List<String[]> expected = createListOfStringFromBook0();

        List<String[]> unfold = transformer.unfold(book0);

        unfold.forEach(strings -> {
            for (String s : strings) {
                assertNotNull(s);
            }
        });
        assertListStringsEquals(expected, unfold);
        // printListStrings(System.err, unfold);
    }

    @Test
    void unfold2()
    {
        Book book0 = createBook0();
        List<String[]> expected = createListOfStringFromBook0();
        Genre genre2 = createGenre2();

        book0.getGenres().add(createGenre2());
        expected.add(new String[]{
            stringOrNULL(book0.getId()), book0.getIsbn(), book0.getTitle(), Integer.toString(book0.getEditionNumber()),
            book0.getCopyright(), "NULL", "NULL", genre2.getGenre()
        });

        List<String[]> unfold = transformer.unfold(book0);

        unfold.forEach(strings -> {
            for (String s : strings) {
                assertNotNull(s);
            }
        });
        assertListStringsEquals(expected, unfold);
        // printListStrings(System.err, unfold);
    }

    @Test
    void unfold3()
    {
        Book book0 = createBook0();
        List<String[]> expected = createListOfStringFromBook0();
        Author author2 = createAuthor2();

        book0.getAuthors().add(createAuthor2());
        expected.add(new String[]{
            stringOrNULL(book0.getId()), book0.getIsbn(), book0.getTitle(), Integer.toString(book0.getEditionNumber()),
            book0.getCopyright(), author2.getFirstName(), author2.getLastName(), "NULL"
        });

        List<String[]> unfold = transformer.unfold(book0);

        unfold.forEach(strings -> {
            for (String s : strings) {
                assertNotNull(s);
            }
        });
        assertListStringsEquals(expected, unfold);
        // printListStrings(System.err, unfold);
    }
}