package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AbstractDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.util.List;

public class SupplierDaoJDBC implements AbstractDao {
    private DataSource datasource;

    public SupplierDaoJDBC(DataSource datasource){
        this.datasource = datasource;
    }


    @Override
    public void add(Object o) {

    }

    @Override
    public Object find(int id) {
        return null;
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
