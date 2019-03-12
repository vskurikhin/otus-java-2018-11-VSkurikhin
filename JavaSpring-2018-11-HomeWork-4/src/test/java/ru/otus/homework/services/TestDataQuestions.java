package ru.otus.homework.services;

import ru.otus.homework.models.*;

import java.util.Arrays;
import java.util.Collections;

public class TestDataQuestions
{
    public static final String EMPTY = "";
    public static final String QUEST_ONE = "\"Q1?\"";
    public static final String QUEST_BADLINE = "\"Q1?\",\"A1.1\"";
    public static final String QUEST = "\"Q1?\",\"A1.1\",1,\"A1.2\",2,\"A1.3\",3,\"A1.4\",4,\"A1.5\",5";
    public static final String QUEST_TWO = "\"Q1?\",\"A1.1\",0,\"A1.2\",1,\"A1.3\",2,\"A1.4\",3,\"A1.5\",4"
                + System.lineSeparator() + "\"Q2?\",\"A2.1\",5,\"A2.2\",6,\"A2.3\",7,\"A2.4\",8,\"A2.5\",9";

    public static Questions createTestQuestions()
    {
        QuestionsImpl expected = new QuestionsImpl();
        AnswerImpl[] aa = new AnswerImpl[] {
            new AnswerImpl(), new AnswerImpl(), new AnswerImpl(), new AnswerImpl(), new AnswerImpl()
        };
        aa[0].setAnswer("A1.1");
        aa[0].setScore(1);
        aa[1].setAnswer("A1.2");
        aa[1].setScore(2);
        aa[2].setAnswer("A1.3");
        aa[2].setScore(3);
        aa[3].setAnswer("A1.4");
        aa[3].setScore(4);
        aa[4].setAnswer("A1.5");
        aa[4].setScore(5);
        QuestionImpl q1 = new QuestionImpl();
        q1.setQuestion("Q1?");
        q1.setAnswers(Arrays.asList(aa));
        expected.setQuestions(Collections.singletonList(q1));
        return expected;
    }
}
