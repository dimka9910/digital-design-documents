package com.github.dimka9910.documents.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DbConnection {
    public static String jdbcUrl = "jdbc:postgresql://localhost:5432/";
    private static DbConnection instance;
    private Connection connection;

    private DbConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(jdbcUrl, "postgres", "admin");
            log.info("Successful DB connection");
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public static Connection getConnection(String ... str) {
        if (instance == null){
            instance = new DbConnection();
        }
        return instance.connection;
    }

}