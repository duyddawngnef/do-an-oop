package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.nhaphang.ChiTietPNH;

public class DanhSachChiTietPNH implements isList {

    private ChiTietPNH[] danhSachChiTiet = new ChiTietPNH[0];
    private int soluong = 0;

    // --- HẰNG SỐ ĐƯỜNG DẪN ĐỘNG BỘ ---
    private static final String FOLDER_CTPNH = "data\\DanhSachChiTietPNH\\";
    // Không thao tác trực tiếp với danh sách sản phẩm tại đây

    // Static Scanner cho toàn bộ chương trình
    private static final Scanner sc = new Scanner(System.in);

    public DanhSachChiTietPNH() {
    }

    public int getSoluong() {
        return this.soluong;
    }

    public ChiTietPNH viTri(int n) {
        if (n >= 0 && n < this.soluong) {
            return this.danhSachChiTiet[n];
        }
        return null;
    }

    public double getTongTien() {
        double tong = 0;
        for (int i = 0; i < this.soluong; ++i) {
            tong += this.danhSachChiTiet[i].thanhTien();
        }
        return tong;
    }

    // Tái sử dụng hàm thêm nội bộ
    public void them(ChiTietPNH chiTiet) {
        this.danhSachChiTiet = Arrays.copyOf(this.danhSachChiTiet, this.danhSachChiTiet.length + 1);
        this.danhSachChiTiet[this.soluong] = chiTiet;
        ++this.soluong;
    }

    private boolean kiemTraMaSPDaThem(String maSP) {
        return timTheoMaSP(maSP) == null;
    }

    public ChiTietPNH timTheoMaSP(String ma) {
        for (int i = 0; i < this.soluong; ++i) {
            if (ma.equalsIgnoreCase(this.danhSachChiTiet[i].getMaSP())) {
                return this.danhSachChiTiet[i];
            }
        }
        return null;
    }

    // Bỏ toàn bộ logic tồn kho khỏi lớp danh sách chi tiết PNH

    // ********* GHI FILE *********
    @Override
    public void write(String maPhieu) {
        // Đảm bảo thư mục tồn tại
        File folder = new File(FOLDER_CTPNH);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = FOLDER_CTPNH + maPhieu + ".txt";

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, false))) {
            for (int i = 0; i < this.soluong; ++i) {
                // Định dạng file: MaSP;SoLuong;DonGia;ThanhTien
                myWriter.write(this.danhSachChiTiet[i].toString());
                myWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("  Lỗi khi ghi chi tiết phiếu nhập vào file " + fileName + ": " + e.getMessage());
        }
    }

    // ********* ĐỌC FILE *********
    @Override
    public void read(String maPhieu) {
        String fileName = FOLDER_CTPNH + maPhieu + ".txt";
        File file = new File(fileName);
        this.danhSachChiTiet = new ChiTietPNH[0];
        this.soluong = 0;

        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");

                if (value.length >= 3) {
                    try {
                        ChiTietPNH chiTiet = new ChiTietPNH();
                        chiTiet.setMaSP(value[0].trim());
                        chiTiet.setSoLuong(Integer.parseInt(value[1].trim()));
                        chiTiet.setDonGia(Double.parseDouble(value[2].trim()));

                        this.them(chiTiet);
                    } catch (NumberFormatException e) {
                        System.err.println(" Lỗi định dạng số khi đọc file chi tiết phiếu nhập trên dòng: " + data);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println(" Cảnh báo: Không tìm thấy file chi tiết phiếu nhập cho mã: " + maPhieu + " (Đường dẫn: "
                    + fileName + ")");
        }
    }

    // ********* THÊM *********
    @Override
    public void them() {
        System.out.print("Nhập mã phiếu nhập đang thao tác để gắn vào chi tiết: ");
        String maPhieu = sc.nextLine().trim();
        this.them(maPhieu);
    }

    public void them(String maPhieu) {
        int tiepTuc = 1;

        do {
            System.out.println("\n=== THÊM CHI TIẾT PHIẾU NHẬP ===");
            
            System.out.print("Nhập mã sản phẩm: ");
            String maSP = sc.nextLine().trim();

            if (!this.kiemTraMaSPDaThem(maSP)) {
                System.err.println("Lỗi: Sản phẩm này đã tồn tại trong chi tiết phiếu nhập hiện tại!");
            } else {
                int soLuongNhap = -1;
                do {
                    System.out.print("Nhập số lượng nhập: ");
                    try {
                        soLuongNhap = Integer.parseInt(sc.nextLine().trim());
                        if (soLuongNhap <= 0) {
                            System.err.println("Số lượng phải lớn hơn 0.");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi: Vui lòng nhập số nguyên.");
                    }
                } while (soLuongNhap <= 0);

                double donGiaNhap = -1;
                do {
                    System.out.print("Nhập đơn giá nhập: ");
                    try {
                        donGiaNhap = Double.parseDouble(sc.nextLine().trim());
                        if (donGiaNhap <= 0) {
                            System.err.println("Đơn giá phải lớn hơn 0.");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi: Vui lòng nhập số hợp lệ.");
                    }
                } while (donGiaNhap <= 0);

                // Tạo chi tiết
                ChiTietPNH ctpnh = new ChiTietPNH(maSP, soLuongNhap, donGiaNhap);
                
                this.them(ctpnh);
                System.out.println("  Thêm chi tiết phiếu nhập thành công!");
            }

            System.out.print("Bạn có muốn nhập chi tiết phiếu nhập tiếp không (1: Có / 0: Dừng lại!)? ");
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
        System.out.print("Nhập mã sản phẩm cần xóa khỏi chi tiết phiếu nhập: ");
        String maSP = sc.nextLine().trim();
        this.xoa(maSP);
    }

    public void xoa(String maSP) {
        if (this.soluong == 0) {
            System.out.println("Danh sách chi tiết phiếu nhập trống!");
            return;
        }

        for (int i = 0; i < this.soluong; ++i) {
            if (maSP.equalsIgnoreCase(this.danhSachChiTiet[i].getMaSP())) {

                System.arraycopy(this.danhSachChiTiet, i + 1, this.danhSachChiTiet, i, this.soluong - i - 1);
                --this.soluong;
                this.danhSachChiTiet = Arrays.copyOf(this.danhSachChiTiet, this.soluong);
                System.out.println("  Đã xóa chi tiết sản phẩm có mã: " + maSP);
                return;
            }
        }
        System.out.println(" Không tìm thấy sản phẩm có mã: " + maSP + " để xóa.");
    }

    // ********* SỬA *********
    @Override
    public void sua() {
        System.out.print("Nhập mã sản phẩm cần sửa số lượng trong chi tiết phiếu nhập: ");
        String maSP = sc.nextLine().trim();

        ChiTietPNH ctpnh = timTheoMaSP(maSP);
        if (ctpnh == null) {
            System.out.println(" Không tìm thấy sản phẩm có mã " + maSP + " trong chi tiết phiếu nhập này.");
            return;
        }

        this.suaSoLuong(ctpnh);
    }

    public void suaSoLuong(ChiTietPNH ctpnh) {
        String maSP = ctpnh.getMaSP();
        int soLuongMoi = -1;
        int soLuongCu = ctpnh.getSoLuong();

        // 1. Nhập số lượng mới và kiểm tra
        do {
            System.out.print("Nhập số lượng mới (số lượng cũ: " + soLuongCu + "): ");
            try {
                soLuongMoi = Integer.parseInt(sc.nextLine().trim());
                if (soLuongMoi <= 0) {
                    System.err.println("Số lượng phải lớn hơn 0.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: Vui lòng nhập số nguyên.");
            }
        } while (soLuongMoi <= 0);

        // 2. Cập nhật chi tiết phiếu nhập
        if (soLuongMoi != soLuongCu) {
            ctpnh.setSoLuong(soLuongMoi);
            System.out.println("  Đã cập nhật số lượng cho sản phẩm " + maSP);
        } else {
            System.out.println(" Số lượng không thay đổi, hủy thao tác sửa.");
        }
    }

    @Override
    public void timTheoMa() {
        System.out.print("Nhập mã sản phẩm cần tìm trong chi tiết phiếu nhập: ");
        String maSP = sc.nextLine().trim();

        ChiTietPNH ctpnh = this.timTheoMaSP(maSP);

        if (ctpnh != null) {
            System.out.println("\n  Đã tìm thấy chi tiết sản phẩm:");
            System.out.printf("%-15s | %-15s | %-15s | %-15s\n", 
                    "Mã SP", "Số Lượng", "Đơn Giá", "Thành Tiền");
            System.out.println("-".repeat(70));
            System.out.printf("%-15s | %-15d | %-15.0f | %-15.0f\n",
                    ctpnh.getMaSP(), ctpnh.getSoLuong(), ctpnh.getDonGia(), ctpnh.thanhTien());
        } else {
            System.out.println("Không tìm thấy sản phẩm có mã " + maSP + " trong chi tiết phiếu nhập này.");
        }
    }

    @Override
    public void in() {
        this.xuat();
    }

    public void xuat() {
        if (this.soluong > 0) {
            System.out.println("\n========== CHI TIẾT PHIẾU NHẬP HÀNG ==========");
            System.out.println("Tổng số mục hàng: " + this.soluong);
            System.out.printf("%-15s | %-15s | %-15s | %-15s\n", 
                    "Mã SP", "Số Lượng", "Đơn Giá", "Thành Tiền");
            System.out.println("-".repeat(70));
            
            for (int i = 0; i < this.soluong; ++i) {
                ChiTietPNH ct = this.danhSachChiTiet[i];
                System.out.printf("%-15s | %-15d | %-15.0f | %-15.0f\n",
                        ct.getMaSP(), ct.getSoLuong(), ct.getDonGia(), ct.thanhTien());
            }
            System.out.println("=".repeat(70));
            System.out.printf("TỔNG GIÁ TRỊ CÁC MỤC HÀNG: %,.0f VND\n", this.getTongTien());
        } else {
            System.out.println("\n Danh sách chi tiết phiếu nhập trống!");
        }
    }

    public void suaThongTinTheoMa(String maSP) {
        ChiTietPNH ctpnh = timTheoMaSP(maSP);
        if (ctpnh != null) {
            suaSoLuong(ctpnh);
        } else {
            System.out.println("Không tìm thấy sản phẩm có mã " + maSP + " trong chi tiết phiếu nhập này.");
        }
    }

    public void traCuuThongTinChiTiet() {
        this.timTheoMa();
    }

    /**
     * Đọc TẤT CẢ các file chi tiết phiếu nhập trong thư mục FOLDER_CTPNH
     * và load vào danh sách hiện tại
     */
    public void readAll() {
        File folder = new File(FOLDER_CTPNH);

        // Kiểm tra thư mục có tồn tại không
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println(" Thư mục chi tiết phiếu nhập không tồn tại: " + FOLDER_CTPNH);
            return;
        }

        // Reset danh sách trước khi đọc
        this.danhSachChiTiet = new ChiTietPNH[0];
        this.soluong = 0;

        // Lấy tất cả file .txt trong thư mục
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println(" Không có file chi tiết phiếu nhập nào trong thư mục.");
            return;
        }

        int soFileDoc = 0;
        System.out.println("\n Đang đọc " + files.length + " file chi tiết phiếu nhập...");

        // Đọc từng file
        for (File file : files) {
            try (Scanner myReader = new Scanner(file)) {
                // Lấy mã phiếu nhập từ tên file (bỏ đuôi .txt)
                String maPhieu = file.getName().replace(".txt", "");

                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] value = data.split(";");

                    if (value.length >= 3) {
                        try {
                            ChiTietPNH chiTiet = new ChiTietPNH();
                            chiTiet.setMaSP(value[0].trim());
                            chiTiet.setSoLuong(Integer.parseInt(value[1].trim()));
                            chiTiet.setDonGia(Double.parseDouble(value[2].trim()));

                            this.them(chiTiet);
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

        System.out.println("  Đã đọc thành công " + soFileDoc + " file, tổng " + this.soluong + " chi tiết phiếu nhập.");
    }
}