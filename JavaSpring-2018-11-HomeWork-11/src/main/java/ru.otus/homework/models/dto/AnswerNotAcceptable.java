package ru.otus.homework.models.dto;

public class AnswerNotAcceptable extends Answer
{
    public AnswerNotAcceptable(String response)
    {
        super("Not Acceptable: " + response);
    }
}
