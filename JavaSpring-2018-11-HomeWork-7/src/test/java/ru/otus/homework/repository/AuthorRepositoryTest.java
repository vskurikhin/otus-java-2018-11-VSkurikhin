package ru.otus.homework.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Class AuthorRepository")
class AuthorRepositoryTest
{
    @Autowired
    private AuthorRepository repository;

    @DisplayName("persists new when save")
    @Test
    void testCreate() throws Exception
    {
        Author expected = createAuthor0();
        repository.save(expected);
        Author test = repository.findById(expected.getId()).orElse(null);
        assertEquals(expected, test);
    }

    @DisplayName("merge detached object when save")
    @Test
    void testUpdate() throws Exception
    {
        Author expected = createAuthor0();
        repository.save(expected);
        expected.setFirstName(expected.getFirstName() + "_test");
        expected.setLastName(expected.getLastName() + "_test");
        repository.save(expected);
        Author test = repository.findById(expected.getId()).orElse(null);
        assertEquals(expected, test);
    }

    @Test
    void delete() throws Exception
    {
        Author expected = createAuthor0();
        repository.save(expected);
        List<Author> list = repository.findAll();
        assertFalse(list.isEmpty());
        repository.delete(expected);
        Author nullResult = repository.findById(expected.getId()).orElse(null);
        assertNull(nullResult);
    }
}