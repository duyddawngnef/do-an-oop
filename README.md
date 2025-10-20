```txt
QuanLyCuaHangMayTinh/
│
├── src/
│   ├── main/
│   │   └── Main.java                          # Chương trình chính
│   │
│   ├── model/
│   │   ├── sanpham/
│   │   │   ├── SanPham.java                   # Lớp cha abstract (Duy Đăng)
│   │   │   ├── Laptop.java                    # Lớp con kế thừa (Duy Đăng)
│   │   │   └── LinhKien.java                  # Lớp con kế thừa (Duy Đăng)
│   │   │
│   │   ├── banhang/
│   │   │   ├── HoaDon.java                    # Quản lý hóa đơn (Văn Đạt)
│   │   │   ├── ChiTietHoaDon.java             # Chi tiết hóa đơn (Văn Đạt)
│   │   │   └── KhachHang.java                 # Thông tin khách hàng (Văn Đạt)
│   │   │
│   │   ├── nhaphang/
│   │   │   ├── PhieuNhapHang.java             # Quản lý phiếu nhập (Thanh Phong)
│   │   │   ├── ChiTietPNH.java                # Chi tiết phiếu nhập (Thanh Phong)
│   │   │   └── NhaCungCap.java                # Thông tin NCC (Thanh Phong)
│   │   │
│   │   └── nhansu/
│   │       └── NhanVien.java                  # Quản lý nhân viên (Thanh Phú)
│   │
│   ├── manager/
│   │   ├── QuanLyCuaHangMayTinh.java          # Lớp trung tâm (Thanh Phú)
│   │   ├── DanhSachSanPham.java               # Quản lý DS sản phẩm (Duy Đăng)
│   │   ├── DanhSachHoaDon.java                # Quản lý DS hóa đơn (Văn Đạt)
│   │   ├── DanhSachChiTietHoaDon.java         # Quản lý DS chi tiết HD (Văn Đạt)
│   │   ├── DanhSachKhachHang.java             # Quản lý DS khách hàng (Văn Đạt)
│   │   ├── DanhSachPhieuNhapHang.java         # Quản lý DS phiếu nhập (Thanh Phong)
│   │   ├── DanhSachChiTietPNH.java            # Quản lý DS chi tiết PNH (Thanh Phong)
│   │   ├── DanhSachNhaCungCap.java            # Quản lý DS nhà cung cấp (Thanh Phong)
│   │   └── DanhSachNhanVien.java              # Quản lý DS nhân viên (Thanh Phú)
│   │
│   ├── utils/
│   │   ├── Utils.java                         # Các hàm tiện ích (Thanh Phú)
│   │   ├── FileHandler.java                   # Xử lý đọc/ghi file
│   │   └── Validator.java                     # Kiểm tra dữ liệu đầu vào
│   │
│   ├── ui/
│   │   ├── Menu.java                          # Menu chính (Thanh Phú)
│   │   ├── SanPhamUI.java                     # Giao diện quản lý sản phẩm
│   │   ├── BanHangUI.java                     # Giao diện bán hàng
│   │   ├── NhapHangUI.java                    # Giao diện nhập hàng
│   │   └── NhanVienUI.java                    # Giao diện quản lý nhân viên
│   │
│   └── exception/
│       ├── KhongDuTonKhoException.java        # Exception tồn kho
│       ├── SanPhamKhongTonTaiException.java   # Exception sản phẩm
│       └── DuLieuKhongHopLeException.java     # Exception validation
│
├── data/                                       # Thư mục chứa file dữ liệu
│   ├── DanhSachSanPham.txt                    # Danh sách tất cả sản phẩm
│   ├── DanhSachHoaDon.txt                     # Danh sách hóa đơn
│   ├── DanhSachChiTietHoaDon.txt              # Danh sách chi tiết hóa đơn
│   ├── DanhSachKhachHang.txt                  # Danh sách khách hàng
│   ├── DanhSachPhieuNhapHang.txt              # Danh sách phiếu nhập
│   ├── DanhSachChiTietPNH.txt                 # Danh sách chi tiết phiếu nhập
│   ├── DanhSachNhaCungCap.txt                 # Danh sách nhà cung cấp
│   └── DanhSachNhanVien.txt                   # Danh sách nhân viên
│
├── docs/                                       # Tài liệu dự án
│   ├── PhanCongDoAn.docx                      # File phân công
│   ├── ClassDiagram.png                       # Sơ đồ lớp UML
│   └── BaoCaoDoAn.docx                        # Báo cáo đồ án
│
├── test/                                       # Thư mục test (optional)
│   ├── SanPhamTest.java
│   ├── HoaDonTest.java
│   └── QuanLyTest.java
│
└── README.md                                   # Hướng dẫn dự án
```
