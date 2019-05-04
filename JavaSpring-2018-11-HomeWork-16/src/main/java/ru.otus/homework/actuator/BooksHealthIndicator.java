package ru.otus.homework.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.homework.dao.BookDao;

@Component
public class BooksHealthIndicator implements HealthIndicator
{
    private static final String KEY = "book.count";

    private static final int MINIMAL_BOOKS_COUNT = 3;

    private final BookDao bookDao;

    @Autowired
    public BooksHealthIndicator(BookDao bookDao)
    {
        this.bookDao = bookDao;
    }

    @Override
    public Health health()
    {
        long count = bookDao.count();

        if (bookDao.count() < MINIMAL_BOOKS_COUNT) {
            return Health.down().withDetail(KEY, count).build();
        }

        return Health.up().withDetail(KEY, count).build();
    }
}
