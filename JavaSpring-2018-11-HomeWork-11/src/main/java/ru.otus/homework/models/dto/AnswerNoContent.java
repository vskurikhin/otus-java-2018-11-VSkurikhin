package ru.otus.homework.models.dto;

public class AnswerNoContent extends Answer
{
    public AnswerNoContent(String response)
    {
        super("No content: " + response);
    }
}
