package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CheckoutDaoJDBC implements AbstractDao {

    private DataSource dataSource;

    public CheckoutDaoJDBC() {
        this.dataSource = Connector.getInstance().connect();
    }

    @Override
    public void add(Object o) {
        Order order = (Order) o;

        String sql = "INSERT INTO orders(buyer_name, buyer_phone," +
                "buyer_email, buyer_shipping_address, buyer_billing_address, cart_id, user_id" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, order.getBuyerName());
            preparedStatement.setString(2, order.getBuyerPhoneNumber());
            preparedStatement.setString(3, order.getBuyerEmailAddress());
            preparedStatement.setString(4, order.getBuyerBillingAddress());
            preparedStatement.setString(5, order.getBuyerShippingAddress());
            preparedStatement.setInt(6, order.getCartId());
            preparedStatement.setInt(7, order.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public List getBy(String column, int id) {
        return null;
    }
}
