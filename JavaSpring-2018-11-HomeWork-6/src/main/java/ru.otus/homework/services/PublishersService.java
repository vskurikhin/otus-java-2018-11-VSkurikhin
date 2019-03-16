package ru.otus.homework.services;

import ru.otus.homework.models.Publisher;

import java.util.List;

public interface PublishersService extends FindService<Publisher>
{
    List<Publisher> findByPublisherName(String publisherName);

    long insert(String publisherName);

    long update(long id, String publisherName);

    void delete(long id);
}
