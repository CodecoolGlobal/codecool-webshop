package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private DataSource dataSource;

    public ProductCategoryDaoJDBC(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public ProductCategory find(String categoryName) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }
}
