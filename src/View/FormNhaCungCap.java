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
import java.awt.*;
import java.util.List;

public class FormNhaCungCap extends JDialog {
    private JTextField txtTen, txtDiaChi, txtSDT;
    private JButton btnLuu, btnHuy;

    private Integer mancc;
    private NhaCungCapModel model;
    private NhaCungCapView parent;

    public FormNhaCungCap(Integer mancc, NhaCungCapView parent) {
        this.mancc = mancc;
        this.parent = parent;
        this.model = new NhaCungCapModel();

        setTitle(mancc == null ? "➕ Thêm nhà cung cấp" : "✏️ Sửa nhà cung cấp");
        setModal(true);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

       
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 15));
        form.setBorder(new EmptyBorder(20, 30, 10, 30));
        form.setBackground(Color.WHITE);

        txtTen = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();

        form.add(new JLabel("Tên NCC:"));
        form.add(txtTen);
        form.add(new JLabel("Địa chỉ:"));
        form.add(txtDiaChi);
        form.add(new JLabel("Số điện thoại:"));
        form.add(txtSDT);

        add(form, BorderLayout.CENTER);

  
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnPanel.setBackground(Color.WHITE);

        btnLuu = new JButton("💾 Lưu");
        btnHuy = new JButton("❌ Hủy");

        btnLuu.setBackground(new Color(40, 167, 69));
        btnLuu.setForeground(Color.WHITE);
        btnHuy.setBackground(new Color(220, 53, 69));
        btnHuy.setForeground(Color.WHITE);

        btnPanel.add(btnLuu);
        btnPanel.add(btnHuy);
        add(btnPanel, BorderLayout.SOUTH);

       
        if (mancc != null) {
            setData();
        }

    
        btnLuu.addActionListener(e -> save());
        btnHuy.addActionListener(e -> dispose());
    }

    private void setData() {
        List<NhaCungCap> list = model.getAllNCC();
        for (NhaCungCap ncc : list) {
            if (ncc.getMancc() == mancc) {
                txtTen.setText(ncc.getTenncc());
                txtDiaChi.setText(ncc.getDiachi());
                txtSDT.setText(ncc.getSdt());
                break;
            }
        }
    }

    private void save() {
        String ten = txtTen.getText().trim();
        String diachi = txtDiaChi.getText().trim();
        String sdt = txtSDT.getText().trim();

        if (ten.isEmpty() || diachi.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (mancc == null) {
            model.insert(new NhaCungCap(0, ten, diachi, sdt));
        } else {
            model.update(new NhaCungCap(mancc, ten, diachi, sdt));
        }

        parent.loadData();
        dispose();
    }
}

