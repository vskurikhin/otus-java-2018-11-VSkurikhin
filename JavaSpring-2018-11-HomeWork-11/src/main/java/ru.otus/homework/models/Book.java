package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable, DataSet
{
    static final long serialVersionUID = -2L;

    @Id
    private BigInteger id;

    @Indexed(name = "isbn", unique = true)
    private String isbn;

    private String title;

    @Field("edition-number")
    private int editionNumber;

    private String copyright;

    @DBRef
    private List<Author> authors = new LinkedList<>();

    @DBRef
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        if ( ! (this.id == null && other.id == null)) {
            if (this.id == null) return false;
            if ( ! this.id.equals(other.id)) return false;
        }
        if ( ! this.isbn.equals(other.isbn)) return false;
        if ( ! this.title.equals(other.title)) return false;
        if (this.editionNumber != other.editionNumber) return false;
        if ( ! this.copyright.equals(other.copyright)) return false;
        if (this.authors.size() != other.authors.size()) return false;
        if ( ! this.authors.isEmpty()) {
            if (!this.authors.containsAll(other.authors)) return false;
        }

        return this.genre.equals(other.genre);
    }

    @Override
    public int hashCode() {
        final int PRIME = 13;
        int result = 1;
        result = (result*PRIME) + this.id.hashCode();
        result = (result*PRIME) + this.isbn.hashCode();
        result = (result*PRIME) + this.title.hashCode();
        result = (result*PRIME) + this.editionNumber;
        result = (result*PRIME) + this.copyright.hashCode();
        result = (result*PRIME) + this.authors.stream().mapToInt(Author::hashCode).sum();
        result = (result*PRIME) + this.genre.hashCode();

        return result;
    }
}
