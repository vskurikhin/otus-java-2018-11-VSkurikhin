package ru.otus.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.models.Answer;
import ru.otus.models.Question;
import ru.otus.models.Questions;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class ConsoleTesterTest
{
    private final InputStream systemIn = System.in;
    private final String test = "First" + ConsoleTester.NL
                              + "SurName" + ConsoleTester.NL
                              + "1" + ConsoleTester.NL;
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private PrintStream ps;
    private ByteArrayInputStream testIn;
    private ConsoleTester consoleTester;

    public ConsoleTesterTest()
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
        consoleTester = new ConsoleTester(System.in, ps, null);
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
        Questions expected = new Questions();
        consoleTester.setQuestions(new Questions());
        Assert.assertEquals(expected, consoleTester.getQuestions());
    }

    @Test
    public void testShowQuestion()
    {
        consoleTester.showQuestion("test");
        String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        System.out.println("test = " + test);
        Assert.assertTrue(test.length() > 13);
    }

    @Test
    public void testShowAnswers()
    {
        Answer answer = new Answer();
        answer.setAnswer("test");
        consoleTester.showAnswers(Collections.singletonList(answer));
        String test = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        Assert.assertTrue(test.length() > 6);
    }

    @Test
    public void testReadAnswer()
    {
        Assert.assertEquals(-2, consoleTester.readAnswer(Collections.singletonList(new Answer())));
    }

    @Test
    public void testGetScore()
    {
        consoleTester.setQuestions(new Questions());
        Assert.assertEquals(0, consoleTester.getScore());
    }

    @Test
    public void testRun()
    {
        Answer answer = new Answer();
        answer.setAnswer("test");
        answer.setScore(13);
        Question question = new Question();
        question.setQuestion("test");
        question.setAnswers(Collections.singletonList(answer));
        Questions questions = new Questions();
        questions.addQuestion(question);
        consoleTester.setQuestions(questions);
        consoleTester.run();
        Assert.assertEquals(13, consoleTester.getScore());
    }
}