package ru.otus.homework.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.homework.models.DataSet;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@CompoundIndexes({
    @CompoundIndex(name = "person",
        unique = true,
        def = "{'first-name' : 1, 'last-name' : 1}")
})
public class Author implements Serializable, DataSet
{
    static final long serialVersionUID = -1L;

    @Id
    private BigInteger id;

    @Field("first-name")
    private String firstName;

    @Field("last-name")
    private String lastName;
}