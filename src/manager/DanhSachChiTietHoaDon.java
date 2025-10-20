package manager;

import java.util.Arrays;
import java.util.Scanner;
import model.banhang.*;
public class DanhSachChiTietHoaDon {
    private ChiTietHoaDon[] dsCTHD;

    public DanhSachChiTietHoaDon() {
        this.dsCTHD = new ChiTietHoaDon[0];
    }

    // Thêm
    public void them() {
        ChiTietHoaDon ct = new ChiTietHoaDon();
        ct.nhap();

        if (kiemTraMa(ct.getMaCTHD())) {
            System.out.println("Lỗi: Mã chi tiết hóa đơn đã tồn tại!");
            return;
        }

        dsCTHD = Arrays.copyOf(dsCTHD, dsCTHD.length + 1);
        dsCTHD[dsCTHD.length - 1] = ct;
        System.out.println("Thêm chi tiết hóa đơn thành công!");
    }

    // Sửa
    public void sua() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã chi tiết cần sửa: ");
        String ma = scanner.nextLine().trim();

        ChiTietHoaDon ct = timTheoMa(ma);
        if (ct == null) {
            System.out.println("Không tìm thấy chi tiết có mã: " + ma);
            
            return;
        }

        System.out.println("Nhập lại thông tin (trừ mã chi tiết):");
        ct.nhap();
        System.out.println("Sửa thông tin thành công!");
        
    }

    // Xóa
    public void xoa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã chi tiết cần xóa: ");
        String ma = scanner.nextLine().trim();

        for (int i = 0; i < dsCTHD.length; i++) {
            if (dsCTHD[i].getMaCTHD().equalsIgnoreCase(ma)) {
                remove(i);
                System.out.println("Đã xóa chi tiết hóa đơn " + ma);
                
                return;
            }
        }
        System.out.println("Không tìm thấy chi tiết có mã: " + ma);
        
    }

    // Hiển Thị
    public void hienThiTatCa() {
        if (dsCTHD.length == 0) {
            System.out.println("Chưa có chi tiết hóa đơn nào.");
            return;
        }

        System.out.printf("%-10s || %-10s || %-10s || %-10s || %-10s || %s\n",
                "Mã CTHD", "Mã HD", "Mã SP", "Số lượng", "Đơn giá", "Thành tiền");
        System.out.println("--------------------------------------------------------------------------");
        for (ChiTietHoaDon ct : dsCTHD) {
            ct.xuat();
        }
    }

    // Hàm Phụ
    private boolean kiemTraMa(String ma) {
        for (ChiTietHoaDon ct : dsCTHD) {
            if (ct.getMaCTHD().equalsIgnoreCase(ma)) return true;
        }
        return false;
    }

    private ChiTietHoaDon timTheoMa(String ma) {
        for (ChiTietHoaDon ct : dsCTHD) {
            if (ct.getMaCTHD().equalsIgnoreCase(ma)) return ct;
        }
        return null;
    }

    private void remove(int index) {
        ChiTietHoaDon[] newArr = new ChiTietHoaDon[dsCTHD.length - 1];
        for (int i = 0, j = 0; i < dsCTHD.length; i++) {
            if (i != index) newArr[j++] = dsCTHD[i];
        }
        dsCTHD = newArr;
    }
}