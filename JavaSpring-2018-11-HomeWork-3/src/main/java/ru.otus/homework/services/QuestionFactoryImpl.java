package ru.otus.homework.services;

import org.springframework.stereotype.Service;
import ru.otus.homework.models.Question;
import ru.otus.homework.models.QuestionImpl;

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
