package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Review;
import ru.otus.homework.services.*;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class ReviewCommands
{

    private MessagesService msg;

    private ReviewService reviewService;

    private ReviewsTransformer transformer;

    private DataStorageService storageService;

    public ReviewCommands(MessagesService msg, ReviewService reviewService, DataStorageService storageService)
    {
        this.msg = msg;
        this.reviewService = reviewService;
        this.transformer = new ReviewsTransformer();
        this.storageService = storageService;
    }

    @ShellMethod(value = "Show reviews from table", group = "Show")
    public Table showAllReviewsForBook(String bookId)
    {
        List<Review> list = storageService.getAllBookReviewsByBookId(bookId);
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return transformer.transformList(list).build();
    }

    @ShellMethod(value = "Insert review for book to table", group = "Insert")
    public String insertReviewForBook(String bookId, String review)
    {
        Optional<Book> optionalBook = storageService.getBookById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Review saved = storageService.saveBookWithReviews(reviewService.create(review, book));

            return msg.get("record_with_with_id_updated", new Object[]{saved.getId()});
        }

        return "Error";
    }

    @ShellMethod(value = "Update review in table", group = "Update")
    public String updateReview(String id, String review)
    {
        Optional<Review> optionalReview = storageService.getBookReviewById(id);
        if (optionalReview.isPresent()) {
            Review result = optionalReview.get();
            result.setReview(review);
            result = storageService.saveBookWithReviews(result);

            return msg.get("record_with_with_id_updated", new Object[]{result.getId()});
        }

        return "Error";
    }

    @ShellMethod(value = "Delete review from table", group = "Delete")
    public void deleteReview(String id)
    {
        storageService.removeBookReview(id);
    }
}
