package ru.otus.homework.services;

import org.springframework.shell.table.*;
import ru.otus.homework.models.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.shell.table.CellMatchers.at;

public class DataTransformer<S extends FindService<T>, T extends DataSet>
{

    protected BorderStyle style = BorderStyle.fancy_light;

    protected S service;

    public DataTransformer(S service)
    {
        this.service = service;
    }

    static TableBuilder create(String[][] data)
    {
        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);

        for (int j = 0; j < data.length; j++) {
            tableBuilder.on(at(0, j)).addAligner(SimpleHorizontalAligner.center);
        }

        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                tableBuilder.on(at(i, j)).addAligner(SimpleHorizontalAligner.left);
                tableBuilder.on(at(i, j)).addAligner(SimpleVerticalAligner.middle);
            }
            tableBuilder.on(at(i, 0)).addAligner(SimpleHorizontalAligner.right);
        }

        return tableBuilder;
    }

    public TableBuilder transformList(List<T> list)
    {
        assert list != null;

        List<String[]> dataList = new ArrayList<>();
        dataList.add(service.getHeader());

        List<String[]> tail = list.stream()
            .map(v -> service.unfold(v))
            .collect(Collectors.toList());
        dataList.addAll(tail);

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return create(data).addFullBorder(style);
    }

    public TableBuilder transformDataSet(T entry)
    {
        assert entry != null;

        List<String[]> dataList = new ArrayList<>();
        dataList.add(service.getHeader());
        dataList.add(service.unfold(entry));

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return create(data).addFullBorder(style);
    }
}
