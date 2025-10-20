package manager;

import java.util.Arrays;
import java.util.Scanner;
import model.banhang.*;

public class DanhSachHoaDon {
    private HoaDon[] dsHoaDon;

    public DanhSachHoaDon() {
        this.dsHoaDon = new HoaDon[0];
    }

    // Thêm
    public void them() {
        Scanner scanner = new Scanner(System.in);
        HoaDon hd = new HoaDon();
        hd.nhap();

        if (kiemTraMa(hd.getMaHD())) {
            System.out.println("Lỗi: Mã hóa đơn đã tồn tại!");
            
            return;
        }

        dsHoaDon = Arrays.copyOf(dsHoaDon, dsHoaDon.length + 1);
        dsHoaDon[dsHoaDon.length - 1] = hd;
        System.out.println("Thêm hóa đơn thành công!");
        
    }

    //Sửa
    public void sua() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần sửa: ");
        String ma = scanner.nextLine().trim();

        HoaDon hd = timTheoMa(ma);
        if (hd == null) {
            System.out.println("Không tìm thấy hóa đơn có mã: " + ma);
            
            return;
        }

        System.out.println("Nhập lại thông tin (trừ mã hóa đơn):");
        hd.nhap();
        System.out.println("Sửa thông tin thành công!");
        
    }

    // Xóa
    public void xoa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        String ma = scanner.nextLine().trim();

        for (int i = 0; i < dsHoaDon.length; i++) {
            if (dsHoaDon[i].getMaHD().equalsIgnoreCase(ma)) {
                remove(i);
                System.out.println("Đã xóa hóa đơn " + ma);
                
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn có mã: " + ma);
        
    }

    // Hiển Thị
    public void hienThiTatCa() {
        if (dsHoaDon.length == 0) {
            System.out.println("Chưa có hóa đơn nào.");
            return;
        }

        System.out.printf("%-10s || %-10s || %-15s || %s\n", "Mã HD", "Mã KH", "Ngày lập", "Tổng tiền");
        System.out.println("------------------------------------------------------");
        for (HoaDon hd : dsHoaDon) {
            hd.xuat();
        }
    }
    // Hàm Phụ
    private boolean kiemTraMa(String ma) {
        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaHD().equalsIgnoreCase(ma)) return true;
        }
        return false;
    }

    private HoaDon timTheoMa(String ma) {
        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaHD().equalsIgnoreCase(ma)) return hd;
        }
        return null;
    }

    private void remove(int index) {
        HoaDon[] newArr = new HoaDon[dsHoaDon.length - 1];
        for (int i = 0, j = 0; i < dsHoaDon.length; i++) {
            if (i != index) newArr[j++] = dsHoaDon[i];
        }
        dsHoaDon = newArr;
    }
}