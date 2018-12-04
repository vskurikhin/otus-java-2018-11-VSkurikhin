package ru.otus.utils;

import ru.otus.exeptions.ExceptionIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class IO
{
    public static void readFile(File fin, Consumer<String> workWithLine) throws ExceptionIO
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fin))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                workWithLine.accept(line);
            }
        }
        catch (IOException e) {
            throw new ExceptionIO(e);
        }
    }
}
