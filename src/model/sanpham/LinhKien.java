package model.sanpham; 

public class LinhKien extends SanPham {
    private String loaiLinhKien;
    private String thongSoKyThuat;
    
    public LinhKien(){
        loaiLinhKien = "";
        thongSoKyThuat = "";
    }
    private static final String[] VAL_LOAI_LINH_KIEN = {
        "CPU", "RAM", "SSD", "HDD", "VGA", "Mainboard", "PSU"
    };
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
        boolean isLK;
        do {
            isLK = false;
            //chuyển mảng thành chuỗi []
            System.out.println("\nDanh sách Linh Kiện hổ trợ : " + java.util.Arrays.toString(VAL_LOAI_LINH_KIEN));
            System.out.println("\nMời Nhập Loại Linh Kiện :");
            //xóa khoảng trắng thừa
            this.loaiLinhKien = scanner.nextLine().trim();
            for(String ValLk : VAL_LOAI_LINH_KIEN){
                if(ValLk.equalsIgnoreCase(this.loaiLinhKien)){
                    isLK = true;
                    break;
                }
            }
            if (!isLK) {
                System.out.println("Lỗi :Loại Linh Kiện không hợp lệ !!");
            }
        } while (!isLK);
        System.out.println("\nMời Nhập Thông Số Kỹ Thuật :");
        this.thongSoKyThuat = scanner.nextLine();
    }
    
    @Override
    //ghi đè hàm xuất(SanPham)
    public void xuat(){
        super.xuat();
        System.out.printf("||%-10s||%s\n", getLoaiLinhKien(), getThongSoKyThuat());
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