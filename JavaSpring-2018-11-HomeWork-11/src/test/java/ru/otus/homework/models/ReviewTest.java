package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.TestData.*;

@DisplayName("Class Review")
class ReviewTest
{
    private Review review;

    @Test
    @DisplayName("is instantiated with new Review()")
    void isInstantiatedWithNew() {
        new Review();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            review = new Review();
        }

        @Test
        @DisplayName("default values in Publisher()")
        void defaults()
        {
            assertThat(review).hasFieldOrPropertyWithValue("id", null);
            assertThat(review).hasFieldOrPropertyWithValue("review", null);
        }

        @Test
        @DisplayName("Setter and getter for publisherName")
        void testGetSetComment()
        {
            review.setReview(TEST);
            assertThat(review).hasFieldOrPropertyWithValue("review", TEST);
            assertEquals(TEST, review.getReview());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            review = new Review(TEST_BID, TEST_COMMENT_NAME, null);
        }

        @Test
        @DisplayName("initialized values in Review()")
        void defaults()
        {
            assertThat(review).hasFieldOrPropertyWithValue("id", TEST_BID);
            assertThat(review).hasFieldOrPropertyWithValue("review", TEST_COMMENT_NAME);
        }

        @Test
        @DisplayName("Equals for class Review and hashCode")
        void testEquals()
        {
            assertNotEquals(new Review(), review);
            Review expected = new Review(TEST_BID, TEST_COMMENT_NAME, null);
            assertEquals(expected.hashCode(), review.hashCode());
            assertEquals(expected, review);
        }

        @Test
        @DisplayName("The length of string from Review::toString is great than zero")
        void testToString()
        {
            assertTrue(review.toString().length() > 0);
        }
    }
}