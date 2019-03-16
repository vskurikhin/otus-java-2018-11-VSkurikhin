package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.Table;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.*;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class BookCommands
{
    private MessagesService msg;

    private BookService bookService;

    private BooksTransformer transformer;

    private DataStorageService storageService;

    public BookCommands(MessagesService msg, BookService bookService, DataStorageService storageService)
    {
        this.msg = msg;
        this.bookService = bookService;
        this.storageService = storageService;
        this.transformer = new BooksTransformer();
    }

    @ShellMethod(value = "Show books from table", group = "Show")
    public Table showAllBooks()
    {
        List<Book> list = storageService.getAllBooks();
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return transformer.transformList(list).build();
    }

    @ShellMethod(value = "Insert book to table", group = "Insert")
    public String insertBook(String isbn, String title, int editionNumber, String copyright,
                             @ShellOption(arity = 2, defaultValue = ShellOption.NULL) String author,
                             @ShellOption(defaultValue = ShellOption.NULL) String genre)
    {
        Book book = bookService.create(isbn, title, editionNumber, copyright, author, genre);
        book = storageService.saveBook(book);

        return msg.get("inserted_with_id", new Object[]{book.getId()});
    }

    @ShellMethod(value = "Update book in table", group = "Update")
    public String updateBook(String id,
                             @ShellOption(defaultValue = ShellOption.NULL) String isbn,
                             @ShellOption(defaultValue = ShellOption.NULL) String title,
                             @ShellOption(defaultValue = "-1") int editionNumber,
                             @ShellOption(defaultValue = ShellOption.NULL) String copyright)
    {
        Optional<Book> optionalBook = storageService.getBookById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book = bookService.update(book, isbn, title, editionNumber, copyright);
            book = storageService.saveBook(book);

            return msg.get("record_with_with_id_updated", new Object[]{book.getId()});
        }

        return "Error";
    }

    @ShellMethod(value = "Add author to book in table", group = "Update")
    public String addAuthorToBook(String id, @ShellOption(arity = 2) String author)
    {
        Optional<Book> optionalBook = storageService.getBookById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book = bookService.addAuthor(book, author);
            book = storageService.saveBook(book);

            return msg.get("record_with_with_id_updated", new Object[]{book.getId()});
        }

        return "Error";
    }

    @ShellMethod(value = "Add genre to book in table", group = "Update")
    public String addGenreToBook(String id, String genre)
    {
        Optional<Book> optionalBook = storageService.getBookById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book = bookService.addGenre(book, genre);
            book = storageService.saveBook(book);

            return msg.get("record_with_with_id_updated", new Object[]{book.getId()});
        }

        return "Error";
    }

    @ShellMethod(value = "Remove genre from book in table", group = "Update")
    public String removeGenreFromBook(String id, String genre)
    {
        Optional<Book> optionalBook = storageService.getBookById(id);

        if (optionalBook.isPresent()) {
            Optional<Genre> optionalGenre = storageService.getGenre(genre);

            if (optionalGenre.isPresent()) {

                Book book = optionalBook.get();
                book = bookService.removeGenre(book, optionalGenre.get());
                book = storageService.saveBook(book);

                return msg.get("record_with_with_id_updated", new Object[]{book.getId()});
            }
        }

        return "Error";
    }

    @ShellMethod(value = "Delete book from table", group = "Delete")
    public void deleteBook(String id)
    {
        storageService.removeBook(id);
    }
}
