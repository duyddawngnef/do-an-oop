package model.nhansu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import utils.TienUtil;

public class NhanVien {
    private String maNhanVien;
    private String ho;
    private String ten;
    private LocalDate ngayVaoLam;
    private String luong;
    private String maPhongBan;
    static String msnv = null;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String ho, String ten, LocalDate ngayVaoLam, String luong, String maPhongBan) {
        this.maNhanVien = maNhanVien;
        this.ho = ho;
        this.ten = ten;
        this.ngayVaoLam = ngayVaoLam;
        this.luong = luong;
        this.maPhongBan = maPhongBan;
    }

    public NhanVien(NhanVien nv) {
        this.maNhanVien = nv.maNhanVien;
        this.ho = nv.ho;
        this.ten = nv.ten;
        this.ngayVaoLam = nv.ngayVaoLam;
        this.luong = nv.luong;
        this.maPhongBan = nv.maPhongBan;
    }

    public String getMaNhanVien() {
        return this.maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHo() {
        return this.ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return this.ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public LocalDate getNgayVaoLam() {
        return this.ngayVaoLam;
    }

    public String getMaPhongBan() {
        return this.maPhongBan;
    }

    public void setMaPhongBan(String maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getLuong() {
        return this.luong;
    }

    public void setLuong(String luong) {
        this.luong = luong;
    }

    public static String getMsnv() {
        return msnv;
    }

    public static void setMsnv(String msnv) {
        NhanVien.msnv = msnv;
    }

    public void nhap() {

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print("Nhập mã nhân viên (bắt đầu bằng NV00...): ");
            this.maNhanVien = scanner.nextLine().trim();
            if (this.maNhanVien.matches("^NV0\\d+$")) { // kiểm tra mẫu NV0...
                break;
            } else {
                System.err.println("Mã nhân viên không hợp lệ! Phải có dạng NV0...");
            }
        }
        while (true) {
            System.out.print("Nhập họ: ");
            this.ho = scanner.nextLine().trim();
            if (this.ho.matches("^[^0-9]+$")) { // không có chữ số
                break;
            } else {
                System.err.println("Họ không được chứa số!");
            }
        }

        // 👤 Nhập tên (không được chứa số)
        while (true) {
            System.out.print("Nhập tên: ");
            this.ten = scanner.nextLine().trim();
            if (this.ten.matches("^[^0-9]+$")) {
                break;
            } else {
                System.err.println("Tên không được chứa số!");
            }
        }
        while (true) {
            System.out.print("Nhập ngày vào làm (dd/MM/yyyy): ");
            String ngayNhap = scanner.nextLine().trim();
            try {
                this.ngayVaoLam = LocalDate.parse(ngayNhap, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.err.println("Ngày không hợp lệ, vui lòng nhập lại!");
            }
        }
        while (true) {
            System.out.print("Nhập lương: ");
            String luongStr = scanner.nextLine().trim();

            if (luongStr.matches("\\d{1,3}(\\.\\d{3})*") || luongStr.matches("\\d+")) {
                this.luong = luongStr;
                break;
            } else {
                System.err.println("Lương không hợp lệ! Hãy nhập số.");
            }
        }
        System.out.print("Nhập mã phòng ban: ");
        this.maPhongBan = scanner.nextLine().trim();
    }

    public void xuat() {
        String ngay = (this.ngayVaoLam != null)
                ? this.ngayVaoLam.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "Chưa có";

        String luongStr = TienUtil.dinhDangTien(this.luong);

        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                this.maNhanVien, this.ho, this.ten, ngay, luongStr + " VNĐ", this.maPhongBan);

        msnv = this.maNhanVien;
    }

}