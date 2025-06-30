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
import Object.NhaCungCap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NhaCungCapView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnThem, btnSua, btnXoa;
    private NhaCungCapModel model;

    private final Color bgBe = new Color(245, 222, 179);
    private final Color brown = new Color(139, 69, 19);
    private final Color white = Color.WHITE;
    private final Color lightBrown = new Color(205, 133, 63);
    private final Color redBtn = new Color(200, 50, 50);

    public NhaCungCapView() {
        setLayout(new BorderLayout());
        setBackground(bgBe);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        model = new NhaCungCapModel();

        JLabel title = new JLabel("QUẢN LÝ NHÀ CUNG CẤP", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(brown);
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Mã NCC", "Tên NCC", "Địa chỉ", "SĐT"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(255, 239, 213));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(bgBe);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(bgBe);
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));

      
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { searchNow(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { searchNow(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchNow(); }

            private void searchNow() {
                String keyword = txtSearch.getText().trim();
                if (!keyword.isEmpty()) {
                    loadTimKiem(keyword);
                } else {
                    loadData();
                }
            }
        });

        searchPanel.add(new JLabel("Tìm tên NCC:"));
        searchPanel.add(txtSearch);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnPanel.setBackground(bgBe);

        btnThem = createButton("➕ Thêm", brown, white);
        btnSua = createButton("✏️ Sửa", lightBrown, white);
        btnXoa = createButton("🗑️ Xoá", redBtn, white);

        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);

        bottomPanel.add(searchPanel, BorderLayout.NORTH);
        bottomPanel.add(btnPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData();

        btnThem.addActionListener(e -> new FormNhaCungCap(null, this).setVisible(true));
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int mancc = (int) tableModel.getValueAt(row, 0);
                new FormNhaCungCap(mancc, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Chọn một dòng để sửa.");
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int mancc = (int) tableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Xoá NCC này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.delete(mancc);
                    loadData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chọn một dòng để xoá.");
            }
        });
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<NhaCungCap> list = model.getAllNCC();
        for (NhaCungCap ncc : list) {
            tableModel.addRow(new Object[]{
                    ncc.getMancc(),
                    ncc.getTenncc(),
                    ncc.getDiachi(),
                    ncc.getSdt()
            });
        }
    }

    public void loadTimKiem(String keyword) {
        tableModel.setRowCount(0);
        List<NhaCungCap> list = model.timKiem(keyword);
        for (NhaCungCap ncc : list) {
            tableModel.addRow(new Object[]{
                    ncc.getMancc(),
                    ncc.getTenncc(),
                    ncc.getDiachi(),
                    ncc.getSdt()
            });
        }
    }
}



