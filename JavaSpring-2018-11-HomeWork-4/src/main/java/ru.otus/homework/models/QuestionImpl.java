package ru.otus.homework.models;

import java.util.LinkedList;
import java.util.List;

public class QuestionImpl implements Question
{
    private String question;

    private List<Answer> answers = new LinkedList<>();

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
    public List<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<Answer> answers)
    {
        this.answers = answers;
    }

    @Override
    public void addAnswer(Answer answer)
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
        if ( ! (obj instanceof QuestionImpl)) {
            return false;
        }
        QuestionImpl other = (QuestionImpl) obj;

        return answers.equals(other.answers) && (null != question ? question.equals(other.question) : null == other.question);
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{ question=\"" + question + "\", answers=" + answers + " }";
    }
}
