package model.nhaphang;

import java.util.Scanner;

public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String sdt;
    // ===== CONSTRUCTORS =====
    public NhaCungCap() {
    }

    public NhaCungCap(String ma, String ten, String dc, String sdt) {
        this.maNCC = ma;
        this.tenNCC = ten;
        this.diaChi = dc;
        this.sdt = sdt;
    }

    // ===== NHẬP (KHÔNG NHẬP MÃ Ở ĐÂY) =====
    public void nhap() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập tên nhà cung cấp: ");
        tenNCC = sc.nextLine().trim();

        System.out.print("Nhập địa chỉ: ");
        diaChi = sc.nextLine().trim();

        System.out.print("Nhập số điện thoại: ");
        sdt = sc.nextLine().trim();
    }

    // ===== XUẤT =====
    public void xuat() {
        System.out.printf("%-10s %-20s %-25s %-15s\n", maNCC, tenNCC, diaChi, sdt);
    }

    // ===== CHUYỂN ĐỔI DỮ LIỆU FILE =====
    @Override
    public String toString() {
        // Định dạng dữ liệu: MaNCC;TenNCC;DiaChi;Sdt
        return maNCC + ";" + tenNCC + ";" + diaChi + ";" + sdt;
    }

    public static NhaCungCap fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] p = line.split(";");
        if (p.length < 4) {
            return null;
        }

        return new NhaCungCap(
                p[0].trim(),
                p[1].trim(),
                p[2].trim(),
                p[3].trim());
    }

    public String toFileString() {
        return this.toString(); 
    }

    // ===== GETTERS & SETTERS (Giữ nguyên) =====
    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}