package ru.otus.homework.services;

import org.springframework.shell.table.TableBuilder;
import ru.otus.homework.models.DataSet;

import java.util.List;

public interface Transformer<T extends DataSet>
{
    String[] getHeader();

    List<String[]> unfold(T value);

    TableBuilder create(String[][] data);

    TableBuilder transformList(List<T> list);

    TableBuilder transformDataSet(T entry);
}
