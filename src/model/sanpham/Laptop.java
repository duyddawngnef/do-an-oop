package model.sanpham; 

public class Laptop extends SanPham {
    private String cpu;
    private String ram;
    public  Laptop(){
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
        super();
        this.cpu = lt.cpu;
        this.ram = lt.ram;
    }
    @Override
    //ghi đè hàm nhập(SanPham)
    public void nhap(){
        super.nhap();
        boolean isCpu,isRam;
        do {        
            isCpu = false;
            //chuyển mảng có tên VAL_CPU thành chuỗi có định dạng []
            System.out.println("\nDanh sách CPU hổ trợ " + java.util.Arrays.toString(VAL_CPU));
            System.out.println("Mời nhập CPU : ");
            //xóa các khoảng trắng thừa 
            this.cpu = scanner.nextLine().trim();
            for( String valdCpu : VAL_CPU ){
                if(valdCpu.equalsIgnoreCase(this.cpu)){
                    isCpu = true;
                    break;
                }
            }
            if(!isCpu){
                 System.out.println("Lỗi CPU không hợp lệ .Vui lòng nhập lại ");
            }

        } while (!isCpu);
        do {
            isRam = false;
            System.out.println("\nDanh sách GAM hổ trợ là " + java.util.Arrays.toString(VAL_RAM));
            System.out.println("Mời nhập GAM : ");  
            this.ram = scanner.nextLine().trim();
            for(String valRam : VAL_RAM){
                if (valRam.equalsIgnoreCase(this.ram)){
                    isRam = true;
                    break;
                }
            }
            if(!isRam){
                System.out.println("Lỗi RAM không hợp lệ .Vui lòng nhập lại ");
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
