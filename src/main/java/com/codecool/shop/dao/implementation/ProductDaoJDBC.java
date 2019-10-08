package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJDBC  implements AbstractDao {
    private DataSource dataSource;
    private AbstractDao supplierDaoJDBC;
    private AbstractDao productCategoryDaoJDBC;

    public ProductDaoJDBC(DataSource dataSource, SupplierDaoJDBC supplierDaoJDBC, ProductCategoryDaoJDBC productCategoryDaoJDBC){
        this.dataSource = dataSource;
        this.supplierDaoJDBC = supplierDaoJDBC;
        this.productCategoryDaoJDBC = productCategoryDaoJDBC;
    }

    @Override
    public void add(Object o) {

    }

    @Override
    public Object find(int id) {
        Product product = null;
        String sql = "SELECT * FROM product WHERE id = ?";

        try {
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int productID = resultSet.getInt("id");
                String productName = resultSet.getString("name");
                String productDesc = resultSet.getString("description");
                double defaultPrice = resultSet.getDouble("default_price");
                String defaultCurrency = resultSet.getString("default_currency");
                int productCategoryID = resultSet.getInt("product_category");
                int supplierID = resultSet.getInt("supplier");
                resultSet.close();
                product = new Product(productID,
                        productName,
                        defaultPrice,
                        defaultCurrency,
                        productDesc,
                        (ProductCategory) productCategoryDaoJDBC.find(productCategoryID),
                        (Supplier) supplierDaoJDBC.find(supplierID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
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
