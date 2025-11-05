package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import model.banhang.ChiTietHoaDon;

public class DanhSachChiTietHoaDon {
    private ChiTietHoaDon[] danhSachCTHD = new ChiTietHoaDon[0];
    private int soluong = 0;

    // --- Các phương thức cơ bản ---

    public int getSoluong() {
        return this.soluong;
    }

    public ChiTietHoaDon viTri(int n) {
        if (n >= 0 && n < this.soluong) {
            return this.danhSachCTHD[n];
        }
        return null;
    }

    public int getTongGia() {
        int tongGia = 0;
        for (int i = 0; i < this.soluong; ++i) {
            tongGia += this.danhSachCTHD[i].getTongGia();
        }
        return tongGia;
    }

    public void them(String maHoaDon) {
        Scanner sc = new Scanner(System.in);
        int tiepTuc = 1;

        do {
            System.out.println("\n=== Thêm Chi Tiết Hóa Đơn ===");
            ChiTietHoaDon cthd = new ChiTietHoaDon();

            cthd.nhap();
            if (!cthd.getMaSanPham().isEmpty()) {
                if (this.kiemTraMaSPDaThem(cthd.getMaSanPham())) {

                    this.danhSachCTHD = Arrays.copyOf(this.danhSachCTHD, this.danhSachCTHD.length + 1);
                    cthd.setMaHoaDon(maHoaDon);

                    this.danhSachCTHD[this.soluong] = cthd; // Ghi thẳng đối tượng đã nhập
                    ++this.soluong;
                    System.out.println(" Thêm chi tiết hóa đơn thành công!");
                } else {
                    System.err.println("Lỗi: Sản phẩm này đã tồn tại trong chi tiết hóa đơn hiện tại!");
                }
            } else {
                System.out.println(" Bỏ qua thêm chi tiết vì không nhập được mã sản phẩm hợp lệ.");
            }

            System.out.print("Bạn có muốn nhập chi tiết hóa đơn tiếp không (1: Có / 0: Dừng lại!)? ");
            try {
                tiepTuc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                tiepTuc = 0;
            }
        } while (tiepTuc == 1);
    }

    public void them(ChiTietHoaDon chiTietHoaDon) {
        this.danhSachCTHD = Arrays.copyOf(this.danhSachCTHD, this.danhSachCTHD.length + 1);
        this.danhSachCTHD[this.soluong] = new ChiTietHoaDon(chiTietHoaDon);
        ++this.soluong;
    }

    private boolean kiemTraMaSPDaThem(String maSP) {
        for (int i = 0; i < this.soluong; ++i) {
            if (maSP.equalsIgnoreCase(this.danhSachCTHD[i].getMaSanPham())) {
                return false; // Đã tồn tại
            }
        }
        return true; // Chưa tồn tại
    }

    public void xuat() {
        if (this.soluong > 0) {
            System.out.println("\n=== CHI TIẾT HÓA ĐƠN ===");
            System.out.printf("%-15s%-20s%-12s%-12s%-12s\n", "Mã Hóa Đơn", "Mã Sản Phẩm", "Đơn Giá", "Số Lượng",
                    "Tổng Giá");
            System.out.println("-------------------------------------------------------------------");
            for (int i = 0; i < this.soluong; ++i) {
                this.danhSachCTHD[i].xuat();
            }
            System.out.println("-------------------------------------------------------------------");
            System.out.printf("TỔNG GIÁ TRỊ HÓA ĐƠN: %,d VND\n", this.getTongGia());
        } else {
            System.out.println("\nDanh sách chi tiết hóa đơn trống!");
        }
    }

    public ChiTietHoaDon timTheoMaSP(String ma) {
        for (int i = 0; i < this.soluong; ++i) {
            if (this.danhSachCTHD[i].getMaSanPham().equalsIgnoreCase(ma)) {
                return this.danhSachCTHD[i];
            }
        }
        return null;
    }

    public void traCuuThongTinChiTietHoaDon() {
        Scanner sc = new Scanner(System.in);
        int tiepTuc;

        do {
            System.out.println("\n--- MENU TRA CỨU CHI TIẾT ---");
            System.out.println("1. Tra cứu thông tin theo Mã Sản Phẩm");
            System.out.println("2. Thoát");
            System.out.print("Lựa chọn: ");

            int luaChon = -1;
            try {
                luaChon = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                luaChon = -1;
            }

            switch (luaChon) {
                case 1:
                    System.out.print("Nhập mã sản phẩm cần tra cứu: ");
                    String maSanPham = sc.nextLine().trim();
                    ChiTietHoaDon cthd = this.timTheoMaSP(maSanPham);
                    if (cthd != null) {
                        System.out.println("=== KẾT QUẢ TRA CỨU ===");
                        cthd.xuat();
                    } else {
                        System.out
                                .println("Không tìm thấy sản phẩm có mã " + maSanPham + " trong chi tiết hóa đơn này.");
                    }
                    break;
                case 2:
                    System.out.println("Thoát tra cứu.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại.");
            }

            System.out.print("Bạn có muốn tra cứu tiếp không (1: Có / 0: Dừng lại!)? ");
            try {
                tiepTuc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                tiepTuc = 0;
            }
        } while (tiepTuc == 1);
    }

    public void xoa(String maSP) {
        if (this.soluong == 0) {
            System.out.println("Danh sách chi tiết hóa đơn trống!");
            return;
        }

        for (int i = 0; i < this.soluong; ++i) {
            if (maSP.equalsIgnoreCase(this.danhSachCTHD[i].getMaSanPham())) {
                // Sử dụng System.arraycopy thay thế cho vòng lặp j để xóa hiệu quả hơn
                System.arraycopy(this.danhSachCTHD, i + 1, this.danhSachCTHD, i, this.soluong - i - 1);

                --this.soluong;
                this.danhSachCTHD = Arrays.copyOf(this.danhSachCTHD, this.soluong);
                System.out.println(" Đã xóa chi tiết sản phẩm có mã: " + maSP);
                return;
            }
        }
        System.out.println(" Không tìm thấy sản phẩm có mã: " + maSP + " để xóa.");
    }

    public void suaThongTinTheoMa(String maSP) {
        ChiTietHoaDon cthd = timTheoMaSP(maSP);
        if (cthd == null) {
            System.out.println(" Không tìm thấy sản phẩm có mã " + maSP + " trong chi tiết hóa đơn này.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int soLuongMoi = -1;

        do {
            System.out.print("Nhập số lượng mới bạn muốn sửa: ");
            try {
                soLuongMoi = Integer.parseInt(scanner.nextLine().trim());
                if (soLuongMoi <= 0) {
                    System.err.println("Số lượng phải lớn hơn 0.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Vui lòng nhập số nguyên.");
            }
        } while (soLuongMoi <= 0);

        cthd.setSoLuongSP(soLuongMoi);
        cthd.getTongGia(); // Đảm bảo tổng giá được tính lại
        System.out.println(" Đã cập nhật số lượng và tổng giá cho sản phẩm " + maSP);
    }

    // --- Xử lý File I/O ---

    public void read(String maHoaDon) {
        // Tên file theo cấu trúc: .\\documents\\HoaDon\\Mã_Hóa_Đơn.txt
        String fileName = ".\\documents\\HoaDon\\" + maHoaDon + ".txt";
        File file = new File(fileName);

        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");

                if (value.length >= 4) { // Đảm bảo có đủ 4 trường
                    try {
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                        chiTietHoaDon.setMaSanPham(value[0].trim());
                        chiTietHoaDon.setDonGia(Integer.parseInt(value[1].trim()));
                        chiTietHoaDon.setSoLuongSP(Integer.parseInt(value[2].trim()));

                        chiTietHoaDon.setMaHoaDon(maHoaDon);
                        this.them(chiTietHoaDon);
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi định dạng số khi đọc file chi tiết Hóa đơn trên dòng: " + data);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(" Cảnh báo: Không tìm thấy file chi tiết hóa đơn cho mã: " + maHoaDon);
        }
    }

    public void write(String maHoaDon) {
        String fileName = ".\\data\\DanhSachHoaDon\\" + maHoaDon + ".txt";

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, false))) {
            for (int i = 0; i < this.soluong; ++i) {
                // Ghi dữ liệu: maSP,donGia,soLuongSP,tongGia
                String data = this.danhSachCTHD[i].getMaSanPham() + ";" +
                        this.danhSachCTHD[i].getDonGia() + ";" +
                        this.danhSachCTHD[i].getSoLuongSP() + ";" +
                        this.danhSachCTHD[i].getTongGia();

                myWriter.write(data);
                myWriter.newLine();
            }
            System.out.println(" Ghi chi tiết hóa đơn cho mã " + maHoaDon + " thành công!");
        } catch (IOException e) {
            System.err.println(" Lỗi khi ghi chi tiết hóa đơn vào file " + fileName + ": " + e.getMessage());
        }
    }
}