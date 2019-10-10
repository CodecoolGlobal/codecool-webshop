package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerEmailAddress;
    private String buyerShippingAddress;
    private String buyerBillingAddress;
    private String id;
    private int cartId;

    public Order(int cartId, String buyerName, String buyerPhoneNumber, String buyerEmailAddress, String buyerShippingAddress, String buyerBillingAddress) {
        this.buyerName = buyerName;
        this.buyerPhoneNumber = buyerPhoneNumber;
        this.buyerEmailAddress = buyerEmailAddress;
        this.buyerShippingAddress = buyerShippingAddress;
        this.buyerBillingAddress = buyerBillingAddress;
        this.id = java.util.UUID.randomUUID().toString();
        this.cartId = cartId;
    }

//    public List<Product> getCart() {
//        return cart;
//    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public String getBuyerEmailAddress() {
        return buyerEmailAddress;
    }

    public String getBuyerShippingAddress() {
        return buyerShippingAddress;
    }

    public String getBuyerBillingAddress() {
        return buyerBillingAddress;
    }

    public String getId() {
        return id;
    }

    private void clear(){
//        cart.clear();
        this.buyerName = "";
        this.buyerPhoneNumber = "";
        this.buyerBillingAddress = "";
        this.buyerEmailAddress = "";
        this.buyerShippingAddress = "";
        this.id = "";
    }


    public int getCartId() {
        return this.cartId;
    }
}
