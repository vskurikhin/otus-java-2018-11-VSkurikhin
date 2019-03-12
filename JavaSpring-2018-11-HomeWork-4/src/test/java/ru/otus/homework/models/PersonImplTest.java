package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class PersonImpl")
class PersonImplTest
{
    private PersonImpl person;

    @Test
    @DisplayName("is instantiated with new QuestionImpl()")
    void isInstantiatedWithNew() {
        new PersonImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion()
        {
            person = new PersonImpl("testFirstName", "testSecondName");
        }

        @Test
        @DisplayName("default values in PersonImpl()")
        void defaults()
        {
            assertThat(person).hasFieldOrPropertyWithValue("firstName", "testFirstName");
            assertThat(person).hasFieldOrPropertyWithValue("surName", "testSecondName");
        }

        @Test
        @DisplayName("Setter and getter for firstName")
        void testAnswer()
        {
            person.setFirstName("test");
            assertThat(person).hasFieldOrPropertyWithValue("firstName", "test");
            assertEquals("test", person.getFirstName());
        }

        @Test
        @DisplayName("Setter and getter for surName")
        void testAnswers()
        {
            person.setSurName("test");
            assertThat(person).hasFieldOrPropertyWithValue("surName", "test");
            assertEquals("test", person.getSurName());
        }

        @Test
        @DisplayName("Equals for class PersonImpl and hashCode")
        void testEquals()
        {
            PersonImpl expected = new PersonImpl("testFirstName", "testSecondName");
            assertEquals(expected.hashCode(), person.hashCode());
            assertNotEquals(new PersonImpl(), person);
            assertEquals(expected, person);
        }

        @Test
        @DisplayName("The length of string from PersonImpl::toString is great than zero")
        void testToString()
        {
            assertTrue(person.toString().length() > 0);
        }
    }
}