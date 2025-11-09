package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import model.nhaphang.NhaCungCap;
sss
public class DanhSachNhaCungCap {
    private NhaCungCap[] ds = new NhaCungCap[0];

    public void them() {
        NhaCungCap ncc = new NhaCungCap();
        ncc.nhap();
        int n = ds.length;
        NhaCungCap[] temp = new NhaCungCap[n + 1];
        System.arraycopy(ds, 0, temp, 0, n);
        temp[n] = ncc;
        ds = temp;
        System.out.println("Đã thêm nhà cung cấp!");
    }

    public void xoaTheoMa() {
        if (ds.length == 0) {
            System.out.println("Danh sách rỗng!");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã NCC cần xoá: ");
        String ma = sc.nextLine();
        int index = -1;
        for (int i = 0; i < ds.length; i++) {
            if (ds[i].getMaNCC().equalsIgnoreCase(ma)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Không tìm thấy mã NCC!");
            return;
        }
        NhaCungCap[] temp = new NhaCungCap[ds.length - 1];
        System.arraycopy(ds, 0, temp, 0, index);
        System.arraycopy(ds, index + 1, temp, index, ds.length - index - 1);
        ds = temp;
        System.out.println("Đã xoá nhà cung cấp!");
    }

    public void xuat() {
        if (ds.length == 0) {
            System.out.println("Danh sách rỗng!");
            return;
        }
        System.out.printf("%-10s %-20s %-25s %-15s\n", "MaNCC", "TenNCC", "DiaChi", "SDT");
        for (NhaCungCap ncc : ds)
            ncc.xuat();
    }

    public void ghiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (NhaCungCap ncc : ds) {
                bw.write(ncc.getMaNCC() + ";" +
                        ncc.getTenNCC() + ";" +
                        ncc.getDiaChi() + ";" +
                        ncc.getSdt());
                bw.newLine();
            }
            System.out.println("✅ Đã ghi file " + tenFile);
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    // 🔹 Đọc file dạng CSV (phân tách bằng ;)
    public void docFile(String tenFile) {
        File file = new File(tenFile);
        if (!file.exists()) {
            System.out.println("⚠️ File chưa tồn tại, sẽ tạo mới khi ghi.");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            ds = new NhaCungCap[0];
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty())
                    continue;

                String[] value = line.split(";");
                if (value.length < 4)
                    continue;

                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(value[0].trim());
                ncc.setTenNCC(value[1].trim());
                ncc.setDiaChi(value[2].trim());
                ncc.setSdt(value[3].trim());

                int n = ds.length;
                NhaCungCap[] temp = new NhaCungCap[n + 1];
                System.arraycopy(ds, 0, temp, 0, n);
                temp[n] = ncc;
                ds = temp;
            }
            System.out.println("✅ Đọc file thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }
}