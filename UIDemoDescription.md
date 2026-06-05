
### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `txtUsername` | TextField | NOT NULL | Nhập tên đăng nhập |
| 2 | `txtPassword` | PasswordField | NOT NULL | Nhập mật khẩu |
| 3 | `btnLogin` | Button | | Gửi yêu cầu đăng nhập |
| 4 | `btnRegister` | Button/Link | | Chuyển sang màn hình đăng ký |
| 5 | `messageDialog` | Dialog | | Hiển thị thông báo lỗi hoặc kết quả đăng nhập |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnLogin_Click` | Đọc username/password, gọi `LoginManager.login()`, nếu hợp lệ thì chuyển vào màn hình tương ứng |
| 2 | `txtPassword_Enter` | Gọi lại xử lý đăng nhập khi người dùng nhấn Enter trong ô mật khẩu |
| 3 | `btnRegister_Click` | Gọi `MainFrame.showRegisterScreen()` để chuyển sang màn hình đăng ký |
| 4 | `login_InvalidInput` | Nếu thiếu dữ liệu hoặc sai tài khoản, hiển thị thông báo lỗi bằng `JOptionPane` |
| 5 | `login_Success` | Nếu tài khoản là User thì mở `DashboardUI`, nếu là Admin thì mở `AdminUI` |

## 3.3.2. Chức năng đăng ký

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `txtName` | TextField | NOT NULL | Nhập họ tên người dùng |
| 2 | `txtUsername` | TextField | NOT NULL, UNIQUE | Nhập tên đăng nhập |
| 3 | `txtPassword` | PasswordField | NOT NULL | Nhập mật khẩu |
| 4 | `txtAge` | TextField | NOT NULL, Number | Nhập tuổi |
| 5 | `genderGroup` | RadioButton Group | NOT NULL | Chọn giới tính |
| 6 | `txtHeight` | TextField | NOT NULL, Number | Nhập chiều cao |
| 7 | `txtWeight` | TextField | NOT NULL, Number | Nhập cân nặng |
| 8 | `cbGoal` | ComboBox | NOT NULL | Chọn mục tiêu tập luyện |
| 9 | `btnRegister` | Button | | Gửi thông tin đăng ký |
| 10 | `btnBack/btnCancel` | Button | | Quay lại màn hình đăng nhập |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnRegister_Click` | Đọc dữ liệu form, validate dữ liệu bắt buộc và kiểu số |
| 2 | `register_ValidInput` | Tạo `User`, gọi `RegisterManager.register(newUser)` để lưu tài khoản |
| 3 | `register_DuplicateUsername` | Nếu username đã tồn tại, hiển thị thông báo lỗi |
| 4 | `register_InvalidNumber` | Nếu tuổi/chiều cao/cân nặng sai định dạng, hiển thị thông báo lỗi |
| 5 | `register_Success` | Đăng ký thành công, chuyển về màn hình đăng nhập hoặc đăng nhập vào hệ thống |
| 6 | `btnBack_Click` | Gọi `MainFrame.showLoginScreen()` |

## 3.3.3. Chức năng ghi lại bài tập

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `exerciseCard` | Card/Panel | NOT NULL | Hiển thị bài tập được chọn từ thư viện |
| 2 | `lblExName` | Label | | Hiển thị tên bài tập |
| 3 | `lblExTarget` | Label | | Hiển thị nhóm cơ và thể loại bài tập |
| 4 | `txtWeight` | TextField | Number, tùy tracking type | Nhập mức tạ |
| 5 | `txtReps` | TextField | Number, tùy tracking type | Nhập số reps |
| 6 | `txtDistance` | TextField | Number, tùy tracking type | Nhập quãng đường |
| 7 | `txtTime` | TextField | Number, tùy tracking type | Nhập thời gian |
| 8 | `lblToggleHint` | Link/Label | | Bật/tắt gợi ý tự động cho set tiếp theo |
| 9 | `lblHintMsg` | Label | | Hiển thị thông điệp gợi ý |
| 10 | `btnSave` | Button | | Lưu log tập luyện hoặc lưu set tiếp theo |
| 11 | `btnBack` | Button | | Quay lại thư viện bài tập |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `exerciseCard_Click` | Chọn bài tập từ `ExerciseLibraryUI`, gọi `DashboardUI.navigateToExerciseInput(ex)` |
| 2 | `setSelectedExercise` | Cập nhật bài tập hiện tại và hiển thị các ô nhập phù hợp với `TrackingType` |
| 3 | `lblToggleHint_Click` | Đổi trạng thái bật/tắt gợi ý, gọi `updateHints()` |
| 4 | `updateHints` | Lấy log gần nhất và lịch sử log, gọi `WorkoutHandling.calculateNextSet()` |
| 5 | `btnSave_Click` | Validate dữ liệu, tạo `WorkoutLog` bằng Builder và gọi `WorkoutLogController.addWorkoutLog()` |
| 6 | `saveWorkoutLog_Success` | Lưu log thành công, xóa nội dung ô nhập và cập nhật lại gợi ý |
| 7 | `saveWorkoutLog_InvalidInput` | Nếu thiếu dữ liệu hoặc nhập sai kiểu số, hiển thị thông báo lỗi |
| 8 | `btnBack_Click` | Gọi `DashboardUI.showLibrary()` để quay lại thư viện |

## 3.3.4. Chức năng tra cứu thông tin dinh dưỡng

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `txtSearchFood` | TextField | NOT NULL | Nhập tên sản phẩm/món ăn cần tra cứu |
| 2 | `btnSearch` | Button | | Gửi yêu cầu tìm kiếm dinh dưỡng |
| 3 | `resultTable` | Table | | Hiển thị danh sách kết quả từ API |
| 4 | `tableModel` | TableModel | | Lưu dữ liệu hiển thị trong bảng kết quả |
| 5 | `btnAddFood` | Button | Cần chọn 1 dòng | Thêm sản phẩm đã chọn vào nhật ký dinh dưỡng |
| 6 | `loadingState` | Label/Dialog | | Thông báo trạng thái đang tìm hoặc không tìm thấy dữ liệu |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnSearch_Click` | Gọi `doSearch()` để bắt đầu tra cứu |
| 2 | `txtSearchFood_Enter` | Tìm kiếm nhanh khi người dùng nhấn Enter |
| 3 | `doSearch` | Gọi `NutritionLogController.lookupNutrition(keyword)` |
| 4 | `lookupNutrition` | Gọi `INutrition.getNutritionInfo()` thông qua `OpenFoodFactsAdapter` |
| 5 | `apiSearch_Done` | Đổ dữ liệu trả về vào `resultTable` hoặc hiển thị thông báo không tìm thấy |
| 6 | `btnAddFood_Click` | Lấy dòng đang chọn, tạo `NutritionLog` bằng Builder và gọi `addNutritionLog()` |
| 7 | `addNutritionLog_Success` | Lưu nutrition log và cập nhật các màn hình observer liên quan |
| 8 | `addNutritionLog_NoSelection` | Nếu chưa chọn sản phẩm, hiển thị thông báo yêu cầu chọn dòng |

## 3.3.5. Chức năng xem thống kê tổng quan

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `lblTotalVolume` | Label/StatBox | ReadOnly | Hiển thị tổng volume tập luyện |
| 2 | `lblTotalCalo` | Label/StatBox | ReadOnly | Hiển thị tổng năng lượng nạp |
| 3 | `workoutChart` | Chart/Panel | ReadOnly | Hiển thị thống kê workout |
| 4 | `nutritionChart` | Chart/Panel | ReadOnly | Hiển thị thống kê nutrition |
| 5 | `tblRecentWorkout` | Table | ReadOnly | Hiển thị workout log gần đây |
| 6 | `tblRecentNutrition` | Table | ReadOnly | Hiển thị nutrition log gần đây |
| 7 | `cbGoal` | ComboBox | NOT NULL | Chọn mục tiêu tập luyện hiện tại |
| 8 | `btnUpdateGoal` | Button | | Lưu thay đổi mục tiêu |
| 9 | `btnRefresh` | Button | | Làm mới dữ liệu thống kê |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnStats_Click` | Từ `DashboardUI`, chuyển sang màn hình `StatisticsUI` |
| 2 | `refreshData` | Gọi `StatisticsPresenter` để lấy tổng volume, tổng calo và log gần đây |
| 3 | `btnRefresh_Click` | Tải lại toàn bộ dữ liệu thống kê |
| 4 | `btnUpdateGoal_Click` | Cập nhật goal của user, gọi `StatisticsPresenter.updateGoal(user)` |
| 5 | `updateGoal_Success` | Gọi `WorkoutHandling.setGoal(user)` để cập nhật Strategy gợi ý set |
| 6 | `observer_Update` | Khi workout/nutrition log thay đổi, `StatisticsUI.update()` tự gọi `refreshData()` |
| 7 | `noData_State` | Nếu chưa có log, hiển thị giá trị mặc định hoặc bảng rỗng |

## 3.3.6. Chức năng quản lý logs

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `tabWorkout` | Button/Tab | | Chuyển sang danh sách workout log |
| 2 | `tabNutrition` | Button/Tab | | Chuyển sang danh sách nutrition log |
| 3 | `workoutLogPanel` | Panel/List | ReadOnly | Hiển thị danh sách workout log của user |
| 4 | `nutritionLogPanel` | Panel/List | ReadOnly | Hiển thị danh sách nutrition log của user |
| 5 | `workoutLogCard` | Card/Panel | | Hiển thị thông tin từng workout log |
| 6 | `nutritionLogCard` | Card/Panel | | Hiển thị thông tin từng nutrition log |
| 7 | `btnDeleteWorkoutLog` | Button | Cần có log | Xóa workout log |
| 8 | `btnDeleteNutritionLog` | Button | Cần có log | Xóa nutrition log |
| 9 | `confirmDialog` | Dialog | | Xác nhận trước khi xóa log |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnLogs_Click` | Từ `DashboardUI`, chuyển sang màn hình `ManageLogUI` |
| 2 | `tabWorkout_Click` | Hiển thị panel workout log và đổi style tab |
| 3 | `tabNutrition_Click` | Hiển thị panel nutrition log và đổi style tab |
| 4 | `loadWorkoutData` | Lấy workout log từ `WorkoutLogController.getAllLogs()`, lọc theo user hiện tại |
| 5 | `loadNutritionData` | Lấy nutrition log từ `NutritionLogController.getAllLogs()`, lọc theo user hiện tại |
| 6 | `btnDeleteWorkoutLog_Click` | Confirm, sau đó gọi `WorkoutLogController.removeWorkoutLog(logID)` |
| 7 | `btnDeleteNutritionLog_Click` | Confirm, sau đó gọi `NutritionLogController.removeNutritionLog(logID)` |
| 8 | `deleteLog_Success` | Xóa log thành công, lưu JSON và gọi `notifyObservers()` |
| 9 | `observer_Update` | Khi log thay đổi, `ManageLogUI.update()` tự load lại danh sách |

## 3.3.7. Chức năng quản lý thư viện

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `filterButtons` | ToggleButton/Chip | | Lọc bài tập theo danh mục |
| 2 | `btnSuggest` | Button | | Bật/tắt danh sách bài tập đề xuất |
| 3 | `cardsContainer` | Panel/List | ReadOnly | Hiển thị danh sách card bài tập |
| 4 | `exerciseCard` | Card/Panel | | Hiển thị tên bài tập, nhóm cơ và icon |
| 5 | `btnAddExercise` | Button | Admin only | Mở dialog thêm bài tập |
| 6 | `btnEditExercise` | Button | Admin only | Mở dialog sửa bài tập |
| 7 | `btnDeleteExercise` | Button | Admin only | Xóa bài tập |
| 8 | `txtName` | TextField | NOT NULL | Nhập tên bài tập trong dialog |
| 9 | `txtMuscle` | TextField | NULL | Nhập nhóm cơ mục tiêu |
| 10 | `cbParentCategory` | ComboBox | NOT NULL | Chọn danh mục cha |
| 11 | `cbSubCategory` | ComboBox | NOT NULL | Chọn danh mục con |
| 12 | `cbTracking` | ComboBox | NOT NULL | Chọn hình thức theo dõi bài tập |
| 13 | `btnSave` | Button | | Lưu thêm/sửa bài tập |
| 14 | `btnCancel` | Button | | Đóng dialog thêm/sửa |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnLibrary_Click` | Từ `DashboardUI` hoặc `AdminUI`, chuyển sang màn hình thư viện |
| 2 | `filterButton_Click` | Cập nhật `activeFilter`, render lại danh sách bài tập |
| 3 | `btnSuggest_Click` | Gọi `ExerciseSuggestionService.suggest(user, library)` để lấy bài tập đề xuất |
| 4 | `exerciseCard_Click` | Với user, chuyển sang màn hình ghi log bài tập |
| 5 | `btnAddExercise_Click` | Với admin, mở dialog thêm bài tập |
| 6 | `cbParentCategory_Change` | Cập nhật danh sách danh mục con tương ứng |
| 7 | `cbSubCategory_Change` | Cập nhật danh sách tracking type hợp lệ |
| 8 | `btnSaveAdd_Click` | Validate dữ liệu, gọi `AdminController.addExercise()` |
| 9 | `btnEditExercise_Click` | Mở dialog sửa bài tập đã chọn |
| 10 | `btnSaveEdit_Click` | Validate dữ liệu, gọi `AdminController.updateExercise()` |
| 11 | `btnDeleteExercise_Click` | Confirm, gọi `AdminController.deleteExercise(exerciseName)` |
| 12 | `library_Update` | Sau khi thêm/sửa/xóa, `ExerciseLibrary.notifyObservers()` làm mới giao diện |

## 3.3.8. Chức năng xem chi tiết thông tin người dùng

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `btnUsers` | Button | Admin only | Chuyển sang danh sách người dùng |
| 2 | `userListPanel` | Panel/List | ReadOnly | Hiển thị danh sách user |
| 3 | `userCard` | Card/Panel | | Hiển thị tên, username và mục tiêu của user |
| 4 | `userDetailPanel` | Panel | ReadOnly | Hiển thị thông tin chi tiết người dùng |
| 5 | `lblUsername` | Label | ReadOnly | Hiển thị tên đăng nhập |
| 6 | `lblGender` | Label | ReadOnly | Hiển thị giới tính |
| 7 | `lblAge` | Label | ReadOnly | Hiển thị tuổi |
| 8 | `lblHeight` | Label | ReadOnly | Hiển thị chiều cao |
| 9 | `lblWeight` | Label | ReadOnly | Hiển thị cân nặng |
| 10 | `lblGoal` | Label | ReadOnly | Hiển thị mục tiêu tập luyện |
| 11 | `workoutHistoryPanel` | Panel/List | ReadOnly | Hiển thị lịch sử tập luyện của user |
| 12 | `btnBack` | Button | | Quay lại danh sách người dùng |
| 13 | `btnLogout` | Button | | Đăng xuất tài khoản admin |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnUsers_Click` | Gọi `refreshUserListPanel()` và hiển thị màn hình danh sách user |
| 2 | `refreshUserListPanel` | Gọi `AdminController.viewUserDetails()` để lấy danh sách user |
| 3 | `userCard_Click` | Gọi `showUserDetails(user)` để mở màn hình chi tiết |
| 4 | `showUserDetails` | Hiển thị thông tin cá nhân của user được chọn |
| 5 | `createWorkoutHistoryPanel` | Lấy workout log từ `WorkoutLogController.getAllLogs()`, lọc theo `userID` |
| 6 | `btnBack_Click` | Quay lại màn hình danh sách người dùng |
| 7 | `btnLogout_Click` | Confirm đăng xuất, sau đó gọi `MainFrame.showLoginScreen()` |
| 8 | `noWorkoutHistory_State` | Nếu user chưa có log tập luyện, hiển thị thông báo chưa có nhật ký |
