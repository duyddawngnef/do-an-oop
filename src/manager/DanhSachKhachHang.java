package manager;

import java.util.Arrays;
import java.util.Scanner;
import model.banhang.*;
public class DanhSachKhachHang {
    private KhachHang[] dsKhachHang;

    public DanhSachKhachHang() {
        this.dsKhachHang = new KhachHang[0];
    }

    //Them
    public void them() {
        Scanner scanner = new Scanner(System.in);
        KhachHang kh = new KhachHang();
        kh.nhap();

        if (kiemTraMa(kh.getMaKH())) {
            System.out.println("Lỗi: Mã khách hàng đã tồn tại!");
            
            return;
        }

        dsKhachHang = Arrays.copyOf(dsKhachHang, dsKhachHang.length + 1);
        dsKhachHang[dsKhachHang.length - 1] = kh;
        System.out.println("Thêm khách hàng thành công!");
        
    }

    // Sửa
    public void sua() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã khách hàng cần sửa: ");
        String ma = scanner.nextLine().trim();

        KhachHang kh = timTheoMa(ma);
        if (kh == null) {
            System.out.println("Không tìm thấy khách hàng có mã: " + ma);
            
            return;
        }

        System.out.println("Nhập lại thông tin (trừ mã khách hàng):");
        kh.nhap();
        System.out.println("Sửa thông tin thành công!");
        
    }

    // Xóa
    public void xoa() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã khách hàng cần xóa: ");
        String ma = scanner.nextLine().trim();

        for (int i = 0; i < dsKhachHang.length; i++) {
            if (dsKhachHang[i].getMaKH().equalsIgnoreCase(ma)) {
                remove(i);
                System.out.println("Đã xóa khách hàng " + ma);
                
                return;
            }
        }
        System.out.println("Không tìm thấy khách hàng có mã: " + ma);
        
    }

    // Hiển Thị
    public void hienThiTatCa() {
        if (dsKhachHang.length == 0) {
            System.out.println("Chưa có khách hàng nào.");
            return;
        }

        System.out.printf("%-10s || %-10s || %-20s || %-15s || %s\n", "Mã KH", "Họ KH", "Tên KH", "SĐT", "Địa chỉ");
        System.out.println("------------------------------------------------------------");
        for (KhachHang kh : dsKhachHang) {
            kh.xuat();
        }
    }

    // Hàm Phụ
    private boolean kiemTraMa(String ma) {
        for (KhachHang kh : dsKhachHang) {
            if (kh.getMaKH().equalsIgnoreCase(ma)) return true;
        }
        return false;
    }

    private KhachHang timTheoMa(String ma) {
        for (KhachHang kh : dsKhachHang) {
            if (kh.getMaKH().equalsIgnoreCase(ma)) return kh;
        }
        return null;
    }

    private void remove(int index) {
        KhachHang[] newArr = new KhachHang[dsKhachHang.length - 1];
        for (int i = 0, j = 0; i < dsKhachHang.length; i++) {
            if (i != index) newArr[j++] = dsKhachHang[i];
        }
        dsKhachHang = newArr;
    }
}