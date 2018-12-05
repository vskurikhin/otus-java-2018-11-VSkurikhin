package ru.otus.models;

import java.util.Iterator;

public interface IQuestions extends Iterable<IQuestion>
{
    void addQuestion(IQuestion question);

    int getScore();

    void addScore(int score);

    int size();

    Iterator<IQuestion> iterator();
}
