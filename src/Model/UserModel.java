/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
package Model;

import Object.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private MyConnection myConnection;

    public UserModel() {
        myConnection = new MyConnection();
    }

    public User authenticateUser(String employeeId, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = myConnection.getConnection();
            String query = "SELECT employee_id, password, MaNV FROM users WHERE employee_id = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, employeeId);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                    resultSet.getString("employee_id"),
                    resultSet.getString("password"),
                    resultSet.getInt("MaNV")
                );
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return user;
    }
    
    public String getFullNameByMaNV(int maNV) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String fullName = null;

        try {
            connection = myConnection.getConnection();
            String query = "SELECT Ho, Ten FROM nhanvien WHERE MaNV = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, maNV);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String ho = resultSet.getString("Ho");
                String ten = resultSet.getString("Ten");
                fullName = ho + " " + ten;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return fullName;
    }
    
    public String getChucVuByMaNV(int maNV) throws SQLException {
        String chucVu = "Nhân Viên"; // mặc định
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = myConnection.getConnection();
            String sql = "SELECT ChucVu FROM nhanvien WHERE MaNV = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maNV);
            rs = stmt.executeQuery();
            if (rs.next()) {
                chucVu = rs.getString("ChucVu");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return chucVu;
    }

}