package ru.otus;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.models.Questions;
import ru.otus.services.*;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class ApplicationConfig
{
    @Value("${application.locale}")
    private String locale = "DEFAULT";

    @Value("${question.filename}")
    private String fileNameTemplate = "quests.csv";

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean("msg")
    public MessagesService msg()
    {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");

        return new MessagesServiceImpl(locale, ms);
    }

    @Bean("reader")
    public QuestionsReader reader(Questions questions)
    {
        String filename = String.format(fileNameTemplate, locale);

        return new CSVQuestionsReader(questions, filename);
    }

    @Bean("tester")
    public QuizExecutor tester(Questions questions, @Qualifier("msg") MessagesService msg)
    {
        return new ConsoleQuizExecutor(System.in, System.out, questions, msg);
    }
}

