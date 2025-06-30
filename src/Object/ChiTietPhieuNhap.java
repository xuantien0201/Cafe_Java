/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

/**
 *
 * @author Admin
 */

public class ChiTietPhieuNhap {
    private int id;
    private String tenhang;
    private int soluong;
    private double dongia;

    public ChiTietPhieuNhap(int id, String tenhang, int soluong, double dongia) {
        this.id = id;
        this.tenhang = tenhang;
        this.soluong = soluong;
        this.dongia = dongia;
    }

    public int getId() { return id; }
    public String getTenhang() { return tenhang; }
    public int getSoluong() { return soluong; }
    public double getDongia() { return dongia; }

    public void setId(int id) { this.id = id; }
    public void setTenhang(String tenhang) { this.tenhang = tenhang; }
    public void setSoluong(int soluong) { this.soluong = soluong; }
    public void setDongia(double dongia) { this.dongia = dongia; }
}



