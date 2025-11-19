package model.cauhinh;

public class RAM {
    private String maRAM;
    private String dungLuong;
    
    public RAM() {
        this.maRAM = "";
        this.dungLuong = "";
    }
    
    public RAM(String maRAM, String dungLuong) {
        this.maRAM = maRAM;
        this.dungLuong = dungLuong;
    }
    
    public RAM(RAM ram) {
        this.maRAM = ram.maRAM;
        this.dungLuong = ram.dungLuong;
    }
    
    public String getMaRAM() {
        return maRAM;
    }
    
    public void setMaRAM(String maRAM) {
        this.maRAM = maRAM;
    }
    
    public String getDungLuong() {
        return dungLuong;
    }
    
    public void setDungLuong(String dungLuong) {
        this.dungLuong = dungLuong;
    }
    
    @Override
    public String toString() {
        return dungLuong;
    }
    
}
