package manager;

/**
 *  LỚP TRUNG TÂM DỮ LIỆU HỆ THỐNG
 * -----------------------------------
 * - Chứa tất cả danh sách dùng chung
 * - Đọc dữ liệu từ file 1 lần khi khởi động
 */
public class QuanLyCuaHangMayTinh {

    // =============================
    //  DỮ LIỆU DÙNG CHUNG
    // =============================

    public static final DanhSachLoaiLinhKien dsLoaiLK = new DanhSachLoaiLinhKien();
    public static final DanhSachCPU dsCPU = new DanhSachCPU();
    public static final DanhSachRAM dsRAM = new DanhSachRAM();
    public static final DanhSachSanPham dssp = new DanhSachSanPham();

    public static final DanhSachNhanVien dsnv = new DanhSachNhanVien();
    public static final DanhSachPhongBan dspb = new DanhSachPhongBan();

    public static final DanhSachKhachHang dskh = new DanhSachKhachHang();
    public static final DanhSachNhaCungCap dsncc = new DanhSachNhaCungCap(); // Sửa: bỏ tham số

    public static final DanhSachChiTietHoaDon dscthd = new DanhSachChiTietHoaDon();

    public static final DanhSachPhieuNhapHang dspnh = new DanhSachPhieuNhapHang();
    public static final DanhSachChiTietPNH dsctpnh = new DanhSachChiTietPNH();

    public static final DanhSachLogin dslogin = new DanhSachLogin();

    public static final DanhSachHoaDon dshd = new DanhSachHoaDon();

    // =============================
    //  KHỞI TẠO DỮ LIỆU BAN ĐẦU
    // =============================
    public static void khoiTaoDuLieu() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("    ĐANG TẢI DỮ LIỆU TOÀN HỆ THỐNG...    ");
        System.out.println("═══════════════════════════════════════════\n");
        
        try {
            // SAN PHẨM & CẤU HÌNH
            System.out.println(" Đang tải cấu hình sản phẩm...");
            dsLoaiLK.read("data\\DanhSachLoaiLinhKien.txt");
            dsCPU.read("data\\DanhSachCPU.txt");
            dsRAM.read("data\\DanhSachRAM.txt");
            dssp.read("data\\DanhSachSanPham.txt");
            System.out.println("   Đã tải xong dữ liệu sản phẩm\n");

            // NHÂN SỰ
            System.out.println(" Đang tải dữ liệu nhân sự...");
            dspb.read("data\\DanhSachPhongBan.txt");
            dsnv.read("data\\DanhSachNhanVien.txt");
            System.out.println("   Đã tải xong dữ liệu nhân sự\n");

            // KHÁCH HÀNG & NHÀ CUNG CẤP
            System.out.println(" Đang tải khách hàng & nhà cung cấp...");
            dskh.read("data\\DanhSachKhachHang.txt");
            dsncc.read("data\\DanhSachNhaCungCap.txt"); // Sửa: thêm filename
            System.out.println("   Đã tải xong khách hàng & NCC\n");

            // HÓA ĐƠN & NHẬP HÀNG
            System.out.println(" Đang tải hóa đơn & phiếu nhập...");
            dshd.read("data\\DanhSachHoaDon.txt");
            dscthd.readAll();
            dspnh.read("data\\DanhSachPhieuNhapHang.txt"); // Sửa: thêm filename
            dsctpnh.readAll();
            System.out.println("   Đã tải xong hóa đơn & phiếu nhập\n");

            // TÀI KHOẢN ĐĂNG NHẬP
            System.out.println(" Đang tải tài khoản...");
            dslogin.read();
            System.out.println("   Đã tải xong tài khoản\n");

            System.out.println("═══════════════════════════════════════════");
            System.out.println("   DỮ LIỆU ĐÃ ĐƯỢC TẢI THÀNH CÔNG!  ");
            System.out.println("═══════════════════════════════════════════\n");
            
        } catch (Exception e) {
            System.err.println("\n LỖI KHI TẢI DỮ LIỆU: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =============================
    //  LƯU DỮ LIỆU KHI THOÁT
    // =============================
    public static void luuTatCa() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("      ĐANG LƯU TOÀN BỘ DỮ LIỆU...       ");
        System.out.println("═══════════════════════════════════════════\n");

        try {
            // SAN PHẨM & CẤU HÌNH
            System.out.println(" Đang lưu dữ liệu sản phẩm...");
            dsLoaiLK.write("data\\DanhSachLoaiLinhKien.txt");
            dsCPU.write("data\\DanhSachCPU.txt");
            dsRAM.write("data\\DanhSachRAM.txt");
            dssp.write("data\\DanhSachSanPham.txt");
            System.out.println("   Đã lưu xong dữ liệu sản phẩm\n");

            // NHÂN SỰ
            System.out.println(" Đang lưu dữ liệu nhân sự...");
            dspb.write("data\\DanhSachPhongBan.txt");
            dsnv.write("data\\DanhSachNhanVien.txt");
            System.out.println("   Đã lưu xong dữ liệu nhân sự\n");

            // KHÁCH HÀNG & NHÀ CUNG CẤP
            System.out.println(" Đang lưu khách hàng & nhà cung cấp...");
            dskh.write("data\\DanhSachKhachHang.txt");
            dsncc.write("data\\DanhSachNhaCungCap.txt");
            System.out.println("   Đã lưu xong khách hàng & NCC\n");

            // HÓA ĐƠN & NHẬP HÀNG
            System.out.println(" Đang lưu hóa đơn & phiếu nhập...");
            dshd.write("data\\DanhSachHoaDon.txt");
            dspnh.write("data\\DanhSachPhieuNhapHang.txt");
            System.out.println("   Đã lưu xong hóa đơn & phiếu nhập\n");

            // TÀI KHOẢN ĐĂNG NHẬP
            System.out.println(" Đang lưu tài khoản...");
            dslogin.write();
            System.out.println("   Đã lưu xong tài khoản\n");

            System.out.println("═══════════════════════════════════════════");
            System.out.println("   ĐÃ LƯU XONG TOÀN BỘ DỮ LIỆU!    ");
            System.out.println("═══════════════════════════════════════════\n");
            
        } catch (Exception e) {
            System.err.println("\n LỖI KHI LƯU DỮ LIỆU: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =============================
    //  THỐNG KÊ TỔNG HỢP HỆ THỐNG
    // =============================
    
    public static void thongKeTongHop() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║      THỐNG KÊ TỔNG HỢP HỆ THỐNG              ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");
        
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  QUẢN LÝ SẢN PHẨM                           │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf("│  • Tổng số sản phẩm: %-23d│\n", dssp.getSoLuongSanPham());
        System.out.printf("│  • Loại linh kiện: %-25d│\n", dsLoaiLK.getSL());
        System.out.printf("│  • CPU hỗ trợ: %-29d│\n", dsCPU.getSL());
        System.out.printf("│  • RAM hỗ trợ: %-29d│\n", dsRAM.getSL());
        System.out.println("└─────────────────────────────────────────────┘\n");
        
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  QUẢN LÝ NHÂN SỰ                            │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf("│  • Tổng số nhân viên: %-22d│\n", dsnv.getSoLuongNhanVien());
        System.out.printf("│  • Số phòng ban: %-27d│\n", dspb.getDanhSachPhongBan().length);
        System.out.printf("│  • Tài khoản: %-30d│\n", dslogin.danhSachLogin.length);
        System.out.println("└─────────────────────────────────────────────┘\n");
        
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  BÁN HÀNG & KHÁCH HÀNG                      │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf("│  • Tổng số khách hàng: %-21d│\n", dskh.getSoLuong());
        System.out.printf("│  • Tổng số hóa đơn: %-24d│\n", dshd.getSoLuong());
        System.out.println("└─────────────────────────────────────────────┘\n");
        
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  NHẬP HÀNG                                  │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf("│  • Số nhà cung cấp: %-24d│\n", dsncc.getSoLuong());
        System.out.printf("│  • Số phiếu nhập: %-26d│\n", dspnh.getSoLuong());
        System.out.println("└─────────────────────────────────────────────┘\n");
    }
    
    // =============================
    //  KIỂM TRA TÍNH TOÀN VẸN DỮ LIỆU
    // =============================
    
    public static void kiemTraDuLieu() {
        System.out.println("\n Đang kiểm tra tính toàn vẹn dữ liệu...\n");
        
        int soLoi = 0;
        
        // Kiểm tra sản phẩm
        if (dssp.isEmpty()) {
            System.out.println("  ⚠ Cảnh báo: Chưa có sản phẩm nào trong hệ thống");
            soLoi++;
        }
        
        // Kiểm tra nhân viên
        if (dsnv.getSoLuongNhanVien() == 0) {
            System.out.println("  ⚠ Cảnh báo: Chưa có nhân viên nào trong hệ thống");
            soLoi++;
        }
        
        // Kiểm tra phòng ban
        if (dspb.getDanhSachPhongBan().length == 0) {
            System.out.println("  ⚠ Cảnh báo: Chưa có phòng ban nào");
            soLoi++;
        }
        
        if (soLoi == 0) {
            System.out.println("   Dữ liệu hệ thống hoạt động bình thường!\n");
        } else {
            System.out.println("\n  Tổng số cảnh báo: " + soLoi + "\n");
        }
    }
}