package ru.otus.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.models.AnswerImpl;
import ru.otus.homework.models.QuestionImpl;
import ru.otus.homework.models.Questions;
import ru.otus.homework.services.*;
import ru.otus.homework.shell.QuizCommands;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Main Integration Test")
class MainIntegrationTest
{
    @Autowired
    private MessagesService msg;

    @Test
    @DisplayName("MessagesService: msg Bean")
    void testMessagesService()
    {
        assertEquals("Hello world.", msg.get("hello_world"));
    }

    @Autowired
    private Questions questions;

    @Test
    @DisplayName("Questions: questions Bean")
    void testQuestions()
    {
        assertEquals(0, questions.size());
    }

    @Autowired
    QuestionsReader reader;

    @Test
    @DisplayName("QuestionsReader: reader Bean")
    void testQuestionsReader()
    {
        assertThat(reader).hasFieldOrPropertyWithValue("questions", questions);
    }

    @Autowired
    AnswerFactory answerFactory;

    @Test
    @DisplayName("AnswerFactory: answerFactory BeanFactory")
    void testAnswerFactory()
    {
        assertEquals(new AnswerImpl(), answerFactory.getObject());
    }

    @Autowired
    QuestionFactory questionFactory;

    @Test
    @DisplayName("QuestionFactory: questionFactory BeanFactory")
    void testQuestionFactory()
    {
        assertEquals(new QuestionImpl(), questionFactory.getObject());
    }

    @Autowired
    YamlProperties yp;

    @Autowired
    QuizHelper quizHelper;

    @Autowired
    QuizCommands quizCommands;

    @Test
    @DisplayName("QuizHelper: tester Bean")
    void testQuizExecutor()
    {
        assertThat(quizCommands).hasFieldOrPropertyWithValue("msg", msg);
        assertThat(quizCommands).hasFieldOrPropertyWithValue("yp", yp);
        assertThat(quizCommands).hasFieldOrPropertyWithValue("executor", quizHelper);
    }
}
