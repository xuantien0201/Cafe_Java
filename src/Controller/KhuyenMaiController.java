package Controller;

import Model.KhuyenMaiModel;
import Object.KhuyenMai;
import View.KhuyenMaiFrame;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhuyenMaiController {

    private final KhuyenMaiModel khuyenMaiModel = new KhuyenMaiModel();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private KhuyenMaiFrame view;

    public KhuyenMaiController() {
    }
    
    public ArrayList<KhuyenMai> getListKhuyenMai() {
        return khuyenMaiModel.getAllKhuyenMai();
    }

    public boolean themKhuyenMai(String ma, String moTa, String mucGiam, String dieuKien,
            String ngayBDStr, String ngayKTStr, String tinhTrang) {
        try {
            Date ngayBD_util = sdf.parse(ngayBDStr.trim());
            Date ngayKT_util = sdf.parse(ngayKTStr.trim());

            java.sql.Date ngayBD = new java.sql.Date(ngayBD_util.getTime());
            java.sql.Date ngayKT = new java.sql.Date(ngayKT_util.getTime());

            KhuyenMai km = new KhuyenMai(ma, moTa, mucGiam, dieuKien, ngayBD, ngayKT, tinhTrang);
            return khuyenMaiModel.addKhuyenMai(km);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin khuyến mãi
    public boolean suaKhuyenMai(String ma, String moTa, String mucGiam, String dieuKien,
            String ngayBDStr, String ngayKTStr, String tinhTrang) {
        try {
            Date ngayBD_util = sdf.parse(ngayBDStr.trim());
            Date ngayKT_util = sdf.parse(ngayKTStr.trim());

            java.sql.Date ngayBD = new java.sql.Date(ngayBD_util.getTime());
            java.sql.Date ngayKT = new java.sql.Date(ngayKT_util.getTime());

            KhuyenMai km = new KhuyenMai(ma, moTa, mucGiam, dieuKien, ngayBD, ngayKT, tinhTrang);
            return khuyenMaiModel.updateKhuyenMai(ma, km);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khuyến mãi theo mã
    public boolean xoaKhuyenMai(String ma) {
        try {
            return khuyenMaiModel.deleteKhuyenMai(ma);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Hàm khởi tạo controller, truyền vào listener để bắt sự kiện khi người dùng chọn khuyến mãi
    public KhuyenMaiController(KhuyenMaiFrame.KhuyenMaiSelectionListener listener) {
        showKhuyenMaiView(listener);
    }
    
    // Hiển thị view danh sách khuyến mãi
    private void showKhuyenMaiView(KhuyenMaiFrame.KhuyenMaiSelectionListener listener) {
        try {
            List<KhuyenMai> danhSach = khuyenMaiModel.getAllKhuyenMai();
            view = new KhuyenMaiFrame(danhSach, listener);
            view.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi tải danh sách khuyến mãi: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
