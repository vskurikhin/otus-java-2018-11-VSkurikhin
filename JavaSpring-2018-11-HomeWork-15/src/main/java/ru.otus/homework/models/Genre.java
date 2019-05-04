package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Genre implements Serializable, DataSet
{
    static final long serialVersionUID = -4L;

    private long id;

    private String value;
}
