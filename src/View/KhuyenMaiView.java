package View;

import Controller.KhuyenMaiController;
import Object.KhuyenMai;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class KhuyenMaiView extends JPanel {

    private final KhuyenMaiController khuyenMaiController = new KhuyenMaiController();

    private JTextField txtMa, txtMoTa, txtMucGiam, txtDieuKien;
    private JFormattedTextField txtNgayBD, txtNgayKT;
    private JComboBox<String> cmbTinhTrang;
    private JTable tblKhuyenMai;
    private DefaultTableModel dtmKhuyenMai;
    private JButton btnReset, btnThem, btnSua, btnXoa;

    public KhuyenMaiView() {
        setLookAndFeel("Windows");
        initGUI();
        addEvents();
    }

    private void setLookAndFeel(String name) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (name.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        JLabel lblTitle = new JLabel("QUẢN LÝ KHUYẾN MÃI", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        JPanel pnTitle = new JPanel(new BorderLayout());
        pnTitle.setBackground(Color.WHITE);
        pnTitle.add(lblTitle, BorderLayout.CENTER);

        JPanel pnForm = new JPanel(new GridLayout(4, 4, 10, 10));
        pnForm.setBackground(Color.WHITE);

        txtMa = new JTextField(); 
        txtMoTa = new JTextField();
        txtMucGiam = new JTextField();
        txtDieuKien = new JTextField();
        txtNgayBD = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        txtNgayKT = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        cmbTinhTrang = new JComboBox<>(new String[]{"Đang áp dụng", "Hết hạn", "Không khuyến mãi"});

        JLabel[] labels = {
                new JLabel("Mã giảm giá:"), new JLabel("Mô tả:"),
                new JLabel("Mức giảm:"), new JLabel("Điều kiện:"),
                new JLabel("Ngày bắt đầu:"), new JLabel("Ngày kết thúc:"),
                new JLabel("Tình trạng:")
        };

        for (JLabel lbl : labels) lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JComponent[] inputs = {txtMa, txtMoTa, txtMucGiam, txtDieuKien, txtNgayBD, txtNgayKT, cmbTinhTrang};
        for (JComponent input : inputs) input.setFont(font);

        pnForm.add(labels[0]); pnForm.add(txtMa);
        pnForm.add(labels[1]); pnForm.add(txtMoTa);
        pnForm.add(labels[2]); pnForm.add(txtMucGiam);
        pnForm.add(labels[3]); pnForm.add(txtDieuKien);
        pnForm.add(labels[4]); pnForm.add(txtNgayBD);
        pnForm.add(labels[5]); pnForm.add(txtNgayKT);
        pnForm.add(labels[6]); pnForm.add(cmbTinhTrang);

        JPanel pnButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        pnButton.setBackground(Color.WHITE);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnReset = new JButton("Làm mới");

        for (JButton btn : new JButton[]{btnThem, btnSua, btnXoa, btnReset}) {
            btn.setFont(font);
            btn.setPreferredSize(new Dimension(120, 35));
            pnButton.add(btn);
        }

        dtmKhuyenMai = new DefaultTableModel(new Object[]{
                "Mã", "Mô tả", "Mức giảm", "Điều kiện", "Ngày BĐ", "Ngày KT", "Tình trạng"
        }, 0);
        tblKhuyenMai = new JTable(dtmKhuyenMai);
        tblKhuyenMai.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tblKhuyenMai);
        scrollPane.setPreferredSize(new Dimension(850, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách khuyến mãi"));

        JPanel pnTop = new JPanel(new BorderLayout(10, 10));
        pnTop.setBackground(Color.WHITE);
        pnTop.add(pnTitle, BorderLayout.NORTH);
        pnTop.add(pnForm, BorderLayout.CENTER);

        add(pnTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnButton, BorderLayout.SOUTH);

        loadDataLenTable();
    }

    private void addEvents() {
        btnReset.addActionListener(e -> resetForm());

        tblKhuyenMai.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblKhuyenMai.getSelectedRow();
                if (row >= 0) {
                    txtMa.setText(tblKhuyenMai.getValueAt(row, 0).toString());
                    txtMoTa.setText(tblKhuyenMai.getValueAt(row, 1).toString());
                    txtMucGiam.setText(tblKhuyenMai.getValueAt(row, 2).toString());
                    txtDieuKien.setText(tblKhuyenMai.getValueAt(row, 3).toString());
                    txtNgayBD.setText(tblKhuyenMai.getValueAt(row, 4).toString());
                    txtNgayKT.setText(tblKhuyenMai.getValueAt(row, 5).toString());
                    cmbTinhTrang.setSelectedItem(tblKhuyenMai.getValueAt(row, 6).toString());
                }
            }
        });

        btnThem.addActionListener(e -> {
            boolean ok = khuyenMaiController.themKhuyenMai(
                    txtMa.getText(),
                    txtMoTa.getText(),
                    txtMucGiam.getText(),
                    txtDieuKien.getText(),
                    txtNgayBD.getText(),
                    txtNgayKT.getText(),
                    cmbTinhTrang.getSelectedItem().toString()
            );
            JOptionPane.showMessageDialog(this, ok ? "Thêm thành công!" : "Thêm thất bại!");
            if (ok) resetForm();
        });

        btnSua.addActionListener(e -> {
            boolean ok = khuyenMaiController.suaKhuyenMai(
                    txtMa.getText(),
                    txtMoTa.getText(),
                    txtMucGiam.getText(),
                    txtDieuKien.getText(),
                    txtNgayBD.getText(),
                    txtNgayKT.getText(),
                    cmbTinhTrang.getSelectedItem().toString()
            );
            JOptionPane.showMessageDialog(this, ok ? "Sửa thành công!" : "Sửa thất bại!");
            if (ok) resetForm();
        });

        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá?", "Xoá", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = khuyenMaiController.xoaKhuyenMai(txtMa.getText());
                JOptionPane.showMessageDialog(this, ok ? "Xoá thành công!" : "Xoá thất bại!");
                if (ok) resetForm();
            }
        });
    }

    private void resetForm() {
        loadDataLenTable();
        txtMa.setText("");
        txtMoTa.setText("");
        txtMucGiam.setText("");
        txtDieuKien.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        cmbTinhTrang.setSelectedIndex(0);
    }

    private void loadDataLenTable() {
        ArrayList<KhuyenMai> list = khuyenMaiController.getListKhuyenMai();
        dtmKhuyenMai.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (KhuyenMai km : list) {
            Object[] rowData = {
                    km.getMaKM(),
                    km.getTenChuongTrinh(),
                    km.getPhanTramGiam(),
                    km.getDieuKien(),
                    sdf.format(km.getNgayBatDau()),
                    sdf.format(km.getNgayKetThuc()),
                    km.getTinhTrang()
            };
            dtmKhuyenMai.addRow(rowData);
        }
    }
} 
