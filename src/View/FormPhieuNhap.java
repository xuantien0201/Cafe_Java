/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author Admin
 */



import Model.NhaCungCapModel;
import Model.PhieuNhapModel;
import Object.NhaCungCap;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class FormPhieuNhap extends JDialog {
    private JSpinner dateSpinner;
    private JComboBox<NhaCungCap> cmbNCC;
    private JButton btnLuu, btnHuy;

    private Integer maphieu;
    private PhieuNhapView parent;
    private PhieuNhapModel model;

    public FormPhieuNhap(Integer maphieu, PhieuNhapView parent) {
        this.maphieu = maphieu;
        this.parent = parent;
        this.model = new PhieuNhapModel();

        setTitle(maphieu == null ? "Thêm phiếu nhập" : "Sửa phiếu nhập");
        setModal(true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panel.setBackground(new Color(255, 250, 240));

        panel.add(new JLabel("Ngày nhập:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        panel.add(dateSpinner);

        panel.add(new JLabel("Nhà cung cấp:"));
        cmbNCC = new JComboBox<>();
        loadNCC();
        panel.add(cmbNCC);

        add(panel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        btnLuu = new JButton("💾 Lưu");
        btnHuy = new JButton("❌ Hủy");

        btnLuu.setBackground(new Color(34, 139, 34));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFocusPainted(false);

        btnHuy.setBackground(new Color(200, 50, 50));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFocusPainted(false);

        bottom.add(btnLuu);
        bottom.add(btnHuy);
        bottom.setBackground(new Color(255, 250, 240));
        add(bottom, BorderLayout.SOUTH);

        // Nếu là sửa thì load dữ liệu lên
        if (maphieu != null) {
            // Có thể load từ DB nếu bạn cần
        }

        btnLuu.addActionListener(e -> {
            Date date = (Date) dateSpinner.getValue();
            NhaCungCap ncc = (NhaCungCap) cmbNCC.getSelectedItem();
            if (ncc == null || date == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                return;
            }

            if (maphieu == null) {
                model.themPhieu(date, ncc.getMancc());
            } else {
                model.suaPhieu(maphieu, date, ncc.getMancc());
            }

            parent.loadData();
            dispose();
        });

        btnHuy.addActionListener(e -> dispose());
    }

    private void loadNCC() {
        List<NhaCungCap> list = new NhaCungCapModel().getAllNCC();
        for (NhaCungCap ncc : list) {
            cmbNCC.addItem(ncc);
        }
    }
}

