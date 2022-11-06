package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.MultiMap;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AddProductServletTest {
    @Test
    public void processRequestTest() throws IOException {
        AddProductServlet servlet = new AddProductServlet();

        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        String name = "test-name";
        parameters.put("name", name);
        String price = "100";
        parameters.put("price", price);
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        String contentType = response.getContentType();
        int status = response.getStatus();
        assertEquals("text/html", contentType);
        assertEquals(HttpServletResponse.SC_OK, status);
    }
}