package ru.otus.homework.repository;

import ru.otus.homework.models.DataSet;

import java.util.List;

public interface Repository<T extends DataSet>
{
    String[] FIND_ALL_HEADER = {};

    List<T> findAll();

    T findById(long id);

    void save(T entity);

    void delete(long id);
}
