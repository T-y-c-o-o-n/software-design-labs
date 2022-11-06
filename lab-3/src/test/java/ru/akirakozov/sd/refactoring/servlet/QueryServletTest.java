package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.MultiMap;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class QueryServletTest {
    @Test
    public void maxCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "max");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        String contentType = response.getContentType();
        int status = response.getStatus();
        assertEquals("text/html", contentType);
        assertEquals(HttpServletResponse.SC_OK, status);
    }

    @Test
    public void minCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "min");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        String contentType = response.getContentType();
        int status = response.getStatus();
        assertEquals("text/html", contentType);
        assertEquals(HttpServletResponse.SC_OK, status);
    }

    @Test
    public void sumCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "sum");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        String contentType = response.getContentType();
        int status = response.getStatus();
        assertEquals("text/html", contentType);
        assertEquals(HttpServletResponse.SC_OK, status);
    }

    @Test
    public void countCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "count");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        String contentType = response.getContentType();
        int status = response.getStatus();
        assertEquals("text/html", contentType);
        assertEquals(HttpServletResponse.SC_OK, status);
    }

    @Test
    public void wrongCommandTest() throws IOException {
        QueryServlet servlet = new QueryServlet();

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "wrong");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        String contentType = response.getContentType();
        int status = response.getStatus();
        assertEquals("text/html", contentType);
        assertEquals(HttpServletResponse.SC_OK, status);
    }
}
