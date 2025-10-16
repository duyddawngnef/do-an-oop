import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PhieuNhapHang p = new PhieuNhapHang();
        p.nhap(sc);
        p.xuat();
        sc.close();
    }
}
