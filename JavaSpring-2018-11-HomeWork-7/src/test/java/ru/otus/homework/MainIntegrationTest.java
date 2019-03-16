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

    @Test
    void testShellAuthor()
    {
        assertThat(shell.evaluate(() -> "show-all-authors")).isNotNull();
        assertThat(shell.evaluate(() -> "insert-author --first-name FirstName --last-name LastName")).isNotNull();
        assertThat(shell.evaluate(() -> "update-author --id 1 --first-name NameFirst --last-name NameLast")).isNotNull();
        assertThat(shell.evaluate(() -> "delete-author --id 1")).isNotNull();
    }

    @Test
    void testShellBook()
    {
        assertThat(shell.evaluate(() -> "show-all-books")).isNotNull();
        assertThat(shell.evaluate(() -> "show-all-books-with-details")).isNotNull();
        assertThat(shell.evaluate(() -> "insert-book --isbn 0000000000 --title title --edition-number 1 --copyright 0000 --publisher-id 1 --genre-id 1")).isNotNull();
        assertThat(shell.evaluate(() -> "update-book --id 1 0000000001 --title Title --edition-number 1 --copyright 2000 --publisher-id 1 --genre-id 1")).isNotNull();
        assertThat(shell.evaluate(() -> "delete-book --id 1")).isNotNull();
    }

    @Test
    void testShellGenre()
    {
        assertThat(shell.evaluate(() -> "show-all-genres")).isNotNull();
        assertThat(shell.evaluate(() -> "insert-genre --genre genre")).isNotNull();
        assertThat(shell.evaluate(() -> "update-genre --id 1 --genre Genre")).isNotNull();
        assertThat(shell.evaluate(() -> "delete-genre --id 1")).isNotNull();
    }

    @Test
    void testShellPublishers()
    {
        assertThat(shell.evaluate(() -> "show-all-publishers")).isNotNull();
        assertThat(shell.evaluate(() -> "insert-publisher --publisher publisher")).isNotNull();
        assertThat(shell.evaluate(() -> "update-publisher --id 1 --publisher Publisher")).isNotNull();
        assertThat(shell.evaluate(() -> "delete-publisher --id 1")).isNotNull();
    }

    @Test
    void testShellReview()
    {
        assertThat(shell.evaluate(() -> "show-all-reviews")).isNotNull();
        assertThat(shell.evaluate(() -> "show-all-reviews-with-book")).isNotNull();
        assertThat(shell.evaluate(() -> "insert-review --review review --book-id 1")).isNotNull();
        assertThat(shell.evaluate(() -> "update-review --id 1 --review Review --book-id 1")).isNotNull();
        assertThat(shell.evaluate(() -> "delete-review --id 1")).isNotNull();
    }
}
