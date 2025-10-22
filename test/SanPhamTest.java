package test;

import java.util.Scanner;
import manager.DanhSachSanPham;

public class SanPhamTest {

    public static void main(String[] args) {
        DanhSachSanPham dssp = new DanhSachSanPham();
        String filename = "./data/DanhSachSanPham.txt"; // Đảm bảo thư mục 'data' tồn tại
        
        // 1. Tải dữ liệu khi bắt đầu
        System.out.println("Đang tải dữ liệu từ file: " + filename);
        dssp.read(filename); //

        Scanner scanner = new Scanner(System.in);
        int luaChon = -1;

        do {
            System.out.println("\n--- MENU QUẢN LÝ SẢN PHẨM ---");
            System.out.println("1. In danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm mới");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Sửa thông tin sản phẩm");
            System.out.println("5. Tìm sản phẩm theo Mã");
            System.out.println("6. Hiển thị sản phẩm theo Loại (Laptop/Linh kiện)");
            System.out.println("7. Tìm sản phẩm theo Khoảng giá");
            System.out.println("8. Tìm Laptop theo Cấu hình (CPU/RAM)");
            System.out.println("9. Xem thống kê kho hàng");
            System.out.println("10. Ghi danh sách vào file (Lưu thay đổi)");
            System.out.println("0. Thoát chương trình");
            System.out.print("Nhập lựa chọn của bạn: ");

            try {
                luaChon = Integer.parseInt(scanner.nextLine());

                switch (luaChon) {
                    case 1:
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
                        dssp.in(); //
                        break;
                    case 2:
                        System.out.println("\n--- THÊM SẢN PHẨM ---");
                        dssp.them(); //
                        break;
                    case 3:
                        System.out.println("\n--- XÓA SẢN PHẨM ---");
                        dssp.xoa(); //
                        break;
                    case 4:
                        System.out.println("\n--- SỬA SẢN PHẨM ---");
                        dssp.sua(); //
                        break;
                    case 5:
                        System.out.println("\n--- TÌM THEO MÃ ---");
                        dssp.timTheoMa(); //
                        break;
                    case 6:
                        System.out.println("\n--- HIỂN THỊ THEO LOẠI ---");
                        dssp.hienThiTheoLoai(); //
                        break;
                    case 7:
                        System.out.println("\n--- TÌM THEO KHOẢNG GIÁ ---");
                        dssp.timTheoKhoangGia(); //
                        break;
                    case 8:
                        System.out.println("\n--- TÌM LAPTOP THEO CẤU HÌNH ---");
                        dssp.timTheoCauHinh(); //
                        break;
                    case 9:
                        menuThongKe(dssp, scanner);
                        break;
                    case 10:
                        System.out.println("\n--- LƯU FILE ---");
                        dssp.write(filename); //
                        System.out.println("Đã lưu thay đổi vào file: " + filename);
                        break;
                    case 0:
                        System.out.println("Đang thoát... Cảm ơn bạn đã sử dụng!");
                        break;
                    default:
                        System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn từ 0 đến 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
                luaChon = -1; // Đặt lại để vòng lặp tiếp tục
            }

        } while (luaChon != 0);

        scanner.close();
    }

    public static void menuThongKe(DanhSachSanPham dssp, Scanner scanner) {
        int luaChonTK = -1;
        do {
            System.out.println("\n--- MENU THỐNG KÊ ---");
            System.out.println("1. Thống kê tổng giá trị tồn kho");
            System.out.println("2. Thống kê chi tiết sản phẩm theo loại");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn loại thống kê: ");

            try {
                luaChonTK = Integer.parseInt(scanner.nextLine());
                switch (luaChonTK) {
                    case 1:
                        dssp.thongKeTongGiaTriTonKho(); //
                        break;
                    case 2:
                        dssp.thongKeSanPhamTheoLoai(); //
                        break;
                    case 0:
                        System.out.println("Đang quay lại menu chính...");
                        break;
                    default:
                        System.out.println("Lỗi: Lựa chọn không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
                luaChonTK = -1;
            }
        } while (luaChonTK != 0);
    }
}