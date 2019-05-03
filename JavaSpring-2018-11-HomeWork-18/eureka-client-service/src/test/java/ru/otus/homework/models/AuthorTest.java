package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class Author")
class AuthorTest
{
    private Author author;

    @Test
    @DisplayName("is instantiated with new Author()")
    void isInstantiatedWithNew() {
        new Author();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            author = new Author();
        }

        @Test
        @DisplayName("default values in Author()")
        void defaults()
        {
            assertThat(author).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(author).hasFieldOrPropertyWithValue("firstName", null);
            assertThat(author).hasFieldOrPropertyWithValue("lastName", null);
        }

        @Test
        @DisplayName("Setter and getter for firstName")
        void testGetSetFirstName()
        {
            author.setFirstName(TEST);
            assertThat(author).hasFieldOrPropertyWithValue("firstName", TEST);
            assertEquals(TEST, author.getFirstName());
        }

        @Test
        @DisplayName("Setter and getter for lastName")
        void testGetSetLastName()
        {
            author.setLastName(TEST);
            assertThat(author).hasFieldOrPropertyWithValue("lastName", TEST);
            assertEquals(TEST, author.getLastName());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            author = new Author(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME);
        }

        @Test
        @DisplayName("initialized values in Author()")
        void defaults()
        {
            assertThat(author).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(author).hasFieldOrPropertyWithValue("firstName", TEST_FIRST_NAME);
            assertThat(author).hasFieldOrPropertyWithValue("lastName", TEST_LAST_NAME);
        }

        @Test
        @DisplayName("Equals for class Author and hashCode")
        void testEquals()
        {
            assertNotEquals(new Author(), author);
            Author expected = new Author(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME);
            assertEquals(expected.hashCode(), author.hashCode());
            assertEquals(expected, author);
        }

        @Test
        @DisplayName("The length of string from Author::toString is great than zero")
        void testToString()
        {
            assertTrue(author.toString().length() > 0);
        }
    }
}