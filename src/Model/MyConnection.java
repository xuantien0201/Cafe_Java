package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/alama";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private Connection conn;

    public MyConnection() {
        // Initialize without connection to lazy-load
    }

    public Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException ex) {
                throw new SQLException("MySQL JDBC Driver not found", ex);
            }
        }
        return conn;
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}