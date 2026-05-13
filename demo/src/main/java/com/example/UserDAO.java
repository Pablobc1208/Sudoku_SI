package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for User-related database operations.
 */
public class UserDAO {

    /**
     * Validates user credentials against the database.
     * 
     * @param username The username provided.
     * @param password The password provided.
     * @return true if credentials are valid, false otherwise.
     */
    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM personas WHERE email = ? AND pass = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if a user was found
            }
            
        } catch (SQLException e) {
            System.err.println("Database authentication error: " + e.getMessage());
            return false;
        }
    }
}
