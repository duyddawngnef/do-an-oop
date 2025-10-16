package model.sanpham; 

public class LapTop extends SanPham {
    private String cpu;
    private String ram;
    public  LapTop(){
        cpu = "";
        ram = "";
    }
    //thiết lập constructor
    public LapTop(String maSP , String tenSP , int soLuongTon, int donGiaBan, String cpu , String ram) {
        super(maSP, tenSP, soLuongTon, donGiaBan);
        this.cpu = cpu;
        this.ram = ram;
    }
    //thiết lập constructor
    public  LapTop(LapTop lt){
        super();
        this.cpu = lt.cpu;
        this.ram = lt.ram;
    }
    @Override
    //ghi đè hàm nhập(SanPham)
    public void nhap(){
        super.nhap();
        System.out.println("\nMời Nhập CPU :");
        this.cpu = scanner.nextLine();
        System.out.println("\nMời Nhập GAM");
        this.ram = scanner.nextLine();

    }
    @Override
    //ghi đè hàm xuất(SanPham)
    public  void xuat(){
        super.xuat();
        System.out.printf("||%-10s||%s\n",getCpu(),getRam());
    }
    @Override
    //ghi đè hiển thị thông tin System(SanPham)
    public String toString(){
        return super.toString()+
                "CPU : " + getCpu()+"\n"
                +"GAM : " + getRam() + "\n";
    }
    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }
}
