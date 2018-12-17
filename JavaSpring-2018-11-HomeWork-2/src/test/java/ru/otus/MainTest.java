package ru.otus;

import org.apache.tools.ant.ExitException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.services.MessagesServiceImpl;
import ru.otus.services.TestQuestionReader;
import ru.otus.services.TestQuestionReaderReadArrayIndexOutOfBoundsException;
import ru.otus.services.TestQuestionReaderReadExceptionIO;

import java.security.Permission;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.services.MessagesServiceImplTest.mockUpLocaleMsg;

@DisplayName("Class Main")
class MainTest
{
    @Test
    @DisplayName("Main with null throws NullPointerException")
    void newMainNullPointerException()
    {
        assertThrows(NullPointerException.class, () ->
            new Main(null, null, null, null, null)
        );
        mockUpLocaleMsg();
        assertThrows(NullPointerException.class, () -> {
            new Main(
                new MessagesServiceImpl("en_US", null),
                null,
                null, null, null
            );
        });
        assertThrows(NullPointerException.class, () -> {
            Main main = new Main(
                new MessagesServiceImpl("en_US", null),
                new TestQuestionReader(),
                null, null, null
            );
            main.run();
        });
    }

    @Test
    @DisplayName("Main catched IOException, ExceptionIO")
    void newMainCatchExceptionIO()
    {
        mockUpLocaleMsg();
        new Main(
            new MessagesServiceImpl("en_US", null),
            new TestQuestionReaderReadExceptionIO(),
            null, null, null
        );
    }

    private static class NoExitSecurityManager extends SecurityManager
    {
        @Override
        public void checkPermission(Permission perm)
        {
            // allow anything.
        }
        @Override
        public void checkPermission(Permission perm, Object context)
        {
            // allow anything.
        }
        @Override
        public void checkExit(int status)
        {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }

    @Test
    @DisplayName("Main exit on ArrayIndexOutOfBoundsException")
    void newMainCatchArrayIndexOutOfBoundsException()
    {
        mockUpLocaleMsg();
        System.setSecurityManager(new NoExitSecurityManager());
        assertThrows(ExitException.class, () -> {
            new Main(
                new MessagesServiceImpl("en_US", null),
                new TestQuestionReaderReadArrayIndexOutOfBoundsException(),
                null, null, null
            );
        });
    }
}