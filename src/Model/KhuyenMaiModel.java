package Model;

import Object.KhuyenMai;
import Object.KhachHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KhuyenMaiModel {
    private final MyConnection myConn = new MyConnection();

    public ArrayList<KhuyenMai> getAllKhuyenMai() {
        ArrayList<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT * FROM khuyenmai";

        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                        rs.getString("MaGiamGia"),
                        rs.getString("MoTa"),
                        rs.getString("MucGiam"),
                        rs.getString("DieuKien"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getString("TinhTrang")
                );
                list.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addKhuyenMai(KhuyenMai km) {
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO khuyenmai (MaGiamGia, MoTa, MucGiam, DieuKien, NgayBatDau, NgayKetThuc, TinhTrang) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, km.getMaKM());
            ps.setString(2, km.getTenChuongTrinh());
            ps.setString(3, km.getPhanTramGiam());
            ps.setString(4, km.getDieuKien());
            ps.setDate(5, new java.sql.Date(km.getNgayBatDau().getTime()));
            ps.setDate(6, new java.sql.Date(km.getNgayKetThuc().getTime()));
            ps.setString(7, km.getTinhTrang());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhuyenMai(String maGiamGia, KhuyenMai km) {
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE khuyenmai SET MoTa=?, MucGiam=?, DieuKien=?, NgayBatDau=?, NgayKetThuc=?, TinhTrang=? WHERE MaGiamGia=?")) {

            ps.setString(1, km.getTenChuongTrinh());
            ps.setString(2, km.getPhanTramGiam());
            ps.setString(3, km.getDieuKien());
            ps.setDate(4, new java.sql.Date(km.getNgayBatDau().getTime()));
            ps.setDate(5, new java.sql.Date(km.getNgayKetThuc().getTime()));
            ps.setString(6, km.getTinhTrang());
            ps.setString(7, maGiamGia);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhuyenMai(String maGiamGia) {
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM khuyenmai WHERE MaGiamGia=?")) {

            ps.setString(1, maGiamGia);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<KhuyenMai> getKhuyenMaiApDung(KhachHang khachHang, double tongTien, int soLuongSanPham) {
        ArrayList<KhuyenMai> list = getAllKhuyenMai();
        ArrayList<KhuyenMai> ketQua = new ArrayList<>();
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        for (KhuyenMai km : list) {
            String dieuKien = km.getDieuKien().toLowerCase().trim();
            boolean hopLe = false;

            if (dieuKien.contains("không điều kiện")) {
                hopLe = true;
            } else if (dieuKien.contains("vip") && khachHang != null && khachHang.getTongChiTieu() > 1000000) {
                hopLe = true;
            } else if (dieuKien.contains("mua từ 3 sản phẩm") && soLuongSanPham >= 3) {
                hopLe = true;
            } else if (dieuKien.contains("thứ 7") || dieuKien.contains("cn")) {
                if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                    hopLe = true;
                }
            } else if (dieuKien.contains("hóa đơn > 100k") && tongTien > 100000) {
                hopLe = true;
            }

            if (hopLe && km.getTinhTrang().equalsIgnoreCase("Đang áp dụng")
                    && today.compareTo(km.getNgayBatDau()) >= 0
                    && today.compareTo(km.getNgayKetThuc()) <= 0) {
                ketQua.add(km);
            }
        }

        return ketQua;
    }
}
