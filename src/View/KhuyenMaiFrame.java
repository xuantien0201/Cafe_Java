package View;

import Object.KhuyenMai;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class KhuyenMaiFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private List<KhuyenMai> dsKhuyenMai;

    public interface KhuyenMaiSelectionListener {
        void onKhuyenMaiSelected(KhuyenMai km);
    }

    public KhuyenMaiFrame(List<KhuyenMai> danhSach, KhuyenMaiSelectionListener listener) {
        this.dsKhuyenMai = danhSach;

        setTitle("Chọn khuyến mãi");
        setSize(850, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI(listener);
    }

    private void initUI(KhuyenMaiSelectionListener listener) {
        // Tạo bảng
        String[] columns = {"Mã KM", "Chương trình", "Phần trăm KM", "Điều kiện", "Ngày bắt đầu", "Ngày kết thúc", "Tình trạng"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Renderer để bôi màu dòng "Hết hạn"
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String tinhTrang = table.getValueAt(row, 6).toString();
                if (tinhTrang.equalsIgnoreCase("Hết hạn")) {
                    c.setBackground(new Color(220, 220, 220));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    if (isSelected) {
                        c.setBackground(new Color(184, 207, 229));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        // Panel chứa 2 nút
        JButton btnBack = new JButton("Quay lại");
        JButton btnXacNhan = new JButton("Xác nhận");
        btnBack.setPreferredSize(new Dimension(100, 40));
        btnXacNhan.setPreferredSize(new Dimension(100, 40));
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        south.setPreferredSize(new Dimension(850, 60));
        south.add(btnBack);
        south.add(btnXacNhan);
        this.add(south, BorderLayout.SOUTH);

        // Thêm dữ liệu vào bảng
        for (KhuyenMai km : dsKhuyenMai) {
            model.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenChuongTrinh(),
                    km.getPhanTramGiam(),
                    km.getDieuKien(),
                    km.getNgayBatDau(),
                    km.getNgayKetThuc(),
                    km.getTinhTrang()
            });
        }

        // Sự kiện xác nhận
        btnXacNhan.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0 && listener != null) {
                String tinhTrang = table.getValueAt(row, 6).toString();
                if (tinhTrang.equalsIgnoreCase("Đang áp dụng")) {
                    KhuyenMai selected = dsKhuyenMai.get(row);
                    listener.onKhuyenMaiSelected(selected);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Khuyến mãi đã hết hạn, vui lòng chọn mã khác!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một mã khuyến mãi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Sự kiện quay lại
        btnBack.addActionListener(e -> dispose());

        // Nhấn Enter để chọn nếu hợp lệ
        table.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = table.getSelectedRow();
                    if (row >= 0 && listener != null) {
                        String tinhTrang = table.getValueAt(row, 6).toString();
                        if (tinhTrang.equalsIgnoreCase("Đang áp dụng")) {
                            listener.onKhuyenMaiSelected(dsKhuyenMai.get(row));
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Khuyến mãi đã hết hạn, vui lòng chọn mã khác!");
                        }
                    }
                }
            }
        });
    }
}
