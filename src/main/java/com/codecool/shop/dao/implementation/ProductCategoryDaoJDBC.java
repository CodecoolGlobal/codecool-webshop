package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements AbstractDao<ProductCategory> {
    private DataSource dataSource;

    public ProductCategoryDaoJDBC() {
        this.dataSource = Connector.getInstance().connect();
    }


    @Override
    public void add(ProductCategory productCategory) {

    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory productCategory = null;
        String sql = "SELECT * FROM product_category WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int productCategoryID = resultSet.getInt("id");
                    String productCategoryName = resultSet.getString("name");
                    String productCategoryDescription = resultSet.getString("description");
                    String productCategoryDepartment = resultSet.getString("department");

                    productCategory = new ProductCategory(
                            productCategoryID,
                            productCategoryName,
                            productCategoryDescription,
                            productCategoryDepartment
                    );
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productCategory;
    }


    public ProductCategory find(String categoryName) {
        ProductCategory productCategory = null;
        String sql = "SELECT * FROM product_category WHERE name = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, categoryName);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    int productCategoryID = resultSet.getInt("id");
                    String productCategoryName = resultSet.getString("name");
                    String productCategoryDescription = resultSet.getString("description");
                    String productCategoryDepartment = resultSet.getString("department");

                    productCategory = new ProductCategory(
                            productCategoryID,
                            productCategoryName,
                            productCategoryDescription,
                            productCategoryDepartment
                    );
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productCategory;
    }


    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> allProductCategories = new ArrayList<>();
        String sql = "SELECT * FROM product_category";

        try (
                PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                int productCategoryID = resultSet.getInt("id");
                String productCategoryName = resultSet.getString("name");
                String productCategoryDescription = resultSet.getString("description");
                String productCategoryDepartment = resultSet.getString("department");

                allProductCategories.add(new ProductCategory(
                                productCategoryID,
                                productCategoryName,
                                productCategoryDescription,
                                productCategoryDepartment
                        )
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allProductCategories;
    }

    @Override
    public <E> List<ProductCategory> getBy(String column, int id) {
        return null;
    }
}
