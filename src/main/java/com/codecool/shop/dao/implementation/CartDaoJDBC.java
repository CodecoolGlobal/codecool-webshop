package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CartDaoJDBC  implements AbstractDao, CartDao {
    private DataSource dataSource;

    public CartDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
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
