package ru.otus.homework.services;

import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public interface MessagesService
{
    String NL = System.lineSeparator();

    /**
     * Try to resolve the message. Treat as an error if the message can't be found.
     * @param code the code to lookup up, such as 'calculator.noRateSet'
     * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     * or {@code null} if none.
     * @return the resolved message
     * @throws NoSuchMessageException if the message wasn't found
     * @see java.text.MessageFormat
     */
    String get(String code) throws NoSuchMessageException;

    /**
     * Try to resolve the message. Treat as an error if the message can't be found.
     * @param code the code to lookup up, such as 'calculator.noRateSet'
     * @param args an array of arguments that will be filled in for params within
     * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     * or {@code null} if none.
     * @return the resolved message
     * @throws NoSuchMessageException if the message wasn't found
     * @see java.text.MessageFormat
     */
    String get(String code, Object[] args) throws NoSuchMessageException;

    String setLocale(String applicationLocale);
}
