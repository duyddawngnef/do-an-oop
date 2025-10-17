package model.banhang;
import java.util.Scanner;
public class KhachHang {
    private String MaKH;
    private String HoKH;
    private String TenKH;
    private String Sdt;
    private String DiaChi;

    public KhachHang() {

    }

    public KhachHang(String MAKH, String HOKH, String TENKH, String SDT, String DIACHI) {
        this.MaKH = MAKH;
        this.HoKH = HOKH;
        this.TenKH = TENKH;
        this.Sdt = SDT;
        this.DiaChi = DIACHI;
    }

    public KhachHang(KhachHang KHACHHANG) {
        this.MaKH = KHACHHANG.MaKH;
        this.HoKH = KHACHHANG.HoKH;
        this.TenKH = KHACHHANG.TenKH;
        this.Sdt = KHACHHANG.Sdt;
        this.DiaChi = KHACHHANG.DiaChi;
    }

    public String getMaKH() {
        return this.MaKH;
    }

    public String getHoKH() {
        return this.HoKH;
    }

    public String getTenKH() {
        return this.TenKH;
    }

    public String getSdt() {
        return this.Sdt;
    }

    public String getDiaChi() {
        return this.DiaChi;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public void setHoKH(String HoKH) {
        this.HoKH = HoKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public void setSdt(String Sdt) {
        this.Sdt = Sdt;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public void nhap() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã khách hàng: ");
        this.MaKH = scanner.nextLine();
        System.out.print("Nhập họ khách hàng: ");
        this.HoKH = scanner.nextLine();
        System.out.print("Nhập tên khách hàng: ");
        this.TenKH = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        this.DiaChi = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        this.Sdt = scanner.nextLine();
    }

    public void xuat() {
        System.out.println("==========================================");
        System.out.println("Mã khách hàng: " + this.MaKH);
        System.out.println("Họ khách hàng: " + this.HoKH);
        System.out.println("Tên khách hàng: " + this.TenKH);
        System.out.println("Địa chỉ: " + this.DiaChi);
        System.out.println("Số điện thoại: " + this.Sdt);
    }
}