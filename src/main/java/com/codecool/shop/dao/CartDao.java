package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;

public interface CartDao {
    void add(Product product);
    void remove(Product product);
    void remove(int cartId);
    Product getProduct(int productId);
    List<Product> getAllProduct(int cartId);
}
