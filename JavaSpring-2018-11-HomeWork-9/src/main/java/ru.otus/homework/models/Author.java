package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "authors")
public class Author implements Serializable, DataSet
{
    static final long serialVersionUID = -1L;

    @Id
    private String id;

    private String firstName;

    private String lastName;
}
