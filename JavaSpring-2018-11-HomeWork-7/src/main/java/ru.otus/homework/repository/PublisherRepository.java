package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.Publisher;

import java.util.List;

public interface PublisherRepository extends CrudRepository<Publisher, Long>
{
    List<Publisher> findByPublisherName(String publisher);

    List<Publisher> findAll();
}
