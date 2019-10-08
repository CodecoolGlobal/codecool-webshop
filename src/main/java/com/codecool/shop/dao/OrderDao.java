package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

public interface OrderDao {
    void add(Order order);
    void remove(Order order);
    Order getOrder(int orderId);
}
