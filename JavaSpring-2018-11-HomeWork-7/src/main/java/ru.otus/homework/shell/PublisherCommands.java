package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import ru.otus.homework.models.Publisher;
import ru.otus.homework.services.*;

import java.util.List;

@ShellComponent
public class PublisherCommands
{
    private MessagesService msg;

    private PublishersService publishersService;

    private DataTransformer<PublishersService, Publisher> dataTransformer;

    public PublisherCommands(MessagesService msg, PublishersService publisherService)
    {
        this.msg = msg;
        this.publishersService = publisherService;
        this.dataTransformer = new DataTransformer<>(publisherService);
    }

    @ShellMethod(value = "Show publishers from table", group = "Show")
    public Table showAllPublishers()
    {
        List<Publisher> list = publishersService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return dataTransformer.transformList(list).build();
    }

    @ShellMethod(value = "Insert publisher to table", group = "Insert")
    public String insertPublisher(String publisher)
    {
        String sid = Long.toString(publishersService.insert(publisher));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update publisher in table", group = "Update")
    public String updatePublisher(long id, String publisher)
    {
        String sid = Long.toString(publishersService.update(id, publisher));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete publisher from table", group = "Delete")
    public void deletePublisher(long id)
    {
        publishersService.delete(id);
    }
}
