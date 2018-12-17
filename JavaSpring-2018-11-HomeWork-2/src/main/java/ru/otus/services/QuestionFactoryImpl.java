package ru.otus.services;

import org.springframework.stereotype.Service;
import ru.otus.models.Question;
import ru.otus.models.QuestionImpl;

@Service("questionFactory")
public class QuestionFactoryImpl implements QuestionFactory
{
    @Override
    public Question getObject()
    {
        return new QuestionImpl();
    }

    @Override
    public Class<?> getObjectType()
    {
        return QuestionImpl.class;
    }

    @Override
    public boolean isSingleton()
    {
        return false;
    }
}
