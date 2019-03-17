package ru.otus.homework.services;

import ru.otus.homework.models.Author;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.util.*;

import static ru.otus.outside.utils.StringHelper.stringOrNULL;

public class ReviewsTransformer extends DataTransformer<Review>
{
    public static String[] FIND_ALL_HEADER = {
        "id", "review", "isbn", "title", "edition_number", "copyright"
    };

    @Override
    public String[] getHeader()
    {
        return FIND_ALL_HEADER;
    }

    @Override
    public List<String[]> unfold(Review entry)
    {
        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        }

        List<String[]> result = new ArrayList<>();

        String id = stringOrNULL(entry.getId());
        String review = stringOrNULL(entry.getReview());
        String isbn = "NULL";
        String title = "NULL";
        String editionNumber = "NULL";
        String copyright = "NULL";

        if ( ! Objects.isNull(entry.getBook())) {
            isbn = entry.getBook().getIsbn();
            title = entry.getBook().getTitle();
            editionNumber = Integer.toString(entry.getBook().getEditionNumber());
            copyright = entry.getBook().getCopyright();
        }

        result.add(new String[]{id, review, isbn, title, editionNumber, copyright});

        return result;
    }
}
