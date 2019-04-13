package ru.otus.homework.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RepositoryRestResource(path = "authors")
public interface AuthorDao extends CrudRepository<Author, Long>
{
    @RestResource(path = "firstName")
    List<Author> findByFirstName(String firstName);

    @RestResource(path = "lastNames")
    List<Author> findByLastName(String lastName);

    @RestResource(path = "names")
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findAll();

    @Modifying
    @Transactional
    void deleteById(long id);
}
