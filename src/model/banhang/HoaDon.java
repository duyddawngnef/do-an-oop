package model.banhang;
import java.util.Scanner;

public class HoaDon {
    private String maHD;
    private String ngayLap;
    private String maKH;
    private ChiTietHoaDon[] dsChiTiet;
    private int soLuongChiTiet; // số lượng sản phẩm trong hóa đơn

    public HoaDon() {
        dsChiTiet = new ChiTietHoaDon[0];
        soLuongChiTiet = 0;
    }

    public HoaDon(String maHD, String ngayLap, String maKH) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.maKH = maKH;
        dsChiTiet = new ChiTietHoaDon[0];
        soLuongChiTiet = 0;
    }
    public void themChiTiet() {
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.nhap();

        // Tăng kích thước mảng thêm 1 phần tử
        ChiTietHoaDon[] temp = new ChiTietHoaDon[soLuongChiTiet + 1];
        for (int i = 0; i < soLuongChiTiet; i++) {
            temp[i] = dsChiTiet[i];
        }
        temp[soLuongChiTiet] = cthd;

        dsChiTiet = temp;
        soLuongChiTiet++;
    }

    public int tinhTongTien() {
        int tong = 0;
        for (int i = 0; i < soLuongChiTiet; i++) {
            tong += dsChiTiet[i].getTHANHTIEN();
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

        for (int i = 0; i < soLuongChiTiet; i++) {
            dsChiTiet[i].xuat();
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