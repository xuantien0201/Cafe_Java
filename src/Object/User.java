package Object;

public class User {
    private String employeeId;
    private String password;
    private int maNV; // Khóa ngoại tới bảng nhanvien

    public User(String employeeId, String password, int maNV) {
        this.employeeId = employeeId;
        this.password = password;
        this.maNV = maNV;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }
}
