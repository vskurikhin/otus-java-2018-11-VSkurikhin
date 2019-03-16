package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class Publisher")
class PublisherTest
{
    private Publisher publisher;

    @Test
    @DisplayName("is instantiated with new Publisher()")
    void isInstantiatedWithNew() {
        new Publisher();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            publisher = new Publisher();
        }

        @Test
        @DisplayName("default values in Publisher()")
        void defaults()
        {
            assertThat(publisher).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(publisher).hasFieldOrPropertyWithValue("publisherName", null);
        }

        @Test
        @DisplayName("Setter and getter for publisherName")
        void testGetSetFirstName()
        {
            publisher.setPublisherName(TEST);
            assertThat(publisher).hasFieldOrPropertyWithValue("publisherName", TEST);
            assertEquals(TEST, publisher.getPublisherName());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            publisher = new Publisher(TEST_ID, TEST_PUBLISHER_NAME);
        }

        @Test
        @DisplayName("initialized values in Publisher()")
        void defaults()
        {
            assertThat(publisher).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(publisher).hasFieldOrPropertyWithValue("publisherName", TEST_PUBLISHER_NAME);
        }

        @Test
        @DisplayName("Equals for class Publisher and hashCode")
        void testEquals()
        {
            assertNotEquals(new Publisher(), publisher);
            Publisher expected = new Publisher(TEST_ID, TEST_PUBLISHER_NAME);
            assertEquals(expected.hashCode(), publisher.hashCode());
            assertEquals(expected, publisher);
        }

        @Test
        @DisplayName("The length of string from Publisher::toString is great than zero")
        void testToString()
        {
            assertTrue(publisher.toString().length() > 0);
        }
    }
}