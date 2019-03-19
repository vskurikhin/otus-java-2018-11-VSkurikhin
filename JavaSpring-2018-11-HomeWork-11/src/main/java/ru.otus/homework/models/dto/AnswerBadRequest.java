package ru.otus.homework.models.dto;

public class AnswerBadRequest extends Answer
{
    public AnswerBadRequest(String response)
    {
        super("Bad Request: " + response);
    }
}
