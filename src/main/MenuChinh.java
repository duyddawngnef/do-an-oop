package main;

import java.util.Scanner;

import manager.DanhSachCPU;
import manager.DanhSachLoaiLinhKien;
import manager.DanhSachRAM;
import manager.DanhSachSanPham;
import model.sanpham.Laptop;
import model.sanpham.LinhKien;

public class MenuChinh {
    
    // Khởi tạo các danh sách quản lý
    private static DanhSachCPU dsCpu = new DanhSachCPU();
    private static DanhSachRAM dsRam = new DanhSachRAM();
    private static DanhSachLoaiLinhKien dsLoaiLinhKien = new DanhSachLoaiLinhKien();
    private static DanhSachSanPham dsSanPham = new DanhSachSanPham();
    
    // Tên file để đọc/ghi dữ liệu
    private static final String FILE_CPU = "data\\DanhSachCPU.txt";
    private static final String FILE_RAM = "data\\DanhSachRAM.txt";
    private static final String FILE_LOAI_LK = "data\\DanhSachLoaiLinhKien.txt";
    private static final String FILE_SAN_PHAM = "data\\DanhSachSanPham.txt";
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        // 1. Khởi tạo dữ liệu (đọc từ file)
        docDuLieuKhoiTao();
        
        // 2. Thiết lập danh sách cho các lớp con (Laptop, LinhKien)
        Laptop.setDanhSachCPU(dsCpu);
        Laptop.setDanhSachRAM(dsRam);
        LinhKien.setDanhSachLoaiLinhKien(dsLoaiLinhKien);
        
        // 3. Chạy menu chính
        menuChinh();
        
        // 4. Lưu dữ liệu khi thoát
        ghiDuLieuKhiThoat();
    }
    
    /**
     * Đọc dữ liệu ban đầu từ các file.
     */
    private static void docDuLieuKhoiTao() {
        System.out.println("\n=============================================");
        System.out.println("        ĐỌC DỮ LIỆU KHỞI TẠO");
        System.out.println("=============================================");
        
        System.out.print("Đang đọc file CPU... ");
        dsCpu.read(FILE_CPU);
        System.out.println("Đã tải " + dsCpu.getSL() + " CPU");
        
        System.out.print("Đang đọc file RAM... ");
        dsRam.read(FILE_RAM);
        System.out.println("Đã tải " + dsRam.getSL() + " RAM");
        
        System.out.print("Đang đọc file Loại Linh Kiện... ");
        dsLoaiLinhKien.read(FILE_LOAI_LK);
        System.out.println("Đã tải " + dsLoaiLinhKien.getSL() + " loại");
        
        System.out.print("Đang đọc file Sản Phẩm... ");
        dsSanPham.read(FILE_SAN_PHAM);
        System.out.println("Đã tải " + dsSanPham.getSoLuongSanPham() + " sản phẩm");
        
        System.out.println("=============================================");
    }

    /**
     * Ghi dữ liệu ra file khi chương trình kết thúc.
     */
    private static void ghiDuLieuKhiThoat() {
        System.out.println("\n=============================================");
        System.out.println("        LƯU DỮ LIỆU TRƯỚC KHI THOÁT");
        System.out.println("=============================================");
        
        dsCpu.write(FILE_CPU);
        dsRam.write(FILE_RAM);
        dsLoaiLinhKien.write(FILE_LOAI_LK);
        dsSanPham.write(FILE_SAN_PHAM);
        
        System.out.println("Đã lưu tất cả dữ liệu thành công.");
    }
    
    /**
     * Hiển thị và xử lý Menu Chính.
     */
    public static void menuChinh() {
        int luaChon = -1;
        do {
            System.out.println("\n=============================================");
            System.out.println("            HỆ THỐNG QUẢN LÝ SẢN PHẨM");
            System.out.println("=============================================");
            System.out.println("1. Quản Lý Sản Phẩm (Laptop & Linh Kiện)");
            System.out.println("2. Quản Lý Cấu Hình (CPU, RAM, Loại Linh Kiện)");
            System.out.println("0. Thoát Chương Trình");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    menuQuanLySanPham();
                    break;
                case 2:
                    menuQuanLyCauHinh();
                    break;
                case 0:
                    System.out.println("\nĐang thoát chương trình...");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }

    /**
     * Hiển thị và xử lý Menu Quản Lý Sản Phẩm.
     */
    public static void menuQuanLySanPham() {
        int luaChon = -1;
        do {
            System.out.println("\n=============== QUẢN LÝ SẢN PHẨM ===============");
            System.out.println("1. Xem Danh Sách Sản Phẩm");
            System.out.println("2. Thêm Sản Phẩm Mới");
            System.out.println("3. Xóa Sản Phẩm (theo mã)");
            System.out.println("4. Sửa Thông Tin Sản Phẩm (theo mã)");
            System.out.println("5. Tìm Kiếm Sản Phẩm");
            System.out.println("6. Thống Kê Sản Phẩm");
            System.out.println("0. Quay Lại Menu Chính");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    dsSanPham.in();
                    break;
                case 2:
                    dsSanPham.them();
                    break;
                case 3:
                    if (!dsSanPham.isEmpty()) {
                        dsSanPham.xoa();
                    } else {
                        System.out.println("Danh sách sản phẩm trống!");
                    }
                    break;
                case 4:
                    if (!dsSanPham.isEmpty()) {
                        dsSanPham.sua();
                    } else {
                        System.out.println("Danh sách sản phẩm trống!");
                    }
                    break;
                case 5:
                    menuTimKiemSanPham();
                    break;
                case 6:
                    menuThongKeSanPham();
                    break;
                case 0:
                    System.out.println("Quay lại Menu Chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }
    
    /**
     * Hiển thị và xử lý Menu Tìm Kiếm Sản Phẩm.
     */
    public static void menuTimKiemSanPham() {
        if (dsSanPham.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống! Không thể tìm kiếm.");
            return;
        }
        
        int luaChon = -1;
        do {
            System.out.println("\n=============== TÌM KIẾM SẢN PHẨM ===============");
            System.out.println("1. Tìm kiếm theo Mã Sản Phẩm");
            System.out.println("2. Hiển thị Sản Phẩm theo Loại (Laptop/Linh Kiện)");
            System.out.println("3. Tìm kiếm theo Khoảng Giá");
            System.out.println("4. Tìm kiếm theo Cấu Hình (CPU/RAM - chỉ áp dụng cho Laptop)");
            System.out.println("0. Quay Lại Menu Quản Lý Sản Phẩm");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    dsSanPham.timTheoMa();
                    break;
                case 2:
                    dsSanPham.hienThiTheoLoai();
                    break;
                case 3:
                    dsSanPham.timTheoKhoangGia();
                    break;
                case 4:
                    dsSanPham.timTheoCauHinh();
                    break;
                case 0:
                    System.out.println("Quay lại Menu Quản Lý Sản Phẩm.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }
    
    /**
     * Hiển thị và xử lý Menu Thống Kê Sản Phẩm.
     */
    public static void menuThongKeSanPham() {
        if (dsSanPham.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống! Không có gì để thống kê.");
            return;
        }
        
        int luaChon = -1;
        do {
            System.out.println("\n=============== THỐNG KÊ SẢN PHẨM ===============");
            System.out.println("1. Thống Kê Tổng Giá Trị Tồn Kho");
            System.out.println("2. Thống Kê Chi Tiết Sản Phẩm theo Loại (CPU/RAM/Loại Linh Kiện)");
            System.out.println("0. Quay Lại Menu Quản Lý Sản Phẩm");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    dsSanPham.thongKeTongGiaTriTonKho();
                    break;
                case 2:
                    
                    break;
                case 0:
                    System.out.println("Quay lại Menu Quản Lý Sản Phẩm.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }
    
    /**
     * Hiển thị và xử lý Menu Quản Lý Cấu Hình (CPU, RAM, Loại Linh Kiện).
     */
    public static void menuQuanLyCauHinh() {
        int luaChon = -1;
        do {
            System.out.println("\n============= QUẢN LÝ CẤU HÌNH =============");
            System.out.println("1. Quản Lý CPU");
            System.out.println("2. Quản Lý RAM");
            System.out.println("3. Quản Lý Loại Linh Kiện");
            System.out.println("0. Quay Lại Menu Chính");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    menuQuanLyCPU();
                    break;
                case 2:
                    menuQuanLyRAM();
                    break;
                case 3:
                    menuQuanLyLoaiLinhKien();
                    break;
                case 0:
                    System.out.println("Quay lại Menu Chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }
    
    /**
     * Hiển thị và xử lý Menu Quản Lý CPU.
     */
    public static void menuQuanLyCPU() {
        int luaChon = -1;
        do {
            System.out.println("\n================= QUẢN LÝ CPU =================");
            System.out.println("1. Xem Danh Sách CPU");
            System.out.println("2. Thêm CPU Mới");
            System.out.println("3. Xóa CPU (theo mã)");
            System.out.println("4. Sửa CPU (theo mã)");
            System.out.println("5. Tìm Kiếm CPU (theo mã)");
            System.out.println("6. Tìm Kiếm CPU theo Hãng");
            System.out.println("7. Thống Kê CPU theo Hãng");
            System.out.println("0. Quay Lại Menu Quản Lý Cấu Hình");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    dsCpu.in();
                    break;
                case 2:
                    dsCpu.them();
                    break;
                case 3:
                    if (!dsCpu.isEmpty()) {
                        dsCpu.xoa();
                    } else {
                        System.out.println("Danh sách CPU trống!");
                    }
                    break;
                case 4:
                    if (!dsCpu.isEmpty()) {
                        dsCpu.sua();
                    } else {
                        System.out.println("Danh sách CPU trống!");
                    }
                    break;
                case 5:
                    dsCpu.timTheoMa();
                    break;
                case 6:
                    dsCpu.timTheoHang();
                    break;
                case 7:
                    dsCpu.thongKeTheoHang();
                    break;
                case 0:
                    System.out.println("Quay lại Menu Quản Lý Cấu Hình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }
    
    /**
     * Hiển thị và xử lý Menu Quản Lý RAM.
     */
    public static void menuQuanLyRAM() {
        int luaChon = -1;
        do {
            System.out.println("\n================= QUẢN LÝ RAM =================");
            System.out.println("1. Xem Danh Sách RAM");
            System.out.println("2. Thêm RAM Mới");
            System.out.println("3. Xóa RAM (theo mã)");
            System.out.println("4. Sửa RAM (theo mã)");
            System.out.println("5. Tìm Kiếm RAM (theo mã)");
            System.out.println("6. Thống Kê RAM theo Dung Lượng");
            System.out.println("0. Quay Lại Menu Quản Lý Cấu Hình");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    dsRam.in();
                    break;
                case 2:
                    dsRam.them();
                    break;
                case 3:
                    if (!dsRam.isEmpty()) {
                        dsRam.xoa();
                    } else {
                        System.out.println("Danh sách RAM trống!");
                    }
                    break;
                case 4:
                    if (!dsRam.isEmpty()) {
                        dsRam.sua();
                    } else {
                        System.out.println("Danh sách RAM trống!");
                    }
                    break;
                case 5:
                    dsRam.timTheoMa();
                    break;
                case 6:
                    dsRam.thongKeTheoDungLuong();
                    break;
                case 0:
                    System.out.println("Quay lại Menu Quản Lý Cấu Hình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }

    /**
     * Hiển thị và xử lý Menu Quản Lý Loại Linh Kiện.
     */
    public static void menuQuanLyLoaiLinhKien() {
        int luaChon = -1;
        do {
            System.out.println("\n========== QUẢN LÝ LOẠI LINH KIỆN ===========");
            System.out.println("1. Xem Danh Sách Loại Linh Kiện");
            System.out.println("2. Thêm Loại Linh Kiện Mới");
            System.out.println("3. Xóa Loại Linh Kiện (theo mã)");
            System.out.println("4. Sửa Loại Linh Kiện (theo mã)");
            System.out.println("5. Tìm Kiếm Loại Linh Kiện (theo mã)");
            System.out.println("6. Tìm Kiếm Loại Linh Kiện (theo tên)");
            System.out.println("7. Thống Kê Loại Linh Kiện Phổ Biến");
            System.out.println("0. Quay Lại Menu Quản Lý Cấu Hình");
            System.out.println("---------------------------------------------");
            System.out.print("Mời nhập lựa chọn của bạn: ");
            
            try {
                luaChon = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                continue;
            }
            
            switch (luaChon) {
                case 1:
                    dsLoaiLinhKien.in();
                    break;
                case 2:
                    dsLoaiLinhKien.them();
                    break;
                case 3:
                    if (!dsLoaiLinhKien.isEmpty()) {
                        dsLoaiLinhKien.xoa();
                    } else {
                        System.out.println("Danh sách Loại Linh Kiện trống!");
                    }
                    break;
                case 4:
                    if (!dsLoaiLinhKien.isEmpty()) {
                        dsLoaiLinhKien.sua();
                    } else {
                        System.out.println("Danh sách Loại Linh Kiện trống!");
                    }
                    break;
                case 5:
                    dsLoaiLinhKien.timTheoMa();
                    break;
                case 6:
                    dsLoaiLinhKien.timKiemTheoTen();
                    break;
                case 7:
                    if (!dsSanPham.isEmpty()) {
                        dsLoaiLinhKien.thongKeLoaiPhoBien(dsSanPham);
                    } else {
                        System.out.println("Danh sách sản phẩm trống! Không thể thống kê.");
                    }
                    break;
                case 0:
                    System.out.println("Quay lại Menu Quản Lý Cấu Hình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (luaChon != 0);
    }
}