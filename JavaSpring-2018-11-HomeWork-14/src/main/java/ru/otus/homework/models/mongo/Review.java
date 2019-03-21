package ru.otus.homework.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Document(collection = "book_reviews")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Review implements Serializable, DataSet
{
    static final long serialVersionUID = -3L;

    @Id
    private BigInteger id;

    private String review;

    @DBRef
    private Book book;
}
