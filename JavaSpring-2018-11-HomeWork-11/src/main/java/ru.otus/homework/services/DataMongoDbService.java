package ru.otus.homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.dao.ReviewDao;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.models.Review;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class DataMongoDbService implements DatabaseService
{
    private AuthorDao authorDao;

    private BookDao bookDao;

    private GenreDao genreDao;

    private ReviewDao reviewDao;

    @Autowired
    public DataMongoDbService(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao, ReviewDao reviewDao)
    {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public Mono<Long> countBooksByAuthorId(BigInteger id)
    {
        return bookDao.countByAuthorId(id);
    }

    @Override
    public Mono<Author> getAuthorById(BigInteger id)
    {
        return authorDao.findById(id);
    }

    @Override
    public Mono<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName)
    {
        return authorDao.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Flux<Author> getAllAuthors()
    {
        return authorDao.findAll();
    }

    @Override
    public Flux<Author> getAuthorsForBookId(BigInteger id)
    {
        return bookDao.findById(id).map(Book::getAuthors).flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Author> saveAuthor(Author author)
    {
        return authorDao.save(author);
    }

    @Override
    public void removeAuthor(BigInteger id)
    {
        authorDao.deleteById(id).block();
    }

    @Override
    public Mono<Void> removeAuthorAsync(BigInteger id)
    {
        return authorDao.deleteById(id);
    }

    @Override
    public Mono<Author> addAuthorToBook(Book book, Author author)
    {
        return authorDao.save(author).flatMap(a -> {
            if ( ! book.getAuthors().contains(a)) {
                book.getAuthors().add(a);
                return bookDao.save(book).map(b -> a);
            }
            return Mono.just(a);
        });
    }


    @Override
    public Mono<Book> getBookById(BigInteger id)
    {
        return bookDao.findById(id);
    }

    @Override
    public Flux<Book> getAllBooks()
    {
        return bookDao.findAll();
    }

    @Override
    public Mono<Book> saveBook(Book book)
    {
        assert book != null;

        return bookDao.save(book);
    }

    @Override
    public Mono<Book> saveBookNewGenre(Book book, String genreValue)
    {
        assert book != null;
        assert genreValue != null;

        Genre genre = new Genre();
        genre.setValue(genreValue);

        return genreDao.save(genre).flatMap(g -> {
            book.setGenre(g);
            return bookDao.save(book);
        });
    }

    @Override
    public Book saveBookWithAuthorsAndGenre(Book book)
    {
        assert book != null;

        if (Objects.isNull(book.getAuthors())) {
            book.setAuthors(new LinkedList<>());
        }

        Mono<List<Author>> listMono = Flux.fromStream(book.getAuthors().stream())
            .flatMap(authorDao::save)
            .collectList();
        Mono<Genre> monoGenre = genreDao.save(book.getGenre());

        return Flux.combineLatest(listMono, monoGenre, (authors, genre) -> {
            book.setGenre(genre);
            book.setAuthors(authors);
            return book;
        }).flatMap(bookDao::save).blockFirst();
    }

    @Override
    public void removeBook(BigInteger id)
    {
        bookDao.deleteById(id).subscribe().dispose();
    }


    @Override
    public Mono<Long> countReviewsByBookId(BigInteger id)
    {
        return bookDao.findById(id).flatMap(book -> reviewDao.countByBook(book));
    }

    @Override
    public Mono<Genre> getGenreById(BigInteger id)
    {
        return genreDao.findById(id);
    }

    @Override
    public Mono<Genre> getGenreByValue(String genre)
    {
        return genreDao.findByValue(genre);
    }

    @Override
    public Flux<Genre> getAllGenres()
    {
        return genreDao.findAll();
    }

    @Override
    public Flux<Review> getAllReviewsForBookById(BigInteger id)
    {
        // return reviewDao.findAllByBookId(id);
        return bookDao.findById(id).flatMapMany(book -> reviewDao.findAllByBook(book));
    }

    @Override
    public Mono<Genre> saveGenre(Genre genre)
    {
        assert genre != null;
        return genreDao.save(genre);
    }

    @Override
    public void removeGenre(BigInteger id)
    {
        genreDao.deleteById(id).subscribe().dispose();
    }


    @Override
    public Mono<Review> getReviewById(BigInteger id)
    {
        return reviewDao.findById(id);
    }

    @Override
    public Flux<Review> getAllReviews()
    {
        return reviewDao.findAll();
    }

    @Override
    public Mono<Review> saveReview(Review review)
    {
        assert review != null;
        return reviewDao.save(review);
    }

    /*
    @Override
    public void removeReview(BigInteger id)
    {
        reviewDao.deleteById(id).block();
    } */

    @Override
    public Mono<Void> removeReview(BigInteger id)
    {
        return reviewDao.deleteById(id);
    }
}
