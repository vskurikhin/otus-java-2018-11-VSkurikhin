package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ru.otus.homework.configs.YamlApplProperties;

@SpringBootApplication
@EnableConfigurationProperties(YamlApplProperties.class)
public class Main
{
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
