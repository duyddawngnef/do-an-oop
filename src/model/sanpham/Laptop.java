package model.sanpham; 

import java.util.Scanner;

public class Laptop extends SanPham {
    private String cpu;
    private String ram;
    public static String[] getDanhSachCpu(){
        return VAL_CPU.clone();
    }
    public static String[] getDanhSachDungLuongRam(){
        return VAL_RAM.clone();
    }
    public static boolean kiemTraCpuHopLe(String name){
        for(String val : VAL_CPU){
            if(val.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
        
    }
    public static boolean kiemTraDungLuongRam(String dungluong){
        for(String val : VAL_RAM){
            if(val.equalsIgnoreCase(dungluong)){
                return true;
            }
        }
        return false;
    }
    public  Laptop(){
        super();
        cpu = "";
        ram = "";
    }
    // Danh Sách các CPU hợp lệ 
    private static final String[] VAL_CPU = {
        "Core i3", "Core i5", "Core i7", "Core i9", 
        "Ryzen 3", "Ryzen 5", "Ryzen 7", "Ryzen 9", 
        "M1", "M2", "M3"
    };
    private static final String[] VAL_RAM ={
        "4GB", "8GB", "16GB", "32GB", "64GB"
    };
    //thiết lập constructor
    public Laptop(String maSP , String tenSP , int soLuongTon, int donGiaBan, String cpu , String ram) {
        super(maSP, tenSP, soLuongTon, donGiaBan);
        this.cpu = cpu;
        this.ram = ram;
    }
    //thiết lập constructor
    public  Laptop(Laptop lt){
        super(lt);
        this.cpu = lt.cpu;
        this.ram = lt.ram;
    }
    @Override
    //ghi đè hàm nhập(SanPham)
    public void nhap(){
        Scanner scanner = new Scanner(System.in);
        super.nhap();
        boolean isCpu,isRam;
        do {        
            isCpu = false;
            //chuyển mảng có tên VAL_CPU thành chuỗi có định dạng []
            System.out.println("\nDanh sách CPU hổ trợ " + java.util.Arrays.toString(VAL_CPU));
            System.out.println("Mời nhập CPU : ");
            //xóa các khoảng trắng thừa 
            this.cpu = scanner.nextLine().trim();
            isCpu = kiemTraCpuHopLe(cpu);
            if(!isCpu){
                System.out.println("Lỗi:Lỗi CPU không hợp lệ!!");
            }

        } while (!isCpu);
        do {
            isRam = false;
            System.out.println("\nDanh sách RAM hổ trợ là " + java.util.Arrays.toString(VAL_RAM));
            System.out.println("Mời nhập RAM : ");  
            this.ram = scanner.nextLine().trim();
            isRam = kiemTraDungLuongRam(ram);
            if(!isRam){
                System.out.println("Lỗi:Loại RAM không hợp lệ !!");
            }
        } while (!isRam);
        

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
                +"RAM : " + getRam() + "\n";
    }
    public String getCpu() {
        return this.cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return this.ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }
    //xuất danh sách CPU hổ trợ
    public void dsHoTroCPU() {
        String[] cpus = getDanhSachCpu();
        System.out.println("\nDanh sách CPU hỗ trợ: " + java.util.Arrays.toString(cpus));
    }
    //xuất danh sách CPU hổ trợ 
    public void dsHoTroRAM() {
        String[] rams = getDanhSachDungLuongRam();
        System.out.println("Danh sách RAM hỗ trợ: " + java.util.Arrays.toString(rams));
    }
}
