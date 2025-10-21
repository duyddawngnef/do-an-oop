package Interface;
public interface isList {
    //đọc file
    void read(String filename);
    //ghi file
    void write(String filename);
    //thêm
    void them();
    //xóa
    void xoa();
    //sửa
    void sua(String ma);
    //tìm theo mã -> index
    int timTheoMa(String ma);
    //in ds
    void in();

}
