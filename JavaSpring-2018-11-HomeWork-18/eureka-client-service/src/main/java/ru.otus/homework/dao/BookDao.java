package ru.otus.homework.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;

import java.util.List;

@Transactional(readOnly = true)
public interface BookDao extends CrudRepository<Book, Long>
{
    @Query(
        value = "SELECT COUNT(b.book_id) FROM book b"
                + " LEFT OUTER JOIN author_isbn ai ON b.book_id = ai.book_id"
                + " LEFT OUTER JOIN author a ON ai.author_id = a.author_id"
                + " WHERE a.author_id = ?1",
        nativeQuery = true
    )
    long countByAuthorId(@Param("id") long id);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.authors a WHERE a.id = :id")
    List<Book> findAllByAuthorId(@Param("id") long id);

    List<Book> findByTitle(String title);

    @Query(
        "SELECT DISTINCT b"
        + " FROM Book b"
        + " LEFT JOIN FETCH b.authors a"
        + " LEFT JOIN FETCH b.genre g"
        + " ORDER BY b.isbn"
    )
    List<Book> findAll();

    @Modifying
    @Transactional
    void deleteById(long id);
}
