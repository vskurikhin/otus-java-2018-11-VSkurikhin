package ru.otus.services;

import ru.otus.exeptions.ExceptionEmptyResource;
import ru.otus.models.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class ConsoleQuizExecutor implements QuizExecutor
{
    public final static String NL = System.lineSeparator();

    private Scanner scanner;

    private PrintStream out;

    private String firstName;

    private String surName;

    private Questions questions;

    private MessagesService msg;

    public ConsoleQuizExecutor(InputStream in, PrintStream out, Questions questions, MessagesService msg)
    {
        this.out = out;
        this.scanner = new Scanner(in);
        this.questions = questions;
        this.msg = msg;
    }

    public Questions getQuestions()
    {
        return questions;
    }

    public void setQuestions(Questions questions)
    {
        this.questions = questions;
    }

    void showQuestion(String question)
    {
        out.println(msg.get("question") + ": " + question);
    }

    void showAnswers(List<Answer> answers)
    {
        int i = 1;
        for (Answer answer : answers) {
            out.println(i + ": " + answer.getAnswer());
            i++;
        }
    }

    int readAnswer(List<Answer> answers)
    {
        out.print(msg.get("enter_the_answer_number") + ": ");

        try {
            int result = scanner.nextInt();

            if (result > answers.size()) {
                return -1;
            }

            return result;
        } catch (InputMismatchException e) {
            return -2;
        } catch (NoSuchElementException e) {
            return -3;
        } catch (IllegalStateException e) {
            return -4;
        } catch (Exception e) {
            return Integer.MIN_VALUE; // Where got unknow error. No need show the error message.
        }
    }

    private String getErrorMessage(int answerIndex)
    {
        switch (answerIndex) {
            case -1:
                return msg.get("the_number_is_not_in_the_answers_range");
            case -2:
            case -3:
            case -4:
                return msg.get("input_mismatch") + " " + msg.get("token_does_not_match_the_int");
            default:
                return "Unknow error!!!";
        }
    }

    private void showErrorMessage(int answerIndex)
    {
        out.println(msg.get("error_message") + ": " + getErrorMessage(answerIndex));
    }

    private void readPersonalInfo()
    {
        out.print(NL + msg.get("enter_your_first_name") + ": ");
        firstName = scanner.nextLine();
        out.print(msg.get("enter_your_surname") + ": ");
        surName = scanner.nextLine();
    }

    @Override
    public void run() throws ExceptionEmptyResource
    {
        if (questions.size() < 1) {
            throw new ExceptionEmptyResource();
        }

        out.println(msg.get("enter_0_for_exit"));
        readPersonalInfo();

        for (Question question : questions) {

            out.println();
            int answerIndex = 0;

            do {
                showQuestion(question.getQuestion());
                showAnswers(question.getAnswers());
                answerIndex = readAnswer(question.getAnswers());

                if (answerIndex < 0) {
                    showErrorMessage(answerIndex);
                }
            } while (answerIndex < 0);

            if (0 == answerIndex) return;

            questions.addScore(question.getAnswers().get(answerIndex - 1).getScore());
        }

        out.println(NL + msg.get("dear") + " " + firstName + " " + surName);
        out.println(msg.get("your_score") + ": " + questions.getScore());
    }

    public int getScore()
    {
        return questions.getScore();
    }
}
