package ru.otus.models;

import java.util.Iterator;

public interface ISetOfQuestions
{
    void addQuestion(IQuestion question);

    int getScore();

    void addScore(int score);

    int size();

    Iterator<IQuestion> iterator();
}
