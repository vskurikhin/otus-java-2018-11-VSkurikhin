package ru.otus.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SetOfQuestions implements ISetOfQuestions, Iterable<IQuestion>
{
    private List<IQuestion> questions = new LinkedList<>();

    private int activeQuestion = 0;

    private int score;

    public List<IQuestion> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<IQuestion> questions)
    {
        this.questions = questions;
    }

    @Override
    public void addQuestion(IQuestion question)
    {
        this.questions.add(question);
    }

    @Override
    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public void addScore(int score)
    {
        this.score += score;
    }

    @Override
    public int size() {
        return questions.size();
    }

    @Override
    public Iterator<IQuestion> iterator()
    {
        return new IteratorQuestion(this);
    }

    private class IteratorQuestion implements Iterator<IQuestion>
    {
        private SetOfQuestions setOfQuestions;

        public IteratorQuestion(SetOfQuestions setOfQuestions)
        {
            this.setOfQuestions = setOfQuestions;
        }

        @Override
        public boolean hasNext()
        {
            return setOfQuestions.questions.size() > setOfQuestions.activeQuestion;
        }

        @Override
        public IQuestion next()
        {
            int returnIndex = setOfQuestions.activeQuestion++;

            return setOfQuestions.questions.get(returnIndex);
        }
    }

    @Override
    public int hashCode()
    {
        return questions.hashCode() + 13 * activeQuestion + 101 * score;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if ( ! (obj instanceof SetOfQuestions)) {
            return false;
        }
        SetOfQuestions other = (SetOfQuestions) obj;

        return questions.equals(other.questions) && activeQuestion == other.activeQuestion && score == other.score;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "{" +
            " questions=\"" + questions +
            "\", activeQuestion=" + activeQuestion +
            ", score=" + score +
            '}';
    }
}
