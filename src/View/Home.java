/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Controller.HoaDonController;
import Object.HoaDon;
import Object.Product;
import Object.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import Model.ProductModel;
import Model.UserModel;
/**
 *
 * @author ADMIN
 */
public class Home extends javax.swing.JPanel {
    private JPanel coffeePanel, sinhToPanel, traPanel, nuocEpPanel, banhPanel;
    private User currentUser;
    private double tongTien = 0;
    
    public Home(User user) {
        this.currentUser = user;
        initComponents();
        // Điều chỉnh tỷ lệ giữa menu và hóa đơn
        jTabbedPane1.setPreferredSize(new Dimension(700, 650));
        jTabbedPane1.setBackground(new java.awt.Color(120,90,60));
        jPanel6.setPreferredSize(new Dimension(300, 650));
        loadProducts();
        lblTongTien.setText("");
    }
    
    private void loadProducts() {
        // Tạo panel cho từng loại sản phẩm
        JPanel coffeePanel = new JPanel(new GridLayout(0, 5, 10, 10));
        JPanel smoothiePanel = new JPanel(new GridLayout(0, 5, 10, 10));
        JPanel teaPanel = new JPanel(new GridLayout(0, 5, 10, 10));
        JPanel juicePanel = new JPanel(new GridLayout(0, 5, 10, 10));
        JPanel cakePanel = new JPanel(new GridLayout(0, 5, 10, 10));

        coffeePanel.setBackground(Color.WHITE);
        smoothiePanel.setBackground(Color.WHITE);
        teaPanel.setBackground(Color.WHITE);
        juicePanel.setBackground(Color.WHITE);
        cakePanel.setBackground(Color.WHITE);

        // Gắn vào scroll pane
        jScrollPane2.setViewportView(coffeePanel);
        jScrollPane3.setViewportView(smoothiePanel);
        jScrollPane4.setViewportView(teaPanel);
        jScrollPane5.setViewportView(juicePanel);
        jScrollPane6.setViewportView(cakePanel);

        // Tạo panel hóa đơn
        invoiceListPanel = new JPanel();
        invoiceListPanel.setLayout(new BoxLayout(invoiceListPanel, BoxLayout.Y_AXIS));
        invoiceListPanel.setBackground(Color.WHITE);
        jScrollPane1.setViewportView(invoiceListPanel);

        // Load sản phẩm
        List<Product> products = getAllProducts();

        for (Product p : products) {
            JPanel productPanel = createProductPanel(p);
            String category = p.getCategory().getName().trim();
            switch (category) {
                case "Coffee" -> coffeePanel.add(productPanel);
                case "Sinh tố" -> smoothiePanel.add(productPanel);
                case "Trà" -> teaPanel.add(productPanel);
                case "Nước ép" -> juicePanel.add(productPanel);
                case "Bánh" -> cakePanel.add(productPanel);
            }
        }

        // Cập nhật lại
        coffeePanel.revalidate(); coffeePanel.repaint();
        smoothiePanel.revalidate(); smoothiePanel.repaint();
        teaPanel.revalidate(); teaPanel.repaint();
        juicePanel.revalidate(); juicePanel.repaint();
        cakePanel.revalidate(); cakePanel.repaint();
    }



    private JPanel createProductPanel(Product p) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(140, 180)); // Kích thước cố định
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(new Color(200, 200, 200), 1));

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/picture/" + p.getImage()));
            Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH); // Ảnh nhỏ hơn kích thước panel
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setPreferredSize(new Dimension(140, 140));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);

            JLabel nameLabel = new JLabel(p.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setPreferredSize(new Dimension(140, 20));

            JLabel priceLabel = new JLabel(String.format("%.0f VNĐ", p.getPrice()));
            priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
            priceLabel.setForeground(new Color(0, 102, 204));
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabel.setPreferredSize(new Dimension(140, 20));

            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.setPreferredSize(new Dimension(140, 40));
            textPanel.setBackground(Color.WHITE);
            textPanel.add(nameLabel);
            textPanel.add(priceLabel);

            panel.add(imageLabel, BorderLayout.CENTER);
            panel.add(textPanel, BorderLayout.SOUTH);
        } catch (Exception ex) {
            JLabel errorLabel = new JLabel("Không có ảnh", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.ITALIC, 11));
            errorLabel.setPreferredSize(new Dimension(140, 180));
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                addProductToInvoice(p);
            }
        });

        return panel;
    }


    private void addProductToInvoice(Product p) {
        InvoiceItemPanel itemPanel = new InvoiceItemPanel(
            p,
            this::updateTotalAmount,
            removedPanel -> {
                invoiceItems.remove(removedPanel); // Xóa khỏi danh sách
                updateTotalAmount();               // Cập nhật tổng tiền
            }
        );

        invoiceListPanel.add(itemPanel);
        invoiceItems.add(itemPanel);
        updateTotalAmount();
        invoiceListPanel.revalidate();
        invoiceListPanel.repaint();
    }



    private void updateTotalAmount() {
        double total = 0;
        for (InvoiceItemPanel item : invoiceItems) {
            Product p = item.getProduct();
            int quantity = item.getQuantity();
            total += p.getPrice() * quantity;
        }

        lblTongTien.setText(String.format("%.0f VNĐ", total));
    }

    // Giả lập sản phẩm (thay bằng ProductDAO thực tế)
    private List<Product> getAllProducts() {
        ProductModel model = new ProductModel(); // tạo đối tượng mới
        return model.getAllProducts();           // gọi phương thức
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnThanhtoan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        jTabbedPane1.setBackground(new java.awt.Color(204, 153, 0));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addTab("Coffee", jScrollPane2);
        jTabbedPane1.addTab("Sinh tố", jScrollPane3);
        jTabbedPane1.addTab("Trà", jScrollPane4);
        jTabbedPane1.addTab("Nước ép", jScrollPane5);
        jTabbedPane1.addTab("Bánh", jScrollPane6);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuy.setText("Hủy đơn");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnThanhtoan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhtoan.setText("Thanh toán");
        btnThanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhtoanActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Thành tiền : ");

        jScrollPane1.setMaximumSize(new java.awt.Dimension(430, 430));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Hóa đơn");

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTien.setText("Tổng tiền");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(242, 242, 242)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addGap(46, 46, 46)
                            .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnThanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTien))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        clearInvoiceItems();        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnThanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhtoanActionPerformed

        if (invoiceListPanel.getComponentCount() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào được chọn.");
            return;
        }

        java.util.Date now = new java.util.Date();
        java.time.LocalDateTime localNow = java.time.LocalDateTime.now();

        String gioVao = java.time.LocalTime.now().toString().substring(0, 8);
        String gioRa = gioVao;

        // 👇 Không cần tạo nguoiLap kiểu String nữa
        // String nguoiLap = currentUser.getFullName();

        StringBuilder danhSachSanPham = new StringBuilder();
        StringBuilder soLuongSanPham = new StringBuilder();
        tongTien = 0;
        int tongSoLuong = 0;

        for (Component comp : invoiceListPanel.getComponents()) {
            if (comp instanceof InvoiceItemPanel itemPanel) {
                int maSP = itemPanel.getProduct().getProductId();
                int soLuong = itemPanel.getQuantity();
                double donGia = itemPanel.getProduct().getPrice();

                danhSachSanPham.append(maSP).append(",");
                soLuongSanPham.append(soLuong).append(",");
                tongTien += donGia * soLuong;
                tongSoLuong += soLuong;
            }
        }

        danhSachSanPham.setLength(danhSachSanPham.length() - 1);
        soLuongSanPham.setLength(soLuongSanPham.length() - 1);

        // 👉 Format ngày giờ
        String ngay = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localNow);
        String gio = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss").format(localNow);

        // 👉 Tạo view Hóa đơn và truyền dữ liệu
        View.HoaDonView panelHoaDon = new View.HoaDonView(currentUser, this); // 👈 truyền cả this

        panelHoaDon.getLblNgayThang().setText(ngay);
        panelHoaDon.getLblGio().setText(gio);
        panelHoaDon.getLblSoLuong().setText(String.valueOf(tongSoLuong));
        DecimalFormat df = new DecimalFormat("#,###");
        panelHoaDon.getLblTongTien().setText(df.format(tongTien));
        
        // ✅ Lấy họ tên từ MaNV
        try {
            UserModel userModel = new UserModel();
            String tenNguoiLap = userModel.getFullNameByMaNV(currentUser.getMaNV());
            panelHoaDon.getLblNguoiLap().setText(tenNguoiLap);
        } catch (Exception e) {
            panelHoaDon.getLblNguoiLap().setText("Không xác định");
            e.printStackTrace();
        }

        // 👉 Truyền đối tượng User thay vì String nguoiLap
//        panelHoaDon.setInvoiceData(
//            now,
//            gioVao,
//            gioRa,
//            currentUser, // 👈 truyền User
//            danhSachSanPham.toString(),
//            soLuongSanPham.toString(),
//            tongTien
//        );

        panelHoaDon.setInvoiceData(
            now,
            gioVao,
            gioRa,
            danhSachSanPham.toString(),
            soLuongSanPham.toString(),
            tongTien
        );


        // Hiển thị bằng JDialog
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Chi tiết hóa đơn", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(panelHoaDon);
        dialog.setSize(479, 530);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnThanhtoanActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    public void clearInvoiceItems() {
       invoiceListPanel.removeAll();
       invoiceItems.clear(); // Xóa luôn danh sách tạm
       tongTien = 0;
       lblTongTien.setText("0 VNĐ"); // hoặc dùng format nếu muốn
       invoiceListPanel.revalidate();
       invoiceListPanel.repaint();
   }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnThanhtoan;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTongTien;
    // End of variables declaration//GEN-END:variables
    private JPanel invoiceListPanel; // nơi chứa các dòng hóa đơn
    private List<InvoiceItemPanel> invoiceItems = new ArrayList<>();


}
