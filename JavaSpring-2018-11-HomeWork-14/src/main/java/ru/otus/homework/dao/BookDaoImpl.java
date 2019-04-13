package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.mongo.Book;

import java.math.BigInteger;
import java.util.Objects;

@Repository
@Transactional
public class BookDaoImpl implements BookExtendDao
{
    private final AuthorDao authorDao;

    private final BookDao bookDao;

    private final GenreDao genreDao;

    private final ReviewDao reviewDao;

    @Lazy
    @Autowired
    public BookDaoImpl(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao, ReviewDao reviewDao)
    {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public Book saveWithGenreAndAuthors(Book book)
    {
        if ( ! Objects.isNull(book.getAuthors())) {
            book.getAuthors().forEach(authorDao::save);
        }

        if ( ! Objects.isNull(book.getGenre())) {
            genreDao.save(book.getGenre());
        }

        return bookDao.save(book);
    }

    @Override
    public void deleteByIdWithReviews(BigInteger id)
    {
        reviewDao.deleteByBookId(id);
        bookDao.deleteById(id);
    }
}
