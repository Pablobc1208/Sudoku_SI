package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class to manage connection to the MySQL database.
 */
public class DatabaseConnection {

    private static final String CONFIG_FILE = "db.propierties";
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
        }
    }

    /**
     * Establishes a connection to the database.
     * 
     * @return The Connection object.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the MySQL driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String host = properties.getProperty("db.url");
            String port = properties.getProperty("db.port");
            String dbName = properties.getProperty("db.name");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            // Construct the JDBC URL
            String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);

            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }
}
