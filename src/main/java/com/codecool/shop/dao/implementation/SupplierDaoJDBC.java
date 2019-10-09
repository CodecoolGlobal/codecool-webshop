package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDaoJDBC implements AbstractDao<Supplier> {
    private DataSource dataSource;

    public SupplierDaoJDBC() {
        this.dataSource = Connector.getInstance().connect();
    }


    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        String sql = "SELECT * FROM supplier WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (

                    ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    int supplierID = resultSet.getInt("id");
                    String supplierName = resultSet.getString("name");
                    String supplierDescription = resultSet.getString("description");

                    supplier = new Supplier(
                            supplierID,
                            supplierName,
                            supplierDescription
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    public Supplier find(String supplierName) {
        Supplier supplier = null;
        String sql = "SELECT * FROM supplier WHERE name = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, supplierName);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    int supplierID = resultSet.getInt("id");
                    String supplierName2 = resultSet.getString("name");
                    String supplierDescription = resultSet.getString("description");

                    supplier = new Supplier(
                            supplierID,
                            supplierName2,
                            supplierDescription
                    );
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public <E> List<Supplier> getBy(String column, int id) {
        return null;
    }
}
