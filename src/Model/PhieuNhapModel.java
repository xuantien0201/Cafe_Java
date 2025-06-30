/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */

import Model.MyConnection;
import Object.PhieuNhap;
import Object.ChiTietPhieuNhap;
import java.sql.*;
import java.util.Date;           
import java.util.List;
import java.util.ArrayList;

public class PhieuNhapModel {
private MyConnection myConn = new MyConnection();

    public List<PhieuNhap> getAll() {
        List<PhieuNhap> list = new ArrayList<>();
        try (Connection con = myConn.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                "SELECT pn.maphieu, pn.ngaynhap, ncc.tenncc, ncc.diachi, ncc.sdt " +
                "FROM phieunhap pn JOIN nhacungcap ncc ON pn.mancc = ncc.mancc")) {

            while (rs.next()) {
                list.add(new PhieuNhap(
                    rs.getInt("maphieu"),
                    rs.getDate("ngaynhap"),
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

    public void themPhieu(Date ngay, int mancc) {
        try (Connection con = myConn.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO phieunhap (ngaynhap, mancc) VALUES (?, ?)")) {
            ps.setDate(1, new java.sql.Date(ngay.getTime()));
            ps.setInt(2, mancc);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void suaPhieu(int maphieu, Date ngay, int mancc) {
        try (Connection con = myConn.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE phieunhap SET ngaynhap = ?, mancc = ? WHERE maphieu = ?")) {
            ps.setDate(1, new java.sql.Date(ngay.getTime()));
            ps.setInt(2, mancc);
            ps.setInt(3, maphieu);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoaPhieu(int maphieu) {
        try (Connection con = myConn.getConnection();
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM chitietphieunhap WHERE maphieu = ?")){
            ps1.setInt(1, maphieu);
            ps1.executeUpdate();
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM phieunhap WHERE maphieu = ?");
            ps2.setInt(1, maphieu);
            ps2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PhieuNhap> timTheoNCC(String keyword) {
        List<PhieuNhap> list = new ArrayList<>();
        try (Connection con = myConn.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "SELECT pn.maphieu, pn.ngaynhap, ncc.tenncc, ncc.diachi, ncc.sdt " +
                "FROM phieunhap pn JOIN nhacungcap ncc ON pn.mancc = ncc.mancc " +
                "WHERE ncc.tenncc LIKE ?")) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PhieuNhap(
                    rs.getInt("maphieu"),
                    rs.getDate("ngaynhap"),
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