package ui;

import java.util.Scanner;

import manager.DanhSachChiTietHoaDon;
import manager.DanhSachHoaDon;
import manager.DanhSachKhachHang;
import manager.QuanLyCuaHangMayTinh;

/**
 *  GIAO DIỆN QUẢN LÝ BÁN HÀNG
 * ------------------------------
 * - Dùng dữ liệu chung từ QuanLyCuaHangMayTinh
 * - Quản lý hóa đơn, chi tiết hóa đơn, khách hàng
 * - Có thống kê doanh thu, sản phẩm bán chạy, theo quý/năm
 */
public class BanHangUI {

    //  DỮ LIỆU DÙNG CHUNG TOÀN HỆ THỐNG
    // private static final DanhSachSanPham dssp = QuanLyCuaHangMayTinh.dssp;
    private static final DanhSachKhachHang dskh = QuanLyCuaHangMayTinh.dskh;
    // private static final DanhSachNhanVien dsnv = QuanLyCuaHangMayTinh.dsnv;
    private static final DanhSachHoaDon dshd = QuanLyCuaHangMayTinh.dshd;
    private static final DanhSachChiTietHoaDon dscthd = QuanLyCuaHangMayTinh.dscthd;

    private static final Scanner sc = new Scanner(System.in);

    // ============================================================
    //  MENU CHÍNH
    // ============================================================
    public static void menu() {
        int chon;
        do {
            System.out.println("\n╔══════════════════════════════════════════════╗");
            System.out.println("║              MENU QUẢN LÝ BÁN HÀNG           ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║ 1. Quản lý Hóa đơn                           ║");
            System.out.println("║ 2. Quản lý Chi tiết Hóa đơn                  ║");
            System.out.println("║ 3. Quản lý Khách hàng                        ║");
            System.out.println("║ 4. Thống kê Bán hàng                         ║");
            System.out.println("║ 0. Quay lại                                  ║");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print(" Chọn chức năng: ");
            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> menuHoaDon();
                case 2 -> menuChiTietHoaDon();
                case 3 -> menuKhachHang();
                case 4 -> menuThongKe();
                case 0 -> {
                    System.out.println("  Quay lại menu chính...");
                    return;
                }
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }
        } while (true);
    }

    // ============================================================
    //  MENU HÓA ĐƠN
    // ============================================================
    private static void menuHoaDon() {
        int chon;
        do {
            System.out.println("\n==========  QUẢN LÝ HÓA ĐƠN ==========");
            System.out.println("1. Lập hóa đơn mới");
            System.out.println("2. Sửa thông tin hóa đơn");
            System.out.println("3. Xóa hóa đơn");
            System.out.println("4. Tìm kiếm hóa đơn theo mã");
            System.out.println("5. Hiển thị tất cả hóa đơn");
            System.out.println("0. Quay lại");
            System.out.print(" Chọn: ");
            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> dshd.them();
                case 2 -> dshd.sua();
                case 3 -> dshd.xoa();
                case 4 -> dshd.timTheoMa();
                case 5 -> dshd.in();

                case 0 -> System.out.println("  Quay lại menu Bán hàng...");
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    // ============================================================
    //  MENU CHI TIẾT HÓA ĐƠN
    // ============================================================
    private static void menuChiTietHoaDon() {
        int chon;
        do {
            System.out.println("\n==========  QUẢN LÝ CHI TIẾT HÓA ĐƠN ==========");
            System.out.println("1. Thêm chi tiết hóa đơn");
            System.out.println("2. Sửa chi tiết hóa đơn");
            System.out.println("3. Xóa chi tiết hóa đơn");
            System.out.println("4. Tìm kiếm chi tiết theo mã sản phẩm");

            System.out.println("5. Hiển thị toàn bộ chi tiết hóa đơn");
            System.out.println("0. Quay lại");
            System.out.print(" Chọn: ");
            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> {
                    System.out.print("Nhập mã hóa đơn cần thêm chi tiết: ");
                    String maHD = sc.nextLine().trim();
                    dscthd.them(maHD);
                }
                case 2 -> {
                    System.out.print("Nhập mã sản phẩm cần sửa trong hóa đơn: ");
                    dscthd.suaThongTinTheoMa(sc.nextLine().trim());
                }
                case 3 -> {
                    System.out.print("Nhập mã sản phẩm cần xóa trong hóa đơn: ");
                    dscthd.xoa(sc.nextLine().trim());
                }
                case 4 -> dscthd.timTheoMa();

                case 5 -> dscthd.xuat();
                case 0 -> System.out.println("  Quay lại menu Bán hàng...");
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    // ============================================================
    // 👥 MENU KHÁCH HÀNG
    // ============================================================
    private static void menuKhachHang() {
        int chon;
        do {
            System.out.println("\n========== 👤 QUẢN LÝ KHÁCH HÀNG ==========");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Sửa thông tin khách hàng");
            System.out.println("3. Xóa khách hàng");
            System.out.println("4. Tìm khách hàng theo mã");
            System.out.println("5. Tìm khách hàng theo tên");
            System.out.println("6. Hiển thị danh sách khách hàng");

            System.out.println("9. Thống kê khách hàng mua nhiều nhất");
            System.out.println("0. Quay lại");
            System.out.print(" Chọn: ");
            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> dskh.them();
                case 2 -> dskh.sua();
                case 3 -> dskh.xoa();
                case 4 -> dskh.timTheoMa();
                case 5 -> dskh.timTheoTen();
                case 6 -> dskh.in();

                case 9 -> dskh.thongKe();
                case 0 -> System.out.println("  Quay lại menu Bán hàng...");
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    // ============================================================
    //  MENU THỐNG KÊ
    // ============================================================
    private static void menuThongKe() {
        int chon;
        do {
            System.out.println("\n========== 📈 THỐNG KÊ BÁN HÀNG ==========");
            System.out.println("1. Thống kê doanh thu theo sản phẩm");
            System.out.println("2. Thống kê sản phẩm bán chạy nhất");
            System.out.println("3. Thống kê doanh thu theo quý");
            System.out.println("4. Thống kê doanh thu theo năm");
            System.out.println("0. Quay lại");
            System.out.print(" Chọn: ");
            chon = nhapSoNguyen();

            switch (chon) {
                case 1 -> dshd.thongKeTheoDoanhThu();
                case 2 -> dshd.thongKeTheoSoLuongSanPham();
                case 3 -> dshd.thongKeTheoQuy();
                case 4 -> dshd.thongKeTheoNam();
                case 0 -> System.out.println("  Quay lại menu Bán hàng...");
                default -> System.out.println("️  Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    // ============================================================
    //  NHẬP SỐ NGUYÊN AN TOÀN
    // ============================================================
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
                System.out.print("️  Nhập sai định dạng! Vui lòng nhập lại: ");
            }
        }
    }
}