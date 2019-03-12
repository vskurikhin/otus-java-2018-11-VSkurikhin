package ru.otus.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("quizFactory")
public class QuizFactoryImpl implements QuizFactory
{
    private AnswerFactory answerFactory;

    private QuestionFactory questionFactory;

    @Autowired
    public QuizFactoryImpl(AnswerFactory answerFactory, QuestionFactory questionFactory)
    {
        this.answerFactory = answerFactory;
        this.questionFactory = questionFactory;
    }

    @Override
    public AnswerFactory getAnswerFactory()
    {
        return answerFactory;
    }

    @Override
    public QuestionFactory getQuestionFactory()
    {
        return questionFactory;
    }
}
