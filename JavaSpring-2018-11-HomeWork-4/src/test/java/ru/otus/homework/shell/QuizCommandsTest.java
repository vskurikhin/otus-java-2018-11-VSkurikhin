package ru.otus.homework.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.models.Person;
import ru.otus.homework.models.PersonImpl;
import ru.otus.homework.services.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.homework.services.MessagesServiceImplTest.DEFAULT_SLOCALE;
import static ru.otus.homework.services.MessagesServiceImplTest.MESSAGE_SOURCE;

@DisplayName("Class QuizCommandsTest")
class QuizCommandsTest
{
    private final MessagesService msg = new MessagesServiceImpl(DEFAULT_SLOCALE, MESSAGE_SOURCE);

    private QuizCommands quizCommands;

    @Test
    @DisplayName("is instantiated with new QuizCommands()")
    void isInstantiatedWithNew() {
        new QuizCommands(msg, null, null);
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        private YamlProperties yamlProperties = new YamlProperties();

        private Person person = new PersonImpl("testFirstName", "testSurName");

        @BeforeEach
        void createNewQuestion()
        {
            YamlProperties properties = mock(YamlProperties.class);
            when(properties.getFileNameTemplate()).thenReturn("test_%s.csv");
            when(properties.getLocale()).thenReturn("en_US");

            QuizHelper helper = mock(QuizShellHelperImpl.class);
            doNothing().when(helper).questionsRead();
            when(helper.register()).thenReturn("register");
            when(helper.getPerson()).thenReturn(person);
            when(helper.answer(0)).thenReturn("number_out_of_range");

            quizCommands = new QuizCommands(msg, yamlProperties, helper);
        }

        @Test
        @DisplayName("default values in QuizCommands()")
        void defaults()
        {
            assertThat(quizCommands).hasFieldOrPropertyWithValue("yp", yamlProperties);
            assertThat(quizCommands).hasFieldOrPropertyWithValue("msg", msg);
        }

        @Test
        @DisplayName("start command")
        void testStart()
        {
            String result = quizCommands.start();
            // TODO check result
            assertTrue(result.length() > 0);
        }

        @Test
        @DisplayName("locale command")
        void testLocale()
        {
            assertTrue(quizCommands.locale("en_US").length() > 0);
            assertTrue(quizCommands.locale("ru_RU").length() > 0);
        }

        @Test
        @DisplayName("register command")
        void testRegister()
        {
            assertEquals("register", quizCommands.register(person.getFirstName(), person.getSurName()));
        }

        @Test
        @DisplayName("answer command")
        void testAnswer()
        {
            assertEquals("number_out_of_range", quizCommands.answer(0));
        }
    }
}