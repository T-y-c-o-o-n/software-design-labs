package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.html.HtmlResponseWriter;

public class MockHtmlResponseWriter extends HtmlResponseWriter {
    @Override
    public void write() {
    }

    public String getHtml() {
        return this.html.toString();
    }
}
