package ru.otus.services;

import org.springframework.beans.factory.FactoryBean;
import ru.otus.models.Question;

public interface QuestionFactory extends FactoryBean<Question>
{
    Question getObject();

    Class<?> getObjectType();

    boolean isSingleton();
}
