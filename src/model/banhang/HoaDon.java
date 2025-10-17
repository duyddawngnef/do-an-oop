package model.banhang;
import java.util.ArrayList;
import java.util.Scanner;

public class HoaDon {
    private String maHD;
    private String ngayLap;
    private String maKH;
    private ArrayList<ChiTietHoaDon> dsChiTiet; // dùng ArrayList thay cho mảng
    // private int soLuongChiTiet; // không cần biến này nữa

    public HoaDon() {
        dsChiTiet = new ArrayList<>();
        // soLuongChiTiet = 0;
    }

    public HoaDon(String maHD, String ngayLap, String maKH) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.maKH = maKH;
        dsChiTiet = new ArrayList<>();
        // soLuongChiTiet = 0;
    }
    public void themChiTiet() {
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.nhap();
        dsChiTiet.add(cthd);
    }

    public int tinhTongTien() {
        int tong = 0;
        for (ChiTietHoaDon cthd : dsChiTiet) {
            tong += cthd.getTHANHTIEN();
        }
        return tong;
    }

    // 🧾 In hóa đơn
    public void inHoaDon() {
        System.out.println("\n========== HÓA ĐƠN BÁN HÀNG ==========");
        System.out.println("Mã hóa đơn: " + maHD);
        System.out.println("Ngày lập: " + ngayLap);
        System.out.println("Mã khách hàng: " + maKH);
        System.out.println("--------------------------------------");
        System.out.printf("%-15s%-12s%-12s%-12s\n", "Mã SP", "Đơn giá", "Số lượng", "Thành tiền");

        for (ChiTietHoaDon cthd : dsChiTiet) {
            cthd.xuat();
        }

        System.out.println(" Tổng tiền: " + tinhTongTien() + " VND");
    }

    // 🧍 Nhập thông tin cơ bản hóa đơn
    public void nhapThongTin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn: ");
        maHD = sc.nextLine();
        System.out.print("Nhập ngày lập: ");
        ngayLap = sc.nextLine();
        System.out.print("Nhập mã khách hàng: ");
        maKH = sc.nextLine();
    }
}