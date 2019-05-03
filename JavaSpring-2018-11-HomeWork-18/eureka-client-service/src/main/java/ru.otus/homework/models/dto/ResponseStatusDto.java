package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseStatusDto
{
    private final String status;

    public ResponseStatusDto()
    {
        status = "";
    }
}
