package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import model.nhansu.NhanVien;
import model.taikhoan.Login;

public class DanhSachLogin {
    public static Login[] danhSachLogin = new Login[0];

    // ================== THÊM TÀI KHOẢN ==================
    public void taoTaiKhoanChoNhanVienMoi() {
        // Nếu danh sách nhân viên rỗng thì không làm gì
        if (DanhSachNhanVien.danhSachNhanVien.length == 0) {
            System.out.println("Chưa có nhân viên nào để tạo tài khoản!");
            return;
        }

        // Lấy nhân viên vừa thêm (cuối danh sách)
        NhanVien nvMoi = DanhSachNhanVien.danhSachNhanVien[DanhSachNhanVien.danhSachNhanVien.length - 1];

        // Tạo tài khoản đăng nhập cho nhân viên đó
        Login lg = new Login();
        lg.setTenDangNhap(nvMoi.getMaNhanVien());

        // Lấy ngày vào làm và chuyển sang định dạng ddMMyyyy
        java.time.LocalDate ngayVaoLam = nvMoi.getNgayVaoLam();
        String matKhau = String.format("%02d%02d%04d",
                ngayVaoLam.getDayOfMonth(),
                ngayVaoLam.getMonthValue(),
                ngayVaoLam.getYear());

        lg.setMatKhau(matKhau);
        lg.setRole("nhanvien");

        // Thêm vào danh sách login
        danhSachLogin = Arrays.copyOf(danhSachLogin, danhSachLogin.length + 1);
        danhSachLogin[danhSachLogin.length - 1] = lg;

        System.out.println("Đã tạo tài khoản cho nhân viên:");
        System.out.println("Tài khoản: " + nvMoi.getMaNhanVien());
        System.out.println("Mật khẩu: " + matKhau);
    }

    // ================== KHỞI TẠO TÀI KHOẢN NHÂN VIÊN ==================
    public void khoiTaoTuDanhSachNhanVien() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        // Duyệt qua danh sách nhân viên
        for (NhanVien nv : DanhSachNhanVien.danhSachNhanVien) {
            if (nv == null || nv.getNgayVaoLam() == null)
                continue;

            String tenDangNhap = nv.getMaNhanVien();
            String matKhau = nv.getNgayVaoLam().format(formatter);

            // Tạo login
            Login l = new Login();
            l.setTenDangNhap(tenDangNhap);
            l.setMatKhau(matKhau);
            l.setRole("nhanvien");

            // Thêm vào danh sách
            danhSachLogin = Arrays.copyOf(danhSachLogin, danhSachLogin.length + 1);
            danhSachLogin[danhSachLogin.length - 1] = l;
        }
    }

    // ================== ĐĂNG NHẬP ==================
    public String dangNhap(String tenDangNhap, String matKhau) {
        // Admin mặc định
        if (tenDangNhap.equalsIgnoreCase("admin") && matKhau.equals("admin")) {
            return "admin";
        }

        // Duyệt danh sách login nhân viên
        for (Login lg : danhSachLogin) {
            if (lg.getTenDangNhap().equalsIgnoreCase(tenDangNhap)
                    && lg.getMatKhau().equals(matKhau)) {
                return "nhanvien";
            }
        }
        return null;
    }

    // ================== IN DANH SÁCH LOGIN ==================
    public void in() {
        if (danhSachLogin.length == 0) {
            System.out.println(" Danh sách tài khoản trống!");
            return;
        }

        System.out.printf("%-15s | %-15s | %-10s\n", "Tài khoản", "Mật khẩu", "Quyền");
        System.out.println("----------------------------------------------");
        for (Login lg : danhSachLogin) {
            System.out.printf("%-15s | %-15s | %-10s\n",
                    lg.getTenDangNhap(), lg.getMatKhau(), lg.getVaiTro());
        }
    }

    // ================== GHI FILE ==================
    public void write() {
        try {
            FileWriter fw = new FileWriter("do-an-oop-main/data/DanhSachLogin.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Login lg : danhSachLogin) {
                if (lg == null)
                    continue;
                bw.write(lg.getTenDangNhap() + ";" + lg.getMatKhau() + ";" + lg.getVaiTro());
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Lỗi ghi file login.txt!");
        }
    }

    // ================== ĐỌC FILE ==================
    public void read() {
        try {
            File file = new File("do-an-oop-main/data/DanhSachLogin.txt");
            if (!file.exists())
                return;

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    Login l = new Login();
                    l.setTenDangNhap(parts[0]);
                    l.setMatKhau(parts[1]);
                    l.setRole(parts[2]);

                    danhSachLogin = Arrays.copyOf(danhSachLogin, danhSachLogin.length + 1);
                    danhSachLogin[danhSachLogin.length - 1] = l;
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(" Lỗi đọc file login.txt!");
        }
    }
}