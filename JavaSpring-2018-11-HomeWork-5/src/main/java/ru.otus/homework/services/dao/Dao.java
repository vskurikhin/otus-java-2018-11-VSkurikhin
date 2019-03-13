package ru.otus.homework.services.dao;

import ru.otus.homework.models.DataSet;

import java.util.List;

public interface Dao<T extends DataSet>
{
    List<T> findAll();

    T findById(long id);

    void insert(T entity);

    void update(T entity);

    void delete(long id);
}
