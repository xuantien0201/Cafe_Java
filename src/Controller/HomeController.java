package Controller;

import View.HoaDonView;
import View.InvoiceItemPanel;
import Object.User;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class HomeController {
    private JPanel invoiceListPanel;
    private JLabel lblTongTien;
    private User currentUser;
    private Component parentComponent; // Dùng để xác định JFrame cha khi mở JDialog

    // ✅ Constructor để truyền tham chiếu cần thiết
    public HomeController(JPanel invoiceListPanel, JLabel lblTongTien, User currentUser, Component parentComponent) {
        this.invoiceListPanel = invoiceListPanel;
        this.lblTongTien = lblTongTien;
        this.currentUser = currentUser;
        this.parentComponent = parentComponent;
    }

    public void thanhToanAction() {
        if (invoiceListPanel.getComponentCount() == 0) {
            JOptionPane.showMessageDialog(parentComponent, "Chưa có sản phẩm nào được chọn.");
            return;
        }

        Date now = new Date();
        LocalDateTime localNow = LocalDateTime.now();

        String gioVao = java.time.LocalTime.now().toString().substring(0, 8);
        String gioRa = gioVao;
        String nguoiLap = currentUser.getFullName();

        StringBuilder danhSachSanPham = new StringBuilder();
        StringBuilder soLuongSanPham = new StringBuilder();
        double tongTien = 0;
        int tongSoLuong = 0;

        for (Component comp : invoiceListPanel.getComponents()) {
            if (comp instanceof InvoiceItemPanel itemPanel) {
                int maSP = itemPanel.getProduct().getProductId();
                int soLuong = itemPanel.getQuantity();
                double donGia = itemPanel.getProduct().getPrice();

                danhSachSanPham.append(maSP).append(",");
                soLuongSanPham.append(soLuong).append(",");
                tongTien += donGia * soLuong;
                tongSoLuong += soLuong;
            }
        }

        danhSachSanPham.setLength(danhSachSanPham.length() - 1);
        soLuongSanPham.setLength(soLuongSanPham.length() - 1);

        // Format
        String ngay = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localNow);
        String gio = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss").format(localNow);
        DecimalFormat df = new DecimalFormat("#,###");

        // Tạo panel hóa đơn
        HoaDonView panelHoaDon = new HoaDonView();
        panelHoaDon.getLblNgayThang().setText(ngay);
        panelHoaDon.getLblGio().setText(gio);
        panelHoaDon.getLblSoLuong().setText(String.valueOf(tongSoLuong));
        panelHoaDon.getLblTongTien().setText(df.format(tongTien));
        panelHoaDon.getLblNguoiLap().setText(nguoiLap);

        panelHoaDon.setInvoiceData(now, gioVao, gioRa, currentUser, danhSachSanPham.toString(), soLuongSanPham.toString(), tongTien);

        // Hiển thị trong dialog modal
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parentComponent) instanceof JFrame ?
                (JFrame) SwingUtilities.getWindowAncestor(parentComponent) : null,
                "Chi tiết hóa đơn", true);
//        panelHoaDon.setDialogParent(dialog); // để nút "Quay lại" đóng được dialog
        dialog.setContentPane(panelHoaDon);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
