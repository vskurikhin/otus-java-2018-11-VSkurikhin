package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ru.otus.homework.configs.YamlProperties;

@SpringBootApplication
@EnableConfigurationProperties(YamlProperties.class)
public class Main
{
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
