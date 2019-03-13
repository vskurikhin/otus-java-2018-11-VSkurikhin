package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.*;

import java.util.List;

@ShellComponent
public class GenreCommands
{
    private MessagesService msg;

    private GenreService genresService;

    private DataTransformer<GenreService, Genre> dataTransformer;

    public GenreCommands(MessagesService msg, GenreService genreService)
    {
        this.msg = msg;
        this.genresService = genreService;
        this.dataTransformer = new DataTransformer<>(genreService);
    }

    @ShellMethod(value = "Show genres from table", group = "Show")
    public Table showAllGenres()
    {
        List<Genre> list = genresService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return dataTransformer.transformList(list).build();
    }

    @ShellMethod(value = "Insert genre to table", group = "Insert")
    public String insertGenre(String genre)
    {
        String sid = Long.toString(genresService.insert(genre));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update genre in table", group = "Update")
    public String updateGenre(long id, String genre)
    {
        String sid = Long.toString(genresService.update(id, genre));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete genre from table", group = "Delete")
    public void deleteGenre(long id)
    {
        genresService.delete(id);
    }
}
