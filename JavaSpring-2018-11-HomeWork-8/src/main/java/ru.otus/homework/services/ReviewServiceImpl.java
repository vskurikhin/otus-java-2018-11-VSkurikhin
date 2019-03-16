package ru.otus.homework.services;

import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl implements ReviewService
{
    @Override
    public Review create(String review, Book book)
    {
        Review result = new Review();
        result.setReview(review);
        result.setBook(book);

        return result;
    }
}
