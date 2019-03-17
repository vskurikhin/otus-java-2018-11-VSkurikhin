package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.exceptions.BookNotFoundException;
import ru.otus.homework.models.Review;
import ru.otus.homework.models.dto.ReviewDto;
import ru.otus.homework.services.DatabaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.homework.controllers.Constants.*;

@Controller
public class ReviewController
{
    private DatabaseService databaseService;

    @Autowired
    public ReviewController(DatabaseService databaseService)
    {
        this.databaseService = databaseService;
    }

    @GetMapping("/reviews")
    public String booksList(Model model)
    {
        return "redirect:/";
    }

    @GetMapping(REQUEST_REVIEWS_LIST)
    public String reviewsForBookList(@RequestParam long bookId, Model model)
    {
        List<ReviewDto> reviews = databaseService.getAllReviewsForBookById(bookId)
            .stream()
            .map(ReviewDto::new)
            .collect(Collectors.toList());
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_REVIEWS, reviews);

        return VIEW_REVIEWS_LIST;
    }

    @GetMapping(REQUEST_REVIEW_CREATE)
    public String toCreateReview(@RequestParam long bookId, Model model)
    {
        ReviewDto dto = new ReviewDto();
        dto.setId("0");
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_REVIEW, dto);

        return VIEW_REVIEW_EDIT;
    }

    @GetMapping(REQUEST_REVIEW_EDIT)
    public String toEditReview(@RequestParam long reviewId, @RequestParam long bookId, Model model)
    {
        Optional<Review> authorOptional = databaseService.getReviewById(reviewId);
        ReviewDto dto = new ReviewDto(authorOptional.orElse(new Review()));
        model.addAttribute(MODEL_BOOK_ID, bookId);
        model.addAttribute(MODEL_REVIEW, dto);

        return VIEW_REVIEW_EDIT;
    }

    @PostMapping(REQUEST_REVIEW_EDIT)
    public String saveReview(@RequestParam long reviewId,
                             @RequestParam long bookId,
                             @ModelAttribute ReviewDto dto)
    {
        Optional<Review> reviewOptional = databaseService.getReviewById(reviewId);
        reviewOptional.ifPresent(dto::updateReview);
        Review review = reviewOptional.orElse(dto.createReview(
            databaseService.getBookById(bookId).orElseThrow(BookNotFoundException::new)
        ));
        databaseService.saveReview(review);

        return "redirect:/";
    }

    @PostMapping(REQUEST_REVIEW_DELETE)
    public String deleteReview(@RequestParam long reviewId, @RequestParam long bookId, Model model)
    {
        databaseService.removeReview(reviewId);

        return "redirect:/";
    }
}
