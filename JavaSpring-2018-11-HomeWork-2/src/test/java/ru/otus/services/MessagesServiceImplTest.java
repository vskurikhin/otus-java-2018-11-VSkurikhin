package ru.otus.services;

import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@DisplayName("Class MessagesServiceImpl")
public class MessagesServiceImplTest
{
    public static void mockUpAnnotationConfigApplicationContext()
    {
        new MockUp<AnnotationConfigApplicationContext>()
        {
            @Mock
            public void $init()
            {
                //Dont assign name variable at all
                //Leave it null
            }
        };
    }

    public static void mockUpLocaleMsg()
    {
        new MockUp<MessagesServiceImpl>()
        {
            @Mock
            public void $init(String myLocale, MessageSource messageSource)
            {
                //Dont assign name variable at all
                //Leave it null
            }

            @Mock
            public String get(String code)
            {
                return "test";
            }

            @Mock
            public String get(String code, Object[] args)
            {
                return "test";
            }
        };
    }

    public static void mockUpLocaleMsgGet_filename_of_question()
    {
        new MockUp<MessagesServiceImpl>()
        {
            @Mock
            public void $init(String myLocale, MessageSource messageSource)
            {
                //Dont assign name variable at all
                //Leave it null
            }

            @Mock
            public String get(String code)
            {
                return "filename_of_question".equals(code) ? "test" : null;
            }
        };
    }
}