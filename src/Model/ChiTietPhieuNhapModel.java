/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */

import Object.ChiTietPhieuNhap;
import Model.MyConnection;

import java.sql.*;
import java.util.*;

public class ChiTietPhieuNhapModel {
    private MyConnection myConn = new MyConnection();
    

    public List<ChiTietPhieuNhap> getAllByMaPN(int maphieu) {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        try (Connection con = myConn.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM chitietphieunhap WHERE maphieu = ?")) {
            ps.setInt(1, maphieu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ChiTietPhieuNhap(
                    rs.getInt("id"),
                    rs.getString("tenhang"),
                    rs.getInt("soluong"),
                    rs.getDouble("dongia")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void them(int maphieu, String tenhang, int soluong, double dongia) {
        try (Connection con = myConn.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO chitietphieunhap (maphieu, tenhang, soluong, dongia) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, maphieu);
            ps.setString(2, tenhang);
            ps.setInt(3, soluong);
            ps.setDouble(4, dongia);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sua(int id, String tenhang, int soluong, double dongia) {
        try (Connection con = myConn.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE chitietphieunhap SET tenhang = ?, soluong = ?, dongia = ? WHERE id = ?")) {
            ps.setString(1, tenhang);
            ps.setInt(2, soluong);
            ps.setDouble(3, dongia);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoa(int id) {
        try (Connection con = myConn.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM chitietphieunhap WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


