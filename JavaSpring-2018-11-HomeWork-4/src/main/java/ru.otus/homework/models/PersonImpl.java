package ru.otus.homework.models;

import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PersonImpl implements Person
{
    private String firstName;

    private String surName;
}
