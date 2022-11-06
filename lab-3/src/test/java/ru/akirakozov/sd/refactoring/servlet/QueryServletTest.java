package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.MultiMap;
import org.junit.Test;

import java.io.IOException;

public class QueryServletTest {
    @Test
    public void maxCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();
        MockHtmlResponseWriter mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "max");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

    }

    @Test
    public void minCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();
        MockHtmlResponseWriter mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "min");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

    }

    @Test
    public void sumCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();
        MockHtmlResponseWriter mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "sum");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

    }

    @Test
    public void countCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();
        MockHtmlResponseWriter mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "count");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

    }

    @Test
    public void wrongCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();
        MockHtmlResponseWriter mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "wrong");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

    }
}
