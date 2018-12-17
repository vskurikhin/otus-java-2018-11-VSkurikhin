package ru.otus.utils;

import java.util.ArrayList;

public class Strings
{
    public static String unQuote(String in)
    {
        if ('"' == in.charAt(0) && '"' == in.charAt(in.length() - 1)) {
            return in.substring(1, in.length() - 1);
        }

        return in;
    }

    public static String[] split(String s)
    {
        ArrayList<String> words = new ArrayList<>();

        boolean notInsideComma = true;
        int start = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == ',' && notInsideComma) {
                words.add(unQuote(s.substring(start, i)));
                start = i + 1;
            }
            else if (s.charAt(i) == '"')
                notInsideComma = ! notInsideComma;
        }

        words.add(unQuote(s.substring(start)));

        return words.toArray(new String[0]);
    }
}
