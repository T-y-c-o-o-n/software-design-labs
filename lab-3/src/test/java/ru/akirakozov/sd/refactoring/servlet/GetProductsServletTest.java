package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GetProductsServletTest {
    @Test
    public void processRequestTest() throws IOException {
        GetProductsServlet servlet = new GetProductsServlet();
        MockHtmlResponseWriter mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;

        Request request = new Request(null, null);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(Utils.joinLines("<html><body>", "</body></html>"), mockHtmlResponseWriter.getHtml());
    }
}
