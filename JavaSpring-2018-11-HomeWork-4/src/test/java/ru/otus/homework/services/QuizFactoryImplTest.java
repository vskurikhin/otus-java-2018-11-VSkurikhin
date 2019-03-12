package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class QuizFactoryImpl")
class QuizFactoryImplTest
{
    private QuizFactoryImpl quizFactory;

    private AnswerFactory answerFactory = new AnswerFactoryImpl();

    private QuestionFactory questionFactory = new QuestionFactoryImpl();


    @Test
    @DisplayName("is instantiated with new QuizFactoryImpl()")
    void isInstantiatedWithNew()
    {
        new QuestionFactoryImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuizFactoryImpl()
        {
            quizFactory = new QuizFactoryImpl(answerFactory, questionFactory);
        }

        @Test
        @DisplayName("The method getAnswerFactory")
        void testGetAnswerFactory()
        {
            assertEquals(answerFactory, quizFactory.getAnswerFactory());
        }

        @Test
        @DisplayName("The method getQuestionFactory")
        void testGetQuestionFactory()
        {
            assertEquals(questionFactory, quizFactory.getQuestionFactory());
        }
    }
}