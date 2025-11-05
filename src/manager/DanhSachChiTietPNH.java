package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import model.nhaphang.ChiTietPNH;

public class DanhSachChiTietPNH {
    private ChiTietPNH[] ds = new ChiTietPNH[0];

    // 🔹 Nhập danh sách phiếu và sản phẩm trong từng phiếu
    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng phiếu nhập: ");
        int soPhieu = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < soPhieu; i++) {
            System.out.println("\n=== Phiếu nhập thứ " + (i + 1) + " ===");
            System.out.print("Nhập mã phiếu nhập: ");
            String maPN = sc.nextLine();

            System.out.print("Nhập số lượng sản phẩm trong phiếu này: ");
            int soSP = Integer.parseInt(sc.nextLine());

            for (int j = 0; j < soSP; j++) {
                System.out.println("\n--- Sản phẩm thứ " + (j + 1) + " của phiếu " + maPN + " ---");
                ChiTietPNH ct = new ChiTietPNH();
                ct.setMaPhieuNhap(maPN);
                System.out.print("Nhập mã sản phẩm: ");
                ct.setMaSanPham(sc.nextLine());
                System.out.print("Nhập số lượng: ");
                ct.setSoLuong(Integer.parseInt(sc.nextLine()));
                System.out.print("Nhập đơn giá: ");
                ct.setDonGia(Double.parseDouble(sc.nextLine()));

                int n = ds.length;
                ChiTietPNH[] temp = new ChiTietPNH[n + 1];
                System.arraycopy(ds, 0, temp, 0, n);
                temp[n] = ct;
                ds = temp;
            }
        }
    }

    // 🔹 Xuất danh sách phân loại theo mã phiếu
    public void xuat() {
        if (ds.length == 0) {
            System.out.println("Danh sách chi tiết phiếu nhập rỗng!");
            return;
        }

        // Lấy danh sách mã phiếu duy nhất
        String[] maPhieu = new String[0];
        for (ChiTietPNH ct : ds) {
            boolean exists = false;
            for (String ma : maPhieu) {
                if (ma.equalsIgnoreCase(ct.getMaPhieuNhap())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                String[] temp = new String[maPhieu.length + 1];
                System.arraycopy(maPhieu, 0, temp, 0, maPhieu.length);
                temp[maPhieu.length] = ct.getMaPhieuNhap();
                maPhieu = temp;
            }
        }

        // Xuất theo từng phiếu
        for (String maPN : maPhieu) {
            System.out.println("\n📦 PHIẾU NHẬP: " + maPN);
            System.out.printf("%-10s %-15s %-10s %-10s %-10s\n",
                    "MaPN", "MaSP", "SoLuong", "DonGia", "ThanhTien");
            for (ChiTietPNH ct : ds) {
                if (ct.getMaPhieuNhap().equalsIgnoreCase(maPN))
                    ct.xuat();
            }
        }
    }

    // 🔹 Thêm sản phẩm cho 1 phiếu đã có hoặc mới
    public void themSanPhamChoPhieu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập cần thêm sản phẩm: ");
        String maPN = sc.nextLine();

        System.out.print("Nhập số lượng sản phẩm muốn thêm cho phiếu " + maPN + ": ");
        int soSP = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < soSP; i++) {
            System.out.println("\n--- Sản phẩm thứ " + (i + 1) + " ---");
            ChiTietPNH ct = new ChiTietPNH();
            ct.setMaPhieuNhap(maPN);
            System.out.print("Nhập mã sản phẩm: ");
            ct.setMaSanPham(sc.nextLine());
            System.out.print("Nhập số lượng: ");
            ct.setSoLuong(Integer.parseInt(sc.nextLine()));
            System.out.print("Nhập đơn giá: ");
            ct.setDonGia(Double.parseDouble(sc.nextLine()));

            int n = ds.length;
            ChiTietPNH[] temp = new ChiTietPNH[n + 1];
            System.arraycopy(ds, 0, temp, 0, n);
            temp[n] = ct;
            ds = temp;
        }

        System.out.println("✅ Đã thêm " + soSP + " sản phẩm cho phiếu nhập " + maPN);
    }

    // 🔹 Xóa toàn bộ sản phẩm của 1 phiếu nhập
    public void xoaTheoPhieu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập cần xóa toàn bộ sản phẩm: ");
        String maPN = sc.nextLine();

        int count = 0;
        for (ChiTietPNH ct : ds)
            if (ct.getMaPhieuNhap().equalsIgnoreCase(maPN))
                count++;

        if (count == 0) {
            System.out.println("Không tìm thấy phiếu nhập " + maPN);
            return;
        }

        ChiTietPNH[] temp = new ChiTietPNH[ds.length - count];
        int j = 0;
        for (ChiTietPNH ct : ds) {
            if (!ct.getMaPhieuNhap().equalsIgnoreCase(maPN))
                temp[j++] = ct;
        }
        ds = temp;
        System.out.println("Đã xóa toàn bộ sản phẩm của phiếu nhập " + maPN);
    }

    // 🔹 Xóa sản phẩm cụ thể trong 1 phiếu nhập (có thể 1 hoặc nhiều mã)
    public void xoaSanPhamTrongPhieu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập: ");
        String maPN = sc.nextLine();

        System.out.print("Nhập các mã sản phẩm cần xóa (cách nhau bằng dấu cách): ");
        String[] maSPXoa = sc.nextLine().split("\\s+");

        int count = 0;
        for (ChiTietPNH ct : ds)
            for (String maSP : maSPXoa)
                if (ct.getMaPhieuNhap().equalsIgnoreCase(maPN)
                        && ct.getMaSanPham().equalsIgnoreCase(maSP))
                    count++;

        if (count == 0) {
            System.out.println("Không tìm thấy sản phẩm nào khớp để xóa!");
            return;
        }

        ChiTietPNH[] temp = new ChiTietPNH[ds.length - count];
        int j = 0;
        for (ChiTietPNH ct : ds) {
            boolean xoa = false;
            for (String maSP : maSPXoa) {
                if (ct.getMaPhieuNhap().equalsIgnoreCase(maPN)
                        && ct.getMaSanPham().equalsIgnoreCase(maSP)) {
                    xoa = true;
                    break;
                }
            }
            if (!xoa)
                temp[j++] = ct;
        }
        ds = temp;
        System.out.println("Đã xóa " + count + " sản phẩm trong phiếu " + maPN);
    }

    // 🔹 Xuất tổng tiền theo từng phiếu
    public void xuatTongTienTheoPhieu() {
        if (ds.length == 0) {
            System.out.println("Danh sách chi tiết rỗng!");
            return;
        }

        String[] maPhieu = new String[0];
        for (ChiTietPNH ct : ds) {
            boolean exists = false;
            for (String ma : maPhieu)
                if (ma.equalsIgnoreCase(ct.getMaPhieuNhap()))
                    exists = true;
            if (!exists) {
                String[] temp = new String[maPhieu.length + 1];
                System.arraycopy(maPhieu, 0, temp, 0, maPhieu.length);
                temp[maPhieu.length] = ct.getMaPhieuNhap();
                maPhieu = temp;
            }
        }

        System.out.println("\nTỔNG TIỀN THEO PHIẾU:");
        for (String maPN : maPhieu) {
            double tong = 0;
            for (ChiTietPNH ct : ds)
                if (ct.getMaPhieuNhap().equalsIgnoreCase(maPN))
                    tong += ct.thanhTien();
            System.out.printf(" - %s: %.2f\n", maPN, tong);
        }
    }

    // 🔹 Ghi danh sách ra file
    // 🔹 Ghi danh sách ra file (phân tách bằng ;)
    public void ghiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (ChiTietPNH ct : ds) {
                bw.write(ct.getMaPhieuNhap() + ";" +
                        ct.getMaSanPham() + ";" +
                        ct.getSoLuong() + ";" +
                        ct.getDonGia());
                bw.newLine();
            }
            System.out.println("✅ Đã ghi file " + tenFile);
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    // 🔹 Đọc danh sách từ file (phân tách bằng ;)
    public void docFile(String tenFile) {
        File file = new File(tenFile);
        if (!file.exists()) {
            System.out.println("⚠️ File chưa tồn tại, sẽ tạo mới khi ghi.");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            ds = new ChiTietPNH[0];
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty())
                    continue;

                String[] value = line.split(";");
                if (value.length < 4)
                    continue;

                ChiTietPNH ct = new ChiTietPNH();
                ct.setMaPhieuNhap(value[0].trim());
                ct.setMaSanPham(value[1].trim());
                ct.setSoLuong(Integer.parseInt(value[2].trim()));
                ct.setDonGia(Double.parseDouble(value[3].trim()));

                int n = ds.length;
                ChiTietPNH[] temp = new ChiTietPNH[n + 1];
                System.arraycopy(ds, 0, temp, 0, n);
                temp[n] = ct;
                ds = temp;
            }
            System.out.println("✅ Đọc file thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }

}