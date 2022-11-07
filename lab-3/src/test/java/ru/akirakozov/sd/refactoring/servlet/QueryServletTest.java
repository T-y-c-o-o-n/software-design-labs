package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.MultiMap;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class QueryServletTest {

    protected QueryServlet servlet;
    protected MockHtmlResponseWriter mockHtmlResponseWriter;
    protected MockProductDB mockProductDB;

    @Before
    public void setUp() {
        servlet = new QueryServlet();
        mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;
        mockProductDB = new MockProductDB();
        servlet.productDB = mockProductDB;
    }

    @Test
    public void maxCommandTest() throws IOException {
        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "max");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(
            Utils.joinLines("<html><body>", "<h1>Product with max price: </h1>", "product-2\t450</br>", "</body></html>"),
            mockHtmlResponseWriter.getHtml()
        );
    }

    @Test
    public void minCommandTest() throws IOException {
        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "min");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(
            Utils.joinLines("<html><body>", "<h1>Product with min price: </h1>", "product-1\t300</br>", "</body></html>"),
            mockHtmlResponseWriter.getHtml()
        );
    }

    @Test
    public void sumCommandTest() throws IOException {
        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "sum");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(
            Utils.joinLines("<html><body>", "Summary price: ", "750", "</body></html>"),
            mockHtmlResponseWriter.getHtml()
        );
    }

    @Test
    public void countCommandTest() throws IOException {
        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        parameters.put("command", "count");
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(
            Utils.joinLines("<html><body>", "Number of products: ", "2", "</body></html>"),
            mockHtmlResponseWriter.getHtml()
        );
    }

    @Test
    public void wrongCommandTest() throws IOException {
        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        String wrongCommand = "wrong";
        parameters.put("command", wrongCommand);
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(
            Utils.joinLines("Unknown command: " + wrongCommand),
            mockHtmlResponseWriter.getHtml()
        );
    }
}
