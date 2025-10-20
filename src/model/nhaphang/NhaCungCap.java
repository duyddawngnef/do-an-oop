
package model.nhaphang;

public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String soDienThoai;

    public NhaCungCap(String maNCC, String tenNCC, String diaChi, String soDienThoai) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

   
    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }
    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public void hienThi() {
    System.out.println("Nhà cung cấp: " + tenNCC + " | Địa chỉ: " + diaChi + " | SDT: " + soDienThoai);
}
    @Override
    public String toString() {
        return String.format("Mã NCC: %s | Tên NCC: %s | Địa chỉ: %s | SĐT: %s",
                maNCC, tenNCC, diaChi, soDienThoai);
    }
 
}