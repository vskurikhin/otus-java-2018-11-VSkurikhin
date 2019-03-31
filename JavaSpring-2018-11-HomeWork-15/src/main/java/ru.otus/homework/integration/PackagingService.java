package ru.otus.homework.integration;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Book;

import java.util.Optional;

@Service
public class PackagingService
{
    public Optional<Book> pack(Book order) throws Exception
    {
        System.out.println("Ordering book ISBN: " + order.getIsbn());
        Thread.sleep(500);

        return RandomUtils.nextInt(0, 9) < 2 ? Optional.empty() : Optional.of(order);
    }
}
