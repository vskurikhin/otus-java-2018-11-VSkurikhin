package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.AnswerImpl;
import ru.otus.homework.models.QuestionImpl;
import ru.otus.homework.models.Questions;
import ru.otus.homework.models.QuestionsImpl;
import ru.otus.outside.exeptions.EmptyResourceRuntimeException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.homework.services.CSVQuestionsReaderTest.WhenRead.questEmpty;
import static ru.otus.homework.services.MessagesServiceImplTest.DEFAULT_SLOCALE;
import static ru.otus.homework.services.MessagesServiceImplTest.MESSAGE_SOURCE;
import static ru.otus.outside.utils.IOHelperTestHelper.*;

@DisplayName("Class ConsoleQuizExecutor")
class ConsoleQuizExecutorTest
{
    public static final String TEST = "test";

    private final IOService ios = new IOServiceSystem();

    private final InputStream saveSystemIn = System.in;

    private final Questions questions = new QuestionsImpl();

    private final MessagesService msg = new MessagesServiceImpl(DEFAULT_SLOCALE, MESSAGE_SOURCE);

    private final AnswerFactory answerFactory = new AnswerFactoryImpl();

    private final QuestionFactory questionFactory = new QuestionFactoryImpl();

    private ConsoleQuizExecutor executor;

    @Test
    @DisplayName("is instantiated with new ConsoleQuizExecutor()")
    void isInstantiatedWithNew() {
        mockUp_IOHelper_getBufferedReader(questEmpty);
        new ConsoleQuizExecutor(ios, msg,  questions, new CSVQuestionsReader(), answerFactory, questionFactory);
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private PrintStream ps;
        private IOService ioService;
        private Questions questions;

        @BeforeEach
        void createNewQuestion()
        {
            try {
                ps = new PrintStream(baos, true, "UTF-8");
                ioService = new IOServiceSystem(saveSystemIn, ps);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            questions = new QuestionsImpl();
            mockUp_IOHelper_getBufferedReader(questEmpty);
            executor = new ConsoleQuizExecutor(
                ioService, msg,  questions, new CSVQuestionsReader(), answerFactory, questionFactory
            );
        }

        @Test
        @DisplayName("default values saveSystemIn ConsoleQuizExecutor()")
        void defaults()
        {
            assertThat(executor).hasFieldOrProperty("scanner").isNotNull();
            assertThat(executor).hasFieldOrPropertyWithValue("out", ps);
            assertThat(executor).hasFieldOrPropertyWithValue("questions", questions);
            assertThat(executor).hasFieldOrPropertyWithValue("msg", msg);
        }

        @Test
        @DisplayName("Setter and getter for questions")
        void testSetGetQuestions()
        {
            QuestionsImpl expected = new QuestionsImpl();
            executor.setQuestions(new QuestionsImpl());
            assertEquals(expected, executor.getQuestions());
        }

        @Test
        @DisplayName("Setter and getter for questions")
        void testGetScore()
        {
            executor.setQuestions(new QuestionsImpl());
            assertEquals(0, executor.getScore());
        }

        @Test
        @DisplayName("Show question")
        void testShowQuestion()
        {
            executor.showQuestion(TEST);
            String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertTrue(test.length() > TEST.length());
        }

        @Test
        @DisplayName("Show answers")
        void testShowAnswers()
        {
            AnswerImpl answerImpl = new AnswerImpl();
            answerImpl.setAnswer(TEST);
            executor.showAnswers(Collections.singletonList(answerImpl));
            String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertTrue(test.length() > TEST.length());
        }
    }

    private ConsoleQuizExecutor createNewConsoleQuizExecutor()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = null;
        try {
            ps = new PrintStream(baos, true, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessagesServiceImpl msg = new MessagesServiceImpl(
            "en_US", MessagesServiceImplTest.createTestMessageSource()
        );
        IOService ioService = new IOServiceSystem(System.in, ps);

        return new ConsoleQuizExecutor(
            ioService, msg,  questions, new CSVQuestionsReader(), answerFactory, questionFactory
        );
    }

    @Nested
    @DisplayName("when readAnswer")
    class WhenReadAnswer
    {
        @BeforeEach
        void createNewExecutor()
        {
            executor = createNewConsoleQuizExecutor();
        }

        @Test
        @DisplayName("run1")
        void testRun1()
        {
            mockUp_Scanner_nextInt(2);
            AnswerImpl answerImpl = new AnswerImpl();
            answerImpl.setAnswer(TEST);
            assertEquals(-1, executor.readAnswer(Collections.singletonList(answerImpl)));
        }

        @Test
        @DisplayName("run throw InputMismatchException")
        void testRunInputMismatchException()
        {
            mockUp_Scanner_nextInt_InputMismatchException();
            assertEquals(-2, executor.readAnswer(null));
        }

        @Test
        @DisplayName("run throw NoSuchElementException")
        void testRunInputNoSuchElementException()
        {
            mockUp_Scanner_nextInt_NoSuchElementException();
            assertEquals(-3, executor.readAnswer(null));
        }
    }

    @Nested
    @DisplayName("when run")
    class WhenRun
    {
        private final String INPUT = "First" + ConsoleQuizExecutor.NL
                                   + "SurName" + ConsoleQuizExecutor.NL
                                   + "1" + ConsoleQuizExecutor.NL;

        private void provideInput(String data) {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
        }

        @BeforeEach
        void createNewExecutor()
        {
            provideInput(INPUT);
            executor = createNewConsoleQuizExecutor();
        }

        @Test
        @DisplayName("run throw EmptyResourceRuntimeException")
        void testRunEmptyResourceRuntimeException()
        {
            QuestionsImpl questionsImpl = new QuestionsImpl();
            executor.setQuestions(questionsImpl);
            assertThrows(EmptyResourceRuntimeException.class, () -> executor.run());
            System.setIn(saveSystemIn);
        }

        @Test
        @DisplayName("run")
        void testRun()
        {
            AnswerImpl answerImpl = new AnswerImpl();
            answerImpl.setAnswer("test");
            answerImpl.setScore(13);
            QuestionImpl questionImpl = new QuestionImpl();
            questionImpl.setQuestion("test");
            questionImpl.setAnswers(Collections.singletonList(answerImpl));
            QuestionsImpl questionsImpl = new QuestionsImpl();
            questionsImpl.addQuestion(questionImpl);
            executor.setQuestions(questionsImpl);
            executor.run();
            System.setIn(saveSystemIn);
            assertEquals(13, executor.getScore());
        }
    }
}