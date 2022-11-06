package ru.akirakozov.sd.refactoring.servlet;

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

    HtmlResponseWriter htmlResponseWriter = new HtmlResponseWriter();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        htmlResponseWriter.setResponse(response);

        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                    htmlResponseWriter.addHtml("<html><body>");
                    htmlResponseWriter.addHtml("<h1>Product with max price: </h1>");

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
                        htmlResponseWriter.addHtml(name + "\t" + price + "</br>");
                    }
                    htmlResponseWriter.addHtml("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                    htmlResponseWriter.addHtml("<html><body>");
                    htmlResponseWriter.addHtml("<h1>Product with min price: </h1>");

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
                        htmlResponseWriter.addHtml(name + "\t" + price + "</br>");
                    }
                    htmlResponseWriter.addHtml("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
                    htmlResponseWriter.addHtml("<html><body>");
                    htmlResponseWriter.addHtml("Summary price: ");

                    if (rs.next()) {
                        htmlResponseWriter.addHtml(Integer.toString(rs.getInt(1)));
                    }
                    htmlResponseWriter.addHtml("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                    htmlResponseWriter.addHtml("<html><body>");
                    htmlResponseWriter.addHtml("Number of products: ");

                    if (rs.next()) {
                        htmlResponseWriter.addHtml(Integer.toString(rs.getInt(1)));
                    }
                    htmlResponseWriter.addHtml("</body></html>");

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            htmlResponseWriter.addHtml("Unknown command: " + command);
        }

        htmlResponseWriter.write();
    }

}
