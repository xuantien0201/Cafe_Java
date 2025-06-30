package Model;

import Controller.NhanVienController;
import Object.NhanVien;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class NhanVienModel {
    private NhanVienController nvDAO = new NhanVienController();
    private ArrayList<NhanVien> listNhanVien = null;

    public NhanVienModel() {
        docDanhSach();
    }

    public void docDanhSach() {
        this.listNhanVien = nvDAO.getDanhSachNhanVien();
    }

    public ArrayList<NhanVien> getDanhSachNhanVien() {
        if (this.listNhanVien == null)
            docDanhSach();
        return this.listNhanVien;
    }

    public boolean themNhanVien(String ho, String ten, String gioiTinh, String chucVu) {
        ho = ho.trim();
        ten = ten.trim();
        chucVu = chucVu.trim();
        if (ten.equals("")) {
            JOptionPane.showMessageDialog(null, "Tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (chucVu.equals("")) {
            JOptionPane.showMessageDialog(null, "Chức vụ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        NhanVien nv = new NhanVien();
        nv.setHo(ho);
        nv.setTen(ten);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        boolean flag = nvDAO.themNhanVien(nv);
        if (!flag) {
            JOptionPane.showMessageDialog(null, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            docDanhSach();
            JOptionPane.showMessageDialog(null, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        return flag;
    }

    public boolean updateNhanVien(String ma, String ho, String ten, String gioiTinh, String chucVu) {
        int maNV;
        try {
            maNV = Integer.parseInt(ma);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        ho = ho.trim();
        ten = ten.trim();
        chucVu = chucVu.trim();
        if (ten.equals("")) {
            JOptionPane.showMessageDialog(null, "Tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (chucVu.equals("")) {
            JOptionPane.showMessageDialog(null, "Chức vụ không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        NhanVien nv = new NhanVien();
        nv.setMaNV(maNV);
        nv.setHo(ho);
        nv.setTen(ten);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        boolean flag = nvDAO.updateNhanVien(nv);
        if (!flag) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            docDanhSach();
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        return flag;
    }

    public ArrayList<NhanVien> timNhanVien(String tuKhoa) {
        if (listNhanVien == null) {
            docDanhSach();
        }
        tuKhoa = tuKhoa.toLowerCase();
        ArrayList<NhanVien> dsnv = new ArrayList<>();
        for (NhanVien nv : listNhanVien) {
            if (nv.getHo().toLowerCase().contains(tuKhoa) || nv.getTen().toLowerCase().contains(tuKhoa) ||
                    nv.getGioiTinh().toLowerCase().contains(tuKhoa) || nv.getChucVu().toLowerCase().contains(tuKhoa)) {
                dsnv.add(nv);
            }
        }
        return dsnv;
    }

    public boolean xoaNhanVien(String ma) {
        try {
            int maNV = Integer.parseInt(ma);
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean flag = nvDAO.deleteNhanVien(maNV);
                if (flag) {
                    docDanhSach();
                    JOptionPane.showMessageDialog(null, "Xoá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Xoá thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                return flag;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhân viên hoặc mã không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean nhapExcel(String ho, String ten, String gioiTinh, String chucVu) {
        ho = ho.trim();
        ten = ten.trim();
        chucVu = chucVu.trim();
        if (ten.equals("") || chucVu.equals("")) {
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        NhanVien nv = new NhanVien();
        nv.setHo(ho);
        nv.setTen(ten);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        boolean flag = nvDAO.nhapExcel(nv);
        if (flag) {
            docDanhSach();
            JOptionPane.showMessageDialog(null, "Nhập từ Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nhập từ Excel thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }
}
