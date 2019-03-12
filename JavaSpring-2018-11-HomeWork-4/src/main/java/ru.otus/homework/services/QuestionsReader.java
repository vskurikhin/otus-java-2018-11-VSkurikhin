package ru.otus.homework.services;

import ru.otus.homework.models.Answer;
import ru.otus.homework.models.Question;
import ru.otus.homework.models.Questions;

import static ru.otus.outside.utils.StringHelper.split;

public interface QuestionsReader
{
    void read();

    default void doSomethingLine(String line, QuizFactory quizFactory, Questions questions)
    {
        String[] fields = split(line);

        Question question = quizFactory.getQuestionFactory().getObject();
        assert question != null;
        question.setQuestion(fields[0]);

        for (int i = 1; i < fields.length; i += 2) {
            Answer answer = quizFactory.getAnswerFactory().getObject();
            assert answer != null;
            answer.setAnswer(fields[i]);

            //noinspection ConstantConditions
            if (i < fields.length) {
                answer.setScore(Integer.parseInt(fields[i + 1]));
            }
            question.addAnswer(answer);
        }

        questions.addQuestion(question);
    }
}
