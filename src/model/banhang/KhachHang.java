package model.banhang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class KhachHang {
    private String maKhachHang;
    private String hoKhachHang;
    private String tenKhachHang;
    private String diaChi;
    private String sdt;

    // Các pattern để validate dữ liệu
    private static final Pattern SDT_PATTERN = Pattern.compile("^0\\d{9}$");
    private static final Pattern MA_KH_PATTERN = Pattern.compile("^KH\\d{3,}$");
    private static final Pattern TEN_PATTERN = Pattern.compile("^[\\p{L}\\s]+$"); // Chỉ chấp nhận chữ cái và khoảng
                                                                                  // trắng

    // Constructor mặc định
    public KhachHang() {
    }

    // Constructor đầy đủ tham số
    public KhachHang(String maKhachHang, String hoKhachHang, String tenKhachHang,
            String diaChi, String sdt) {
        this.maKhachHang = maKhachHang;
        this.hoKhachHang = hoKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    // Constructor sao chép
    public KhachHang(KhachHang khachHang) {
        this.maKhachHang = khachHang.maKhachHang;
        this.hoKhachHang = khachHang.hoKhachHang;
        this.tenKhachHang = khachHang.tenKhachHang;
        this.diaChi = khachHang.diaChi;
        this.sdt = khachHang.sdt;
    }

    // Getters
    public String getMaKhachHang() {
        return maKhachHang;
    }

    public String getHoKhachHang() {
        return hoKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    // Getter cho họ tên đầy đủ
    public String getHoTenDayDu() {
        return hoKhachHang + " " + tenKhachHang;
    }

    // Setters với validation
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public void setHoKhachHang(String hoKhachHang) {
        this.hoKhachHang = hoKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    // Kiểm tra tính hợp lệ của mã khách hàng
    public static boolean isValidMaKhachHang(String ma) {
        return ma != null && MA_KH_PATTERN.matcher(ma).matches();
    }

    public static boolean isValidTen(String ten) {
        return ten != null && TEN_PATTERN.matcher(ten.trim()).matches();
    }

    // Kiểm tra tính hợp lệ của số điện thoại
    public static boolean isValidSdt(String sdt) {
        return sdt != null && SDT_PATTERN.matcher(sdt).matches();
    }

    // Validate toàn bộ thông tin khách hàng
    public boolean validate() {
        if (maKhachHang == null || maKhachHang.trim().isEmpty()) {
            System.out.println("Lỗi: Mã khách hàng không được để trống!");
            return false;
        }
        if (hoKhachHang == null || hoKhachHang.trim().isEmpty()) {
            System.out.println("Lỗi: Họ khách hàng không được để trống!");
            return false;
        }
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            System.out.println("Lỗi: Tên khách hàng không được để trống!");
            return false;
        }
        if (sdt == null || sdt.trim().isEmpty()) {
            System.out.println("Lỗi: Số điện thoại không được để trống!");
            return false;
        }
        if (!isValidSdt(sdt)) {
            System.out.println("Lỗi: Số điện thoại không hợp lệ! (Phải có 10 số và bắt đầu bằng 0)");
            return false;
        }
        return true;
    }

    // Nhập thông tin khách hàng với validation
    public void nhap() {
        Scanner scanner = new Scanner(System.in);

        // Nhập mã khách hàng
        do {
            System.out.print("Nhập mã khách hàng (VD: KH001): ");
            this.maKhachHang = scanner.nextLine().trim();
            if (this.maKhachHang.isEmpty()) {
                System.out.println("Mã khách hàng không được để trống!");
            }
        } while (this.maKhachHang.isEmpty());

        // Nhập họ
        do {
            System.out.print("Nhập họ khách hàng: ");
            this.hoKhachHang = scanner.nextLine().trim();
            if (this.hoKhachHang.isEmpty()) {
                System.out.println("Họ không được để trống!");
            }
        } while (this.hoKhachHang.isEmpty());

        // Nhập tên
        do {
            System.out.print("Nhập tên khách hàng: ");
            this.tenKhachHang = scanner.nextLine().trim();
            if (this.tenKhachHang.isEmpty()) {
                System.out.println("Tên không được để trống!");
            }
        } while (this.tenKhachHang.isEmpty());

        // Nhập địa chỉ
        System.out.print("Nhập địa chỉ: ");
        this.diaChi = scanner.nextLine().trim();

        // Nhập số điện thoại
        do {
            System.out.print("Nhập số điện thoại (10 số, bắt đầu bằng 0): ");
            this.sdt = scanner.nextLine().trim();
            if (!isValidSdt(this.sdt)) {
                System.out.println("Số điện thoại không hợp lệ! Vui lòng nhập lại.");
            }
        } while (!isValidSdt(this.sdt));
    }

    // Xuất thông tin khách hàng
    public void xuat() {
        System.out.printf("%-10s | %-30s | %-40s | %-12s%n",
                maKhachHang, getHoTenDayDu(), diaChi, sdt);
    }

    // Hiển thị thông tin dạng 1 dòng
    public void xuatMotDong() {
        System.out.printf("%-10s | %-30s | %-40s | %-12s%n",
                maKhachHang, getHoTenDayDu(), diaChi, sdt);
    }

    // Đọc thông tin khách hàng từ file tại dòng cụ thể
    public KhachHang read(String filename, int row) {
        int currentRow = 0;
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File không tồn tại: " + filename);
            return null;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                currentRow++;
                String line = scanner.nextLine().trim();

                if (line.isEmpty())
                    continue;

                if (currentRow == row) {
                    String[] values = line.split(";");
                    if (values.length >= 5) {
                        KhachHang kh = new KhachHang();
                        kh.setMaKhachHang(values[0].trim());
                        kh.setHoKhachHang(values[1].trim());
                        kh.setTenKhachHang(values[2].trim());
                        kh.setSdt(values[3].trim());
                        kh.setDiaChi(values[4].trim());
                        return kh;
                    } else {
                        System.out.println("Dữ liệu tại dòng " + row + " không đầy đủ!");
                        return null;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy file " + filename);
        }

        System.out.println("Không tìm thấy dữ liệu tại dòng " + row);
        return null;
    }

    // Ghi thông tin khách hàng vào file
    public boolean write(String filename) {
        if (!validate()) {
            return false;
        }

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(String.format("%s;%s;%s;%s;%s%n",
                    maKhachHang, hoKhachHang, tenKhachHang, sdt, diaChi));
            return true;
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
            return false;
        }
    }

    // Ghi static method để ghi nhiều khách hàng
    public static boolean write(String filename, KhachHang khachHang) {
        return khachHang.write(filename);
    }

    // Tìm kiếm khách hàng theo mã
    public static KhachHang timKiemTheoMa(String filename, String maKH) {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty())
                    continue;

                String[] values = line.split(";");
                if (values.length >= 5 && values[0].trim().equals(maKH)) {
                    KhachHang kh = new KhachHang();
                    kh.setMaKhachHang(values[0].trim());
                    kh.setHoKhachHang(values[1].trim());
                    kh.setTenKhachHang(values[2].trim());
                    kh.setSdt(values[3].trim());
                    kh.setDiaChi(values[4].trim());
                    return kh;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy file " + filename);
        }
        return null;
    }

    // Chuyển đổi thành chuỗi theo định dạng file
    public String toFileString() {
        return String.format("%s;%s;%s;%s;%s",
                maKhachHang, hoKhachHang, tenKhachHang, sdt, diaChi);
    }

    // Override toString
    @Override
    public String toString() {
        return String.format("KhachHang[Ma=%s, HoTen=%s, DiaChi=%s, SDT=%s]",
                maKhachHang, getHoTenDayDu(), diaChi, sdt);
    }

    // Override equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        KhachHang other = (KhachHang) obj;
        return maKhachHang != null && maKhachHang.equals(other.maKhachHang);
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return maKhachHang != null ? maKhachHang.hashCode() : 0;
    }
}