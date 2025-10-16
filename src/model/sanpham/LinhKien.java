package model.sanpham; 

public class LinhKien extends SanPham {
    private String loaiLinhKien;
    private String thongSoKyThuat;
    
    public LinhKien(){
        loaiLinhKien = "";
        thongSoKyThuat = "";
    }
    
    //thiết lập constructor
    public LinhKien(String maSP , String tenSP , int soLuongTon, int donGiaBan, String loaiLinhKien , String thongSoKyThuat) {
        super(maSP, tenSP, soLuongTon, donGiaBan);
        this.loaiLinhKien = loaiLinhKien;
        this.thongSoKyThuat = thongSoKyThuat;
    }
    
    //thiết lập constructor
    public LinhKien(LinhKien lk){
        super(lk);
        this.loaiLinhKien = lk.loaiLinhKien;
        this.thongSoKyThuat = lk.thongSoKyThuat;
    }
    
    @Override
    //ghi đè hàm nhập(SanPham)
    public void nhap(){
        super.nhap();
        System.out.println("\nMời Nhập Loại Linh Kiện :");
        this.loaiLinhKien = scanner.nextLine();
        System.out.println("\nMời Nhập Thông Số Kỹ Thuật :");
        this.thongSoKyThuat = scanner.nextLine();
    }
    
    @Override
    //ghi đè hàm xuất(SanPham)
    public void xuat(){
        super.xuat();
        System.out.printf("||%-15s||%s%n", getLoaiLinhKien(), getThongSoKyThuat());
    }
    
    @Override
    //ghi đè hiển thị thông tin System(SanPham)
    public String toString(){
        return super.toString() +
                "Loại Linh Kiện : " + getLoaiLinhKien() + "\n" +
                "Thông Số Kỹ Thuật : " + getThongSoKyThuat() + "\n";
    }
    
    public String getLoaiLinhKien() {
        return loaiLinhKien;
    }

    public void setLoaiLinhKien(String loaiLinhKien) {
        this.loaiLinhKien = loaiLinhKien;
    }

    public String getThongSoKyThuat() {
        return thongSoKyThuat;
    }

    public void setThongSoKyThuat(String thongSoKyThuat) {
        this.thongSoKyThuat = thongSoKyThuat;
    }
}