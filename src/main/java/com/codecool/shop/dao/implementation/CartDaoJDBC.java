package com.codecool.shop.dao.implementation;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CartDaoJDBC {
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String USER = "daniel";
    private static final String PASSWORD = "pleasework";

    private DataSource dataSource;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE, USER, PASSWORD);
    }

    public CartDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
