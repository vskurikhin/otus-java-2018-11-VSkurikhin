package ru.otus.homework.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AuthorDao extends CrudRepository<Author, Long>
{
    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findAll();

    @Modifying
    @Transactional
    void deleteById(long id);
}
