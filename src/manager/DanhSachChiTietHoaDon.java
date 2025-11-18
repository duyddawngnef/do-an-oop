package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.banhang.ChiTietHoaDon;

// Triển khai interface isList
public class DanhSachChiTietHoaDon implements isList {

    private ChiTietHoaDon[] danhSachCTHD = new ChiTietHoaDon[0];
    private int soluong = 0;

    // --- HẰNG SỐ ĐƯỜNG DẪN ĐỒNG BỘ ---

    private static final String FOLDER_CTHD = "data\\DanhSachChiTietHoaDon\\";
    // Không còn thao tác trực tiếp với danh sách sản phẩm tại đây

    // Static Scanner cho toàn bộ chương trình
    private static final Scanner sc = new Scanner(System.in);

    public DanhSachChiTietHoaDon() {
    }

    public int getSoluong() {
        return this.soluong;
    }

    public ChiTietHoaDon viTri(int n) {
        if (n >= 0 && n < this.soluong) {
            return this.danhSachCTHD[n];
        }
        return null;
    }

    public long getTongGia() {
        long tong = 0;
        for (int i = 0; i < this.soluong; ++i) {
            tong += this.danhSachCTHD[i].getTongGia();
        }
        return tong;
    }

    // Tái sử dụng hàm thêm nội bộ
    public void them(ChiTietHoaDon chiTietHoaDon) {
        this.danhSachCTHD = Arrays.copyOf(this.danhSachCTHD, this.danhSachCTHD.length + 1);
        this.danhSachCTHD[this.soluong] = chiTietHoaDon;
        ++this.soluong;
    }

    private boolean kiemTraMaSPDaThem(String maSP) {
        return timTheoMaSP(maSP) == null;
    }

    public ChiTietHoaDon timTheoMaSP(String ma) {
        for (int i = 0; i < this.soluong; ++i) {
            if (ma.equalsIgnoreCase(this.danhSachCTHD[i].getMaSanPham())) {
                return this.danhSachCTHD[i];
            }
        }
        return null;
    }

    // Bỏ mọi logic tồn kho khỏi lớp danh sách chi tiết hóa đơn

    // ********* GHI FILE *********
    @Override
    public void write(String maHoaDon) { // Ghi Chi tiết HD vào file riêng theo mã HD
        // Đảm bảo thư mục tồn tại
        File folder = new File(FOLDER_CTHD);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = FOLDER_CTHD + maHoaDon + ".txt";

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, false))) {
            for (int i = 0; i < this.soluong; ++i) {
                // Định dạng file: MaSP;DonGia;SoLuongSP;TongGia
                String data = this.danhSachCTHD[i].getMaSanPham() + ";" +
                        this.danhSachCTHD[i].getDonGia() + ";" +
                        this.danhSachCTHD[i].getSoLuongSP() + ";" +
                        this.danhSachCTHD[i].getTongGia();

                myWriter.write(data);
                myWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("  Lỗi khi ghi chi tiết hóa đơn vào file " + fileName + ": " + e.getMessage());
        }
    }

    // ********* ĐỌC FILE *********
    @Override
    public void read(String maHoaDon) {
        String fileName = FOLDER_CTHD + maHoaDon + ".txt";
        File file = new File(fileName);
        this.danhSachCTHD = new ChiTietHoaDon[0];
        this.soluong = 0;

        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");

                if (value.length >= 4) {
                    try {
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                        chiTietHoaDon.setMaSanPham(value[0].trim());
                        chiTietHoaDon.setDonGia(Integer.parseInt(value[1].trim()));
                        chiTietHoaDon.setSoLuongSP(Integer.parseInt(value[2].trim()));

                        chiTietHoaDon.setMaHoaDon(maHoaDon);
                        this.them(chiTietHoaDon);
                    } catch (NumberFormatException e) {
                        System.err.println(" Lỗi định dạng số khi đọc file chi tiết Hóa đơn trên dòng: " + data);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println(" Cảnh báo: Không tìm thấy file chi tiết hóa đơn cho mã: " + maHoaDon + " (Đường dẫn: "
                    + fileName + ")");
        }
    }

    // ********* THÊM *********
    @Override
    public void them() {
        System.out.print("Nhập mã hóa đơn đang thao tác để gắn vào chi tiết: ");
        String maHoaDon = sc.nextLine().trim();
        this.them(maHoaDon);
    }

    public void them(String maHoaDon) {
        int tiepTuc = 1;

        do {
            System.out.println("\n=== Thêm Chi Tiết Hóa Đơn ===");
            ChiTietHoaDon cthd = new ChiTietHoaDon();
            cthd.nhap(); // Giả sử hàm nhập tối giản (chỉ lấy MaSP, SoLuong
            String maSP = cthd.getMaSanPham();
            if (!this.kiemTraMaSPDaThem(maSP)) {
                System.err.println("Lỗi: Sản phẩm này đã tồn tại trong chi tiết hóa đơn hiện tại!");
            } else {
                cthd.capNhatTongGia();
                cthd.setMaHoaDon(maHoaDon);
                this.them(cthd);
                System.out.println(" Thêm chi tiết hóa đơn thành công!");
            }
            System.out.print("Bạn có muốn nhập chi tiết hóa đơn tiếp không (1: Có / 0: Dừng lại!)? ");
            try {
                tiepTuc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                tiepTuc = 0;
            }
        } while (tiepTuc == 1);
    }

    // ********* XÓA *********
    @Override
    public void xoa() {
        System.out.print("Nhập mã sản phẩm cần xóa khỏi chi tiết hóa đơn: ");
        String maSP = sc.nextLine().trim();
        this.xoa(maSP);
    }

    public void xoa(String maSP) {
        if (this.soluong == 0) {
            System.out.println("Danh sách chi tiết hóa đơn trống!");
            return;
        }

        for (int i = 0; i < this.soluong; ++i) {
            if (maSP.equalsIgnoreCase(this.danhSachCTHD[i].getMaSanPham())) {

                System.arraycopy(this.danhSachCTHD, i + 1, this.danhSachCTHD, i, this.soluong - i - 1);
                --this.soluong;
                this.danhSachCTHD = Arrays.copyOf(this.danhSachCTHD, this.soluong);
                System.out.println(" Đã xóa chi tiết sản phẩm có mã: " + maSP);
                return;
            }
        }
        System.out.println(" Không tìm thấy sản phẩm có mã: " + maSP + " để xóa.");
    }

    // ********* SỬA *********
    @Override
    public void sua() {
        System.out.print("Nhập mã sản phẩm cần sửa số lượng trong chi tiết hóa đơn: ");
        String maSP = sc.nextLine().trim();

        ChiTietHoaDon cthd = timTheoMaSP(maSP);
        if (cthd == null) {
            System.out.println(" Không tìm thấy sản phẩm có mã " + maSP + " trong chi tiết hóa đơn này.");
            return;
        }

        this.suaSoLuong(cthd);
    }

    public void suaSoLuong(ChiTietHoaDon cthd) {
        String maSP = cthd.getMaSanPham();
        int soLuongMoi = -1;
        int soLuongCu = cthd.getSoLuongSP();

        // 1. Nhập số lượng mới (không thao tác tồn kho tại đây)
        do {
            System.out.print("Nhập số lượng mới bạn muốn sửa: ");
            try {
                soLuongMoi = Integer.parseInt(sc.nextLine().trim());
                if (soLuongMoi <= 0) {
                    System.err.println("Số lượng phải lớn hơn 0.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Vui lòng nhập số nguyên.");
            }
        } while (soLuongMoi <= 0);

        // 2. Cập nhật chi tiết hóa đơn
        if (soLuongMoi != soLuongCu) {
            cthd.setSoLuongSP(soLuongMoi);
            cthd.capNhatTongGia();
            System.out.println(" Đã cập nhật số lượng và tổng giá cho sản phẩm " + maSP);
        } else {
            System.out.println(" Số lượng không thay đổi, hủy thao tác sửa.");
        }
    }

    @Override
    public void timTheoMa() {
        System.out.print("Nhập mã sản phẩm cần tìm trong chi tiết hóa đơn: ");
        String maSP = sc.nextLine().trim();

        ChiTietHoaDon cthd = this.timTheoMaSP(maSP);

        if (cthd != null) {
            System.out.println("\n Đã tìm thấy chi tiết sản phẩm:");
            ChiTietHoaDon.xuatHeader();
            cthd.xuat();
            ChiTietHoaDon.xuatFooter();
        } else {
            System.out.println("Không tìm thấy sản phẩm có mã " + maSP + " trong chi tiết hóa đơn này.");
        }
    }

    @Override
    public void in() {
        this.xuat();
    }

    public void xuat() {
        if (this.soluong > 0) {
            System.out.println("\n========== CHI TIẾT HÓA ĐƠN ==========");
            System.out.println("Tổng số mục hàng: " + this.soluong);
            ChiTietHoaDon.xuatHeader();
            for (int i = 0; i < this.soluong; ++i) {
                this.danhSachCTHD[i].xuat();
            }
            ChiTietHoaDon.xuatFooter();
            System.out.printf("TỔNG GIÁ TRỊ CÁC MỤC HÀNG: %,d VND\n", this.getTongGia());
        } else {
            System.out.println("\n Danh sách chi tiết hóa đơn trống!");
        }
    }

    public void suaThongTinTheoMa(String maSP) {
        ChiTietHoaDon cthd = timTheoMaSP(maSP);
        if (cthd != null) {
            suaSoLuong(cthd);
        } else {
            System.out.println("Không tìm thấy sản phẩm có mã " + maSP + " trong chi tiết hóa đơn này.");
        }
    }

    public void traCuuThongTinChiTietHoaDon() {
        this.timTheoMa();
    }

    /**
     * Đọc TẤT CẢ các file chi tiết hóa đơn trong thư mục FOLDER_CTHD
     * và load vào danh sách hiện tại
     */
    public void readAll() {
        File folder = new File(FOLDER_CTHD);

        // Kiểm tra thư mục có tồn tại không
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println(" Thư mục chi tiết hóa đơn không tồn tại: " + FOLDER_CTHD);
            return;
        }

        // Reset danh sách trước khi đọc
        this.danhSachCTHD = new ChiTietHoaDon[0];
        this.soluong = 0;

        // Lấy tất cả file .txt trong thư mục
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println(" Không có file chi tiết hóa đơn nào trong thư mục.");
            return;
        }

        int soFileDoc = 0;
        System.out.println("\n Đang đọc " + files.length + " file chi tiết hóa đơn...");

        // Đọc từng file
        for (File file : files) {
            try (Scanner myReader = new Scanner(file)) {
                // Lấy mã hóa đơn từ tên file (bỏ đuôi .txt)
                String maHoaDon = file.getName().replace(".txt", "");

                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] value = data.split(";");

                    if (value.length >= 4) {
                        try {
                            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                            chiTietHoaDon.setMaSanPham(value[0].trim());
                            chiTietHoaDon.setDonGia(Integer.parseInt(value[1].trim()));
                            chiTietHoaDon.setSoLuongSP(Integer.parseInt(value[2].trim()));
                            chiTietHoaDon.setMaHoaDon(maHoaDon);

                            this.them(chiTietHoaDon);
                        } catch (NumberFormatException e) {
                            System.err.println(" Lỗi định dạng số trong file " + file.getName() + ": " + data);
                        }
                    }
                }
                soFileDoc++;
            } catch (FileNotFoundException e) {
                System.err.println(" Không thể đọc file: " + file.getName());
            }
        }

        System.out.println(" Đã đọc thành công " + soFileDoc + " file, tổng " + this.soluong + " chi tiết hóa đơn.");
    }
}