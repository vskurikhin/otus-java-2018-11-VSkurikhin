package ru.otus.exeptions;

import java.io.IOException;

public class ExceptionIO extends IOException
{
    public ExceptionIO(IOException e)
    {
        super(e);
    }
}
