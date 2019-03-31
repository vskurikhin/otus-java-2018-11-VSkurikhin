package ru.otus.homework.integration;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.models.Book;

import java.util.Collection;
import java.util.Optional;

// This is messaging gateway annotation
@MessagingGateway
public interface PostOffice
{
    // This is gateway annotation with required channels
    @Gateway(requestChannel = "itemsChannel", replyChannel = "booksChannel")
    Collection<Optional<Book>> process(Collection<Book> orderItem);
}
