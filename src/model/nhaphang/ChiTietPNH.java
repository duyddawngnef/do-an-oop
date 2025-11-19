package model.nhaphang;

public class ChiTietPNH {
    private String maSP;
    private int soLuong;
    private double donGia;

    public ChiTietPNH() {
    }

    public ChiTietPNH(String maSP, int soLuong, double donGia) {
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public double thanhTien() {
        return soLuong * donGia;
    }

    @Override
    public String toString() {
        return maSP + ";" + soLuong + ";" + donGia + ";" + thanhTien();
    }

    // GET/SET
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}