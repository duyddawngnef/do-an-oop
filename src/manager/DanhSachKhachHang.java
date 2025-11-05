package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.banhang.KhachHang;

public class DanhSachKhachHang implements isList {
    private static KhachHang[] danhSachKhachHang = new KhachHang[0];

    // -------------------- Các hàm tiện ích --------------------
    public int getSoLuong() {
        return danhSachKhachHang.length;
    }

    public int viTriKhachHangTrongDanhSach(String maKhachHang) {
        for (int i = 0; i < danhSachKhachHang.length; ++i) {
            if (danhSachKhachHang[i].getMaKhachHang().equalsIgnoreCase(maKhachHang.trim())) {
                return i;
            }
        }
        return -1;
    }

    public KhachHang timMaKhachHang(String maKhachHangCanTim) {
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getMaKhachHang().equalsIgnoreCase(maKhachHangCanTim.trim())) {
                return kh;
            }
        }
        return null;
    }

    @Override
    public void read(String fileName) {
        File myFile = new File(fileName);
        danhSachKhachHang = new KhachHang[0]; // reset danh sách trước khi đọc

        try (Scanner myReader = new Scanner(myFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");
                if (value.length >= 5) {
                    KhachHang tmp = new KhachHang();
                    tmp.setMaKhachHang(value[0].trim());
                    tmp.setHoKhachHang(value[1].trim());
                    tmp.setTenKhachHang(value[2].trim());
                    tmp.setDiaChi(value[3].trim());
                    tmp.setSdt(value[4].trim());

                    danhSachKhachHang = Arrays.copyOf(danhSachKhachHang, danhSachKhachHang.length + 1);
                    danhSachKhachHang[danhSachKhachHang.length - 1] = tmp;
                } else {
                    System.out.println(" Dữ liệu không hợp lệ (thiếu cột)");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(" Không tìm thấy file: " + fileName);
        }
    }

    @Override
    public void write(String fileName) {
        try (FileWriter fw = new FileWriter(fileName, false)) {
            for (KhachHang kh : danhSachKhachHang) {
                fw.write(kh.getMaKhachHang() + ";" + kh.getHoKhachHang() + ";" + kh.getTenKhachHang() + ";" +
                        kh.getDiaChi() + ";" + kh.getSdt() + "\n");
            }
        } catch (IOException e) {
            System.out.println(" Lỗi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void them() {
        Scanner sc = new Scanner(System.in);
        KhachHang khachHangMoi = new KhachHang();
        khachHangMoi.nhap();

        if (timMaKhachHang(khachHangMoi.getMaKhachHang()) == null) {
            danhSachKhachHang = Arrays.copyOf(danhSachKhachHang, danhSachKhachHang.length + 1);
            danhSachKhachHang[danhSachKhachHang.length - 1] = khachHangMoi;
            write("data/DanhSachKhachHang.txt");
            System.out.println(" Thêm khách hàng thành công!");
        } else {
            System.out.println(" Mã khách hàng đã tồn tại!");
        }
    }

    @Override
    public void xoa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã khách hàng cần xóa: ");
        String maKhachHang = sc.nextLine();

        int viTri = viTriKhachHangTrongDanhSach(maKhachHang);
        if (viTri != -1) {
            for (int i = viTri; i < danhSachKhachHang.length - 1; ++i) {
                danhSachKhachHang[i] = danhSachKhachHang[i + 1];
            }
            danhSachKhachHang = Arrays.copyOf(danhSachKhachHang, danhSachKhachHang.length - 1);
            write("data/DanhSachKhachHang.txt");
            System.out.println(" Xóa khách hàng thành công!");
        } else {
            System.out.println(" Không tìm thấy khách hàng có mã: " + maKhachHang);
        }
    }

    @Override
    public void sua() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã khách hàng cần sửa: ");
        String ma = sc.nextLine();

        int viTri = viTriKhachHangTrongDanhSach(ma);
        if (viTri == -1) {
            System.out.println(" Không tồn tại mã khách hàng: " + ma);
            return;
        }

        KhachHang khMoi = new KhachHang();
        khMoi.nhap();

        // Kiểm tra trùng mã
        for (int i = 0; i < danhSachKhachHang.length; ++i) {
            if (i != viTri && khMoi.getMaKhachHang().equalsIgnoreCase(danhSachKhachHang[i].getMaKhachHang())) {
                System.out.println(" Mã khách hàng đã tồn tại!");
                return;
            }
        }

        danhSachKhachHang[viTri] = khMoi;
        write("data/DanhSachKhachHang.txt");
        System.out.println(" Sửa thông tin thành công!");
    }

    @Override
    public void in() {
        if (danhSachKhachHang.length == 0) {
            System.out.println(" Danh sách khách hàng trống!");
        } else {
            for (KhachHang kh : danhSachKhachHang) {
                kh.xuat();
                System.out.println("--------------------");
            }
        }
    }

    @Override
    public void timTheoMa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã khách hàng cần tìm: ");
        String ma = sc.nextLine();

        KhachHang kh = timMaKhachHang(ma);
        if (kh != null) {
            kh.xuat();
        } else {
            System.out.println(" Không tìm thấy khách hàng có mã: " + ma);
        }
    }
}