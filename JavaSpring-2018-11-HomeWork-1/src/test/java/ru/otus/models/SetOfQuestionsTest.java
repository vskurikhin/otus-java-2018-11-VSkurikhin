package ru.otus.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class SetOfQuestionsTest
{
    private SetOfQuestions setOfQuestions;

    @Before
    public void setUp() throws Exception
    {
        setOfQuestions = new SetOfQuestions();
    }

    @After
    public void tearDown() throws Exception
    {
        setOfQuestions = null;
    }

    @Test
    public void testQuestions()
    {
        setOfQuestions.setQuestions(new LinkedList<>());
        Assert.assertEquals(new LinkedList<>(), setOfQuestions.getQuestions());
    }

    @Test
    public void testScore()
    {
        setOfQuestions.setScore(13);
        Assert.assertEquals(13, setOfQuestions.getScore());
        setOfQuestions.addScore(4);
        Assert.assertEquals(17, setOfQuestions.getScore());
    }

    @Test
    public void testSize()
    {
        Assert.assertEquals(0, setOfQuestions.size());
        setOfQuestions.setQuestions(Collections.singletonList(new Question()));
        Assert.assertEquals(1, setOfQuestions.size());
    }


    private Question[] getTestDataQuestions(SetOfQuestions testDataSet)
    {
        Question[] questions = new Question[]{ new Question(), new Question(), new Question()};
        questions[0].setQuestion("question0");
        questions[1].setQuestion("question1");
        questions[1].setAnswers(Collections.singletonList(new Answer()));
        testDataSet.setQuestions(Arrays.asList(questions));

        return questions;
    }

    @Test
    public void testIterate()
    {
        Question[] questions = getTestDataQuestions(setOfQuestions);

        int i = 0;
        Iterator<IQuestion> questionIterator = setOfQuestions.iterator();
        while (questionIterator.hasNext()) {
            Question question = (Question) questionIterator.next();
            Assert.assertTrue(question == questions[i++]);
        }
        Assert.assertTrue(i > 0);
    }
    @Test
    public void testEquals()
    {
        SetOfQuestions expected = new SetOfQuestions();
        Assert.assertEquals(expected.hashCode(), setOfQuestions.hashCode());

        getTestDataQuestions(setOfQuestions);
        getTestDataQuestions(expected);
        Assert.assertEquals(expected.hashCode(), setOfQuestions.hashCode());
        Assert.assertTrue(setOfQuestions.equals(expected));
        Assert.assertFalse(setOfQuestions.equals(null));
        Assert.assertFalse(setOfQuestions.equals(new Object()));
        Assert.assertEquals(expected.hashCode(), setOfQuestions.hashCode());
    }

    @Test
    public void testToString()
    {
        Assert.assertTrue(setOfQuestions.toString().length() > 0);
    }
}