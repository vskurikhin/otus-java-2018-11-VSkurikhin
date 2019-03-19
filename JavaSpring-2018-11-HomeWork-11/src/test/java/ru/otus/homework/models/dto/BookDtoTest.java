package ru.otus.homework.models.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Book;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.TestData.*;

class BookDtoTest
{
    private BookDto bookDto;

    @Test
    @DisplayName("is instantiated with new BookDto()")
    void isInstantiatedWithNew() {
        new BookDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            bookDto = new BookDto();
        }

        @Test
        @DisplayName("default values in BookDto()")
        void defaults()
        {
            assertThat(bookDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(bookDto).hasFieldOrPropertyWithValue("isbn", null);
            assertThat(bookDto).hasFieldOrPropertyWithValue("title", null);
            assertThat(bookDto).hasFieldOrPropertyWithValue("editionNumber", null);
            assertThat(bookDto).hasFieldOrPropertyWithValue("authors", null);
            assertThat(bookDto).hasFieldOrPropertyWithValue("genre", null);
        }

        @Test
        @DisplayName("Setter and getter for id")
        void testGetSetId()
        {
            bookDto.setId(TEST_SID);
            assertThat(bookDto).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertEquals(TEST_SID, bookDto.getId());
        }

        @Test
        @DisplayName("Setter and getter for isbn")
        void testGetSetIsbn()
        {
            bookDto.setIsbn(TEST);
            assertThat(bookDto).hasFieldOrPropertyWithValue("isbn", TEST);
            assertEquals(TEST, bookDto.getIsbn());
        }

        @Test
        @DisplayName("Setter and getter for title")
        void testGetSetTitle()
        {
            bookDto.setTitle(TEST);
            assertThat(bookDto).hasFieldOrPropertyWithValue("title", TEST);
            assertEquals(TEST, bookDto.getTitle());
        }

        @Test
        @DisplayName("Setter and getter for editionNumber")
        void testGetSetEditionNumber()
        {
            bookDto.setEditionNumber(TEST);
            assertThat(bookDto).hasFieldOrPropertyWithValue("editionNumber", TEST);
            assertEquals(TEST, bookDto.getEditionNumber());
        }

        @Test
        @DisplayName("Setter and getter for authors")
        void testGetSetAuthors()
        {
            // TODO
        }

        @Test
        @DisplayName("Setter and getter for genre")
        void testGetSetGenre()
        {
            bookDto.setGenre(TEST);
            assertThat(bookDto).hasFieldOrPropertyWithValue("genre", TEST);
            assertEquals(TEST, bookDto.getGenre());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            bookDto = new BookDto(TEST_SID, TEST, TEST, TEST, TEST, null, TEST);
        }

        @Test
        @DisplayName("initialized values in BookDto()")
        void defaults()
        {
            assertThat(bookDto).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertThat(bookDto).hasFieldOrPropertyWithValue("isbn", TEST);
            assertThat(bookDto).hasFieldOrPropertyWithValue("title", TEST);
            assertThat(bookDto).hasFieldOrPropertyWithValue("editionNumber", TEST);
            assertThat(bookDto).hasFieldOrPropertyWithValue("authors", null);
            assertThat(bookDto).hasFieldOrPropertyWithValue("genre", TEST);
        }

        @Test
        @DisplayName("Equals for class BookDto and hashCode")
        void testEquals()
        {
            assertNotEquals(new BookDto(), bookDto);
            BookDto expected = new BookDto(TEST_SID, TEST, TEST, TEST, TEST, null, TEST);
            assertEquals(expected.hashCode(), bookDto.hashCode());
            assertEquals(expected, bookDto);
        }

        @Test
        @DisplayName("The length of string from BookDto::toString is great than zero")
        void testToString()
        {
            assertTrue(bookDto.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("converters")
    class Converters
    {
        @Test
        @DisplayName("when new with Book arg constructor")
        void testBookConstructor()
        {
            Book book = createBook0();
            BookDto bookDto = new BookDto(book);
            assertThat(bookDto).hasFieldOrPropertyWithValue("id", "0");
            assertThat(bookDto).hasFieldOrPropertyWithValue("isbn", book.getIsbn());
            assertThat(bookDto).hasFieldOrPropertyWithValue("title", book.getTitle());
            assertThat(bookDto).hasFieldOrPropertyWithValue("editionNumber", Integer.toString(book.getEditionNumber()));
            assertThat(bookDto).hasFieldOrPropertyWithValue("authors", book.getAuthors());
            assertThat(bookDto).hasFieldOrPropertyWithValue("genre", book.getGenre().getValue());
        }

        @Test
        @DisplayName("when update Book from BookDto")
        void testUpdateBook()
        {
            Book expected = createBook0();
            Book book = new Book();
            BookDto bookDto = new BookDto(expected);
            bookDto.updateBook(book);
            // TODO assertEquals(expected, book);
        }

        @Test
        @DisplayName("when update Book from BookDto")
        void testCreateBook()
        {
            // TODO
        }
    }
}