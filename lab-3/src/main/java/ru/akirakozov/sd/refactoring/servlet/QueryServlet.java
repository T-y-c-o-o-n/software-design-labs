package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.Product;
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
public class QueryServlet extends HttpServlet {

    ProductDB productDB = new ProductDB();
    HtmlResponseWriter htmlResponseWriter = new HtmlResponseWriter();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        htmlResponseWriter.setResponse(response);

        String command = request.getParameter("command");

        if ("max".equals(command)) {
            htmlResponseWriter.addHtml("<html><body>");
            htmlResponseWriter.addHtml("<h1>Product with max price: </h1>");
            Product product = productDB.maxProduct();
            htmlResponseWriter.addHtml(product.getName() + "\t" + product.getPrice() + "</br>");
            htmlResponseWriter.addHtml("</body></html>");
        } else if ("min".equals(command)) {
            htmlResponseWriter.addHtml("<html><body>");
            htmlResponseWriter.addHtml("<h1>Product with min price: </h1>");
            Product product = productDB.minProduct();
            htmlResponseWriter.addHtml(product.getName() + "\t" + product.getPrice() + "</br>");
            htmlResponseWriter.addHtml("</body></html>");
        } else if ("sum".equals(command)) {
            htmlResponseWriter.addHtml("<html><body>");
            htmlResponseWriter.addHtml("Summary price: ");
            int sum = productDB.sumProducts();
            htmlResponseWriter.addHtml(Integer.toString(sum));
            htmlResponseWriter.addHtml("</body></html>");
        } else if ("count".equals(command)) {
            htmlResponseWriter.addHtml("<html><body>");
            htmlResponseWriter.addHtml("Number of products: ");
            int count = productDB.countProducts();
            htmlResponseWriter.addHtml(Integer.toString(count));
            htmlResponseWriter.addHtml("</body></html>");
        } else {
            htmlResponseWriter.addHtml("Unknown command: " + command);
        }

        htmlResponseWriter.write();
    }

}
