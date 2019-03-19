package ru.otus.homework.controllers;

import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.exceptions.*;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.dto.*;
import ru.otus.homework.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigInteger;
import java.util.Objects;

import static ru.otus.homework.controllers.Constants.REST_API;
import static ru.otus.homework.controllers.Constants.REST_V1_AUTHORS;

@RestController
public class AuthorsRestController
{
    private DatabaseService databaseService;

    @Autowired
    public AuthorsRestController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @PostMapping(REST_API + REST_V1_AUTHORS)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<AnswerCreated> createAuthor(@RequestBody AuthorBookIdDto authorDto,
                                            HttpServletRequest request, HttpServletResponse response)
    {
        if ( ! Objects.isNull(authorDto.getId())) throw new BadValueForAuthorIdException();
        BigInteger bookId = new BigInteger(authorDto.getBookId());
        Author author = authorDto.createAuthor();

        return databaseService.getBookById(bookId).flatMap(
            book -> databaseService.getAuthorByFirstNameAndLastName(author.getFirstName(), author.getLastName())
            .flatMap(
                author1 -> databaseService.addAuthorToBook(book, author1)
                .map(author2 -> new AnswerCreated(response, request.getRequestURI(), author2.getId()))
            ).switchIfEmpty(databaseService.addAuthorToBook(book, author)
                .map(author3 -> new AnswerCreated(response, request.getRequestURI(), author3.getId()))
            )).switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @GetMapping(REST_API + REST_V1_AUTHORS + "/{bookId}")
    public Flux<AuthorBookIdDto> readAuthorsForBookId(@PathVariable BigInteger bookId)
    {
        if (bookId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForBookIdException();

        return databaseService.getAuthorsForBookId(bookId).map(a -> {
            AuthorBookIdDto dto = new AuthorBookIdDto(a);
            dto.setBookId(bookId.toString());
            return dto;
        });
    }

    @PutMapping(REST_API + REST_V1_AUTHORS)
    public Mono<AnswerOk> updateAuthor(@RequestBody AuthorBookIdDto dto)
    {
        if (Objects.isNull(dto)) return Mono.error(new AuthorIsNullException());
        BigInteger authorId = new BigInteger(dto.getId());
        if (authorId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForAuthorIdException();

        return databaseService.getAuthorById(authorId).flatMap(author -> {
            dto.updateAuthor(author);
            return databaseService.saveAuthor(author)
                .flatMap(s -> Mono.just(new AnswerOk()))
                .switchIfEmpty(Mono.error(new AuthorDontSavedException()));
        }).switchIfEmpty(Mono.error(new AuthorNotFoundException()));
    }

    @DeleteMapping(REST_API + REST_V1_AUTHORS + "/{authorId}/from-book/{bookId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<AnswerNoContent> deleteAuthorFromBook(@PathVariable BigInteger authorId,
                                                      @PathVariable BigInteger bookId)
    {
        if (authorId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForAuthorIdException();
        if (bookId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForBookIdException();

        return databaseService.getBookById(bookId).flatMap(
            book -> databaseService.getAuthorById(authorId).flatMap(author -> {
                book.getAuthors().remove(author);
                return databaseService.saveBook(book)
                    .flatMap(b -> Mono.just(new AnswerNoContent("remove successfully")));
            }));
    }

    @ExceptionHandler(AuthorIsNullException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody
    AnswerNotAcceptable handleException(AuthorIsNullException e)
    {
        return new AnswerNotAcceptable("Author is null");
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody
    AnswerNotAcceptable handleException(AuthorNotFoundException e)
    {
        return new AnswerNotAcceptable("Author not found");
    }

    @ExceptionHandler(AuthorDontSavedException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody
    AnswerNotAcceptable handleException(AuthorDontSavedException e)
    {
        return new AnswerNotAcceptable("Author don't saved");
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody
    AnswerNotAcceptable handleException(BookNotFoundException e)
    {
        return new AnswerNotAcceptable("Book not found");
    }

    @ExceptionHandler(BadValueForAuthorIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    AnswerBadRequest handleException(BadValueForAuthorIdException e)
    {
        return new AnswerBadRequest("Bad value for Author Id");
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
