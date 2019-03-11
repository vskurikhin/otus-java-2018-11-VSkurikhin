package ru.otus.homework.services;

import java.io.InputStream;
import java.io.PrintStream;

public interface IOService
{
    InputStream getIn();

    PrintStream getOut();
}
