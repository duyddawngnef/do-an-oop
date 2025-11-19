package model.nhansu;

public class PhongBan {
    private String maPB; // Mã phòng ban (ví dụ: BH, KT)
    private String tenPB; // Tên phòng ban

    // Constructor cơ bản
    public PhongBan(String maPB, String tenPB) {
        this.maPB = maPB;
        this.tenPB = tenPB;
    }

    // Getter và Setter
    public String getMaPB() {
        return maPB;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public String getTenPB() {
        return tenPB;
    }

    // Phương thức hiển thị thông tin phòng ban
    @Override
    public String toString() {
        return String.format("%-10s | %-25s ",
                maPB, tenPB);
    }

}