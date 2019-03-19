package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.dto.BookDto;
import ru.otus.homework.services.DatabaseService;

import java.util.Optional;

import static ru.otus.homework.controllers.Constants.*;

@Controller
public class BooksController
{
    private DatabaseService databaseService;

    @Autowired
    public BooksController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String booksList(Model model)
    {
        model.addAttribute(MODEL_BOOKS, REST_API + REST_V1_BOOKS);
        model.addAttribute(MODEL_AUTHORS, REST_API + REST_V1_AUTHORS);
        model.addAttribute(MODEL_REVIEWS, REST_API + REST_V1_REVIEWS);

        return VIEW_BOOKS_LIST;
    }

    @GetMapping(REQUEST_BOOK_CREATE)
    public String createBook(Model model)
    {
        BookDto dto = new BookDto();
        dto.setId(0L);
        model.addAttribute(MODEL_BOOK, dto);
        model.addAttribute(MODEL_BOOKS, REST_API + REST_V1_BOOKS);

        return VIEW_BOOK_EDIT;
    }

    @GetMapping(REQUEST_BOOK_EDIT)
    public String toEditBook(@RequestParam long bookId, Model model)
    {

        Optional<Book> bookOptional = databaseService.getBookById(bookId);
        BookDto dto = new BookDto(bookOptional.orElse(new Book()));
        model.addAttribute(MODEL_BOOK, dto);
        model.addAttribute(MODEL_BOOKS, REST_API + REST_V1_BOOKS);

        return VIEW_BOOK_EDIT;
    }
}
