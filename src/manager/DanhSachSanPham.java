package manager;
import java.util.Arrays;
import java.util.Scanner;
import model.sanpham.*;
public class DanhSachSanPham {
    SanPham[] dsSanPham = new SanPham[0];
     public DanhSachSanPham(){
        this.dsSanPham = new SanPham[0];
     }
     private  void them(){
      Scanner scanner = new Scanner(System.in);
      System.out.println("\n Bạn muốn thêm sản phẩm nào ?");
      System.out.println("1. Laptop");
      System.out.println("2. Linh kiện");
      System.out.println("Lựa chọn : ");
      int loaiSP = 0;
      do {
          try {
            loaiSP = Integer.parseInt(scanner.nextLine());
            if(loaiSP != 1 || loaiSP != 2){
               System.out.println("Số lượng không hợp lệ !!");
            }
         } 
         catch (NumberFormatException e) {
            System.out.println("Lỗi: Vui lòng nhập số nguyên!!");
         }
      } while (loaiSP != 1 || loaiSP != 2);
     
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
      scanner.close();
     }
     //Sửa Sản Phẩm 
     private void sua(){
      Scanner scanner = new Scanner(System.in);
      System.out.println("\n Nhập mã Sản Phẩm cần sửa : ");
      String masp = scanner.nextLine().trim();
      SanPham spCanSua = timSanPhamTheoMa(masp);
      if(spCanSua != null){
         System.out.println("Đang sửa cho thông tin cho sản phẩm " + spCanSua.getTenSP());
         System.out.println("Nhập lại thông tin (trừ Mã Sản Phẩm ) :");
         spCanSua.nhap();
      }
      else{
         System.out.println("Không tìm thấy với mã: " + masp);
      }
      scanner.close();
     }
     private void xoa(){
      Scanner scanner = new Scanner(System.in);
      System.out.println("Nhập mã sản phẩm cần xóa : ");
      String masp = scanner.nextLine().trim();
      SanPham spxoa = timSanPhamTheoMa(masp);
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
      scanner.close();
     }
     private void hienThiTatCa() {
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
      // Hàm hiển thị theo loại
      private void hienThiTheoLoai() {
         Scanner scanner = new Scanner(System.in);
         System.out.println("Bạn muốn hiển thị loại nào?");
         System.out.println("1. Laptop");
         System.out.println("2. Linh kiện");
         System.out.print("Lựa chọn: ");
         int loai = Integer.parseInt(scanner.nextLine());

         System.out.printf("%-15s||%-20s||%-15s||%-15s||%-15s||%s\n", "Mã SP", "Tên SP", "Số Lượng Tồn", "Đơn Giá Bán", "Loại/CPU", "Thông số/RAM");
         System.out.println("--------------------------------------------------------------------------------------------------");
         for (SanPham sp : dsSanPham) {
            if (loai == 1 && sp instanceof Laptop) {
                  sp.xuat();
            } else if (loai == 2 && sp instanceof LinhKien) {
                  sp.xuat();
            }
         }
         scanner.close();
      }
      public void add(SanPham sp){
      System.out.println("Thêm thành công !");
      dsSanPham = Arrays.copyOf(dsSanPham,dsSanPham.length+1);
      dsSanPham[dsSanPham.length-1] = sp;
     }
     private boolean kiemTraMaSP(String masp){
      for(SanPham sp : dsSanPham){
         if(masp.equalsIgnoreCase(sp.getMaSP())){
            return true;
         }
      }
      return false;
     }
     private SanPham timSanPhamTheoMa(String masp){
       for(SanPham sp : dsSanPham){
         if(masp.equalsIgnoreCase(sp.getMaSP())){
            return sp;
         }
      }
      return null;
     }
     private void remove(int i ){
      if(i < 0 || i >= dsSanPham.length){
         System.out.println("Vị trí index lỗi !!");
         return ;
      }
      SanPham removesp = dsSanPham[i];
      int numberRemove = dsSanPham.length -i -1;
      // lấy numbeRemove phần tử của dsSanPham (i+1) và đè lên chính dsSanPham(i)
      System.arraycopy(dsSanPham, i+1, dsSanPham, i, numberRemove);
      System.out.println("Xóa thành công !");
     }
     private boolean isEmpty(){
      if(this.dsSanPham.length == 0 )return true;
      return false;
     }
}
