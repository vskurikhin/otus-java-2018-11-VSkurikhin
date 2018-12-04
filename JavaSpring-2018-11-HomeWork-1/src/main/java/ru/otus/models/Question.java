package ru.otus.models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Question implements IQuestion
{
    private String question;

    private List<IAnswer> answers = new LinkedList<>();

    @Override
    public String getQuestion()
    {
        return question;
    }

    @Override
    public void setQuestion(String question)
    {
        this.question = question;
    }

    @Override
    public List<IAnswer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<IAnswer> answers)
    {
        this.answers = answers;
    }

    @Override
    public void addAnswer(IAnswer answer)
    {
        this.answers.add(answer);
    }

    @Override
    public int hashCode()
    {
        return answers.hashCode() + (null != question ? 13 * question.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if ( ! (obj instanceof Question)) {
            return false;
        }
        Question other = (Question) obj;

        return answers.equals(other.answers) && (null != question ? question.equals(other.question) : null == other.question);
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{ question=\"" + question + "\", answers=" + answers + " }";
    }
}
