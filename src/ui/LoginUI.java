package ui;

import java.util.Scanner;

import manager.DanhSachLogin;
import manager.QuanLyCuaHangMayTinh;

/**
 *  GIAO DIỆN ĐĂNG NHẬP HỆ THỐNG QUẢN LÝ CỬA HÀNG MÁY TÍNH
 * ---------------------------------------------------------
 * - Cho phép đăng nhập bằng ADMIN hoặc NHÂN VIÊN
 * - Đồng bộ với dữ liệu từ QuanLyCuaHangMayTinh
 * - Truy cập các module: Sản phẩm, Bán hàng, Nhập hàng, Nhân viên, Phòng ban
 */
public class LoginUI {
    private static final Scanner sc = new Scanner(System.in);
    private static final DanhSachLogin dsLogin = QuanLyCuaHangMayTinh.dslogin;

    // =====================================
    //  KHỞI ĐỘNG CHƯƠNG TRÌNH
    // =====================================
    public static void menu() {
        int chon;
        do {
            System.out.println("\n╔══════════════════════════════════════════════╗");
            System.out.println("║              CHÀO MỪNG ĐẾN HỆ THỐNG          ║");
            System.out.println("║           QUẢN LÝ CỬA HÀNG MÁY TÍNH          ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ 1. Đăng nhập                                 ║");
            System.out.println("║ 0. Thoát chương trình                        ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print(" Chọn chức năng: ");

            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> dangNhap();
                case 0 -> {
                    System.out.println(" Tạm biệt, hẹn gặp lại!");
                    return;
                }
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }

        } while (true);
    }

    // =====================================
    //  ĐĂNG NHẬP HỆ THỐNG
    // =====================================
    private static void dangNhap() {
        System.out.println("\n===  ĐĂNG NHẬP HỆ THỐNG ===");
        System.out.print("Tên đăng nhập: ");
        String user = sc.nextLine().trim();
        System.out.print("Mật khẩu: ");
        String pass = sc.nextLine().trim();

        // ---- ADMIN ----
        if (user.equals("admin") && pass.equals("admin")) {
            System.out.println("\n Xin chào ADMIN!");
            menuAdmin();
            return;
        }

        // ---- NHÂN VIÊN ----
        String vaiTro = dsLogin.dangNhap(user, pass);
        if (vaiTro == null) {
            System.out.println(" Sai tên đăng nhập hoặc mật khẩu!");
        } else if (vaiTro.equalsIgnoreCase("NHANVIEN")) {
            System.out.println("\n Đăng nhập thành công (Nhân viên)!");
            menuNhanVien();
        } else {
            System.out.println("️  Tài khoản không có quyền truy cập!");
        }
    }

    // =====================================
    //  MENU ADMIN
    // =====================================
    private static void menuAdmin() {
        int chon;
        do {
            System.out.println("\n╔══════════════════════════════════════════════╗");
            System.out.println("║               MENU QUẢN TRỊ ADMIN            ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ 1. Quản lý sản phẩm                          ║");
            System.out.println("║ 2. Quản lý nhân viên                         ║");
            System.out.println("║ 3. Quản lý nhập hàng                         ║");
            System.out.println("║ 4. Quản lý bán hàng                          ║");
            System.out.println("║ 5. Quản lý phòng ban                         ║");
            System.out.println("║ 6. Quản lý tài khoản đăng nhập               ║");
            System.out.println("║ 0. Đăng xuất                                 ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print(" Chọn chức năng: ");

            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> SanPhamUI.menu();
                case 2 -> NhanVienUI.menu();
                case 3 -> NhapHangUI.menu();
                case 4 -> BanHangUI.menu();
                case 5 -> PhongBanUI.menu();
                case 6 -> new DanhSachLogin().in();
                case 0 -> {
                    System.out.println("  Đăng xuất khỏi tài khoản ADMIN...");
                    return;
                }
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }

        } while (true);
    }

    // =====================================
    //  MENU NHÂN VIÊN
    // =====================================
    private static void menuNhanVien() {
        int chon;
        do {
            System.out.println("\n╔══════════════════════════════════════════════╗");
            System.out.println("║               MENU NHÂN VIÊN                 ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ 1. Quản lý sản phẩm                          ║");
            System.out.println("║ 2. Quản lý bán hàng                          ║");
            System.out.println("║ 0. Đăng xuất                                 ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print(" Chọn chức năng: ");

            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> SanPhamUI.menu();
                case 2 -> BanHangUI.menu();
                case 0 -> {
                    System.out.println("  Đăng xuất tài khoản nhân viên...");
                    return;
                }
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }
        } while (true);
    }

    // =====================================
    //  HÀM NHẬP SỐ AN TOÀN
    // =====================================
    private static int nhapSoNguyen() {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("️  Không được để trống! Nhập lại: ");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("️  Nhập sai định dạng! Nhập lại: ");
            }
        }
    }
}