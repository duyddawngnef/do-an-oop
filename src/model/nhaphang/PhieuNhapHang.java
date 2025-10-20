
package model.nhaphang;
public class PhieuNhapHang {
    private String maPhieu;
    private String ngayNhap;
    private NhaCungCap nhaCungCap;
    private ChiTietPNH[] dsChiTiet;
    private int soLuongSP;
    private double tongTien;

    public PhieuNhapHang(String maPhieu, String ngayNhap, NhaCungCap ncc) {
        this.maPhieu = maPhieu;
        this.ngayNhap = ngayNhap;
        this.nhaCungCap = ncc;
        this.dsChiTiet = new ChiTietPNH[100]; 
        this.soLuongSP = 0;
        this.tongTien = 0;
    }

    public void themSanPham(ChiTietPNH sp) {
        if (soLuongSP < dsChiTiet.length) {
            dsChiTiet[soLuongSP] = sp;
            soLuongSP++;
            tongTien += sp.getThanhTienNhap();
        } else {
            System.out.println("Danh sách sản phẩm đã đầy!");
        }
    }

    public void xoaSanPham(String maSP) {
        for (int i = 0; i < soLuongSP; i++) {
            if (dsChiTiet[i].getMaSP().equalsIgnoreCase(maSP)) {
                tongTien -= dsChiTiet[i].getThanhTienNhap();
                for (int j = i; j < soLuongSP - 1; j++) {
                    dsChiTiet[j] = dsChiTiet[j + 1];
                }
                dsChiTiet[soLuongSP - 1] = null;
                soLuongSP--;
                System.out.println("Đã xóa sản phẩm " + maSP);
                return;
            }
        }
        System.out.println("Không tìm thấy mã sản phẩm cần xóa!");
    }

    public void hienThiPhieu() {
        System.out.println("\n===== PHIẾU NHẬP HÀNG =====");
        System.out.println("Mã phiếu: " + maPhieu);
        System.out.println("Ngày nhập: " + ngayNhap);
        nhaCungCap.hienThi();
        System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
        for (int i = 0; i < soLuongSP; i++) {
            dsChiTiet[i].hienThi();
        }
        System.out.printf("TỔNG TIỀN: %.2f\n", tongTien);
    }

    public double getTongTien() {
        return tongTien;
    }
}