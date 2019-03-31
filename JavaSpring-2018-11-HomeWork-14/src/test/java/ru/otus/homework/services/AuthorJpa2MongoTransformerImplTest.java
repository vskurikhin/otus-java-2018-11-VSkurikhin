package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.Main;
import ru.otus.homework.models.mongo.Author;
import ru.otus.homework.models.mongo.Book;
import ru.otus.homework.models.mongo.Genre;
import ru.otus.homework.models.mongo.Review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.shell.jline.InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED;
import static org.springframework.shell.jline.ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED;
import static ru.otus.outside.models.jpa.TestData.*;
import static ru.otus.outside.models.mongo.TestData.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class, properties = {
    SPRING_SHELL_INTERACTIVE_ENABLED + "=false", SPRING_SHELL_SCRIPT_ENABLED + "=false"
})

@DisplayName("Integration tests for data layer with class DataJpaService")
class AuthorJpa2MongoTransformerImplTest
{
    @BeforeEach
    void setUp() { }

    @Test
    void testAuthorJpa2MongoTransformer()
    {
        Author expected = createMongoAuthor0();
        Author author = AuthorJpa2MongoTransformer.transform(createJpaAuthor0());
        assertEquals(expected, author);
    }

    @Test
    void testBookJpa2MongoTransformer()
    {
        Book expected = createMongoBook0();
        Book book = BookJpa2MongoTransformer.transform(createJpaBook0());
        assertEquals(expected, book);
    }

    @Test
    void testGenreJpa2MongoTransformer()
    {
        Genre expected = createMongoGenre0();
        Genre genre = GenreJpa2MongoTransformer.transform(createJpaGenre0());
        assertEquals(expected, genre);
    }

    @Test
    void testReviewJpa2MongoTransformer()
    {
        Review expected = createMongoReview0();
        Review review = ReviewJpa2MongoTransformer.transform(createJpaReview0());
        assertEquals(expected, review);
    }
}