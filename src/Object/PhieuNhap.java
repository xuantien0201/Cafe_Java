/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

/**
 *
 * @author Admin
 */


import java.util.Date;

public class PhieuNhap {
    private int maphieu;
    private Date ngaynhap;
    private String tenncc;
    private String diachi;
    private String sdt;

    public PhieuNhap(int maphieu, Date ngaynhap, String tenncc, String diachi, String sdt) {
        this.maphieu = maphieu;
        this.ngaynhap = ngaynhap;
        this.tenncc = tenncc;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public int getMaphieu() { return maphieu; }
    public Date getNgaynhap() { return ngaynhap; }
    public String getTenncc() { return tenncc; }
    public String getDiachi() { return diachi; }
    public String getSdt() { return sdt; }

    public void setMaphieu(int maphieu) { this.maphieu = maphieu; }
    public void setNgaynhap(Date ngaynhap) { this.ngaynhap = ngaynhap; }
    public void setTenncc(String tenncc) { this.tenncc = tenncc; }
    public void setDiachi(String diachi) { this.diachi = diachi; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}


