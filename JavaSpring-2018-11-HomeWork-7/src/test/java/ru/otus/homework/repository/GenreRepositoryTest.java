package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.createGenre0;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Class GenreRepository")
class GenreRepositoryTest
{
    @Autowired
    private GenreRepository repository;

    @DisplayName("persists new when save")
    @Test
    void testCreate() throws Exception
    {
        Genre expected = createGenre0();
        repository.save(expected);
        Genre test = repository.findById(expected.getId()).orElse(null);
        assertEquals(expected, test);
    }

    @DisplayName("merge detached object when save")
    @Test
    void testUpdate() throws Exception
    {
        Genre expected = createGenre0();
        repository.save(expected);
        expected.setGenre(expected.getGenre() + "_test");
        repository.save(expected);
        Genre test = repository.findById(expected.getId()).orElse(null);
        assertEquals(expected, test);
    }

    @Test
    void delete() throws Exception
    {
        Genre expected = createGenre0();
        repository.save(expected);
        List<Genre> list = repository.findAll();
        assertFalse(list.isEmpty());
        repository.delete(expected);
        Genre nullResult = repository.findById(expected.getId()).orElse(null);
        assertNull(nullResult);
    }
}