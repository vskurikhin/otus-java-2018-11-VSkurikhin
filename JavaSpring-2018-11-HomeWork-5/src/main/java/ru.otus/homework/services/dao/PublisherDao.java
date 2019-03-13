package ru.otus.homework.services.dao;

import ru.otus.homework.models.Publisher;

import java.util.List;

public interface PublisherDao extends Dao<Publisher>
{
    List<Publisher> findByName(String name);
}
