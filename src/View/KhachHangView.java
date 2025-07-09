package View;

import Controller.KhachHangController;
import Object.KhachHang;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

public class KhachHangView extends JPanel {

    private final KhachHangController khachHangController = new KhachHangController();

    private JTextField txtMa, txtHoTen, txtTongChiTieu, txtTukhoa, txtMaxChiTieu, txtMinchiTieu, txtSdt;
    private JComboBox<String> cmbGioiTinh;
    private JTable tblKhachHang;
    private DefaultTableModel dtmKhachHang;
    private JButton btnReset, btnThem, btnSua, btnXoa, btnTim;

    public KhachHangView() {
        initGUI();
        addEvents();
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        JPanel pnTitle = new JPanel(new BorderLayout());
        pnTitle.setBackground(Color.WHITE);
        pnTitle.add(lblTitle, BorderLayout.CENTER);

        JPanel pnForm = new JPanel(new GridLayout(3, 4, 15, 15));
        pnForm.setBackground(Color.WHITE);
        JLabel lblMa = new JLabel("Mã KH:");
        JLabel lblHoTen = new JLabel("Họ tên:");
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        JLabel lblTongChiTieu = new JLabel("Tổng chi tiêu:");
        JLabel lblSdt = new JLabel("Số điện thoại:");

        JLabel[] labels = {lblMa, lblHoTen, lblGioiTinh, lblTongChiTieu, lblSdt};
        for (JLabel lbl : labels) {
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        }

        txtMa = new JTextField();
        txtMa.setEditable(false);
        txtHoTen = new JTextField();
        cmbGioiTinh = new JComboBox<>(new String[]{"Chọn giới tính", "Nam", "Nữ"});
        txtTongChiTieu = new JTextField();
        txtTongChiTieu.setEditable(false);
        txtSdt = new JTextField();

        JComponent[] inputs = {txtMa, txtHoTen, cmbGioiTinh, txtTongChiTieu, txtSdt};
        for (JComponent input : inputs) {
            input.setFont(font);
            input.setPreferredSize(new Dimension(200, 30));
        }

        pnForm.add(lblMa);
        pnForm.add(txtMa);
        pnForm.add(lblHoTen);
        pnForm.add(txtHoTen);
        pnForm.add(lblGioiTinh);
        pnForm.add(cmbGioiTinh);
        pnForm.add(lblTongChiTieu);
        pnForm.add(txtTongChiTieu);
        pnForm.add(lblSdt);
        pnForm.add(txtSdt);

        JPanel pnSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        pnSearch.setBackground(Color.WHITE);
        txtTukhoa = new JTextField(10);
        txtMinchiTieu = new JTextField(6);
        txtMaxChiTieu = new JTextField(6);
        btnTim = new JButton("Tìm", new ImageIcon(getClass().getResource("/picture/Search-icon.png")));

        pnSearch.add(new JLabel("Từ khóa:"));
        pnSearch.add(txtTukhoa);
        pnSearch.add(new JLabel("Chi tiêu từ:"));
        pnSearch.add(txtMinchiTieu);
        pnSearch.add(new JLabel("đến:"));
        pnSearch.add(txtMaxChiTieu);
        pnSearch.add(btnTim);

        JPanel pnButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        pnButton.setBackground(Color.WHITE);
        btnThem = new JButton("Thêm", new ImageIcon(getClass().getResource("/picture/add-icon.png")));
        btnSua = new JButton("Sửa", new ImageIcon(getClass().getResource("/picture/Pencil-icon.png")));
        btnXoa = new JButton("Xóa", new ImageIcon(getClass().getResource("/picture/delete-icon.png")));
        btnReset = new JButton("Làm mới", new ImageIcon(getClass().getResource("/picture/Refresh-icon.png")));

        for (JButton btn : new JButton[]{btnThem, btnSua, btnXoa, btnReset}) {
            btn.setFont(font);
            btn.setPreferredSize(new Dimension(120, 35));
            pnButton.add(btn);
        }

        dtmKhachHang = new DefaultTableModel(new Object[]{"Mã KH", "Họ tên", "Giới tính", "Tổng chi tiêu", "SĐT"}, 0);
        tblKhachHang = new JTable(dtmKhachHang);
        tblKhachHang.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
        scrollPane.setPreferredSize(new Dimension(850, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

        JPanel pnTop = new JPanel(new BorderLayout(10, 10));
        pnTop.setBackground(Color.WHITE);
        pnTop.add(pnTitle, BorderLayout.NORTH);
        pnTop.add(pnForm, BorderLayout.CENTER);
        pnTop.add(pnSearch, BorderLayout.SOUTH);

        add(pnTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnButton, BorderLayout.SOUTH);

        loadDataLenTableKhachHang();
    }

    private void addEvents() {
        btnReset.addActionListener(e -> resetForm());

        tblKhachHang.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblKhachHang.getSelectedRow();
                if (row >= 0) {
                    txtMa.setText(tblKhachHang.getValueAt(row, 0).toString());
                    txtHoTen.setText(tblKhachHang.getValueAt(row, 1).toString());
                    cmbGioiTinh.setSelectedItem(tblKhachHang.getValueAt(row, 2).toString());
                    txtTongChiTieu.setText(tblKhachHang.getValueAt(row, 3).toString());
                    txtSdt.setText(tblKhachHang.getValueAt(row, 4).toString());
                }
            }
        });

        txtTukhoa.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                xuLyLiveSearch();
            }

            public void removeUpdate(DocumentEvent e) {
                xuLyLiveSearch();
            }

            public void changedUpdate(DocumentEvent e) {
                xuLyLiveSearch();
            }
        });

        txtMinchiTieu.addActionListener(e -> txtMaxChiTieu.requestFocus());
        txtMaxChiTieu.addActionListener(e -> btnTim.doClick());
        btnTim.addActionListener(e -> xuLyTimKiemTheoKhoang());

        btnThem.addActionListener(e -> {
            boolean ok = khachHangController.themKhachHang(txtHoTen.getText(), cmbGioiTinh.getSelectedItem().toString(), txtSdt.getText());
            JOptionPane.showMessageDialog(this, ok ? "Thêm thành công!" : "Thêm thất bại!");
            if (ok) {
                resetForm();
            }
        });

        btnSua.addActionListener(e -> {
            boolean ok = khachHangController.suaKhachHang(txtMa.getText(), txtHoTen.getText(), cmbGioiTinh.getSelectedItem().toString(), txtSdt.getText());
            JOptionPane.showMessageDialog(this, ok ? "Sửa thành công!" : "Sửa thất bại!");
            if (ok) {
                resetForm();
            }
        });

        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá?", "Xoá", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = khachHangController.xoaKhachHang(txtMa.getText());
                JOptionPane.showMessageDialog(this, ok ? "Xoá thành công!" : "Xoá thất bại!");
                if (ok) {
                    resetForm();
                }
            }
        });
    }

    private void resetForm() {
        loadDataLenTableKhachHang();
        txtMa.setText("");
        txtHoTen.setText("");
        txtTongChiTieu.setText("");
        txtSdt.setText("");
        txtTukhoa.setText("");
        txtMinchiTieu.setText("");
        txtMaxChiTieu.setText("");
        cmbGioiTinh.setSelectedIndex(0);
    }

    private void loadDataLenTableKhachHang() {
        ArrayList<KhachHang> dskh = khachHangController.getListKhachHang();
        loadDataLenTableKhachHang(dskh);
    }

    private void loadDataLenTableKhachHang(ArrayList<KhachHang> dskh) {
        dtmKhachHang.setRowCount(0); 
        DecimalFormat dcf = new DecimalFormat("###,###");

        for (KhachHang kh : dskh) {
            Object[] rowData = {
                kh.getMaKH(),
                kh.getHoTen(),
                kh.getGioiTinh(),
                dcf.format(kh.getTongChiTieu()),
                kh.getSdt()
            };
            dtmKhachHang.addRow(rowData);  
        }
    }

    private void xuLyLiveSearch() {
        ArrayList<KhachHang> dskh = khachHangController.timKiemKhachHangTheoTen(
                khachHangController.getListKhachHang(), txtTukhoa.getText());
        loadDataLenTableKhachHang(dskh);
    }

    private void xuLyTimKiemTheoKhoang() {
        ArrayList<KhachHang> dskh = khachHangController.timKiemKhachHangTheoChiTieu(
                khachHangController.getListKhachHang(), txtMinchiTieu.getText(), txtMaxChiTieu.getText());
        loadDataLenTableKhachHang(dskh);
    }
}
