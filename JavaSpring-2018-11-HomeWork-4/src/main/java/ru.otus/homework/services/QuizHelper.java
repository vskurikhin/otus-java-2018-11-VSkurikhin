package ru.otus.homework.services;

import ru.otus.homework.models.Person;

public interface QuizHelper
{
    Person getPerson();

    void clear();

    void questionsRead();

    String register();

    String answer(int number);

    boolean isNotRegistered();

    boolean isStarted();
}
