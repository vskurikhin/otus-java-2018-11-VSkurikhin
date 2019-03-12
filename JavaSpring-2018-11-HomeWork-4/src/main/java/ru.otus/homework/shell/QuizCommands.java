package ru.otus.homework.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.services.*;

@ShellComponent
public class QuizCommands
{
    public final static String NL = System.lineSeparator();

    private MessagesService msg;

    private YamlProperties yp;

    private QuizHelper executor;

    public QuizCommands(MessagesService msg, YamlProperties yp, QuizHelper quizHelper)
    {
        this.msg = msg;
        this.yp = yp;
        this.executor = quizHelper;
        System.out.println(msg.get("start_quiz"));
    }

    @ShellMethod(key = "start", value = "Start quiz.")
    public String start()
    {
        executor.clear();
        // Show hint for exit
        return msg.get("exit_quit") + NL + msg.get("for_start") + NL;
    }

    @ShellMethod("Translate text from one language to another.")
    public String locale(@ShellOption(defaultValue = "en_US") String to)
    {
        yp.setLocale(to);
        return msg.setLocale(to);
    }

    @ShellMethodAvailability({"start", "locale"})
    public Availability availabilityCheckQuizLocale() {
        return ! executor.isStarted() && executor.isNotRegistered()
             ? Availability.available()
             : Availability.unavailable("you are started!");
    }

    @ShellMethod(key = "register", value = "Start quiz.")
    public String register(String firstName, String surName)
    {
        executor.questionsRead();
        executor.getPerson().setFirstName(firstName);
        executor.getPerson().setSurName(surName);

        return executor.register();
    }

    @ShellMethodAvailability({"register"})
    public Availability availabilityCheckRegister() {
        return executor.isNotRegistered()
             ? Availability.available()
             : Availability.unavailable("wrong questions!");
    }

    @ShellMethod(key = "answer", value = "Set answer number for current question.")
    public String answer(int number)
    {
        return executor.answer(number);
    }

    @ShellMethodAvailability({"answer"})
    public Availability availabilityCheckAnswer() {
        return executor.isStarted() && ! executor.isNotRegistered()
             ? Availability.available()
             : Availability.unavailable("you are not started!");
    }
}
