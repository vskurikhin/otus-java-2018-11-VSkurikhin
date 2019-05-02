package ru.otus.outside.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class IOHelper
{
    public static BufferedReader getBufferedReader(InputStream is)
    {
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    public static void readFile(InputStream is, Consumer<String> workWithLine)
    {
        try (BufferedReader br = getBufferedReader(is)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                workWithLine.accept(line);
            }
        }
        catch (IOException ignored) { /* None */ }
    }
}
