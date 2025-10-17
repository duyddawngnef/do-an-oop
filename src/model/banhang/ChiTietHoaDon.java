package model.banhang;

import java.util.Scanner;
public class ChiTietHoaDon {
    private String MaSP;
    private String TenSP;
    private int SoLuongBan;
    private int DonGia;
    private int ThanhTien;

    public ChiTietHoaDon() {

    }

    public ChiTietHoaDon(String MaSP, String TenSP, int SoLuongBan, int DonGia, Double ThanhTien) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.SoLuongBan = SoLuongBan;
        this.DonGia = DonGia;
        this.ThanhTien = SoLuongBan * DonGia;
    }

    public ChiTietHoaDon(ChiTietHoaDon HD) {
        this.MaSP = HD.MaSP;
        this.TenSP = HD.TenSP;
        this.SoLuongBan = HD.SoLuongBan;
        this.DonGia = HD.DonGia;
        this.ThanhTien = this.SoLuongBan * this.DonGia;
    }

    public int getDONGIA() {
        return this.DonGia;
    }

    public String getTENSP() {
        return this.TenSP;
    }

    public String getMASP() {
        return this.MaSP;
    }

    public int getSOLUONGBAN() {
        return this.SoLuongBan;
    }

    public void setDONGIA(int donGia) {
        this.DonGia = donGia;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public void setSoLuongBan(int SoLuongBan) {
        this.SoLuongBan = SoLuongBan;
    }

    public int getTHANHTIEN() {
        this.ThanhTien = this.SoLuongBan * this.DonGia;
        return this.ThanhTien;
    }
    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã sản phẩm: ");
        this.MaSP = sc.nextLine();
        System.out.print("Nhập tên sản phẩm: ");
        this.TenSP = sc.nextLine();
        System.out.print("Nhập đơn giá: ");
        this.DonGia = sc.nextInt();
        System.out.print("Nhập số lượng bán: ");
        this.SoLuongBan = sc.nextInt();
        sc.nextLine(); // clear buffer
        this.ThanhTien = this.SoLuongBan * this.DonGia;
    }

    public void xuat() {
        System.out.printf("%-15s%-15s%-12d%-12d%-12d\n",
                this.MaSP, this.TenSP, this.DonGia, this.SoLuongBan, this.getTHANHTIEN());
    }
}
