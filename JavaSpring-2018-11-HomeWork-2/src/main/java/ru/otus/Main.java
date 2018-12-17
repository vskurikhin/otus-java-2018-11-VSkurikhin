package ru.otus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.otus.exeptions.ExceptionEmptyResource;
import ru.otus.exeptions.ExceptionIO;
import ru.otus.services.*;

@Service("application")
public class Main
{
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private QuizExecutor tester;

    private MessagesService msg;

    private void exitOnExceptionResource(Throwable exception)
    {
        System.err.println(
            msg.get("exception_resource", new Object[] {exception.toString(), exception.getMessage()})
        );
        LOGGER.error(exception);
        System.exit(-1);
    }

    @Autowired
    public Main(@Qualifier("msg") MessagesService msg,
                @Qualifier("reader") QuestionsReader questionsReader,
                @Qualifier("tester") QuizExecutor quizExecutor,
                AnswerFactory answerFactory, QuestionFactory questionFactory
    ){
        this.msg = msg;
        System.out.print(msg.get("hello_world") + " ");

        try {
            questionsReader.read(answerFactory, questionFactory);
        }
        catch (ExceptionIO exceptionIO) {
            LOGGER.error(exceptionIO);
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            exitOnExceptionResource(exception);
        }
        tester = quizExecutor;
    }

    void run()
    {
        try {
            tester.run();
        }
        catch (ExceptionEmptyResource exception) {
            exitOnExceptionResource(exception);
        }
    }

    private static ConfigurableApplicationContext getApplicationContext()
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationConfig.class);
        ctx.refresh();

        return ctx;
    }

    public static void main(String[] args)
    {
        getApplicationContext().getBean("application", Main.class).run();
    }
}
