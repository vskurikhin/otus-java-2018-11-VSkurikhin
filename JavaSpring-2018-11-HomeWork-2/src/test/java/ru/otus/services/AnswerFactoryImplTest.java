package ru.otus.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.models.AnswerImpl;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class CSVQuestionsReader")
class AnswerFactoryImplTest
{
    private AnswerFactoryImpl answerFactory;

    @Test
    @DisplayName("is instantiated with new AnswerFactoryImpl()")
    void isInstantiatedWithNew() {
        new AnswerFactoryImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion()
        {
            answerFactory = new AnswerFactoryImpl();
        }

        @Test
        @DisplayName("AnswerFactoryImpl::getObject()")
        void getObject()
        {
            assertEquals(new AnswerImpl(), answerFactory.getObject());
        }

        @Test
        @DisplayName("AnswerFactoryImpl::getObjectType()")
        void getObjectType()
        {
            assertEquals(AnswerImpl.class, answerFactory.getObjectType());
        }

        @Test
        @DisplayName("Answer is not singleton")
        void isSingleton()
        {
            assertFalse(answerFactory.isSingleton());
        }
    }
}