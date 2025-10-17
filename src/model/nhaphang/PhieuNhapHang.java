package model.nhaphang;
import java.util.Scanner;

public class PhieuNhapHang {
    private String maPNH;
    private String ngayNhap;
    private NhaCungCap nhaCungCap;
    private ChiTietPNH[] dsCT;
    private int soSP;

    public PhieuNhapHang() {}

    public void nhap(Scanner sc) {
        System.out.print("Nhập mã phiếu nhập: ");
        maPNH = sc.nextLine();
        System.out.print("Nhập ngày nhập: ");
        ngayNhap = sc.nextLine();

        System.out.println("\n--- Nhập thông tin nhà cung cấp ---");
        nhaCungCap = new NhaCungCap();
        nhaCungCap.nhap(sc);

        System.out.print("\nNhập số sản phẩm: ");
        soSP = sc.nextInt();
        sc.nextLine();

        dsCT = new ChiTietPNH[soSP];
        for (int i = 0; i < soSP; i++) {
            System.out.println("\n--- Nhập chi tiết sản phẩm " + (i + 1) + " ---");
            dsCT[i] = new ChiTietPNH();
            dsCT[i].nhap(sc);
        }
    }

    public void xuat() {
        System.out.println("\n====== PHIẾU NHẬP HÀNG ======");
        System.out.println("Mã phiếu: " + maPNH);
        System.out.println("Ngày nhập: " + ngayNhap);
        System.out.println("--- Thông tin NCC ---");
        nhaCungCap.xuat();

        System.out.println("\n--- Danh sách sản phẩm ---");
        for (int i = 0; i < soSP; i++) {
            dsCT[i].xuat();
        }
        System.out.println("Tổng tiền nhập: " + tinhTongTienNhap());
    }

    public double tinhTongTienNhap() {
        double tong = 0;
        for (int i = 0; i < soSP; i++) {
            tong += dsCT[i].tinhThanhTienNhap();
        }
        return tong;
    }
}