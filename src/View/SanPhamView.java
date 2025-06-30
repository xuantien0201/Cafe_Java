package View;

import Controller.SanPhamController;
import Object.Product;
import Object.Category;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SanPhamView extends JPanel {
    private JTextField txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa;
    private JTextField txtTen, txtGia, txtMoTa, txtHinh;
    private JComboBox<String> cbLoai, cbLoaiInput;
    private SanPhamController controller;

    public SanPhamView() {
        controller = new SanPhamController();
        setLayout(new BorderLayout(10, 10));

        // Panel chứa title, input, và tìm kiếm
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Tiêu đề
        JLabel title = new JLabel("QUẢN LÝ SẢN PHẨM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(title);

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        txtTen = new JTextField();
        cbLoaiInput = new JComboBox<>();
        txtGia = new JTextField();
        txtMoTa = new JTextField();
        txtHinh = new JTextField();

        controller.loadCategoryToComboBox(cbLoaiInput);

        inputPanel.add(new JLabel("Tên SP:"));
        inputPanel.add(new JLabel("Danh mục:"));
        inputPanel.add(new JLabel("Giá:"));
        inputPanel.add(new JLabel("Mô tả:"));
        inputPanel.add(new JLabel("Hình ảnh:"));

        inputPanel.add(txtTen);
        inputPanel.add(cbLoaiInput);
        inputPanel.add(txtGia);
        inputPanel.add(txtMoTa);
        inputPanel.add(txtHinh);

        topPanel.add(inputPanel);

        // Panel tìm kiếm nâng cao
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(15);
        cbLoai = new JComboBox<>();
        cbLoai.addItem("Tất cả");
        controller.loadCategoryToComboBox(cbLoai);

        JButton btnTim = new JButton("Tìm...");

        filters.add(new JLabel("Từ khóa:"));
        filters.add(txtTimKiem);
        filters.add(new JLabel("Danh mục:"));
        filters.add(cbLoai);
        filters.add(btnTim);

        searchPanel.add(filters, BorderLayout.CENTER);
        topPanel.add(searchPanel);

        add(topPanel, BorderLayout.NORTH);

        // Bảng sản phẩm
        String[] columnNames = {"Mã SP", "Tên SP", "Mã loại", "Giá", "Mô tả", "Hình ảnh"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút thao tác
        JPanel actionPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        actionPanel.add(btnThem);
        actionPanel.add(btnSua);
        actionPanel.add(btnXoa);
        add(actionPanel, BorderLayout.SOUTH);

        // Load dữ liệu sản phẩm
        loadData();

        // Sự kiện
        btnTim.addActionListener(e -> controller.locSanPham(this));
        btnThem.addActionListener(e -> controller.themSanPham(this));
        btnSua.addActionListener(e -> controller.suaSanPham(this));
        btnXoa.addActionListener(e -> controller.xoaSanPham(this));

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { controller.locSanPham(SanPhamView.this); }
            public void removeUpdate(DocumentEvent e) { controller.locSanPham(SanPhamView.this); }
            public void changedUpdate(DocumentEvent e) { controller.locSanPham(SanPhamView.this); }
        });

        cbLoai.addActionListener(e -> controller.locSanPham(this));

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<Product> ds = controller.layTatCaSanPham();
        for (Product p : ds) {
            tableModel.addRow(new Object[]{
                p.getProductId(),
                p.getName(),
                p.getCategory().getCategoryId(),
                p.getPrice(),
                p.getDescription(),
                p.getImage()
            });
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row != -1) {
            txtTen.setText(table.getValueAt(row, 1).toString());
            cbLoaiInput.setSelectedItem(table.getValueAt(row, 2).toString());
            txtGia.setText(table.getValueAt(row, 3).toString());
            txtMoTa.setText(table.getValueAt(row, 4).toString());
            txtHinh.setText(table.getValueAt(row, 5).toString());
        }
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getTxtTen() {
        return txtTen;
    }

    public JComboBox<String> getCbLoaiInput() {
        return cbLoaiInput;
    }

    public JTextField getTxtGia() {
        return txtGia;
    }

    public JTextField getTxtMoTa() {
        return txtMoTa;
    }

    public JTextField getTxtHinh() {
        return txtHinh;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public JComboBox<String> getCbLoai() {
        return cbLoai;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void clearForm() {
        txtTen.setText("");
        cbLoaiInput.setSelectedIndex(0);
        txtGia.setText("");
        txtMoTa.setText("");
        txtHinh.setText("");
    }
}
