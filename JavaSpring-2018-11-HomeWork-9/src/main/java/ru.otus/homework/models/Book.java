package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "books")
public class Book implements Serializable, DataSet
{
    static final long serialVersionUID = -2L;

    @Id
    private String id;

    private String isbn;

    private String title;

    private int editionNumber;

    private String copyright;

    @DBRef
    private List<Author> authors = new LinkedList<>();

    @DBRef
    private List<Genre> genres = new LinkedList<>();
}
