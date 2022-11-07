package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.MultiMap;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AddProductServletTest {

    protected AddProductServlet servlet;
    protected MockHtmlResponseWriter mockHtmlResponseWriter;
    protected MockProductDB mockProductDB;

    @Before
    public void setUp() {
        servlet = new AddProductServlet();
        mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;
        mockProductDB = new MockProductDB();
        servlet.productDB = mockProductDB;
    }

    @Test
    public void processRequestTest() throws IOException {
        Request request = new Request(null, null);
        MultiMap<String> parameters = new MultiMap<>();
        String name = "test-name";
        parameters.put("name", name);
        String price = "100";
        parameters.put("price", price);
        request.setQueryParameters(parameters);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(Utils.joinLines("OK"), mockHtmlResponseWriter.getHtml());
    }
}
