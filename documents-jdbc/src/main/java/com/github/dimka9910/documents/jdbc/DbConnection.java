package com.github.dimka9910.documents.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Slf4j
public class DbConnection {
    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String url = resourceBundle.getString("db.url");
        String username = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        String databaseName = resourceBundle.getString("db.name");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMinIdle(5);
        dataSource.setMinIdle(10);
    }

    public static Connection getConnectionFromPool() throws SQLException {
        return dataSource.getConnection();
    }

    public static String jdbcUrl = "jdbc:postgresql://localhost:5432/";
    private static DbConnection instance;
    private Connection connection;


    public static Connection getConnection(String ... str) {
        try {
            System.out.println("successsssssssssssssssssssssssssssss");
            log.info("socccuss");
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}