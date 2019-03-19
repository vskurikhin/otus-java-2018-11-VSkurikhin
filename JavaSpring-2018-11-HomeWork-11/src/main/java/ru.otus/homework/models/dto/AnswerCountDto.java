package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerCountDto
{
    private final Long count;

    public AnswerCountDto(long count)
    {
        this.count = count;
    }
}
