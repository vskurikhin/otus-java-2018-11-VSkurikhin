package ru.otus.homework.services;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class MessagesServiceImpl implements MessagesService
{
    private Locale locale;

    private MessageSource ms;

    public MessagesServiceImpl(String applicationLocale, MessageSource messageSource)
    {
        LocaleEnum localeEnum = LocaleEnum.valueOf(applicationLocale);
        ms = messageSource;

        switch (localeEnum) {
            case ru_RU:
                locale = new Locale("ru", "RU"); break;
            case en_US:
            default:
                locale = Locale.ENGLISH; break;
        }
    }

    @Override
    public String get(String code) throws NoSuchMessageException
    {
        return ms.getMessage(code, null, locale);
    }

    @Override
    public String get(String code, Object[] args) throws NoSuchMessageException
    {
        return ms.getMessage(code, args, locale);
    }
}
