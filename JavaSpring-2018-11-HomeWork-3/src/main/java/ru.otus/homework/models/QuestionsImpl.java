package ru.otus.homework.models;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Component("questions")
public class QuestionsImpl implements Questions
{
    private List<Question> questions = new LinkedList<>();

    private int activeQuestion = 0;

    private int score;

    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }

    @Override
    public void addQuestion(Question question)
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
    public Iterator<Question> iterator()
    {
        return new IteratorQuestion(this);
    }

    private class IteratorQuestion implements Iterator<Question>
    {
        private QuestionsImpl setOfQuestionsImpl;

        public IteratorQuestion(QuestionsImpl setOfQuestionsImpl)
        {
            this.setOfQuestionsImpl = setOfQuestionsImpl;
        }

        @Override
        public boolean hasNext()
        {
            return setOfQuestionsImpl.questions.size() > setOfQuestionsImpl.activeQuestion;
        }

        @Override
        public Question next()
        {
            int returnIndex = setOfQuestionsImpl.activeQuestion++;

            return setOfQuestionsImpl.questions.get(returnIndex);
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
        if ( ! (obj instanceof QuestionsImpl)) {
            return false;
        }
        QuestionsImpl other = (QuestionsImpl) obj;

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
