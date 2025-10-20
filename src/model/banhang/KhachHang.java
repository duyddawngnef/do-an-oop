package model.banhang;
import java.util.Scanner;

public class KhachHang {
    private String maKH;
    private String hoKH;
    private String tenKH;
    private String soDienThoai;
    private String diaChi;

    public KhachHang() {}

    public KhachHang(String maKH,String hoKH, String tenKH, String soDienThoai, String diaChi) {
        this.maKH = maKH;
        this.hoKH= hoKH;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public void nhap() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã khách hàng: ");
        maKH = scanner.nextLine();
        System.out.print("Nhập họ khách hàng: ");
        hoKH = scanner.nextLine();
        System.out.print("Nhập tên khách hàng: ");
        tenKH = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        soDienThoai = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        diaChi = scanner.nextLine();
        
    }

    public void xuat() {
        System.out.printf("%-10s || %-10s || %-20s || %-15s || %s\n", maKH, hoKH, tenKH, soDienThoai, diaChi);
    }

    // Getter & Setter
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public String getHoKH(){return hoKH;}
    public void setHoKH(String hoKH){this.hoKH=hoKH;}
    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}