package Object;

import java.sql.Date;

public class KhuyenMai {
    private String maKM;
    private String tenChuongTrinh;
    private String phanTramGiam;
    private String dieuKien;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String tinhTrang;

    public KhuyenMai(String maKM, String tenChuongTrinh, String phanTramGiam, String dieuKien,
                     Date ngayBatDau, Date ngayKetThuc, String tinhTrang) {
        this.maKM = maKM;
        this.tenChuongTrinh = tenChuongTrinh;
        this.phanTramGiam = phanTramGiam;
        this.dieuKien = dieuKien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tinhTrang = tinhTrang;
    }

    public String getMaKM() {
        return maKM;
    }

    public String getTenChuongTrinh() {
        return tenChuongTrinh;
    }

    public String getPhanTramGiam() {
        return phanTramGiam;
    }

    public String getDieuKien() {
        return dieuKien;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }
}
