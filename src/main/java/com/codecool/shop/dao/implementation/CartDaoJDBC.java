package com.codecool.shop.dao.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CartDaoJDBC {
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String USER = "attila";
    private static final String PASSWORD = "nobilitas";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE, USER, PASSWORD);
    }

}
