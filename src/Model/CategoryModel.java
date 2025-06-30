package Model;

import Object.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    private Model.MyConnection myConn;

    public CategoryModel() {
        myConn = new Model.MyConnection();
    }
    
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");
                list.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE category_id = ?";
        try (Connection conn = myConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    return new Category(id, name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}