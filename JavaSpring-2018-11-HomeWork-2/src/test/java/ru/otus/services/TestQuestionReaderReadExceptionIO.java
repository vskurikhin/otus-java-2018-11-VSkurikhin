package ru.otus.services;

import ru.otus.exeptions.ExceptionIO;

import java.io.IOException;

public class TestQuestionReaderReadExceptionIO implements QuestionsReader
{
    @Override
    public void read(AnswerFactory getQuestionBean, QuestionFactory getAnswerBean) throws ExceptionIO
    {
        throw new ExceptionIO(new IOException());
    }
}
