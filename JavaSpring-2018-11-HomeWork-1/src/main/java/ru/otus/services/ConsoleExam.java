package ru.otus.services;

import ru.otus.models.ISetOfQuestions;

public class ConsoleExam implements IExam
{
    private ISetOfQuestions setOfQuestions;

    public ISetOfQuestions getSetOfQuestions()
    {
        return setOfQuestions;
    }

    public void setSetOfQuestions(ISetOfQuestions setOfQuestions)
    {
        this.setOfQuestions = setOfQuestions;
    }

    public void run() {

    }

    public int getScore() {
        return setOfQuestions.getScore();
    }
}
