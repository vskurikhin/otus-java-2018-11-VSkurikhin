package ru.otus.homework.models.dto;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

public class AnswerCreated extends Answer
{
    public AnswerCreated(HttpServletResponse response, String location, BigInteger id)
    {
        super("Created");
        response.addHeader("Location", location + '/' + id);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
