package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.AnswerImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class AnswerFactoryImpl")
class AnswerFactoryImplTest
{
    AnswerFactoryImpl answerFactory;

    @Test
    @DisplayName("is instantiated with new AnswerFactoryImpl()")
    void isInstantiatedWithNew()
    {
        new AnswerFactoryImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewAnswer()
        {
            answerFactory = new AnswerFactoryImpl();
        }

        @Test
        @DisplayName("The method getObject")
        void testGetObject()
        {
            assertEquals(new AnswerImpl(), answerFactory.getObject());
        }

        @Test
        @DisplayName("The method getObjectType")
        void testGetObjectType()
        {
            assertEquals(AnswerImpl.class, answerFactory.getObjectType());
        }

        @Test
        @DisplayName("The method isSingleton")
        void testIsSingleton()
        {
            assertFalse(answerFactory.isSingleton());
        }
    }
}