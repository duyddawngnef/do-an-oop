package model.nhaphang;

import java.util.Scanner;

public class PhieuNhapHang {
    private String maPhieu;
    private String ngayNhap;
    private String maNCC;
    private String maNhanVien;
    private double tongTien;

    public PhieuNhapHang() {
    }

    public PhieuNhapHang(String maPhieu, String ngayNhap, String maNCC, String maNV, double tongTien) {
        this.maPhieu = maPhieu;
        this.ngayNhap = ngayNhap;
        this.maNCC = maNCC;
        this.maNhanVien = maNV;
        this.tongTien = tongTien;
    }

    // ===== NHẬP =====
    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập: ");
        this.maPhieu = sc.nextLine().trim();
        System.out.print("Nhập ngày nhập (dd/MM/yyyy): ");
        this.ngayNhap = sc.nextLine().trim();
        System.out.print("Nhập mã nhà cung cấp: ");
        this.maNCC = sc.nextLine().trim();
        System.out.print("Nhập mã nhân viên: ");
        this.maNhanVien = sc.nextLine().trim();
        this.tongTien = 0;
    }

    // ===== XUẤT =====
    public void xuat() {
        System.out.printf("%-10s | %-12s | %-10s | %-10s | %-15.0f VND\n",
                maPhieu, ngayNhap, maNCC, maNhanVien, tongTien);
    }

    // ===== CHUYỂN ĐỔI FILE =====
    @Override
    public String toString() {
        return maPhieu + ";" + ngayNhap + ";" + maNCC + ";" + maNhanVien + ";" + tongTien;
    }

    public static PhieuNhapHang fromString(String line) {
        String[] p = line.split(";");
        if (p.length >= 5) {
            try {
                return new PhieuNhapHang(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(),
                        Double.parseDouble(p[4].trim()));
            } catch (NumberFormatException e) {
                return new PhieuNhapHang(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), 0);
            }
        }
        return null;
    }

    // ===== GETTER & SETTER =====
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}