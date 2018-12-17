package ru.otus.services;

import org.springframework.beans.factory.FactoryBean;
import ru.otus.models.Answer;

public interface AnswerFactory extends FactoryBean<Answer>
{
    Answer getObject();

    Class<?> getObjectType();

    boolean isSingleton();
}
