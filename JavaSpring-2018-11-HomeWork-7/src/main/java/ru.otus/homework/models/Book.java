package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@NamedQueries({
    @NamedQuery(
        name="Book.findAll",
        query="SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.publisher p LEFT JOIN FETCH b.genre g"
    ),
    @NamedQuery(
        name="Book.findAllWithDetail",
        query="SELECT DISTINCT b"
             + " FROM Book b"
             + " LEFT JOIN FETCH b.publisher p"
             + " LEFT JOIN FETCH b.authors a"
             + " LEFT JOIN FETCH b.genre g"
    )
})

public class Book implements Serializable, DataSet
{
    static final long serialVersionUID = -2L;

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String isbn;

    @Column
    private String title;

    @Column(name = "edition_number")
    private int editionNumber;

    @Column
    private String copyright;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "author_isbn",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;
}
