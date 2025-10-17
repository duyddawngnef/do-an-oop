
package model.nhaphang;
import java.util.Scanner;

public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String soDienThoai;
    private String diaChi;

    public NhaCungCap() {}

    public NhaCungCap(String maNCC, String tenNCC, String soDienThoai, String diaChi) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public void nhap(Scanner sc) {
        System.out.print("Nhập mã NCC: ");
        maNCC = sc.nextLine();
        System.out.print("Nhập tên NCC: ");
        tenNCC = sc.nextLine();
        System.out.print("Nhập số điện thoại: ");
        soDienThoai = sc.nextLine();
        System.out.print("Nhập địa chỉ: ");
        diaChi = sc.nextLine();
    }

    public void xuat() {
        System.out.println("Mã NCC: " + maNCC);
        System.out.println("Tên NCC: " + tenNCC);
        System.out.println("SĐT: " + soDienThoai);
        System.out.println("Địa chỉ: " + diaChi);
    }

    // Getter Setter
    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}
