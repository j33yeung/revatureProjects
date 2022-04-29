package com.revature.bankingapp.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector implements DBConnector {

    private String username;
    private String password;
    private String url;

    public MySQLConnector() {
    }

    public MySQLConnector(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    @Override
    public Connection getConnection(String username, String password, String url) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getConnection(username, password, url);
    }
}
