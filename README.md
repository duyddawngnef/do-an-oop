```markdown
```txt
 QuanLyCuaHangMayTinh/
 └── README.md                                   # Hướng dẫn dự án
 ```
 
 ## Java 21 upgrade

 This repository has been updated with a Maven build configuration that targets Java 21.

 Files changed/added:
 - `pom.xml` — new Maven POM configured to compile with Java 21 (maven-compiler-plugin release=21) and an enforcer rule requiring Java 21.

 Quick steps to use Java 21 locally (Windows / cmd.exe):

 1. Install JDK 21 from a vendor of your choice (Adoptium, Oracle, Azul, etc.).
 2. Set JAVA_HOME system-wide (run as Administrator):

	 setx JAVA_HOME "C:\Program Files\Java\jdk-21" /M
	 setx PATH "%JAVA_HOME%\\bin;%PATH%" /M

	 Then open a new cmd window and verify:

	 java -version

 3. Install Apache Maven (3.9+ recommended). Verify:

	 mvn -v

 4. Build the project with Maven (from the repo root):

	 mvn -T1C clean test

 Notes:
 - The `maven-enforcer-plugin` in `pom.xml` requires Java 21 — Maven will fail the build if Java < 21 is used.
 - If you prefer Gradle or another build tool, let me know and I can add a Gradle configuration instead.
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
