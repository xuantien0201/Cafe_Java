package View;

import Model.UserModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Object.User;

public class MainView extends JFrame {

    JPanel pnMenu, pnCard;
    JLabel lblKhachHang, lblHoaDon, lblSanPham, lblNhanVien, lblNhapHang, lblKhuyenMai;
    CardLayout cardLayout;
    KhachHangView khachHangView;
    Home homeView;
    KhuyenMaiView khuyenMaiView;
    User currentUser;  // Nhận từ login
    SanPhamView sanPhamView;
    QuanLyNhanVien quanLyNhanVien;
    NhapHang nhapHang;

//    public MainView() {
//        setTitle("Phần mềm quản lý Cafe");
//        setSize(1200, 800);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        initGUI();
//        setVisible(true);
//    }
    public MainView(User user) {
        this.currentUser = user;
        setTitle("Phần mềm quản lý Cafe");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initGUI();
        setVisible(true);
    }

    private void initGUI() {
        Container con = getContentPane();
        con.setLayout(new BorderLayout());

        // ========== MENU BÊN TRÁI ==========
        pnMenu = new JPanel();
        pnMenu.setPreferredSize(new Dimension(200, 0));
        pnMenu.setBackground(new Color(63, 74, 89));
        pnMenu.setLayout(new BoxLayout(pnMenu, BoxLayout.Y_AXIS));

        // ======= THÊM ẢNH QUÁN LÊN MENU =======
//        try {
//            ImageIcon icon = new ImageIcon(getClass().getResource("/picture/ALAMA_poster_new.jpg"));
//            Image scaled = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
//            JLabel logoLabel = new JLabel(new ImageIcon(scaled));
//            logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // khoảng cách
//            pnMenu.add(logoLabel);
//        } catch (Exception e) {
//            JLabel fallback = new JLabel("Tên quán", SwingConstants.CENTER);
//            fallback.setForeground(Color.WHITE);
//            fallback.setFont(new Font("Arial", Font.BOLD, 18));
//            fallback.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
//            fallback.setAlignmentX(Component.CENTER_ALIGNMENT);
//            pnMenu.add(fallback);
//        }
        lblKhachHang = new JLabel("  Quản lý khách hàng");
        styleMenuLabel(lblKhachHang);

        lblHoaDon = new JLabel("  Quản lý hóa đơn");
        styleMenuLabel(lblHoaDon);

        lblSanPham = new JLabel("  Quản lý sản phẩm");
        styleMenuLabel(lblSanPham);

        lblNhanVien = new JLabel("  Quản lý nhân viên");
        styleMenuLabel(lblNhanVien);

        lblNhapHang = new JLabel("  Quản lý nhập hàng");
        styleMenuLabel(lblNhapHang);

        lblKhuyenMai = new JLabel("  Quản lý khuyến mãi");
        styleMenuLabel(lblKhuyenMai);

        String chucVu = "Nhân Viên"; // Mặc định nếu không lấy được

        try {
            UserModel userModel = new UserModel();
            chucVu = userModel.getChucVuByMaNV(currentUser.getMaNV());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Thêm vào menu theo vai trò
        pnMenu.add(Box.createVerticalStrut(20));
        pnMenu.add(lblKhachHang);
        pnMenu.add(lblHoaDon);

        if (chucVu.equalsIgnoreCase("Quản lý")) {
            pnMenu.add(lblSanPham);
            pnMenu.add(lblNhanVien);
            pnMenu.add(lblNhapHang);
            pnMenu.add(lblKhuyenMai);
        }

        // ========== KHU VỰC HIỂN THỊ CHÍNH ==========
        cardLayout = new CardLayout();
        pnCard = new JPanel(cardLayout);

        // Thêm view khách hàng mặc định
        khachHangView = new KhachHangView();
        pnCard.add(khachHangView, "KhachHang");

        // Không khởi tạo Home vội
        // pnCard.add(homeView, "HoaDon"); ← bỏ
        // ========== SỰ KIỆN ==========
        lblKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnCard, "KhachHang");
                highlightMenu(lblKhachHang);
            }
        });

        lblHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (homeView == null) {
                    homeView = new Home(currentUser); // tạo khi cần
                    pnCard.add(homeView, "HoaDon");
                }
                cardLayout.show(pnCard, "HoaDon");
                highlightMenu(lblHoaDon);
            }
        });

        lblSanPham.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (sanPhamView == null) {
                    sanPhamView = new SanPhamView();
                    pnCard.add(sanPhamView, "SanPham");
                }
                cardLayout.show(pnCard, "SanPham");
                highlightMenu(lblSanPham);
            }
        });
        lblKhuyenMai.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (khuyenMaiView == null) {
                    khuyenMaiView = new KhuyenMaiView();
                    pnCard.add(khuyenMaiView, "KhuyenMai");
                }
                cardLayout.show(pnCard, "KhuyenMai");
                highlightMenu(lblKhuyenMai);
            }
        });

        lblNhanVien.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (quanLyNhanVien == null) {
                    quanLyNhanVien = new QuanLyNhanVien();
                    pnCard.add(quanLyNhanVien, "NhanVien");
                    pnCard.revalidate();
                    pnCard.repaint();
                }
                cardLayout.show(pnCard, "NhanVien");
                highlightMenu(lblNhanVien);
            }
        });

        lblNhapHang.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (nhapHang == null) {
                    nhapHang = new NhapHang();
                    pnCard.add(nhapHang, "NhapHang");
                    pnCard.revalidate();
                    pnCard.repaint();
                }
                cardLayout.show(pnCard, "NhapHang");
                highlightMenu(lblNhapHang);
            }
        });

        // ========== GẮN VÀO FRAME ==========
        con.add(pnMenu, BorderLayout.WEST);
        con.add(pnCard, BorderLayout.CENTER);

        // Mặc định hiển thị khách hàng
        cardLayout.show(pnCard, "KhachHang");
        highlightMenu(lblKhachHang);
    }

    private void styleMenuLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setOpaque(true);
        label.setBackground(new Color(63, 74, 89));
        label.setPreferredSize(new Dimension(200, 50));
        label.setMaximumSize(new Dimension(200, 50));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void highlightMenu(JLabel selected) {
        lblKhachHang.setBackground(new Color(63, 74, 89));
        lblHoaDon.setBackground(new Color(63, 74, 89));
        lblSanPham.setBackground(new Color(63, 74, 89));
        lblNhanVien.setBackground(new Color(63, 74, 89));
        lblNhapHang.setBackground(new Color(63, 74, 89));
        lblKhuyenMai.setBackground(new Color(63, 74, 89)); 

        selected.setBackground(new Color(51, 202, 187));
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MainView());
//    }
}
