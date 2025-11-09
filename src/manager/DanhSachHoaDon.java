package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import Interface.isList;
import model.banhang.ChiTietHoaDon;
import model.banhang.HoaDon;
import model.sanpham.Laptop;
import model.sanpham.LinhKien;
import model.sanpham.SanPham;

public class DanhSachHoaDon implements isList {

    private HoaDon[] danhSachHoaDon;

    private static final String FILE_HOADON = "data\\DanhSachHoaDon.txt";

    // Tham chiếu đến các danh sách khác
    private DanhSachNhanVien dsNhanVien;
    private DanhSachKhachHang dsKhachHang;
    private DanhSachSanPham danhSachSanPham;

    // --- Constructor ---
    public DanhSachHoaDon(DanhSachNhanVien dsNhanVien, DanhSachKhachHang dsKhachHang, DanhSachSanPham danhSachSanPham) {
        this.danhSachHoaDon = new HoaDon[0];
        this.dsNhanVien = dsNhanVien;
        this.dsKhachHang = dsKhachHang;
        this.danhSachSanPham = danhSachSanPham;
    }

    // --- Getters & Hàm tiện ích ---
    public int getSoLuong() {
        return danhSachHoaDon.length;
    }

    public HoaDon timTheoMaHoaDon(String maHoaDon) {
        for(HoaDon hoaDon : danhSachHoaDon) {
            if (hoaDon.getMaHoaDon().equalsIgnoreCase(maHoaDon)) {
                return hoaDon;
            }
        }
        return null;
    }

    public boolean kiemTraMaHoaDon(String maHoaDon) {
        return timTheoMaHoaDon(maHoaDon) != null;
    }

    private void themVaoMang(HoaDon hoaDon) {
        danhSachHoaDon = Arrays.copyOf(danhSachHoaDon, danhSachHoaDon.length + 1);
        danhSachHoaDon[danhSachHoaDon.length - 1] = hoaDon;
    }

    @Override
    public void write(String filename) {
        String tenFileGhi = (filename == null || filename.isEmpty()) ? FILE_HOADON : filename;

        if (danhSachHoaDon.length == 0) {
            System.out.println(" Danh sách trống, không có dữ liệu để ghi!");
            return;
        }

        File file = new File(tenFileGhi);
        File thuMucCha = file.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }

        try (BufferedWriter boGhi = new BufferedWriter(new FileWriter(tenFileGhi, false))) {
            for(HoaDon hd : danhSachHoaDon) {
                boGhi.write(hd.toFileString());
                boGhi.newLine();

                // Ghi chi tiết hóa đơn vào file riêng
                hd.getDanhSachChiTietHoaDon().write(hd.getMaHoaDon());
            }

            System.out.println("✓ Ghi danh sách hóa đơn thành công vào file: " + tenFileGhi);
        } catch (IOException e) {
            System.err.println(" ✗ Lỗi khi ghi danh sách hóa đơn: " + e.getMessage());
        }
    }

    @Override
    public void read(String filename) {
        String tenFileDoc = (filename == null || filename.isEmpty()) ? FILE_HOADON : filename;
        File file = new File(tenFileDoc);

        if (!file.exists()) {
            System.out.println(" File không tồn tại: " + tenFileDoc);
            return;
        }

        this.danhSachHoaDon = new HoaDon[0];
        int soLuongThanhCong = 0;
        int soLuongLoi = 0;
        int soDong = 0;

        try (Scanner boDoc = new Scanner(file)) {
            while(boDoc.hasNextLine()) {
                soDong++;
                String duLieu = boDoc.nextLine();
                if (duLieu.trim().isEmpty()) continue;

                try {
                    HoaDon hoaDon = HoaDon.fromFileString(duLieu);

                    // Truyền danhSachSanPham vào constructor
                    DanhSachChiTietHoaDon danhSachChiTietHoaDon = new DanhSachChiTietHoaDon(danhSachSanPham);
                    danhSachChiTietHoaDon.read(hoaDon.getMaHoaDon());
                    hoaDon.setDanhSachChiTietHoaDon(danhSachChiTietHoaDon);

                    if (kiemTraMaHoaDon(hoaDon.getMaHoaDon())) {
                        System.out.println("Dòng " + soDong + ": Mã hóa đơn trùng lặp, bỏ qua.");
                        soLuongLoi++;
                    } else if (!hoaDon.validate()) {
                        themVaoMang(hoaDon);
                        soLuongThanhCong++;
                    } else {
                        themVaoMang(hoaDon);
                        soLuongThanhCong++;
                    }
                } catch (ParseException | NumberFormatException e) {
                    System.err.println("⚠ Lỗi phân tích dữ liệu dòng " + soDong + ": " + e.getMessage());
                    soLuongLoi++;
                } catch (IllegalArgumentException e) {
                    System.err.println("⚠ Lỗi định dạng dòng " + soDong + ": " + e.getMessage());
                    soLuongLoi++;
                }
            }
            System.out.println("✓ Đọc file thành công! Số hóa đơn hợp lệ: " + soLuongThanhCong + (soLuongLoi > 0 ? " (" + soLuongLoi + " lỗi)" : ""));

        } catch (FileNotFoundException e) {
            System.err.println("✗ Lỗi: Không tìm thấy file hóa đơn: " + tenFileDoc);
        }
    }

    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);
        int tiepTuc = 1;

        if (dsNhanVien == null || dsKhachHang == null) {
            System.err.println("Lỗi: Không thể thực hiện thêm hóa đơn do thiếu dữ liệu NV/KH.");
            return;
        }

        do {
            System.out.println("\n========== NHẬP HÓA ĐƠN MỚI ==========");
            HoaDon hoaDon = new HoaDon();

            hoaDon.nhap(this.dsNhanVien, this.dsKhachHang);

            if (!hoaDon.validate()) {
                System.out.println("✗ Dữ liệu hóa đơn không hợp lệ (Mã, Chi tiết,...)! Hủy thao tác.");
            } else if (this.kiemTraMaHoaDon(hoaDon.getMaHoaDon())) {
                System.out.println("✗ Lỗi: Mã hóa đơn đã tồn tại. Vui lòng nhập lại.");
            } else {
                themVaoMang(hoaDon);
                write(FILE_HOADON);
                System.out.println("✓ Thêm hóa đơn " + hoaDon.getMaHoaDon() + " thành công.");
            }

            System.out.print("Bạn có tiếp tục nhập hóa đơn không? (1: tiếp tục / 0: dừng lại): ");
            try {
                tiepTuc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                tiepTuc = 0;
            }
        } while(tiepTuc == 1);
    }

    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        String maHoaDon = sc.nextLine().trim();

        int viTri = -1;
        for(int i = 0; i < danhSachHoaDon.length; ++i) {
            if (danhSachHoaDon[i].getMaHoaDon().equalsIgnoreCase(maHoaDon)) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.err.println("✗ Không tìm thấy hóa đơn có mã: " + maHoaDon);
            return;
        }

        // Xóa hóa đơn
        HoaDon hdCanXoa = danhSachHoaDon[viTri];
        DanhSachChiTietHoaDon dsCTHD = hdCanXoa.getDanhSachChiTietHoaDon();

        // Xóa khỏi mảng
        System.arraycopy(danhSachHoaDon, viTri + 1, danhSachHoaDon, viTri, danhSachHoaDon.length - 1 - viTri);
        danhSachHoaDon = Arrays.copyOf(danhSachHoaDon, danhSachHoaDon.length - 1);
        write(FILE_HOADON);
        System.out.println("✓ Đã xóa hóa đơn có mã: " + maHoaDon);
    }

    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần sửa: ");
        String maHoaDon = sc.nextLine().trim();

        HoaDon hd = timTheoMaHoaDon(maHoaDon);
        if (hd == null) {
            System.out.println("✗ Không tìm thấy hóa đơn với mã: " + maHoaDon);
            return;
        }

        System.out.println("\n--- SỬA HÓA ĐƠN: " + maHoaDon + " ---");
        hd.xuat();

        System.out.println("1. Sửa thông tin cơ bản (Mã NV, Mã KH, Ngày)");
        System.out.println("2. Quản lý Chi tiết Hóa đơn (Thêm/Sửa/Xóa SP)");
        System.out.print("Chọn: ");
        String luaChon = sc.nextLine().trim();

        if (luaChon.equals("1")) {
            suaThongTinCoBan(hd, sc);
        } else if (luaChon.equals("2")) {
            quanLyChiTietHoaDon(hd, sc);
        } else {
            System.out.println("→ Hủy thao tác sửa.");
        }

        hd.getTongGiaHoaDon();
        write(FILE_HOADON);
        System.out.println("✓ Cập nhật hóa đơn hoàn tất.");
    }

    private void suaThongTinCoBan(HoaDon hoaDon, Scanner sc) {
        System.out.print("Nhập mã nhân viên mới (Enter để bỏ qua): ");
        String maNhanVienMoi = sc.nextLine().trim();
        if (!maNhanVienMoi.isEmpty()) {
            if (dsNhanVien.timTheoMa(maNhanVienMoi) != null) {
                hoaDon.setMaNhanVien(maNhanVienMoi);
            } else {
                System.err.println("⚠ Mã NV không tồn tại. Bỏ qua cập nhật NV.");
            }
        }

        System.out.print("Nhập mã khách hàng mới (Enter để bỏ qua): ");
        String maKhachHangMoi = sc.nextLine().trim();
        if (!maKhachHangMoi.isEmpty()) {
            if (dsKhachHang.timMaKhachHang(maKhachHangMoi) != null) {
                hoaDon.setMaKhachHang(maKhachHangMoi);
            } else {
                System.err.println("⚠ Mã KH không tồn tại. Bỏ qua cập nhật KH.");
            }
        }

        System.out.print("Nhập ngày tạo hóa đơn mới dd/MM/yyyy (Enter để bỏ qua): ");
        String ngayTaoMoi = sc.nextLine().trim();
        if (!ngayTaoMoi.isEmpty()) {
            SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
            try {
                hoaDon.setNgayTaoHoaDon(dinhDangNgay.parse(ngayTaoMoi));
            } catch (ParseException e) {
                System.err.println("✗ Lỗi định dạng ngày. Ngày tạo không được cập nhật.");
            }
        }
        System.out.println("✓ Đã cập nhật thông tin cơ bản.");
    }

    private void quanLyChiTietHoaDon(HoaDon hoaDon, Scanner sc) {
        DanhSachChiTietHoaDon dsCTHD = hoaDon.getDanhSachChiTietHoaDon();

        System.out.println("\n--- MENU QUẢN LÝ CHI TIẾT ---");
        System.out.println("1. Thêm sản phẩm mới vào Hóa đơn");
        System.out.println("2. Sửa số lượng sản phẩm");
        System.out.println("3. Xóa sản phẩm khỏi Hóa đơn");
        System.out.print("Chọn: ");
        String luaChon = sc.nextLine().trim();

        if (luaChon.equals("1")) {
            dsCTHD.them(hoaDon.getMaHoaDon());
        } else if (luaChon.equals("2")) {
            System.out.print("Nhập mã sản phẩm cần sửa số lượng: ");
            String maSP = sc.nextLine().trim();
            dsCTHD.suaThongTinTheoMa(maSP);

        } else if (luaChon.equals("3")) {
            System.out.print("Nhập mã sản phẩm cần xóa: ");
            String maSP = sc.nextLine().trim();
            dsCTHD.xoa(maSP);
        } else {
            System.out.println("→ Hủy thao tác quản lý chi tiết.");
        }
        hoaDon.getDanhSachChiTietHoaDon().write(hoaDon.getMaHoaDon());
    }

    @Override
    public void in() {
        System.out.println("\n=======================================================");
        System.out.println("               DANH SÁCH HÓA ĐƠN               ");
        System.out.println("Tổng số hóa đơn: " + danhSachHoaDon.length);
        System.out.println("=======================================================");

        for(HoaDon hd : danhSachHoaDon) {
            hd.xuat();
        }
    }

    @Override
    public void timTheoMa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần tìm: ");
        String maHoaDon = sc.nextLine().trim();

        HoaDon hd = this.timTheoMaHoaDon(maHoaDon);
        if (hd != null) {
            System.out.println("✓ Đã tìm thấy hóa đơn:");
            hd.xuat();
        } else {
            System.out.println("✗ Không tìm thấy hóa đơn có mã: " + maHoaDon);
        }
    }

    @SuppressWarnings("resource")
    public void traCuuThongTin() {
        Scanner sc = new Scanner(System.in);
        int tiepTuc = 1;

        do {
            System.out.println("\n--- MENU TRA CỨU HÓA ĐƠN ---");
            System.out.println("1. Tra cứu theo Mã Nhân Viên");
            System.out.println("2. Tra cứu theo Mã Khách Hàng");
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
                    System.out.print("Nhập mã nhân viên: ");
                    String maNhanVien = sc.nextLine().trim();
                    int[] viTriNV = this.timTheoMaNhanVien(maNhanVien);
                    if (viTriNV.length > 0) {
                        System.out.println("=== KẾT QUẢ TRA CỨU THEO MÃ NV: " + maNhanVien + " ===");
                        for(int i = 0; i < viTriNV.length; ++i) {
                            danhSachHoaDon[viTriNV[i]].xuat();
                        }
                    } else {
                        System.out.println("Không tìm thấy hóa đơn nào cho mã nhân viên này.");
                    }
                    break;
                case 2:
                    System.out.print("Nhập mã khách hàng: ");
                    String maKhachHang = sc.nextLine().trim();
                    int[] viTriKH = this.timTheoMaKhachHang(maKhachHang);
                    if (viTriKH.length > 0) {
                        System.out.println("=== KẾT QUẢ TRA CỨU THEO MÃ KH: " + maKhachHang + " ===");
                        for(int i = 0; i < viTriKH.length; ++i) {
                            danhSachHoaDon[viTriKH[i]].xuat();
                        }
                    } else {
                        System.out.println("Không tìm thấy hóa đơn nào cho mã khách hàng này.");
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
        } while(tiepTuc == 1);
    }

    public int[] timTheoMaNhanVien(String maNhanVien) {
        int[] viTri = new int[0];
        for(int i = 0; i < danhSachHoaDon.length; ++i) {
            if (danhSachHoaDon[i].getMaNhanVien().toLowerCase().contains(maNhanVien.toLowerCase())) {
                viTri = Arrays.copyOf(viTri, viTri.length + 1);
                viTri[viTri.length - 1] = i;
            }
        }
        return viTri;
    }

    public int[] timTheoMaKhachHang(String maKhachHang) {
        int[] viTri = new int[0];
        for(int i = 0; i < danhSachHoaDon.length; ++i) {
            if (danhSachHoaDon[i].getMaKhachHang().toLowerCase().contains(maKhachHang.toLowerCase())) {
                viTri = Arrays.copyOf(viTri, viTri.length + 1);
                viTri[viTri.length - 1] = i;
            }
        }
        return viTri;
    }

    // Class nội bộ để lưu thông tin doanh thu từng sản phẩm
    private class ThongTinDoanhThu {
        String maSanPham;
        long tongDoanhThu;

        ThongTinDoanhThu(String maSanPham, long tongDoanhThu) {
            this.maSanPham = maSanPham;
            this.tongDoanhThu = tongDoanhThu;
        }
    }

    private class ThongKeDoanhThu {
        String maSP;
        long doanhThu;

        ThongKeDoanhThu(String maSP, long doanhThu) {
            this.maSP = maSP;
            this.doanhThu = doanhThu;
        }
    }

    // ĐÃ SỬA: Không dùng Map, chỉ dùng mảng
    public void thongKeTheoDoanhThu() {
        if (danhSachSanPham == null) {
            System.err.println("Lỗi: DanhSachSanPham chưa được khởi tạo!");
            return;
        }

        // Tạo mảng lưu thống kê doanh thu
        ThongKeDoanhThu[] thongKe = new ThongKeDoanhThu[0];

        // Duyệt qua tất cả hóa đơn
        for (HoaDon hoaDon : danhSachHoaDon) {
            DanhSachChiTietHoaDon danhSachChiTietHoaDon = hoaDon.getDanhSachChiTietHoaDon();

            for (int k = 0; k < danhSachChiTietHoaDon.getSoluong(); k++) {
                ChiTietHoaDon cthd = danhSachChiTietHoaDon.viTri(k);
                if (cthd != null) {
                    String maSP = cthd.getMaSanPham();
                    long tongGia = cthd.getTongGia();

                    // Tìm xem sản phẩm đã có trong mảng thống kê chưa
                    boolean timThay = false;
                    for (int i = 0; i < thongKe.length; i++) {
                        if (thongKe[i].maSP.equalsIgnoreCase(maSP)) {
                            thongKe[i].doanhThu += tongGia;
                            timThay = true;
                            break;
                        }
                    }

                    // Nếu chưa có, thêm mới vào mảng
                    if (!timThay) {
                        thongKe = Arrays.copyOf(thongKe, thongKe.length + 1);
                        thongKe[thongKe.length - 1] = new ThongKeDoanhThu(maSP, tongGia);
                    }
                }
            }
        }

        // Sắp xếp mảng theo doanh thu giảm dần (Bubble Sort)
        for (int i = 0; i < thongKe.length - 1; i++) {
            for (int j = 0; j < thongKe.length - i - 1; j++) {
                if (thongKe[j].doanhThu < thongKe[j + 1].doanhThu) {
                    // Hoán đổi
                    ThongKeDoanhThu temp = thongKe[j];
                    thongKe[j] = thongKe[j + 1];
                    thongKe[j + 1] = temp;
                }
            }
        }

        // In kết quả
        System.out.println("\n=== THỐNG KÊ DOANH THU THEO SẢN PHẨM ===");
        for (int i = 0; i < thongKe.length; i++) {
            SanPham sp = danhSachSanPham.timTheoMa(thongKe[i].maSP);
            String tenSP = (sp != null) ? sp.getTenSP() : "Không tìm thấy SP (" + thongKe[i].maSP + ")";
            System.out.printf("%-40s | Doanh thu: %,d VND\n", tenSP, thongKe[i].doanhThu);
        }
    }

    // Class nội bộ để lưu thông tin số lượng sản phẩm bán
    private class ThongKeSoLuong {
        String maSP;
        int soLuong;

        ThongKeSoLuong(String maSP, int soLuong) {
            this.maSP = maSP;
            this.soLuong = soLuong;
        }
    }

    // ĐÃ SỬA: Không dùng Map, List, chỉ dùng mảng
    public void thongKeTheoSoLuongSanPham() {
        if (danhSachSanPham == null) {
            System.err.println("Lỗi: DanhSachSanPham chưa được khởi tạo!");
            return;
        }

        // Tạo mảng lưu thống kê số lượng
        ThongKeSoLuong[] thongKe = new ThongKeSoLuong[0];

        // Duyệt qua tất cả hóa đơn
        for (HoaDon hoaDon : danhSachHoaDon) {
            DanhSachChiTietHoaDon danhSachChiTietHoaDon = hoaDon.getDanhSachChiTietHoaDon();

            for (int k = 0; k < danhSachChiTietHoaDon.getSoluong(); k++) {
                ChiTietHoaDon cthd = danhSachChiTietHoaDon.viTri(k);
                if (cthd != null) {
                    String maSP = cthd.getMaSanPham();
                    int soLuong = cthd.getSoLuongSP();

                    // Tìm xem sản phẩm đã có trong mảng thống kê chưa
                    boolean timThay = false;
                    for (int i = 0; i < thongKe.length; i++) {
                        if (thongKe[i].maSP.equalsIgnoreCase(maSP)) {
                            thongKe[i].soLuong += soLuong;
                            timThay = true;
                            break;
                        }
                    }

                    // Nếu chưa có, thêm mới vào mảng
                    if (!timThay) {
                        thongKe = Arrays.copyOf(thongKe, thongKe.length + 1);
                        thongKe[thongKe.length - 1] = new ThongKeSoLuong(maSP, soLuong);
                    }
                }
            }
        }

        // Sắp xếp mảng theo số lượng giảm dần (Bubble Sort)
        for (int i = 0; i < thongKe.length - 1; i++) {
            for (int j = 0; j < thongKe.length - i - 1; j++) {
                if (thongKe[j].soLuong < thongKe[j + 1].soLuong) {
                    // Hoán đổi
                    ThongKeSoLuong temp = thongKe[j];
                    thongKe[j] = thongKe[j + 1];
                    thongKe[j + 1] = temp;
                }
            }
        }

        // In kết quả
        System.out.println("\n=== THỐNG KÊ SỐ LƯỢNG SẢN PHẨM BÁN RA ===");
        for (int i = 0; i < thongKe.length; i++) {
            SanPham sp = danhSachSanPham.timTheoMa(thongKe[i].maSP);
            if (sp != null) {
                String loaiSP = "";
                if (sp instanceof Laptop) {
                    Laptop laptop = (Laptop)sp;
                    loaiSP = " (Laptop: " + laptop.getCpu() + "/" + laptop.getRam() + ")";
                } else if (sp instanceof LinhKien) {
                    LinhKien linhKien = (LinhKien)sp;
                    loaiSP = " (Linh Kiện: " + linhKien.getLoaiLinhKien() + ")";
                }
                System.out.printf("%-40s bán được: %d sản phẩm\n", sp.getTenSP() + loaiSP, thongKe[i].soLuong);
            }
        }

        // Tóm tắt min/max
        if (thongKe.length > 0) {
            System.out.println("\n--- TÓM TẮT ---");

            // Sản phẩm bán chạy nhất (đầu mảng sau khi sắp xếp)
            String maMax = thongKe[0].maSP;
            int slMax = thongKe[0].soLuong;
            SanPham spMax = danhSachSanPham.timTheoMa(maMax);

            // Sản phẩm bán ít nhất (cuối mảng sau khi sắp xếp)
            String maMin = thongKe[thongKe.length - 1].maSP;
            int slMin = thongKe[thongKe.length - 1].soLuong;
            SanPham spMin = danhSachSanPham.timTheoMa(maMin);

            if (spMax != null) {
                System.out.println("Sản phẩm bán chạy nhất: " + spMax.getTenSP() + " (" + slMax + " sản phẩm)");
            }
            if (spMin != null) {
                System.out.println("Sản phẩm bán ít nhất: " + spMin.getTenSP() + " (" + slMin + " sản phẩm)");
            }
        }
    }

    @SuppressWarnings("resource")
    public void thongKeTheoQuy() {
        Scanner sc = new Scanner(System.in);
        int year;

        System.out.print("Nhập năm cần thống kê: ");
        try {
            year = sc.nextInt();
        } catch (Exception e) {
            System.err.println("Năm không hợp lệ!");
            return;
        }

        long[] doanhThuQuy = new long[4];
        int[] soLuongQuy = new int[4];

        System.out.println("=== THỐNG KÊ THEO NĂM " + year + " ===");

        for(HoaDon hoaDon : danhSachHoaDon) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(hoaDon.getNgayTaoHoaDon());
            int hoaDonYear = cal.get(Calendar.YEAR);
            // Lấy tháng (0-11)
            int hoaDonMonth = cal.get(Calendar.MONTH);

            if (hoaDonYear == year) {
                // Quy = (Tháng 0-11) / 3
                int quy = hoaDonMonth / 3;

                doanhThuQuy[quy] += hoaDon.getTongGiaHoaDon();

                DanhSachChiTietHoaDon danhSachChiTietHoaDon = hoaDon.getDanhSachChiTietHoaDon();
                for(int k = 0; k < danhSachChiTietHoaDon.getSoluong(); ++k) {
                    ChiTietHoaDon cthd = danhSachChiTietHoaDon.viTri(k);
                    if (cthd != null) {
                        soLuongQuy[quy] += cthd.getSoLuongSP();
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            System.out.printf("Quý %d: Bán được %,d sản phẩm, Doanh thu: %,d VND\n",
                    i + 1, soLuongQuy[i], doanhThuQuy[i]);
        }
    }

    public long thongKeTuNgayDenNgay(Date day1, Date day2) {
        long doanhThu = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            day1 = sdf.parse(sdf.format(day1));
            day2 = sdf.parse(sdf.format(day2));
        } catch (ParseException e) {
            return 0;
        }

        Calendar calDay2 = Calendar.getInstance();
        calDay2.setTime(day2);
        calDay2.add(Calendar.DATE, 1);
        Date day2Inclusive = calDay2.getTime();

        if (day1.compareTo(day2) <= 0) {
            for(HoaDon hoaDon : danhSachHoaDon) {
                Date ngayTao = null;
                try {
                    // Dùng SDF để loại bỏ yếu tố thời gian (HH:mm:ss) khỏi ngày tạo hóa đơn
                    ngayTao = sdf.parse(sdf.format(hoaDon.getNgayTaoHoaDon()));
                } catch (ParseException e) {
                    continue;
                }

                if (ngayTao.compareTo(day1) >= 0 && ngayTao.compareTo(day2Inclusive) < 0) {
                    doanhThu += hoaDon.getTongGiaHoaDon();
                }
            }
        }
        return doanhThu;
    }

    public void thongKeTheoNam() {
        if (danhSachHoaDon.length == 0) {
            System.out.println("Không có hóa đơn để thống kê.");
            return;
        }

        int minYear = Integer.MAX_VALUE;
        int maxYear = Integer.MIN_VALUE;

        for(HoaDon hoaDon : danhSachHoaDon) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(hoaDon.getNgayTaoHoaDon());
            int currentYear = cal.get(Calendar.YEAR);
            if (currentYear < minYear) {
                minYear = currentYear;
            }
            if (currentYear > maxYear) {
                maxYear = currentYear;
            }
        }

        if (minYear == Integer.MAX_VALUE) return; // Trường hợp không có hóa đơn

        System.out.println("\n=== THỐNG KÊ DOANH THU THEO NĂM (Từ " + minYear + " đến " + maxYear + ") ===");

        for(int year = minYear; year <= maxYear; year++) {

            Calendar calDay1 = Calendar.getInstance();
            calDay1.set(year, Calendar.JANUARY, 1, 0, 0, 0);
            calDay1.set(Calendar.MILLISECOND, 0);
            Date day1 = calDay1.getTime();

            Calendar calDay2 = Calendar.getInstance();
            calDay2.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
            calDay2.set(Calendar.MILLISECOND, 999);
            Date day2 = calDay2.getTime();

            long doanhThuNam = this.thongKeTuNgayDenNgay(day1, day2);

            if (doanhThuNam > 0) {
                System.out.printf("Doanh thu năm %d: %,d VND\n", year, doanhThuNam);
            }
        }
    }
}