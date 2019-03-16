package ru.otus.homework.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;

import java.util.Objects;

@Repository
@Transactional
public class BookRepositoryImpl implements BookExtendRepository
{
    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final ReviewRepository reviewRepository;

    @Lazy
    @Autowired
    public BookRepositoryImpl(AuthorRepository authorRepository, BookRepository bookRepository,
                                    GenreRepository genreRepository, ReviewRepository reviewRepository)
    {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Book saveWithAuthorsAndGenres(Book book)
    {
        if ( ! Objects.isNull(book.getAuthors())) {
            book.getAuthors().forEach(authorRepository::save);
        }

        if ( ! Objects.isNull(book.getGenres())) {
            book.getGenres().forEach(genreRepository::save);
        }

        return bookRepository.save(book);
    }

    @Override
    public void deleteByIdWithReviews(String id)
    {
        reviewRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }
}
