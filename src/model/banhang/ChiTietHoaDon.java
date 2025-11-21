package model.banhang;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException; // Giả sử SanPham nằm trong model.sanpham
import java.util.Scanner;

import utils.TienUtil;

public class ChiTietHoaDon {
    private String maHoaDon;
    private String maSanPham;
    private int donGia; // Đơn giá tại thời điểm lập hóa đơn
    private int soLuongSP;
    private int tongGia;

    // Model thuần: không đọc/ghi danh sách sản phẩm ở đây

    // --- Constructors ---
    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maHoaDon, String maSanPham, int donGia, int soLuongSP) {
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.donGia = donGia;
        this.soLuongSP = soLuongSP;
        this.capNhatTongGia();
    }

    public ChiTietHoaDon(ChiTietHoaDon hd) {
        this.maHoaDon = hd.maHoaDon;
        this.maSanPham = hd.maSanPham;
        this.donGia = hd.donGia;
        this.soLuongSP = hd.soLuongSP;
        this.capNhatTongGia();
    }

    // --- Validation và Cập nhật Giá ---
    public void capNhatTongGia() {
        this.tongGia = this.soLuongSP * this.donGia;
    }

    public boolean validate() {
        if (maSanPham == null || maSanPham.trim().isEmpty()) {
            System.out.println("Lỗi: Mã sản phẩm không được để trống!");
            return false;
        }
        if (donGia <= 0) {
            System.out.println("Lỗi: Đơn giá phải là số dương!");
            return false;
        }
        if (soLuongSP <= 0) {
            System.out.println("Lỗi: Số lượng mua phải là số dương!");
            return false;
        }
        return true;
    }

    // Bỏ toàn bộ logic tồn kho khỏi model

    // --- Phương thức Nhập Dữ liệu ---
    @SuppressWarnings("resource")
    public void nhap() {
        Scanner sc = new Scanner(System.in);
        // 1) Nhập mã sản phẩm (model không kiểm tra tồn tại ở đây)
        do {
            System.out.print("Nhập mã sản phẩm cần mua: ");
            this.maSanPham = sc.nextLine().trim();
            if (this.maSanPham.isEmpty()) {
                System.err.println("Mã sản phẩm không được để trống.");
            }
        } while (this.maSanPham.isEmpty());

        // 2) Nhập đơn giá và số lượng (validation cơ bản dương)
        do {
            System.out.print("Nhập đơn giá: ");
            try {
                this.donGia = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                this.donGia = -1;
            }
            if (this.donGia <= 0) System.err.println("Đơn giá phải > 0.");
        } while (this.donGia <= 0);

        do {
            System.out.print("Nhập số lượng sản phẩm cần mua: ");
            try {
                this.soLuongSP = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                this.soLuongSP = -1;
            }
            if (this.soLuongSP <= 0) System.err.println("Số lượng phải > 0.");
        } while (this.soLuongSP <= 0);

        this.capNhatTongGia();
    }

    // --- Phương thức Xuất Dữ liệu ---
    public static void xuatHeader() {
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("%-15s| %-20s| %-15s| %-12s| %-20s%n",
                "MÃ HĐ", "MÃ SẢN PHẨM", "ĐƠN GIÁ", "SỐ LƯỢNG", "THÀNH TIỀN");
        System.out.println("-------------------------------------------------------------------------------");
    }

    public void xuat() {
        System.out.printf("%-15s| %-20s| %15s| %,12d| %20s%n",
                this.maHoaDon,
                this.maSanPham,
                TienUtil.dinhDangTien(donGia),
                this.soLuongSP,
                TienUtil.dinhDangTien(this.getTongGia()));
    }

    public static void xuatFooter() {
        System.out.println("-------------------------------------------------------------------------------");
    }

    // --- Phương thức Ghi File Chi Tiết Hóa Đơn ---
    public String toFileString() {
        return String.format("%s;%s;%d;%d;%d",
                this.maHoaDon, this.maSanPham, TienUtil.dinhDangTien(donGia), this.soLuongSP, this.getTongGia());
    }

    public void write(String fileName) {
        if (!validate()) {
            System.err.println("Không thể ghi chi tiết hóa đơn do dữ liệu không hợp lệ.");
            return;
        }

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            myWriter.write(this.toFileString());
            myWriter.newLine();
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi chi tiết hóa đơn vào file: " + fileName);
            e.printStackTrace();
        }
    }

    // --- Getters và Setters ---
    public int getDonGia() {
        return this.donGia;
    }

    public String getMaHoaDon() {
        return this.maHoaDon;
    }

    public String getMaSanPham() {
        return this.maSanPham;
    }

    public int getSoLuongSP() {
        return this.soLuongSP;
    }

    public int getTongGia() {
        this.capNhatTongGia(); // Đảm bảo luôn tính toán lại
        return this.tongGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    // Override toString (Cho mục đích debug)
    @Override
    public String toString() {
        return String.format("ChiTietHD[MaSP=%s, DonGia=%s, SL=%s, TongGia=%s]",
                maSanPham, donGia, soLuongSP, tongGia);
    }
}