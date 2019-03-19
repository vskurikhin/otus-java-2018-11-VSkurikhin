package ru.otus.homework.models.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.Author;

import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.TestData.*;

class AuthorDtoTest
{
    private AuthorDto authorDto;

    @Test
    @DisplayName("is instantiated with new AuthorDto()")
    void isInstantiatedWithNew() {
        new AuthorDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            authorDto = new AuthorDto();
        }

        @Test
        @DisplayName("default values in AuthorDto()")
        void defaults()
        {
            assertThat(authorDto).hasFieldOrPropertyWithValue("id", null);
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

        @BeforeEach
        void createNew()
        {
            authorDto = new AuthorDto(TEST_SID, TEST_FIRST_NAME, TEST_LAST_NAME);
        }

        @Test
        @DisplayName("initialized values in AuthorDto()")
        void defaults()
        {
            assertThat(authorDto).hasFieldOrPropertyWithValue("id", TEST_SID);
            assertThat(authorDto).hasFieldOrPropertyWithValue("firstName", TEST_FIRST_NAME);
            assertThat(authorDto).hasFieldOrPropertyWithValue("lastName", TEST_LAST_NAME);
        }

        @Test
        @DisplayName("Equals for class AuthorDto and hashCode")
        void testEquals()
        {
            assertNotEquals(new AuthorDto(), authorDto);
            AuthorDto expected = new AuthorDto(TEST_SID, TEST_FIRST_NAME, TEST_LAST_NAME);
            assertEquals(expected.hashCode(), authorDto.hashCode());
            assertEquals(expected, authorDto);
        }

        @Test
        @DisplayName("The length of string from AuthorDto::toString is great than zero")
        void testToString()
        {
            assertTrue(authorDto.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("converters")
    class Converters
    {
        @Test
        @DisplayName("when new with Author arg constructor")
        void testAuthorConstructor()
        {
            Author author = createAuthor0();
            AuthorDto authorDto = new AuthorDto(author);
            assertThat(authorDto).hasFieldOrPropertyWithValue("id", "0");
            assertThat(authorDto).hasFieldOrPropertyWithValue("firstName", author.getFirstName());
            assertThat(authorDto).hasFieldOrPropertyWithValue("lastName", author.getLastName());
        }

        @Test
        @DisplayName("when update Author from AuthorDto")
        void testUpdateAuthor()
        {
            Author expected = createAuthor0();
            Author author = createAuthor0();
            AuthorDto authorDto = new AuthorDto(expected);
            authorDto.updateAuthor(author);
            assertEquals(expected, author);
        }

        @Test
        @DisplayName("when update Author from AuthorDto")
        void testCreateAuthor()
        {
            authorDto = new AuthorDto(TEST_SID, TEST_FIRST_NAME, TEST_LAST_NAME);
            Author expected = new Author(new BigInteger(authorDto.getId()), authorDto.getFirstName(), authorDto.getLastName());
            Author author = authorDto.createAuthor();
            assertEquals(expected, author);
        }
    }
}