/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */

import Object.NhaCungCap;
import Model.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapModel {
    private MyConnection myConn = new MyConnection();
    
    public List<NhaCungCap> getAllNCC() {
        List<NhaCungCap> list = new ArrayList<>();
        try (Connection con = myConn.getConnection()) {
            String sql = "SELECT * FROM nhacungcap";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new NhaCungCap(
                        rs.getInt("mancc"),
                        rs.getString("tenncc"),
                        rs.getString("diachi"),
                        rs.getString("sdt")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(NhaCungCap ncc) {
        try (Connection con = myConn.getConnection()) {
            String sql = "INSERT INTO nhacungcap(tenncc, diachi, sdt) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ncc.getTenncc());
            ps.setString(2, ncc.getDiachi());
            ps.setString(3, ncc.getSdt());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhaCungCap ncc) {
        try (Connection con = myConn.getConnection()) {
            String sql = "UPDATE nhacungcap SET tenncc=?, diachi=?, sdt=? WHERE mancc=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ncc.getTenncc());
            ps.setString(2, ncc.getDiachi());
            ps.setString(3, ncc.getSdt());
            ps.setInt(4, ncc.getMancc());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int mancc) {
        try (Connection con = myConn.getConnection()) {
            String sql = "DELETE FROM nhacungcap WHERE mancc=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, mancc);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<NhaCungCap> timKiem(String keyword) {
    List<NhaCungCap> list = new ArrayList<>();
    try (Connection con = myConn.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM nhacungcap WHERE tenncc LIKE ?")) {
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new NhaCungCap(
                rs.getInt("mancc"),
                rs.getString("tenncc"),
                rs.getString("diachi"),
                rs.getString("sdt")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}

