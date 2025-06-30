package Model;

import Object.Category;
import Object.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    private Model.MyConnection myConn;

    public ProductModel() {
        myConn = new Model.MyConnection();
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT p.product_id, p.name, p.category_id, p.price, p.description, p.image,
                   c.name AS category_name
            FROM products p
            JOIN category c ON p.category_id = c.category_id
        """;

        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Tạo category
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setName(rs.getString("category_name"));

                // Tạo sản phẩm
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setCategory(c); // set đầy đủ
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public boolean insertProduct(Product p) {
        String sql = "INSERT INTO products(name, category_id, price, description, image) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getCategory().getCategoryId());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getDescription());
            ps.setString(5, p.getImage());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET name=?, category_id=?, price=?, description=?, image=? WHERE product_id=?";
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getCategory().getCategoryId());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getDescription());
            ps.setString(5, p.getImage());
            ps.setInt(6, p.getProductId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE product_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
