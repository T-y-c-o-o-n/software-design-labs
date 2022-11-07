package ru.akirakozov.sd.refactoring.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductDB {

    public void createProductsTable() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }

    }

    public List<Product> listProducts() {
        List<Product> products = new ArrayList<>();
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    products.add(new Product(name, price));
                }

                rs.close();
                stmt.close();
            }

            return products;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(Product product) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")";
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product maxProduct() {
        Product product;
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

                if (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    product = new Product(name, price);
                } else {
                    throw new NoSuchElementException();
                }

                rs.close();
                stmt.close();
            }

            return product;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Product minProduct() {
        Product product;
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

                if (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    product = new Product(name, price);
                } else {
                    throw new NoSuchElementException();
                }

                rs.close();
                stmt.close();
            }

            return product;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int sumProducts() {
        int sum = 0;
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

                if (rs.next()) {
                    sum = rs.getInt(1);
                }

                rs.close();
                stmt.close();
            }

            return sum;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int countProducts() {
        int count = 0;
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

                if (rs.next()) {
                    count = rs.getInt(1);
                }

                rs.close();
                stmt.close();
            }

            return count;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
