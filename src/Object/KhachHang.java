package Object;

public class KhachHang {
    private int maKH;
    private String hoTen;
    private String gioiTinh;
    private int tongChiTieu;
    private String sdt;

    // Constructor đầy đủ
    public KhachHang(int maKH, String hoTen, String gioiTinh, int tongChiTieu, String sdt) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.tongChiTieu = tongChiTieu;
        this.sdt = sdt;
    }

    // Constructor không có mã KH (dùng khi thêm mới)
    public KhachHang(String hoTen, String gioiTinh, int tongChiTieu, String sdt) {
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.tongChiTieu = tongChiTieu;
        this.sdt = sdt;
    }

    // Getter và Setter
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getTongChiTieu() {
        return tongChiTieu;
    }

    public void setTongChiTieu(int tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
