package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book implements Serializable, DataSet
{
    static final long serialVersionUID = -2L;

    private long id;

    private String isbn;

    private String title;

    private int editionNumber;

    private String copyright;

    private int year;

    private List<Author> authors = new LinkedList<>();

    private Genre genre;
}
