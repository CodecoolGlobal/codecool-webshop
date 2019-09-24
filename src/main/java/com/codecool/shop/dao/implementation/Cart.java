package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    protected List<Product> addedProducts = new ArrayList<>();

    public List<Product> getAddedProducts() {
        return addedProducts;
    }

    public void addProduct(Product product) {
        this.addedProducts.add(product);
    }

    public void removeProduct(Product product) {
        if (this.addedProducts.contains(product)) {
            this.addedProducts.remove(product);
        }
    }
}
