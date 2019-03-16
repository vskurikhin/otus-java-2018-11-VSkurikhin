package ru.otus.homework.services;

import org.springframework.shell.table.*;
import ru.otus.homework.models.DataSet;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.shell.table.CellMatchers.at;

public abstract class DataTransformer<T extends DataSet> implements Transformer<T>
{
    protected BorderStyle style = BorderStyle.fancy_light;

    public TableBuilder create(String[][] data)
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

    @SuppressWarnings("Duplicates")
    public TableBuilder transformList(List<T> list)
    {
        assert list != null;

        List<String[]> dataList = new ArrayList<>();
        dataList.add(getHeader());

        for (T e : list) {
            dataList.addAll(unfold(e));
        }

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return create(data).addFullBorder(style);
    }

    public TableBuilder transformDataSet(T entry)
    {
        assert entry != null;

        List<String[]> dataList = new ArrayList<>();
        dataList.add(getHeader());
        dataList.addAll(unfold(entry));

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return create(data).addFullBorder(style);
    }
}
