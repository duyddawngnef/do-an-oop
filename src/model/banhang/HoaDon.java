package model.banhang;
import java.util.Scanner;

public class HoaDon {
    private String maHD;
    private String maKH;
    private String ngayLap;
    private double tongTien;

    public HoaDon() {}

    public HoaDon(String maHD, String maKH, String ngayLap, double tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    public void nhap() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn: ");
        maHD = scanner.nextLine();
        System.out.print("Nhập mã khách hàng: ");
        maKH = scanner.nextLine();
        System.out.print("Nhập ngày lập (dd/mm/yyyy): ");
        ngayLap = scanner.nextLine();
        System.out.print("Nhập tổng tiền: ");
        tongTien = Double.parseDouble(scanner.nextLine());
        
    }

    public void xuat() {
        System.out.printf("%-10s || %-10s || %-15s || %.2f\n", maHD, maKH, ngayLap, tongTien);
    }

    // Getter & Setter
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public String getNgayLap() { return ngayLap; }
    public void setNgayLap(String ngayLap) { this.ngayLap = ngayLap; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}