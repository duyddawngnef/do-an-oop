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
        // mã sản phẩm không được null 
        do { 
            System.out.print("\nMời Nhập Mã Sản Phẩm :");
            this.maSP = scanner.nextLine();
        } while (this.maSP.isEmpty());
        System.out.print("\nMời Nhập Tên Sản Phẩm :");
        this.tenSP = scanner.nextLine();
        do{
            System.out.print("\nMời Nhập Số Lượng Tồn :");
            try{
                this.soLuongTon = Integer.parseInt(scanner.nextLine());
                if(this.soLuongTon < 0){
                    System.out.println(" Số lượng tồn phải lớn hơn hoặc bằng 0 !!");
                }
            }   
            catch (NumberFormatException e ){
                System.out.println("Lỗi :Vui nhập số nguyên hợp lệ !!");
                this.soLuongTon = -1;
            }
        }while (this.soLuongTon < 0 );
        do{
            System.out.print("\nMời Nhập Đơn Giá Bán :");
            try {
                this.donGiaBan = Integer.parseInt(scanner.nextLine());
                if(this.donGiaBan <= 0){
                    System.out.println("Đơn giá phải lớn hơn hoặc bằng 0 !!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi :Vui nhập số nguyên hợp lệ !!");
                this.donGiaBan = -1;
            }
        }while (this.donGiaBan <= 0);
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