# Danh sách sự kiện theo chức năng

Tài liệu này tổng hợp các sự kiện UI chính theo từng chức năng của hệ thống Gym Tracking. Các sự kiện được rà từ source code trong package `view` và luồng xử lý qua `controller/model/util`.

## 1. Chức năng đăng nhập

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `LoginForm` | `btnLogin.addActionListener` | Người dùng bấm nút đăng nhập | Gọi `handleLogin()` -> `LoginManager.login(username, password)` | Nếu là `Admin` thì mở `AdminUI`, nếu là `User` thì mở `DashboardUI` |
| 2 | `LoginForm` | `txtPassword.addActionListener` | Người dùng nhấn Enter trong ô mật khẩu | Gọi lại `handleLogin()` | Đăng nhập nhanh không cần bấm nút |
| 3 | `LoginForm` | `btnRegister.addActionListener` | Người dùng bấm nút đăng ký | Gọi `MainFrame.showRegisterScreen()` | Chuyển sang màn hình đăng ký |
| 4 | `LoginForm` | `JOptionPane.showMessageDialog` | Thiếu input hoặc sai tài khoản | Kiểm tra input trước/sau khi gọi `LoginManager` | Hiển thị thông báo lỗi |

## 2. Chức năng đăng ký

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `RegisterForm` | `btnRegister.addActionListener` | Người dùng bấm đăng ký | Đọc form -> tạo `User` -> gọi `RegisterManager.register(newUser)` | Lưu user mới nếu username chưa tồn tại |
| 2 | `RegisterForm` | `btnBack.addActionListener` | Người dùng bấm quay lại | Gọi `MainFrame.showLoginScreen()` | Quay về đăng nhập |
| 3 | `RegisterForm` | `btnCancel.addActionListener` | Người dùng hủy đăng ký | Gọi `MainFrame.showLoginScreen()` | Quay về đăng nhập |
| 4 | `RegisterForm` | `btnRegister.addMouseListener` | Hover nút đăng ký | Đổi trạng thái hiển thị của nút | Tăng phản hồi giao diện |
| 5 | `RegisterForm` | `JOptionPane.showMessageDialog` | Thiếu input, sai kiểu số, trùng username | Validate form và kết quả register | Hiển thị lỗi hoặc thông báo thành công |

## 3. Chức năng ghi lại bài tập

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `ExerciseLibraryUI` | `card.addMouseListener.mouseClicked` | Người dùng click card bài tập | Gọi `DashboardUI.navigateToExerciseInput(ex)` -> `ExerciseUI.setSelectedExercise(ex)` | Mở form ghi log cho bài tập đã chọn |
| 2 | `ExerciseUI` | `btnSave.addActionListener` | Người dùng bấm lưu/set tiếp theo | Validate input -> tạo `WorkoutLog.WorkoutLogBuilder` -> `WorkoutLogController.addWorkoutLog(log)` | Lưu workout log và gọi `notifyObservers()` |
| 3 | `ExerciseUI` | `lblToggleHint.addMouseListener.mouseClicked` | Người dùng bật/tắt gợi ý tự động | Đổi `isHintEnabled` -> gọi `updateHints()` | Hiển thị hoặc xóa hint set tiếp theo |
| 4 | `ExerciseUI` | `btnBack.addActionListener` | Người dùng bấm quay lại | Gọi `DashboardUI.showLibrary()` | Quay về thư viện bài tập |
| 5 | `ExerciseUI` | `updateHints()` | Bật gợi ý hoặc sau khi lưu log | Lấy `WorkoutLogController.getAllLogs()` -> tìm log tuần -> `WorkoutHandling.calculateNextSet(...)` | Điền hint dựa trên `NextSetRecommendationStrategy` |
| 6 | `WorkoutLogController` | `notifyObservers()` | Thêm/xóa workout log thành công | Gọi `update()` trên observer | `StatisticsUI`, `ManageLogUI` tự refresh |

## 4. Chức năng tra cứu thông tin dinh dưỡng

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `NutritionUI` | `btnSearch.addActionListener` | Người dùng bấm tìm | Gọi `doSearch()` | Bắt đầu tra cứu dinh dưỡng |
| 2 | `NutritionUI` | `txtSearchFood.addActionListener` | Người dùng nhấn Enter trong ô tìm kiếm | Gọi `doSearch()` | Tìm nhanh bằng bàn phím |
| 3 | `NutritionUI` | `SwingWorker.doInBackground` | Sau khi gọi `doSearch()` | Gọi `NutritionLogController.lookupNutrition(keyword)` -> `INutrition.getNutritionInfo()` -> `OpenFoodFactsAdapter` -> `OpenFoodFactsAPI` | Lấy danh sách sản phẩm không khóa UI |
| 4 | `NutritionUI` | `SwingWorker.done` | API trả kết quả | Đổ dữ liệu vào `DefaultTableModel` | Hiển thị bảng kết quả hoặc báo không tìm thấy |
| 5 | `NutritionUI` | `btnAddFood.addActionListener` | Người dùng chọn sản phẩm và bấm thêm | Tạo `NutritionLog.Builder` -> `NutritionLogController.addNutritionLog(logToSave)` | Lưu nutrition log và gọi `notifyObservers()` |
| 6 | `NutritionUI` | `btnAddFood.addMouseListener` | Hover nút thêm món | Đổi màu nút khi enabled | Tăng phản hồi giao diện |

## 5. Chức năng xem thống kê tổng quan

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `DashboardUI` | `btnStats.addActionListener` | Người dùng chọn tab tiến độ/thống kê | Gọi `switchTo("STATS", btnStats)` | Hiển thị `StatisticsUI` |
| 2 | `StatisticsUI` | `btnRefresh.addActionListener` | Người dùng bấm làm mới | Gọi `refreshData()` | Tải lại chart và log gần đây |
| 3 | `StatisticsUI` | `btnUpdateGoal.addActionListener` | Người dùng chọn mục tiêu mới và bấm lưu | `StatisticsPresenter.updateGoal(user)` -> `WorkoutHandling.setGoal(user)` | Cập nhật mục tiêu và strategy gợi ý set |
| 4 | `StatisticsUI` | `update()` | Workout/nutrition log thay đổi | Observer callback gọi `refreshData()` | Biểu đồ và bảng gần đây tự cập nhật |
| 5 | `StatisticsPresenter` | Các method thống kê | Khi `StatisticsUI.refreshData()` gọi | Đọc `LogCollection`, lọc theo user/date | Trả dữ liệu chart, tổng volume, tổng kcal |

## 6. Chức năng quản lý logs

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `DashboardUI` | `btnLogs.addActionListener` | Người dùng chọn tab nhật ký | Gọi `switchTo("LOGS", btnLogs)` | Hiển thị `ManageLogUI` |
| 2 | `ManageLogUI` | `tabWorkout.addActionListener` | Người dùng chọn tab workout | Hiển thị workout panel và đổi style tab | Xem danh sách workout log |
| 3 | `ManageLogUI` | `tabNutrition.addActionListener` | Người dùng chọn tab nutrition | Hiển thị nutrition panel và đổi style tab | Xem danh sách nutrition log |
| 4 | `ManageLogUI` | `btnDel.addActionListener` ở workout panel | Người dùng bấm xóa log tập | Confirm -> `WorkoutLogController.removeWorkoutLog(logID)` | Xóa workout log và notify observer |
| 5 | `ManageLogUI` | `btnDel.addActionListener` ở nutrition panel | Người dùng bấm xóa log dinh dưỡng | Confirm -> `NutritionLogController.removeNutritionLog(logID)` | Xóa nutrition log và notify observer |
| 6 | `ManageLogUI` | `update()` | Subject notify sau khi log thay đổi | Gọi `loadWorkoutData()` và `loadNutritionData()` | Bảng log tự refresh theo user hiện tại |

## 7. Chức năng quản lý thư viện bài tập

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `DashboardUI` | `btnLibrary.addActionListener` | User chọn tab thư viện | Gọi `switchTo("LIBRARY", btnLibrary)` | Hiển thị `ExerciseLibraryUI` |
| 2 | `ExerciseLibraryUI` | `filterBtn.addActionListener` | Người dùng chọn chip category | Đổi `activeFilter` và render lại card | Lọc danh sách bài tập |
| 3 | `ExerciseLibraryUI` | `btnSuggest.addActionListener` | User bật/tắt gợi ý | Gọi `ExerciseSuggestionService.suggest(user, library)` | Hiển thị danh sách bài tập gợi ý |
| 4 | `ExerciseLibraryUI` | `btnAdd.addActionListener` | Admin bấm thêm bài tập | Mở `showAddExerciseDialog()` | Hiển thị dialog thêm bài |
| 5 | `ExerciseLibraryUI` | `btnEdit.addActionListener` | Admin bấm sửa bài tập | Mở `showEditExerciseDialog(ex)` | Hiển thị dialog sửa bài |
| 6 | `ExerciseLibraryUI` | `btnDel.addActionListener` | Admin bấm xóa bài tập | Confirm -> `AdminController.deleteExercise(exerciseName)` | Xóa bài, lưu JSON, notify thư viện |
| 7 | `ExerciseLibraryUI` | `cbParentCategory.addActionListener` | Admin đổi danh mục cha trong dialog | Cập nhật danh mục con tương ứng | Giới hạn lựa chọn category hợp lệ |
| 8 | `ExerciseLibraryUI` | `cbSubCategory.addActionListener` | Admin đổi danh mục con trong dialog | Cập nhật tracking type hợp lệ | Giới hạn tracking theo category |
| 9 | `ExerciseLibraryUI` | `btnSave.addActionListener` trong dialog thêm/sửa | Admin lưu thay đổi | Validate -> `AdminController.addExercise/updateExercise` | Thêm/cập nhật bài tập và refresh thư viện |
| 10 | `ExerciseLibraryUI` | `update()` | `ExerciseLibrary.notifyObservers()` | Render lại danh sách card | UI thư viện tự cập nhật |

## 8. Chức năng xem chi tiết thông tin người dùng

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `AdminUI` | `btnUsers.addActionListener` | Admin chọn tab người dùng | Gọi `refreshUserListPanel()` -> `cardLayout.show(cardPanel, "USERS")` | Hiển thị danh sách user |
| 2 | `AdminUI` | `userCard.addMouseListener.mouseClicked` | Admin click card user | Gọi `showUserDetails(user)` | Hiển thị chi tiết user và lịch sử tập |
| 3 | `AdminUI` | `btnBack.addActionListener` | Admin bấm quay lại | `cardLayout.show(cardPanel, "USERS")` | Quay về danh sách user |
| 4 | `AdminUI` | `createWorkoutHistoryPanel(user)` | Khi mở chi tiết user | Gọi `WorkoutLogController.getAllLogs()`, lọc theo `userID` | Hiển thị lịch sử tập luyện của user |
| 5 | `AdminUI` | `btnLibrary.addActionListener` | Admin chọn thư viện | `cardLayout.show(cardPanel, "LIBRARY")` | Quay sang quản lý thư viện |
| 6 | `AdminUI` | `btnLogout.addActionListener` | Admin bấm đăng xuất | Confirm -> `MainFrame.showLoginScreen()` | Đăng xuất admin |

## 9. Chức năng xem hồ sơ và đăng xuất

| STT | Màn hình/Lớp | Sự kiện | Trigger | Luồng xử lý | Kết quả |
|---|---|---|---|---|---|
| 1 | `DashboardUI` | `btnProfile.addActionListener` | User chọn tab hồ sơ | Gọi `switchTo("PROFILE", btnProfile)` | Hiển thị `ProfileUI` |
| 2 | `ProfileUI` | `btnLogout.addActionListener` | User bấm đăng xuất | Confirm -> `MainFrame.showLoginScreen()` | Đăng xuất user |
| 3 | `ProfileUI` | `btnLogout.addMouseListener` | Hover nút đăng xuất | Đổi màu nút | Tăng phản hồi giao diện |

