package main;

import manager.QuanLyCuaHangMayTinh;
import ui.LoginUI;
/**
 *  MENU CHÍNH CỦA HỆ THỐNG
 * ---------------------------
 * - Là điểm khởi động chương trình (main)
 * - Gọi khởi tạo dữ liệu, giao diện đăng nhập
 * - Tự động lưu dữ liệu khi thoát
 */
public class MenuChinh {

    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("    HỆ THỐNG QUẢN LÝ CỬA HÀNG MÁY TÍNH    ");
        System.out.println("=============================================\n");

        try {
            // B1. Đọc dữ liệu
            QuanLyCuaHangMayTinh.khoiTaoDuLieu();

            // B2. Gọi giao diện đăng nhập
            LoginUI.menu();

        } catch (Exception e) {
            System.err.println(" Lỗi khi chạy chương trình: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n Cảm ơn bạn đã sử dụng hệ thống!");
        }
    }
}