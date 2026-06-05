# Bổ sung mô tả lớp cho Chương IV

Tài liệu này liệt kê các lớp theo package trong source code hiện tại. Mỗi lớp có hai bảng cố định:

- Bảng thuộc tính: `STT`, `Tên Thuộc Tính`, `Kiểu dữ liệu`, `Ràng Buộc`, `Ý nghĩa/Ghi chú`.
- Bảng phương thức: `STT`, `Tên Phương Thức`, `Dữ Liệu Đầu Vào`, `Dữ Liệu Đầu Ra`, `Ghi Chú/Ý Nghĩa`.

Ghi chú rà soát:

- `ExerciseFactory` là **Simple Factory**, không phải Factory Method.
- `JsonLogDatabase` implements trực tiếp `DataConnection<LogCollection>`, không kế thừa `AJsonDatabase`.
- `User.password` trong demo hiện lưu dạng chuỗi, chưa mã hóa.
- `LogCollection` là wrapper/DTO để gom dữ liệu JSON, không phải Composite chính.
- Tổng số mục dưới đây: 54 lớp/interface/enum top-level và 4 lớp nested/builder, tổng 58 mục.

## 1. Package `com.group3`

### 1.1. Lớp `GymTracking`

Lớp khởi động chương trình, khởi tạo dữ liệu mặc định, database, controller/service và hiển thị giao diện chính.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có thuộc tính instance | - | - | Lớp chỉ chứa hàm `main` |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `main` | `String[] args` | `void` | Cấu hình look-and-feel, tạo database/controller/service và mở `MainFrame` |

## 2. Package `com.group3.model`

### 2.1. Interface `IAccount`

Interface chung cho `User` và `Admin`, giúp đăng nhập trả về tài khoản theo kiểu đa hình.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface chỉ định nghĩa hành vi chung |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `getUsername` | Không | `String` | Lấy tên đăng nhập |
| 2 | `getPassword` | Không | `String` | Lấy mật khẩu |

### 2.2. Lớp `Admin`

Lớp đại diện tài khoản quản trị viên.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `name` | `String` | Không rỗng | Tên hiển thị admin |
| 2 | `username` | `String` | Duy nhất | Tên đăng nhập admin |
| 3 | `password` | `String` | Không rỗng | Mật khẩu admin |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `Admin` | `name`, `username`, `password` | Constructor | Khởi tạo admin |
| 2 | `getName` | Không | `String` | Lấy tên admin |
| 3 | `getUsername` | Không | `String` | Lấy username |
| 4 | `getPassword` | Không | `String` | Lấy password |

### 2.3. Lớp `User`

Lớp lưu thông tin người dùng, chỉ số cơ thể, mục tiêu và danh sách log tạm thời.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Duy nhất | Mã định danh người dùng |
| 2 | `name` | `String` | Không rỗng | Tên hiển thị |
| 3 | `username` | `String` | Duy nhất | Tên đăng nhập |
| 4 | `password` | `String` | Không rỗng | Mật khẩu trong demo |
| 5 | `age` | `int` | `> 0` | Tuổi |
| 6 | `gender` | `String` | Không rỗng | Giới tính |
| 7 | `height` | `double` | `> 0` | Chiều cao, có thể nhập cm hoặc m |
| 8 | `weight` | `double` | `> 0` | Cân nặng kg |
| 9 | `goal` | `WorkoutGoal` | Theo enum | Mục tiêu tập luyện |
| 10 | `workoutLog` | `transient List<WorkoutLog>` | Có thể rỗng | Danh sách log tập luyện tạm thời |
| 11 | `nutritionLog` | `transient List<NutritionLog>` | Có thể rỗng | Danh sách log dinh dưỡng tạm thời |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `User` | Không | Constructor | Khởi tạo list log rỗng |
| 2 | `User` | `userID`, `name`, `username`, `password`, `age`, `gender`, `height`, `weight`, `goal` | Constructor | Khởi tạo user đầy đủ |
| 3 | Getters/Setters | Tùy thuộc thuộc tính | Tùy thuộc thuộc tính | Truy xuất và cập nhật dữ liệu đóng gói |
| 4 | `bmiCal` | Không | `double` | Tính BMI, tự đổi cm sang m nếu cần |

### 2.4. Enum `WorkoutGoal`

Enum biểu diễn mục tiêu tập luyện của người dùng.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `LOSE_FAT` | Enum constant | Cố định | Mục tiêu giảm mỡ |
| 2 | `MUSCLE_GAIN` | Enum constant | Cố định | Mục tiêu tăng cơ |
| 3 | `MAINTENANCE` | Enum constant | Cố định | Mục tiêu duy trì |
| 4 | `displayName` | `String` | Không rỗng | Tên hiển thị của enum |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `WorkoutGoal` | `displayName` | Constructor enum | Gán tên hiển thị |
| 2 | `toString` | Không | `String` | Trả về tên hiển thị |

### 2.5. Enum `TrackingType`

Enum biểu diễn cách ghi nhận dữ liệu cho từng bài tập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `WEIGHT_REP_TIME` | Enum constant | Cố định | Theo dõi tạ, reps, thời gian |
| 2 | `DISTANCE_TIME` | Enum constant | Cố định | Theo dõi quãng đường và thời gian |
| 3 | `TIME_ONLY` | Enum constant | Cố định | Chỉ theo dõi thời gian |
| 4 | `WEIGHT_REP` | Enum constant | Cố định | Theo dõi tạ và reps |
| 5 | `REP_ONLY` | Enum constant | Cố định | Chỉ theo dõi reps |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | Không có phương thức riêng | - | - | Sử dụng các phương thức mặc định của enum |

### 2.6. Lớp `ExerciseCategory`

Lớp biểu diễn danh mục bài tập và danh mục con theo Composite rút gọn.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `catID` | `int` | Duy nhất | Mã danh mục |
| 2 | `catName` | `String` | Không rỗng | Tên danh mục |
| 3 | `allowedTrackingTypes` | `List<TrackingType>` | Có thể rỗng | Các kiểu tracking được phép |
| 4 | `subCat` | `List<ExerciseCategory>` | Khởi tạo rỗng | Danh mục con |
| 5 | `parentCat` | `transient ExerciseCategory` | Có thể null | Danh mục cha |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseCategory` | `catID`, `catName`, `allowedTrackingTypes` | Constructor | Khởi tạo danh mục |
| 2 | `addSubCat` | `ExerciseCategory newSubCat` | `void` | Thêm danh mục con và gán parent |
| 3 | Getters | Không | Tùy thuộc thuộc tính | Lấy thông tin danh mục |
| 4 | `setParentCat` | `ExerciseCategory parentCat` | `void` | Gán danh mục cha |
| 5 | `toString` | Không | `String` | Trả về tên danh mục |
| 6 | `equals`, `hashCode` | `Object obj` / Không | `boolean` / `int` | So sánh theo `catID` |

### 2.7. Lớp `Exercise`

Lớp mô tả một bài tập trong thư viện.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `exerciseID` | `int` | Duy nhất | Mã bài tập |
| 2 | `exerciseName` | `String` | Không rỗng | Tên bài tập |
| 3 | `category` | `ExerciseCategory` | Khác null | Danh mục bài tập |
| 4 | `trackingType` | `TrackingType` | Khác null | Kiểu theo dõi chỉ số |
| 5 | `targetMuscle` | `String` | Có thể null | Nhóm cơ tác động |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `Exercise` | `ExerciseBuilder builder` | Constructor private | Tạo `Exercise` từ builder |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy thông tin bài tập |
| 3 | `toString` | Không | `String` | Chuỗi mô tả bài tập |

### 2.8. Lớp nested `Exercise.ExerciseBuilder`

Builder tạo `Exercise` theo fluent API.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `exerciseID` | `int` | Mặc định `-1` | Mã bài tập cần set |
| 2 | `exerciseName` | `String` | Không rỗng | Tên bài tập |
| 3 | `category` | `ExerciseCategory` | Khác null | Danh mục |
| 4 | `trackingType` | `TrackingType` | Khác null | Kiểu tracking |
| 5 | `targetMuscle` | `String` | Có thể null | Nhóm cơ |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `setExerciseID` | `int exerciseID` | `ExerciseBuilder` | Gán ID |
| 2 | `setExerciseName` | `String exerciseName` | `ExerciseBuilder` | Gán tên |
| 3 | `setCategory` | `ExerciseCategory category` | `ExerciseBuilder` | Gán danh mục |
| 4 | `setTrackingType` | `TrackingType trackingType` | `ExerciseBuilder` | Gán kiểu tracking |
| 5 | `setTargetMuscle` | `String targetMuscle` | `ExerciseBuilder` | Gán nhóm cơ |
| 6 | `build` | Không | `Exercise` | Kiểm tra dữ liệu bắt buộc và tạo object |

### 2.9. Lớp `ExerciseFactory`

Simple Factory tạo `Exercise` và kết hợp `ExerciseBuilder`.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Factory stateless |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `createExercise` | `id`, `name`, `category`, `trackingType`, `targetMuscle` | `Exercise` | Tạo bài tập, tự chọn tracking mặc định nếu null |

### 2.10. Lớp `ExerciseLibrary`

Thư viện bài tập, đồng thời là `Subject` trong Observer Pattern.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `lib` | `List<Exercise>` | Có thể rỗng | Danh sách bài tập |
| 2 | `observers` | `transient List<Observer>` | Khởi tạo khi cần | Danh sách view đăng ký theo dõi |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseLibrary` | `List<Exercise> list` | Constructor | Khởi tạo thư viện |
| 2 | `getLib` | Không | `List<Exercise>` | Lấy danh sách bài tập |
| 3 | `add`, `remove`, `notifyObservers` | `Observer o` / Không | `void` | Cài đặt Subject |
| 4 | `addExercise` | `Exercise exercise` | `void` | Thêm bài tập và notify |
| 5 | `removeExercise` | `Exercise exercise` | `void` | Xóa bài tập và notify |
| 6 | `getByCategory` | `ExerciseCategory cat` | `List<Exercise>` | Lọc bài tập theo danh mục |
| 7 | `getCategoryByName` | `String catName` | `List<Exercise>` | Lọc bài tập theo tên danh mục |
| 8 | `searchExercise` | `String name` | `Exercise` | Tìm bài tập theo tên |

### 2.11. Interface `Observer`

Interface cho các view cần tự cập nhật khi dữ liệu thay đổi.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `update` | Không | `void` | Cập nhật view khi subject thay đổi |

### 2.12. Interface `Subject`

Interface cho đối tượng phát thông báo trong Observer Pattern.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `add` | `Observer o` | `void` | Đăng ký observer |
| 2 | `remove` | `Observer o` | `void` | Hủy đăng ký observer |
| 3 | `notifyObservers` | Không | `void` | Thông báo observer cập nhật |

### 2.13. Lớp `WorkoutLog`

Lớp lưu một bản ghi tập luyện.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã người dùng |
| 2 | `logID` | `int` | Duy nhất | Mã log |
| 3 | `date` | `LocalDateTime` | Khác null | Thời điểm tập |
| 4 | `exercise` | `Exercise` | Khác null | Bài tập |
| 5 | `weight` | `Double` | Có thể null | Mức tạ kg |
| 6 | `reps` | `Integer` | Có thể null | Số reps |
| 7 | `distance` | `Double` | Có thể null | Quãng đường km |
| 8 | `time` | `Double` | Có thể null | Thời gian phút |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `WorkoutLog` | `WorkoutLogBuilder builder` | Constructor private | Tạo log từ builder |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy dữ liệu log |
| 3 | `toString` | Không | `String` | Chuỗi mô tả log |
| 4 | `paceCal` | Không | `double` | Tính pace `time / distance` |

### 2.14. Lớp nested `WorkoutLog.WorkoutLogBuilder`

Builder tạo `WorkoutLog`.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã user |
| 2 | `logID` | `int` | Mặc định `-1` | Mã log bắt buộc |
| 3 | `date` | `LocalDateTime` | Khác null | Thời điểm |
| 4 | `exercise` | `Exercise` | Khác null | Bài tập |
| 5 | `weight`, `distance`, `time` | `Double` | Có thể null | Chỉ số thực |
| 6 | `reps` | `Integer` | Có thể null | Số reps |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | Các setter fluent | Giá trị từng thuộc tính | `WorkoutLogBuilder` | Gán dữ liệu từng bước |
| 2 | `build` | Không | `WorkoutLog` | Kiểm tra `logID`, `date`, `exercise` và tạo log |

### 2.15. Lớp `NutritionLog`

Lớp lưu một bản ghi dinh dưỡng.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã người dùng |
| 2 | `logID` | `int` | Duy nhất | Mã log dinh dưỡng |
| 3 | `productID` | `int` | Có thể dùng từ API | Mã sản phẩm |
| 4 | `productName` | `String` | Không rỗng | Tên thực phẩm |
| 5 | `addTime` | `LocalDateTime` | Khác null | Thời điểm thêm |
| 6 | `quantity` | `Integer` | Mặc định `1` | Số lượng |
| 7 | `energy` | `Double` | Có thể null | Kcal |
| 8 | `protein` | `Double` | Có thể null | Protein |
| 9 | `fat` | `Double` | Có thể null | Fat |
| 10 | `carb` | `Double` | Có thể null | Carbohydrate |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `NutritionLog` | `Builder builder` | Constructor private | Tạo log từ builder |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy dữ liệu dinh dưỡng |
| 3 | `toString` | Không | `String` | Chuỗi mô tả dinh dưỡng |

### 2.16. Lớp nested `NutritionLog.Builder`

Builder tạo `NutritionLog`.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã user |
| 2 | `logID` | `int` | Mặc định `-1` | Mã log |
| 3 | `productID` | `int` | Mặc định `-1` | Mã sản phẩm bắt buộc |
| 4 | `productName` | `String` | Không rỗng | Tên sản phẩm bắt buộc |
| 5 | `addTime` | `LocalDateTime` | Mặc định hiện tại | Thời điểm thêm |
| 6 | `quantity` | `int` | Mặc định `1` | Số lượng |
| 7 | `energy`, `protein`, `fat`, `carbohydrates` | `Double` | Có thể null | Chỉ số dinh dưỡng |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | Các setter fluent | Giá trị từng thuộc tính | `Builder` | Gán dữ liệu từng bước |
| 2 | `build` | Không | `NutritionLog` | Kiểm tra product bắt buộc và tạo log |

### 2.17. Lớp `LogCollection`

Wrapper gom workout log và nutrition log để đọc/ghi JSON.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutLogs` | `List<WorkoutLog>` | Không null sau constructor | Danh sách log tập |
| 2 | `nutritionLogs` | `List<NutritionLog>` | Không null sau constructor | Danh sách log dinh dưỡng |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `LogCollection` | `workoutLogs`, `nutritionLogs` | Constructor | Nếu input null thì tạo list rỗng |
| 2 | `getWorkoutLogs` | Không | `List<WorkoutLog>` | Lấy workout logs |
| 3 | `getNutritionLogs` | Không | `List<NutritionLog>` | Lấy nutrition logs |

### 2.18. Lớp `RecommendationResult`

Kết quả trả về của chiến lược gợi ý set tập tiếp theo.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `suggestedWeight` | `Double` | Có thể null | Mức tạ gợi ý |
| 2 | `suggestedReps` | `Integer` | Có thể null | Số reps gợi ý |
| 3 | `suggestedDistance` | `Double` | Có thể null | Quãng đường gợi ý |
| 4 | `suggestedTime` | `Double` | Có thể null | Thời gian gợi ý |
| 5 | `message` | `String` | Có thể rỗng | Lời giải thích |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `RecommendationResult` | `weight`, `reps`, `distance`, `time`, `message` | Constructor | Đóng gói kết quả |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy giá trị gợi ý |
| 3 | `toString` | Không | `String` | Chuỗi mô tả kết quả |

### 2.19. Interface `ExerciseSuggestionStrategy`

Strategy interface cho gợi ý bài tập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface thuật toán |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Gợi ý danh sách bài tập |

### 2.20. Lớp `FitStrategy`

Strategy gợi ý bài tập cân bằng/duy trì.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Lọc/chọn bài tập phù hợp mục tiêu duy trì |

### 2.21. Lớp `ThinStrategy`

Strategy gợi ý bài tập cho người cần tăng cơ/cải thiện thể trạng gầy.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Gợi ý bài tập thiên về tăng cơ |

### 2.22. Lớp `FatStrategy`

Strategy gợi ý bài tập cho người cần giảm mỡ.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Gợi ý bài tập thiên về cardio/giảm mỡ |

### 2.23. Interface `NextSetRecommendationStrategy`

Strategy interface cho gợi ý set tập tiếp theo.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface thuật toán |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `WorkoutLog currentLog`, `List<WorkoutLog> weeklyLogs` | `RecommendationResult` | Gợi ý set tiếp theo dựa trên log hiện tại và lịch sử tuần |
| 2 | `calculateNextSet` | `WorkoutLog currentLog` | `RecommendationResult` | Overload mặc định dùng lịch sử rỗng |
| 3 | `hasWeeklyHistory`, `isHighWeeklyLoad` | `weeklyLogs` | `boolean` | Helper mặc định đánh giá lịch sử tuần |

### 2.24. Lớp `LoseFatStrategy`

Strategy gợi ý set tập theo mục tiêu giảm mỡ.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Tính gợi ý phù hợp giảm mỡ, có xét tải tập tuần |

### 2.25. Lớp `MuscleGainStrategy`

Strategy gợi ý set tập theo mục tiêu tăng cơ.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Tính gợi ý phù hợp tăng cơ, có xét tải tập tuần |

### 2.26. Lớp `NoWeightStrategy`

Strategy gợi ý cho bài tập không dùng tạ hoặc không có `weight`.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Gợi ý reps/time/distance cho bài không có tạ |

### 2.27. Interface `DataConnection<T>`

Interface persistence tổng quát.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `loadData` | Không | `T` | Đọc dữ liệu từ nguồn lưu trữ |
| 2 | `saveData` | `T data` | `boolean` | Ghi dữ liệu xuống nguồn lưu trữ |

### 2.28. Lớp abstract `AJsonDatabase<T>`

Abstract class áp dụng Template Method cho đọc/ghi JSON.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `filePath` | `String` | Khác null | Đường dẫn file JSON |
| 2 | `gson` | `Gson` | Khác null | Bộ parse/serialize JSON |
| 3 | `typeOfT` | `Type` | Khác null | Kiểu dữ liệu generic cần parse |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `AJsonDatabase` | `String filePath`, `Type typeOfT` | Constructor | Khởi tạo đường dẫn, type và Gson |
| 2 | `createGson` | Không | `Gson` | Tạo Gson có pretty printing |
| 3 | `loadData` | Không | `T` | Đọc JSON, lỗi thì trả default |
| 4 | `saveData` | `T data` | `boolean` | Ghi JSON |
| 5 | `getDefaultValue` | Không | `T` | Hook method do subclass cài đặt |

### 2.29. Lớp `JsonUserDatabase`

Database JSON cho user.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `JsonUserDatabase` | Không | Constructor | Dùng file user JSON mặc định |
| 2 | `getDefaultValue` | Không | `List<User>` | Trả list user rỗng |
| 3 | `checkLogin` | `username`, `password` | `User` | Xác thực user |
| 4 | `isUsernameExist` | `username` | `boolean` | Kiểm tra trùng username |
| 5 | `addUser` | `User newUser` | `boolean` | Thêm user mới |
| 6 | `updateUser` | `User updatedUser` | `boolean` | Cập nhật thông tin user |

### 2.30. Lớp `JsonAdminDatabase`

Database JSON cho admin.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `JsonAdminDatabase` | Không | Constructor | Dùng file admin JSON mặc định |
| 2 | `checkLogin` | `username`, `password` | `Admin` | Xác thực admin |
| 3 | `isUsernameExist` | `username` | `boolean` | Kiểm tra username admin |
| 4 | `getDefaultValue` | Không | `List<Admin>` | Trả list admin mặc định/rỗng |

### 2.31. Lớp `JsonExerciseDatabase`

Database JSON cho thư viện bài tập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `JsonExerciseDatabase` | Không | Constructor | Dùng file thư viện bài tập mặc định |
| 2 | `getDefaultValue` | Không | `List<Exercise>` | Trả list bài tập rỗng |

### 2.32. Lớp `JsonCategoryDatabase`

Database JSON cho danh mục bài tập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `JsonCategoryDatabase` | Không | Constructor | Dùng file category mặc định |
| 2 | `getDefaultValue` | Không | `List<ExerciseCategory>` | Trả list danh mục rỗng |

### 2.33. Lớp `JsonLogDatabase`

Database JSON riêng cho hai loại log.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutFile` | `String` | Hằng instance | File workout log |
| 2 | `nutritionFile` | `String` | Hằng instance | File nutrition log |
| 3 | `gson` | `Gson` | Khác null | Gson có adapter cho `LocalDateTime` |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `JsonLogDatabase` | Không | Constructor | Khởi tạo Gson cho log |
| 2 | `loadData` | Không | `LogCollection` | Đọc hai file log |
| 3 | `saveData` | `LogCollection data` | `boolean` | Ghi hai file log |
| 4 | `loadList` | `file`, `type` | `List<T>` | Đọc một list generic |
| 5 | `saveList` | `file`, `list` | `boolean` | Ghi một list generic |

### 2.34. Interface `INutrition`

Target interface trong Adapter Pattern.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `getNutritionInfo` | `String productName` | `List<NutritionLog>` | Tra cứu dinh dưỡng theo tên sản phẩm |

### 2.35. Lớp `OpenFoodFactsAdapter`

Adapter chuyển dữ liệu OpenFoodFacts API thành `NutritionLog`.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `api` | `OpenFoodFactsAPI` | Khác null | Adaptee được adapter sử dụng |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `OpenFoodFactsAdapter` | Không hoặc `OpenFoodFactsAPI api` | Constructor | Khởi tạo adapter |
| 2 | `getNutritionInfo` | `String productName` | `List<NutritionLog>` | Gọi API và parse kết quả |
| 3 | `parseJsonObject` | `String jsonResponse` | `JsonObject` | Parse JSON lenient |
| 4 | `resolveProductName` | `JsonObject product`, `String fallbackName` | `String` | Lấy tên sản phẩm ưu tiên vi/en/default |
| 5 | `getSafeString`, `getSafeObject`, `getSafeDouble` | JSON object và key | Tùy kiểu | Đọc JSON an toàn |
| 6 | `getEnergyKcal`, `getFirstSafeDouble` | `JsonObject nutriments` / key list | `Double` | Lấy chỉ số dinh dưỡng, hỗ trợ fallback |

## 3. Package `com.group3.controller`

### 3.1. Lớp `AdminController`

Điều phối nghiệp vụ quản trị thư viện bài tập và xem user.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `exerciseLibrary` | `ExerciseLibrary` | Khác null | Thư viện bài tập |
| 2 | `admin` | `Admin` | Khác null | Admin hiện tại |
| 3 | `libraryDB` | `JsonExerciseDatabase` | Khác null | Database bài tập |
| 4 | `userDB` | `JsonUserDatabase` | Khác null | Database user |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `AdminController` | `library`, `admin`, `libraryDB`, `userDB` | Constructor | Khởi tạo controller admin |
| 2 | `addExercise` | `id`, `name`, `category`, `trackingType`, `targetMuscle` | `boolean` | Tạo bài qua factory và lưu |
| 3 | `updateExercise` | `oldExercise`, `newName`, `category`, `trackingType`, `targetMuscle` | `boolean` | Cập nhật bài tập |
| 4 | `deleteExercise` | `String exerciseName` | `boolean` | Xóa bài tập theo tên |
| 5 | `viewUserDetails` | Không | `List<User>` | Lấy danh sách user |

### 3.2. Lớp `LoginManager`

Xử lý đăng nhập user/admin.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userDatabase` | `JsonUserDatabase` | Khác null | Database user |
| 2 | `adminDatabase` | `JsonAdminDatabase` | Khác null | Database admin |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `LoginManager` | `userDatabase`, `adminDatabase` | Constructor | Khởi tạo manager |
| 2 | `login` | `username`, `password` | `IAccount` | Xác thực, trả về `Admin` hoặc `User` |

### 3.3. Lớp `RegisterManager`

Xử lý đăng ký user mới.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userDatabase` | `JsonUserDatabase` | Khác null | Database user |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `RegisterManager` | `JsonUserDatabase userDatabase` | Constructor | Khởi tạo manager |
| 2 | `register` | `User newUser` | `boolean` | Kiểm tra trùng username và thêm user |

### 3.4. Lớp `WorkoutLogController`

Controller quản lý workout log và là `Subject`.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutDB` | `DataConnection<LogCollection>` | Khác null | Nguồn lưu log |
| 2 | `observers` | `List<Observer>` | Khởi tạo rỗng | View đăng ký cập nhật |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `WorkoutLogController` | `DataConnection<LogCollection> workoutDB` | Constructor | Khởi tạo controller |
| 2 | `add`, `remove`, `notifyObservers` | `Observer o` / Không | `void` | Cài đặt Subject |
| 3 | `addWorkoutLog` | `WorkoutLog newWorkoutLog` | `boolean` | Thêm log và notify |
| 4 | `removeWorkoutLog` | `int logID` | `boolean` | Xóa log và notify |
| 5 | `getAllLogs` | Không | `List<WorkoutLog>` | Lấy toàn bộ workout log |

### 3.5. Lớp `NutritionLogController`

Controller quản lý nutrition log, tra cứu dinh dưỡng và là `Subject`.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `nutritionDB` | `DataConnection<LogCollection>` | Khác null | Nguồn lưu log |
| 2 | `nutrition` | `INutrition` | Khác null | Adapter interface |
| 3 | `observers` | `List<Observer>` | Khởi tạo rỗng | View đăng ký cập nhật |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `NutritionLogController` | `nutritionDB`, `nutritionAPI` | Constructor | Khởi tạo controller |
| 2 | `addNutritionLog` | `NutritionLog newNutritionLog` | `boolean` | Thêm log và notify |
| 3 | `removeNutritionLog` | `int logID` | `boolean` | Xóa log và notify |
| 4 | `lookupNutrition` | `String productName` | `List<NutritionLog>` | Tra cứu dinh dưỡng |
| 5 | `getAllLogs` | Không | `List<NutritionLog>` | Lấy toàn bộ nutrition log |
| 6 | `add`, `remove`, `notifyObservers` | `Observer o` / Không | `void` | Cài đặt Subject |

### 3.6. Lớp `StatisticsPresenter`

Tổng hợp dữ liệu thống kê để View hiển thị chart/bảng.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `database` | `DataConnection<LogCollection>` | Khác null | Nguồn dữ liệu thống kê |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `StatisticsPresenter` | `DataConnection<LogCollection> database` | Constructor | Khởi tạo presenter |
| 2 | `dailyNutritionSummary` | `LocalDate targetDate`, `int userID` | `double[]` | Tổng hợp dinh dưỡng theo ngày |
| 3 | `dailyWorkoutSummary` | `LocalDate targetDate`, `int userID` | `double[]` | Tổng hợp tập luyện theo ngày |
| 4 | `getWorkoutChartData` | `int userID` | `Map<LocalDate, Map<ExerciseCategory, Double>>` | Dữ liệu chart tập luyện |
| 5 | `getNutritionChartData` | `int userID` | `Map<LocalDate, Double>` | Dữ liệu chart dinh dưỡng |
| 6 | `updateGoal` | `User user` | `boolean` | Cập nhật mục tiêu user |
| 7 | `getRecentWorkout` | `userID`, `limit` | `List<WorkoutLog>` | Lấy workout log gần đây |
| 8 | `getRecentNutrition` | `userID`, `limit` | `List<NutritionLog>` | Lấy nutrition log gần đây |

### 3.7. Lớp `WorkoutHandling`

Context của Strategy gợi ý set tập tiếp theo.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `noWeightStrategy` | `NoWeightStrategy` | Khởi tạo sẵn | Strategy mặc định cho bài không có tạ |
| 2 | `nextSetStrategy` | `NextSetRecommendationStrategy` | Có thể null trước khi set goal | Strategy hiện tại |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `WorkoutLog currentLog` | `RecommendationResult` | Gợi ý với lịch sử rỗng |
| 2 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Gợi ý dựa trên lịch sử tuần |
| 3 | `setGoal` | `User user` | `void` | Chọn strategy theo mục tiêu |

### 3.8. Lớp `ExerciseSuggestionService`

Service điều phối Strategy gợi ý bài tập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có field lưu strategy | - | - | Strategy được tạo theo từng lần gọi |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `recommend` | `User newUser` | `WorkoutGoal` | Gợi ý mục tiêu theo BMI |
| 2 | `applyUserChoice` | `User user`, `WorkoutGoal chosenGoal` | `void` | Áp dụng mục tiêu người dùng chọn |
| 3 | `suggest` | `User user`, `ExerciseLibrary library` | `List<Exercise>` | Tạo strategy và gợi ý bài tập |
| 4 | `createStrategy` | `WorkoutGoal goal` | `ExerciseSuggestionStrategy` | Tạo strategy cụ thể |

## 4. Package `com.group3.util`

### 4.1. Lớp `OpenFoodFactsAPI`

Adaptee gọi OpenFoodFacts API.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `PREFIX_URL` | `String` | `static final` | URL gốc API |
| 2 | `SEARCH_OPTIONS` | `String` | `static final` | Tham số tìm kiếm |
| 3 | `VI_LOCALE` | `String` | `static final` | Locale tiếng Việt |
| 4 | `EN_LOCALE` | `String` | `static final` | Locale tiếng Anh fallback |
| 5 | `CLIENT` | `HttpClient` | `static final` | HTTP client timeout |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `fetchNutritionData` | `String productName` | `String` | Tìm tiếng Việt trước, fallback tiếng Anh |
| 2 | `fetchByLocale` | `encodedName`, `localeQuery` | `String` | Gửi request theo locale |
| 3 | `isEmptyResult` | `String responseBody` | `boolean` | Kiểm tra response rỗng |

### 4.2. Lớp `RandomAlgorithm`

Utility random bài tập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Utility class stateless |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `RandomAlgorithm` | Không | Constructor private | Ngăn tạo object utility |
| 2 | `pickRandom` | `List<Exercise> source`, `int count` | `List<Exercise>` | Shuffle và lấy tối đa `count` bài tập |

## 5. Package `com.group3.view`

### 5.1. Lớp `MainFrame`

Cửa sổ chính điều hướng giữa login, register, dashboard user và dashboard admin.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `cardLayout`, `mainContainer` | `CardLayout`, `JPanel` | Khác null | Điều hướng màn hình |
| 2 | `loginManager`, `registerManager` | Manager | Khác null | Xử lý auth |
| 3 | `libraryDB`, `userDB` | Database | Khác null | Database chính |
| 4 | `workoutCtrl`, `nutritionCtrl` | Controller | Khác null | Controller log |
| 5 | `workoutHandling`, `suggestionService`, `statPresenter` | Service/Presenter | Khác null | Logic nghiệp vụ |
| 6 | `library` | `ExerciseLibrary` | Khác null | Thư viện bài tập |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `MainFrame` | Các manager/database/controller/service | Constructor | Khởi tạo cửa sổ chính |
| 2 | `showLoginScreen` | Không | `void` | Chuyển sang login |
| 3 | `showRegisterScreen` | Không | `void` | Chuyển sang đăng ký |
| 4 | `showUserDashboard` | `User user` | `void` | Mở dashboard user |
| 5 | `showAdminDashboard` | `Admin admin` | `void` | Mở dashboard admin |

### 5.2. Lớp `LoginForm`

Màn hình đăng nhập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame` | `MainFrame` | Khác null | Điều hướng sau đăng nhập |
| 2 | `loginManager` | `LoginManager` | Khác null | Xử lý đăng nhập |
| 3 | Các component nhập liệu | `JTextField`, `JPasswordField`, `JButton` | Khác null sau init | Nhập username/password và thao tác |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `LoginForm` | `MainFrame`, `LoginManager` | Constructor | Khởi tạo form |
| 2 | `initComponents` | Không | `void` | Tạo giao diện |
| 3 | `handleLogin` | Không | `void` | Gọi `LoginManager` và điều hướng |

### 5.3. Lớp `RegisterForm`

Màn hình đăng ký tài khoản user.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame` | `MainFrame` | Khác null | Điều hướng |
| 2 | `registerManager` | `RegisterManager` | Khác null | Xử lý đăng ký |
| 3 | Các field nhập liệu | Swing components | Khác null sau init | Nhập thông tin user |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `RegisterForm` | `MainFrame`, `RegisterManager` | Constructor | Khởi tạo form |
| 2 | `initComponents` | Không | `void` | Tạo giao diện |
| 3 | `handleRegister` | Không | `void` | Tạo user và gọi register |

### 5.4. Lớp `DashboardUI`

Dashboard user và thanh điều hướng nội bộ.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame`, `user` | `MainFrame`, `User` | Khác null | Ngữ cảnh màn hình |
| 2 | `workoutCtrl`, `nutritionCtrl` | Controller | Khác null | Controller dữ liệu |
| 3 | `workoutHandling`, `statPresenter`, `suggestionService` | Service/Presenter | Khác null | Logic nghiệp vụ |
| 4 | `library` | `ExerciseLibrary` | Khác null | Thư viện bài tập |
| 5 | `cardPanel`, `cardLayout`, `exerciseUI` | Swing/View | Khác null sau init | Điều hướng màn hình con |
| 6 | Các nút nav | `JButton` | Khác null | Chuyển tab chức năng |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `DashboardUI` | Các dependency chính | Constructor | Khởi tạo dashboard |
| 2 | `navigateToExerciseInput` | `Exercise ex` | `void` | Mở màn hình nhập log bài tập |
| 3 | `showLibrary` | Không | `void` | Quay về thư viện |
| 4 | `buildNavBar`, `switchTo`, `setNavActive`, `createNavButton` | Tùy hàm | `JPanel` / `void` / `JButton` | Xử lý UI điều hướng |

### 5.5. Lớp `ExerciseLibraryUI`

Màn hình hiển thị, lọc, gợi ý và quản lý thư viện bài tập.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `library` | `ExerciseLibrary` | Khác null | Nguồn bài tập |
| 2 | `currentAccount` | `IAccount` | Khác null | User/admin hiện tại |
| 3 | `suggestionService`, `adminController` | Service/Controller | Có thể null theo role | Gợi ý/admin thao tác thư viện |
| 4 | `dashboardUI` | `DashboardUI` | Có thể null | Điều hướng sang `ExerciseUI` |
| 5 | `cardsContainer`, `filterBtns` | Swing components | Khác null sau init | Hiển thị card và bộ lọc |
| 6 | `activeFilter`, `rootCategories`, `isSuggestMode`, `currentSuggestions` | State | Có thể thay đổi | Trạng thái lọc/gợi ý |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseLibraryUI` | Dependency thư viện/account/service/controller | Constructor | Khởi tạo màn hình |
| 2 | `update` | Không | `void` | Cập nhật khi thư viện thay đổi |
| 3 | Các hàm render/filter | Tùy hàm | `void` / `JPanel` | Tạo card, lọc category, hiển thị gợi ý |
| 4 | Các handler admin | Tùy thao tác | `void` | Thêm/sửa/xóa bài tập |

### 5.6. Lớp `ExerciseUI`

Form nhập dữ liệu set tập và gợi ý set tiếp theo.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `logController` | `WorkoutLogController` | Khác null | Lưu workout log |
| 2 | `handling` | `WorkoutHandling` | Khác null | Gợi ý set tiếp theo |
| 3 | `user`, `dashboard` | `User`, `DashboardUI` | Khác null | Ngữ cảnh user và điều hướng |
| 4 | `currentExercise` | `Exercise` | Có thể null trước khi chọn | Bài tập hiện tại |
| 5 | Các label/text/button/panel | Swing components | Khác null sau init | Giao diện nhập liệu |
| 6 | `isHintEnabled` | `boolean` | Mặc định false | Bật/tắt gợi ý |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseUI` | `logController`, `handling`, `user`, `dashboard` | Constructor | Khởi tạo form |
| 2 | `setSelectedExercise` | `Exercise ex` | `void` | Gán bài tập đang nhập |
| 3 | `updateFormVisibility` | `Exercise ex` | `void` | Hiện field theo `TrackingType` |
| 4 | `updateHints` | Không | `void` | Gợi ý set tiếp theo |
| 5 | `getDoubleValue`, `getIntValue` | `HintTextField txt` | `Double` / `Integer` | Đọc input/hint an toàn |
| 6 | Các hàm validate/history | Tùy hàm | `boolean` / `WorkoutLog` / `List<WorkoutLog>` | Kiểm tra input và lấy log tuần |

### 5.7. Lớp nested `ExerciseUI.HintTextField`

Text field có khả năng hiển thị hint.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `hint` | `String` | Mặc định rỗng | Nội dung hint gợi ý |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `HintTextField` | `int columns` | Constructor | Khởi tạo text field |
| 2 | `setHint` | `String hint` | `void` | Gán hint |
| 3 | `getHint` | Không | `String` | Lấy hint |
| 4 | `paintComponent` | `Graphics g` | `void` | Vẽ hint khi ô trống |

### 5.8. Lớp `NutritionUI`

Màn hình tra cứu dinh dưỡng và thêm món ăn vào log.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `nutritionController` | `NutritionLogController` | Khác null | Controller dinh dưỡng |
| 2 | `user` | `User` | Khác null | User hiện tại |
| 3 | `txtSearchFood`, `btnSearch`, `btnAddFood` | Swing components | Khác null sau init | Nhập/tìm/thêm món |
| 4 | `resultTable`, `tableModel` | `JTable`, `DefaultTableModel` | Khác null | Bảng kết quả |
| 5 | `currentListResults` | `List<NutritionLog>` | Có thể null | Kết quả tìm kiếm hiện tại |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `NutritionUI` | `nutritionController`, `user` | Constructor | Khởi tạo màn hình |
| 2 | `initComponents`, `setupEvents` | Không | `void` | Tạo UI và gắn sự kiện |
| 3 | `doSearch` | Không | `void` | Tìm dinh dưỡng bằng `SwingWorker` |
| 4 | `formatNutritionValue` | `Double value` | `String` | Hiển thị `-` nếu không có dữ liệu |

### 5.9. Lớp `ManageLogUI`

Màn hình xem/xóa workout log và nutrition log.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutCtrl`, `nutritionCtrl` | Controller | Khác null | Quản lý log |
| 2 | `user` | `User` | Khác null | Chỉ hiển thị log của user |
| 3 | `workoutTable`, `nutritionTable` | `JTable` | Khác null sau init | Bảng log |
| 4 | `workoutModel`, `nutritionModel` | `DefaultTableModel` | Khác null | Model bảng |
| 5 | `displayedWorkoutLogs`, `displayedNutritionLogs` | `List` | Có thể rỗng | Log đang hiển thị |
| 6 | `formatter` | `DateTimeFormatter` | Khác null | Format thời gian |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `ManageLogUI` | `workoutCtrl`, `nutritionCtrl`, `user` | Constructor | Khởi tạo và đăng ký observer |
| 2 | `update` | Không | `void` | Reload dữ liệu khi log thay đổi |
| 3 | `loadWorkoutData`, `loadNutritionData` | Không | `void` | Nạp log theo user |
| 4 | `deleteWorkoutLog`, `deleteNutritionLog` | Không | `void` | Xóa log đang chọn |
| 5 | Các hàm dựng UI | Tùy hàm | `JPanel` / `JButton` / `JTable` / `void` | Tạo tab, bảng, style |

### 5.10. Lớp `StatisticsUI`

Màn hình thống kê, chart và log gần đây.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `presenter` | `StatisticsPresenter` | Khác null | Nguồn dữ liệu thống kê |
| 2 | `user` | `User` | Khác null | User hiện tại |
| 3 | `workoutHandling` | `WorkoutHandling` | Khác null | Cập nhật strategy khi đổi goal |
| 4 | `workoutDataset`, `nutritionDataset` | `DefaultCategoryDataset` | Khác null sau init | Dataset chart |
| 5 | `recentWorkoutModel`, `recentNutritionModel` | `DefaultTableModel` | Khác null | Bảng log gần đây |
| 6 | `lblTotalVolume`, `lblTotalCalo`, `cbGoal`, `btnUpdateGoal` | Swing components | Khác null | Hiển thị/cập nhật thống kê |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `StatisticsUI` | `presenter`, `user`, `workoutHandling`, `wCtrl`, `nCtrl` | Constructor | Khởi tạo và đăng ký observer |
| 2 | `update` | Không | `void` | Refresh khi log thay đổi |
| 3 | `refreshData` | Không | `void` | Nạp chart và log gần đây |
| 4 | Các hàm dựng/style UI | Tùy hàm | `JPanel` / `JTable` / `void` | Tạo card, chart, table |

### 5.11. Lớp `ProfileUI`

Màn hình thông tin cá nhân và đăng xuất.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Các hằng màu | `Color` | `static final` | Màu giao diện |
| 2 | User truyền vào constructor | `User` | Khác null | Dùng để render thông tin |
| 3 | `mainFrame` truyền vào constructor | `MainFrame` | Khác null | Điều hướng đăng xuất |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `ProfileUI` | `User user`, `MainFrame mainFrame` | Constructor | Tạo màn hình hồ sơ |
| 2 | `makeStatCard`, `makeInfoRow` | Chuỗi hiển thị | `JPanel` | Tạo card/row thông tin |
| 3 | `getInitials`, `goalLabel`, `bmi` | `User` / `String` | `String` / `double` | Tính nội dung hiển thị |

### 5.12. Lớp `AdminUI`

Màn hình làm việc của quản trị viên.

| STT | Tên Thuộc Tính | Kiểu dữ liệu | Ràng Buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame` | `MainFrame` | Khác null | Điều hướng |
| 2 | `controller` | `AdminController` | Khác null | Controller admin |
| 3 | `admin` | `Admin` | Khác null | Admin hiện tại |
| 4 | `library` | `ExerciseLibrary` | Khác null | Thư viện bài tập |
| 5 | `workoutCtrl` | `WorkoutLogController` | Khác null | Xem lịch sử tập user |
| 6 | `cardPanel`, `cardLayout`, `userListContainer`, `userDetailsContainer` | Swing components | Khác null sau init | Quản lý màn hình con |

| STT | Tên Phương Thức | Dữ Liệu Đầu Vào | Dữ Liệu Đầu Ra | Ghi Chú/Ý Nghĩa |
|---|---|---|---|---|
| 1 | `AdminUI` | `mainFrame`, `controller`, `admin`, `library`, `workoutCtrl` | Constructor | Khởi tạo admin UI |
| 2 | `refreshUserListPanel` | Không | `void` | Refresh danh sách user |
| 3 | `showUserDetails` | `User user` | `void` | Mở chi tiết user |
| 4 | `createUserListPanel`, `createAdminLibraryPanel`, `createWorkoutHistoryPanel` | Tùy hàm | `JPanel` | Tạo các panel chính |
| 5 | `createWorkoutLogRow`, `formatWorkoutResult` | `WorkoutLog log` | `JPanel` / `String` | Hiển thị lịch sử tập |
