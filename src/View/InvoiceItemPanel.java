package View;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import Object.Product;

public class InvoiceItemPanel extends JPanel {
    private final Product product;
    private int quantity = 1;

    private final JLabel quantityLabel;
    private final JLabel totalPriceLabel;

    private final Runnable updateTotalCallback;
    private final Consumer<InvoiceItemPanel> removeCallback;

    public InvoiceItemPanel(Product p, Runnable updateTotalCallback, Consumer<InvoiceItemPanel> removeCallback) {
        this.product = p;
        this.updateTotalCallback = updateTotalCallback;
        this.removeCallback = removeCallback;

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // full chiều ngang

        JLabel nameLabel = new JLabel("  " + p.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setPreferredSize(new Dimension(175, 25));

        JButton decreaseBtn = new JButton("-");
        decreaseBtn.setMargin(new Insets(3, 6, 3, 6));
        JButton increaseBtn = new JButton("+");
        increaseBtn.setMargin(new Insets(3, 6, 3, 6));

        quantityLabel = new JLabel(String.valueOf(quantity));
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        quantityLabel.setPreferredSize(new Dimension(40, 25));
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);

        double unitPrice = p.getPrice();
        totalPriceLabel = new JLabel(String.format("%.0f VNĐ", unitPrice * quantity), JLabel.CENTER);
        totalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalPriceLabel.setPreferredSize(new Dimension(120, 25));
        totalPriceLabel.setForeground(new Color(0, 102, 204));

        JButton optionButton = new JButton("Xóa");
        optionButton.setMargin(new Insets(3, 6, 3, 6));

        // ➖ Giảm số lượng
        decreaseBtn.addActionListener(e -> {
            if (quantity > 1) {
                quantity--;
                updateQuantity(unitPrice);
                updateTotalCallback.run(); // 👉 gọi lại cập nhật tổng tiền
            }
        });

        // ➕ Tăng số lượng
        increaseBtn.addActionListener(e -> {
            quantity++;
            updateQuantity(unitPrice);
            updateTotalCallback.run();
        });

        // ❌ Xóa sản phẩm
        optionButton.addActionListener(e -> {
            Container parent = this.getParent();
            if (parent != null) {
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
            }
            removeCallback.accept(this);  // Gỡ khỏi danh sách controller
            updateTotalCallback.run();    // Cập nhật tổng tiền
        });
        
        add(nameLabel);
        add(decreaseBtn);
        add(quantityLabel);
        add(increaseBtn);
        add(totalPriceLabel);
        add(optionButton);
    }

    private void updateQuantity(double unitPrice) {
        quantityLabel.setText(String.valueOf(quantity));
        totalPriceLabel.setText(String.format("%.0f VNĐ", unitPrice * quantity));
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
