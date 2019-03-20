package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.exceptions.BadValueForReviewIdException;
import ru.otus.homework.exceptions.BookNotFoundException;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.dto.*;
import ru.otus.homework.services.DatabaseService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping(REST_API + REST_V1_REVIEWS + "/{id}")
    public List<ReviewDto> getReviews(@PathVariable long id)
    {
        return databaseService.getAllReviewsForBookById(id)
            .stream()
            .map(ReviewDto::new)
            .peek(reviewDto -> reviewDto.setBookId(id))
            .collect(Collectors.toList());
    }

    @GetMapping(REST_API + REST_V1_REVIEWS + "/count/by-book/{id}")
    public ResponseCountDto countReviewsByBookId(@PathVariable long id)
    {
        return new ResponseCountDto(databaseService.countReviewsByBookId(id));
    }

    @PutMapping(REST_API + REST_V1_REVIEWS)
    public ResponseStatusDto updateReview(@RequestBody ReviewDto reviewDto)
    {
        if (reviewDto.getId()< 1) throw new BadValueForReviewIdException();

        Optional<Review> reviewOptional = databaseService.getReviewById(reviewDto.getId());
        reviewOptional.ifPresent(reviewDto::updateReview);
        Review review = reviewOptional.orElse(reviewDto.createReview(
            databaseService.getBookById(reviewDto.getBookId()).orElseThrow(BookNotFoundException::new)
        ));
        databaseService.saveReview(review);

        return new ResponseStatusOk();
    }

    @PostMapping(REST_API + REST_V1_REVIEWS)
    public ResponseStatusDto createReview(@RequestBody ReviewDto reviewDto, HttpServletResponse response)
    {
        if (reviewDto.getId() != 0) throw new BadValueForReviewIdException();

        Book book = databaseService.getBookById(reviewDto.getBookId()).orElseThrow(BookNotFoundException::new);
        Review review = new Review(0L, reviewDto.getReview(), book);
        databaseService.saveReview(review);

        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ResponseStatusCreated();
    }

    @DeleteMapping(REST_API + REST_V1_REVIEWS + "/{reviewId}")
    public ResponseStatusDto deleteReview(@PathVariable long reviewId, HttpServletResponse response)
    {
        databaseService.removeReview(reviewId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return new ResponseStatusNoContent();
    }
}
