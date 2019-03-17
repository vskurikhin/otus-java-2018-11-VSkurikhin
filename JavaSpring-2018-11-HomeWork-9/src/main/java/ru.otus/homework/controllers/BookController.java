package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.dto.BookDto;
import ru.otus.homework.services.DatabaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.homework.controllers.Constants.*;

@Controller
public class BookController
{
    private DatabaseService databaseService;

    @Autowired
    public BookController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String booksList(Model model)
    {
        List<BookDto> books = databaseService.getAllBooks()
            .stream()
            .map(BookDto::new)
            .collect(Collectors.toList());
        model.addAttribute(MODEL_BOOKS, books);

        return VIEW_BOOKS_LIST;
    }

    @GetMapping(REQUEST_BOOK_CREATE)
    public String createBook(Model model)
    {
        BookDto dto = new BookDto();
        dto.setId("0");
        model.addAttribute(MODEL_BOOK, dto);

        return VIEW_BOOK_EDIT;
    }

    @GetMapping(REQUEST_BOOK_EDIT)
    public String toEditBook(@RequestParam long bookId, Model model)
    {

        Optional<Book> bookOptional = databaseService.getBookById(bookId);
        BookDto dto = new BookDto(bookOptional.orElse(new Book()));
        model.addAttribute(MODEL_BOOK, dto);
        List<Review> reviews = databaseService.getAllReviewsForBookById(bookId);
        model.addAttribute(MODEL_REVIEWS, reviews);

        return VIEW_BOOK_EDIT;
    }

    @PostMapping(REQUEST_BOOK_EDIT)
    public String saveBook(@RequestParam long bookId, @ModelAttribute BookDto dto)
    {
        Optional<Book> bookOptional = databaseService.getBookById(bookId);
        bookOptional.ifPresent(dto::updateBook);
        Book book = bookOptional.orElse(dto.createBook(bookId));
        Optional<Genre> optionalGenre = databaseService.getGenreByValue(dto.getGenre());
        if (optionalGenre.isPresent()) {
            book.setGenre(optionalGenre.get());
            databaseService.saveBook(book);
        }
        else {
            databaseService.saveBookNewGenre(book, dto.getGenre());
        }

        return "redirect:/";
    }

    @PostMapping(REQUEST_BOOK_DELETE)
    public String deleteBook(@RequestParam long bookId, Model model)
    {
        databaseService.removeBook(bookId);

        return "redirect:/";
    }
}
