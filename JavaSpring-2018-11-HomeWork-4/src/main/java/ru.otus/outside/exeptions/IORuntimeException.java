package ru.otus.outside.exeptions;

import java.io.IOException;

public class IORuntimeException extends RuntimeException
{
    public IORuntimeException(IOException e)
    {
        super(e);
    }
}
