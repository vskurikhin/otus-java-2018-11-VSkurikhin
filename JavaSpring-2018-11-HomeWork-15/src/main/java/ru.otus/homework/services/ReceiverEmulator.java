package ru.otus.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.stereotype.Service;
import ru.otus.homework.exceptions.BadLineFormatException;
import ru.otus.homework.integration.PostOffice;
import ru.otus.homework.models.Book;
import ru.otus.outside.utils.IOHelper;
import ru.otus.outside.utils.StringHelper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableIntegration
public class ReceiverEmulator implements Receiving, CommandLineRunner
{
    private PostOffice postOffice;

    @Autowired
    public ReceiverEmulator(PostOffice postOffice)
    {
        this.postOffice = postOffice;
    }


    private Collection<Book> generateOrderItems()
    {
        try {
            List<Book> books = new ArrayList<>();

            IOHelper.readFile(System.in, line -> {
                String[] fields = StringHelper.split(line);
                if (fields.length < 3) {
                    throw new BadLineFormatException();
                }
                Book book = new Book();
                book.setIsbn(fields[0]);
                book.setTitle(fields[1]);
                books.add(book);
            });

            return books;
        } catch (BadLineFormatException | StringIndexOutOfBoundsException e) {
            System.err.println("Error: " + e);
            return Collections.emptyList();
        }
    }

    @Override
    public void receiving() throws InterruptedException
    {
        while (true) {
            Collection<Book> order = generateOrderItems();
            if (order.isEmpty()) { return; }
            System.out.println(
                "New order with ISBNs: " + order.stream()
                    .map(Book::getIsbn)
                    .collect(Collectors.joining(","))
            );
            Collection<Optional<Book>> food = postOffice.process(order);
            System.out.println(
                (food.stream().allMatch(Optional::isPresent)
                    ? "Order full completed: "
                    : "Order partial completed: " )
                    + food.stream()
                    .filter(Optional::isPresent)
                    .map(o -> o.get().getIsbn())
                    .collect(Collectors.joining(","))
            );

            Thread.sleep(1000);
        }
    }

    @Override
    public void run(String... args) throws Exception
    {
        receiving();
    }
}
