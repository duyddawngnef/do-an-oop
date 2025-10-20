package model.banhang;
import java.util.Scanner;
public class ChiTietHoaDon {
    private String maCTHD;
    private String maHD;
    private String maSP;
    private int soLuong;
    private double donGia;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(String maCTHD, String maHD, String maSP, int soLuong, double donGia) {
        this.maCTHD = maCTHD;
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public void nhap() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã chi tiết hóa đơn: ");
        maCTHD = scanner.nextLine();
        System.out.print("Nhập mã hóa đơn: ");
        maHD = scanner.nextLine();
        System.out.print("Nhập mã sản phẩm: ");
        maSP = scanner.nextLine();
        System.out.print("Nhập số lượng: ");
        soLuong = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập đơn giá: ");
        donGia = Double.parseDouble(scanner.nextLine());
        
    }

    public void xuat() {
        System.out.printf("%-10s || %-10s || %-10s || %-10d || %-10.2f || %-10.2f\n",
                maCTHD, maHD, maSP, soLuong, donGia, tinhThanhTien());
    }

    public double tinhThanhTien() {
        return soLuong * donGia;
    }

    // Getter & Setter
    public String getMaCTHD() { return maCTHD; }
    public void setMaCTHD(String maCTHD) { this.maCTHD = maCTHD; }
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public static final Scanner scanner = new Scanner(System.in);
}