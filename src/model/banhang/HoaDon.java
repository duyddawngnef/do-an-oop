package model.banhang;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HoaDon {
    private String maHoaDon;
    private String maNhanVien;
    private String maKhachHang;
    private long tongGiaHoaDon;
    private Date ngayTaoHoaDon;

    // --- HẰNG SỐ VÀ PATTERN ---
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT_PATTERN);
    private static final Pattern MA_HD_PATTERN = Pattern.compile("^HD\\d{3,}$");
    private static final Scanner sc = new Scanner(System.in);

    // --- Constructors ---
    public HoaDon() {
        this.ngayTaoHoaDon = new Date();
        this.tongGiaHoaDon = 0;
    }

    // Constructor đầy đủ (Tùy chọn)
    public HoaDon(String maHoaDon, String maNhanVien, String maKhachHang, Date ngayTaoHoaDon) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.ngayTaoHoaDon = ngayTaoHoaDon;
    }

    // --- Getters và Setters ---
    public String getMaHoaDon() {
        return this.maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return this.maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaKhachHang() {
        return this.maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Date getNgayTaoHoaDon() {
        return (Date) this.ngayTaoHoaDon.clone();
    }

    public void setNgayTaoHoaDon(Date ngayTaoHoaDon) {
        this.ngayTaoHoaDon = ngayTaoHoaDon;
    }

    public void setTongGiaHoaDon(long tongGiaHoaDon) {
        this.tongGiaHoaDon = tongGiaHoaDon;
    }

    // Phương thức tính toán/lấy tổng giá
    public long getTongGiaHoaDon() {
        return this.tongGiaHoaDon;
    }

    // --- Validation và Utility Methods ---

    public static boolean isValidMaHoaDon(String ma) {
        return ma != null && MA_HD_PATTERN.matcher(ma).matches();
    }

    /**
     * Kiểm tra tính hợp lệ của các trường dữ liệu
     */
    public boolean validate() {
        if (maHoaDon == null || maHoaDon.trim().isEmpty() || !isValidMaHoaDon(maHoaDon)) {
            System.err.println("Lỗi: Mã hóa đơn không hợp lệ hoặc để trống (VD: HD001)!");
            return false;
        }
        if (maNhanVien == null || maNhanVien.trim().isEmpty()) {
            System.err.println("Lỗi: Mã nhân viên không được để trống!");
            return false;
        }
        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
            System.err.println("Lỗi: Mã khách hàng không được để trống!");
            return false;
        }
        return true;
    }

    // --- Phương thức Nhập Dữ liệu ---
    public void nhap() {
        String inputMa;

        System.out.println("\n========== NHẬP THÔNG TIN HÓA ĐƠN ==========");

        do {
            System.out.print("Nhập mã hóa đơn (VD: HD001): ");
            inputMa = sc.nextLine().trim();
            this.setMaHoaDon(inputMa);
            if (!isValidMaHoaDon(inputMa)) {
                System.out.println("Mã hóa đơn không hợp lệ! Vui lòng nhập lại.");
            }
        } while (!isValidMaHoaDon(inputMa));

        System.out.print("Nhập mã nhân viên: ");
        this.maNhanVien = sc.nextLine().trim();

        System.out.print("Nhập mã khách hàng: ");
        this.maKhachHang = sc.nextLine().trim();

        this.ngayTaoHoaDon = new Date();
    }

    // --- Phương thức Xuất Dữ liệu ---
    public void xuat() {
        System.out.println(
                "=========================================================================================================");
        System.out.println("Mã hóa đơn: " + this.maHoaDon);
        System.out.println("Ngày tạo hóa đơn: " + SDF.format(this.ngayTaoHoaDon));
        System.out.println("Mã nhân viên: " + this.maNhanVien);
        System.out.println("Mã khách hàng: " + this.maKhachHang);

        // Chi tiết hóa đơn được quản lý và in từ lớp quản lý chi tiết (manager)

        // Xuất tổng tiền (đã được tính lại trong getter)
        System.out.printf("TỔNG TIỀN HÓA ĐƠN: %,d VND\n", this.getTongGiaHoaDon());
        System.out.println(
                "=========================================================================================================");
    }

    // --- Phương thức Ghi File ---

    public String toFileString() {
        String ngayTaoFormatted = SDF.format(this.ngayTaoHoaDon);
        return String.format("%s;%s;%s;%s;%d",
                this.maHoaDon,
                ngayTaoFormatted,
                this.maNhanVien,
                this.maKhachHang,
                this.getTongGiaHoaDon());
    }

    public boolean write(String fileName) {
        if (!validate()) {
            System.err.println(" Không thể ghi hóa đơn do dữ liệu không hợp lệ!");
            return false;
        }

        boolean hoaDonGhiThanhCong = false;

        // 1. Ghi thông tin Hóa đơn chính
        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            myWriter.write(this.toFileString());
            myWriter.newLine();
            System.out.println("Ghi hóa đơn " + this.maHoaDon + " vào file chính thành công!");
            hoaDonGhiThanhCong = true;
        } catch (IOException e) {
            System.err.println("  Lỗi khi ghi hóa đơn vào file " + fileName + ": " + e.getMessage());
        }

        return hoaDonGhiThanhCong;
    }

    // --- Static Utility for Reading (Tương tự KhachHang) ---
    public static HoaDon fromFileString(String line) throws ParseException, NumberFormatException {
        String[] values = line.split(";");
        if (values.length < 5) {
            throw new IllegalArgumentException("Dòng dữ liệu Hóa đơn không đủ trường (cần 5 cột).");
        }

        HoaDon hd = new HoaDon();
        hd.setMaHoaDon(values[0].trim());
        hd.setNgayTaoHoaDon(SDF.parse(values[1].trim()));
        hd.setMaNhanVien(values[2].trim());
        hd.setMaKhachHang(values[3].trim());
        try {
            hd.setTongGiaHoaDon(Long.parseLong(values[4].trim()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Giá trị tổng tiền không hợp lệ hoặc quá lớn: " + values[4].trim());
        }

        return hd;
    }

    // Override toString
    @Override
    public String toString() {
        return String.format("HoaDon[Ma=%s, Ngay=%s, NV=%s, KH=%s, TongGia=%s]",
                maHoaDon, SDF.format(ngayTaoHoaDon), maNhanVien, maKhachHang, tongGiaHoaDon);
    }
}