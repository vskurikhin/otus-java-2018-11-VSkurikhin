package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.services.QuizExecutor;

@SpringBootApplication
@EnableConfigurationProperties(YamlProperties.class)
public class Main
{
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        ctx.getBean(QuizExecutor.class).run();
    }
}
