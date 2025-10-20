
package model.nhaphang;

public class ChiTietPNH {
    private String maSP;
    private String tenSP;
    private int soLuongNhap;
    private double donGiaNhap;
    private double thanhTienNhap;

    public ChiTietPNH(String maSP, String tenSP, int soLuongNhap, double donGiaNhap) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongNhap = soLuongNhap;
        this.donGiaNhap = donGiaNhap;
        this.thanhTienNhap = soLuongNhap * donGiaNhap;
    }

    public double getThanhTienNhap() {
        return thanhTienNhap;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void hienThi() {
    System.out.printf("Mã SP: %s | Tên SP: %s | SL: %d | Đơn giá: %.2f | Thành tiền: %.2f\n",
            maSP, tenSP, soLuongNhap, donGiaNhap, thanhTienNhap);
}
    @Override
    public String toString() {
        return String.format("%-10s %-20s %-10d %-15.2f %-15.2f",
                maSP, tenSP, soLuongNhap, donGiaNhap, thanhTienNhap);
    }
    

}