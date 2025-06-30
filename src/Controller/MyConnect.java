package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnect {
    public static Connection conn = null;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/alama";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Kết nối đến CSDL
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Kết nối CSDL thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy Driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Kết nối CSDL thất bại!");
            e.printStackTrace();
        }
    }
     public static void main(String[] args) {
        if (MyConnect.conn != null) {
            System.out.println("Kết nối thành công!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
