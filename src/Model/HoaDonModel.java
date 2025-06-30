// Model: HoaDonModel.java
package Model;

import Object.HoaDon;
import Model.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HoaDonModel {
    private MyConnection myConn = new MyConnection();

    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO hoadon (ngayLap, gioVao, gioRa, nguoiLap, danhSachSanPham, soLuongSanPham,"
                + " tongTien, khuyenMai, tongTienThuc, loaiDon, khachHang) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(hd.getNgayLap().getTime()));
            ps.setString(2, hd.getGioVao());
            ps.setString(3, hd.getGioRa());
            ps.setString(4, hd.getNguoiLap());
            ps.setString(5, hd.getDanhSachSanPham());
            ps.setString(6, hd.getSoLuongSanPham());
            ps.setDouble(7, hd.getTongTien());
            ps.setDouble(8, hd.getKhuyenMai());
            ps.setDouble(9, hd.getTongTienThuc());
            ps.setString(10, hd.getLoaiDon()); // ✅ thêm
            ps.setInt(11, hd.getKhachHang());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
