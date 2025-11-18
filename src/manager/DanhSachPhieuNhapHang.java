package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.nhaphang.PhieuNhapHang;
import model.sanpham.SanPham;

public class DanhSachPhieuNhapHang implements isList {

    private PhieuNhapHang[] danhSachPhieuNhap;
    
    private static final String FILE_PHIEUNHAP = "data\\DanhSachPhieuNhapHang.txt";
    
    // Không giữ tham chiếu danh sách khác tại đây

    // --- Constructor ---
    
    public DanhSachPhieuNhapHang() {
        this.danhSachPhieuNhap = new PhieuNhapHang[0];
    }

    // Không có constructor liên kết

    // --- Getters & Hàm tiện ích ---
    public int getSoLuong() {
        return danhSachPhieuNhap.length;
    }

    public PhieuNhapHang timTheoMaPhieu(String maPhieu) {
        for (PhieuNhapHang pnh : danhSachPhieuNhap) {
            if (pnh.getMaPhieu().equalsIgnoreCase(maPhieu)) {
                return pnh;
            }
        }
        return null;
    }

    public boolean kiemTraMaPhieu(String maPhieu) {
        return timTheoMaPhieu(maPhieu) != null;
    }

    private void themVaoMang(PhieuNhapHang phieuNhap) {
        danhSachPhieuNhap = Arrays.copyOf(danhSachPhieuNhap, danhSachPhieuNhap.length + 1);
        danhSachPhieuNhap[danhSachPhieuNhap.length - 1] = phieuNhap;
    }

    @Override
    public void write(String filename) {
        String tenFileGhi = (filename == null || filename.isEmpty()) ? FILE_PHIEUNHAP : filename;

        if (danhSachPhieuNhap.length == 0) {
            System.out.println(" Danh sách trống, không có dữ liệu để ghi!");
            return;
        }

        File file = new File(tenFileGhi);
        File thuMucCha = file.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }

        try (BufferedWriter boGhi = new BufferedWriter(new FileWriter(tenFileGhi, false))) {
            for (PhieuNhapHang pnh : danhSachPhieuNhap) {
                boGhi.write(pnh.toString());
                boGhi.newLine();
                
                // Ghi chi tiết phiếu nhập vào file riêng (nếu có)
                // Giả sử PhieuNhapHang có phương thức getDanhSachChiTiet()
                // pnh.getDanhSachChiTiet().write(pnh.getMaPhieu());
            }

            System.out.println(" Ghi danh sách phiếu nhập hàng thành công vào file: " + tenFileGhi);
        } catch (IOException e) {
            System.err.println("  Lỗi khi ghi danh sách phiếu nhập: " + e.getMessage());
        }
    }

    @Override
    public void read(String filename) {
        String tenFileDoc = (filename == null || filename.isEmpty()) ? FILE_PHIEUNHAP : filename;
        File file = new File(tenFileDoc);

        if (!file.exists()) {
            System.out.println(" File không tồn tại: " + tenFileDoc);
            return;
        }

        this.danhSachPhieuNhap = new PhieuNhapHang[0];
        int soLuongThanhCong = 0;
        int soLuongLoi = 0;
        int soDong = 0;

        try (Scanner boDoc = new Scanner(file)) {
            while (boDoc.hasNextLine()) {
                soDong++;
                String duLieu = boDoc.nextLine();
                if (duLieu.trim().isEmpty())
                    continue;

                try {
                    PhieuNhapHang phieuNhap = PhieuNhapHang.fromString(duLieu);

                    if (phieuNhap == null) {
                        System.err.println("Dòng " + soDong + ": Dữ liệu không hợp lệ.");
                        soLuongLoi++;
                        continue;
                    }

                    // Đọc chi tiết phiếu nhập từ file riêng
                    // DanhSachChiTietPNH danhSachChiTiet = new DanhSachChiTietPNH(danhSachSanPham);
                    // danhSachChiTiet.read(phieuNhap.getMaPhieu());
                    // phieuNhap.setDanhSachChiTiet(danhSachChiTiet);

                    if (kiemTraMaPhieu(phieuNhap.getMaPhieu())) {
                        System.out.println("Dòng " + soDong + ": Mã phiếu nhập trùng lặp, bỏ qua.");
                        soLuongLoi++;
                    } else {
                        themVaoMang(phieuNhap);
                        soLuongThanhCong++;
                    }
                } catch (Exception e) {
                    System.err.println(" Lỗi phân tích dữ liệu dòng " + soDong + ": " + e.getMessage());
                    soLuongLoi++;
                }
            }
            System.out.println(" Đọc file thành công! Số phiếu nhập hợp lệ: " + soLuongThanhCong
                    + (soLuongLoi > 0 ? " (" + soLuongLoi + " lỗi)" : ""));

        } catch (FileNotFoundException e) {
            System.err.println(" Lỗi: Không tìm thấy file phiếu nhập: " + tenFileDoc);
        }
    }

    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);
        int tiepTuc = 1;

        do {
            System.out.println("\n========== NHẬP PHIẾU NHẬP HÀNG MỚI ==========");
            PhieuNhapHang phieuNhap = new PhieuNhapHang();

            // Nhập thông tin cơ bản
            System.out.print("Nhập mã phiếu nhập: ");
            String maPhieu = sc.nextLine().trim();
            
            if (kiemTraMaPhieu(maPhieu)) {
                System.err.println(" Lỗi: Mã phiếu nhập đã tồn tại!");
                continue;
            }
            phieuNhap.setMaPhieu(maPhieu);

            System.out.print("Nhập ngày nhập (dd/MM/yyyy): ");
            String ngayNhap = sc.nextLine().trim();
            phieuNhap.setNgayNhap(ngayNhap);

            // Nhập mã nhà cung cấp
            System.out.print("Nhập mã nhà cung cấp: ");
            String maNCC = sc.nextLine().trim();
            phieuNhap.setMaNCC(maNCC);

            // Nhập mã nhân viên
            System.out.print("Nhập mã nhân viên: ");
            String maNV = sc.nextLine().trim();
            phieuNhap.setMaNhanVien(maNV);

            // Nhập chi tiết phiếu nhập (tùy chỉnh logic)
            // DanhSachChiTietPNH dsChiTiet = new DanhSachChiTietPNH(danhSachSanPham);
            // dsChiTiet.them(maPhieu);
            // phieuNhap.setTongTien(dsChiTiet.getTongTien());

            phieuNhap.setTongTien(0); // Tạm thời set = 0

            themVaoMang(phieuNhap);
            write(FILE_PHIEUNHAP);
            System.out.println("  Thêm phiếu nhập " + phieuNhap.getMaPhieu() + " thành công.");

            System.out.print("Bạn có tiếp tục nhập phiếu nhập không? (1: tiếp tục / 0: dừng lại): ");
            try {
                tiepTuc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                tiepTuc = 0;
            }
        } while (tiepTuc == 1);
    }

    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập cần xóa: ");
        String maPhieu = sc.nextLine().trim();

        int viTri = -1;
        for (int i = 0; i < danhSachPhieuNhap.length; ++i) {
            if (danhSachPhieuNhap[i].getMaPhieu().equalsIgnoreCase(maPhieu)) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.err.println(" Không tìm thấy phiếu nhập có mã: " + maPhieu);
            return;
        }

        // Xóa phiếu nhập
        System.arraycopy(danhSachPhieuNhap, viTri + 1, danhSachPhieuNhap, viTri, 
                danhSachPhieuNhap.length - 1 - viTri);
        danhSachPhieuNhap = Arrays.copyOf(danhSachPhieuNhap, danhSachPhieuNhap.length - 1);
        
        write(FILE_PHIEUNHAP);
        System.out.println("  Đã xóa phiếu nhập có mã: " + maPhieu);
    }

    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập cần sửa: ");
        String maPhieu = sc.nextLine().trim();

        PhieuNhapHang pnh = timTheoMaPhieu(maPhieu);
        if (pnh == null) {
            System.out.println(" Không tìm thấy phiếu nhập với mã: " + maPhieu);
            return;
        }

        System.out.println("\n--- SỬA PHIẾU NHẬP: " + maPhieu + " ---");
        pnh.xuat();

        System.out.println("1. Sửa thông tin cơ bản (Ngày nhập, Mã NCC, Mã NV)");
        System.out.println("2. Quản lý Chi tiết Phiếu Nhập (Thêm/Sửa/Xóa SP)");
        System.out.print("Chọn: ");
        String luaChon = sc.nextLine().trim();

        if (luaChon.equals("1")) {
            suaThongTinCoBan(pnh, sc);
        } else if (luaChon.equals("2")) {
            System.out.println("Chức năng quản lý chi tiết đang phát triển...");
            // quanLyChiTietPhieuNhap(pnh, sc);
        } else {
            System.out.println(" Hủy thao tác sửa.");
        }

        write(FILE_PHIEUNHAP);
        System.out.println("  Cập nhật phiếu nhập hoàn tất.");
    }

    private void suaThongTinCoBan(PhieuNhapHang phieuNhap, Scanner sc) {
        System.out.print("Nhập ngày nhập mới (Enter để bỏ qua): ");
        String ngayNhapMoi = sc.nextLine().trim();
        if (!ngayNhapMoi.isEmpty()) {
            phieuNhap.setNgayNhap(ngayNhapMoi);
        }

        System.out.print("Nhập mã nhà cung cấp mới (Enter để bỏ qua): ");
        String maNCCMoi = sc.nextLine().trim();
        if (!maNCCMoi.isEmpty()) {
            phieuNhap.setMaNCC(maNCCMoi);
        }

        System.out.print("Nhập mã nhân viên mới (Enter để bỏ qua): ");
        String maNVMoi = sc.nextLine().trim();
        if (!maNVMoi.isEmpty()) {
            phieuNhap.setMaNhanVien(maNVMoi);
        }
        System.out.println("  Đã cập nhật thông tin cơ bản.");
    }

    @Override
    public void in() {
        System.out.println("\n=======================================================");
        System.out.println("            DANH SÁCH PHIẾU NHẬP HÀNG               ");
        System.out.println("Tổng số phiếu nhập: " + danhSachPhieuNhap.length);
        System.out.println("=======================================================");

        if (danhSachPhieuNhap.length == 0) {
            System.out.println(" Danh sách phiếu nhập trống!");
            return;
        }

        System.out.printf("%-10s | %-12s | %-10s | %-10s | %-15s\n", 
                "Mã Phiếu", "Ngày Nhập", "Mã NCC", "Mã NV", "Tổng Tiền");
        System.out.println("-".repeat(70));

        for (PhieuNhapHang pnh : danhSachPhieuNhap) {
            pnh.xuat();
        }
    }

    @Override
    public void timTheoMa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phiếu nhập cần tìm: ");
        String maPhieu = sc.nextLine().trim();

        PhieuNhapHang pnh = this.timTheoMaPhieu(maPhieu);
        if (pnh != null) {
            System.out.println("  Đã tìm thấy phiếu nhập:");
            System.out.printf("%-10s | %-12s | %-10s | %-10s | %-15s\n", 
                    "Mã Phiếu", "Ngày Nhập", "Mã NCC", "Mã NV", "Tổng Tiền");
            System.out.println("-".repeat(70));
            pnh.xuat();
        } else {
            System.out.println(" Không tìm thấy phiếu nhập có mã: " + maPhieu);
        }
    }

    // Các phương thức tra cứu mở rộng
    public PhieuNhapHang[] timTheoMaNCC(String maNCC) {
        PhieuNhapHang[] ketQua = new PhieuNhapHang[0];
        for (PhieuNhapHang pnh : danhSachPhieuNhap) {
            if (pnh.getMaNCC().equalsIgnoreCase(maNCC)) {
                ketQua = Arrays.copyOf(ketQua, ketQua.length + 1);
                ketQua[ketQua.length - 1] = pnh;
            }
        }
        return ketQua;
    }

    public PhieuNhapHang[] timTheoMaNhanVien(String maNV) {
        PhieuNhapHang[] ketQua = new PhieuNhapHang[0];
        for (PhieuNhapHang pnh : danhSachPhieuNhap) {
            if (pnh.getMaNhanVien().equalsIgnoreCase(maNV)) {
                ketQua = Arrays.copyOf(ketQua, ketQua.length + 1);
                ketQua[ketQua.length - 1] = pnh;
            }
        }
        return ketQua;
    }

    public void traCuuThongTin() {
        Scanner sc = new Scanner(System.in);
        int tiepTuc = 1;

        do {
            System.out.println("\n--- MENU TRA CỨU PHIẾU NHẬP ---");
            System.out.println("1. Tra cứu theo Mã Nhà Cung Cấp");
            System.out.println("2. Tra cứu theo Mã Nhân Viên");
            System.out.println("0. Thoát!");
            System.out.print("Lựa chọn: ");

            int luaChon;
            try {
                luaChon = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                luaChon = -1;
            }

            switch (luaChon) {
                case 0:
                    return;
                case 1:
                    System.out.print("Nhập mã nhà cung cấp: ");
                    String maNCC = sc.nextLine().trim();
                    PhieuNhapHang[] pnhNCC = timTheoMaNCC(maNCC);
                    if (pnhNCC.length > 0) {
                        System.out.println("=== KẾT QUẢ TRA CỨU THEO MÃ NCC: " + maNCC + " ===");
                        for (PhieuNhapHang pnh : pnhNCC) {
                            pnh.xuat();
                        }
                    } else {
                        System.out.println("Không tìm thấy phiếu nhập nào cho mã nhà cung cấp này.");
                    }
                    break;
                case 2:
                    System.out.print("Nhập mã nhân viên: ");
                    String maNV = sc.nextLine().trim();
                    PhieuNhapHang[] pnhNV = timTheoMaNhanVien(maNV);
                    if (pnhNV.length > 0) {
                        System.out.println("=== KẾT QUẢ TRA CỨU THEO MÃ NV: " + maNV + " ===");
                        for (PhieuNhapHang pnh : pnhNV) {
                            pnh.xuat();
                        }
                    } else {
                        System.out.println("Không tìm thấy phiếu nhập nào cho mã nhân viên này.");
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

            System.out.print("Bạn có muốn tra cứu tiếp không (1: có / 0: dừng lại!)? ");
            try {
                tiepTuc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                tiepTuc = 0;
            }
        } while (tiepTuc == 1);
    }

    // Thống kê
    public void thongKeTheoNhaCungCap() {
        System.out.println("\n=== THỐNG KÊ THEO NHÀ CUNG CẤP ===");
        
        // Tạo mảng lưu thống kê
        String[] dsMaNCC = new String[0];
        double[] tongTienNCC = new double[0];
        
        for (PhieuNhapHang pnh : danhSachPhieuNhap) {
            String maNCC = pnh.getMaNCC();
            boolean found = false;
            
            for (int i = 0; i < dsMaNCC.length; i++) {
                if (dsMaNCC[i].equalsIgnoreCase(maNCC)) {
                    tongTienNCC[i] += pnh.getTongTien();
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                dsMaNCC = Arrays.copyOf(dsMaNCC, dsMaNCC.length + 1);
                tongTienNCC = Arrays.copyOf(tongTienNCC, tongTienNCC.length + 1);
                dsMaNCC[dsMaNCC.length - 1] = maNCC;
                tongTienNCC[tongTienNCC.length - 1] = pnh.getTongTien();
            }
        }
        
        System.out.printf("%-15s | %-30s | %-20s\n", "Mã NCC", "Tên NCC", "Tổng Tiền Nhập");
        System.out.println("-".repeat(70));
        
        for (int i = 0; i < dsMaNCC.length; i++) {
            System.out.printf("%-15s | %-30s | %,20.0f VND\n", 
                    dsMaNCC[i], "(Tên NCC tra cứu ở mức liên kết)", tongTienNCC[i]);
        }
    }



























    //tinh tong nhap hang cua sp x 
    public int thongkenhap(SanPham x){
        String masp = x.getMaSP();
        int sum = 0;
        for(PhieuNhapHang nh : danhSachPhieuNhap){
            if(nh.getmaSP().equalsIgnoreCase(masp)){
                sum += nh.getTongTien();
            }
        }
        return sum;
    }


    //tinh tong 4 quy theo nam
    // public void thongkethong(int year){
    //     Scanner scanner =  new Scanner(System.in);
    //     int[] tong = new int[4];
    //     for()
    // }
}