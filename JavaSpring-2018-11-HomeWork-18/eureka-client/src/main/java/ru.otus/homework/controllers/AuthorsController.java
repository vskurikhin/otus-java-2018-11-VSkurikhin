package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.dto.AuthorDto;
import ru.otus.homework.services.DatabaseService;

import java.util.Optional;

import static ru.otus.homework.controllers.Constants.*;

@Controller
public class AuthorsController
{
    private DatabaseService databaseService;

    @Autowired
    public AuthorsController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @GetMapping(REQUEST_BOOK_AUTHORS_LIST)
    public String bookAuthorsList(@RequestParam long bookId, Model model)
    {
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_AUTHORS, REST_API + REST_V1_AUTHORS);

        return VIEW_BOOK_AUTHORS_LIST;
    }

    @GetMapping(REQUEST_AUTHOR_CREATE)
    public String toCreateAuthor(@RequestParam long bookId, Model model)
    {
        AuthorDto dto = new AuthorDto();
        dto.setId(0L);
        model.addAttribute(MODEL_BOOK_ID, bookId).addAttribute(MODEL_AUTHOR, dto);
        model.addAttribute(MODEL_AUTHORS, REST_API + REST_V1_AUTHORS);

        return VIEW_BOOK_AUTHOR_EDIT;
    }

    @GetMapping(REQUEST_AUTHOR_EDIT)
    public String toEditAuthor(@RequestParam long authorId, @RequestParam long bookId,  Model model)
    {
        Optional<Author> authorOptional = databaseService.getAuthorById(authorId);
        AuthorDto dto = new AuthorDto(authorOptional.orElse(new Author()));
        model.addAttribute(MODEL_BOOK_ID, bookId).addAttribute(MODEL_AUTHOR, dto);
        model.addAttribute(MODEL_AUTHORS, REST_API + REST_V1_AUTHORS);

        return VIEW_BOOK_AUTHOR_EDIT;
    }
}
