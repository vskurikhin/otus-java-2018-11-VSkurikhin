package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class Genre")
class GenreTest
{
    private Genre genre;

    @Test
    @DisplayName("is instantiated with new Genre()")
    void isInstantiatedWithNew() {
        new Genre();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            genre = new Genre();
        }

        @Test
        @DisplayName("default values in Genre()")
        void defaults()
        {
            assertThat(genre).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(genre).hasFieldOrPropertyWithValue("value", null);
        }

        @Test
        @DisplayName("Setter and getter for publisherName")
        void testGetSetFirstName()
        {
            genre.setValue(TEST);
            assertThat(genre).hasFieldOrPropertyWithValue("value", TEST);
            assertEquals(TEST, genre.getValue());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            genre = new Genre(TEST_ID, TEST_GENRE_NAME);
        }

        @Test
        @DisplayName("initialized values in Genre()")
        void defaults()
        {
            assertThat(genre).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(genre).hasFieldOrPropertyWithValue("value", TEST_GENRE_NAME);
        }

        @Test
        @DisplayName("Equals for class Genre and hashCode")
        void testEquals()
        {
            assertNotEquals(new Genre(), genre);
            Genre expected = new Genre(TEST_ID, TEST_GENRE_NAME);
            assertEquals(expected.hashCode(), genre.hashCode());
            assertEquals(expected, genre);
        }

        @Test
        @DisplayName("The length of string from Genre::toString is great than zero")
        void testToString()
        {
            assertTrue(genre.toString().length() > 0);
        }
    }
}