package ru.otus.outside.utils;

import ru.otus.outside.exeptions.IORuntimeException;

import java.io.*;
import java.util.function.Consumer;

public class IOHelper
{
    public static BufferedReader getBufferedReaderFromString(String stream)
    {
        return new BufferedReader(new StringReader(stream));
    }

    public static BufferedReader getBufferedReader(File fin) throws FileNotFoundException
    {
        return new BufferedReader(new FileReader(fin));
    }

    public static BufferedReader getBufferedReader(Class<?> clazz, String resource)
    {
        return new BufferedReader(new InputStreamReader(
            clazz.getClassLoader().getResourceAsStream(resource)
        ));
    }

    public static void readFile(File fin, Consumer<String> workWithLine)
    {
        try (BufferedReader br = getBufferedReader(fin)) {
            String line;
            while ((line = br.readLine()) != null) {
                workWithLine.accept(line);
            }
        }
        catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static void readFile(Class<?> clazz, String resource, Consumer<String> workWithLine)
    {
        try (BufferedReader br = getBufferedReader(clazz, resource)) {
            String line;
            while ((line = br.readLine()) != null) {
                workWithLine.accept(line);
            }
        }
        catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static void readStringAsFile(String stream, Consumer<String> workWithLine)
    {
        try (BufferedReader br = getBufferedReaderFromString(stream)) {
            String line ;
            while ((line = br.readLine()) != null) {
                workWithLine.accept(line);
            }
        }
        catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}
