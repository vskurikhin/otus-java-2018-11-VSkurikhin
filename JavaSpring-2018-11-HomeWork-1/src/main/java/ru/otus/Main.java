package ru.otus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.exeptions.ExceptionIO;
import ru.otus.models.IAnswer;
import ru.otus.models.IQuestion;
import ru.otus.services.ConsoleTester;
import ru.otus.services.IReader;

import java.util.function.Supplier;

public class Main
{
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");

        Supplier<IAnswer> getAnswerBean = () -> ctx.getBean("answer", IAnswer.class);
        Supplier<IQuestion> getQuestionBean = () -> ctx.getBean("question", IQuestion.class);
        IReader reader = ctx.getBean("reader", IReader.class);

        try {
            reader.read(getQuestionBean, getAnswerBean);
        }
        catch (ExceptionIO exceptionIO) {
            LOGGER.error(exceptionIO);
        }
        ConsoleTester exam = ctx.getBean("exam", ConsoleTester.class);
        exam.run();
    }
}