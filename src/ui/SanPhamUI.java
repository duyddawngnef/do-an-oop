package ui;

import java.util.Scanner;

public class SanPhamUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int luaChon;

        do {
            System.out.println("\n||======================================||");
            System.out.println("||       MENU QUẢN LÝ SẢN PHẨM        ||");
            System.out.println("||======================================||");
            System.out.println("|| 1. Thêm sản phẩm mới               ||");
            System.out.println("|| 2. Sửa thông tin sản phẩm          ||");
            System.out.println("|| 3. Xóa sản phẩm                    ||");
            System.out.println("|| 4. Tìm kiếm sản phẩm               ||");
            System.out.println("|| 5. Hiển thị tất cả sản phẩm        ||");
            System.out.println("|| 6. Hiển thị theo loại (Laptop/Linh kiện) ||");
            System.out.println("|| 0. Thoát chương trình              ||");
            System.out.println("||======================================||");
            System.out.print("Mời bạn nhập lựa chọn: ");

            // Xử lý nhập liệu, tránh lỗi nếu người dùng nhập chữ
            try {
                luaChon = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                luaChon = -1; // Gán một giá trị không hợp lệ để lặp lại
                continue;
            }

            switch (luaChon) {
                case 1:
                    // Gọi hàm thêm sản phẩm
                    System.out.println("Chức năng Thêm sản phẩm");
                    break;
                case 2:
                    // Gọi hàm sửa sản phẩm
                    System.out.println("Chức năng Sửa sản phẩm");
                    break;
                case 3:
                    // Gọi hàm xóa sản phẩm
                    System.out.println("Chức năng Xóa sản phẩm");
                    break;
                case 4:
                    // Gọi hàm tìm kiếm sản phẩm
                    System.out.println("Chức năng Tìm kiếm sản phẩm");
                    break;
                case 5:
                    // Gọi hàm hiển thị tất cả
                    System.out.println("Chức năng Hiển thị tất cả");
                    break;
                case 6:
                    // Gọi hàm hiển thị theo loại
                    System.out.println("Chức năng Hiển thị theo loại");
                    break;
                case 0:
                    System.out.println("Đang thoát chương trình...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại.");
            }
        } while (luaChon != 0);
        
        // Đóng scanner sau khi không dùng nữa để tránh rò rỉ tài nguyên
        scanner.close(); 
    }
}