package ru.otus.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.models.AnswerImpl;
import ru.otus.models.QuestionImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Class CSVQuestionsReader")
class QuestionFactoryImplTest
{
    private QuestionFactoryImpl questionFactory;

    @Test
    @DisplayName("is instantiated with new QuestionFactoryImpl()")
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
            questionFactory = new QuestionFactoryImpl();
        }

        @Test
        @DisplayName("QuestionFactoryImpl::getObject()")
        void getObject()
        {
            assertEquals(new QuestionImpl(), questionFactory.getObject());
        }

        @Test
        @DisplayName("QuestionFactoryImpl::getObjectType()")
        void getObjectType()
        {
            assertEquals(QuestionImpl.class, questionFactory.getObjectType());
        }

        @Test
        @DisplayName("QuestionImpl is not singleton")
        void isSingleton()
        {
            assertFalse(questionFactory.isSingleton());
        }
    }
}