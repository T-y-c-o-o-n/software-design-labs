package ru.akirakozov.sd.refactoring.html;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HtmlResponseWriter {
    private HttpServletResponse response;
    protected StringBuilder html;

    public HtmlResponseWriter() {
        html = new StringBuilder();
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void addHtml(String str) {
        html.append(str).append((char) Character.LINE_SEPARATOR);
    }

    public void write() throws IOException {
        response.getWriter().println(html);
        html = new StringBuilder();
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
