package ru.otus.models;

import java.util.List;

public interface IQuestion
{
    String getQuestion();

    void setQuestion(String question);

    List<IAnswer> getAnswers();

    void addAnswer(IAnswer answer);
}
