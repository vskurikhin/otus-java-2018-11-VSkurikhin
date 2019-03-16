package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import ru.otus.homework.models.Book;
import ru.otus.homework.services.BookTransformer;
import ru.otus.homework.services.BooksService;
import ru.otus.homework.services.BooksServiceImpl;
import ru.otus.homework.services.MessagesService;

import java.util.List;

@ShellComponent
public class BookCommands
{
    private MessagesService msg;

    private BookTransformer dataTransformer;

    private BooksService booksService;

    public BookCommands(MessagesService msg, BooksServiceImpl bookService)
    {
        this.msg = msg;
        this.booksService = bookService;
        this.dataTransformer = new BookTransformer(bookService);
    }

    @ShellMethod(value = "Show books from table", group = "Show")
    public Table showAllBooks()
    {
        List<Book> list = booksService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return dataTransformer.transformList(list).build();
    }

    @ShellMethod(value = "Show books from table", group = "Show")
    public Table showAllBooksWithDetails()
    {
        List<Book> list = booksService.findAllBooksAndTheirAuthors();
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return dataTransformer.transformListBookAndAuthor(list).build();
    }

    @ShellMethod(value = "Insert book to table", group = "Insert")
    public String insertBook(String isbn, String title, int editionNumber, String copyright, long publisherId,
                             long genreId)
    {
        String sid = Long.toString(booksService.insert(isbn, title, editionNumber, copyright, publisherId, genreId));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update book in table", group = "Update")
    public String updateBook(long id, String isbn, String title, int editionNumber, String copyright, long publisherId,
                             long genreId)
    {
        String sid = Long.toString(booksService.update(id, isbn, title, editionNumber, copyright, publisherId, genreId));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete book from table", group = "Delete")
    public void deleteBook(long id)
    {
        booksService.delete(id);
    }
}
