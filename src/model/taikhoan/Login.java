package model.taikhoan;

public class Login {
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro; // ADMIN, NHANVIEN, KHACHHANG

    public Login() {
    }

    public Login(String tenDangNhap, String matKhau, String vaiTro) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;

    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setRole(String role) {
        this.vaiTro = role;
    }

    public void xuat() {
        System.out.printf("| %-15s | %-15s |\n", tenDangNhap, vaiTro);
    }
}