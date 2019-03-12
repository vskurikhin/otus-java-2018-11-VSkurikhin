package ru.otus.homework.services;

import org.springframework.beans.factory.FactoryBean;
import ru.otus.homework.models.Answer;

public interface AnswerFactory extends FactoryBean<Answer>
{
    Answer getObject();

    Class<?> getObjectType();

    boolean isSingleton();
}
