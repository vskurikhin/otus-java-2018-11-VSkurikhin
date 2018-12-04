package ru.otus.services;

import ru.otus.exeptions.ExceptionIO;
import ru.otus.models.IAnswer;
import ru.otus.models.IQuestion;
import ru.otus.models.ISetOfQuestions;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import static ru.otus.utils.IO.readFile;

public class CSVReader implements IReader
{
    private String filename = "";

    private ISetOfQuestions setOfQuestions;

    public CSVReader() { /* NOne */ }

    public CSVReader(String filename)
    {
        this.filename = filename;
    }

    public ISetOfQuestions getSetOfQuestions()
    {
        return setOfQuestions;
    }

    public void setSetOfQuestions(ISetOfQuestions setOfQuestions)
    {
        this.setOfQuestions = setOfQuestions;
    }

    @Override
    public void read(Supplier<IQuestion> getQuestionBean, Supplier<IAnswer> getAnswerBean) throws ExceptionIO
    {
        try {
            readFile(new File(filename), line -> {
                String[] fields = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

                IQuestion question = getQuestionBean.get();
                question.setQuestion(fields[0]);

                for (int i = 1; i < fields.length; i += 2) {
                    IAnswer answer = getAnswerBean.get();
                    answer.setAnswer(fields[i]);

                    //noinspection ConstantConditions
                    if (i < fields.length) {
                        answer.setScore(Integer.parseInt(fields[i + 1]));
                    }
                    question.addAnswer(answer);
                }

                setOfQuestions.addQuestion(question);
            });
        }
        catch (IOException e) {
            throw new ExceptionIO(e);
        }
    }
}
