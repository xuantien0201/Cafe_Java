// Object/HoaDon.java
package Object;

import java.util.Date;

public class HoaDon {
    private int maHoaDon;
    private Date ngayLap;
    private String gioVao;
    private String gioRa;
    private String nguoiLap;
    private String danhSachSanPham;
    private String soLuongSanPham;
    private double tongTien;
    private double khuyenMai;
    private double tongTienThuc;
    private String loaiDon;
    private int khachHang;

    public HoaDon(int maHoaDon, Date ngayLap, String gioVao, String gioRa, String nguoiLap,
                  String danhSachSanPham, String soLuongSanPham,
                  double tongTien, double khuyenMai, double tongTienThuc, String loaiDon, int khachHang) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.nguoiLap = nguoiLap;
        this.danhSachSanPham = danhSachSanPham;
        this.soLuongSanPham = soLuongSanPham;
        this.tongTien = tongTien;
        this.khuyenMai = khuyenMai;
        this.tongTienThuc = tongTienThuc;
        this.loaiDon = loaiDon;
        this.khachHang = khachHang;
    }
    public int getMaHoaDon() { return maHoaDon; }
    public Date getNgayLap() { return ngayLap; }
    public String getGioVao() { return gioVao; }
    public String getGioRa() { return gioRa; }
    public String getNguoiLap() { return nguoiLap; }
    public String getDanhSachSanPham() { return danhSachSanPham; }
    public String getSoLuongSanPham() { return soLuongSanPham; }
    public double getTongTien() { return tongTien; }
    public double getKhuyenMai() { return khuyenMai; }
    public double getTongTienThuc() { return tongTienThuc; }
    public String getLoaiDon() {  return loaiDon;  }
    public int getKhachHang() { return khachHang; }
}
