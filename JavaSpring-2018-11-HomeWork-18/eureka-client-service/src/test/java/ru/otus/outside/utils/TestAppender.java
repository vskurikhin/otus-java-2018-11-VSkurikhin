package ru.otus.outside.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.Stack;

public class TestAppender extends AppenderBase<ILoggingEvent>
{
    private Stack<ILoggingEvent> events = new Stack<>();

    @Override
    protected void append(ILoggingEvent event) {
        events.add(event);
    }

    public void clear() {
        events.clear();
    }

    public ILoggingEvent getLastEvent() {
        return events.pop();
    }
}
