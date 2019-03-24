package ru.otus.homework.services;

import ru.otus.homework.models.mongo.Review;
import ru.otus.homework.models.mongo.Book;

public class ReviewJpa2MongoTransformer
{
    public static Review transform(ru.otus.homework.models.jpa.Review review)
    {
        Book book = BookJpa2MongoTransformer.transform(review.getBook());

        return new Review(review.getId(), review.getReview(), book);
    }
}
