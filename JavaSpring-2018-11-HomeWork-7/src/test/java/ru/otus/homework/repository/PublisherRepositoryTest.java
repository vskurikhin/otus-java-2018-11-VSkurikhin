package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.models.Publisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.createPublisher0;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Class PublisherRepository")
class PublisherRepositoryTest
{
    @Autowired
    private PublisherRepository repository;

    @DisplayName("persists new when save")
    @Test
    void testCreate() throws Exception
    {
        Publisher expected = createPublisher0();
        repository.save(expected);
        Publisher test = repository.findById(expected.getId()).orElse(null);
        assertEquals(expected, test);
    }

    @DisplayName("merge detached object when save")
    @Test
    void testUpdate() throws Exception
    {
        Publisher expected = createPublisher0();
        repository.save(expected);
        expected.setPublisherName(expected.getPublisherName() + "_test");
        repository.save(expected);
        Publisher test = repository.findById(expected.getId()).orElse(null);
        assertEquals(expected, test);
    }

    @Test
    void delete() throws Exception
    {
        Publisher expected = createPublisher0();
        repository.save(expected);
        List<Publisher> list = repository.findAll();
        assertFalse(list.isEmpty());
        repository.delete(expected);
        Publisher nullResult = repository.findById(expected.getId()).orElse(null);
        assertNull(nullResult);
    }
}