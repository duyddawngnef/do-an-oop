package manager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.cauhinh.RAM;

public class DanhSachRAM implements isList {
    RAM[] dsRam = new RAM[0];
    public RAM[]  getDanhSachRAM(){
        return this.dsRam;
    }
    public DanhSachRAM(){
        this.dsRam = new  RAM[0];
    }

    public int getSL(){
        return dsRam.length;
    }
    public boolean isEmpty(){
        return dsRam.length == 0;
    }

    //đọc file
    @Override
    public void read(String filename){
        File myFile = new File(filename);
        try (Scanner myReader = new Scanner(myFile)) {
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] value = data.split(";");
                try {
                    RAM ram = new RAM();
                    ram.setMaRAM(value[0].trim());
                    ram.setDungLuong(value[1].trim());
                    dsRam = Arrays.copyOf(dsRam, dsRam.length  + 1);
                    dsRam[dsRam.length -1] = ram;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Lỗi : Thếu dữ liệu trên dòng : " + data);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi : Không tìm thấy file " + filename);
        }
    }
    //ghi file

    @Override
    public void write(String filename){
        BufferedWriter myWriter = null;
        try {
            myWriter = new BufferedWriter(new FileWriter(filename));
            for(RAM ram : dsRam){
                String dataLine = "";
                dataLine = ram.getMaRAM()+";"+
                            ram.getDungLuong();
                if(!dataLine.isEmpty()){
                    myWriter.write(dataLine);
                }
                myWriter.newLine();
            }
        } catch (IOException e) {
        }
        finally{
            try {
                if(myWriter != null){
                    myWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Lỗi không thể đống file " + filename);
            }
        }
    }
    //thêm

    @Override
    public void them(){
        Scanner scanner = new Scanner(System.in);
        RAM ram = new RAM();
        boolean timthay = true;
        do {
            System.out.print("\nNhập mã RAM : ");
            String maram = scanner.nextLine().trim();
            timthay =kiemTraMa(maram);
            if (timthay) {
                System.out.println("Lỗi: Mã RAM tồn tại "+ maram);
            }
            else{
                ram.setMaRAM(maram);
            }
        } while (timthay);
        System.out.print("\nNhập dung lượng : ");
        String dungluong = scanner.nextLine().trim();
        ram.setDungLuong(dungluong);
        add(ram);
    }
    //xóa

    @Override
    public void xoa(){
        if(isEmpty()){
            System.out.println("Danh sách rỗng không có phần tử !!");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nNhập mã RAM cần xóa : ");
        String maram = scanner.nextLine().trim();
        boolean flag = false;
        for(int i = 0 ; i < getSL(); i++){
            if(dsRam[i].getMaRAM().equalsIgnoreCase(maram)){
                flag = true;
                remove(i);
                break;
            }
        }
        if(!flag){
            System.out.println("Không tìm thấy mã " +maram);
        }
    }
    //sửa

    @Override
    public void sua() {
        if (isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nNhập mã RAM cần sửa: ");
        String maram = scanner.nextLine().trim();
        RAM ramSua = timTheoMa(maram);
        
        if (ramSua != null) {
            System.out.println("\n=== THÔNG TIN RAM HIỆN TẠI ===");
            System.out.printf("%-15s||%-15s\n", "Mã RAM", "Dung Lượng");
            System.out.printf("%-15s||%-15s\n", ramSua.getMaRAM(), ramSua.getDungLuong());
            
            System.out.print("\nNhập dung lượng mới: ");
            String dungLuongMoi = scanner.nextLine().trim();
            
            if (!dungLuongMoi.isEmpty()) {
                ramSua.setDungLuong(dungLuongMoi);
                System.out.println("Đã cập nhật dung lượng RAM!");
            } else {
                System.out.println("Dung lượng không được để trống!");
            }
        } else {
            System.out.println("Không tìm thấy RAM với mã: " + maram);
        }
    }
    //tìm theo mã -> index
    @Override
    public void timTheoMa() {
        if (isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nNhập mã RAM cần tìm: ");
        String maram = scanner.nextLine().trim();
        
        RAM ram = timTheoMa(maram);
        if (ram != null) {
            System.out.printf("\n%-15s||%-15s\n", "Mã RAM", "Dung Lượng");
            System.out.println("--------------------------------");
            System.out.printf("%-15s||%-15s\n", ram.getMaRAM(), ram.getDungLuong());
        } else {
            System.out.println("Không tìm thấy RAM với mã: " + maram);
        }
    }
    //in ds
    @Override
    public void in(){
        if (isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        
        System.out.printf("\n%-15s||%-15s\n", "Mã RAM", "Dung Lượng");
        System.out.println("--------------------------------");
        for (RAM ram : dsRam) {
            System.out.printf("%-15s||%-15s\n", ram.getMaRAM(), ram.getDungLuong());
        }
    }
    // Thống kê theo dung lượng
    public void thongKeTheoDungLuong() {
        if (isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        
        int count4GB = 0, count8GB = 0, count16GB = 0, count32GB = 0, count64GB = 0, countKhac = 0;
        
        for (RAM ram : dsRam) {
            String dl = ram.getDungLuong().toLowerCase();
            if (dl.contains("4gb")) count4GB++;
            else if (dl.contains("8gb")) count8GB++;
            else if (dl.contains("16gb")) count16GB++;
            else if (dl.contains("32gb")) count32GB++;
            else if (dl.contains("64gb")) count64GB++;
            else countKhac++;
        }
        
        System.out.println("\n=== THỐNG KÊ RAM THEO DUNG LƯỢNG ===");
        if (count4GB > 0) System.out.printf("4GB:   %d (%.1f%%)\n", count4GB, (count4GB * 100.0 / getSL()));
        if (count8GB > 0) System.out.printf("8GB:   %d (%.1f%%)\n", count8GB, (count8GB * 100.0 / getSL()));
        if (count16GB > 0) System.out.printf("16GB:  %d (%.1f%%)\n", count16GB, (count16GB * 100.0 / getSL()));
        if (count32GB > 0) System.out.printf("32GB:  %d (%.1f%%)\n", count32GB, (count32GB * 100.0 / getSL()));
        if (count64GB > 0) System.out.printf("64GB:  %d (%.1f%%)\n", count64GB, (count64GB * 100.0 / getSL()));
        if (countKhac > 0) System.out.printf("Khác:  %d (%.1f%%)\n", countKhac, (countKhac * 100.0 / getSL()));
        System.out.printf("Tổng:  %d\n", getSL());
    }
    private void add(RAM ram){
        dsRam = Arrays.copyOf(dsRam, dsRam.length  + 1);
        dsRam[dsRam.length -1] = ram;
        System.out.println("Thêm thành công !");
    }
    private boolean  kiemTraMa(String ma){
        for(RAM ram : dsRam){
            if(ram.getMaRAM().equalsIgnoreCase(ma)){
                return true;
            }
        }
        return false;
    }
    private void remove(int i ){
        if(isEmpty()){
            System.out.println("Danh sách rỗng không có phần tử !!");
        }
        if(i < dsRam.length - 1){
        int numberRemove = getSL() - i -1;
        System.arraycopy(dsRam, i+1 , dsRam, i, numberRemove);
        }
        dsRam = Arrays.copyOf(dsRam, dsRam.length-1);
        System.out.println("Xóa thành công !");
    }
    private RAM timTheoMa(String ma) {
        for (RAM ram : dsRam) {
            if (ram.getMaRAM().equalsIgnoreCase(ma)) {
                return ram;
            }
        }
        return null;
    }
    private int layGiaTriDuong(String dungluong){
        try {
            return Integer.parseInt(dungluong.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
