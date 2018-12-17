package ru.otus.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Class QuestionsImpl")
class QuestionsImplTest
{
    QuestionsImpl questions;


    @Test
    @DisplayName("is instantiated with new QuestionsImpl()")
    void isInstantiatedWithNew() {
        new QuestionsImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions() {
            questions = new QuestionsImpl();
        }

        @Test
        @DisplayName("default values in QuestionsImpl()")
        void defaults() {
            assertThat(questions).hasFieldOrPropertyWithValue("activeQuestion", 0);
            assertThat(questions).hasFieldOrPropertyWithValue("score", 0);
            assertThat(questions).hasFieldOrProperty("questions").isNotNull();
            assertTrue(questions.getQuestions().isEmpty());
        }

        @Test
        @DisplayName("Setter and getter for score")
        void testScore()
        {
            questions.setScore(13);
            assertThat(questions).hasFieldOrPropertyWithValue("score", 13);
            assertEquals(13, questions.getScore());
        }

        @Test
        @DisplayName("The method size")
        public void testSize()
        {
            assertEquals(0, questions.size());
            questions.setQuestions(Collections.singletonList(new QuestionImpl()));
            assertEquals(1, questions.size());
        }

        private QuestionImpl[] getTestDataQuestions(QuestionsImpl testDataSet)
        {
            QuestionImpl[] questionImpls = new QuestionImpl[]{ new QuestionImpl(), new QuestionImpl(), new QuestionImpl()};
            questionImpls[0].setQuestion("question0");
            questionImpls[1].setQuestion("question1");
            questionImpls[1].setAnswers(Collections.singletonList(new AnswerImpl()));
            testDataSet.setQuestions(Arrays.asList(questionImpls));

            return questionImpls;
        }

        @Test
        public void testIterate()
        {
            QuestionImpl[] questionsArray = getTestDataQuestions(questions);

            int i = 0;
            Iterator<Question> questionIterator = questions.iterator();
            while (questionIterator.hasNext()) {
                QuestionImpl questionImpl = (QuestionImpl) questionIterator.next();
                assertTrue(questionImpl == questionsArray[i++]);
                assertThat(questions).hasFieldOrPropertyWithValue("activeQuestion", i);
            }
            assertTrue(i > 0);
        }

        @Test
        @DisplayName("Equals for class QuestionsImpl and hashCode")
        public void testEquals()
        {
            QuestionsImpl expected = new QuestionsImpl();
            assertEquals(expected.hashCode(), questions.hashCode());

            getTestDataQuestions(questions);
            getTestDataQuestions(expected);
            assertEquals(expected.hashCode(), questions.hashCode());
            assertTrue(questions.equals(expected));
            assertFalse(questions.equals(null));
            assertFalse(questions.equals(new Object()));
            assertEquals(expected.hashCode(), questions.hashCode());
        }

        @Test
        @DisplayName("The length of string from QuestionsImpl::toString is great than zero")
        public void testToString()
        {
            assertTrue(questions.toString().length() > 0);
        }
    }
}