package ru.akirakozov.sd.refactoring.servlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GetProductsServletTest {

    protected GetProductsServlet servlet;
    protected MockHtmlResponseWriter mockHtmlResponseWriter;
    protected MockProductDB mockProductDB;

    @Before
    public void setUp() {
        servlet = new GetProductsServlet();
        mockHtmlResponseWriter = new MockHtmlResponseWriter();
        servlet.htmlResponseWriter = mockHtmlResponseWriter;
        mockProductDB = new MockProductDB();
        servlet.productDB = mockProductDB;
    }

    @Test
    public void processRequestTest() throws IOException {
        Request request = new Request(null, null);
        Response response = new Response(null, null);

        servlet.doGet(request, response);

        assertEquals(
            Utils.joinLines("<html><body>", "product-1\t300</br>", "product-2\t450</br>", "</body></html>"),
            mockHtmlResponseWriter.getHtml()
        );
    }
}
