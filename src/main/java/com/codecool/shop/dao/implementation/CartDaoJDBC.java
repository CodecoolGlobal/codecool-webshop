package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CartDaoJDBC implements CartDao {
    private DataSource dataSource;

    public CartDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int testSelect() {
        String query = "SELECT * FROM cart WHERE product_id = ?";

        try {
            PreparedStatement selectTest = dataSource.getConnection().prepareStatement(query);
            selectTest.setInt(1, 2);

            ResultSet selectedTest = selectTest.executeQuery();

            while (selectedTest.next()) {

                int productId = selectedTest.getInt("product_id");

                return productId;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public void remove(Product product) {

    }

    @Override
    public void remove(int cartId) {

    }

    @Override
    public Product getProduct(int productId) {
        return null;
    }

    @Override
    public List<Product> getAllProduct(int cartId) {
        return null;
    }
}
