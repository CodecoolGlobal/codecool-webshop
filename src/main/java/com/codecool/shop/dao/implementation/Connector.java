package com.codecool.shop.dao.implementation;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Connector {

    private static Connector instance = new Connector();

    private Connector(){};

    public static Connector getInstance(){
        return instance;
    }

    public DataSource connect() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(System.getenv("DB_NAME"));
        dataSource.setUser(System.getenv("DB_USER"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));

        System.out.println("Trying to connect...");;
        System.out.println("Connection OK");

        return dataSource;
    }
}
