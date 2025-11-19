package model.cauhinh;

public class LoaiLinhKien {
    private String maLoai;
    private String tenLoai;
    private String moTa;
    
    public LoaiLinhKien() {
        this.maLoai = "";
        this.tenLoai = "";
        this.moTa = "";
    }
    
    public LoaiLinhKien(String maLoai, String tenLoai, String moTa) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
    }
    
    public LoaiLinhKien(LoaiLinhKien loai) {
        this.maLoai = loai.maLoai;
        this.tenLoai = loai.tenLoai;
        this.moTa = loai.moTa;
    }
    
    public String getMaLoai() {
        return maLoai;
    }
    
    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }
    
    public String getTenLoai() {
        return tenLoai;
    }
    
    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
    
    public String getMoTa() {
        return moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    @Override
    public String toString() {
        return tenLoai;
    }
    
}
