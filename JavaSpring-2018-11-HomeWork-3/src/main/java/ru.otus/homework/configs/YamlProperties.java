package ru.otus.homework.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class YamlProperties
{
    private String locale;

    private String fileNameTempalate;

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public String getFileNameTempalate()
    {
        return fileNameTempalate;
    }

    public void setFileNameTempalate(String fileNameTempalate)
    {
        this.fileNameTempalate = fileNameTempalate;
    }
}
