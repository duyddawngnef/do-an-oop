package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.cauhinh.LoaiLinhKien;
import model.sanpham.LinhKien;
import model.sanpham.SanPham;
public class DanhSachLoaiLinhKien implements isList {
    LoaiLinhKien[] dsLoai = new LoaiLinhKien[0];
    public LoaiLinhKien[] getDanhSachLoaiLinhKien(){
        return dsLoai;
    }
    public DanhSachLoaiLinhKien() {
        this.dsLoai = new LoaiLinhKien[0];
    }

    public int getSL() {
        return dsLoai.length;
    }

    public boolean isEmpty() {
        return dsLoai.length == 0;
    }

    // Đọc file
    @Override
    public void read(String filename) {
        File myFile = new File(filename);
        try (Scanner myReader = new Scanner(myFile)) {

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");

                try {
                    LoaiLinhKien loai = new LoaiLinhKien();
                    loai.setMaLoai(value[0].trim());
                    loai.setTenLoai(value[1].trim());
                    loai.setMoTa(value[2].trim()); // Giả sử MoTa là trường thứ 3
                    dsLoai = Arrays.copyOf(dsLoai, dsLoai.length + 1);
                    dsLoai[dsLoai.length - 1] = loai;
                }
                // Lỗi: thiếu dữ liệu
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Lỗi thiếu dữ liệu trên dòng: " + data);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi : Không tìm thấy file " + filename);
        }
    }

    // Ghi file
    @Override
    public void write(String filename) {
        BufferedWriter myWriter = null;
        try {
            myWriter = new BufferedWriter(new FileWriter(filename));
            for (LoaiLinhKien loai : dsLoai) {
                String dataLine = "";
                dataLine = loai.getMaLoai() + ';' +
                           loai.getTenLoai() + ';' +
                           loai.getMoTa();
                if (!dataLine.isEmpty()) {
                    myWriter.write(dataLine);
                }
                myWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi: Không thể ghi được file " + filename + " !!");
        } finally {
            try {
                if (myWriter != null) {
                    myWriter.close();
                }

            } catch (IOException e) {
                System.out.println("Lỗi : Không thể đóng file " + filename + " !!");
            }
        }
    }

    // Thêm
    @Override
    public void them() {
        Scanner scanner = new Scanner(System.in);
        LoaiLinhKien loai = new LoaiLinhKien();
        boolean timthay = true;
        do {
            System.out.print("\nMời nhập Mã Loại Linh Kiện : ");
            String maloai = scanner.nextLine().trim();
            loai.setMaLoai(maloai);
            timthay = kiemTraMa(maloai);
            if (timthay) {
                System.out.println("Lỗi : Mã loại linh kiện " + loai.getMaLoai() + " đã tồn tại !");
            }
        } while (timthay);

        System.out.print("\nMời nhập Tên Loại Linh Kiện : ");
        String tenloai = scanner.nextLine().trim();
        loai.setTenLoai(tenloai);
        
        System.out.print("\nMời nhập Mô Tả : ");
        String mota = scanner.nextLine().trim();
        loai.setMoTa(mota);
        
        add(loai);
    }

    // Xóa
    @Override
    public void xoa() {
        if (isEmpty()) {
            System.out.println("Danh sách rỗng không có phần tử !!");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nNhập mã loại linh kiện cần xóa : ");
        String maloai = scanner.nextLine().trim();
        LoaiLinhKien loaiXoa = timTheoMa(maloai);
        if (loaiXoa != null) {
            for (int i = 0; i < dsLoai.length; i++) {
                
                if (dsLoai[i].getMaLoai().equalsIgnoreCase(maloai)) {
                    remove(i);
                    break;
                }
            }
        } else {
            System.out.println("\n Không tìm thấy với mã : " + maloai);
        }
    }

    // Sửa
    @Override
    public void sua() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n Nhập mã Loại Linh Kiện cần sửa : ");
        String maloai = scanner.nextLine().trim();
        LoaiLinhKien loaiSua = timTheoMa(maloai);
        if (loaiSua != null) {
            // Giả sử LoaiLinhKien có phương thức xuat()
            System.err.println("\n==THÔNG TIN LOẠI LINH KIỆN HIỆN TẠI ===");
            // LoaiLinhKien cần có phương thức xuat()
            // loaiSua.xuat(); 
            System.out.println("Mã Loại: " + loaiSua.getMaLoai());
            System.out.println("Tên Loại: " + loaiSua.getTenLoai());
            System.out.println("Mô Tả: " + loaiSua.getMoTa());
            
            System.out.println();
            boolean tieptuc = true;
            while (tieptuc) {
                System.out.println("\n === MENU SỬA LOẠI LINH KIỆN ====");
                System.out.println("Chọn thuộc tính cần sửa:");
                System.out.println("1. Tên Loại");
                System.out.println("2. Mô Tả");
                System.out.println("0. Thoát và lưu thay đổi");
                System.out.print("Lựa chọn: ");
                int luaChon = -1;
                try {
                    luaChon = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Vui lòng nhập số nguyên !!!");
                    continue;
                }
                switch (luaChon) {
                    case 0:
                        tieptuc = false;
                        System.out.println("Đã lưu thay đổi!");
                        break;
                    case 1:
                        System.out.print("\nNhập Tên Loại mới : ");
                        String tenMoi = scanner.nextLine().trim();
                        if (!tenMoi.isEmpty()) {
                            loaiSua.setTenLoai(tenMoi);
                            System.out.println("Đã cập nhật Tên Loại!");
                        } else {
                            System.out.println("Tên không được để trống !");
                        }
                        break;
                    case 2:
                        System.out.println("Nhâp Mô Tả mới : ");
                        String moTaMoi = scanner.nextLine().trim();
                        if (!moTaMoi.isEmpty()) {
                            loaiSua.setMoTa(moTaMoi);
                            System.out.println("Đã cập nhật Mô Tả!");
                        } else {
                            System.out.println("Mô Tả không được để trống !");
                        }
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ !");
                        break;
                }
            }
        } else {
            System.out.println("Không tìm thấy Loại Linh Kiện với mã: " + maloai);
        }
    }

    // Tìm theo mã -> in thông tin
    @Override
    public void timTheoMa() {
        if (isEmpty()) {
            System.out.print("Chưa có loại linh kiện nào trong danh sách");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nNhập mã Loại Linh Kiện : ");
        String maloai = scanner.nextLine().trim();
        LoaiLinhKien loai = timTheoMa(maloai);
        if(loai != null){
             // Giả sử LoaiLinhKien có phương thức xuat()
            // loai.xuat(); 
            System.out.printf("%-15s||%-20s||%-30s\n", "Mã Loại", "Tên Loại", "Mô Tả");
            System.out.printf("%-15s||%-20s||%-30s\n", loai.getMaLoai(), loai.getTenLoai(), loai.getMoTa());
        }
        else{
            System.out.println("Không tìm thấy mã Loại Linh Kiện !!!");
        }
    }

    // In ds
    @Override
    public void in() {
        if (isEmpty()) {
            System.out.println("Chưa có loại linh kiện nào trong danh sách");
            return;
        }
        System.out.printf("\n%-15s||%-20s||%-30s\n", "Mã Loại", "Tên Loại", "Mô Tả");
        System.out.println("------------------------------------------------------------------");
        for (LoaiLinhKien loai : dsLoai) {
            // Giả sử LoaiLinhKien có phương thức xuat() hoặc in trực tiếp
            // loai.xuat();
            System.out.printf("%-15s||%-20s||%-30s\n", loai.getMaLoai(), loai.getTenLoai(), loai.getMoTa());
        }
    }
    public void thongKeLoaiPhoBien(DanhSachSanPham dssp){
        if (isEmpty()) {
            System.out.println("Chưa có loại linh kiện nào trong danh sách");
            return;
        }
        int[] count  = new int[getSL()];
        for( SanPham sp : dssp.getDanhSachSanPham()){
            if(sp instanceof LinhKien){
                for(int i = 0 ; i < getSL();i++){
                    if(dsLoai[i].getTenLoai().equalsIgnoreCase(((LinhKien) sp).getLoaiLinhKien())){
                        count[i]++;
                        break;
                    }
                }
            }
        }
        System.out.println("\n=== THỐNG KÊ LOẠI LINH KIỆN PHỔ BIẾN ===");
        System.out.printf("%-15s||%-20s||%-15s\n", "Mã Loại", "Tên Loại", "Số SP Sử Dụng");
        System.out.println("--------------------------------------------------------");
        
        //tìm max
        for (int i = 0; i < dsLoai.length; i++) {
            System.out.printf("%-15s||%-20s||%-15d\n", 
                dsLoai[i].getMaLoai(), 
                dsLoai[i].getTenLoai(), 
                count[i]);
        }
        // loại phổ biến
        int maxIndex = 0;
        for (int i = 1; i < count.length; i++) {
            if (count[i] > count[maxIndex]) {
                maxIndex = i;
            }
        }
        
        if (count[maxIndex] > 0) {
            System.out.println("\n Loại phổ biến nhất: " + dsLoai[maxIndex].getTenLoai() + 
                            " (" + count[maxIndex] + " sản phẩm)");
        }
    }
    public boolean kiemTraMaDangSuDung(String ma , DanhSachSanPham dssp){
        if(dssp == null || isEmpty()){
            return false;
        }
        for(SanPham sp : dssp.getDanhSachSanPham()){
            if(sp instanceof  LinhKien){
                LinhKien lk = (LinhKien)sp;
                if(lk.getLoaiLinhKien().equalsIgnoreCase(ma)){
                    return true;
                }
            }
        }
        return false;
    }
    public void timKiemTheoTen(){
        if (isEmpty()) {
            System.out.println("Chưa có loại linh kiện nào trong danh sách");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nNhập tên loại linh kiện cần tìm: ");
        String tenLoai = scanner.nextLine().trim();

        boolean found = false;
        System.out.printf("%-15s||%-20s||%-30s\n", "Mã Loại", "Tên Loại", "Mô Tả");
        System.out.println("------------------------------------------------------------------");

        for (LoaiLinhKien loai : dsLoai) {
            if (loai.getTenLoai().toLowerCase().contains(tenLoai.toLowerCase())) {
                found = true;
                System.out.printf("%-15s||%-20s||%-30s\n", 
                    loai.getMaLoai(), loai.getTenLoai(), loai.getMoTa());
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy loại linh kiện với tên: " + tenLoai);
        }
    }

    private void add(LoaiLinhKien loai) {
        dsLoai = Arrays.copyOf(dsLoai, dsLoai.length + 1);
        dsLoai[dsLoai.length - 1] = loai;
        System.out.println("Thêm thành công !");
    }

    private boolean kiemTraMa(String maloai) {
        for (LoaiLinhKien loai : dsLoai) {
            if (loai.getMaLoai().equalsIgnoreCase(maloai)) {
                return true;
            }
        }
        return false;
    }

    private LoaiLinhKien timTheoMa(String maloai) {
        for (LoaiLinhKien loai : dsLoai) {
            if (loai.getMaLoai().equalsIgnoreCase(maloai)) {
                return loai;
            }
        }
        return null;
    }

    private void remove(int i) {
        if (i < 0 || i >= dsLoai.length) {
            System.out.println("Vị trí index lỗi !!");
            return;
        }
        int numberRemove = dsLoai.length - i - 1;
        System.arraycopy(dsLoai, i + 1, dsLoai, i, numberRemove);
        dsLoai = Arrays.copyOf(dsLoai, dsLoai.length-1);
        System.out.println("Xóa thành công !");
    } 
}