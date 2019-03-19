package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Genre implements Serializable, DataSet
{
    static final Long serialVersionUID = -4L;

    @Id
    private BigInteger id;

    @Indexed(name = "genre", unique = true)
    private String value;
}
