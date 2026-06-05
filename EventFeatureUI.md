# Mô tả giao diện và sự kiện theo màn hình

## Danh sách các màn hình

| STT | Màn hình | Mô tả |
|---|---|---|
| 1 | `MainFrame` | Màn hình chính, quản lý việc chuyển đổi giữa các panel |
| 2 | `DashboardUI` | Trang chủ của người dùng |
| 3 | `AdminUI` | Trang quản trị dành cho admin |
| 4 | `ExerciseLibraryUI` | Màn hình thư viện bài tập |
| 5 | `ExerciseUI` | Màn hình ghi nhận bài tập |
| 6 | `LoginForm` | Màn hình đăng nhập |
| 7 | `RegisterForm` | Màn hình đăng ký |
| 8 | `ManageLogUI` | Màn hình quản lý log |
| 9 | `NutritionUI` | Màn hình tra cứu sản phẩm dinh dưỡng |
| 10 | `ProfileUI` | Màn hình trang cá nhân của người dùng |
| 11 | `StatisticsUI` | Màn hình thống kê tổng quan |

## Màn hình đăng nhập

### Giao diện

Màn hình đăng nhập cho phép người dùng nhập tài khoản, mật khẩu và chuyển sang màn hình đăng ký nếu chưa có tài khoản.

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
| 1 | `btnLogin_Click` | Đọc username/password, gọi `LoginManager.login(username, password)`, nếu hợp lệ thì chuyển vào màn hình tương ứng |
| 2 | `txtPassword_Enter` | Gọi lại xử lý đăng nhập khi người dùng nhấn Enter trong ô mật khẩu |
| 3 | `btnRegister_Click` | Gọi `MainFrame.showRegisterScreen()` để chuyển sang màn hình đăng ký |
| 4 | `login_InvalidInput` | Nếu thiếu dữ liệu hoặc sai tài khoản, hiển thị thông báo lỗi bằng `JOptionPane` |
| 5 | `login_Success` | Nếu tài khoản là User thì mở `DashboardUI`, nếu là Admin thì mở `AdminUI` |

## Màn hình đăng ký

### Giao diện

Màn hình đăng ký cho phép người dùng tạo tài khoản mới bằng cách nhập thông tin cá nhân, chỉ số cơ thể và mục tiêu tập luyện.

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

## Màn hình trang chủ

### Giao diện

Màn hình trang chủ là khu vực điều hướng chính của người dùng sau khi đăng nhập, chứa các tab chức năng như thư viện bài tập, dinh dưỡng, nhật ký, thống kê và hồ sơ.

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `mainFrame` | MainFrame | NOT NULL | Quản lý chuyển đổi giữa các màn hình |
| 2 | `cardPanel` | JPanel/CardLayout | NOT NULL | Chứa các panel chức năng |
| 3 | `btnLibrary` | Button/Navigation | | Mở màn hình thư viện bài tập |
| 4 | `btnNutrition` | Button/Navigation | | Mở màn hình tra cứu dinh dưỡng |
| 5 | `btnLogs` | Button/Navigation | | Mở màn hình quản lý log |
| 6 | `btnStats` | Button/Navigation | | Mở màn hình thống kê |
| 7 | `btnProfile` | Button/Navigation | | Mở màn hình hồ sơ cá nhân |
| 8 | `libraryUI` | Panel | | Hiển thị thư viện bài tập |
| 9 | `exerciseUI` | Panel | | Hiển thị form ghi bài tập |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnLibrary_Click` | Gọi `switchTo("LIBRARY", btnLibrary)` để hiển thị `ExerciseLibraryUI` |
| 2 | `btnNutrition_Click` | Gọi `switchTo("NUTRITION", btnNutrition)` để hiển thị `NutritionUI` |
| 3 | `btnLogs_Click` | Gọi `switchTo("LOGS", btnLogs)` để hiển thị `ManageLogUI` |
| 4 | `btnStats_Click` | Gọi `switchTo("STATS", btnStats)` để hiển thị `StatisticsUI` |
| 5 | `btnProfile_Click` | Gọi `switchTo("PROFILE", btnProfile)` để hiển thị `ProfileUI` |
| 6 | `navigateToExerciseInput` | Nhận bài tập được chọn từ thư viện, gọi `ExerciseUI.setSelectedExercise(ex)` và chuyển sang màn hình ghi bài tập |
| 7 | `showLibrary` | Chuyển về màn hình thư viện bài tập |

## Màn hình thư viện bài tập

### Giao diện

Màn hình thư viện bài tập hiển thị danh sách bài tập theo danh mục, cho phép người dùng chọn bài tập để ghi log. Với admin, màn hình còn hỗ trợ thêm, sửa, xóa bài tập.

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
| 1 | `filterButton_Click` | Cập nhật `activeFilter`, render lại danh sách bài tập |
| 2 | `btnSuggest_Click` | Gọi `ExerciseSuggestionService.suggest(user, library)` để lấy bài tập đề xuất |
| 3 | `exerciseCard_Click` | Với user, chuyển sang màn hình ghi log bài tập |
| 4 | `btnAddExercise_Click` | Với admin, mở dialog thêm bài tập |
| 5 | `cbParentCategory_Change` | Cập nhật danh sách danh mục con tương ứng |
| 6 | `cbSubCategory_Change` | Cập nhật danh sách tracking type hợp lệ |
| 7 | `btnSaveAdd_Click` | Validate dữ liệu, gọi `AdminController.addExercise()` |
| 8 | `btnEditExercise_Click` | Mở dialog sửa bài tập đã chọn |
| 9 | `btnSaveEdit_Click` | Validate dữ liệu, gọi `AdminController.updateExercise()` |
| 10 | `btnDeleteExercise_Click` | Confirm, gọi `AdminController.deleteExercise(exerciseName)` |
| 11 | `library_Update` | Sau khi thêm/sửa/xóa, `ExerciseLibrary.notifyObservers()` làm mới giao diện |

## Màn hình của Admin

### Giao diện

Màn hình admin cho phép quản trị viên chuyển giữa khu vực quản lý thư viện bài tập và khu vực xem thông tin người dùng.

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `btnLibrary` | Button/Navigation | Admin only | Mở màn hình quản lý thư viện |
| 2 | `btnUsers` | Button/Navigation | Admin only | Mở danh sách người dùng |
| 3 | `btnLogout` | Button | Admin only | Đăng xuất tài khoản admin |
| 4 | `cardPanel` | JPanel/CardLayout | NOT NULL | Chứa các màn hình con của admin |
| 5 | `libraryPanel` | Panel | | Hiển thị thư viện bài tập cho admin quản lý |
| 6 | `userListPanel` | Panel/List | ReadOnly | Hiển thị danh sách user |
| 7 | `userCard` | Card/Panel | | Hiển thị thông tin tóm tắt của user |
| 8 | `userDetailPanel` | Panel | ReadOnly | Hiển thị chi tiết user được chọn |
| 9 | `btnBack` | Button | | Quay lại danh sách người dùng |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnLibrary_Click` | Gọi `cardLayout.show(cardPanel, "LIBRARY")` để hiển thị thư viện bài tập |
| 2 | `btnUsers_Click` | Gọi `refreshUserListPanel()` và hiển thị danh sách người dùng |
| 3 | `userCard_Click` | Gọi `showUserDetails(user)` để mở màn hình chi tiết user |
| 4 | `btnBack_Click` | Quay lại màn hình danh sách người dùng |
| 5 | `btnLogout_Click` | Confirm đăng xuất, sau đó gọi `MainFrame.showLoginScreen()` |
| 6 | `refreshUserListPanel` | Gọi `AdminController.viewUserDetails()` để lấy danh sách user |

## Màn hình ghi nhận bài tập

### Giao diện

Màn hình ghi nhận bài tập cho phép người dùng nhập thông tin set tập theo loại tracking của bài tập và có thể bật gợi ý tự động cho set tiếp theo.

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `exerciseCard` | Card/Panel | NOT NULL | Hiển thị bài tập được chọn từ thư viện |
| 2 | `lblExName` | Label | ReadOnly | Hiển thị tên bài tập |
| 3 | `lblExTarget` | Label | ReadOnly | Hiển thị nhóm cơ và thể loại bài tập |
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
| 1 | `setSelectedExercise` | Cập nhật bài tập hiện tại và hiển thị các ô nhập phù hợp với `TrackingType` |
| 2 | `lblToggleHint_Click` | Đổi trạng thái bật/tắt gợi ý, gọi `updateHints()` |
| 3 | `updateHints` | Lấy log gần nhất và lịch sử log, gọi `WorkoutHandling.calculateNextSet()` |
| 4 | `btnSave_Click` | Validate dữ liệu, tạo `WorkoutLog` bằng Builder và gọi `WorkoutLogController.addWorkoutLog()` |
| 5 | `saveWorkoutLog_Success` | Lưu log thành công, xóa nội dung ô nhập và cập nhật lại gợi ý |
| 6 | `saveWorkoutLog_InvalidInput` | Nếu thiếu dữ liệu hoặc nhập sai kiểu số, hiển thị thông báo lỗi |
| 7 | `btnBack_Click` | Gọi `DashboardUI.showLibrary()` để quay lại thư viện |
| 8 | `workoutLog_NotifyObservers` | Sau khi thêm log thành công, `WorkoutLogController.notifyObservers()` cập nhật các màn hình liên quan |

## Màn hình quản lý log

### Giao diện

Màn hình quản lý log cho phép người dùng xem và xóa các nhật ký tập luyện hoặc nhật ký dinh dưỡng đã lưu.

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `tabWorkout` | Button/Tab | | Chuyển sang danh sách workout log |
| 2 | `tabNutrition` | Button/Tab | | Chuyển sang danh sách nutrition log |
| 3 | `workoutLogPanel` | Panel/List | ReadOnly | Hiển thị danh sách workout log của user |
| 4 | `nutritionLogPanel` | Panel/List | ReadOnly | Hiển thị danh sách nutrition log của user |
| 5 | `workoutLogCard` | Card/Panel | ReadOnly | Hiển thị thông tin từng workout log |
| 6 | `nutritionLogCard` | Card/Panel | ReadOnly | Hiển thị thông tin từng nutrition log |
| 7 | `btnDeleteWorkoutLog` | Button | Cần có log | Xóa workout log |
| 8 | `btnDeleteNutritionLog` | Button | Cần có log | Xóa nutrition log |
| 9 | `confirmDialog` | Dialog | | Xác nhận trước khi xóa log |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `tabWorkout_Click` | Hiển thị panel workout log và đổi style tab |
| 2 | `tabNutrition_Click` | Hiển thị panel nutrition log và đổi style tab |
| 3 | `loadWorkoutData` | Lấy workout log từ `WorkoutLogController.getAllLogs()`, lọc theo user hiện tại |
| 4 | `loadNutritionData` | Lấy nutrition log từ `NutritionLogController.getAllLogs()`, lọc theo user hiện tại |
| 5 | `btnDeleteWorkoutLog_Click` | Confirm, sau đó gọi `WorkoutLogController.removeWorkoutLog(logID)` |
| 6 | `btnDeleteNutritionLog_Click` | Confirm, sau đó gọi `NutritionLogController.removeNutritionLog(logID)` |
| 7 | `deleteLog_Success` | Xóa log thành công, lưu JSON và gọi `notifyObservers()` |
| 8 | `observer_Update` | Khi log thay đổi, `ManageLogUI.update()` tự load lại danh sách |

## Màn hình thống kê tổng quan

### Giao diện

Màn hình thống kê tổng quan hiển thị dữ liệu tập luyện, dinh dưỡng, log gần đây và cho phép người dùng cập nhật mục tiêu tập luyện.

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
| 1 | `refreshData` | Gọi `StatisticsPresenter` để lấy tổng volume, tổng calo và log gần đây |
| 2 | `btnRefresh_Click` | Tải lại toàn bộ dữ liệu thống kê |
| 3 | `btnUpdateGoal_Click` | Cập nhật goal của user, gọi `StatisticsPresenter.updateGoal(user)` |
| 4 | `updateGoal_Success` | Gọi `WorkoutHandling.setGoal(user)` để cập nhật Strategy gợi ý set |
| 5 | `observer_Update` | Khi workout/nutrition log thay đổi, `StatisticsUI.update()` tự gọi `refreshData()` |
| 6 | `noData_State` | Nếu chưa có log, hiển thị giá trị mặc định hoặc bảng rỗng |

## Màn hình tra cứu dinh dưỡng

### Giao diện

Màn hình tra cứu dinh dưỡng cho phép người dùng tìm sản phẩm, xem thông tin kcal/protein/carb/fat và thêm kết quả vào nhật ký dinh dưỡng.

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `txtSearchFood` | TextField | NOT NULL | Nhập tên sản phẩm/món ăn cần tra cứu |
| 2 | `btnSearch` | Button | | Gửi yêu cầu tìm kiếm dinh dưỡng |
| 3 | `resultTable` | Table | ReadOnly | Hiển thị danh sách kết quả từ API |
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

## Màn hình trang cá nhân

### Giao diện

Màn hình trang cá nhân hiển thị thông tin cơ bản của người dùng, chỉ số cơ thể, mục tiêu tập luyện và nút đăng xuất.

### Mô tả các đối tượng trên màn hình

| STT | Tên | Kiểu | Ràng buộc | Chức năng |
|---|---|---|---|---|
| 1 | `lblAvatar` | Label/Icon | ReadOnly | Hiển thị ký tự đại diện của người dùng |
| 2 | `lblName` | Label | ReadOnly | Hiển thị họ tên người dùng |
| 3 | `lblSub` | Label | ReadOnly | Hiển thị username và mục tiêu |
| 4 | `profileStatCards` | Card/Panel | ReadOnly | Hiển thị tuổi, chiều cao, cân nặng, BMI |
| 5 | `infoRows` | Label/Row | ReadOnly | Hiển thị thông tin chi tiết |
| 6 | `btnLogout` | Button | | Đăng xuất khỏi hệ thống |
| 7 | `confirmDialog` | Dialog | | Xác nhận trước khi đăng xuất |

### Danh sách các biến cố và xử lý

| STT | Biến cố | Xử lý |
|---|---|---|
| 1 | `btnLogout_Click` | Hiển thị hộp thoại xác nhận đăng xuất |
| 2 | `logout_Confirm` | Gọi `MainFrame.showLoginScreen()` để quay về màn hình đăng nhập |
| 3 | `btnLogout_MouseEnter` | Đổi màu nút đăng xuất khi hover |
| 4 | `btnLogout_MouseExit` | Khôi phục màu nút đăng xuất khi rời chuột |
| 5 | `profile_Load` | Lấy thông tin từ `User` hiện tại để hiển thị lên các label/card |
