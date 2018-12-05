package ru.otus.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnswerTest
{
    private Answer answer;

    @Before
    public void setUp() throws Exception
    {
        answer = new Answer();
    }

    @After
    public void tearDown() throws Exception
    {
        answer = null;
    }

    @Test
    public void testAnswer()
    {
        answer.setAnswer("test");
        Assert.assertEquals("test", answer.getAnswer());
    }

    @Test
    public void testScore()
    {
        answer.setScore(13);
        Assert.assertEquals(13, answer.getScore());
    }

    @Test
    public void testEquals()
    {
        Answer expected = new Answer();
        Assert.assertTrue(answer.equals(expected));
        expected.setAnswer("test");
        expected.setScore(13);
        answer.setAnswer("test");
        answer.setScore(13);
        Assert.assertTrue(answer.equals(expected));
        Assert.assertFalse(answer.equals(null));
        Assert.assertFalse(answer.equals(new Object()));
        Assert.assertEquals(expected.hashCode(), answer.hashCode());
    }

    @Test
    public void testToString()
    {
        Assert.assertTrue(answer.toString().length() > 0);
    }
}