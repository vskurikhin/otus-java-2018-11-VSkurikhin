package ru.otus.homework.repository;

import ru.otus.homework.models.Publisher;

import java.util.List;

public interface PublisherRepository extends Repository<Publisher>
{
    List<Publisher> findByPublisher(String publisher);
}
