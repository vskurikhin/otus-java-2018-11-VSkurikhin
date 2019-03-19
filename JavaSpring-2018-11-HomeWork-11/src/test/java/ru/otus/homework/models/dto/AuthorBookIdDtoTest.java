package ru.otus.homework.models.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Author;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.TestData.*;

class AuthorBookIdDtoTest
{
    private AuthorBookIdDto authorDto;

    @Test
    @DisplayName("is instantiated with new AuthorBookIdDto()")
    void isInstantiatedWithNew() {
        new AuthorBookIdDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            authorDto = new AuthorBookIdDto();
        }

        @Test
        @DisplayName("default values in AuthorBookIdDto()")
        void defaults()
        {
            assertThat(authorDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(authorDto).hasFieldOrPropertyWithValue("bookId", null);
            assertThat(authorDto).hasFieldOrPropertyWithValue("firstName", null);
            assertThat(authorDto).hasFieldOrPropertyWithValue("lastName", null);
        }

        @Test
        @DisplayName("Setter and getter for id")
        void testGetSetId()
        {
            authorDto.setId(TEST_SID);
            assertThat(authorDto).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertEquals(TEST_SID, authorDto.getId());
        }

        @Test
        @DisplayName("Setter and getter for bookId")
        void testGetSetBookId()
        {
            authorDto.setBookId(TEST_SID);
            assertThat(authorDto).hasFieldOrPropertyWithValue("bookId", TEST_SID);
            assertEquals(TEST_SID, authorDto.getBookId());
        }

        @Test
        @DisplayName("Setter and getter for firstName")
        void testGetSetFirstName()
        {
            authorDto.setFirstName(TEST);
            assertThat(authorDto).hasFieldOrPropertyWithValue("firstName", TEST);
            assertEquals(TEST, authorDto.getFirstName());
        }

        @Test
        @DisplayName("Setter and getter for lastName")
        void testGetSetLastName()
        {
            authorDto.setLastName(TEST);
            assertThat(authorDto).hasFieldOrPropertyWithValue("lastName", TEST);
            assertEquals(TEST, authorDto.getLastName());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        Author author = createAuthor0();

        @BeforeEach
        void createNew()
        {
            authorDto = new AuthorBookIdDto(author);
        }

        @Test
        @DisplayName("initialized values in AuthorBookIdDto()")
        void defaults()
        {
            assertThat(authorDto).hasFieldOrPropertyWithValue("bookId", null);
        }

        @Test
        @DisplayName("Equals for class AuthorBookIdDto and hashCode")
        void testEquals()
        {
            assertNotEquals(new AuthorBookIdDto(), authorDto);
            AuthorBookIdDto expected = new AuthorBookIdDto(author);
            assertEquals(expected.hashCode(), authorDto.hashCode());
            assertEquals(expected, authorDto);
        }

        @Test
        @DisplayName("The length of string from AuthorBookIdDto::toString is great than zero")
        void testToString()
        {
            assertTrue(authorDto.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with Author arg constructor")
    class Converters
    {
        @Test
        void testAuthorConstructor()
        {
            Author author = createAuthor0();
            AuthorBookIdDto authorDto = new AuthorBookIdDto(author);
            assertThat(authorDto).hasFieldOrPropertyWithValue("id", "0");
            assertThat(authorDto).hasFieldOrPropertyWithValue("bookId", null);
            assertThat(authorDto).hasFieldOrPropertyWithValue("firstName", author.getFirstName());
            assertThat(authorDto).hasFieldOrPropertyWithValue("lastName", author.getLastName());
        }
    }
}