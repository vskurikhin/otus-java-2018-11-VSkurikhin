package ru.otus.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.configs.YamlProperties;
import ru.otus.homework.services.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("Main Integration Test")
class MainIntegrationTest
{
    @Autowired
    private MessagesService msg;

    @Autowired
    private YamlProperties yp;

    @Autowired
    Shell shell;

    @Test
    @DisplayName("YamlProperties: Bean and locale field.")
    void testYamlProperties()
    {
        assertThat(yp).hasFieldOrProperty("locale").isNotNull();
        assertThat(yp).hasFieldOrPropertyWithValue("locale", "en_US");
    }

    @Test
    @DisplayName("YamlProperties: getter and setter for locale.")
    void test()
    {
        String oldLocale = yp.getLocale();
        yp.setLocale("ru_RU");
        assertThat(yp).hasFieldOrProperty("locale").isNotNull();
        assertThat(yp).hasFieldOrPropertyWithValue("locale", "ru_RU");
        yp.setLocale(oldLocale);
    }
}
