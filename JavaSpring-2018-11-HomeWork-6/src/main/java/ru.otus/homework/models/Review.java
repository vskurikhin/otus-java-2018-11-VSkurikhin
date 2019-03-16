package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "book_review")
@NamedQueries({
    @NamedQuery(
        name="Review.findAllWithBook",
        query="SELECT DISTINCT r"
            + " FROM Review r"
            + " LEFT JOIN FETCH r.book b"
    )
})
public class Review implements Serializable, DataSet
{
    static final long serialVersionUID = -3L;

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
