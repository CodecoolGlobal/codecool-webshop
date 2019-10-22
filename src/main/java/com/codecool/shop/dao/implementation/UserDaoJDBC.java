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

                    int userID = resultSet.getInt("id");
                    String userPassword = resultSet.getString("password");
                    String user_Name = resultSet.getString("user_name");

                    user.put("id", String.valueOf(userID));
                    user.put("password", userPassword);
                    user.put("userName", user_Name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void add(String userName, String hashPassword, String email) {

        String sql = "INSERT INTO users(user_name, password, email) VALUES (?, ?, ?)";

        try(Connection con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, hashPassword);
            preparedStatement.setString(3, email);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
