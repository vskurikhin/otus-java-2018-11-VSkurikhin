package ru.otus.homework.models.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.models.TestConstants.*;

@DisplayName("Class Book")
class BookTest
{
    private Book book;

    private Genre genre = new Genre();

    @Test
    @DisplayName("is instantiated with new Book()")
    void isInstantiatedWithNew() {
        new Book();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNewAnswer()
        {
            book = new Book();
        }

        @Test
        @DisplayName("default values in Book()")
        void defaults()
        {
            assertThat(book).hasFieldOrPropertyWithValue("id", null);
            assertThat(book).hasFieldOrPropertyWithValue("isbn", null);
            assertThat(book).hasFieldOrPropertyWithValue("title", null);
            assertThat(book).hasFieldOrPropertyWithValue("editionNumber", 0);
            assertThat(book).hasFieldOrPropertyWithValue("copyright", null);
            assertThat(book).hasFieldOrPropertyWithValue("authors", Collections.emptyList());
            assertThat(book).hasFieldOrPropertyWithValue("genre", null);
        }

        @Test
        @DisplayName("Setter and getter for isbn")
        void testGetSetIsbn()
        {
            book.setIsbn(TEST);
            assertThat(book).hasFieldOrPropertyWithValue("isbn", TEST);
            assertEquals(TEST, book.getIsbn());
        }

        @Test
        @DisplayName("Setter and getter for title")
        void testGetSetTitle ()
        {
            book.setTitle(TEST);
            assertThat(book).hasFieldOrPropertyWithValue("title", TEST);
            assertEquals(TEST, book.getTitle());
        }

        @Test
        @DisplayName("Setter and getter for editionNumber")
        void testGetSetEditionNumber()
        {
            book.setEditionNumber(TEST_NUM_3);
            assertThat(book).hasFieldOrPropertyWithValue("editionNumber", TEST_NUM_3);
            assertEquals(TEST_NUM_3, book.getEditionNumber());
        }

        @Test
        @DisplayName("Setter and getter for copyright")
        void testGetSetCopyright()
        {
            book.setCopyright(TEST);
            assertThat(book).hasFieldOrPropertyWithValue("copyright", TEST);
            assertEquals(TEST, book.getCopyright());
        }

        @Test
        @DisplayName("Setter and getter for publisher")
        void testGetSetGenre()
        {
            book.setGenre(genre);
            assertThat(book).hasFieldOrPropertyWithValue("genre", genre);
            assertEquals(genre, book.getGenre());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            book = new Book(
                TEST_BID_13, TEST_ISBN_13, TEST_TITLE_13, TEST_NUM_3, TEST_COPYRIGHT_13, Collections.emptyList(), genre
            );
        }

        @Test
        @DisplayName("initialized values in Person()")
        void defaults()
        {
            assertThat(book).hasFieldOrPropertyWithValue("id", TEST_BID_13);
            assertThat(book).hasFieldOrPropertyWithValue("isbn", TEST_ISBN_13);
            assertThat(book).hasFieldOrPropertyWithValue("title", TEST_TITLE_13);
            assertThat(book).hasFieldOrPropertyWithValue("editionNumber", TEST_NUM_3);
            assertThat(book).hasFieldOrPropertyWithValue("copyright", TEST_COPYRIGHT_13);
            assertThat(book).hasFieldOrPropertyWithValue("authors", Collections.emptyList());
            assertThat(book).hasFieldOrPropertyWithValue("genre", genre);
        }

        @Test
        @DisplayName("Equals for class PersonImpl and hashCode")
        void testEquals()
        {
            assertNotEquals(new Book(), book);
            assertNotEquals(book, new Book());
            Book expected = new Book(
                TEST_BID_13, TEST_ISBN_13, TEST_TITLE_13, TEST_NUM_3, TEST_COPYRIGHT_13, Collections.emptyList(), genre
            );
            assertEquals(expected.hashCode(), book.hashCode());
            assertEquals(expected, book);

            Book expected1 = new Book(
                BigInteger.ONE, TEST_ISBN_13, TEST_TITLE_13, TEST_NUM_3, TEST_COPYRIGHT_13, Collections.emptyList(), genre
            );
            Book expected2 = new Book(
                new BigInteger("1"), TEST_ISBN_13, TEST_TITLE_13, TEST_NUM_3, TEST_COPYRIGHT_13, Collections.emptyList(), genre
            );
            assertEquals(expected1, expected2);
        }

        @Test
        @DisplayName("The length of string from PersonImpl::toString is great than zero")
        void testToString()
        {
            assertTrue(book.toString().length() > 0);
        }
    }
}