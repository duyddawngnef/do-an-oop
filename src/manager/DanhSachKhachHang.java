package manager;

import Interface.isList;
import model.banhang.KhachHang;
import java.io.*;
import java.util.*;

public class DanhSachKhachHang implements isList {
    private KhachHang[] danhSachKhachHang;
    private static final String FILE_PATH = "data/DanhSachKhachHang.txt";

    // Constructor
    public DanhSachKhachHang() {
        this.danhSachKhachHang = new KhachHang[0];
    }

    // -------------------- Các hàm tiện ích --------------------

    // Lấy số lượng khách hàng
    public int getSoLuong() {
        return danhSachKhachHang.length;
    }

    // Lấy khách hàng theo index
    public KhachHang getKhachHang(int index) {
        if (index >= 0 && index < danhSachKhachHang.length) {
            return danhSachKhachHang[index];
        }
        return null;
    }

    // Tìm vị trí khách hàng trong danh sách theo mã
    public int viTriKhachHangTrongDanhSach(String maKhachHang) {
        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
            return -1;
        }

        for (int i = 0; i < danhSachKhachHang.length; i++) {
            if (danhSachKhachHang[i].getMaKhachHang()
                    .equalsIgnoreCase(maKhachHang.trim())) {
                return i;
            }
        }
        return -1;
    }

    // Tìm khách hàng theo mã
    public KhachHang timMaKhachHang(String maKhachHangCanTim) {
        if (maKhachHangCanTim == null || maKhachHangCanTim.trim().isEmpty()) {
            return null;
        }

        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getMaKhachHang().equalsIgnoreCase(maKhachHangCanTim.trim())) {
                return kh;
            }
        }
        return null;
    }

    // Kiểm tra mã khách hàng có tồn tại không
    public boolean kiemTraMaTonTai(String maKhachHang) {
        return timMaKhachHang(maKhachHang) != null;
    }

    // Tạo mã khách hàng tự động
    public String taoMaKhachHangTuDong() {
        if (danhSachKhachHang.length == 0) {
            return "KH001";
        }

        int maxMa = 0;
        for (KhachHang kh : danhSachKhachHang) {
            String ma = kh.getMaKhachHang();
            if (ma.startsWith("KH")) {
                try {
                    int soMa = Integer.parseInt(ma.substring(2));
                    if (soMa > maxMa) {
                        maxMa = soMa;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu format không đúng
                }
            }
        }
        return String.format("KH%03d", maxMa + 1);
    }

    // Thêm một khách hàng vào mảng
    private void themVaoMang(KhachHang kh) {
        danhSachKhachHang = Arrays.copyOf(danhSachKhachHang, danhSachKhachHang.length + 1);
        danhSachKhachHang[danhSachKhachHang.length - 1] = kh;
    }

    // Xóa khách hàng tại vị trí
    private void xoaTaiViTri(int viTri) {
        if (viTri < 0 || viTri >= danhSachKhachHang.length) {
            return;
        }

        KhachHang[] mangMoi = new KhachHang[danhSachKhachHang.length - 1];
        for (int i = 0, j = 0; i < danhSachKhachHang.length; i++) {
            if (i != viTri) {
                mangMoi[j++] = danhSachKhachHang[i];
            }
        }
        danhSachKhachHang = mangMoi;
    }

    // -------------------- Các hàm chính --------------------

    @Override
    public void read(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("⚠ File không tồn tại: " + fileName);
            System.out.println("→ Sẽ tạo file mới khi lưu dữ liệu.");
            return;
        }

        danhSachKhachHang = new KhachHang[0]; // Reset danh sách
        int lineNumber = 0;
        int successCount = 0;
        int errorCount = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) {
                    continue; // Bỏ qua dòng trống
                }

                String[] values = line.split(";");
                if (values.length >= 5) {
                    try {
                        KhachHang kh = new KhachHang();
                        kh.setMaKhachHang(values[0].trim());
                        kh.setHoKhachHang(values[1].trim());
                        kh.setTenKhachHang(values[2].trim());
                        kh.setSdt(values[3].trim());
                        kh.setDiaChi(values[4].trim());

                        // Validate dữ liệu trước khi thêm
                        if (kh.validate()) {
                            themVaoMang(kh);
                            successCount++;
                        } else {
                            errorCount++;
                            System.out.println("⚠ Dòng " + lineNumber + ": Dữ liệu không hợp lệ");
                        }
                    } catch (Exception e) {
                        errorCount++;
                        System.out.println("⚠ Lỗi đọc dòng " + lineNumber + ": " + e.getMessage());
                    }
                } else {
                    errorCount++;
                    System.out.println("⚠ Dòng " + lineNumber + ": Thiếu dữ liệu (cần 5 cột)");
                }
            }

            System.out.println("✓ Đọc file thành công!");
            System.out.println("  - Số khách hàng hợp lệ: " + successCount);
            if (errorCount > 0) {
                System.out.println("  - Số dòng lỗi: " + errorCount);
            }

        } catch (FileNotFoundException e) {
            System.out.println("✗ Lỗi: Không tìm thấy file " + fileName);
        } catch (Exception e) {
            System.out.println("✗ Lỗi đọc file: " + e.getMessage());
        }
    }

    @Override
    public void write(String fileName) {
        if (danhSachKhachHang.length == 0) {
            System.out.println("⚠ Danh sách trống, không có dữ liệu để ghi!");
            return;
        }

        // Tạo thư mục nếu chưa tồn tại
        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (KhachHang kh : danhSachKhachHang) {
                writer.write(kh.toFileString() + "\n");
            }
            System.out.println("✓ Lưu file thành công: " + fileName);
        } catch (IOException e) {
            System.out.println("✗ Lỗi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n========== THÊM KHÁCH HÀNG MỚI ==========");

        // Tùy chọn tạo mã tự động
        System.out.print("Bạn có muốn tạo mã tự động? (Y/N): ");
        String choice = sc.nextLine().trim().toUpperCase();

        KhachHang khachHangMoi = new KhachHang();

        if (choice.equals("Y")) {
            String maTuDong = taoMaKhachHangTuDong();
            khachHangMoi.setMaKhachHang(maTuDong);
            System.out.println("→ Mã khách hàng tự động: " + maTuDong);

            // Nhập các thông tin còn lại
            System.out.print("Nhập họ khách hàng: ");
            String ho;
            do {
                ho = sc.nextLine().trim();
                if (ho.isEmpty()) {
                    System.out.print("Họ không được để trống! Nhập lại: ");
                } else if (!KhachHang.isValidTen(ho)) {
                    System.out.print("Họ chỉ được chứa chữ cái! Nhập lại: ");
                    ho = "";
                }
            } while (ho.isEmpty());
            khachHangMoi.setHoKhachHang(ho);

            System.out.print("Nhập tên khách hàng: ");
            String ten;
            do {
                ten = sc.nextLine().trim();
                if (ten.isEmpty()) {
                    System.out.print("Tên không được để trống! Nhập lại: ");
                } else if (!KhachHang.isValidTen(ten)) {
                    System.out.print("Tên chỉ được chứa chữ cái! Nhập lại: ");
                    ten = "";
                }
            } while (ten.isEmpty());
            khachHangMoi.setTenKhachHang(ten);

            System.out.print("Nhập địa chỉ: ");
            khachHangMoi.setDiaChi(sc.nextLine().trim());

            System.out.print("Nhập số điện thoại (10 số, bắt đầu bằng 0): ");
            String sdt;
            do {
                sdt = sc.nextLine().trim();
                if (!KhachHang.isValidSdt(sdt)) {
                    System.out.print("SDT không hợp lệ! Nhập lại: ");
                }
            } while (!KhachHang.isValidSdt(sdt));
            khachHangMoi.setSdt(sdt);

        } else {
            khachHangMoi.nhap();
        }

        // Kiểm tra trùng mã
        if (kiemTraMaTonTai(khachHangMoi.getMaKhachHang())) {
            System.out.println("✗ Mã khách hàng đã tồn tại!");
            return;
        }

        // Validate trước khi thêm
        if (!khachHangMoi.validate()) {
            System.out.println("✗ Thông tin khách hàng không hợp lệ!");
            return;
        }

        themVaoMang(khachHangMoi);
        write(FILE_PATH);
        System.out.println("✓ Thêm khách hàng thành công!");
        khachHangMoi.xuat();
    }

    @Override
    public void xoa() {
        if (danhSachKhachHang.length == 0) {
            System.out.println("⚠ Danh sách khách hàng trống!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\n========== XÓA KHÁCH HÀNG ==========");
        System.out.print("Nhập mã khách hàng cần xóa: ");
        String maKhachHang = sc.nextLine().trim();

        int viTri = viTriKhachHangTrongDanhSach(maKhachHang);
        if (viTri == -1) {
            System.out.println("✗ Không tìm thấy khách hàng có mã: " + maKhachHang);
            return;
        }

        // Hiển thị thông tin trước khi xóa
        System.out.println("\nThông tin khách hàng sẽ bị xóa:");
        danhSachKhachHang[viTri].xuat();

        // Xác nhận xóa
        System.out.print("\nBạn có chắc chắn muốn xóa? (Y/N): ");
        String confirm = sc.nextLine().trim().toUpperCase();

        if (confirm.equals("Y")) {
            xoaTaiViTri(viTri);
            write(FILE_PATH);
            System.out.println("✓ Xóa khách hàng thành công!");
        } else {
            System.out.println("→ Đã hủy thao tác xóa.");
        }
    }

    @Override
    public void sua() {
        if (danhSachKhachHang.length == 0) {
            System.out.println("⚠ Danh sách khách hàng trống!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\n========== SỬA THÔNG TIN KHÁCH HÀNG ==========");
        System.out.print("Nhập mã khách hàng cần sửa: ");
        String ma = sc.nextLine().trim();

        int viTri = viTriKhachHangTrongDanhSach(ma);
        if (viTri == -1) {
            System.out.println("✗ Không tìm thấy khách hàng có mã: " + ma);
            return;
        }

        KhachHang khCu = danhSachKhachHang[viTri];
        System.out.println("\nThông tin hiện tại:");
        khCu.xuat();

        System.out.println("\n--- Chọn thông tin cần sửa ---");
        System.out.println("1. Sửa toàn bộ thông tin");
        System.out.println("2. Sửa họ");
        System.out.println("3. Sửa tên");
        System.out.println("4. Sửa địa chỉ");
        System.out.println("5. Sửa số điện thoại");
        System.out.println("0. Hủy");
        System.out.print("→ Chọn: ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("✗ Lựa chọn không hợp lệ!");
            return;
        }

        switch (choice) {
            case 1: // Sửa toàn bộ
                System.out.println("\nNhập thông tin mới:");
                KhachHang khMoi = new KhachHang();
                khMoi.nhap();

                // Kiểm tra trùng mã (nếu đổi mã)
                if (!khMoi.getMaKhachHang().equalsIgnoreCase(ma)
                        && kiemTraMaTonTai(khMoi.getMaKhachHang())) {
                    System.out.println("✗ Mã khách hàng mới đã tồn tại!");
                    return;
                }

                if (!khMoi.validate()) {
                    System.out.println("✗ Thông tin không hợp lệ!");
                    return;
                }

                danhSachKhachHang[viTri] = khMoi;
                break;

            case 2: // Sửa họ
                System.out.print("Nhập họ mới: ");
                String hoMoi;
                do {
                    hoMoi = sc.nextLine().trim();
                    if (!KhachHang.isValidTen(hoMoi)) {
                        System.out.print("Họ không hợp lệ! Nhập lại: ");
                        hoMoi = "";
                    }
                } while (hoMoi.isEmpty());
                khCu.setHoKhachHang(hoMoi);
                break;

            case 3: // Sửa tên
                System.out.print("Nhập tên mới: ");
                String tenMoi;
                do {
                    tenMoi = sc.nextLine().trim();
                    if (!KhachHang.isValidTen(tenMoi)) {
                        System.out.print("Tên không hợp lệ! Nhập lại: ");
                        tenMoi = "";
                    }
                } while (tenMoi.isEmpty());
                khCu.setTenKhachHang(tenMoi);
                break;

            case 4: // Sửa địa chỉ
                System.out.print("Nhập địa chỉ mới: ");
                khCu.setDiaChi(sc.nextLine().trim());
                break;

            case 5: // Sửa SDT
                System.out.print("Nhập số điện thoại mới: ");
                String sdtMoi;
                do {
                    sdtMoi = sc.nextLine().trim();
                    if (!KhachHang.isValidSdt(sdtMoi)) {
                        System.out.print("SDT không hợp lệ! Nhập lại: ");
                    }
                } while (!KhachHang.isValidSdt(sdtMoi));
                khCu.setSdt(sdtMoi);
                break;

            case 0:
                System.out.println("→ Đã hủy thao tác sửa.");
                return;

            default:
                System.out.println("✗ Lựa chọn không hợp lệ!");
                return;
        }

        write(FILE_PATH);
        System.out.println("✓ Cập nhật thông tin thành công!");
        System.out.println("\nThông tin sau khi sửa:");
        danhSachKhachHang[viTri].xuat();
    }

    @Override
    public void in() {
        if (danhSachKhachHang.length == 0) {
            System.out.println("⚠ Danh sách khách hàng trống!");
            return;
        }

        System.out.println("\n========== DANH SÁCH KHÁCH HÀNG ==========");
        System.out.println("Tổng số khách hàng: " + danhSachKhachHang.length);
        System.out.println("=".repeat(110));
        System.out.printf("%-10s | %-30s | %-40s | %-12s%n",
                "Mã KH", "Họ và Tên", "Địa chỉ", "SĐT");
        System.out.println("-".repeat(110));

        for (KhachHang kh : danhSachKhachHang) {
            kh.xuatMotDong();
        }
        System.out.println("=".repeat(110));
    }

    @Override
    public void timTheoMa() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n========== TÌM KIẾM KHÁCH HÀNG ==========");
        System.out.print("Nhập mã khách hàng cần tìm: ");
        String ma = sc.nextLine().trim();

        KhachHang kh = timMaKhachHang(ma);
        if (kh != null) {
            System.out.println("\n✓ Tìm thấy khách hàng:");
            kh.xuat();
        } else {
            System.out.println("✗ Không tìm thấy khách hàng có mã: " + ma);
        }
    }

    // -------------------- Các hàm mở rộng --------------------

    // Tìm kiếm theo tên
    public void timTheoTen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n========== TÌM KIẾM THEO TÊN ==========");
        System.out.print("Nhập tên cần tìm: ");
        String tenCanTim = sc.nextLine().trim().toLowerCase();

        KhachHang[] ketQua = new KhachHang[0];
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getTenKhachHang().toLowerCase().contains(tenCanTim) ||
                    kh.getHoKhachHang().toLowerCase().contains(tenCanTim)) {
                ketQua = Arrays.copyOf(ketQua, ketQua.length + 1);
                ketQua[ketQua.length - 1] = kh;
            }
        }

        if (ketQua.length == 0) {
            System.out.println("✗ Không tìm thấy khách hàng nào!");
        } else {
            System.out.println("\n✓ Tìm thấy " + ketQua.length + " khách hàng:");
            System.out.println("=".repeat(110));
            System.out.printf("%-10s | %-30s | %-40s | %-12s%n",
                    "Mã KH", "Họ và Tên", "Địa chỉ", "SĐT");
            System.out.println("-".repeat(110));
            for (KhachHang kh : ketQua) {
                kh.xuatMotDong();
            }
            System.out.println("=".repeat(110));
        }
    }

    // Tìm kiếm theo số điện thoại
    public void timTheoSDT() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n========== TÌM KIẾM THEO SỐ ĐIỆN THOẠI ==========");
        System.out.print("Nhập số điện thoại cần tìm: ");
        String sdtCanTim = sc.nextLine().trim();

        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getSdt().contains(sdtCanTim)) {
                System.out.println("\n✓ Tìm thấy khách hàng:");
                kh.xuat();
                return;
            }
        }
        System.out.println("✗ Không tìm thấy khách hàng nào!");
    }

    // Sắp xếp theo mã
    public void sapXepTheoMa() {
        for (int i = 0; i < danhSachKhachHang.length - 1; i++) {
            for (int j = i + 1; j < danhSachKhachHang.length; j++) {
                if (danhSachKhachHang[i].getMaKhachHang()
                        .compareTo(danhSachKhachHang[j].getMaKhachHang()) > 0) {
                    KhachHang temp = danhSachKhachHang[i];
                    danhSachKhachHang[i] = danhSachKhachHang[j];
                    danhSachKhachHang[j] = temp;
                }
            }
        }
        System.out.println("✓ Đã sắp xếp danh sách theo mã khách hàng!");
        in();
    }

    // Sắp xếp theo tên
    public void sapXepTheoTen() {
        for (int i = 0; i < danhSachKhachHang.length - 1; i++) {
            for (int j = i + 1; j < danhSachKhachHang.length; j++) {
                if (danhSachKhachHang[i].getTenKhachHang()
                        .compareTo(danhSachKhachHang[j].getTenKhachHang()) > 0) {
                    KhachHang temp = danhSachKhachHang[i];
                    danhSachKhachHang[i] = danhSachKhachHang[j];
                    danhSachKhachHang[j] = temp;
                }
            }
        }
        System.out.println("✓ Đã sắp xếp danh sách theo tên!");
        in();
    }

    // Thống kê
    public void thongKe() {
        System.out.println("\n========== THỐNG KÊ ==========");
        System.out.println("Tổng số khách hàng: " + danhSachKhachHang.length);

        if (danhSachKhachHang.length == 0) {
            System.out.println("=".repeat(40));
            return;
        }

        // Đếm số khách hàng theo vùng (phần cuối địa chỉ)
        String[] vung = new String[danhSachKhachHang.length];
        int[] soLuong = new int[danhSachKhachHang.length];
        int soVung = 0;

        for (KhachHang kh : danhSachKhachHang) {
            String diaChi = kh.getDiaChi();
            if (diaChi != null && !diaChi.isEmpty()) {
                String[] parts = diaChi.split(",");
                String vungHienTai = parts[parts.length - 1].trim();

                // Tìm vùng đã tồn tại
                int viTri = -1;
                for (int i = 0; i < soVung; i++) {
                    if (vung[i].equalsIgnoreCase(vungHienTai)) {
                        viTri = i;
                        break;
                    }
                }

                if (viTri == -1) {
                    vung[soVung] = vungHienTai;
                    soLuong[soVung] = 1;
                    soVung++;
                } else {
                    soLuong[viTri]++;
                }
            }
        }

        if (soVung > 0) {
            System.out.println("\nThống kê theo vùng:");
            for (int i = 0; i < soVung; i++) {
                System.out.printf("  - %s: %d khách hàng%n", vung[i], soLuong[i]);
            }
        }
        System.out.println("=".repeat(40));
    }

    // Xóa tất cả
    sss 
    public void xoaTatCa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n⚠ Bạn có chắc chắn muốn xóa TẤT CẢ khách hàng? (YES để xác nhận): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equals("YES")) {
            danhSachKhachHang = new KhachHang[0];
            write(FILE_PATH);
            System.out.println("✓ Đã xóa toàn bộ danh sách khách hàng!");
        } else {
            System.out.println("→ Đã hủy thao tác xóa.");
        }
    }
}