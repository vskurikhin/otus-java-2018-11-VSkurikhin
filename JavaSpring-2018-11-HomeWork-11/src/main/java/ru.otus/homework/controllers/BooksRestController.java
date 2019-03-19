package ru.otus.homework.controllers;

import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.exceptions.*;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.dto.*;
import ru.otus.homework.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigInteger;
import java.util.Objects;

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

    private Mono<Book> saveBookAndGenre(Book book, Genre genre)
    {
        book.setGenre(genre);

        return databaseService.saveBook(book);
    }

    private Mono<Book> saveBookAndGenre(Book dto, String genreValue)
    {
        return databaseService.saveBookNewGenre(dto, genreValue);
    }

    private Mono<AnswerCreated> makeBook(BookDto dto, HttpServletRequest req, HttpServletResponse resp)
    {
        Book book = dto.createBook();

        return databaseService.getGenreByValue(dto.getGenre())
            .flatMap(genre -> saveBookAndGenre(book, genre)
                .map(b -> new AnswerCreated(resp, req.getRequestURI(), b.getId())))
            .switchIfEmpty(saveBookAndGenre(book, dto.getGenre())
                .map(b -> new AnswerCreated(resp, req.getRequestURI(), b.getId())));
    }

    private Mono<AnswerOk> amendBook(BookDto bookDto)
    {
        Book book = bookDto.createBook();

        return databaseService.getGenreByValue(bookDto.getGenre())
            .flatMap(genre -> saveBookAndGenre(book, genre)
                .map(b -> new AnswerOk()))
            .switchIfEmpty(saveBookAndGenre(book, bookDto.getGenre())
                .map(b -> new AnswerOk()));
    }

    private BigInteger getAndCheckBookDtoId(BookDto dto)
    {
        if (Objects.isNull(dto)) throw new BookIsNullException();
        if (Objects.isNull(dto.getId())) throw new BadValueForBookIdException();
        BigInteger bookId = new BigInteger(dto.getId());
        if (bookId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForBookIdException();

        return bookId;
    }

    @PostMapping(REST_API + REST_V1_BOOKS)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<AnswerCreated> createBook(@RequestBody BookDto dto,
                                          HttpServletRequest req, HttpServletResponse resp)
    {
        if (Objects.isNull(dto)) throw new BookIsNullException();
        if ( ! Objects.isNull(dto.getId())) throw new BadValueForBookIdException();

        if (Objects.isNull(dto.getGenre())) {
            return databaseService.saveBook(dto.createBook())
                .map(b -> new AnswerCreated(resp, req.getRequestURI(), b.getId()));
        }

        return makeBook(dto, req, resp);
    }

    @GetMapping(REST_API + REST_V1_BOOKS)
    public Flux<BookDto> readBooks()
    {
        return databaseService.getAllBooks().map(BookDto::new);
    }

    @PutMapping(REST_API + REST_V1_BOOKS)
    public Mono<? extends Answer> updateBook(@RequestBody BookDto dto)
    {
        BigInteger bookId = getAndCheckBookDtoId(dto);

        return databaseService.getBookById(bookId)
            .flatMap(book -> amendBook(dto))
            .switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @DeleteMapping(REST_API + REST_V1_BOOKS + "/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<AnswerNoContent> deleteBook(@PathVariable BigInteger bookId)
    {
        if (bookId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForBookIdException();

        return databaseService.getBookById(bookId)
            .flatMap(b -> {
                databaseService.removeBook(bookId);
                return Mono.just(new AnswerNoContent("remove successfully"));
            }).switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @ExceptionHandler(BookIsNullException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody AnswerNotAcceptable handleException(BookIsNullException e)
    {
        return new AnswerNotAcceptable("Author is null");
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody
    AnswerNotAcceptable handleException(BookNotFoundException e)
    {
        return new AnswerNotAcceptable("Book not found");
    }

    @ExceptionHandler(BadValueForBookIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(BadValueForBookIdException e)
    {
        return new AnswerBadRequest("Bad value for Book Id");
    }

    @ExceptionHandler(MongoWriteException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody
    AnswerNotAcceptable handleException(MongoWriteException e)
    {
        return new AnswerNotAcceptable("Write exception" + e.getMessage());
    }
}
