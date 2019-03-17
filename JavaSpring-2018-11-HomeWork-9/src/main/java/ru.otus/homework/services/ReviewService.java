package ru.otus.homework.services;

import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

public interface ReviewService
{
    Review create(String review, Book book);
}
