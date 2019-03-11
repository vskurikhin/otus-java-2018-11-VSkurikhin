package ru.otus.homework.services;

import org.springframework.beans.factory.FactoryBean;
import ru.otus.homework.models.Question;

public interface QuestionFactory extends FactoryBean<Question>
{
    Question getObject();

    Class<?> getObjectType();

    boolean isSingleton();
}
