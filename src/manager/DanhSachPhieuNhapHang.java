package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import model.nhaphang.PhieuNhapHang;
sss
public class DanhSachPhieuNhapHang {
    private PhieuNhapHang[] ds = new PhieuNhapHang[0];

    // ====== THÊM ======
    public void them() {
        PhieuNhapHang p = new PhieuNhapHang();
        p.nhap();
        int n = ds.length;
        PhieuNhapHang[] temp = new PhieuNhapHang[n + 1];
        System.arraycopy(ds, 0, temp, 0, n);
        temp[n] = p;
        ds = temp;
        System.out.println("✅ Đã thêm phiếu nhập!");
    }

    // ====== XÓA THEO MÃ ======
    public void xoaTheoMa() {
        if (ds.length == 0) {
            System.out.println("⚠️ Danh sách rỗng!");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu cần xoá: ");
        String ma = sc.nextLine();
        int index = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getMaPhieu().equalsIgnoreCase(ma)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("❌ Không tìm thấy mã phiếu!");
            return;
        }
        PhieuNhapHang[] temp = new PhieuNhapHang[ds.length - 1];
        System.arraycopy(ds, 0, temp, 0, index);
        System.arraycopy(ds, index + 1, temp, index, ds.length - index - 1);
        ds = temp;
        System.out.println("✅ Đã xoá phiếu nhập!");
    }

    // ====== XUẤT DANH SÁCH ======
    public void xuat() {
        if (ds.length == 0) {
            System.out.println("⚠️ Danh sách rỗng!");
            return;
        }
        System.out.printf("%-10s %-15s %-10s %-10s\n", "MaPhieu", "NgayNhap", "MaNCC", "TongTien");
        for (PhieuNhapHang p : ds)
            p.xuat();
    }

    // ====== GHI FILE ======
    public void ghiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (PhieuNhapHang p : ds) {
                bw.write(p.getMaPhieu() + ";" +
                        p.getNgayNhap() + ";" +
                        (p.getNhaCungCap() != null ? p.getNhaCungCap().getMaNCC() : "null") + ";" +
                        p.getTongTien());
                bw.newLine();
            }
            System.out.println("✅ Đã ghi file: " + tenFile);
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    // ====== ĐỌC FILE ======
    public void docFile(String tenFile) {
        File file = new File(tenFile);
        if (!file.exists()) {
            System.out.println("⚠️ File chưa tồn tại, sẽ tạo mới khi ghi.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            ds = new PhieuNhapHang[0];
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                PhieuNhapHang p = PhieuNhapHang.fromString(line);
                if (p != null) {
                    int n = ds.length;
                    PhieuNhapHang[] temp = new PhieuNhapHang[n + 1];
                    System.arraycopy(ds, 0, temp, 0, n);
                    temp[n] = p;
                    ds = temp;
                }
            }
            System.out.println("✅ Đọc file thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }
}