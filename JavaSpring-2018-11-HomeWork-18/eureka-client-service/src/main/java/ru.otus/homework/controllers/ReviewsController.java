package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.exceptions.BadValueForReviewIdException;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.dto.ReviewDto;
import ru.otus.homework.services.DatabaseService;

import java.util.Optional;

import static ru.otus.homework.controllers.Constants.*;

@Controller
public class ReviewsController
{
    private DatabaseService databaseService;

    @Autowired
    public ReviewsController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @GetMapping(REQUEST_REVIEWS_LIST)
    public String booksList(@RequestParam long bookId, Model model)
    {
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_REVIEWS, REST_API + REST_V1_REVIEWS);

        return VIEW_REVIEWS_LIST;
    }

    @GetMapping(REQUEST_REVIEW_CREATE)
    public String toCreateReview(@RequestParam long bookId, Model model)
    {
        ReviewDto dto = new ReviewDto();
        dto.setId(0L);
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_REVIEW, dto);
        model.addAttribute(MODEL_REVIEWS, REST_API + REST_V1_REVIEWS);

        return VIEW_REVIEW_EDIT;
    }

    @GetMapping(REQUEST_REVIEW_EDIT)
    public String toEditReview(@RequestParam long reviewId, @RequestParam long bookId, Model model)
    {
        Optional<Review> optionalReview = databaseService.getReviewById(reviewId);
        ReviewDto dto = new ReviewDto(optionalReview.orElseThrow(BadValueForReviewIdException::new));
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_REVIEW, dto);
        model.addAttribute(MODEL_REVIEWS, REST_API + REST_V1_REVIEWS);

        return VIEW_REVIEW_EDIT;
    }
}
