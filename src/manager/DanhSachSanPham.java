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
      dsSanPham = new SanPham[0];

   }
   public SanPham[] getDanhSachSanPham() {
      return dsSanPham;
   }
   public boolean isEmpty(){
      if(dsSanPham.length == 0 ) return true;
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
                     
                     dsSanPham = Arrays.copyOf(dsSanPham,dsSanPham.length+1);
                     dsSanPham[dsSanPham.length-1] = lkSanPham;
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
                     
                     dsSanPham = Arrays.copyOf(dsSanPham,dsSanPham.length+1);
                     dsSanPham[dsSanPham.length-1] = ltSanPham;
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
   public void them(){
      Scanner scanner = new Scanner(System.in);
      
      int loaiSP = 0;
      String maSP;
      String tenSP;
      int soLuongTon;
      int donGiaBan;
      String ram, cpu, loaiLinhKien, thongSoKyThuat;
      boolean timThay = false;
      
      // ===== CHỌN LOẠI SẢN PHẨM =====
      do {
         try {
               System.out.println("\n Bạn muốn thêm sản phẩm nào?");
               System.out.println("1. Laptop");
               System.out.println("2. Linh kiện");
               System.out.print("Lựa chọn: ");
               loaiSP = Integer.parseInt(scanner.nextLine());
               if(loaiSP != 1 && loaiSP != 2){
                  System.out.println("Số lựa chọn không hợp lệ!!");
               }
         } 
         catch (NumberFormatException e) {
               loaiSP = 0;
               System.out.println("Lỗi: Vui lòng nhập số nguyên!!");
         }
      } while (loaiSP != 1 && loaiSP != 2);
      
      SanPham spMoi;
      if(loaiSP == 1){
         spMoi = new Laptop();
      }
      else{
         spMoi = new LinhKien();
      }
      
      // nhập sản phẩm 
      do { 
         System.out.print("\nMời Nhập Mã Sản Phẩm: ");
         maSP = scanner.nextLine().trim();
         
         if (maSP.isEmpty()) {
               System.out.println("Lỗi: Mã sản phẩm không được để trống!");
               timThay = true;
               continue;
         }
         
         // Kiểm tra tiền tố mã phù hợp với loại sản phẩm
         if(loaiSP == 1 && !maSP.toUpperCase().startsWith("LT")){
               System.out.println("Lỗi: Mã Laptop phải bắt đầu bằng 'LT'!");
               timThay = true;
               continue;
         }
         if(loaiSP == 2 && !maSP.toUpperCase().startsWith("LK")){
               System.out.println("Lỗi: Mã Linh Kiện phải bắt đầu bằng 'LK'!");
               timThay = true;
               continue;
         }
         
         // kiểm tra tồn tại 
         timThay = kiemTraMaSP(maSP);
         if(timThay){
               System.out.println("Lỗi: Mã sản phẩm '" + maSP + "' đã tồn tại!");
         }
         else{
               spMoi.setMaSP(maSP);
         }
      } while (maSP.isEmpty() || timThay);
      
      // nhập tên
      do {
         System.out.print("\nMời Nhập Tên Sản Phẩm: ");
         tenSP = scanner.nextLine().trim();
         if(tenSP.isEmpty()){
               System.out.println("Lỗi: Tên sản phẩm không được để trống!");
         }
      } while(tenSP.isEmpty());
      spMoi.setTenSP(tenSP);
      
      // số lượng tồn 
      do{
         System.out.print("\nMời Nhập Số Lượng Tồn: ");
         try{
               soLuongTon = Integer.parseInt(scanner.nextLine());
               if(soLuongTon < 0){
                  System.out.println("Số lượng tồn phải lớn hơn hoặc bằng 0!!");
               }
         }   
         catch (NumberFormatException e){
               System.out.println("Lỗi: Vui lòng nhập số nguyên hợp lệ!!");
               soLuongTon = -1;
         }
      }while (soLuongTon < 0);
      spMoi.setSoLuongTon(soLuongTon);
      
      // đơn giá
      do{
         System.out.print("\nMời Nhập Đơn Giá Bán: ");
         try {
               donGiaBan = Integer.parseInt(scanner.nextLine());
               if(donGiaBan <= 0){
                  System.out.println("Đơn giá phải lớn hơn 0!!");
               }
         } catch (NumberFormatException e) {
               System.out.println("Lỗi: Vui lòng nhập số nguyên hợp lệ!!");
               donGiaBan = -1;
         }
      }while (donGiaBan <= 0);
      spMoi.setDonGiaBan(donGiaBan);
      
      //LAPTOP
      if(spMoi instanceof Laptop){
         Laptop lt = (Laptop)spMoi;
         boolean isCpu, isRam;
         
         // CPU
         do {        
               isCpu = false;
               String[] danhSachCPU = Laptop.getDanhSachCpu();
               System.out.println("\nDanh sách CPU hỗ trợ: " + java.util.Arrays.toString(danhSachCPU));
               System.out.print("Mời nhập CPU: ");
               cpu = scanner.nextLine().trim();
               
               if(cpu.isEmpty()){
                  System.out.println("Lỗi: CPU không được để trống!");
                  continue;
               }
               
               // Kiểm tra CPU có hợp lệ không (đã sửa logic)
               isCpu = Laptop.kiemTraCpuHopLe(cpu);
               if(!isCpu){
                  System.out.println("Lỗi: CPU '" + cpu + "' không hợp lệ! Vui lòng chọn trong danh sách.");
               }
               else{
                  lt.setCpu(cpu);
               }
         } while (!isCpu);
         
         // RAM
         do {
               isRam = false;
               String[] danhSachRAM = Laptop.getDanhSachDungLuongRam();
               System.out.println("\nDanh sách RAM hỗ trợ: " + java.util.Arrays.toString(danhSachRAM));
               System.out.print("Mời nhập RAM: ");  
               ram = scanner.nextLine().trim();
               
               if(ram.isEmpty()){
                  System.out.println("Lỗi: RAM không được để trống!");
                  continue;
               }
               
               // Kiểm tra RAM có hợp lệ không (đã sửa logic)
               isRam = Laptop.kiemTraDungLuongRam(ram);
               if(!isRam){
                  System.out.println("Lỗi: Dung lượng RAM '" + ram + "' không hợp lệ! Vui lòng chọn trong danh sách.");
               }
               else{
                  lt.setRam(ram);
               }
         } while (!isRam);
      }
      // LINH KIỆN
      else if (spMoi instanceof LinhKien){
         LinhKien lk = (LinhKien)spMoi;
         boolean isLK;
         
         // LOẠI LINH KIỆN
         do {
               isLK = false;
               String[] danhSachLoaiLK = LinhKien.getLoaiLinhKienHopLe();
               System.out.println("\nDanh sách Loại Linh Kiện hỗ trợ: " + java.util.Arrays.toString(danhSachLoaiLK));
               System.out.print("\nMời Nhập Loại Linh Kiện: ");
               loaiLinhKien = scanner.nextLine().trim();
               
               if(loaiLinhKien.isEmpty()){
                  System.out.println("Lỗi: Loại linh kiện không được để trống!");
                  continue;
               }
               
               // kiểm tra hợp lệ
               isLK = LinhKien.kiemTraHopLe(loaiLinhKien);
               if (!isLK) {
                  System.out.println("Lỗi: Loại Linh Kiện '" + loaiLinhKien + "' không hợp lệ! Vui lòng chọn trong danh sách.");
               }
               else{
                  lk.setLoaiLinhKien(loaiLinhKien);
               }
         } while (!isLK);
         
         // THÔNG SỐ KỸ THUẬT 
         do {
               System.out.print("\nMời Nhập Thông Số Kỹ Thuật: ");
               thongSoKyThuat = scanner.nextLine().trim();
               if(thongSoKyThuat.isEmpty()){
                  System.out.println("Lỗi: Thông số kỹ thuật không được để trống!");
               }
         } while(thongSoKyThuat.isEmpty());
         lk.setThongSoKyThuat(thongSoKyThuat);
      }
      
      //thêm sản phẩm
      add(spMoi);
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
         System.out.printf("%-15s||%-20s||%-15s||%-15s||%-10s||%s\n", "Mã SP", "Tên SP", "Số Lượng Tồn", "Đơn Giá Bán", "Loại/CPU", "Thông số/RAM");
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
      // Thống kê sản phẩm theo loại (Laptop, Linh Kiện)
   public void thongKeSanPhamTheoLoai() {
      if (isEmpty()) {
         System.out.println("Danh sách Sản Phẩm trống !!");
         System.out.println("Tổng số lượng Laptop : 0");
         System.out.println("Tổng số lượng Linh Kiện : 0");
         return;
      }

      // Thu thập các giá trị CPU/RAM/Loại LK trực tiếp từ dữ liệu hiện có
      String[] VAL_CPU = new String[0];
      String[] VAL_RAM = new String[0];
      String[] VAL_LOAI_LINH_KIEN = new String[0];
      for (SanPham sp : dsSanPham) {
         if (sp instanceof Laptop) {
            Laptop ltTmp = (Laptop) sp;
            String cpu = ltTmp.getCpu();
            String ram = ltTmp.getRam();
            boolean foundCpu = false, foundRam = false;
            for (int i = 0; i < VAL_CPU.length; i++) {
               if (VAL_CPU[i].equalsIgnoreCase(cpu)) { foundCpu = true; break; }
            }
            for (int i = 0; i < VAL_RAM.length; i++) {
               if (VAL_RAM[i].equalsIgnoreCase(ram)) { foundRam = true; break; }
            }
            if (!foundCpu) {
               VAL_CPU = java.util.Arrays.copyOf(VAL_CPU, VAL_CPU.length + 1);
               VAL_CPU[VAL_CPU.length - 1] = cpu;
            }
            if (!foundRam) {
               VAL_RAM = java.util.Arrays.copyOf(VAL_RAM, VAL_RAM.length + 1);
               VAL_RAM[VAL_RAM.length - 1] = ram;
            }
         } else if (sp instanceof LinhKien) {
            String loai = ((LinhKien) sp).getLoaiLinhKien();
            boolean foundLoai = false;
            for (int i = 0; i < VAL_LOAI_LINH_KIEN.length; i++) {
               if (VAL_LOAI_LINH_KIEN[i].equalsIgnoreCase(loai)) { foundLoai = true; break; }
            }
            if (!foundLoai) {
               VAL_LOAI_LINH_KIEN = java.util.Arrays.copyOf(VAL_LOAI_LINH_KIEN, VAL_LOAI_LINH_KIEN.length + 1);
               VAL_LOAI_LINH_KIEN[VAL_LOAI_LINH_KIEN.length - 1] = loai;
            }
         }
      }

      int slLT = 0;
      int slLK = 0;

      // Mảng đếm loại
      int[] cntCPU = new int[VAL_CPU.length];
      int[] cntRAM = new int[VAL_RAM.length];
      int[] cntLK = new int[VAL_LOAI_LINH_KIEN.length];

      // Đếm số lượng
      for (SanPham sp : dsSanPham) {
         if (sp instanceof Laptop) {
               slLT++;
               Laptop lt = (Laptop) sp;
               
               // Điếm CPU
               for (int i = 0; i < VAL_CPU.length; i++) {
                  if (VAL_CPU[i].equalsIgnoreCase(lt.getCpu())) {
                     cntCPU[i]++;
                     break;
                  }
               }
               
               // Điếm RAM
               for (int i = 0; i < VAL_RAM.length; i++) {
                  if (VAL_RAM[i].equalsIgnoreCase(lt.getRam())) {
                     cntRAM[i]++;
                     break;
                  }
               }
         }
         
         if (sp instanceof LinhKien) {
               slLK++;
               LinhKien lk = (LinhKien) sp;
               
      // Điếm linh kiện
               for (int i = 0; i < VAL_LOAI_LINH_KIEN.length; i++) {
                  if (VAL_LOAI_LINH_KIEN[i].equalsIgnoreCase(lk.getLoaiLinhKien())) {
                     cntLK[i]++;
                     break;
                  }
               }
         }
      }

      // ==================== HIỂN THỊ KẾT QUẢ ====================
      
      System.out.println("\n========== THỐNG KÊ SẢN PHẨM THEO LOẠI ==========");
      
      // Tổng quan
      System.out.println("\n--- TỔNG QUAN ---");
      System.out.printf("Tổng số Laptop      : %d\n", slLT);
      System.out.printf("Tổng số Linh kiện   : %d\n", slLK);
      System.out.printf("Tổng cộng           : %d sản phẩm\n", getSoLuongSanPham());

      // Chi tiết Laptop
      if (slLT > 0) {
         System.out.println("\n--- CHI TIẾT LAPTOP ---");
         
         // Thống kê theo CPU
         System.out.println("\n>>> Thống kê theo CPU:");
         System.out.println("┌────────────────────────┬─────────────┐");
         System.out.println("│      LOẠI CPU          │  SỐ LƯỢNG   │");
         System.out.println("├────────────────────────┼─────────────┤");
         for (int i = 0; i < VAL_CPU.length; i++) {
               if (cntCPU[i] > 0) {
                  System.out.printf("│ %-22s │ %11d │\n", VAL_CPU[i], cntCPU[i]);
               }
         }
         System.out.println("└────────────────────────┴─────────────┘");

         // Thống kê theo RAM
         System.out.println("\n>>> Thống kê theo RAM:");
         System.out.println("┌────────────────────────┬─────────────┐");
         System.out.println("│      LOẠI RAM          │  SỐ LƯỢNG   │");
         System.out.println("├────────────────────────┼─────────────┤");
         for (int i = 0; i < VAL_RAM.length; i++) {
               if (cntRAM[i] > 0) {
                  System.out.printf("│ %-22s │ %11d │\n", VAL_RAM[i], cntRAM[i]);
               }
         }
         System.out.println("└────────────────────────┴─────────────┘");
      }

      // Chi tiết Linh kiện
      if (slLK > 0) {
         System.out.println("\n--- CHI TIẾT LINH KIỆN ---");
         System.out.println("┌────────────────────────┬─────────────┐");
         System.out.println("│   LOẠI LINH KIỆN       │  SỐ LƯỢNG   │");
         System.out.println("├────────────────────────┼─────────────┤");
         for (int i = 0; i < VAL_LOAI_LINH_KIEN.length; i++) {
               if (cntLK[i] > 0) {
                  System.out.printf("│ %-22s │ %11d │\n", VAL_LOAI_LINH_KIEN[i], cntLK[i]);
               }
         }
         System.out.println("└────────────────────────┴─────────────┘");
      }
      
      System.out.println("\n=================================================\n");
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
