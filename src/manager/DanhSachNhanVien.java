package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.nhansu.NhanVien;

public class DanhSachNhanVien implements isList {
    public static NhanVien[] danhSachNhanVien = new NhanVien[0];
    private static Scanner sc = new Scanner(System.in);

    // ================== THÊM NHÂN VIÊN ==================
    public void them(NhanVien nhanVien) {
        if (timTheoMa(nhanVien.getMaNhanVien()) != null) {
            System.out.println("Mã nhân viên đã tồn tại! " + nhanVien.getMaNhanVien());
            return;
        }
        danhSachNhanVien = Arrays.copyOf(danhSachNhanVien, danhSachNhanVien.length + 1);
        danhSachNhanVien[danhSachNhanVien.length - 1] = nhanVien;
    }

    public NhanVien[] getDanhSachNhanVien() {
        return danhSachNhanVien;
    }

    public boolean coNhanVienThuocPhongBan(String maPhongBan) {
        for (NhanVien nv : danhSachNhanVien) {
            if (nv.getMaPhongBan().equalsIgnoreCase(maPhongBan)) {
                return true; // có nhân viên thuộc phòng này
            }
        }
        return false;
    }

    public int getSoLuongNhanVien() {
        return danhSachNhanVien.length;
    }

    @Override
    public void them() {
        do {
            NhanVien nv = new NhanVien();
            nv.nhap();

            if (timTheoMa(nv.getMaNhanVien()) != null) {
                System.out.println("Lỗi : Mã nhân viên " + nv.getMaNhanVien() + " đã tồn tại !");
                continue;
            }

            them(nv);

            DanhSachLogin dsLogin = new DanhSachLogin();
            dsLogin.taoTaiKhoanChoNhanVienMoi();
            String input;
            while (true) {
                System.out.print("Bạn có muốn nhập tiếp không (1: Có / 0: Không): ");
                input = sc.nextLine().trim();

                if (input.matches("[01]")) {
                    break; // chỉ khi nhập 0 hoặc 1 mới thoát khỏi vòng hỏi lại
                } else {
                    System.err.println("Chỉ được nhập 0 hoặc 1! Vui lòng nhập lại.");
                }
            }

            if (input.equals("0")) {
                break; // nếu nhập 0 thì dừng thêm
            }
        } while (true);
    }

    // ================== IN DANH SÁCH ==================
    @Override
    public void in() {
        if (danhSachNhanVien.length == 0) {
            System.out.println("Danh sách nhân viên trống!");
            return;
        }

        // Tiêu đề
        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                "Mã NV", "Họ", "Tên", "Ngày vào làm", "Lương", "Mã PB");
        System.out.println("---------------------------------------------------------------------");

        // In từng nhân viên
        for (NhanVien nv : danhSachNhanVien) {
            nv.xuat(); // xuat() đã in đúng định dạng
        }
    }

    // ================== TÌM THEO MÃ ==================
    public NhanVien timTheoMa(String ma) {
        for (NhanVien nv : danhSachNhanVien) {
            if (nv.getMaNhanVien().equalsIgnoreCase(ma))
                return nv;
        }
        return null;
    }

    public void timTheoTen() {
        System.out.print("Nhập tên cần tìm (gần đúng): ");
        String key = sc.nextLine().toLowerCase();
        boolean found = false;

        System.out.println("\n=== Kết quả tìm kiếm theo tên ===");
        for (NhanVien nv : danhSachNhanVien) {
            if (nv.getTen().toLowerCase().contains(key)) {
                nv.xuat();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy nhân viên nào!");
        }
    }

    @Override
    public void timTheoMa() {
        System.out.print("Nhập mã nhân viên cần tìm: ");
        String ma = sc.nextLine();
        NhanVien nv = timTheoMa(ma);
        if (nv != null) {
            System.out.println(" Tìm thấy nhân viên:");
            nv.xuat();
        } else {
            System.out.println(" Không tìm thấy nhân viên có mã: " + ma);
        }
    }

    // ================== SỬA THÔNG TIN ==================
    public void sua() {
        System.out.print("Nhập mã nhân viên cần sửa: ");
        String ma = sc.nextLine();
        NhanVien nv = timTheoMa(ma);

        if (nv == null) {
            System.out.println(" Không tìm thấy nhân viên có mã đó: ");
            return;
        }
        nv.xuat();
        int chon;
        do {
            System.out.println("\n||============================================||");
            System.out.println("||              MENU SỬA                        ||");
            System.out.println("||============================================||");
            System.out.println("|| 1. Sửa họ                                  ||");
            System.out.println("|| 2. Sửa tên                                 ||");
            System.out.println("|| 3. Sửa lương                               ||");
            System.out.println("|| 4. Sửa ngày làm                            ||");
            System.out.println("|| 5. Sửa phòng ban                           ||");
            System.out.println("|| 0. Thoát                                   ||");
            System.out.println("||============================================||");
            System.out.print("Chọn: ");
            chon = sc.nextInt();
            sc.nextLine();

            switch (chon) {
                case 1:
                    System.out.print("Nhập họ mới: ");
                    String hoMoi = sc.nextLine();
                    if (!hoMoi.isEmpty())
                        nv.setHo(hoMoi);
                    break;
                case 2:
                    System.out.print("Nhập tên mới: ");
                    String tenMoi = sc.nextLine();
                    if (!tenMoi.isEmpty())
                        nv.setTen(tenMoi);
                    break;
                case 3:
                    System.out.print("Nhập lương mới: ");
                    String luongMoi = sc.nextLine();
                    try {
                        Long.parseLong(luongMoi.replace(".", ""));
                        nv.setLuong(luongMoi);
                    } catch (NumberFormatException e) {
                        System.out.println("Lương phải là số nguyên!");
                    }

                    break;
                case 4:
                    System.out.print("Nhập ngày vào làm mới (dd/MM/yyyy): ");
                    String ngayMoi = sc.nextLine();

                    if (!ngayMoi.isEmpty()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        try {
                            LocalDate date = LocalDate.parse(ngayMoi, formatter);
                            nv.setNgayVaoLam(date);
                        } catch (DateTimeParseException e) {
                            System.out.println("Ngày không hợp lệ! Vui lòng nhập đúng định dạng dd/MM/yyyy.");
                        }
                    }
                    break;

                case 5:
                    DanhSachPhongBan dspb = new DanhSachPhongBan();
                    System.out.println("\n===== DANH SÁCH PHÒNG BAN HIỆN CÓ =====");
                    dspb.in();

                    String maMoi;
                    while (true) {
                        System.out.print("\nNhập mã phòng ban mới (chỉ chọn từ danh sách trên): ");
                        maMoi = sc.nextLine().trim().toUpperCase();

                        if (maMoi.isEmpty()) {
                            System.out.println("Mã phòng ban không được để trống!");
                            continue;
                        }

                        if (dspb.tonTaiMaPhongBan(maMoi)) {
                            nv.setMaPhongBan(maMoi);
                            System.out.println("Đã cập nhật phòng ban mới cho nhân viên!");
                            break;
                        } else {
                            System.out.println("Mã phòng ban không tồn tại! Vui lòng nhập lại theo danh sách.");
                        }
                    }
                    break;

                case 0:
                    System.out.println(" Đã lưu thay đổi!");
                    break;
                default:
                    System.out.println(" Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    // read()
    @Override
    public void read(String fileName) {
        File myFile = new File(fileName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int count = 0; // đếm số nhân viên đọc được

        try (Scanner myReader = new Scanner(myFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");
                if (value.length < 6)
                    continue; // bỏ qua dòng không hợp lệ

                NhanVien tmp = new NhanVien();
                tmp.setMaNhanVien(value[0].trim());
                tmp.setHo(value[1].trim());
                tmp.setTen(value[2].trim());

                try {
                    if (!value[3].trim().isEmpty()) {
                        tmp.setNgayVaoLam(LocalDate.parse(value[3].trim(), formatter));
                    }
                } catch (DateTimeParseException e) {
                    System.err.println(" Ngày không hợp lệ trong file: " + value[3]);
                }

                tmp.setLuong(value[4].trim());
                tmp.setMaPhongBan(value[5].trim());
                them(tmp);
                count++;
            }
            new DanhSachLogin().khoiTaoTuDanhSachNhanVien();
            System.out.println(" Đọc file thành công (" + count + " nhân viên).");
        } catch (FileNotFoundException e) {
            System.err.println(" File không tồn tại: " + fileName);
        }
    }

    @Override
    public void write(String fileName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int count = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (NhanVien nv : danhSachNhanVien) {
                String ngay = (nv.getNgayVaoLam() != null) ? nv.getNgayVaoLam().format(formatter) : "";
                String data = nv.getMaNhanVien() + ";" + nv.getHo() + ";" + nv.getTen() + ";" + ngay + ";"
                        + nv.getLuong() + ";" + nv.getMaPhongBan();
                writer.write(data);
                writer.newLine();
                count++;
            }
            System.out.println(" Ghi file thành công (" + count + " nhân viên).");
        } catch (IOException e) {
            System.err.println(" Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void xoa() {
        if (danhSachNhanVien.length == 0) {
            System.out.println(" Danh sách nhân viên trống!");
            return;
        }
        in();

        System.out.print("Nhập mã nhân viên cần xóa: ");
        String ma = sc.nextLine();

        int viTri = -1;
        for (int i = 0; i < danhSachNhanVien.length; i++) {
            if (danhSachNhanVien[i].getMaNhanVien().equalsIgnoreCase(ma)) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.out.println(" Không tìm thấy nhân viên có mã: " + ma);
            return;
        }

        NhanVien[] temp = new NhanVien[danhSachNhanVien.length - 1];
        int index = 0;
        for (int i = 0; i < danhSachNhanVien.length; i++) {
            if (i != viTri) {
                temp[index++] = danhSachNhanVien[i];
            }
        }
        danhSachNhanVien = temp;

        System.out.println(" Đã xóa nhân viên có mã: " + ma);
    }

    // ================== THỐNG KÊ ==================
    public void thongKeTheoMucLuong() {
        String duoi5 = "";
        String tu5den10 = "";
        String tren10 = "";

        int demDuoi5 = 0, demTu5den10 = 0, demTren10 = 0;

        for (NhanVien nv : danhSachNhanVien) {
            try {
                // Loại bỏ dấu '.' rồi chuyển lương về số
                long luong = Long.parseLong(nv.getLuong().replace(".", "").trim());

                if (luong < 5000000) {
                    duoi5 += nv.getMaNhanVien() + "  ";
                    demDuoi5++;
                } else if (luong <= 10000000) {
                    tu5den10 += nv.getMaNhanVien() + "  ";
                    demTu5den10++;
                } else {
                    tren10 += nv.getMaNhanVien() + "  ";
                    demTren10++;
                }

            } catch (NumberFormatException e) {
                System.err.println("Lỗi định dạng lương của nhân viên: " + nv.getMaNhanVien());
            }
        }

        // Xuất kết quả
        System.out.println("\n=============================================");
        System.out.println("       THỐNG KÊ NHÂN VIÊN THEO MỨC LƯƠNG    ");
        System.out.println("=============================================");

        // Dưới 5 triệu
        if (duoi5.isEmpty())
            System.out.println("Dưới 5 triệu : Không có");
        else
            System.out.println("Dưới 5 triệu (" + demDuoi5 + " NV): " + duoi5);

        // Từ 5 đến 10 triệu
        if (tu5den10.isEmpty())
            System.out.println("Từ 5 đến 10 triệu : Không có");
        else
            System.out.println("Từ 5 đến 10 triệu (" + demTu5den10 + " NV): " + tu5den10);

        // Trên 10 triệu
        if (tren10.isEmpty())
            System.out.println("Trên 10 triệu : Không có");
        else
            System.out.println("Trên 10 triệu (" + demTren10 + " NV): " + tren10);

        System.out.println("=============================================");

    }

    public void sapXepNhanVienTheoLuong(boolean tangDan) {
        NhanVien[] temp = Arrays.copyOf(danhSachNhanVien, danhSachNhanVien.length);
        for (int i = 0; i < temp.length - 1; i++) {
            for (int j = i + 1; j < temp.length; j++) {
                int luongI = Integer.parseInt(temp[i].getLuong().replace(".", ""));
                int luongJ = Integer.parseInt(temp[j].getLuong().replace(".", ""));
                if (tangDan) {
                    if (luongI > luongJ) {
                        NhanVien tmp = temp[i];
                        temp[i] = temp[j];
                        temp[j] = tmp;
                    }
                } else {

                    if (luongI < luongJ) {
                        NhanVien tmp = temp[i];
                        temp[i] = temp[j];
                        temp[j] = tmp;
                    }
                }
            }
        }
        String trangThai;
        if (tangDan) {
            trangThai = "TĂNG DẦN";
        } else {
            trangThai = "GIẢM DẦN";
        }

        // In tiêu đề danh sách nhân viên theo lương
        System.out.println();
        System.out.println("===== DANH SÁCH NHÂN VIÊN THEO LƯƠNG " + trangThai + " =====");

        // In tiêu đề các cột
        System.out.printf("%-15s | %-20s%n", "Mã Nhân Viên", "Lương");

        // In dòng phân cách
        System.out.println("----------------------------------------------------");

        for (NhanVien nv : temp) {
            String luongHienThi = nv.getLuong();
            try {
                if (luongHienThi.matches("\\d+")) {
                    long soLuong = Long.parseLong(luongHienThi);
                    luongHienThi = String.format("%,d", soLuong).replace(",", ".") + " VNĐ";
                } else if (!luongHienThi.endsWith("VNĐ")) {
                    luongHienThi += " VNĐ";
                }
            } catch (Exception e) {
                // giữ nguyên nếu lỗi
            }

            System.out.printf("%-15s | %-20s\n", nv.getMaNhanVien(), luongHienThi);
        }
    }

    public void thongKeTheoThamNien() {
        NhanVien[] ds = DanhSachNhanVien.danhSachNhanVien;
        if (ds == null || ds.length == 0) {
            System.out.println("Danh sách nhân viên trống!");
            return;
        }

        System.out.printf("%-15s | %-10s\n", "Mã NV", "Số năm làm");
        System.out.println("-------------------------------");
        String duoi1Nam = "", duoi3Nam = "", duoi5Nam = "", duoi10Nam = "";
        int demDuoi1 = 0, demDuoi3 = 0, demDuoi5 = 0, demDuoi10 = 0;
        for (NhanVien nv : ds) {
            if (nv != null && nv.getNgayVaoLam() != null) {
                try {
                    LocalDate now = LocalDate.now();
                    Period period = Period.between(nv.getNgayVaoLam(), now);
                    int namLam = period.getYears();
                    if (namLam < 1) {
                        duoi1Nam += nv.getMaNhanVien() + "  ";
                        demDuoi1++;
                    } else if (namLam < 3) {
                        duoi3Nam += nv.getMaNhanVien() + "  ";
                        demDuoi3++;
                    } else if (namLam < 5) {
                        duoi5Nam += nv.getMaNhanVien() + "  ";
                        demDuoi5++;
                    } else {
                        duoi10Nam += nv.getMaNhanVien() + "  ";
                        demDuoi10++;
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Lỗi định dạng lương của nhân viên: " + nv.getMaNhanVien());
                }
            }

        }
        System.out.println("\n=============================================");
        System.out.println("       THỐNG KÊ NHÂN VIÊN THEO NĂM LÀM         ");
        System.out.println("===============================================");

        // Dưới 1 năm
        if (duoi1Nam.isEmpty())
            System.out.println("Không có");
        else
            System.out.println("Năm công tác dưới 1 năm (" + demDuoi1 + " NV): " + duoi1Nam);

        // Dưới 3 năm
        if (duoi3Nam.isEmpty())
            System.out.println("Không có");
        else
            System.out.println("Năm công tác dưới 3 năm (" + demDuoi3 + " NV): " + duoi3Nam);

        // dưới 5 năm
        if (duoi5Nam.isEmpty())
            System.out.println("Không có");
        else
            System.out.println("Năm công tác dưới 5 năm (" + demDuoi5 + " NV): " + duoi5Nam);
        // dưới 10 năm
        if (duoi10Nam.isEmpty())
            System.out.println("Không có");
        else
            System.out.println("Năm cô  ng tác dưới 10 năm (" + demDuoi10 + " NV): " + duoi10Nam);

        System.out.println("=============================================");
    }

}