package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CartDaoJDBC implements AbstractDao<Cart> {
    private DataSource dataSource;

    public CartDaoJDBC() {
        this.dataSource = Connector.getInstance().connect();
    }

    public int testSelect(Integer id) {
        String query = "SELECT cart_id FROM cart WHERE product_id = ?";


        try {
            PreparedStatement selectTest = dataSource.getConnection().prepareStatement(query);
            selectTest.setInt(1, id);

            ResultSet selectedTest = selectTest.executeQuery();

            while (selectedTest.next()) {

                int productId = selectedTest.getInt("cart_id");

                return productId;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void add(Cart cart) {

    }

    @Override
    public Cart find(int id) {
        return null;
    }

    @Override
    public void remove(int cartId) {

    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public <E> List<Cart> getBy(String column, int id) {
        return null;
    }
}
