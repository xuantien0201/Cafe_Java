package View;

import Controller.KhachHangController;
import Object.KhachHang;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class ThemKhachHangFrame extends JDialog {
    private JTextField txtHoTen, txtSdt;
    private JComboBox<String> cmbGioiTinh;
    private JButton btnThem, btnHuy;

    private JTable tblKhachHang;
    private DefaultTableModel dtmKhachHang;

    private final KhachHangController khachHangController = new KhachHangController();

    public interface KhachHangCallback {
        void onKhachHangAdded(KhachHang khachHang);
    }


    public ThemKhachHangFrame(Window parent, KhachHangCallback callback) {
        super(parent, "Thêm khách hàng", ModalityType.APPLICATION_MODAL);
        initGUI();
        initEvents(callback);
        loadKhachHangTable(khachHangController.getListKhachHang());
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));

        // --- Form Input ---
        JLabel lblHoTen = new JLabel("Họ tên:");
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        JLabel lblSdt = new JLabel("Số điện thoại:");

        txtHoTen = new JTextField(20);
        txtSdt = new JTextField(15);
        cmbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        formPanel.add(lblHoTen);
        formPanel.add(txtHoTen);
        formPanel.add(lblGioiTinh);
        formPanel.add(cmbGioiTinh);
        formPanel.add(lblSdt);
        formPanel.add(txtSdt);

        // --- Button ---
        btnThem = new JButton("Thêm");
        btnHuy = new JButton("Hủy");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnHuy);
        btnPanel.add(btnThem);

        // --- Table ---
        dtmKhachHang = new DefaultTableModel(new String[]{"Mã KH", "Họ tên", "Giới tính", "Tổng chi tiêu", "SĐT"}, 0);
        tblKhachHang = new JTable(dtmKhachHang);
        tblKhachHang.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tblKhachHang);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

        // --- Main Layout ---
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void initEvents(KhachHangCallback callback) {
    btnHuy.addActionListener(e -> dispose());

    btnThem.addActionListener(e -> {
        String hoTen = txtHoTen.getText().trim();
        String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
        String sdt = txtSdt.getText().trim();

        if (hoTen.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra khách đã tồn tại chưa
        KhachHang khachCu = khachHangController.timKhachHangTheoSdt(sdt);
        if (khachCu != null) {
            JOptionPane.showMessageDialog(this, "Khách hàng đã tồn tại, sử dụng lại thông tin.");
            callback.onKhachHangAdded(khachCu);
            dispose();
            return;
        }

        // Nếu chưa có thì thêm mới
        boolean ok = khachHangController.themKhachHang(hoTen, gioiTinh, sdt);
        if (ok) {
            KhachHang khMoi = khachHangController.timKhachHangTheoSdt(sdt);
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            callback.onKhachHangAdded(khMoi); // Truyền nguyên object
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    });

    txtSdt.getDocument().addDocumentListener(new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {
            filterKhachHangBySdt();
        }

        public void removeUpdate(DocumentEvent e) {
            filterKhachHangBySdt();
        }

        public void changedUpdate(DocumentEvent e) {
            filterKhachHangBySdt();
        }
    });

    tblKhachHang.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = tblKhachHang.getSelectedRow();
            if (selectedRow >= 0) {
                txtHoTen.setText(tblKhachHang.getValueAt(selectedRow, 1).toString());
                cmbGioiTinh.setSelectedItem(tblKhachHang.getValueAt(selectedRow, 2).toString());
                txtSdt.setText(tblKhachHang.getValueAt(selectedRow, 4).toString());
            }
        }
    });
}


    private void filterKhachHangBySdt() {
        String keyword = txtSdt.getText().trim().toLowerCase();
        ArrayList<KhachHang> list = khachHangController.getListKhachHang();
        ArrayList<KhachHang> filtered = new ArrayList<>();
        for (KhachHang kh : list) {
            if (kh.getSdt().toLowerCase().contains(keyword)) {
                filtered.add(kh);
            }
        }
        loadKhachHangTable(filtered);
    }

    private void loadKhachHangTable(ArrayList<KhachHang> list) {
        dtmKhachHang.setRowCount(0);
        for (KhachHang kh : list) {
            Vector<Object> vec = new Vector<>();
            vec.add(kh.getMaKH());
            vec.add(kh.getHoTen());
            vec.add(kh.getGioiTinh());
            vec.add(kh.getTongChiTieu());
            vec.add(kh.getSdt());
            dtmKhachHang.addRow(vec);
        }
    }
}
