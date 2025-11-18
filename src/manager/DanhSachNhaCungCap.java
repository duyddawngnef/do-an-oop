package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.nhaphang.*;

public class DanhSachNhaCungCap implements isList {

    private NhaCungCap[] danhSachNCC = new NhaCungCap[0];
    // Giữ FILE_PATH làm đường dẫn mặc định cho các thao tác CRUD nội bộ
    private static final String FILE_PATH = "data\\DanhSachNhaCungCap.txt";
    private static final Scanner sc = new Scanner(System.in);

    // Constructor
    public DanhSachNhaCungCap() {
        this.danhSachNCC = new NhaCungCap[0];
    }

    public NhaCungCap[] getDanhSachNCC() {
        return danhSachNCC;
    }

    public int getSoLuong() {
        return danhSachNCC.length;
    }

    // ===================== HÀM TIỆN ÍCH =====================

    public NhaCungCap timTheoMa(String maNCC) {
        if (maNCC == null || maNCC.trim().isEmpty()) {
            return null;
        }
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getMaNCC().equalsIgnoreCase(maNCC.trim())) {
                return ncc;
            }
        }
        return null;
    }

    public boolean kiemTraMaTonTai(String maNCC) {
        return timTheoMa(maNCC) != null;
    }

    private void themVaoMang(NhaCungCap ncc) {
        danhSachNCC = Arrays.copyOf(danhSachNCC, danhSachNCC.length + 1);
        danhSachNCC[danhSachNCC.length - 1] = ncc;
    }

    // ================== IMPLEMENT isList (ĐỌC/GHI FILE) ==================
    @Override
    public void read(String fileName) {
        String path = (fileName == null || fileName.trim().isEmpty()) ? FILE_PATH : fileName;
        File file = new File(path);

        if (!file.exists()) {
            System.out.println(" File nhà cung cấp không tồn tại: " + path);
            return;
        }

        danhSachNCC = new NhaCungCap[0]; // Reset danh sách
        int successCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                // Giả định dữ liệu file là: MaNCC;TenNCC;DiaChi;Sdt
                if (values.length >= 4) { 
                    try {
                        NhaCungCap ncc = new NhaCungCap();
                        ncc.setMaNCC(values[0].trim());
                        ncc.setTenNCC(values[1].trim());
                        ncc.setDiaChi(values[2].trim());
                        ncc.setSdt(values[3].trim());
                        themVaoMang(ncc);
                        successCount++;
                    } catch (Exception e) {
                        System.err.println(" Lỗi đọc dữ liệu NCC: " + line + " - " + e.getMessage());
                    }
                }
            }
            System.out.println(" Tải dữ liệu nhà cung cấp thành công (" + successCount + " NCC) từ file: " + path);
        } catch (IOException e) {
            System.err.println(" Lỗi khi đọc file nhà cung cấp: " + e.getMessage());
        }
    }

    /**
     * Phương thức ghi file (theo interface isList) - Sử dụng tên file được truyền vào.
     */
    @Override
    public void write(String fileName) {
        String path = (fileName == null || fileName.trim().isEmpty()) ? FILE_PATH : fileName;
        File file = new File(path);
        
        // Tạo thư mục nếu chưa tồn tại
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (NhaCungCap ncc : danhSachNCC) {
                // Giả định NhaCungCap có toFileString()
                bw.write(ncc.toFileString()); 
                bw.newLine();
            }
            System.out.println(" Ghi file nhà cung cấp thành công: " + path);
        } catch (IOException e) {
            System.err.println(" Lỗi khi ghi file nhà cung cấp: " + e.getMessage());
        }
    }

    // ===================== CRUD OPERATIONS =====================

    @Override
    public void them() {
        System.out.println("\n========== THÊM NHÀ CUNG CẤP MỚI ==========");
        NhaCungCap nccMoi = new NhaCungCap();
        
        // Giả định NhaCungCap có phương thức nhap()
        nccMoi.nhap(); 
        
        if (kiemTraMaTonTai(nccMoi.getMaNCC())) {
            System.err.println(" Lỗi: Mã nhà cung cấp đã tồn tại!");
            return;
        }

        themVaoMang(nccMoi);
        // Ghi vào đường dẫn mặc định
        write(FILE_PATH); 
        System.out.println(" Thêm nhà cung cấp thành công!");
    }

    @Override
    public void xoa() {
        if (danhSachNCC.length == 0) {
            System.out.println(" Danh sách nhà cung cấp trống!");
            return;
        }
        
        System.out.print("Nhập mã nhà cung cấp cần xóa: ");
        String ma = sc.nextLine().trim();

        int viTri = -1;
        for (int i = 0; i < danhSachNCC.length; i++) {
            if (danhSachNCC[i].getMaNCC().equalsIgnoreCase(ma)) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.out.println(" Không tìm thấy nhà cung cấp có mã: " + ma);
            return;
        }

        // Xóa khỏi mảng
        for (int i = viTri; i < danhSachNCC.length - 1; i++) {
            danhSachNCC[i] = danhSachNCC[i + 1];
        }
        danhSachNCC = Arrays.copyOf(danhSachNCC, danhSachNCC.length - 1);

        write(FILE_PATH);
        System.out.println(" Đã xóa nhà cung cấp có mã: " + ma);
    }

    @Override
    public void sua() {
        if (danhSachNCC.length == 0) {
            System.out.println(" Danh sách nhà cung cấp trống!");
            return;
        }

        System.out.print("Nhập mã nhà cung cấp cần sửa: ");
        String ma = sc.nextLine().trim();

        NhaCungCap nccCanSua = timTheoMa(ma);

        if (nccCanSua == null) {
            System.out.println(" Không tìm thấy nhà cung cấp có mã: " + ma);
            return;
        }

        System.out.println("\n--- THÔNG TIN HIỆN TẠI ---");
        // Giả định có hàm xuất
        nccCanSua.xuat(); 

        System.out.println("\n--- CHỌN THÔNG TIN CẦN SỬA ---");
        System.out.println("1. Tên nhà cung cấp");
        System.out.println("2. Địa chỉ");
        System.out.println("3. Số điện thoại");
        System.out.println("0. Hủy");
        System.out.print("Lựa chọn: ");
        String chon = sc.nextLine().trim();

        switch (chon) {
            case "1":
                System.out.print("Nhập tên NCC mới: ");
                String tenMoi = sc.nextLine().trim();
                if (!tenMoi.isEmpty()) {
                    nccCanSua.setTenNCC(tenMoi);
                    System.out.println(" Đã cập nhật Tên NCC.");
                } else {
                    System.out.println(" Tên không được để trống!");
                }
                break;
            case "2":
                System.out.print("Nhập địa chỉ mới: ");
                String diaChiMoi = sc.nextLine().trim();
                nccCanSua.setDiaChi(diaChiMoi);
                System.out.println(" Đã cập nhật Địa chỉ.");
                break;
            case "3":
                System.out.print("Nhập SĐT mới: ");
                String sdtMoi = sc.nextLine().trim();
                nccCanSua.setSdt(sdtMoi);
                System.out.println(" Đã cập nhật SĐT.");
                break;
            case "0":
                System.out.println(" Hủy thao tác sửa.");
                return;
            default:
                System.out.println(" Lựa chọn không hợp lệ!");
                return;
        }
        
        write(FILE_PATH);
        System.out.println(" Cập nhật nhà cung cấp hoàn tất!");
    }

    @Override
    public void timTheoMa() {
        System.out.print("Nhập mã nhà cung cấp cần tìm: ");
        String ma = sc.nextLine().trim();
        NhaCungCap ncc = timTheoMa(ma);
        if (ncc != null) {
            System.out.println("\n Tìm thấy nhà cung cấp:");
            ncc.xuat();
        } else {
            System.out.println(" Không tìm thấy nhà cung cấp có mã: " + ma);
        }
    }

    @Override
    public void in() {
        if (danhSachNCC.length == 0) {
            System.out.println(" Danh sách nhà cung cấp trống!");
            return;
        }

        System.out.println("\n========== DANH SÁCH NHÀ CUNG CẤP ==========");
        System.out.printf("%-10s | %-30s | %-40s | %-12s\n", 
            "Mã NCC", "Tên NCC", "Địa chỉ", "SĐT");
        System.out.println("-".repeat(95));
        
        for (NhaCungCap ncc : danhSachNCC) {
            System.out.printf("%-10s | %-30s | %-40s | %-12s\n", 
                ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSdt());
        }
        System.out.println("=".repeat(95));
    }
    
    // ===================== HÀM MỞ RỘNG =====================
    
    public void timTheoTen() {
        System.out.print("Nhập tên (hoặc một phần) NCC cần tìm: ");
        String tenCanTim = sc.nextLine().trim().toLowerCase();
        
        NhaCungCap[] ketQua = new NhaCungCap[0];
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getTenNCC().toLowerCase().contains(tenCanTim)) {
                ketQua = Arrays.copyOf(ketQua, ketQua.length + 1);
                ketQua[ketQua.length - 1] = ncc;
            }
        }

        if (ketQua.length == 0) {
            System.out.println(" Không tìm thấy nhà cung cấp nào có tên liên quan.");
        } else {
            System.out.println("\n Tìm thấy " + ketQua.length + " nhà cung cấp:");
            System.out.printf("%-10s | %-30s | %-40s | %-12s\n", 
                "Mã NCC", "Tên NCC", "Địa chỉ", "SĐT");
            System.out.println("-".repeat(95));
            for (NhaCungCap ncc : ketQua) {
                System.out.printf("%-10s | %-30s | %-40s | %-12s\n", 
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSdt());
            }
            System.out.println("=".repeat(95));
        }
    }
}