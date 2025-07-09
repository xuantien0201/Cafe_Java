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
        final String insertSql =
            "INSERT INTO hoadon (ngayLap, gioVao, gioRa, nguoiLap, danhSachSanPham," +
            " soLuongSanPham, tongTien, khuyenMai, tongTienThuc, loaiDon, khachHang) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        final String updateSql =
            "UPDATE khachhang SET TongChiTieu = (" +
            " SELECT IFNULL(SUM(tongTienThuc), 0) FROM hoadon WHERE khachHang = ?" +
            ") WHERE MaKH = ?";

        try (Connection conn = myConn.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psInsert = conn.prepareStatement(insertSql);
                 PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {

                // luu hoa don
                psInsert.setDate   (1, new java.sql.Date(hd.getNgayLap().getTime()));
                psInsert.setString (2, hd.getGioVao());
                psInsert.setString (3, hd.getGioRa());
                psInsert.setString (4, hd.getNguoiLap());
                psInsert.setString (5, hd.getDanhSachSanPham());
                psInsert.setString (6, hd.getSoLuongSanPham());
                psInsert.setDouble (7, hd.getTongTien());
                psInsert.setDouble (8, hd.getKhuyenMai());
                psInsert.setDouble (9, hd.getTongTienThuc());
                psInsert.setString (10, hd.getLoaiDon());
                psInsert.setInt    (11, hd.getKhachHang());

                int rowHoadon = psInsert.executeUpdate();
                if (rowHoadon == 0) {
                    conn.rollback();
                    return false;                       // chèn hóa đơn thất bại
                }

                // update tong chi tieu khach hang
                psUpdate.setDouble(1, hd.getTongTienThuc());
                psUpdate.setInt   (2, hd.getKhachHang());

                int rowKh = psUpdate.executeUpdate();
                if (rowKh == 0) {                       // không tìm thấy MaKH phù hợp
                    conn.rollback();
                    return false;
                }
                conn.commit();
                return true;

            } catch (SQLException ex) {
                conn.rollback();                       // có lỗi, hủy giao dịch
                ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);              // trả lại trạng thái mặc định
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
