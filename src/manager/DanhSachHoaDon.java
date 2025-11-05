package manager;

import Interface.isList;
import model.banhang.HoaDon;
import model.sanpham.SanPham;
import model.banhang.ChiTietHoaDon;
import manager.DanhSachSanPham;
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
import java.util.Locale;
import java.util.Scanner;
import model.sanpham.SanPham;
import model.sanpham.Laptop;
import model.sanpham.LinhKien;

// ----------------------------------------------------------------------------------

public class DanhSachHoaDon implements isList {
    private static HoaDon[] danhSachHoaDons = new HoaDon[0];
    private int soLuong = 0; // Biến này không đồng bộ với kích thước mảng static

    // --- Phương thức của Interface IsList ---

    @Override
    public void write(String filename) {
        String defaultFileName = "data\\DanhSachHoaDon.txt";

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(defaultFileName, false))) {

            for (int i = 0; i < danhSachHoaDons.length; ++i) {
                // Ghi dữ liệu:
                // maHoaDon,ngayTaoHoaDon.toString(),maNhanVien,maKhachHang,tongGiaHoaDon
                String data = danhSachHoaDons[i].getMaHoaDon() + ";" +
                        danhSachHoaDons[i].getNgayTaoHoaDon().toString() + ";" +
                        danhSachHoaDons[i].getMaNhanVien() + ";" +
                        danhSachHoaDons[i].getMaKhachHang() + ";" +
                        danhSachHoaDons[i].getTongGiaHoaDon();

                myWriter.write(data);
                myWriter.newLine();

                danhSachHoaDons[i].getDanhSachChiTietHoaDon().write(danhSachHoaDons[i].getMaHoaDon());
            }

            System.out.println(" Ghi danh sách hóa đơn thành công vào file: " + defaultFileName);

        } catch (IOException e) {
            System.err.println(" Lỗi khi ghi danh sách hóa đơn: " + e.getMessage());
        }
    }

    @Override
    public void read(String filename) {
        String defaultFileName = "data\\DanhSachHoaDon.txt";
        File file = new File(defaultFileName);

        try (Scanner myReader = new Scanner(file)) {
            danhSachHoaDons = new HoaDon[0];

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.trim().isEmpty())
                    continue;

                String[] value = data.split(";");
                if (value.length < 5)
                    continue;

                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(value[0].trim());

                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
                Date ngayTaoHoaDon = null;

                try {
                    ngayTaoHoaDon = sdf.parse(value[1].trim());
                } catch (ParseException e) {
                    System.err.println("Lỗi phân tích cú pháp ngày: " + value[1]);
                }

                hoaDon.setNgayTaoHoaDon(ngayTaoHoaDon != null ? ngayTaoHoaDon : new Date());
                hoaDon.setMaNhanVien(value[2].trim());
                hoaDon.setMaKhachHang(value[3].trim());

                try {
                    hoaDon.setTongGiaHoaDon(Integer.parseInt(value[4].trim()));
                } catch (NumberFormatException e) {
                    hoaDon.setTongGiaHoaDon(0);
                }

                DanhSachChiTietHoaDon danhSachChiTietHoaDon = new DanhSachChiTietHoaDon();
                danhSachChiTietHoaDon.read(hoaDon.getMaHoaDon());
                hoaDon.setDanhSachChiTietHoaDon(danhSachChiTietHoaDon);

                this.them(hoaDon);
            }
            System.out.println(" Đọc thành công " + danhSachHoaDons.length + " hóa đơn từ file.");

        } catch (FileNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy file hóa đơn: " + defaultFileName);
        }
    }

    @Override
    public void them() {
        // Tái sử dụng phương thức them(String maHoaDon) cũ
        Scanner sc = new Scanner(System.in);
        int tiepTuc = 1;

        do {
            System.out.println("\n--- NHẬP HÓA ĐƠN MỚI ---");
            HoaDon hoaDon = new HoaDon();
            hoaDon.nhap();

            String maHD = hoaDon.getMaHoaDon();
            if (maHD == null || maHD.isEmpty()) {
                System.out.println(" Lỗi: Mã hóa đơn không hợp lệ. Bỏ qua thêm.");
            } else if (!this.kiemTraMaHoaDon(maHD)) {
                // Thêm vào mảng static
                danhSachHoaDons = Arrays.copyOf(danhSachHoaDons, danhSachHoaDons.length + 1);
                danhSachHoaDons[danhSachHoaDons.length - 1] = hoaDon;
                System.out.println(" Thêm hóa đơn " + maHD + " thành công.");

            } else {
                System.out.println(" Lỗi: Mã hóa đơn đã tồn tại. Vui lòng nhập lại.");
            }

            System.out.print("Bạn có tiếp tục nhập hóa đơn không? (1: tiếp tục / 0: dừng lại): ");
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
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        String maHoaDon = sc.nextLine().trim();
        this.xoa(maHoaDon);
    }

    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần sửa: ");
        String maHoaDon = sc.nextLine().trim();
        this.suaThongTinTheoMa(maHoaDon);
    }

    @Override
    public void in() {
        this.inDanhSach();
    }

    @Override
    public void timTheoMa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần tìm: ");
        String maHoaDon = sc.nextLine().trim();

        HoaDon hd = this.timTheoMaHoaDon(maHoaDon);
        if (hd != null) {
            System.out.println(" Đã tìm thấy hóa đơn:");
            hd.xuat();
        }
    }

    public int getSoLuong() {
        return danhSachHoaDons.length;
    }

    public void them(HoaDon hoaDon) {
        if (!this.kiemTraMaHoaDon(hoaDon.getMaHoaDon())) {
            danhSachHoaDons = Arrays.copyOf(danhSachHoaDons, danhSachHoaDons.length + 1);
            danhSachHoaDons[danhSachHoaDons.length - 1] = hoaDon;
        } else {
            System.out.println("Mã hóa đơn " + hoaDon.getMaHoaDon() + " đã tồn tại, không thêm.");
        }
    }

    public boolean kiemTraMaHoaDon(String maHoaDon) {
        for (HoaDon hoaDon : danhSachHoaDons) {
            if (hoaDon.getMaHoaDon().equalsIgnoreCase(maHoaDon)) {
                return true;
            }
        }
        return false;
    }

    public void inDanhSach() { // Được gọi từ in()
        System.out.println("\n=======================================================");
        System.out.println("               DANH SÁCH HÓA ĐƠN               ");
        System.out.println("Tổng số hóa đơn: " + danhSachHoaDons.length);
        System.out.println("=======================================================");

        for (int i = 0; i < danhSachHoaDons.length; ++i) {
            danhSachHoaDons[i].xuat();
        }
    }

    public void suaThongTinTheoMa(String maHoaDon) { // Được gọi từ sua()
        Scanner sc = new Scanner(System.in);
        int viTriSua = -1;

        for (int i = 0; i < danhSachHoaDons.length; ++i) {
            if (danhSachHoaDons[i].getMaHoaDon().equalsIgnoreCase(maHoaDon)) {
                viTriSua = i;
                break;
            }
        }

        if (viTriSua == -1) {
            System.out.println(" Không tìm thấy hóa đơn với mã: " + maHoaDon);
            return;
        }

        HoaDon hoaDon = new HoaDon(danhSachHoaDons[viTriSua]);

        System.out.println("--- SỬA HÓA ĐƠN: " + maHoaDon + " ---");
        System.out.print("Nhập mã nhân viên mới (Enter để bỏ qua): ");
        String maNhanVienMoi = sc.nextLine().trim();
        if (!maNhanVienMoi.isEmpty())
            hoaDon.setMaNhanVien(maNhanVienMoi);

        System.out.print("Nhập mã khách hàng mới (Enter để bỏ qua): ");
        String maKhachHangMoi = sc.nextLine().trim();
        if (!maKhachHangMoi.isEmpty())
            hoaDon.setMaKhachHang(maKhachHangMoi);

        System.out.print("Nhập ngày tạo hóa đơn mới dd/MM/yyyy (Enter để bỏ qua): ");
        String ngayTaoMoi = sc.nextLine().trim();
        if (!ngayTaoMoi.isEmpty()) {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            try {
                hoaDon.setNgayTaoHoaDon(date.parse(ngayTaoMoi));
            } catch (ParseException e) {
                System.err.println("Lỗi định dạng ngày. Ngày tạo không được cập nhật.");
            }
        }

        System.out.println("\n--- CẬP NHẬT CHI TIẾT HÓA ĐƠN ---");
        hoaDon.getDanhSachChiTietHoaDon().them(hoaDon.getMaHoaDon());

        hoaDon.getTongGiaHoaDon();

        danhSachHoaDons[viTriSua] = hoaDon;
        System.out.println(" Thông tin hóa đơn đã được cập nhật:");
        hoaDon.xuat();
    }

    public void xoa(String maHoaDon) {
        for (int i = 0; i < danhSachHoaDons.length; ++i) {
            if (danhSachHoaDons[i].getMaHoaDon().equalsIgnoreCase(maHoaDon)) {
                System.arraycopy(danhSachHoaDons, i + 1, danhSachHoaDons, i, danhSachHoaDons.length - 1 - i);
                danhSachHoaDons = Arrays.copyOf(danhSachHoaDons, danhSachHoaDons.length - 1);
                System.out.println(" Đã xóa hóa đơn có mã: " + maHoaDon);
                return;
            }
        }
        System.err.println(" Không tìm thấy hóa đơn có mã: " + maHoaDon);
    }

    public HoaDon timTheoMaHoaDon(String maHoaDon) {
        for (HoaDon hoaDon : danhSachHoaDons) {
            if (hoaDon.getMaHoaDon().equalsIgnoreCase(maHoaDon)) {
                return hoaDon;
            }
        }
        return null;
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
                        for (int i = 0; i < viTriNV.length; ++i) {
                            danhSachHoaDons[viTriNV[i]].xuat();
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
                        for (int i = 0; i < viTriKH.length; ++i) {
                            danhSachHoaDons[viTriKH[i]].xuat();
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
        } while (tiepTuc == 1);
    }

    public int[] timTheoMaNhanVien(String maNhanVien) {
        int[] viTri = new int[0];
        for (int i = 0; i < danhSachHoaDons.length; ++i) {
            if (danhSachHoaDons[i].getMaNhanVien().toLowerCase().contains(maNhanVien.toLowerCase())) {
                viTri = Arrays.copyOf(viTri, viTri.length + 1);
                viTri[viTri.length - 1] = i;
            }
        }
        return viTri;
    }

    public int[] timTheoMaKhachHang(String maKhachHang) {
        int[] viTri = new int[0];
        for (int i = 0; i < danhSachHoaDons.length; ++i) {
            if (danhSachHoaDons[i].getMaKhachHang().toLowerCase().contains(maKhachHang.toLowerCase())) {
                viTri = Arrays.copyOf(viTri, viTri.length + 1);
                viTri[viTri.length - 1] = i;
            }
        }
        return viTri;
    }

    public void thongKeTheoDoanhThu() {
        DanhSachSanPham danhSachSP = new DanhSachSanPham();
        SanPham[] dsSP = danhSachSP.getDanhSachSanPham(); // Lấy toàn bộ danh sách sản phẩm
        int soLuongSanPham = danhSachSP.getSoLuongSanPham();
        long[] doanhThu = new long[soLuongSanPham];

        System.out.println("\n=== THỐNG KÊ DOANH THU THEO SẢN PHẨM ===");

        for (int i = 0; i < soLuongSanPham; ++i) {
            SanPham sp = dsSP[i]; // Lấy từng sản phẩm

            for (int j = 0; j < danhSachHoaDons.length; ++j) {
                DanhSachChiTietHoaDon danhSachChiTietHoaDon = danhSachHoaDons[j].getDanhSachChiTietHoaDon();

                for (int j2 = 0; j2 < danhSachChiTietHoaDon.getSoluong(); ++j2) {
                    ChiTietHoaDon cthd = danhSachChiTietHoaDon.viTri(j2);
                    if (sp.getMaSP().equals(cthd.getMaSanPham())) {
                        doanhThu[i] += cthd.getTongGia();
                    }
                }
            }
            System.out.printf("%-30s | Doanh thu: %,d VND\n", sp.getTenSP(), doanhThu[i]);
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

        for (HoaDon hoaDon : danhSachHoaDons) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(hoaDon.getNgayTaoHoaDon());
            int hoaDonYear = cal.get(Calendar.YEAR);
            int hoaDonMonth = cal.get(Calendar.MONTH) + 1;

            if (hoaDonYear == year) {
                int quy = (hoaDonMonth - 1) / 3;

                doanhThuQuy[quy] += hoaDon.getTongGiaHoaDon();

                DanhSachChiTietHoaDon danhSachChiTietHoaDon = hoaDon.getDanhSachChiTietHoaDon();
                for (int k = 0; k < danhSachChiTietHoaDon.getSoluong(); ++k) {
                    soLuongQuy[quy] += danhSachChiTietHoaDon.viTri(k).getSoLuongSP();
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
            for (HoaDon hoaDon : danhSachHoaDons) {
                Date ngayTao = null;
                try {
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
        if (danhSachHoaDons.length == 0) {
            System.out.println("Không có hóa đơn để thống kê.");
            return;
        }

        Calendar calMin = Calendar.getInstance();
        calMin.setTime(danhSachHoaDons[0].getNgayTaoHoaDon());
        int minYear = calMin.get(Calendar.YEAR);

        for (HoaDon hoaDon : danhSachHoaDons) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(hoaDon.getNgayTaoHoaDon());
            if (cal.get(Calendar.YEAR) < minYear) {
                minYear = cal.get(Calendar.YEAR);
            }
        }

        Calendar calCurrent = Calendar.getInstance();
        int currentYear = calCurrent.get(Calendar.YEAR);

        System.out.println("\n=== THỐNG KÊ DOANH THU THEO NĂM (Từ " + minYear + " đến " + currentYear + ") ===");

        for (int year = minYear; year <= currentYear; year++) {

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