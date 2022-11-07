package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.Product;
import ru.akirakozov.sd.refactoring.db.ProductDB;

import java.util.ArrayList;
import java.util.List;

public class MockProductDB extends ProductDB {
    private final List<Product> products;

    public MockProductDB() {
        products = new ArrayList<>();
        products.add(new Product("product-1", 300));
        products.add(new Product("product-2", 450));
    }

    @Override
    public List<Product> listProducts() {
        return products;
    }

    @Override
    public Product maxProduct() {
        return products.get(1);
    }

    @Override
    public Product minProduct() {
        return products.get(0);
    }

    @Override
    public int sumProducts() {
        return products.get(0).getPrice() + products.get(1).getPrice();
    }

    @Override
    public int countProducts() {
        return products.size();
    }
}
