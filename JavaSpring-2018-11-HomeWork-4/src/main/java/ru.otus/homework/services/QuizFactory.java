package ru.otus.homework.services;

public interface QuizFactory
{
    AnswerFactory getAnswerFactory();

    QuestionFactory getQuestionFactory();
}
