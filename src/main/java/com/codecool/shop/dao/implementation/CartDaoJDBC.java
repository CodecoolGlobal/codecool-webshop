package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CartDaoJDBC implements AbstractDao<Cart> {
    private DataSource dataSource;

    public CartDaoJDBC() {
        this.dataSource = Connector.getInstance().connect();
    }


    @Override
    public void add(Cart cart) {

        int lastIndex = cart.getProductsInCart().size() - 1;
        Product mostRecentProduct = cart.getProductsInCart().get(lastIndex);

        String sql = "INSERT INTO cart(cart_id, product_id, user_id) VALUES (?, ?, ?)";

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setInt(1, cart.getId());
            preparedStatement.setInt(2, mostRecentProduct.getId());
            preparedStatement.setInt(3, cart.getUserId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Cart find(int id) {
        return null;
    }

    @Override
    public void remove(int cartId) {

    }

    public void removeProduct(int cartId, int productId) {
        String sql = "DELETE FROM cart WHERE id IN (SELECT MAX(id) FROM cart WHERE cart_id = ? AND product_id = ?)";

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAll(int cartId, int productId) {
        String sql = "DELETE FROM cart WHERE cart_id = ? AND product_id = ?";

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public <E> List<Cart> getBy(String column, int id) {
        return null;
    }

    public int getLast() {
        int lastIndex = 0;
        String sql = "SELECT cart_id FROM cart ORDER BY cart_id DESC LIMIT 1";

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {


                while (resultSet.next()) {
                    lastIndex = resultSet.getInt("cart_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastIndex;
    }
}
