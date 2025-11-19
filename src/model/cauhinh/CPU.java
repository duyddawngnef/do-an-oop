package model.cauhinh;

public class CPU {
    private String maCPU;
    private String tenCPU;
    private String hangSanXuat;
    
    public CPU() {
        this.maCPU = "";
        this.tenCPU = "";
        this.hangSanXuat = "";
    }
    
    public CPU(String maCPU, String tenCPU, String hangSanXuat) {
        this.maCPU = maCPU;
        this.tenCPU = tenCPU;
        this.hangSanXuat = hangSanXuat;
    }
    
    public CPU(CPU cpu) {
        this.maCPU = cpu.maCPU;
        this.tenCPU = cpu.tenCPU;
        this.hangSanXuat = cpu.hangSanXuat;
    }
    
    public String getMaCPU() {
        return maCPU;
    }
    
    public void setMaCPU(String maCPU) {
        this.maCPU = maCPU;
    }
    
    public String getTenCPU() {
        return tenCPU;
    }
    
    public void setTenCPU(String tenCPU) {
        this.tenCPU = tenCPU;
    }
    
    public String getHangSanXuat() {
        return hangSanXuat;
    }
    
    public void setHangSanXuat(String hangSanXuat) {
        this.hangSanXuat = hangSanXuat;
    }
    public void xuat() {
        System.out.printf("%-15s||%-20s||%-15s\n", getMaCPU(), getTenCPU(), getHangSanXuat());
    }
    @Override
    public String toString() {
        return tenCPU;
    }
}
