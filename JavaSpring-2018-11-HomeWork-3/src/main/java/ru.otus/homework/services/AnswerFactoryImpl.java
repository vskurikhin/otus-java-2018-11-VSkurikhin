package ru.otus.homework.services;

import org.springframework.stereotype.Service;
import ru.otus.homework.models.Answer;
import ru.otus.homework.models.AnswerImpl;

@Service("answerFactory")
public class AnswerFactoryImpl implements AnswerFactory
{
    @Override
    public Answer getObject()
    {
        return new AnswerImpl();
    }

    @Override
    public Class<?> getObjectType()
    {
        return AnswerImpl.class;
    }

    @Override
    public boolean isSingleton()
    {
        return false;
    }
}
