package ru.otus.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuestionTest
{
    private Question question;

    @Before
    public void setUp() throws Exception
    {
        question = new Question();
    }

    @After
    public void tearDown() throws Exception
    {
        question = null;
    }

    @Test
    public void testQuestion()
    {
        question.setQuestion("test");
        Assert.assertEquals("test", question.getQuestion());
    }


    @Test
    public void testAnswersEmptyArray()
    {
        List<Answer> expected0 = new ArrayList<>();
        Assert.assertEquals(expected0, question.getAnswers());
        List<Answer> expected1 = new LinkedList<>();
        question.setAnswers(new LinkedList());
        Assert.assertEquals(expected1, question.getAnswers());
    }

    @Test
    public void testAnswers()
    {
        Question expected = new Question();
        expected.addAnswer(new Answer());
        // Answer[] answers = new Answer[]{ new Answer() };
        question.addAnswer(new Answer());
        Assert.assertEquals(expected, question);
    }

    @Test
    public void testEquals()
    {
        Question expected = new Question();
        Assert.assertEquals(expected.hashCode(), question.hashCode());
        expected.addAnswer(new Answer());
        question.addAnswer(new Answer());
        expected.setQuestion("test");
        question.setQuestion("test");
        Assert.assertTrue(question.equals(expected));
        Assert.assertFalse(question.equals(null));
        Assert.assertFalse(question.equals(new Object()));
        Assert.assertEquals(expected.hashCode(), question.hashCode());
    }

    @Test
    public void testToString()
    {
        Assert.assertTrue(question.toString().length() > 0);
    }
}