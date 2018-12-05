package ru.otus.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class QuestionsTest
{
    private Questions questions;

    @Before
    public void setUp() throws Exception
    {
        questions = new Questions();
    }

    @After
    public void tearDown() throws Exception
    {
        questions = null;
    }

    @Test
    public void testQuestions()
    {
        questions.setQuestions(new LinkedList<>());
        Assert.assertEquals(new LinkedList<>(), questions.getQuestions());
    }

    @Test
    public void testScore()
    {
        questions.setScore(13);
        Assert.assertEquals(13, questions.getScore());
        questions.addScore(4);
        Assert.assertEquals(17, questions.getScore());
    }

    @Test
    public void testSize()
    {
        Assert.assertEquals(0, questions.size());
        questions.setQuestions(Collections.singletonList(new Question()));
        Assert.assertEquals(1, questions.size());
    }


    private Question[] getTestDataQuestions(Questions testDataSet)
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
        Question[] questions = getTestDataQuestions(this.questions);

        int i = 0;
        Iterator<IQuestion> questionIterator = this.questions.iterator();
        while (questionIterator.hasNext()) {
            Question question = (Question) questionIterator.next();
            Assert.assertTrue(question == questions[i++]);
        }
        Assert.assertTrue(i > 0);
    }
    @Test
    public void testEquals()
    {
        Questions expected = new Questions();
        Assert.assertEquals(expected.hashCode(), questions.hashCode());

        getTestDataQuestions(questions);
        getTestDataQuestions(expected);
        Assert.assertEquals(expected.hashCode(), questions.hashCode());
        Assert.assertTrue(questions.equals(expected));
        Assert.assertFalse(questions.equals(null));
        Assert.assertFalse(questions.equals(new Object()));
        Assert.assertEquals(expected.hashCode(), questions.hashCode());
    }

    @Test
    public void testToString()
    {
        Assert.assertTrue(questions.toString().length() > 0);
    }
}