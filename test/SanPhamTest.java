package test;
import model.sanpham.*;

public class SanPhamTest {
    public static void main(String[] args) {
        LinhKien sp = new LinhKien("SP01", "Ten", 100, 90, "CPU", "Ram");

        System.out.printf("%-15s||%-20s||%-15s||%-15s||%-15s||%s%n",
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng Tồn",
                "Đơn Giá Bán", "Loại Linh Kiện", "Thông Số Kỹ Thuật");

        sp.xuat();
        System.out.println();
        System.out.printf("SP: %s%n", sp);
    }
}
