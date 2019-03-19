package ru.otus.homework.controllers;

import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.exceptions.BadValueForReviewIdException;
import ru.otus.homework.exceptions.BookNotFoundException;
import ru.otus.homework.exceptions.ReviewIsNullException;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.dto.*;
import ru.otus.homework.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.Objects;

import static ru.otus.homework.controllers.Constants.*;

@RestController
public class ReviewsRestController
{
    private DatabaseService databaseService;

    @Autowired
    public ReviewsRestController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    private Mono<AnswerCreated> makeReview(Review review, HttpServletRequest req, HttpServletResponse resp)
    {
        return databaseService.saveReview(review)
            .map(r -> new AnswerCreated(resp, req.getRequestURI(), r.getId()));
    }

    private Mono<AnswerOk> amendReview(Review review)
    {
        return databaseService.saveReview(review).map(r -> new AnswerOk());
    }

    private void checkReviewDtoId(ReviewDto dto)
    {
        if (Objects.isNull(dto)) throw new ReviewIsNullException();
        if (Objects.isNull(dto.getId())) throw new BadValueForReviewIdException();
        BigInteger reviewId = new BigInteger(dto.getId());
        if (reviewId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForReviewIdException();
    }

    @GetMapping(REST_API + REST_V1_REVIEWS + "/count/by-book/{id}")
    public Mono<AnswerCountDto> countReviewsByBookId(@PathVariable BigInteger id)
    {
        return databaseService.countReviewsByBookId(id).map(AnswerCountDto::new);
    }

    @PostMapping(REST_API + REST_V1_REVIEWS)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<AnswerCreated> createReview(@RequestBody ReviewDto dto,
                                            HttpServletRequest req, HttpServletResponse resp)
    {
        if (Objects.isNull(dto)) throw new ReviewIsNullException();
        if ( ! Objects.isNull(dto.getId())) throw new BadValueForReviewIdException();
        BigInteger bookId = new BigInteger(dto.getBookId());

        return databaseService.getBookById(bookId)
            .flatMap(book -> makeReview(dto.createReview(book), req, resp))
            .switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @GetMapping(REST_API + REST_V1_REVIEWS + "/{id}")
    public Flux<ReviewDto> readReviews(@PathVariable BigInteger id)
    {
        return databaseService.getAllReviewsForBookById(id).map(ReviewDto::new);
    }

    @PutMapping(REST_API + REST_V1_REVIEWS)
    public Mono<? extends Answer> updateReview(@RequestBody ReviewDto dto)
    {
        checkReviewDtoId(dto);
        BigInteger bookId = new BigInteger(dto.getBookId());

        return databaseService.getBookById(bookId)
            .flatMap(book -> amendReview(dto.createReview(book)))
            .switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @DeleteMapping(REST_API + REST_V1_REVIEWS + "/{reviewId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<AnswerNoContent> deleteReview(@PathVariable BigInteger reviewId)
    {
        if (reviewId.compareTo(BigInteger.ZERO) < 0) throw new BadValueForReviewIdException();

        return databaseService.removeReview(reviewId)
            .map(v -> new AnswerNoContent("remove successfully"));
    }

    @ExceptionHandler(ReviewIsNullException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody AnswerNotAcceptable handleException(ReviewIsNullException e)
    {
        return new AnswerNotAcceptable("Review is null");
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody AnswerNotAcceptable handleException(BookNotFoundException e)
    {
        return new AnswerNotAcceptable("Book not found");
    }

    @ExceptionHandler(BadValueForReviewIdException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody AnswerBadRequest handleException(BadValueForReviewIdException e)
    {
        return new AnswerBadRequest("Bad value for Review Id");
    }

    @ExceptionHandler(MongoWriteException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody
    AnswerNotAcceptable handleException(MongoWriteException e)
    {
        return new AnswerNotAcceptable("Write exception" + e.getMessage());
    }
}
