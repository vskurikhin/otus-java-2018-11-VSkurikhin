package ru.otus.homework.services;

import ru.otus.homework.models.DataSet;

import java.util.List;

public interface FindService<T extends DataSet>
{
    List<T> findAll();

    String[] getHeader();

    String[] unfold(T value);

    T findById(long id);
}
