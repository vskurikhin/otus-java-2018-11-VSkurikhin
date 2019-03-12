package ru.otus.homework.models;

import java.util.Iterator;

public interface Questions extends Iterable<Question>
{
    void addQuestion(Question question);

    int getScore();

    void addScore(int score);

    int size();

    void clear();

    Iterator<Question> iterator();
}
