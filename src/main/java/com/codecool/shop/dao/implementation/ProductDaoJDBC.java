package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements AbstractDao<Product> {
    private DataSource dataSource;
    private AbstractDao supplierDaoJDBC;
    private AbstractDao productCategoryDaoJDBC;

    public ProductDaoJDBC(DataSource dataSource, SupplierDaoJDBC supplierDaoJDBC, ProductCategoryDaoJDBC productCategoryDaoJDBC){
        this.dataSource = dataSource;
        this.supplierDaoJDBC = supplierDaoJDBC;
        this.productCategoryDaoJDBC = productCategoryDaoJDBC;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
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

                product = new Product(
                        productID,
                        productName,
                        defaultPrice,
                        defaultCurrency,
                        productDesc,
                        (ProductCategory) productCategoryDaoJDBC.find(productCategoryID),
                        (Supplier) supplierDaoJDBC.find(supplierID)
                );
            }

            resultSet.close();

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
    public List<Product> getBy(String column, int id) {

        List<Product> products = new ArrayList<>();
        String sql = "";
        switch (column){
            case "Category":
                sql = "SELECT * FROM product WHERE product_category = ?";
                break;
            case "Supplier":
                sql = "SELECT * FROM product WHERE supplier = ?";
                break;
        }

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()){

            preparedStatement.setInt(1, id);


            while (resultSet.next()){

                int productID = resultSet.getInt("id");
                String productName = resultSet.getString("name");
                String productDesc = resultSet.getString("description");
                double defaultPrice = resultSet.getDouble("default_price");
                String defaultCurrency = resultSet.getString("default_currency");
                int productCategoryID = resultSet.getInt("product_category");
                int supplierID = resultSet.getInt("supplier");

                products.add(new Product(
                        productID,
                        productName,
                        defaultPrice,
                        defaultCurrency,
                        productDesc,
                        (ProductCategory) productCategoryDaoJDBC.find(productCategoryID),
                        (Supplier) supplierDaoJDBC.find(supplierID)
                    )
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
