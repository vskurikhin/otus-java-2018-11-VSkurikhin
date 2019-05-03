package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Book;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.createBook0;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Class BookDao")
class BookDaoTest
{
    @Autowired
    protected BookDao repository;

    @Autowired
    protected GenreDao genreDao;

    public void saveNewBook(Book book)
    {
       genreDao.save(book.getGenre());
       repository.save(book);
    }

    @DisplayName("persists new when save")
    @Test
    void testCreate() throws Exception
    {
        Book expected = createBook0();
        saveNewBook(expected);
        Book test = repository.findById(expected.getId()).orElse(null);
        expected.setAuthors(Collections.emptyList());
        assertNotNull(test);
        test.setAuthors(Collections.emptyList());
        assertEquals(expected, test);
    }

    @DisplayName("merge detached object when save")
    @Test
    void testUpdate() throws Exception
    {
        Book expected = createBook0();
        saveNewBook(expected);
        expected.setTitle(expected.getTitle() + "_test");
        expected.setCopyright(expected.getCopyright() + "_test");
        repository.save(expected);
        Book test = repository.findById(expected.getId()).orElse(null);
        expected.setAuthors(Collections.emptyList());
        assertNotNull(test);
        test.setAuthors(Collections.emptyList());
        assertEquals(expected, test);
    }

    @Test
    void delete() throws Exception
    {
        Book expected = createBook0();
        saveNewBook(expected);
        List<Book> list = repository.findAll();
        assertFalse(list.isEmpty());
        repository.deleteById(expected.getId());
        Book nullResult = repository.findById(expected.getId()).orElse(null);
        assertNull(nullResult);
    }
}