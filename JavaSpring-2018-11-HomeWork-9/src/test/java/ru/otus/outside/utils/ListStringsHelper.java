package ru.otus.outside.utils;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ListStringsHelper
{
    public static void printListStrings(PrintStream ps, List<String[]> listStrings)
    {
        listStrings.forEach(strings -> ps.println(Arrays.toString(strings)));
    }

    public static void assertListStringsEquals(List<String[]> expected, List<String[]> testList)
    {
        for (int i = 0; i < expected.size(); i++)
        {
            assertArrayEquals(expected.get(i), testList.get(i));
        }
    }
}
