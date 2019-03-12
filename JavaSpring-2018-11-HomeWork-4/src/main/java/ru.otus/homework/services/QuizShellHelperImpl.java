package ru.otus.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.homework.models.*;
import ru.otus.outside.exeptions.IORuntimeException;
import ru.otus.outside.exeptions.ReadRuntimeException;

import java.util.Iterator;
import java.util.List;

@Service("shellQuizExecutor")
public class QuizShellHelperImpl implements QuizHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QuizShellHelperImpl.class);

    public final static String NL = System.lineSeparator();

    private MessagesService msg;

    private Questions questions;

    private QuestionsReader questionsReader;

    private Iterator<Question> currentQuestion;

    private List<Answer> answers;

    private Person person = new PersonImpl();

    public QuizShellHelperImpl(MessagesService msg, Questions questions, QuestionsReader reader)
    {
        this.msg = msg;
        this.questions = questions;
        this.questionsReader = reader;
    }

    @Override
    public Person getPerson()
    {
        return person;
    }

    private String msgGetQuestion(Question question)
    {
        return msg.get("next_question") + NL + question.getQuestion() + NL;
    }

    private String msgGetAnswers(Question question)
    {
        int i = 1;
        StringBuilder sb = new StringBuilder();

        for (Answer answer : question.getAnswers()) {
            sb.append(i).append(": ").append(answer.getAnswer()).append(NL);
            i++;
        }

        return sb.toString();
    }

    private String msgGetResult()
    {
        return NL + msg.get("dear_person", new Object[]{person.getFirstName(), person.getSurName()})
             + NL + msg.get("your_score") + ": " + questions.getScore();
    }

    @Override
    public void clear()
    {
        questions.clear();
        person.setFirstName(null);
        person.setSurName(null);
        currentQuestion = null;
        answers = null;
    }

    @Override
    public void questionsRead()
    {
        try {
            questionsReader.read();
        }
        catch (IORuntimeException | ArrayIndexOutOfBoundsException exception) {
            LOGGER.error("questionsRead", exception);
            throw new ReadRuntimeException(exception);
        }
        currentQuestion = questions.iterator();
    }

    private String msgGetAnswerCommand(Question question)
    {
        return msg.get("answer_command") + NL + msgGetQuestion(question) + msgGetAnswers(question);
    }

    @Override
    public String register()
    {
        currentQuestion = questions.iterator();

        // Increase index in questions
        if (currentQuestion.hasNext()) {
            Question question = currentQuestion.next();
            answers = question.getAnswers();

            // Show first question
            // Show Answers for first question
            return msgGetAnswerCommand(question);
        }

        return msg.get("empty_questions");
    }

    @Override
    public String answer(int number)
    {
        if (1 > number || number > answers.size()) {
            return msg.get("number_out_of_range");
        }

        questions.addScore(answers.get(number - 1).getScore());

        // Show first question
        if (currentQuestion.hasNext()) {
            Question question = currentQuestion.next();
            answers = question.getAnswers();

            return msgGetAnswerCommand(question);
        }
        else {
            String result = msgGetResult();
            clear();

            return result;
        }
    }

    @Override
    public boolean isNotRegistered()
    {
        return questions.size() == 0 && person.getFirstName() == null && person.getSurName() == null;
    }

    @Override
    public boolean isStarted()
    {
        return currentQuestion != null;
    }
}
