
package model.nhaphang;
import java.util.Scanner;

public class ChiTietPNH {
    private String maSP;
    private String tenSP;
    private int soLuongNhap;
    private double donGiaNhap;

    public ChiTietPNH() {}

    public ChiTietPNH(String maSP, String tenSP, int soLuongNhap, double donGiaNhap) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongNhap = soLuongNhap;
        this.donGiaNhap = donGiaNhap;
    }

    public void nhap(Scanner sc) {
        System.out.print("Nhập mã SP: ");
        maSP = sc.nextLine();
        System.out.print("Nhập tên SP: ");
        tenSP = sc.nextLine();
        System.out.print("Nhập số lượng: ");
        soLuongNhap = sc.nextInt();
        System.out.print("Nhập đơn giá: ");
        donGiaNhap = sc.nextDouble();
        sc.nextLine(); // clear newline
    }

    public void xuat() {
        System.out.println("Mã SP: " + maSP + " | Tên SP: " + tenSP +
                           " | SL: " + soLuongNhap + " | Đơn giá: " + donGiaNhap +
                           " | Thành tiền: " + tinhThanhTienNhap());
    }

    public double tinhThanhTienNhap() {
        return soLuongNhap * donGiaNhap;
    }

    // Getter Setter
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public int getSoLuongNhap() { return soLuongNhap; }
    public void setSoLuongNhap(int soLuongNhap) { this.soLuongNhap = soLuongNhap; }
    public double getDonGiaNhap() { return donGiaNhap; }
    public void setDonGiaNhap(double donGiaNhap) { this.donGiaNhap = donGiaNhap; }
}
