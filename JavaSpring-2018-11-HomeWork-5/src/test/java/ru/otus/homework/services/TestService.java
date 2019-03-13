package ru.otus.homework.services;

import ru.otus.homework.models.TestEmpty;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static ru.otus.outside.utils.TestData.TEST_STRING_ARRAY_TEST_ID;
import static ru.otus.outside.utils.TestData.TEST_STRING_ARRAY_WITH_NULL;

public class TestService implements FindService<TestEmpty>
{
    public static final TestEmpty TEST_EMPTY = new TestEmpty();

    @Override
    public List<TestEmpty> findAll()
    {
        return new LinkedList<>(Collections.singleton(TEST_EMPTY));
    }

    @Override
    public String[] getHeader()
    {
        return TEST_STRING_ARRAY_TEST_ID;
    }

    @Override
    public String[] unfold(TestEmpty value)
    {
        return TEST_STRING_ARRAY_WITH_NULL;
    }

    @Override
    public TestEmpty findById(long id)
    {
        if (id < 0) {
            return null;
        }
        return TEST_EMPTY;
    }
}
