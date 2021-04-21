package com.github.dimka9910.documents.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DbConnection {
    public static final String jdbcUrl = "jdbc:sqlite:documents-jdbc/sqlitesample.db";
    private static DbConnection instance;
    private Connection connection;

    private DbConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(jdbcUrl);
            log.info("Successful DB connection");
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (instance == null){
            instance = new DbConnection();
        }
        return instance.connection;
    }

}