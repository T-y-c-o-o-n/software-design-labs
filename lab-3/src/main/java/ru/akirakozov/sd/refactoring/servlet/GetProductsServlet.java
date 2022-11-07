package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.ProductDB;
import ru.akirakozov.sd.refactoring.html.HtmlResponseWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    ProductDB productDB = new ProductDB();
    HtmlResponseWriter htmlResponseWriter = new HtmlResponseWriter();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        htmlResponseWriter.setResponse(response);
        htmlResponseWriter.addHtml("<html><body>");
        productDB.listProducts().forEach(product ->
            htmlResponseWriter.addHtml(product.getName() + "\t" + product.getPrice() + "</br>")
        );
        htmlResponseWriter.addHtml("</body></html>");
        htmlResponseWriter.write();
    }
}
