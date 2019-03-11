package ru.otus.homework.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.homework.models.Questions;
import ru.otus.homework.services.*;

@Configuration
@ComponentScan
public class ApplicationConfig
{
    private YamlProperties yp;

    public ApplicationConfig(YamlProperties yamlProperties) {
        yp = yamlProperties;
    }

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

        return new MessagesServiceImpl(yp.getLocale(), ms);
    }

    @Bean("reader")
    public QuestionsReader reader(Questions questions)
    {
        String filename = String.format(yp.getFileNameTempalate(), yp.getLocale());

        return new CSVQuestionsReader(questions, filename);
    }
}

