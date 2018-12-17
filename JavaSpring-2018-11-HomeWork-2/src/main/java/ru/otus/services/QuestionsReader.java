package ru.otus.services;

import ru.otus.exeptions.ExceptionIO;

public interface QuestionsReader
{
    void read(AnswerFactory getQuestionBean, QuestionFactory getAnswerBean) throws ExceptionIO;
}
