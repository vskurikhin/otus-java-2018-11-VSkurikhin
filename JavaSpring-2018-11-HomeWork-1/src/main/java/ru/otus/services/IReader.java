package ru.otus.services;

import ru.otus.exeptions.ExceptionIO;
import ru.otus.models.IAnswer;
import ru.otus.models.IQuestion;

import java.util.function.Supplier;

public interface IReader
{
    void read(Supplier<IQuestion> getQuestionBean, Supplier<IAnswer> getAnswerBean) throws ExceptionIO;
}
