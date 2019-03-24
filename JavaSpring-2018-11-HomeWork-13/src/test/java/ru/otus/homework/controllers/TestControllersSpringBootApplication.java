package ru.otus.homework.controllers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.homework.configs.YamlApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(YamlApplicationProperties.class)
@ComponentScan(basePackages = { "ru.otus.homework.configs" })
@ComponentScan(basePackages = { "ru.otus.homework.security" })
public class TestControllersSpringBootApplication { /* None */ }
