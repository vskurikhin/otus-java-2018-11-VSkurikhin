package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class QuestionImpl")
class QuestionImplTest
{
    private QuestionImpl question;

    @Test
    @DisplayName("is instantiated with new QuestionImpl()")
    void isInstantiatedWithNew() {
        new QuestionImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion() {
            question = new QuestionImpl();
        }

        @Test
        @DisplayName("default values in AnswerImpl()")
        void defaults() {
            assertThat(question).hasFieldOrPropertyWithValue("question", null);
            assertThat(question).hasFieldOrProperty("answers").isNotNull();
            assertTrue(question.getAnswers().isEmpty());
        }

        @Test
        @DisplayName("Setter and getter for question")
        void testAnswer()
        {
            question.setQuestion("test");
            assertThat(question).hasFieldOrPropertyWithValue("question", "test");
            assertEquals("test", question.getQuestion());
        }

        @Test
        @DisplayName("Setter and getter for answers")
        void testAnswers()
        {
            AnswerImpl answerImpl = new AnswerImpl();
            List<Answer> answers = new ArrayList<>(Collections.singletonList(answerImpl));
            question.setAnswers(answers);
            assertThat(question).hasFieldOrPropertyWithValue("answers", answers);
            assertEquals(1, question.getAnswers().size());
            assertTrue(question.getAnswers().contains(answerImpl));
        }

        @Test
        @DisplayName("Equals for class QuestionImpl and hashCode")
        void testEquals()
        {
            QuestionImpl expected = new QuestionImpl();
            assertEquals(expected.hashCode(), question.hashCode());
            expected.addAnswer(new AnswerImpl());
            question.addAnswer(new AnswerImpl());
            expected.setQuestion("test");
            question.setQuestion("test");
            assertEquals(question, expected);
            assertNotEquals(null, question);
            assertNotEquals(question, new Object());
            assertEquals(expected.hashCode(), question.hashCode());
        }

        @Test
        @DisplayName("The length of string from QuestionImpl::toString is great than zero")
        void testToString()
        {
            assertTrue(question.toString().length() > 0);
        }
    }
}
