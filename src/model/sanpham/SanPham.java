package model.sanpham; 

import java.util.Scanner; 

public abstract class SanPham {
    private String maSP;
    private String tenSP;
    private int soLuongTon;
    private int donGiaBan;
    Scanner scanner = new Scanner(System.in);
    //bảo vệ bằng protected
    protected  SanPham() {
        maSP = "";
        tenSP = "";
        soLuongTon = 0;
        donGiaBan = 0;
    }

    protected  SanPham(String maSP, String tenSP, int soLuongTon, int donGiaBan) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongTon = soLuongTon;
        this.donGiaBan = donGiaBan;
    }

    protected  SanPham(SanPham sp) {
        this.maSP = sp.maSP;
        this.tenSP = sp.tenSP;
        this.soLuongTon = sp.soLuongTon;
        this.donGiaBan = sp.donGiaBan;
    }

    public void nhap() {
        System.out.print("\nMời Nhập Mã Sản Phẩm :");
        this.maSP = scanner.nextLine();
        System.out.print("\nMời Nhập Tên Sản Phẩm :");
        this.tenSP = scanner.nextLine();
        System.out.print("\nMời Nhập Số Lượng Tồn :");
        this.soLuongTon = scanner.nextInt();
        System.out.print("\nMời Nhập Đơn Giá Bán :");
        this.donGiaBan = scanner.nextInt();
    }

    public void xuat() {
        System.out.printf("%-15s||%-20s||%-15s||%-15s", getMaSP(), getTenSP(), getSoLuongTon(), getDonGiaBan());
    }

    @Override
    public String toString() {
        return "Mã Sản Phẩm : " + getMaSP() + "\n" +
                "Tên Sản Phẩm : " + getTenSP() + "\n" +
                "Số Lượng Tồn : " + getSoLuongTon() + "\n" +
                "Đơn Giá Bán : " + getDonGiaBan() + "\n";
    }
    
    // Các hàm getter và setter
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(int donGiaBan) {
        this.donGiaBan = donGiaBan;
    }
}