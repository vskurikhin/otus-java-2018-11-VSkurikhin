package ru.otus.homework.models.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.models.TestConstants.*;

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
            assertThat(genre).hasFieldOrPropertyWithValue("id", null);
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
            genre = new Genre(TEST_BID_13, TEST_GENRE_NAME_13);
        }

        @Test
        @DisplayName("initialized values in Genre()")
        void defaults()
        {
            assertThat(genre).hasFieldOrPropertyWithValue("id", TEST_BID_13);
            assertThat(genre).hasFieldOrPropertyWithValue("value", TEST_GENRE_NAME_13);
        }

        @Test
        @DisplayName("Equals for class Genre and hashCode")
        void testEquals()
        {
            assertNotEquals(new Genre(), genre);
            Genre expected = new Genre(TEST_BID_13, TEST_GENRE_NAME_13);
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