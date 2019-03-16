package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.AuthorsServiceImpl;
import ru.otus.homework.services.DataTransformer;
import ru.otus.homework.services.MessagesService;

import java.util.List;

@ShellComponent
public class AuthorCommands
{
    private MessagesService msg;

    private AuthorsServiceImpl authorsService;

    private DataTransformer<AuthorsServiceImpl, Author> dataTransformer;

    public AuthorCommands(MessagesService msg, AuthorsServiceImpl authorsService)
    {
        this.msg = msg;
        this.authorsService = authorsService;
        this.dataTransformer = new DataTransformer<>(authorsService);
    }

    @ShellMethod(value = "Show Authors from table", group = "Show")
    public Table showAllAuthors()
    {
        List<Author> authors = authorsService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{authors.size()}));

        return dataTransformer.transformList(authors).build();
    }

    @ShellMethod(value = "Insert Author to table", group = "Insert")
    public String insertAuthor(String firstName, String lastName)
    {
        String sid = Long.toString(authorsService.insert(firstName, lastName));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update Author in table", group = "Update")
    public String updateAuthor(long id, String firstName, String lastName)
    {
        String sid = Long.toString(authorsService.update(id, firstName, lastName));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete Author from table", group = "Delete")
    public void deleteAuthor(long id)
    {
        authorsService.delete(id);
    }
}
