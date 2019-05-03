package ru.otus.homework.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class YamlApplicationProperties
{
    private String rememberKey = "key";

    private String locale = "en_US";

    private int strength = 4;

    public String getRememberKey()
    {
        return rememberKey;
    }

    public void setRememberKey(String rememberKey)
    {
        this.rememberKey = rememberKey;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public int getStrength()
    {
        return strength;
    }

    public void setStrength(int strength)
    {
        this.strength = strength;
    }
}
