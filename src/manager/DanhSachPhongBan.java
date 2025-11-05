package manager;

import Interface.isList;
import model.nhansu.PhongBan;
import model.nhansu.NhanVien;
import utils.TienUtil;
import java.io.*;
import java.util.*;

public class DanhSachPhongBan implements isList {
    private static PhongBan[] danhSachPhongBan = new PhongBan[0];
    private static Scanner sc = new Scanner(System.in);

    public DanhSachPhongBan() {
    }

    public PhongBan[] getDanhSachPhongBan() {
        return danhSachPhongBan;
    }

    public boolean tonTaiMaPhongBan(String maPB) {
        for (PhongBan pb : danhSachPhongBan) {
            if (pb != null && pb.getMaPB().equalsIgnoreCase(maPB)) {
                return true;
            }
        }
        return false;
    }

    public PhongBan timTheoMa(String maPB) {
        for (PhongBan pb : danhSachPhongBan) {
            if (pb.getMaPB().equalsIgnoreCase(maPB)) {
                return pb;
            }
        }
        return null;
    }

    public void them(PhongBan pb) {
        if (timTheoMa(pb.getMaPB()) != null) {
            System.err.println("Mã phòng ban đã tồn tại: " + pb.getMaPB());
            return;
        }
        danhSachPhongBan = Arrays.copyOf(danhSachPhongBan, danhSachPhongBan.length + 1);
        danhSachPhongBan[danhSachPhongBan.length - 1] = pb;
    }

    // ================== IMPLEMENT isList ==================
    @Override
    public void read(String fileName) {
        File myFile = new File(fileName);
        int count = 0;
        try (Scanner myReader = new Scanner(myFile)) {
            danhSachPhongBan = new PhongBan[0];
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");
                if (value.length < 2)
                    continue;

                String maPB = value[0].trim();
                String tenPB = value[1].trim();
                PhongBan pb = new PhongBan(maPB, tenPB);
                them(pb);
                count++;
            }
            System.out.println("Tải dữ liệu phòng ban thành công (" + count + " PB).");
        } catch (FileNotFoundException e) {
            System.err.println("File dữ liệu phòng ban không tồn tại: " + fileName);
        }
    }

    @Override
    public void write(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (PhongBan pb : danhSachPhongBan) {
                writer.println(pb.getMaPB() + ";" + pb.getTenPB());
            }
            System.out.println("Ghi file phòng ban thành công (" + danhSachPhongBan.length + " PB).");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file phòng ban: " + e.getMessage());
        }
    }

    @Override
    public void them() {
        String maPB, tenPB;
        do {
            System.out.println("\n===== NHẬP THÔNG TIN PHÒNG BAN MỚI =====");

            // Nhập mã PB
            while (true) {
                System.out.print("Nhập mã phòng ban: ");
                maPB = sc.nextLine().trim().toUpperCase();

                if (!maPB.matches("^[^0-9]+$")) {
                    System.err.println("Mã PB không được chứa số! Nhập lại.");
                    continue;
                }
                if (timTheoMa(maPB) != null) {
                    System.err.println("Mã phòng ban đã tồn tại! Nhập lại.");
                    continue;
                }
                break;
            }

            // Nhập tên PB
            while (true) {
                System.out.print("Nhập tên phòng ban: ");
                tenPB = sc.nextLine().trim();
                if (!tenPB.matches("^[^0-9]+$")) {
                    System.err.println("Tên PB không được chứa số! Nhập lại.");
                    continue;
                }
                break;
            }

            them(new PhongBan(maPB, tenPB));
            System.out.println("Thêm phòng ban thành công!");

            System.out.print("Nhập tiếp? (1: Có / 0: Không): ");
            String input = sc.nextLine().trim();
            if (input.equals("0"))
                break;
        } while (true);
    }

    @Override
    public void xoa() {
        if (danhSachPhongBan.length == 0) {
            System.out.println("Danh sách phòng ban trống!");
            return;
        }

        System.out.print("Nhập mã phòng ban cần xóa: ");
        String ma = sc.nextLine().trim().toUpperCase();
        int viTri = -1;

        for (int i = 0; i < danhSachPhongBan.length; i++) {
            if (danhSachPhongBan[i].getMaPB().equals(ma)) {
                viTri = i;
                break;
            }
        }

        if (viTri == -1) {
            System.out.println("Không tìm thấy phòng ban có mã: " + ma);
            return;
        }

        PhongBan[] temp = new PhongBan[danhSachPhongBan.length - 1];
        for (int i = 0, j = 0; i < danhSachPhongBan.length; i++) {
            if (i != viTri)
                temp[j++] = danhSachPhongBan[i];
        }
        danhSachPhongBan = temp;
        System.out.println("✅ Đã xóa phòng ban có mã: " + ma);
    }

    @Override
    public void sua() {
        System.out.print("Nhập mã phòng ban cần sửa: ");
        String ma = sc.nextLine().trim();
        PhongBan pb = timTheoMa(ma);

        if (pb == null) {
            System.out.println("Không tìm thấy phòng ban có mã đó.");
            return;
        }

        System.out.printf("%-10s | %-25s\n", "Mã PB", "Tên Phòng Ban");
        System.out.println("------------------------------------");
        System.out.printf("%-10s | %-25s\n", pb.getMaPB(), pb.getTenPB());

        System.out.print("\nNhập tên phòng ban mới (để trống để giữ nguyên): ");
        String tenMoi = sc.nextLine().trim();

        if (!tenMoi.isEmpty()) {
            pb.setTenPB(tenMoi);
            System.out.println("✅ Đã sửa tên phòng ban thành: " + tenMoi);
        } else {
            System.out.println("Không có thay đổi nào.");
        }
    }

    @Override
    public void timTheoMa() {
        System.out.print("Nhập mã phòng ban cần tìm: ");
        String ma = sc.nextLine();
        PhongBan pb = timTheoMa(ma);
        if (pb != null) {
            System.out.println(" Tìm thấy phòng ban:");
            System.out.printf("%-10s | %-25s\n", "Mã PB", "Tên Phòng Ban");
            System.out.println("----------------------------------");
            System.out.printf("%-10s | %-25s\n", pb.getMaPB(), pb.getTenPB());
        } else {
            System.out.println("Không tìm thấy phòng ban có mã: " + ma);
        }
    }

    @Override
    public void in() {
        if (danhSachPhongBan.length == 0) {
            System.out.println("Danh sách phòng ban trống!");
            return;
        }

        System.out.println("\n===== DANH SÁCH PHÒNG BAN =====");
        System.out.printf("%-10s | %-30s\n", "Mã PB", "Tên Phòng Ban");
        System.out.println("----------------------------------------");

        for (PhongBan pb : danhSachPhongBan) {
            System.out.printf("%-10s | %-30s\n", pb.getMaPB(), pb.getTenPB());
        }
    }

    // =================== THỐNG KÊ ===================

    public void thongKeSoLuongNV(NhanVien[] danhSachNV) {
        if (danhSachPhongBan.length == 0 || danhSachNV == null || danhSachNV.length == 0) {
            System.out.println("Không có dữ liệu để thống kê!");
            return;
        }

        System.out.println("\n===== THỐNG KÊ SỐ LƯỢNG NHÂN VIÊN THEO PHÒNG =====");
        System.out.printf("%-10s | %-30s | %-10s\n", "Mã PB", "Tên Phòng Ban", "Số NV");
        System.out.println("----------------------------------------------------");

        for (PhongBan pb : danhSachPhongBan) {
            int dem = 0;
            for (NhanVien nv : danhSachNV) {
                if (nv != null && nv.getMaPhongBan().equalsIgnoreCase(pb.getMaPB())) {
                    dem++;
                }
            }
            System.out.printf("%-10s | %-30s | %-10d\n", pb.getMaPB(), pb.getTenPB(), dem);
        }
    }

    public void thongKeLuongTheoPhong(NhanVien[] danhSachNV) {
        if (danhSachPhongBan.length == 0 || danhSachNV == null || danhSachNV.length == 0) {
            System.out.println("⚠️ Không có dữ liệu để thống kê!");
            return;
        }

        System.out.println("\n===== THỐNG KÊ LƯƠNG THEO PHÒNG BAN =====");
        System.out.printf("%-10s | %-30s | %-20s | %-20s\n",
                "Mã PB", "Tên Phòng Ban", "Tổng Lương", "TB Lương");
        System.out.println("-----------------------------------------------------------------------");

        for (PhongBan pb : danhSachPhongBan) {
            long tong = 0;
            int dem = 0;
            for (NhanVien nv : danhSachNV) {
                if (nv != null && nv.getMaPhongBan().equalsIgnoreCase(pb.getMaPB())) {
                    try {
                        tong += Long.parseLong(nv.getLuong().replace(".", "").trim());
                        dem++;
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi định dạng lương nhân viên: " + nv.getMaNhanVien());
                    }
                }
            }
            long tb;
            if (dem > 0) {
                tb = tong / dem;
            } else {
                tb = 0;
            }
            System.out.printf("%-10s | %-30s | %-20s | %-20s\n",
                    pb.getMaPB(), pb.getTenPB(),
                    TienUtil.dinhDangTien(tong),
                    TienUtil.dinhDangTien(tb));
        }
    }

    public static void menuThongKe() {
        DanhSachPhongBan dspb = new DanhSachPhongBan();
        DanhSachNhanVien dsnv = new DanhSachNhanVien();

        dspb.read("do-an-oop-main/data/DanhSachPhongBan.txt");
        NhanVien[] dsNV = dsnv.getDanhSachNhanVien();

        int luaChon;
        do {
            System.out.println("\n||======================================================||");
            System.out.println("||            MENU THỐNG KÊ PHÒNG BAN                   ||");
            System.out.println("||======================================================||");
            System.out.println("|| 1. Thống kê số lượng nhân viên theo phòng            ||");
            System.out.println("|| 2. Thống kê tổng lương & trung bình lương theo phòng ||");
            System.out.println("|| 0. Thoát                                             ||");
            System.out.println("||======================================================||");
            System.out.print("Chọn: ");

            try {
                luaChon = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                luaChon = -1;
            }

            switch (luaChon) {
                case 1:
                    dspb.thongKeSoLuongNV(dsNV);
                    break;
                case 2:
                    dspb.thongKeLuongTheoPhong(dsNV);
                    break;
                case 0:
                    System.out.println("Quay lại menu chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
            }
        } while (luaChon != 0);
    }
}