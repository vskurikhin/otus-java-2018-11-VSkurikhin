package ru.otus.homework.services;

import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Publisher;

import java.util.List;

public interface PublishersService extends FindService<Publisher>
{
    List<Publisher> findByPublisher(String publisher);

    long insert(String publisher);

    long update(long id, String publisher);

    void delete(long id);
}
