# BÁO CÁO KIỂM TRA ĐỒNG BỘ CÁC DANH SÁCH

## TỔNG QUAN
Hệ thống quản lý cửa hàng máy tính sử dụng nhiều danh sách (manager classes) để quản lý dữ liệu. Báo cáo này kiểm tra tính đồng bộ giữa các danh sách.

---

## CÁC VẤN ĐỀ ĐỒNG BỘ ĐÃ PHÁT HIỆN

### 1. ⚠️ ĐỒNG BỘ GIỮA NHÂN VIÊN VÀ TÀI KHOẢN ĐĂNG NHẬP

**Vấn đề:**
- Khi thêm nhân viên mới, tài khoản được tạo tự động trong `DanhSachNhanVien.them()` (dòng 64)
- Khi xóa nhân viên, tài khoản tương ứng KHÔNG được xóa tự động
- Khi sửa mã nhân viên, tài khoản không được cập nhật

**Vị trí:**
- `DanhSachNhanVien.java`: Dòng 304-337 (xóa nhân viên)
- `DanhSachLogin.java`: Không có phương thức xóa tài khoản theo mã nhân viên

**Khuyến nghị:**
- Thêm logic xóa tài khoản khi xóa nhân viên
- Cập nhật tài khoản khi sửa mã nhân viên

---

### 2. ⚠️ ĐỒNG BỘ GIỮA PHÒNG BAN VÀ NHÂN VIÊN

**Vấn đề:**
- Khi xóa phòng ban, hệ thống kiểm tra xem có nhân viên thuộc phòng ban không (dòng 180)
- Tuy nhiên, khi xóa nhân viên, không có kiểm tra hoặc cập nhật số lượng nhân viên trong phòng ban
- Khi sửa mã phòng ban, nhân viên thuộc phòng ban đó không được cập nhật mã phòng ban mới

**Vị trí:**
- `DanhSachPhongBan.java`: Dòng 135-164 (xóa phòng ban)
- `DanhSachNhanVien.java`: Dòng 304-337 (xóa nhân viên)

**Khuyến nghị:**
- Khi xóa nhân viên, cập nhật thống kê phòng ban
- Khi sửa mã phòng ban, cập nhật tất cả nhân viên thuộc phòng ban đó

---

### 3. ⚠️ ĐỒNG BỘ GIỮA HÓA ĐƠN VÀ CHI TIẾT HÓA ĐƠN

**Vấn đề:**
- Khi xóa hóa đơn, chi tiết hóa đơn (file trong thư mục) KHÔNG được xóa
- Khi sửa hóa đơn, chi tiết được cập nhật nhưng tổng giá có thể không đồng bộ

**Vị trí:**
- `DanhSachHoaDon.java`: Dòng 185-211 (xóa hóa đơn)
- `DanhSachChiTietHoaDon.java`: File được lưu riêng theo mã hóa đơn

**Khuyến nghị:**
- Khi xóa hóa đơn, xóa file chi tiết tương ứng
- Đảm bảo tổng giá luôn được tính từ chi tiết

---

### 4. ⚠️ ĐỒNG BỘ GIỮA PHIẾU NHẬP VÀ CHI TIẾT PHIẾU NHẬP

**Vấn đề:**
- Tương tự hóa đơn, khi xóa phiếu nhập, chi tiết phiếu nhập không được xóa
- Tổng tiền của phiếu nhập có thể không đồng bộ với chi tiết

**Vị trí:**
- `DanhSachPhieuNhapHang.java`: Dòng 203-228 (xóa phiếu nhập)
- `DanhSachChiTietPNH.java`: File được lưu riêng theo mã phiếu

**Khuyến nghị:**
- Xóa file chi tiết khi xóa phiếu nhập
- Đảm bảo tổng tiền luôn được tính từ chi tiết

---

### 5. ⚠️ ĐỒNG BỘ TỒN KHO SẢN PHẨM

**Vấn đề:**
- Khi thêm chi tiết hóa đơn, tồn kho được trừ (dòng 213)
- Khi xóa chi tiết hóa đơn, tồn kho được hoàn lại (dòng 254)
- Khi sửa chi tiết hóa đơn, tồn kho được cập nhật (dòng 319)
- **NHƯNG:** Khi xóa hóa đơn, tồn kho KHÔNG được hoàn lại

**Vị trí:**
- `DanhSachChiTietHoaDon.java`: Dòng 243-268 (xóa chi tiết)
- `DanhSachHoaDon.java`: Dòng 185-211 (xóa hóa đơn - thiếu hoàn kho)

**Khuyến nghị:**
- Khi xóa hóa đơn, đọc chi tiết và hoàn lại tồn kho cho tất cả sản phẩm

---

### 6. ⚠️ ĐỒNG BỘ TỒN KHO KHI NHẬP HÀNG

**Vấn đề:**
- Khi thêm chi tiết phiếu nhập, tồn kho được cộng (dòng 242)
- Khi xóa chi tiết phiếu nhập, tồn kho được trừ lại (dòng 276)
- **NHƯNG:** Khi xóa phiếu nhập, tồn kho KHÔNG được trừ lại

**Vị trí:**
- `DanhSachChiTietPNH.java`: Dòng 265-290 (xóa chi tiết)
- `DanhSachPhieuNhapHang.java`: Dòng 203-228 (xóa phiếu nhập - thiếu trừ kho)

**Khuyến nghị:**
- Khi xóa phiếu nhập, đọc chi tiết và trừ lại tồn kho cho tất cả sản phẩm

---

### 7. ⚠️ ĐỒNG BỘ GIỮA HÓA ĐƠN VÀ KHÁCH HÀNG/NHÂN VIÊN

**Vấn đề:**
- Khi xóa khách hàng, không kiểm tra xem có hóa đơn nào liên quan không
- Khi xóa nhân viên, không kiểm tra xem có hóa đơn nào do nhân viên đó tạo không

**Vị trí:**
- `DanhSachKhachHang.java`: Dòng 284-316 (xóa khách hàng)
- `DanhSachNhanVien.java`: Dòng 304-337 (xóa nhân viên)
- `DanhSachHoaDon.java`: Có tham chiếu đến mã khách hàng và mã nhân viên

**Khuyến nghị:**
- Kiểm tra ràng buộc trước khi xóa khách hàng/nhân viên
- Hoặc đặt mã khách hàng/nhân viên thành null khi xóa

---

### 8. ⚠️ ĐỒNG BỘ GIỮA PHIẾU NHẬP VÀ NHÀ CUNG CẤP/NHÂN VIÊN

**Vấn đề:**
- Tương tự hóa đơn, khi xóa nhà cung cấp hoặc nhân viên, không kiểm tra phiếu nhập

**Vị trí:**
- `DanhSachNhaCungCap.java`: Dòng 145-175 (xóa nhà cung cấp)
- `DanhSachPhieuNhapHang.java`: Có tham chiếu đến mã NCC và mã NV

**Khuyến nghị:**
- Kiểm tra ràng buộc trước khi xóa

---

### 9. ⚠️ ĐỒNG BỘ DỮ LIỆU KHI ĐỌC FILE

**Vấn đề:**
- Trong `QuanLyCuaHangMayTinh.khoiTaoDuLieu()`, thứ tự đọc file có thể gây vấn đề:
  - Đọc phòng ban trước nhân viên (đúng)
  - Đọc hóa đơn trước chi tiết hóa đơn (có thể gây lỗi nếu chi tiết không tồn tại)
  - Đọc phiếu nhập trước chi tiết phiếu nhập (tương tự)

**Vị trí:**
- `QuanLyCuaHangMayTinh.java`: Dòng 38-85

**Khuyến nghĩ:**
- Thứ tự đọc hiện tại là hợp lý, nhưng cần xử lý lỗi tốt hơn khi file chi tiết không tồn tại

---

### 10. ⚠️ ĐỒNG BỘ KHI GHI FILE

**Vấn đề:**
- Khi ghi hóa đơn, chi tiết hóa đơn được ghi (dòng 78-82)
- Khi ghi phiếu nhập, chi tiết phiếu nhập KHÔNG được ghi (dòng 69-72 bị comment)

**Vị trí:**
- `DanhSachHoaDon.java`: Dòng 78-82 (ghi chi tiết)
- `DanhSachPhieuNhapHang.java`: Dòng 69-72 (không ghi chi tiết)

**Khuyến nghị:**
- Thêm logic ghi chi tiết phiếu nhập khi ghi phiếu nhập

---

## TÓM TẮT CÁC VẤN ĐỀ

| STT | Vấn đề | Mức độ | File liên quan |
|-----|--------|--------|----------------|
| 1 | Xóa nhân viên không xóa tài khoản | 🔴 Cao | DanhSachNhanVien, DanhSachLogin |
| 2 | Xóa hóa đơn không hoàn lại tồn kho | 🔴 Cao | DanhSachHoaDon, DanhSachChiTietHoaDon |
| 3 | Xóa phiếu nhập không trừ lại tồn kho | 🔴 Cao | DanhSachPhieuNhapHang, DanhSachChiTietPNH |
| 4 | Xóa hóa đơn không xóa file chi tiết | 🟡 Trung bình | DanhSachHoaDon |
| 5 | Xóa phiếu nhập không xóa file chi tiết | 🟡 Trung bình | DanhSachPhieuNhapHang |
| 6 | Xóa khách hàng không kiểm tra ràng buộc | 🟡 Trung bình | DanhSachKhachHang, DanhSachHoaDon |
| 7 | Xóa nhân viên không kiểm tra ràng buộc | 🟡 Trung bình | DanhSachNhanVien, DanhSachHoaDon |
| 8 | Sửa mã phòng ban không cập nhật nhân viên | 🟡 Trung bình | DanhSachPhongBan, DanhSachNhanVien |
| 9 | Ghi phiếu nhập không ghi chi tiết | 🟢 Thấp | DanhSachPhieuNhapHang |

---

## KHUYẾN NGHỊ TỔNG THỂ

1. **Thêm phương thức kiểm tra ràng buộc** trước khi xóa các entity có quan hệ
2. **Hoàn lại tồn kho** khi xóa hóa đơn/phiếu nhập
3. **Xóa file chi tiết** khi xóa hóa đơn/phiếu nhập
4. **Đồng bộ tài khoản** khi thêm/sửa/xóa nhân viên
5. **Cập nhật tham chiếu** khi sửa mã của các entity có quan hệ
6. **Thêm transaction** để đảm bảo tính nhất quán dữ liệu

---

## KẾT LUẬN

Hệ thống có nhiều vấn đề đồng bộ cần được xử lý, đặc biệt là:
- Đồng bộ tồn kho khi xóa hóa đơn/phiếu nhập
- Đồng bộ tài khoản khi xóa nhân viên
- Xóa file chi tiết khi xóa hóa đơn/phiếu nhập

Các vấn đề này có thể gây mất dữ liệu hoặc dữ liệu không nhất quán trong hệ thống.

