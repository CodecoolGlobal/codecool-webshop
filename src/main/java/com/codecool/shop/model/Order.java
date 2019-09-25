package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> cart = new ArrayList<>();
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerEmailAddress;

    public Order(List<Product> cart, String buyerName, String buyerPhoneNumber, String buyerEmailAddress) {
        this.cart = cart;
        this.buyerName = buyerName;
        this.buyerPhoneNumber = buyerPhoneNumber;
        this.buyerEmailAddress = buyerEmailAddress;
    }

    public List<Product> getCart() {
        return cart;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public String getBuyerEmailAddress() {
        return buyerEmailAddress;
    }
}