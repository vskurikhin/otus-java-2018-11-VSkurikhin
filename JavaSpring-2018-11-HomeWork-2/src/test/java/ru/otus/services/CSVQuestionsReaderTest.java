package ru.otus.services;

import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.exeptions.ExceptionIO;
import ru.otus.models.*;
import ru.otus.utils.IOHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.services.MessagesServiceImplTest.mockUpAnnotationConfigApplicationContext;
import static ru.otus.services.MessagesServiceImplTest.mockUpLocaleMsgGet_filename_of_question;
import static ru.otus.utils.IOHelper.getBufferedReaderFromString;

@DisplayName("Class CSVQuestionsReader")
class CSVQuestionsReaderTest
{
    private CSVQuestionsReader reader;

    @Test
    @DisplayName("is instantiated with new QuestionImpl()")
    void isInstantiatedWithNew() {
        new CSVQuestionsReader();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestion()
        {
            reader = new CSVQuestionsReader();
        }

        @Test
        @DisplayName("default values in CSVQuestionsReader()")
        void defaults() {
            assertThat(reader).hasFieldOrPropertyWithValue("filename", "");
            assertThat(reader).hasFieldOrProperty("questions").isNotNull();
        }

        @Test
        @DisplayName("Setter and getter for questions")
        void testAnswers()
        {
            QuestionsImpl questions = new QuestionsImpl();
            QuestionImpl question = new QuestionImpl();
            List<Question> questionList = new ArrayList<>(Collections.singletonList(question));
            questions.setQuestions(questionList);
            reader.setQuestions(questions);
            assertThat(reader).hasFieldOrPropertyWithValue("questions", questions);
            assertEquals(1, reader.getQuestions().size());
        }

        @Test
        @DisplayName("read(null, null) throws ExceptionIO")
        void testNullPointerException()
        {
            assertThrows(NullPointerException.class, () -> reader.read(null, null));
        }
    }

    @Nested
    @DisplayName("when read")
    class WhenRead
    {
        public static final String questEmpty = "";
        public static final String questOne = "\"Q1?\"";
        public static final String questBadLine = "\"Q1?\",\"A1.1\"";
        public static final String questOneLine = "\"Q1?\",\"A1.1\",1,\"A1.2\",2,\"A1.3\",3,\"A1.4\",4,\"A1.5\",5";

        private AnswerFactory getAnswer;
        private QuestionFactory getQuestion;

        @BeforeEach
        void createNewQuestion()
        {
            mockUpAnnotationConfigApplicationContext();
            mockUpLocaleMsgGet_filename_of_question();
            getAnswer = new AnswerFactoryImpl();
            getQuestion = new QuestionFactoryImpl();
            reader = new CSVQuestionsReader(new QuestionsImpl(), "test.csv");
        }

        @Test
        @DisplayName("mockit filename in CSVQuestionsReader()")
        void defaults() {
            assertThat(reader).hasFieldOrPropertyWithValue("filename", "test.csv");
        }

        @Test
        @DisplayName("QuestionsReader::read from empty stream")
        void readEmpty() throws ExceptionIO
        {
            new MockUp<IOHelper>() {
                @Mock
                public BufferedReader getBufferedReader(Class<?> clazz, String resource) {
                    return getBufferedReaderFromString(questEmpty);
                }
            };

            reader.read(getAnswer, getQuestion);
            assertEquals(0, reader.getQuestions().size());
        }

        @Test
        @DisplayName("QuestionsReader::read only question")
        void readOnlyQuestion() throws ExceptionIO
        {
            new MockUp<IOHelper>() {
                @Mock
                public BufferedReader getBufferedReader(Class<?> clazz, String resource) {
                    return getBufferedReaderFromString(questOne);
                }
            };

            reader.read(getAnswer, getQuestion);
            assertEquals(1, reader.getQuestions().size());
        }

        @Test
        @DisplayName("QuestionsReader::read bad line")
        void readBadLine() throws ExceptionIO
        {
            new MockUp<IOHelper>() {
                @Mock
                public BufferedReader getBufferedReader(Class<?> clazz, String resource) {
                    return getBufferedReaderFromString(questBadLine);
                }
            };

            assertThrows(ArrayIndexOutOfBoundsException.class, () -> reader.read(getAnswer, getQuestion));
        }

        @Test
        @DisplayName("QuestionsReader::read one line")
        void readOneLine() throws ExceptionIO
        {
            new MockUp<IOHelper>() {
                @Mock
                public BufferedReader getBufferedReader(Class<?> clazz, String resource) {
                    return getBufferedReaderFromString(questOneLine);
                }
            };

            reader.read(getAnswer, getQuestion);
            assertEquals(1, reader.getQuestions().size());
            QuestionsImpl expected = new QuestionsImpl();
            AnswerImpl[] aa = new AnswerImpl[] {new AnswerImpl(), new AnswerImpl(), new AnswerImpl(), new AnswerImpl(), new AnswerImpl()};
            aa[0].setAnswer("A1.1"); aa[0].setScore(1);
            aa[1].setAnswer("A1.2"); aa[1].setScore(2);
            aa[2].setAnswer("A1.3"); aa[2].setScore(3);
            aa[3].setAnswer("A1.4"); aa[3].setScore(4);
            aa[4].setAnswer("A1.5"); aa[4].setScore(5);
            QuestionImpl q1 = new QuestionImpl();
            q1.setQuestion("Q1?");
            q1.setAnswers(Arrays.asList(aa));
            expected.setQuestions(Collections.singletonList(q1));
            assertThat(reader).hasFieldOrPropertyWithValue("questions", expected);
        }

        @Test
        @DisplayName("QuestionsReader::read throw IOException")
        void readNull() throws ExceptionIO
        {
            new MockUp<IOHelper>() {
                @Mock
                public BufferedReader getBufferedReader(Class<?> clazz, String resource) throws IOException
                {
                    throw new IOException();
                }
            };

            assertThrows(IOException.class, () -> reader.read(getAnswer, getQuestion));
        }
    }
}