/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author Admin
 */

import Model.ChiTietPhieuNhapModel;
import Object.ChiTietPhieuNhap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Các phần không thay đổi ở đầu import...

public class FormChiTietPhieuNhap extends JDialog {
    private int maphieu;
    private JTable table;
    private DefaultTableModel modelTable;

    private JTextField txtTenHang, txtSoLuong, txtDonGia;
    private JButton btnThem, btnSua, btnXoa, btnClear;

    private ChiTietPhieuNhapModel model;

    public FormChiTietPhieuNhap(int maphieu) {
        this.maphieu = maphieu;
        this.model = new ChiTietPhieuNhapModel();

        setTitle("Chi tiết phiếu nhập #" + maphieu);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#F5F5F5")); // Nền xám nhẹ

        // === Table ===
        modelTable = new DefaultTableModel(new String[]{"ID", "Tên hàng", "Số lượng", "Đơn giá"}, 0);
        table = new JTable(modelTable);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.decode("#212121"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // === Panel dưới ===
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(Color.decode("#F5F5F5"));

        // === Form nhập liệu ===
        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        inputPanel.setBackground(Color.decode("#F5F5F5"));
        txtTenHang = new JTextField();
        txtSoLuong = new JTextField();
        txtDonGia = new JTextField();

        inputPanel.add(new JLabel("Tên hàng:"));
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(new JLabel("Đơn giá:"));
        inputPanel.add(txtTenHang);
        inputPanel.add(txtSoLuong);
        inputPanel.add(txtDonGia);

        // === Button Panel ===
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.decode("#F5F5F5"));

        btnThem = new JButton("➕ Thêm");
        btnSua = new JButton("✏️ Sửa");
        btnXoa = new JButton("🗑️ Xoá");
        btnClear = new JButton("🧹 Xoá trắng");

        // === Màu nút theo logo ===
        styleButton(btnThem, "#FF6F00", Color.WHITE);
        styleButton(btnSua, "#FFC107", Color.BLACK);
        styleButton(btnXoa, "#D32F2F", Color.WHITE);
        styleButton(btnClear, "#9E9E9E", Color.WHITE);

        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnClear);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        loadData();
        addEvents();
    }

    // ==== Styling Helper ====
    private void styleButton(JButton button, String bgColor, Color fgColor) {
        button.setBackground(Color.decode(bgColor));
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(100, 30));
    }

    // ==== Dữ liệu và sự kiện ====
    private void loadData() {
        modelTable.setRowCount(0);
        List<ChiTietPhieuNhap> list = model.getAllByMaPN(maphieu);
        for (ChiTietPhieuNhap ct : list) {
            modelTable.addRow(new Object[]{
                    ct.getId(), ct.getTenhang(), ct.getSoluong(), ct.getDongia()
            });
        }
    }

    private void addEvents() {
        btnThem.addActionListener(e -> {
            try {
                String ten = txtTenHang.getText().trim();
                int sl = Integer.parseInt(txtSoLuong.getText().trim());
                double dg = Double.parseDouble(txtDonGia.getText().trim());
                model.them(maphieu, ten, sl, dg);
                clearFields();
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
            }
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) modelTable.getValueAt(row, 0);
                try {
                    String ten = txtTenHang.getText().trim();
                    int sl = Integer.parseInt(txtSoLuong.getText().trim());
                    double dg = Double.parseDouble(txtDonGia.getText().trim());
                    model.sua(id, ten, sl, dg);
                    clearFields();
                    loadData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chọn một dòng để sửa.");
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) modelTable.getValueAt(row, 0);
                model.xoa(id);
                clearFields();
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Chọn một dòng để xoá.");
            }
        });

        btnClear.addActionListener(e -> clearFields());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtTenHang.setText(modelTable.getValueAt(row, 1).toString());
                    txtSoLuong.setText(modelTable.getValueAt(row, 2).toString());
                    txtDonGia.setText(modelTable.getValueAt(row, 3).toString());
                }
            }
        });
    }

    private void clearFields() {
        txtTenHang.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
    }
}



