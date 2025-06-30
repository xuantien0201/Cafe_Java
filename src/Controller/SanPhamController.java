package Controller;

import Object.Product;
import Object.Category;
import View.SanPhamView;
import Model.ProductModel;
import Model.CategoryModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SanPhamController {
    private ProductModel productModel;
    private CategoryModel categoryModel;

    public SanPhamController() {
        productModel = new ProductModel();
        categoryModel = new CategoryModel();
    }

    public List<Product> layTatCaSanPham() {
        return productModel.getAllProducts();
    }

    public void loadCategoryToComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Tất cả");
        for (Category c : categoryModel.getAllCategories()) {
            comboBox.addItem(c.getCategoryId() + " - " + c.getName());
        }
    }

    public void themSanPham(SanPhamView view) {
        try {
            String ten = view.getTxtTen().getText().trim();
            String loaiStr = (String) view.getCbLoaiInput().getSelectedItem();
            double gia = Double.parseDouble(view.getTxtGia().getText().trim());
            String moTa = view.getTxtMoTa().getText().trim();
            String hinhAnh = view.getTxtHinh().getText().trim();

            if (ten.isEmpty() || loaiStr == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            int loaiId = Integer.parseInt(loaiStr.split(" - ")[0]);
            Category category = categoryModel.getCategoryById(loaiId);
            Product sp = new Product(0, ten, category, gia, moTa, hinhAnh);
            if (productModel.insertProduct(sp)) {
                JOptionPane.showMessageDialog(view, "Thêm sản phẩm thành công!");
                view.loadData();
                view.clearForm();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi thêm sản phẩm: " + ex.getMessage());
        }
    }

    public void suaSanPham(SanPhamView view) {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Chọn sản phẩm để sửa!");
            return;
        }

        try {
            DefaultTableModel tm = view.getTableModel();
            int id = (int) tm.getValueAt(row, 0);
            String ten = view.getTxtTen().getText().trim();
            String loaiStr = (String) view.getCbLoaiInput().getSelectedItem();
            double gia = Double.parseDouble(view.getTxtGia().getText().trim());
            String moTa = view.getTxtMoTa().getText().trim();
            String hinh = view.getTxtHinh().getText().trim();

            int loaiId = Integer.parseInt(loaiStr.split(" - ")[0]);
            Category category = categoryModel.getCategoryById(loaiId);
            Product sp = new Product(id, ten, category, gia, moTa, hinh);
            if (productModel.updateProduct(sp)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                view.loadData();
                view.clearForm();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi sửa sản phẩm: " + ex.getMessage());
        }
    }

    public void xoaSanPham(SanPhamView view) {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Chọn sản phẩm cần xóa!");
            return;
        }

        int id = (int) view.getTable().getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (productModel.deleteProduct(id)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                view.loadData();
                view.clearForm();
            }
        }
    }

    public void locSanPham(SanPhamView view) {
        String keyword = view.getTxtTimKiem().getText().trim().toLowerCase();
        String selectedLoai = (String) view.getCbLoai().getSelectedItem();

        List<Product> ds = productModel.getAllProducts();
        DefaultTableModel tm = view.getTableModel();
        tm.setRowCount(0);

        for (Product p : ds) {
            boolean matchTen = p.getName().toLowerCase().contains(keyword);
            boolean matchLoai = selectedLoai.equals("Tất cả") || selectedLoai.startsWith(String.valueOf(p.getCategory().getCategoryId()));
            if (matchTen && matchLoai) {
                tm.addRow(new Object[]{
                    p.getProductId(),
                    p.getName(),
                    p.getCategory().getCategoryId(),
                    p.getPrice(),
                    p.getDescription(),
                    p.getImage()
                });
            }
        }
    }
}
