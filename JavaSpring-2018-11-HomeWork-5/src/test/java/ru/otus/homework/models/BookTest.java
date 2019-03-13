package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class Book")
class BookTest
{
    private Book book;

    private Publisher publisher = new Publisher();

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
            assertThat(book).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(book).hasFieldOrPropertyWithValue("isbn", null);
            assertThat(book).hasFieldOrPropertyWithValue("title", null);
            assertThat(book).hasFieldOrPropertyWithValue("editionNumber", 0);
            assertThat(book).hasFieldOrPropertyWithValue("copyright", null);
            assertThat(book).hasFieldOrPropertyWithValue("publisher", null);
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
            book.setEditionNumber(TEST_NUM);
            assertThat(book).hasFieldOrPropertyWithValue("editionNumber", TEST_NUM);
            assertEquals(TEST_NUM, book.getEditionNumber());
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
        void testGetSetPublisher()
        {
            book.setPublisher(publisher);
            assertThat(book).hasFieldOrPropertyWithValue("publisher", publisher);
            assertEquals(publisher, book.getPublisher());
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
            book = new Book(TEST_ID, TEST_ISBN, TEST_TITLE, TEST_NUM, TEST_COPYRIGHT, publisher, genre, null);
        }

        @Test
        @DisplayName("initialized values in Person()")
        void defaults()
        {
            assertThat(book).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(book).hasFieldOrPropertyWithValue("isbn", TEST_ISBN);
            assertThat(book).hasFieldOrPropertyWithValue("title", TEST_TITLE);
            assertThat(book).hasFieldOrPropertyWithValue("editionNumber", TEST_NUM);
            assertThat(book).hasFieldOrPropertyWithValue("copyright", TEST_COPYRIGHT);
            assertThat(book).hasFieldOrPropertyWithValue("publisher", publisher);
            assertThat(book).hasFieldOrPropertyWithValue("genre", genre);
        }

        @Test
        @DisplayName("Equals for class PersonImpl and hashCode")
        void testEquals()
        {
            assertNotEquals(new Book(), book);
            Book expected = new Book(TEST_ID, TEST_ISBN, TEST_TITLE, TEST_NUM, TEST_COPYRIGHT, publisher, genre, null);
            assertEquals(expected.hashCode(), book.hashCode());
            assertEquals(expected, book);
        }

        @Test
        @DisplayName("The length of string from PersonImpl::toString is great than zero")
        void testToString()
        {
            assertTrue(book.toString().length() > 0);
        }
    }
}