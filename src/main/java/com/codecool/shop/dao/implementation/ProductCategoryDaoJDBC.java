package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductCategoryDaoJDBC implements AbstractDao {
    private DataSource dataSource;

    public ProductCategoryDaoJDBC(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void add(Object o) {

    }

    @Override
    public Object find(int id) {
        ProductCategory productCategory = null;
        String sql = "SELECT * FROM product_category WHERE id = ?";

        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int productCategoryID = resultSet.getInt("id");
                String productCategoryName = resultSet.getString("name");
                String productCategoryDesc = resultSet.getString("description");
                String productCategoryDepartment = resultSet.getString("department");

                resultSet.close();
                productCategory = new ProductCategory(productCategoryID,
                        productCategoryName,
                        productCategoryDesc,
                        productCategoryDepartment);
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
    public List getAll() {
        return null;
    }

    @Override
    public List getBy(Object o) {
        return null;
    }
}
