package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.models.Answer;
import ru.otus.models.AnswerImpl;

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
