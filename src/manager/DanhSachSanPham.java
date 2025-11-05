package manager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Interface.isList;
import model.sanpham.Laptop;
import model.sanpham.LinhKien;
import model.sanpham.SanPham;
public class DanhSachSanPham implements  isList{
   SanPham[] dsSanPham = new SanPham[0];
   public DanhSachSanPham(){
      this.dsSanPham = new SanPham[0];
   }
   public static  SanPham[] getDanhSachSanPham() {
      return dsSanPham;
   }
   public boolean isEmpty(){
      if(this.dsSanPham.length == 0 ) return true;
      return false;
   }
   public int getSoLuongSanPham(){
      return dsSanPham.length;
   }
   @Override
   public void read(String filename) {
      File myFile = new File(filename);
      try (Scanner myReader = new Scanner(myFile)) {
         
         while (myReader.hasNextLine()) {
               String data = myReader.nextLine();
               String[] value = data.split(";");

               try {
                     // Xử lý Linh Kiện
                  if (value[0].contains("LK")) {
                     LinhKien lkSanPham = new LinhKien();
                     lkSanPham.setMaSP(value[0].trim());
                     lkSanPham.setTenSP(value[1].trim());
                     lkSanPham.setSoLuongTon(Integer.parseInt(value[2].trim()));
                     lkSanPham.setDonGiaBan(Integer.parseInt(value[3].trim()));
                     lkSanPham.setLoaiLinhKien(value[4].trim());
                     lkSanPham.setThongSoKyThuat(value[5].trim());
                     
                     this.add(lkSanPham); 
                  }
                  else if (value[0].contains("LT")) {
                     Laptop ltSanPham = new Laptop();
                     
                     ltSanPham.setMaSP(value[0].trim());
                     ltSanPham.setTenSP(value[1].trim());
                     ltSanPham.setSoLuongTon(Integer.parseInt(value[2].trim()));
                     ltSanPham.setDonGiaBan(Integer.parseInt(value[3].trim()));
                     
                        // Các thuộc tính riêng của Laptop
                     ltSanPham.setCpu(value[4].trim());
                     ltSanPham.setRam(value[5].trim());
                     
                        this.add(ltSanPham); // Thêm vào danh sách
                  }

               }
               // Lỗi: ép chuỗi -> số
               catch (NumberFormatException e) {
                  System.out.println("Lỗi định dạng số trên dòng: " + data);
               }
               // Lỗi: thiếu dữ liệu
               catch (ArrayIndexOutOfBoundsException e) {
                  System.out.println("Lỗi thiếu dữ liệu trên dòng: " + data);
               }
         }
      } 
      catch (FileNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy file: " + filename);
      }
   }
   @Override
   public void write(String filename){
      BufferedWriter myWriter = null;
      try {
         //xóa nếu đã tồn tại rồi 
         myWriter =  new BufferedWriter(new FileWriter(filename));
         for(int i = 0; i < getSoLuongSanPham() ; i++){
            String dataLine = "";
            if(dsSanPham[i] instanceof Laptop){
               //ép kiểu về Laptop 
               Laptop lt = (Laptop)dsSanPham[i];
               dataLine = lt.getMaSP() + ";" +
                           lt.getTenSP() + ";" +
                           lt.getSoLuongTon() + ";" +
                           lt.getDonGiaBan() + ";" +
                           lt.getCpu() + ";" +
                           lt.getRam();
            }
            if (dsSanPham[i] instanceof LinhKien) {
                // ép kiểu về LinhKien
               LinhKien lk = (LinhKien) dsSanPham[i];
               
               dataLine = lk.getMaSP() + ";" +
                           lk.getTenSP() + ";" +
                           lk.getSoLuongTon() + ";" +
                           lk.getDonGiaBan() + ";" +
                           lk.getLoaiLinhKien() + ";" +
                           lk.getThongSoKyThuat();
            }
            if(!dataLine.isEmpty()){
               myWriter.write(dataLine);
            //ký tự xuống dòng 
            myWriter.newLine();
            }
         }
      } catch (IOException e) {
         System.out.println("Lỗi: Không thể ghi được file "+ filename +" !!");
      }
      finally{
         try {
            if(myWriter != null){
               myWriter.close();
            }
         } catch (IOException e) {
            System.out.println("Lỗi : Không thể đống file " + filename + " !!");
         }
      }
   }
   @Override
   public  void them(){
      Scanner scanner = new Scanner(System.in);
   
      int loaiSP = 0;
      do {
         try {
            System.out.println("\n Bạn muốn thêm sản phẩm nào ?");
            System.out.println("1. Laptop");
            System.out.println("2. Linh kiện");
            System.out.println("Lựa chọn : ");
            loaiSP = Integer.parseInt(scanner.nextLine());
            if(loaiSP != 1 && loaiSP != 2){
               System.out.println("Số lượng không hợp lệ !!");
            }
         } 
         catch (NumberFormatException e) {
            loaiSP = 0;
            System.out.println("Lỗi: Vui lòng nhập số nguyên!!");
         }
      } while (loaiSP != 1 && loaiSP != 2);
      SanPham spMoi;
      if(loaiSP ==1){
         spMoi = new Laptop();
      }
      else{
         spMoi = new LinhKien();
      }
      spMoi.nhap();
      boolean timthhay = kiemTraMaSP(spMoi.getMaSP());
      if(timthhay){
         System.out.println("Lỗi : Mã sản phẩm "+ spMoi.getMaSP() + "đã tồn tại !");
      }
      else{
         add(spMoi);
      }
   }
   @Override
   public void xoa(){
      Scanner scanner = new Scanner(System.in);
      System.out.println("Nhập mã sản phẩm cần xóa : ");
      String masp = scanner.nextLine().trim();
      SanPham spxoa = timTheoMa(masp);
      if(spxoa != null){
         for(int i = 0 ; i < dsSanPham.length ; i++){
            if(dsSanPham[i].getMaSP().equalsIgnoreCase(masp)){
               remove(i);
               break;
            }
         }
      }
      else{
         System.out.println("\n Không tìm thấy với mã : " + masp);
      }
   }
     //Sửa Sản Phẩm

   @Override
   public void sua(){
      Scanner scanner = new Scanner(System.in);
      System.out.print("\nNhập mã Sản Phẩm cần sửa: ");
      String masp = scanner.nextLine().trim();
      SanPham spCanSua = timTheoMa(masp);
      
      if(spCanSua != null){
         System.out.println("\n=== THÔNG TIN SẢN PHẨM HIỆN TẠI ===");
         spCanSua.xuat();
         System.out.println();
         
         boolean tiepTuc = true;
         
         while(tiepTuc){
               System.out.println("\n=== MENU SỬA SẢN PHẨM ===");
               System.out.println("Chọn thuộc tính cần sửa:");
               System.out.println("1. Tên Sản Phẩm");
               System.out.println("2. Số Lượng Tồn");
               System.out.println("3. Đơn Giá Bán");
               
               if(spCanSua instanceof Laptop){
                  System.out.println("4. CPU");
                  System.out.println("5. RAM");
               }
               else if(spCanSua instanceof LinhKien){
                  System.out.println("4. Loại Linh Kiện");
                  System.out.println("5. Thông Số Kỹ Thuật");
               }
               
               System.out.println("0. Thoát và lưu thay đổi");
               System.out.print("Lựa chọn: ");
               
               int luaChon = -1;
               try {
                  luaChon = Integer.parseInt(scanner.nextLine().trim());
               } catch (NumberFormatException e) {
                  System.out.println("Lỗi: Vui lòng nhập số nguyên!");
                  continue;
               }
               
               try {
                  switch(luaChon){
                     case 0:
                           tiepTuc = false;
                           System.out.println("Đã lưu thay đổi!");
                           break;
                           
                     case 1:
                           System.out.print("Nhập Tên Sản Phẩm mới: ");
                           String tenMoi = scanner.nextLine().trim();
                           if(!tenMoi.isEmpty()){
                              spCanSua.setTenSP(tenMoi);
                              System.out.println("Đã cập nhật Tên Sản Phẩm!");
                           } else {
                              System.out.println("Tên không được để trống!");
                           }
                           break;
                           
                     case 2:
                           System.out.print("Nhập Số Lượng Tồn mới: ");
                           int slMoi = Integer.parseInt(scanner.nextLine().trim());
                           if(slMoi >= 0){
                              spCanSua.setSoLuongTon(slMoi);
                              System.out.println("Đã cập nhật Số Lượng Tồn!");
                           } else {
                              System.out.println("Số lượng phải >= 0!");
                           }
                           break;
                           
                     case 3:
                           System.out.print("Nhập Đơn Giá Bán mới: ");
                           int giaMoi = Integer.parseInt(scanner.nextLine().trim());
                           if(giaMoi > 0){
                              spCanSua.setDonGiaBan(giaMoi);
                              System.out.println("Đã cập nhật Đơn Giá Bán!");
                           } else {
                              System.out.println("Đơn giá phải > 0!");
                           }
                           break;
                           
                     case 4:
                           if(spCanSua instanceof Laptop){
                              Laptop lt = (Laptop)spCanSua;
                              lt.dsHoTroCPU();
                              System.out.print("Nhập CPU mới: ");
                              String cpuMoi = scanner.nextLine().trim();
                              lt.setCpu(cpuMoi);
                              System.out.println("Đã cập nhật CPU!");
                           }
                           else if(spCanSua instanceof LinhKien){
                              LinhKien lk = (LinhKien)spCanSua;
                              System.out.println("\nDanh sách Linh Kiện hỗ trợ: [CPU, RAM, SSD, HDD, VGA, Mainboard, PSU]");
                              System.out.print("Nhập Loại Linh Kiện mới: ");
                              String loaiMoi = scanner.nextLine().trim();
                              lk.setLoaiLinhKien(loaiMoi);
                              System.out.println(" Đã cập nhật Loại Linh Kiện!");
                           }
                           break;
                           
                     case 5:
                           if(spCanSua instanceof Laptop){
                              Laptop lt = (Laptop)spCanSua;
                              lt.dsHoTroRAM();
                              System.out.print("Nhập RAM mới: ");
                              String ramMoi = scanner.nextLine().trim();
                              lt.setRam(ramMoi);
                              System.out.println(" Đã cập nhật RAM!");
                           }
                           else if(spCanSua instanceof LinhKien){
                              LinhKien lk = (LinhKien)spCanSua;
                              System.out.print("Nhập Thông Số Kỹ Thuật mới: ");
                              String tsktMoi = scanner.nextLine().trim();
                              lk.setThongSoKyThuat(tsktMoi);
                              System.out.println(" Đã cập nhật Thông Số Kỹ Thuật!");
                           }
                           break;
                           
                     default:
                           System.out.println("Lựa chọn không hợp lệ!");
                           break;
                  }
                  
                  // Hiển thị thông tin sau khi sửa
                  if(luaChon >= 1 && luaChon <= 5){
                     System.out.println("\n--- Thông tin sau khi sửa ---");
                     spCanSua.xuat();
                     System.out.println();
                  }
                  
               } catch (NumberFormatException e) {
                  System.out.println("Lỗi: Định dạng số không hợp lệ!");
               }
         }
         
      } else {
         System.out.println("Không tìm thấy sản phẩm với mã: " + masp);
      }
   }
   @Override
   public void in() {
         if (isEmpty()) {
            System.out.println("Chưa có sản phẩm nào trong danh sách.");
            return;
         }
         System.out.println("DANH SÁCH TẤT CẢ SẢN PHẨM:");
         System.out.printf("%-15s||%-20s||%-15s||%-15s||%-15s||%s\n", "Mã SP", "Tên SP", "Số Lượng Tồn", "Đơn Giá Bán", "Loại/CPU", "Thông số/RAM");
         System.out.println("--------------------------------------------------------------------------------------------------");
         //hiển thị đa hình 
         for (SanPham sp : dsSanPham) {
            sp.xuat(); 
         }
      }
      // tìm sản phẩm theo mã(nhập)
      @Override
      public void  timTheoMa(){
         Scanner scanner = new Scanner(System.in);
         System.out.println("\nMời nhập mã sản phẩm cần tim : ");
         String masp = scanner.nextLine().trim();
         boolean flag = false;
         for(SanPham sp : dsSanPham){
            if(masp.equalsIgnoreCase(sp.getMaSP())){
               flag = true;
               sp.xuat();
            }  
         }
         if(!flag){
            System.out.println("Không tìm thấy mã sản phẩm  " + masp + " !");
         }

      }
   // Hàm hiển thị theo loại
   public void hienThiTheoLoai() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Bạn muốn hiển thị loại nào?");
      System.out.println("1. Laptop");
      System.out.println("2. Linh kiện");
      System.out.print("Lựa chọn: ");
      int loaiSP = 0;
      do {
         try {
            System.out.println("Bạn muốn hiển thị loại nào?");
            System.out.println("1. Laptop");
            System.out.println("2. Linh kiện");
            System.out.print("Lựa chọn: ");
            loaiSP = Integer.parseInt(scanner.nextLine());
            if(loaiSP != 1 && loaiSP != 2){
               System.out.println("Số lượng không hợp lệ !!");
            }
         } 
         catch (NumberFormatException e) {
            loaiSP = 0;
            System.out.println("Lỗi: Vui lòng nhập số nguyên!!");
         }
      } while (loaiSP != 1 && loaiSP != 2);
      System.out.printf("%-15s||%-20s||%-15s||%-15s||%-15s||%s\n", "Mã SP", "Tên SP", "Số Lượng Tồn", "Đơn Giá Bán", "Loại/CPU", "Thông số/RAM");
      System.out.println("--------------------------------------------------------------------------------------------------");
      for (SanPham sp : dsSanPham) {
         if (loaiSP == 1 && sp instanceof Laptop) {
               sp.xuat();
         } else if (loaiSP == 2 && sp instanceof LinhKien) {
               sp.xuat();
         }
      }
      
   }
   public SanPham[] timSanPhamTheoTen(String tensp){
      SanPham[] resSanPham = new SanPham[0];
      for(SanPham sp : dsSanPham){
         if(tensp.equalsIgnoreCase(sp.getTenSP())){
            resSanPham = Arrays.copyOf(resSanPham, resSanPham.length+1);
            resSanPham[resSanPham.length -1] = sp;
         }
      }
      return resSanPham;
   }
   public void timTheoKhoangGia(){
      Scanner scanner = new  Scanner(System.in);
      int giaMin = -1 , giaMax = -1;
      do {
         try {
            System.out.println("\n Mời nhập giá Min : ");
            giaMin = Integer.parseInt(scanner.nextLine());
         } catch (NumberFormatException e) {
            giaMin = -1;
            System.out.println("Số lượng không hợp lệ !!");
         }
      } while (giaMin < 0 );
      do {
         try {
            System.out.println("\n Mời nhập giá Max : ");
            giaMax = Integer.parseInt(scanner.nextLine());
         } catch (NumberFormatException e) {
            giaMax = -1;
            System.out.println("Số lượng không hợp lệ !!");
         }
      } while (giaMax < giaMin);
      boolean flag = false;
      System.out.printf("%-15s||%-20s||%-15s||%-15s||%-15s||%s\n", "Mã SP", "Tên SP", "Số Lượng Tồn", "Đơn Giá Bán", "Loại/CPU", "Thông số/RAM");
      System.out.println("--------------------------------------------------------------------------------------------------");
      for(SanPham sp : dsSanPham){
         if(sp.getDonGiaBan() >= giaMin && sp.getDonGiaBan() <= giaMax){
            flag = true;
            sp.xuat();
         }
      }
      if(!flag){
         System.out.println("Không tìm thấy sản phẩm nào trong giá từ " + giaMin +" --> " + giaMax);
      }
   }
   //tìm CPU và RAM 
   public void timTheoCauHinh(){
      Scanner scanner = new  Scanner(System.in);
      int luaChon = 0;
      do {
         try {
            System.out.println("\n Bạn muốn tìm theo CPU hay RAM ?");
            System.out.println("1. CPU");
            System.out.println("2. RAM");
            System.out.println("Lựa chọn : ");
            luaChon = Integer.parseInt(scanner.nextLine());
            if(luaChon != 1 && luaChon != 2){
               System.out.println("Số lượng không hợp lệ !!");
            }
         } 
         catch (NumberFormatException e) {
            luaChon = 0;
            System.out.println("Lỗi: Vui lòng nhập số nguyên!!");
         }
      } while (luaChon != 1 && luaChon != 2);
      Laptop lt = new Laptop();
      String tuKhoa = "";
      if (luaChon == 1) {
         lt.dsHoTroCPU();
         System.out.print("Nhập CPU cần tìm : ");
         tuKhoa = scanner.nextLine().trim();
      } else if (luaChon == 2) {
         lt.dsHoTroRAM();
         System.out.print("Nhập RAM cần tìm : ");
         tuKhoa = scanner.nextLine().trim();
      }
      boolean flag = false;
      System.out.printf("%-15s||%-20s||%-15s||%-15s||%-15s||%s\n", "Mã SP", "Tên SP", "Số Lượng Tồn", "Đơn Giá Bán", "Loại/CPU", "Thông số/RAM");
      System.out.println("--------------------------------------------------------------------------------------------------");
      for(SanPham sp : dsSanPham){
         if(sp instanceof Laptop){
            if(luaChon == 1 && tuKhoa.equalsIgnoreCase(((Laptop) sp).getCpu())){
               flag = true;
               sp.xuat();
            }
            else if ( luaChon == 2 && tuKhoa.equalsIgnoreCase(((Laptop)sp).getRam())){
               flag = true;
               sp.xuat();
            }
         }
      }
      if(!flag && luaChon == 1){
         System.out.println("Không tìm thấy sản phẩm nào có CPU là "+tuKhoa);
      }
      else if (!flag && luaChon == 2) {
         System.out.println("Không tìm thấy sản phẩm nào có RAM là "+tuKhoa);
      }
   }
   public void thongKeTongGiaTriTonKho(){
      if(isEmpty()){
         System.out.println("Danh sách Sản Phẩm trống !!");
         System.out.println("Tổng giá trị kho :0 VND");
      }
      // tổng giá trị 
      int tongiatri = 0;
      //tổng số lượng
      int tongsl = 0 ;
      for( SanPham sp : dsSanPham){
         tongiatri += (sp.getDonGiaBan() * sp.getSoLuongTon());
         tongsl++;
      }
      System.out.println("\n--- THỐNG KÊ TỔNG GIÁ TRỊ KHO ---");
      //định dạng tiền bằng %,d 
      System.out.printf("Tổng số loại sản phẩm (SKUs): %d\n", getSoLuongSanPham());
      System.out.printf("Tổng số lượng hàng tồn kho: %,d\n", tongsl);
      System.out.printf("Tổng giá trị tồn kho:         %,d VND\n", tongiatri);
   }
   // thống kê sản phẩm theo loại (Laptop , Linh Kiện)
   public void thongKeSanPhamTheoLoai(){
       if(isEmpty()){
         System.out.println("Danh sách Sản Phẩm trống !!");
         System.out.println("Tổng số lượng Laptop : 0 ");
         System.out.println("Tổng số lượng Linh Kiện : 0 ");
      }
      // các loại CPU hổ trợ 
      String[] VAL_CPU = {
      "Core i3", "Core i5", "Core i7", "Core i9", 
      "Ryzen 3", "Ryzen 5", "Ryzen 7", "Ryzen 9", 
      "M1", "M2", "M3"  
      };
      // các loại RAM hổ trợ 
      String[] VAL_RAM ={
      "4GB", "8GB", "16GB", "32GB", "64GB"
      };
      // các loại linh kiện hổ trợ 
      String[] VAL_LOAI_LINH_KIEN = {
      "CPU", "RAM", "SSD", "HDD", "VGA", "Mainboard", "PSU"
      };
      int slLT = 0;
      int slLK = 0;
      //mảng điếm loại linh kiện 
      int[] cntCPU = new int[VAL_CPU.length];
      int[] cntRAM = new int[VAL_RAM.length];
      int[] cntLK = new int[VAL_LOAI_LINH_KIEN.length];
      for(SanPham sp : dsSanPham){
         if(sp instanceof Laptop){
            slLT++;
            Laptop lt = (Laptop)sp;
            // điếm cpu 
            for(int i = 0 ; i < VAL_CPU.length; i++){
               if(VAL_CPU[i].equalsIgnoreCase(lt.getCpu())){
                  cntCPU[i]++;
                  break;
               }
            }
            //điếm RAM 
            for(int i = 0 ; i < VAL_RAM.length; i++){
               if(VAL_RAM[i].equalsIgnoreCase(lt.getRam())){
                  cntRAM[i]++;
                  break;
               }
            }
         }
         if (sp instanceof LinhKien){
            slLK++;
            //điếm linh kiện 
            LinhKien lk = (LinhKien) sp;
            for(int i = 0 ; i < VAL_LOAI_LINH_KIEN.length; i++){
               if(VAL_LOAI_LINH_KIEN[i].equalsIgnoreCase(lk.getLoaiLinhKien())){
                  cntLK[i]++;
                  break;
               }
            }
         }
      }
       System.out.println("\n--- THỐNG KÊ CHI TIẾT THEO LOẠI SẢN PHẨM ---");
      System.out.println("\n--- TỔNG QUAN ---");
      System.out.printf("Tổng số Laptop:     %d\n", slLT);
      System.out.printf("Tổng số Linh kiện:  %d\n", slLK);
      System.out.printf("Tổng cộng:          %d sản phẩm (SKUs)\n", getSoLuongSanPham());
      if(slLT > 0 ){
         //thống kê theo từng loại CPU
         System.out.println("CHI TIẾT LAPTOP THEO CPU !");
         for(int i = 0 ; i < VAL_CPU.length;i++){
            if(cntCPU[i] > 0 ){
               System.out.printf("%-10s: %d sản phẩm\n", VAL_CPU[i], cntCPU[i]);
            }
         }
         //thống kê theo từng loại RAM
         System.out.println("CHI TIẾT LAPTOP THEO RAM !");
         for (int i = 0; i < VAL_RAM.length; i++) {
            if (cntRAM[i] > 0) {
               System.out.printf("%-10s: %d sản phẩm\n", VAL_RAM[i], cntRAM[i]);
            }
         }
      }
      if(slLK > 0){
         System.out.println("CHI TIẾT LINH KIỆN THEO LOẠI !");
         for (int i = 0; i < VAL_LOAI_LINH_KIEN.length; i++) {
            if (cntLK[i] > 0) {
               System.out.printf("%-12s: %d sản phẩm\n", VAL_LOAI_LINH_KIEN[i], cntLK[i]);
            }
         }
      }
   }

   private  boolean kiemTraMaSP(String masp){
      for(SanPham sp : dsSanPham){
         if(masp.equalsIgnoreCase(sp.getMaSP())){
            return true;
         }
      }
      return false;
   }
   public  SanPham timTheoMa(String masp){
      for(SanPham sp : dsSanPham){
         if(masp.equalsIgnoreCase(sp.getMaSP())){
            return sp;
         }
      }
      return null;
   }
   private  void remove(int i ){
      if(i < 0 || i >= dsSanPham.length){
         System.out.println("Vị trí index lỗi !!");
         return ;
      }
      if(i < dsSanPham.length - 1){
         int numberRemove = dsSanPham.length -i -1;
         // lấy numbeRemove phần tử của dsSanPham (i+1) và đè lên chính dsSanPham(i)
         System.arraycopy(dsSanPham, i+1, dsSanPham, i, numberRemove);
      }

      dsSanPham = Arrays.copyOf(dsSanPham,dsSanPham.length-1 );

      System.out.println("Xóa thành công !");
   }
   private  void add(SanPham sp){
      System.out.println("Thêm thành công !");
      dsSanPham = Arrays.copyOf(dsSanPham,dsSanPham.length+1);
      dsSanPham[dsSanPham.length-1] = sp;
   }
}
