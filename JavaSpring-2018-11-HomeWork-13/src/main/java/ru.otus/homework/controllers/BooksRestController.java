package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.dto.*;
import ru.otus.homework.services.DatabaseService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.homework.controllers.Constants.REST_API;
import static ru.otus.homework.controllers.Constants.REST_V1_BOOKS;

@RestController
public class BooksRestController
{
    private DatabaseService databaseService;

    @Autowired
    public BooksRestController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    private Integer countReviews(BookDto dto)
    {
        Long count = databaseService.countReviewsByBookId(dto.getId());

        return count > Integer.MAX_VALUE ? Integer.MAX_VALUE : count.intValue();
    }

    @GetMapping(REST_API + REST_V1_BOOKS)
    public List<BookDto> getBooks()
    {
        return databaseService.getAllBooks()
            .stream()
            .map(BookDto::new)
            .peek(bookDto -> bookDto.setReviewsCount(countReviews(bookDto)))
            .collect(Collectors.toList());
    }

    private void saveBook(Book book, String genre)
    {
        Optional<Genre> optionalGenre = databaseService.getGenreByValue(genre);
        if (optionalGenre.isPresent()) {
            book.setGenre(optionalGenre.get());
            databaseService.saveBook(book);
        }
        else {
            databaseService.saveBookNewGenre(book, genre);
        }
    }

    @PutMapping(REST_API + REST_V1_BOOKS)
    public ResponseStatusDto updateBook(@RequestBody BookDto bookDto)
    {
        Optional<Book> bookOptional = databaseService.getBookById(bookDto.getId());
        bookOptional.ifPresent(bookDto::updateBook);
        Book book = bookOptional.orElse(bookDto.createBook(bookDto.getId()));
        saveBook(book, bookDto.getGenre());

        return new ResponseStatusOk();
    }

    @PostMapping(REST_API + REST_V1_BOOKS)
    public ResponseStatusDto createBook(@RequestBody BookDto bookDto, HttpServletResponse response)
    {
        Book book = bookDto.createBook(0L);
        saveBook(book, bookDto.getGenre());

        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ResponseStatusCreated();
    }

    @DeleteMapping(REST_API + REST_V1_BOOKS + "/{bookId}")
    public ResponseStatusDto deleteBook(@PathVariable long bookId, HttpServletResponse response) {
        databaseService.removeBook(bookId);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return new ResponseStatusNoContent();
    }
}
