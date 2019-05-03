package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseCountDto
{
    private final Long count;

    public ResponseCountDto(long count)
    {
        this.count = count;
    }
}
