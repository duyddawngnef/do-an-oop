package manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.cauhinh.CPU;

public class DanhSachCPU implements isList {
    CPU[] dsCpu = new CPU[0];

    public DanhSachCPU() {
        this.dsCpu = new CPU[0];
    }
    public int getSL(){
        return dsCpu.length;
    }
    public boolean isEmpty(){
        return dsCpu.length == 0;
    }
    // đọc file
    @Override
    public void read(String filename){
        File myFile = new File(filename);
        try (Scanner myReader = new Scanner(myFile)) {
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] value = data.split(";");

                try {
                    CPU cpu = new CPU();
                    cpu.setMaCPU(value[0].trim());
                    cpu.setTenCPU(value[1].trim());
                    cpu.setHangSanXuat(value[2].trim());
                    add(cpu);
                }
                // Lỗi: thiếu dữ liệu
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Lỗi thiếu dữ liệu trên dòng: " + data);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Lỗi : Không tìm thấy file "+ filename);
        }
    }
    // ghi file

    @Override
    public void write(String filename) {
        BufferedWriter myWriter = null;
        try {
            myWriter = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < getSL(); i++) {
                String dataLine = "";
                CPU  cpu = dsCpu[i];
                dataLine = cpu.getMaCPU()+';'+
                            cpu.getTenCPU()+ ';'+
                            cpu.getHangSanXuat();
                if(!dataLine.isEmpty()){
                    myWriter.write(dataLine);
                }
                myWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi: Không thể ghi được file "+ filename +" !!");
        }
        finally{
            try {
                if(myWriter != null ){
                    myWriter.close();
                }

            } catch (IOException e) {
                System.out.println("Lỗi : Không thể đống file " + filename + " !!");
            }
        }
    }

    // thêm

    @Override
    public void them() {
        Scanner scanner = new Scanner(System.in);
        CPU cpu = new CPU();
        boolean timthay = true;
        do {
            System.out.println("Mời nhập Mã CPU : ");
            String macpu = scanner.nextLine().trim();
            cpu.setMaCPU(macpu);
            timthay = kiemTraMa(macpu);
            if(timthay){
                System.out.println("Lỗi : Mã sản phẩm "+ cpu.getMaCPU() + "đã tồn tại !");
            }
        } while (timthay);
        System.out.println("Mời nhập tên CPU : ");
        String tencpu = scanner.nextLine().trim();
        cpu.setTenCPU(tencpu);
        System.out.println("Mời nhập hãng sản xuất : ");
        String hangsx = scanner.nextLine().trim();
        cpu.setHangSanXuat(hangsx);
        add(cpu);
    }
    // xóa

    @Override
    public void xoa() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã sản phẩm cần xóa : ");
        String macpu = scanner.nextLine().trim();
        CPU cpuxoa = timTheoMa(macpu);
        if(cpuxoa != null){
            for(int i = 0 ; i < dsCpu.length;i++){
                if(dsCpu[i].equals(cpuxoa)){
                    remove(i);
                    break;
                }
            }
        }
        else{
            System.out.println("\n Không tìm thấy với mã : " + macpu);
        }
    }
    // sửa

    @Override
    public void sua() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n Nhập mã CPU cần sửa : ");
        String macpu = scanner.nextLine().trim();
        CPU cpusua = timTheoMa(macpu);
        if(cpusua != null){
            System.err.println("\n==THÔNG TIN CPU HIỆN TẠI ===");
            cpusua.xuat();
            System.out.println();
            boolean tieptuc = true;
            while(tieptuc){
                System.out.println("\n === MENU SỬA CPU ====");
                System.out.println("Chọn thuộc tính cần sửa:");
                System.out.println("1. Tên CPU");
                System.out.println("2. Hãng Sản Xuất");
                System.out.println("0. Thoát và lưu thay đổi");
                System.out.print("Lựa chọn: ");
                int luaChon = -1;
                try {
                    luaChon = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Vui lòng nhập số nguyên !!!");
                    continue;
                }
                switch(luaChon){
                    case 0:
                        tieptuc = false;
                        System.out.println("Đã lưu thay đổi!");
                        break;
                    case 1:
                        System.out.println("Nhập tên CPU mới : ");
                        String tenMoi = scanner.nextLine().trim();
                        if(!tenMoi.isEmpty()){
                            cpusua.setTenCPU(tenMoi);
                            System.out.println("Đã cập nhật Tên CPU!");
                        }
                        else{
                            System.out.println("Tên không được để trống !");
                        }
                        break;
                    case 2:
                        System.out.println("Nhâp Hãng Sản Xuất mới : ");
                        String hangmoi = scanner.nextLine().trim();
                        if(!hangmoi.isEmpty()){
                            cpusua.setHangSanXuat(hangmoi);
                            System.out.println("Đã cập nhật Hãng Sản Xuất CPU!");
                        }
                        else{
                            System.out.println("Hãng sản xuất không được để trống !");
                        }
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ !");
                        break;
                }
            }
        }
        else{
            System.out.println("Không tìm thấy CPU với mã: " + macpu);
        }
    }

    // tìm theo mã -> index
    @Override
    public void timTheoMa() {
        if(isEmpty()){
            System.out.print("Chưa có  nào trong danh sách");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nNhập mã CPU : ");
        String macpu = scanner.nextLine().trim();
        boolean flag = false;
        for(CPU cpu : dsCpu){
            if(cpu.getMaCPU().equalsIgnoreCase(macpu)){
                flag = true;
                cpu.xuat();
            }
        }
        if(!flag){
            System.out.println("Không tìm thấy mã CPU !!!");
        }
    }

    // in ds
    @Override
    public void in() {
        if(isEmpty()){
            System.out.println("Chưa có sản phẩm nào trong danh sách");
            return;
        }
        System.out.printf("%-15s||%-20s||%-15s", "Mã CPU", "Tên CPU", "Hãng Sản Xuâtx");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for(CPU cpu : dsCpu){
            cpu.xuat();
        }

    }
    private void add(CPU cpu ){
        System.err.println("Thêm thành công !");
        dsCpu = Arrays.copyOf(dsCpu,dsCpu.length+1);
        dsCpu[dsCpu.length-1] = cpu;
    }
    private boolean kiemTraMa(String macpu){
        for(CPU cpu : dsCpu){
            if(cpu.getMaCPU().equalsIgnoreCase(macpu)){
                return true;
            }
        }
        return false;
    }
    private CPU timTheoMa(String macpu){
        for(CPU cpu : dsCpu){
            if(cpu.getMaCPU().equalsIgnoreCase(macpu)){
                return cpu;
            }
        }
        return null;
    }
    private void remove(int i ){
        if(i < 0 || i >= dsCpu.length){
            System.out.println("Vị trí index lỗi !!");
            return ;
        }
        if(i < dsCpu.length - 1){
        int numberRemove = dsCpu.length - i  -1;
        System.arraycopy(dsCpu, i+1, dsCpu, i, numberRemove);
        }
        dsCpu = Arrays.copyOf(dsCpu, dsCpu.length-1);
        System.err.println("Xóa thành công !");
    }
    
}
