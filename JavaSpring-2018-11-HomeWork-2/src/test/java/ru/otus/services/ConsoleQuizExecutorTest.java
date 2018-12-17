package ru.otus.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.exeptions.ExceptionEmptyResource;
import ru.otus.models.AnswerImpl;
import ru.otus.models.QuestionImpl;
import ru.otus.models.QuestionsImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static ru.otus.services.MessagesServiceImplTest.mockUpLocaleMsg;

public class ConsoleQuizExecutorTest
{
    private final InputStream systemIn = System.in;
    private final String test = "First" + ConsoleQuizExecutor.NL
                              + "SurName" + ConsoleQuizExecutor.NL
                              + "1" + ConsoleQuizExecutor.NL;
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private PrintStream ps;
    private ByteArrayInputStream testIn;
    private ConsoleQuizExecutor consoleTester;

    public ConsoleQuizExecutorTest()
    {
        try {
            ps = new PrintStream(baos, true, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Before
    public void setUp() throws Exception
    {
        provideInput(test);
        mockUpLocaleMsg();
        consoleTester = new ConsoleQuizExecutor(System.in, ps, null, new MessagesServiceImpl("en_US", null));
    }

    @After
    public void tearDown() throws Exception
    {
        System.setIn(systemIn);
        consoleTester = null;
    }

    @Test
    public void testSetOfQuestions()
    {
        QuestionsImpl expected = new QuestionsImpl();
        consoleTester.setQuestions(new QuestionsImpl());
        Assert.assertEquals(expected, consoleTester.getQuestions());
    }

    @Test
    public void testShowQuestion()
    {
        consoleTester.showQuestion("test");
        String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        System.out.println("test = " + test);
        Assert.assertTrue(test.length() > 9);
    }

    @Test
    public void testShowAnswers()
    {
        AnswerImpl answerImpl = new AnswerImpl();
        answerImpl.setAnswer("test");
        consoleTester.showAnswers(Collections.singletonList(answerImpl));
        String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        Assert.assertTrue(test.length() > 6);
    }

    @Test
    public void testReadAnswer()
    {
        Assert.assertEquals(-2, consoleTester.readAnswer(Collections.singletonList(new AnswerImpl())));
    }

    @Test
    public void testGetScore()
    {
        consoleTester.setQuestions(new QuestionsImpl());
        Assert.assertEquals(0, consoleTester.getScore());
    }

    @Test
    public void testRun() throws ExceptionEmptyResource
    {

        AnswerImpl answerImpl = new AnswerImpl();
        answerImpl.setAnswer("test");
        answerImpl.setScore(13);
        QuestionImpl questionImpl = new QuestionImpl();
        questionImpl.setQuestion("test");
        questionImpl.setAnswers(Collections.singletonList(answerImpl));
        QuestionsImpl questionsImpl = new QuestionsImpl();
        questionsImpl.addQuestion(questionImpl);
        consoleTester.setQuestions(questionsImpl);
        consoleTester.run();
        Assert.assertEquals(13, consoleTester.getScore());
    }
}