package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book implements Serializable, DataSet
{
    static final long serialVersionUID = -2L;

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String isbn;

    @Column
    private String title;

    @Column(name = "edition_number")
    private int editionNumber;

    @Column
    private String copyright;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "author_isbn",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new LinkedList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        if (this.id != other.id) return false;
        if ( ! this.isbn.equals(other.isbn)) return false;
        if ( ! this.title.equals(other.title)) return false;
        if (this.editionNumber != other.editionNumber) return false;
        if ( ! this.copyright.equals(other.copyright)) return false;
        if ( ! this.authors.containsAll(other.authors)) return false;

        return this.genre.equals(other.genre);
    }

    @Override
    public int hashCode() {
        final int PRIME = 13;
        int result = 1;
        result = (int) ((result*PRIME) + (this.id % Integer.MAX_VALUE));
        result = (result*PRIME) + this.isbn.hashCode();
        result = (result*PRIME) + this.title.hashCode();
        result = (result*PRIME) + this.editionNumber;
        result = (result*PRIME) + this.copyright.hashCode();
        result = (result*PRIME) + this.authors.stream().mapToInt(Author::hashCode).sum();
        result = (result*PRIME) + this.genre.hashCode();

        return result;
    }
}
