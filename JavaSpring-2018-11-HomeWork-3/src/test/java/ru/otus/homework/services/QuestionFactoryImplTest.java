package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.QuestionImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class QuestionFactoryImpl")
class QuestionFactoryImplTest
{
    QuestionFactoryImpl questionFactory;

    @Test
    @DisplayName("is instantiated with new QuestionFactoryImpl()")
    void isInstantiatedWithNew()
    {
        new QuestionFactoryImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewAnswer()
        {
            questionFactory = new QuestionFactoryImpl();
        }

        @Test
        @DisplayName("The method getObject")
        void testGetObject()
        {
            assertEquals(new QuestionImpl(), questionFactory.getObject());
        }

        @Test
        @DisplayName("The method getObjectType")
        void testGetObjectType()
        {
            assertEquals(QuestionImpl.class, questionFactory.getObjectType());
        }

        @Test
        @DisplayName("The method isSingleton")
        void testIsSingleton()
        {
            assertFalse(questionFactory.isSingleton());
        }
    }
}