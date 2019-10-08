package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDaoJDBC implements AbstractDao<Supplier> {
    private DataSource dataSource;

    public SupplierDaoJDBC(DataSource datasource) {
        this.dataSource = datasource;
    }


    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        String sql = "SELECT * FROM supplier WHERE id = ?";

        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                int supplierID = resultSet.getInt("id");
                String supplierName = resultSet.getString("name");
                String supplierDescription = resultSet.getString("description");

                supplier = new Supplier(
                        supplierID,
                        supplierName,
                        supplierDescription
                );
            }

            resultSet.close();

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
    public <E> List<Supplier> getBy(int id) {
        return null;
    }
}
