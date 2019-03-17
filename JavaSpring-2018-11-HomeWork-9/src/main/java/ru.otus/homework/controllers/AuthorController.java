package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.exceptions.AuthorNotFoundException;
import ru.otus.homework.exceptions.BookNotFoundException;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.dto.AuthorDto;
import ru.otus.homework.services.DatabaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.homework.controllers.Constants.*;

@Controller
public class AuthorController
{
    private DatabaseService databaseService;

    @Autowired
    public AuthorController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @GetMapping("/authors")
    public String booksList(Model model)
    {
        return "redirect:/";
    }

    @GetMapping(REQUEST_BOOK_AUTHORS_LIST)
    public String bookAuthorsList(@RequestParam long bookId, Model model)
    {
        List<AuthorDto> authors = databaseService.getBookById(bookId)
            .orElse(new Book())
            .getAuthors()
            .stream()
            .map(AuthorDto::new)
            .collect(Collectors.toList());
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_AUTHORS, authors);

        return VIEW_BOOK_AUTHORS_LIST;
    }

    @GetMapping(REQUEST_AUTHOR_CREATE)
    public String toCreateAuthor(@RequestParam long bookId, Model model)
    {
        AuthorDto dto = new AuthorDto();
        dto.setId("0");
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_AUTHOR, dto);

        return VIEW_AUTHOR_EDIT;
    }

    @GetMapping(REQUEST_AUTHOR_EDIT)
    public String toEditAuthor(@RequestParam long authorId, @RequestParam long bookId,  Model model)
    {
        Optional<Author> authorOptional = databaseService.getAuthorById(authorId);
        AuthorDto dto = new AuthorDto(authorOptional.orElse(new Author()));
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_AUTHOR, dto);

        return VIEW_AUTHOR_EDIT;
    }

    @PostMapping(REQUEST_AUTHOR_EDIT)
    public String saveAuthor(@RequestParam long authorId,
                             @RequestParam long bookId,
                             @ModelAttribute AuthorDto dto)
    {
        Optional<Author> optionalAuthor;
        Optional<Book> bookOptional = databaseService.getBookById(bookId);
        Book book = bookOptional.orElseThrow(BookNotFoundException::new);

        if (authorId == 0L) {
            Author author = databaseService.getAuthorByFirstNameAndLastName(
                dto.getFirstName(), dto.getLastName()
            ).orElse(new Author(0L, dto.getFirstName(), dto.getLastName()));
            databaseService.addAuthorToBook(author, book);
        }
        else {
            optionalAuthor = databaseService.getAuthorById(authorId);
            optionalAuthor.ifPresent(author -> {
                dto.updateAuthor(author);
                databaseService.saveAuthor(author);
            });
        }

        return "redirect:/";
    }

    @PostMapping(REQUEST_AUTHOR_DELETE)
    public String deleteAuthor(@RequestParam long authorId, @RequestParam long bookId, Model model)
    {
        Optional<Book> bookOptional = databaseService.getBookById(bookId);
        Book book = bookOptional.orElseThrow(BookNotFoundException::new);
        Optional<Author> authorOptional = databaseService.getAuthorById(authorId);
        Author author = authorOptional.orElseThrow(AuthorNotFoundException::new);
        book.getAuthors().remove(author);
        databaseService.saveBook(book);

        return "redirect:/";
    }
}
