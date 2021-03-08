package com.repairshop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for creating a connection to the database
 */
public class ConnectionFactory {
    public static void init() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Connection create(String connectionUrl, String username, String password) throws SQLException {
        var con = DriverManager.getConnection(connectionUrl, username, password);
        con.setAutoCommit(false);

        return con;
    }
}
