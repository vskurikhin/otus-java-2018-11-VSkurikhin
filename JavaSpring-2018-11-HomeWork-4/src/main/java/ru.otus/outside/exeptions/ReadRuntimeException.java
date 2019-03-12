package ru.otus.outside.exeptions;

public class ReadRuntimeException extends RuntimeException
{
    public ReadRuntimeException(Exception e)
    {
        super(e);
    }
}
