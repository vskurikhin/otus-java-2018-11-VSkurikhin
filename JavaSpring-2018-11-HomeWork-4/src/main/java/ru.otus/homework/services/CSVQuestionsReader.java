package ru.otus.homework.services;

import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.models.Questions;

import static ru.otus.outside.utils.IOHelper.readFile;

public class CSVQuestionsReader implements QuestionsReader
{
    private YamlProperties properties;

    private Questions questions;

    private QuizFactory quizFactory;

    public CSVQuestionsReader() { /* None */ }

    public CSVQuestionsReader(Questions questions, YamlProperties properties, QuizFactory quizFactory)
    {
        this.questions = questions;
        this.properties = properties;
        this.quizFactory = quizFactory;
    }

    public Questions getQuestions()
    {
        return questions;
    }

    public void setQuestions(Questions questions)
    {
        this.questions = questions;
    }

    @Override
    public void read()
    {
        String filename = String.format(properties.getFileNameTemplate(), properties.getLocale());
        readFile(this.getClass(), filename , line -> doSomethingLine(line, quizFactory, questions));
    }
}
