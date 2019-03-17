package ru.otus.homework.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;

import java.util.List;

@Transactional(readOnly = true)
public interface BookDao extends CrudRepository<Book, Long>
{
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
