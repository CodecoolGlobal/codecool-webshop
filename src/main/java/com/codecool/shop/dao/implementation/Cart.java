package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private CartDaoJDBC cartDao = new CartDaoJDBC();
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    protected List<Product> productsInCart = new ArrayList<>();

    public List<Product> getProductsInCart() {
        return productsInCart;
    }

    public void addProduct(Product product) {
        this.productsInCart.add(product);
    }

    public void removeProduct(Product product) {
        if (productsInCart.contains(product)) {
            productsInCart.remove(product);
        }
    }

    public void removeAllProductInstances(Product product) {
        while (productsInCart.contains(product)) {
            productsInCart.remove(product);
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0;

        //TODO should be changed to double and handle rounding on website

        for (Product product : productsInCart) {
            totalPrice += product.getDefaultPrice();
        }

        return totalPrice;
    }
}
