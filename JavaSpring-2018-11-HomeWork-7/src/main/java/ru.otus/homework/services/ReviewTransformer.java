package ru.otus.homework.services;

import org.springframework.shell.table.TableBuilder;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewTransformer extends DataTransformer<ReviewsService, Review>
{
    public ReviewTransformer(ReviewsService service)
    {
        super(service);
    }

    public TableBuilder transformListReviewAndBook(List<Review> reviews)
    {
        assert reviews != null;
        List<String[]> dataList = new ArrayList<>();
        dataList.add(service.getHeaderReviewAndBook());

        for (Review review : reviews) {
            dataList.add(service.mergeReviewAndBook(review, review.getBook()));
        }

        String[][] data = new String[dataList.size()][];
        data = dataList.toArray(data);

        return create(data).addFullBorder(style);
    }
}
