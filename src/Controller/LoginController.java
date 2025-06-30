/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author ADMIN
 */

import Object.User;
import Model.UserModel;
import View.Home;
import View.LoginForm;
import View.MainView;
import java.awt.BorderLayout;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Properties;
import javax.swing.JFrame;


public class LoginController {
    private LoginForm view;
    private UserModel model;
    private User loggedInUser;

    public LoginController(LoginForm view) {
        this.view = view;
        this.model = new UserModel();
        loadRememberedLogin();
    }
    
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void login() {
        String employeeId = view.getTxtUsername().getText().trim();
        String password = new String(view.getTxtPassword().getPassword()).trim();

        if (employeeId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User user = model.authenticateUser(employeeId, password);
            if (user != null) {
                loggedInUser = user;

                // ✅ Lấy họ tên từ bảng nhanvien
                String fullName = model.getFullNameByMaNV(user.getMaNV());

                // ✅ Thêm họ tên vào thông báo
                JOptionPane.showMessageDialog(view, 
                    "Đăng nhập thành công!\nXin chào " + fullName, 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);

                if (view.getRememberMeCheckBox().isSelected()) {
                    saveRememberedLogin(employeeId, password);
                } else {
                    new File(REMEMBER_FILE).delete();
                }

                view.dispose();

                // Mở giao diện chính (Home)
                javax.swing.SwingUtilities.invokeLater(() -> {
                    MainView mainView = new MainView(loggedInUser);
                    mainView.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    mainView.setVisible(true);
                });

            } else {
                JOptionPane.showMessageDialog(view, 
                    "Sai tên đăng nhập hoặc mật khẩu!", 
                    "Lỗi đăng nhập", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, 
                "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), 
                "Lỗi hệ thống", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    
    private final String REMEMBER_FILE = "remember.properties";

    private void saveRememberedLogin(String username, String password) {
        try {
            Properties props = new Properties();
            props.setProperty("username", username);
            props.setProperty("password", password);

            FileOutputStream out = new FileOutputStream(REMEMBER_FILE);
            props.store(out, "Remembered Login");
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadRememberedLogin() {
        try {
            File file = new File(REMEMBER_FILE);
            if (!file.exists()) return;

            Properties props = new Properties();
            FileInputStream in = new FileInputStream(file);
            props.load(in);
            in.close();

            String username = props.getProperty("username");
            String password = props.getProperty("password");

            if (username != null) view.getTxtUsername().setText(username);
            if (password != null) view.getTxtPassword().setText(password);
            view.getRememberMeCheckBox().setSelected(true);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
