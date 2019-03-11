package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Class MessagesServiceImpl")
public class MessagesServiceImplTest
{
    static final String DEFAULT_SLOCALE = "en_US";
    static final String RU_RU_SLOCALE = "ru_RU";
    static final Locale RU_RU_LOCALE = new Locale("ru", "RU");

    public static final String TEST = "test";

    public static class TestMessageSource extends AbstractMessageSource
    {
        @Override
        protected MessageFormat resolveCode(String code, Locale locale)
        {
            return new MessageFormat(code);
        }
    }

    public static TestMessageSource createTestMessageSource()
    {
        return new TestMessageSource();
    }

    public static MessageSource createMockMessageSource()
    {
        MessageSource mockMessageSource = mock(MessageSource.class);
        when(mockMessageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn(TEST);

        return mockMessageSource;
    }

    static final MessageSource MESSAGE_SOURCE = createTestMessageSource();

    MessagesServiceImpl messagesService;

    @Test
    @DisplayName("is instantiated with new MessagesServiceImpl()")
    void isInstantiatedWithNew()
    {
        new MessagesServiceImpl(DEFAULT_SLOCALE, MESSAGE_SOURCE);
    }

    @Nested
    @DisplayName("when new default locale")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            messagesService = new MessagesServiceImpl(DEFAULT_SLOCALE, MESSAGE_SOURCE);
        }

        @Test
        @DisplayName("default values in QuestionsImpl()")
        void defaults()
        {
            assertThat(messagesService).hasFieldOrPropertyWithValue("locale", Locale.ENGLISH);
            assertThat(messagesService).hasFieldOrPropertyWithValue("ms", MESSAGE_SOURCE);
        }

        @Test
        @DisplayName("The method get")
        void testGet()
        {
            assertEquals(TEST, messagesService.get(TEST));
        }
    }

    @Test
    @DisplayName("when new with ru_RU locale")
    void testRuRuLocale()
    {
        MessagesServiceImpl ms = new MessagesServiceImpl(RU_RU_SLOCALE, MESSAGE_SOURCE);
        assertThat(ms).hasFieldOrPropertyWithValue("locale", RU_RU_LOCALE);
    }

    @Test
    @DisplayName("The method get when new mock getMessage(anyString, any Object[], any Locale)")
    void testGetMessage()
    {
        MessageSource mockMessageSource = createMockMessageSource();
        MessagesServiceImpl mockMessageService = new MessagesServiceImpl(DEFAULT_SLOCALE, mockMessageSource);
        assertEquals(TEST, mockMessageService.get(TEST, new Object[]{TEST}));
    }
}