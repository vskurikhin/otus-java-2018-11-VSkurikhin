package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class IOServiceSystem")
class IOServiceSystemTest
{
    private IOServiceSystem ios;

    @Test
    @DisplayName("is instantiated with new IOServiceSystem()")
    void isInstantiatedWithNew()
    {
        new IOServiceSystem();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            ios = new IOServiceSystem(System.in, System.out);
        }

        @Test
        @DisplayName("default values in IOServiceSystem")
        void defaults()
        {
            assertThat(ios).hasFieldOrPropertyWithValue("in", System.in);
            assertThat(ios).hasFieldOrPropertyWithValue("out", System.out);
        }

        @Test
        @DisplayName("Getter for in")
        void testGetIn()
        {
            assertEquals(System.in, ios.getIn());
        }

        @Test
        @DisplayName("Getter for out")
        void testGetOut()
        {
            assertEquals(System.out, ios.getOut());
        }
    }
}