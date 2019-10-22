package com.codecool.shop.dao.implementation;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserDaoJDBC {
    private DataSource dataSource;

    public UserDaoJDBC(Connector connection) {
        this.dataSource = connection.connect();
    }

    public HashMap find(String userName) {
        HashMap<String, String> user = new HashMap<>();

        String sql = "SELECT * FROM users WHERE user_name = ?";

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {


                while (resultSet.next()) {

                    int productID = resultSet.getInt("id");
                    String productName = resultSet.getString("name");
                    String productDesc = resultSet.getString("description");

                    user =
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
