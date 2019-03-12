package ru.otus.homework.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class YamlProperties
{
    private String locale;

    private String fileNameTemplate;

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public String getFileNameTemplate()
    {
        return fileNameTemplate;
    }

    public void setFileNameTemplate(String fileNameTemplate)
    {
        this.fileNameTemplate = fileNameTemplate;
    }
}
