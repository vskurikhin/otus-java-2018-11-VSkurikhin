package ru.otus.models;

import java.util.Iterator;

public interface Questions extends Iterable<Question>
{
    void addQuestion(Question question);

    int getScore();

    void addScore(int score);

    int size();

    Iterator<Question> iterator();
}
