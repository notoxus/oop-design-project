# Gym Tracking App

Gym Tracking App là ứng dụng Android hỗ trợ người dùng theo dõi quá trình tập luyện và dinh dưỡng. Ứng dụng hướng đến người dùng muốn cải thiện sức khỏe, tăng cơ, giảm mỡ hoặc duy trì vóc dáng cân đối.

## Tính năng chính

### Người dùng

- Đăng ký, đăng nhập và quản lý thông tin cá nhân.
- Chọn mục tiêu tập luyện: giảm mỡ, tăng cơ hoặc duy trì.
- Xem thư viện bài tập theo danh mục.
- Nhận gợi ý bài tập phù hợp với BMI và mục tiêu tập luyện.
- Ghi lại nhật ký tập luyện theo từng set.
- Nhận gợi ý set tập tiếp theo dựa trên log gần nhất và lịch sử tập luyện.
- Tra cứu thông tin dinh dưỡng sản phẩm.
- Lưu nhật ký dinh dưỡng.
- Xem thống kê tổng quan về tập luyện và dinh dưỡng.
- Quản lý lịch sử workout log và nutrition log.
- Xem hồ sơ cá nhân và đăng xuất.

### Admin

- Xem danh sách người dùng.
- Xem chi tiết thông tin và lịch sử tập luyện của từng người dùng.
- Quản lý thư viện bài tập: thêm, sửa, xóa bài tập.

## Requirements

- Java Development Kit 21.
- Gradle hoặc Gradle Wrapper có sẵn trong project.
- Eclipse IDE hoặc IDE hỗ trợ Java/Gradle.
- Kết nối Internet để tra cứu dữ liệu dinh dưỡng.

## Thư viện sử dụng

- Gson: đọc và ghi dữ liệu JSON.
- JFreeChart: hiển thị biểu đồ thống kê.
- FlatLaf: cải thiện giao diện trong bản demo.
- JUnit 5: viết và chạy unit test.

## Cách chạy

Chạy chương trình:

```powershell
.\gradlew.bat run
```

Chạy test:

```powershell
.\gradlew.bat test
```

Build project:

```powershell
.\gradlew.bat build
```
