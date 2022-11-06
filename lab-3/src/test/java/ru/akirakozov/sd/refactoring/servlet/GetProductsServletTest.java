package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GetProductsServletTest {
    @Test
    public void processRequestTest() throws IOException {
        GetProductsServlet servlet = new GetProductsServlet();

        Request request = new Request(null, null);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        String contentType = response.getContentType();
        int status = response.getStatus();
        assertEquals("text/html", contentType);
        assertEquals(HttpServletResponse.SC_OK, status);
    }
}
