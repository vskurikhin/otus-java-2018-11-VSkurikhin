package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.Table;
import ru.otus.homework.models.Review;
import ru.otus.homework.services.DataTransformer;
import ru.otus.homework.services.ReviewService;
import ru.otus.homework.services.MessagesService;
import ru.otus.homework.services.ReviewTransformer;

import java.util.List;

@ShellComponent
public class ReviewCommands
{
    private MessagesService msg;

    private ReviewService reviewsService;

    private ReviewTransformer dataTransformer;

    public ReviewCommands(MessagesService msg, ReviewService reviewService)
    {
        this.msg = msg;
        this.reviewsService = reviewService;
        this.dataTransformer = new ReviewTransformer(reviewService);
    }

    @ShellMethod(value = "Show reviews from table", group = "Show")
    public Table showAllReviews()
    {
        List<Review> list = reviewsService.findAll();
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return dataTransformer.transformList(list).build();
    }

    @ShellMethod(value = "Show reviews from table", group = "Show")
    public Table showAllReviewsWithBook()
    {
        List<Review> list = reviewsService.findAllWithBook();
        System.out.println(msg.get("number_of_rows", new Object[]{list.size()}));

        return dataTransformer.transformListReviewAndBook(list).build();
    }

    @ShellMethod(value = "Insert review to table", group = "Insert")
    public String insertReview(String review, long bookId)
    {
        String sid = Long.toString(reviewsService.insert(review, bookId));

        return msg.get("inserted_with_id", new Object[]{sid});
    }

    @ShellMethod(value = "Update review in table", group = "Update")
    public String updateReview(long id, String review, long bookId)
    {
        String sid = Long.toString(reviewsService.update(id, review, bookId));

        return msg.get("record_with_with_id_updated", new Object[]{sid});
    }

    @ShellMethod(value = "Delete review from table", group = "Delete")
    public void deleteReview(long id)
    {
        reviewsService.delete(id);
    }
}
