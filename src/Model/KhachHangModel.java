package Model;

import Object.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class KhachHangModel {

    private MyConnection myConn = new MyConnection();

    public ArrayList<KhachHang> getListKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        try (Connection conn = myConn.getConnection()) {
            String sql = "SELECT * FROM KhachHang";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getInt("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("GioiTinh"),
                        rs.getInt("TongChiTieu"),
                        rs.getString("Sdt")
                );
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addKhachHang(KhachHang kh) {
        try (Connection conn = myConn.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO khachhang(HoTen, GioiTinh, TongChiTieu, SDT) VALUES (?, ?, ?,  ?)")) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getGioiTinh());
            ps.setFloat(3, kh.getTongChiTieu());
            ps.setString(4, kh.getSdt());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhachHang(int maKH, KhachHang kh) {
        try (Connection conn = myConn.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "UPDATE khachhang SET HoTen=?, GioiTinh=?, SDT=? WHERE MaKH=?")) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getGioiTinh());
            ps.setString(3, kh.getSdt());
            ps.setInt(4, maKH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhachHang(int maKH) {
        try (Connection conn = myConn.getConnection(); PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM khachhang WHERE MaKH=?")) {
            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public KhachHang findKhachHangBySdt(String sdt) {
        KhachHang result = null;
        try {
            Connection conn = myConn.getConnection();
            String sql = "SELECT * FROM khachhang WHERE sdt = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int maKH = rs.getInt("maKH");
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                int tongChiTieu = rs.getInt("tongChiTieu");
                String sdtDb = rs.getString("sdt");

                result = new KhachHang(maKH, hoTen, gioiTinh, tongChiTieu, sdtDb);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
