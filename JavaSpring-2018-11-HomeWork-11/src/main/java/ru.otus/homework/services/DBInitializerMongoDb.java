package ru.otus.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

@Service
public class DBInitializerMongoDb implements DBInitializer, CommandLineRunner
{
    private DatabaseService databaseService;

    @Autowired
    public DBInitializerMongoDb(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @Override
    public void initializer()
    {
        Author author1 = new Author(null, "Harvey", "Deitel");
        Author author2 = new Author(null, "Paul", "Deitel");
        Author author3 = new Author(null, "Tem", "Nieto");

        databaseService.saveAuthor(author1).block();
        databaseService.saveAuthor(author2).block();
        databaseService.saveAuthor(author3).block();

        Genre genre = new Genre(null, "Information Technology");
        databaseService.saveGenre(genre).block();

        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();

        book1.setIsbn("0130895601");
        book1.setTitle("Advanced Java 2 Platform How to Program");
        book1.setEditionNumber(1);
        book1.setCopyright("2002");
        book1.getAuthors().add(author1);
        book1.getAuthors().add(author2);
        book1.setGenre(genre);

        book2.setIsbn("0130829293");
        book2.setTitle("XML How to Program");
        book2.setEditionNumber(1);
        book2.setCopyright("2001");
        book2.getAuthors().add(author1);
        book2.getAuthors().add(author2);
        book2.getAuthors().add(author3);
        book2.setGenre(genre);

        book3.setIsbn("0130895520");
        book3.setTitle("The Complete Perl Training Course");
        book3.setEditionNumber(1);
        book3.setCopyright("2001");
        book3.getAuthors().add(author3);
        book3.setGenre(genre);

        databaseService.saveBook(book1).block();
        databaseService.saveBook(book2).block();
        databaseService.saveBook(book3).block();

        Review review1 = new Review(null, "Review for Advanced Java 2 Platform How to Program", book1);
        Review review2 = new Review(null, "Advanced Review for Advanced Java 2 Platform How to Program", book1);
        Review review3 = new Review(null, "The Complete Perl Training Course review 1", book3);
        Review review4 = new Review(null, "The Complete Perl Training Course review 2", book3);
        Review review5 = new Review(null, "The Complete Perl Training Course review 3", book3);

        databaseService.saveReview(review1).block();
        databaseService.saveReview(review2).block();
        databaseService.saveReview(review3).block();
        databaseService.saveReview(review4).block();
        databaseService.saveReview(review5).block();
    }

    @Override
    public void run(String... args) throws Exception
    {
        initializer();
    }
}
