package ru.otus.homework.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.models.PersonImpl;
import ru.otus.homework.models.Questions;
import ru.otus.homework.models.QuestionsImpl;
import ru.otus.outside.exeptions.ReadRuntimeException;
import ru.otus.outside.utils.TestAppender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.homework.services.MessagesServiceImplTest.DEFAULT_SLOCALE;
import static ru.otus.homework.services.MessagesServiceImplTest.MESSAGE_SOURCE;
import static ru.otus.homework.services.TestDataQuestions.EMPTY;
import static ru.otus.homework.services.TestDataQuestions.QUEST;
import static ru.otus.homework.services.TestDataQuestions.QUEST_TWO;
import static ru.otus.outside.utils.IOHelperTestHelper.mockUp_IOHelper_getBufferedReader;
import static ru.otus.outside.utils.IOHelperTestHelper.mockUp_IOHelper_getBufferedReader_IORuntimeException;

@DisplayName("Class QuestionFactoryImpl")
class QuizShellHelperImplTest
{
    private final MessagesService msg = new MessagesServiceImpl(DEFAULT_SLOCALE, MESSAGE_SOURCE);

    private final Questions questions = new QuestionsImpl();

    private QuestionsReader reader;

    private QuizShellHelperImpl helper;

    @Test
    @DisplayName("is instantiated with new QuestionFactoryImpl()")
    void isInstantiatedWithNew()
    {
        new QuestionFactoryImpl();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        private TestAppender testAppender;

        @BeforeEach
        void createNewAnswer()
        {
            YamlProperties properties = mock(YamlProperties.class);
            when(properties.getFileNameTemplate()).thenReturn("test_%s.csv");
            when(properties.getLocale()).thenReturn("en_US");

            QuizFactory quizFactory = new QuizFactoryImpl(new AnswerFactoryImpl(), new QuestionFactoryImpl());
            reader = new CSVQuestionsReader(questions, properties, quizFactory);
            helper = new QuizShellHelperImpl(msg, questions, reader);

            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            testAppender = (TestAppender)root.getAppender("TEST");
            if (testAppender != null) {
                testAppender.clear();
            }
        }

        @Test
        @DisplayName("default values in QuestionsImpl()")
        void defaults()
        {
            assertThat(helper).hasFieldOrPropertyWithValue("msg", msg);
            assertThat(helper).hasFieldOrPropertyWithValue("questions", questions);
            assertThat(helper).hasFieldOrPropertyWithValue("questionsReader", reader);
            assertThat(helper).hasFieldOrPropertyWithValue("currentQuestion", null);
            assertThat(helper).hasFieldOrPropertyWithValue("answers", null);
            assertThat(helper).hasFieldOrProperty("person").isNotNull();
            assertTrue(helper.isNotRegistered());
            assertFalse(helper.isStarted());
        }

        @Test
        @DisplayName("Getter for person")
        void testGetterPerson()
        {
            assertEquals(new PersonImpl(), helper.getPerson());
            assertNull(helper.getPerson().getFirstName());
            assertNull(helper.getPerson().getSurName());
        }

        @Test
        @DisplayName("Clear for questions and person")
        void testClear()
        {
            helper.getPerson().setFirstName("testFirstName");
            helper.getPerson().setSurName("testSurName");
            assertNotNull(helper.getPerson().getFirstName());
            assertNotNull(helper.getPerson().getSurName());
            helper.clear();
            assertNull(helper.getPerson().getFirstName());
            assertNull(helper.getPerson().getSurName());
        }

        @Test
        @DisplayName("Read for questions")
        void testRead()
        {
            mockUp_IOHelper_getBufferedReader(EMPTY);

            helper.questionsRead();
            assertThat(helper).hasFieldOrProperty("currentQuestion").isNotNull();
            assertTrue(helper.isStarted());
        }

        @Test
        @DisplayName("Read for questions throw ReadRuntimeException")
        void testReadReadRuntimeException()
        {
            mockUp_IOHelper_getBufferedReader_IORuntimeException();

            assertThrows(ReadRuntimeException.class, () -> helper.questionsRead());

            ILoggingEvent lastEvent = testAppender.getLastEvent();
            assertEquals("questionsRead", lastEvent.getMessage());
            assertEquals(Level.ERROR, lastEvent.getLevel());
            assertThat(helper).hasFieldOrPropertyWithValue("currentQuestion", null);
        }

        @Test
        @DisplayName("Register for questions")
        void testRegister()
        {
            mockUp_IOHelper_getBufferedReader(QUEST);

            helper.questionsRead();
            assertThat(helper).hasFieldOrPropertyWithValue("answers", null);
            String result = helper.register();
            // TODO check result
            assertTrue(result.length() > 0);
            assertFalse(helper.isNotRegistered());
            assertThat(helper).hasFieldOrProperty("answers").isNotNull();
        }

        @Test
        @DisplayName("Register and questions empty")
        void testRegister2()
        {
            mockUp_IOHelper_getBufferedReader(EMPTY);

            helper.questionsRead();
            assertEquals("empty_questions", helper.register());
            assertThat(helper).hasFieldOrPropertyWithValue("answers", null);
        }

        @Test
        @DisplayName("Answer for questions NullPointerException")
        void testAnswerNullPointerException()
        {
            mockUp_IOHelper_getBufferedReader(EMPTY);

            helper.questionsRead();
            // wasn't call register
            assertThrows(NullPointerException.class, () -> helper.answer(Integer.MAX_VALUE));
        }

        @Test
        @DisplayName("Answer for questions NumberOutOfRange")
        void testAnswer_NumberOutOfRange()
        {
            mockUp_IOHelper_getBufferedReader(QUEST);

            helper.questionsRead();
            helper.register();
            assertEquals("number_out_of_range", helper.answer(0));
            assertEquals("number_out_of_range", helper.answer(Integer.MAX_VALUE));
        }

        @Test
        @DisplayName("Answer for last question")
        void testAnswerLast()
        {
            mockUp_IOHelper_getBufferedReader(QUEST);

            helper.questionsRead();
            helper.register();
            String result = helper.answer(1);
            // TODO check result
            assertTrue(result.length() > 0);
            assertThat(helper).hasFieldOrPropertyWithValue("currentQuestion", null);
            assertThat(helper).hasFieldOrPropertyWithValue("answers", null);
        }

        @Test
        @DisplayName("Answer for last question")
        void testAnswer()
        {
            mockUp_IOHelper_getBufferedReader(QUEST_TWO);

            helper.questionsRead();
            helper.register();
            String result = helper.answer(1);
            // TODO check result
            assertTrue(result.length() > 0);
        }
    }
}

