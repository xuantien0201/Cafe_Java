package View;

import javax.swing.*;
import java.awt.*;

public class NhapHang extends JPanel {
    public NhapHang() {
        setLayout(new BorderLayout()); // quan trọng: cho phép child component giãn ra toàn bộ

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("Phiếu nhập", new PhieuNhapView());
        tab.addTab("Nhà cung cấp", new NhaCungCapView());

        add(tab, BorderLayout.CENTER); // tab sẽ chiếm toàn bộ panel
    }
}
