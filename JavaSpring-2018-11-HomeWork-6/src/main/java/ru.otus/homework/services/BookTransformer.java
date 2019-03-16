package ru.otus.homework.services;

import org.springframework.shell.table.*;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookTransformer extends DataTransformer<BooksService, Book>
{
    public BookTransformer(BooksService service)
    {
        super(service);
    }

    public TableBuilder transformListBookAndAuthor(List<Book> books)
    {
        assert books != null;
        String[] ss = service.getHeaderBookAndAuthor();

        List<String[]> dataList = new ArrayList<>();
        dataList.add(service.getHeaderBookAndAuthor());

        for (Book book : books) {
            if (book.getAuthors().isEmpty()) {
                dataList.add(service.mergeBookAndAuthor(book, null));
            }
            else {
                for (Author author : book.getAuthors()) {
                    dataList.add(service.mergeBookAndAuthor(book, author));
                }
            }
        }

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return create(data).addFullBorder(style);
    }
}
