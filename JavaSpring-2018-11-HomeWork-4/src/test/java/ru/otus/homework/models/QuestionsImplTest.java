package ru.otus.homework.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class QuestionsImpl")
class QuestionsImplTest
{
    private QuestionsImpl questions;

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
        @DisplayName("The method addQuestion")
        void testAddQuestion()
        {
            assertEquals(0, questions.size());
            questions.addQuestion(new QuestionImpl());
            assertEquals(1, questions.size());
        }

        @Test
        @DisplayName("The method addScore")
        void testAddScore()
        {
            assertEquals(0, questions.getScore());
            questions.addScore(13);
            assertEquals(13, questions.getScore());
            questions.addScore(29);
            assertEquals(42, questions.getScore());
        }

        @Test
        @DisplayName("The method size")
        void testSize()
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
        void testIterate()
        {
            QuestionImpl[] questionsArray = getTestDataQuestions(questions);

            int i = 0;
            for (Question question : questions) {
                QuestionImpl questionImpl = (QuestionImpl) question;
                assertSame(questionImpl, questionsArray[i++]);
                assertThat(questions).hasFieldOrPropertyWithValue("activeQuestion", i);
            }
            assertTrue(i > 0);
        }

        @Test
        void testClear()
        {
            assertEquals(0, questions.size());
            questions.addQuestion(new QuestionImpl());
            assertEquals(1, questions.size());
            questions.addScore(1);
            questions.clear();
            assertEquals(0, questions.size());
            assertEquals(0, questions.getScore());
        }

        @Test
        @DisplayName("Equals for class QuestionsImpl and hashCode")
        void testEquals()
        {
            QuestionsImpl expected = new QuestionsImpl();
            assertEquals(expected.hashCode(), questions.hashCode());

            getTestDataQuestions(questions);
            getTestDataQuestions(expected);
            assertEquals(expected.hashCode(), questions.hashCode());
            assertEquals(questions, expected);
            assertNotEquals(null, questions);
            assertNotEquals(questions, new Object());
            assertEquals(expected.hashCode(), questions.hashCode());
        }

        @Test
        @DisplayName("The length of string from QuestionsImpl::toString is great than zero")
        void testToString()
        {
            assertTrue(questions.toString().length() > 0);
        }
    }
}
