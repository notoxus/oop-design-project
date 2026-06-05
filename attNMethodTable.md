# ATTRIBUTE, METHOD TABLE

## 2. Package `com.group3.model`

- Interface IAccount:
Interface chung cho `User` và `Admin`, giúp đăng nhập trả về tài khoản theo kiểu đa hình.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface chỉ định nghĩa hành vi chung |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `getUsername` | Không | `String` | Lấy tên đăng nhập |
| 2 | `getPassword` | Không | `String` | Lấy mật khẩu |

- Lớp Admin:
Lớp đại diện tài khoản quản trị viên.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `name` | `String` | NOT NULL | Tên hiển thị admin |
| 2 | `username` | `String` | Duy nhất | Tên đăng nhập admin |
| 3 | `password` | `String` | NOT NULL | Mật khẩu admin |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `Admin` | `name`, `username`, `password` | Constructor | Khởi tạo admin |
| 2 | `getName` | Không | `String` | Lấy tên admin |
| 3 | `getUsername` | Không | `String` | Lấy username |
| 4 | `getPassword` | Không | `String` | Lấy password |

- Lớp User:
Lớp lưu thông tin người dùng, chỉ số cơ thể, mục tiêu và danh sách log tạm thời.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Duy nhất | Mã định danh người dùng |
| 2 | `name` | `String` | NOT NULL | Tên hiển thị |
| 3 | `username` | `String` | Duy nhất | Tên đăng nhập |
| 4 | `password` | `String` | NOT NULL | Mật khẩu trong demo |
| 5 | `age` | `int` | `> 0` | Tuổi |
| 6 | `gender` | `String` | NOT NULL | Giới tính |
| 7 | `height` | `double` | `> 0` | Chiều cao, có thể nhập cm hoặc m |
| 8 | `weight` | `double` | `> 0` | Cân nặng kg |
| 9 | `goal` | `WorkoutGoal` | Theo enum | Mục tiêu tập luyện |
| 10 | `workoutLog` | `transient List<WorkoutLog>` | NULL | Danh sách log tập luyện tạm thời |
| 11 | `nutritionLog` | `transient List<NutritionLog>` | NULL | Danh sách log dinh dưỡng tạm thời |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `User` | Không | Constructor | Khởi tạo list log rỗng |
| 2 | `User` | `userID`, `name`, `username`, `password`, `age`, `gender`, `height`, `weight`, `goal` | Constructor | Khởi tạo user đầy đủ |
| 3 | Getters/Setters | Tùy thuộc thuộc tính | Tùy thuộc thuộc tính | Truy xuất và cập nhật dữ liệu đóng gói |
| 4 | `bmiCal` | Không | `double` | Tính BMI, tự đổi cm sang m nếu cần |

- Enum WorkoutGoal:
Enum biểu diễn mục tiêu tập luyện của người dùng.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `LOSE_FAT` | Enum constant | Cố định | Mục tiêu giảm mỡ |
| 2 | `MUSCLE_GAIN` | Enum constant | Cố định | Mục tiêu tăng cơ |
| 3 | `MAINTENANCE` | Enum constant | Cố định | Mục tiêu duy trì |
| 4 | `displayName` | `String` | NOT NULL | Tên hiển thị của enum |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `WorkoutGoal` | `displayName` | Constructor enum | Gán tên hiển thị |
| 2 | `toString` | Không | `String` | Trả về tên hiển thị |

- Enum TrackingType:
Enum biểu diễn cách ghi nhận dữ liệu cho từng bài tập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `WEIGHT_REP_TIME` | Enum constant | Cố định | Theo dõi tạ, reps, thời gian |
| 2 | `DISTANCE_TIME` | Enum constant | Cố định | Theo dõi quãng đường và thời gian |
| 3 | `TIME_ONLY` | Enum constant | Cố định | Chỉ theo dõi thời gian |
| 4 | `WEIGHT_REP` | Enum constant | Cố định | Theo dõi tạ và reps |
| 5 | `REP_ONLY` | Enum constant | Cố định | Chỉ theo dõi reps |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | Không có phương thức riêng | - | - | Sử dụng các phương thức mặc định của enum |

- Lớp ExerciseCategory:
Lớp biểu diễn danh mục bài tập và danh mục con theo Composite rút gọn.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `catID` | `int` | Duy nhất | Mã danh mục |
| 2 | `catName` | `String` | NOT NULL | Tên danh mục |
| 3 | `allowedTrackingTypes` | `List<TrackingType>` | NULL | Các kiểu tracking được phép |
| 4 | `subCat` | `List<ExerciseCategory>` | NOT NULL, khởi tạo rỗng | Danh mục con |
| 5 | `parentCat` | `transient ExerciseCategory` | NULL | Danh mục cha |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseCategory` | `catID`, `catName`, `allowedTrackingTypes` | Constructor | Khởi tạo danh mục |
| 2 | `addSubCat` | `ExerciseCategory newSubCat` | `void` | Thêm danh mục con và gán parent |
| 3 | Getters | Không | Tùy thuộc thuộc tính | Lấy thông tin danh mục |
| 4 | `setParentCat` | `ExerciseCategory parentCat` | `void` | Gán danh mục cha |
| 5 | `toString` | Không | `String` | Trả về tên danh mục |
| 6 | `equals`, `hashCode` | `Object obj` / Không | `boolean` / `int` | So sánh theo `catID` |

- Lớp Exercise:
Lớp mô tả một bài tập trong thư viện.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `exerciseID` | `int` | Duy nhất | Mã bài tập |
| 2 | `exerciseName` | `String` | NOT NULL | Tên bài tập |
| 3 | `category` | `ExerciseCategory` | NOT NULL | Danh mục bài tập |
| 4 | `trackingType` | `TrackingType` | NOT NULL | Kiểu theo dõi chỉ số |
| 5 | `targetMuscle` | `String` | NULL | Nhóm cơ tác động |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `Exercise` | `ExerciseBuilder builder` | Constructor private | Tạo `Exercise` từ builder |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy thông tin bài tập |
| 3 | `toString` | Không | `String` | Chuỗi mô tả bài tập |

- Lớp Exercise.ExerciseBuilder:
Builder tạo `Exercise` theo fluent API.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `exerciseID` | `int` | Mặc định `-1` | Mã bài tập cần set |
| 2 | `exerciseName` | `String` | NOT NULL | Tên bài tập |
| 3 | `category` | `ExerciseCategory` | NOT NULL | Danh mục |
| 4 | `trackingType` | `TrackingType` | NOT NULL | Kiểu tracking |
| 5 | `targetMuscle` | `String` | NULL | Nhóm cơ |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `setExerciseID` | `int exerciseID` | `ExerciseBuilder` | Gán ID |
| 2 | `setExerciseName` | `String exerciseName` | `ExerciseBuilder` | Gán tên |
| 3 | `setCategory` | `ExerciseCategory category` | `ExerciseBuilder` | Gán danh mục |
| 4 | `setTrackingType` | `TrackingType trackingType` | `ExerciseBuilder` | Gán kiểu tracking |
| 5 | `setTargetMuscle` | `String targetMuscle` | `ExerciseBuilder` | Gán nhóm cơ |
| 6 | `build` | Không | `Exercise` | Kiểm tra dữ liệu bắt buộc và tạo object |

- Lớp ExerciseFactory:
Simple Factory tạo `Exercise` và kết hợp `ExerciseBuilder`.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Factory stateless |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `createExercise` | `id`, `name`, `category`, `trackingType`, `targetMuscle` | `Exercise` | Tạo bài tập, tự chọn tracking mặc định nếu null |

- Lớp ExerciseLibrary:
Thư viện bài tập, đồng thời là `Subject` trong Observer Pattern.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `lib` | `List<Exercise>` | NULL | Danh sách bài tập |
| 2 | `observers` | `transient List<Observer>` | Khởi tạo khi cần | Danh sách view đăng ký theo dõi |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseLibrary` | `List<Exercise> list` | Constructor | Khởi tạo thư viện |
| 2 | `getLib` | Không | `List<Exercise>` | Lấy danh sách bài tập |
| 3 | `add`, `remove`, `notifyObservers` | `Observer o` / Không | `void` | Cài đặt Subject |
| 4 | `addExercise` | `Exercise exercise` | `void` | Thêm bài tập và notify |
| 5 | `removeExercise` | `Exercise exercise` | `void` | Xóa bài tập và notify |
| 6 | `getByCategory` | `ExerciseCategory cat` | `List<Exercise>` | Lọc bài tập theo danh mục |
| 7 | `getCategoryByName` | `String catName` | `List<Exercise>` | Lọc bài tập theo tên danh mục |
| 8 | `searchExercise` | `String name` | `Exercise` | Tìm bài tập theo tên |

- Interface Observer:
Interface cho các view cần tự cập nhật khi dữ liệu thay đổi.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `update` | Không | `void` | Cập nhật view khi subject thay đổi |

- Interface Subject:
Interface cho đối tượng phát thông báo trong Observer Pattern.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `add` | `Observer o` | `void` | Đăng ký observer |
| 2 | `remove` | `Observer o` | `void` | Hủy đăng ký observer |
| 3 | `notifyObservers` | Không | `void` | Thông báo observer cập nhật |

- Lớp WorkoutLog:
Lớp lưu một bản ghi tập luyện.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã người dùng |
| 2 | `logID` | `int` | Duy nhất | Mã log |
| 3 | `date` | `LocalDateTime` | NOT NULL | Thời điểm tập |
| 4 | `exercise` | `Exercise` | NOT NULL | Bài tập |
| 5 | `weight` | `Double` | NULL | Mức tạ kg |
| 6 | `reps` | `Integer` | NULL | Số reps |
| 7 | `distance` | `Double` | NULL | Quãng đường km |
| 8 | `time` | `Double` | NULL | Thời gian phút |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `WorkoutLog` | `WorkoutLogBuilder builder` | Constructor private | Tạo log từ builder |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy dữ liệu log |
| 3 | `toString` | Không | `String` | Chuỗi mô tả log |
| 4 | `paceCal` | Không | `double` | Tính pace `time / distance` |

- Lớp WorkoutLog.WorkoutLogBuilder:
Builder tạo `WorkoutLog`.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã user |
| 2 | `logID` | `int` | Mặc định `-1` | Mã log bắt buộc |
| 3 | `date` | `LocalDateTime` | NOT NULL | Thời điểm |
| 4 | `exercise` | `Exercise` | NOT NULL | Bài tập |
| 5 | `weight`, `distance`, `time` | `Double` | NULL | Chỉ số thực |
| 6 | `reps` | `Integer` | NULL | Số reps |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | Các setter fluent | Giá trị từng thuộc tính | `WorkoutLogBuilder` | Gán dữ liệu từng bước |
| 2 | `build` | Không | `WorkoutLog` | Kiểm tra `logID`, `date`, `exercise` và tạo log |

- Lớp NutritionLog:
Lớp lưu một bản ghi dinh dưỡng.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã người dùng |
| 2 | `logID` | `int` | Duy nhất | Mã log dinh dưỡng |
| 3 | `productID` | `int` | Có thể dùng từ API | Mã sản phẩm |
| 4 | `productName` | `String` | NOT NULL | Tên thực phẩm |
| 5 | `addTime` | `LocalDateTime` | NOT NULL | Thời điểm thêm |
| 6 | `quantity` | `Integer` | Mặc định `1` | Số lượng |
| 7 | `energy` | `Double` | NULL | Kcal |
| 8 | `protein` | `Double` | NULL | Protein |
| 9 | `fat` | `Double` | NULL | Fat |
| 10 | `carb` | `Double` | NULL | Carbohydrate |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `NutritionLog` | `Builder builder` | Constructor private | Tạo log từ builder |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy dữ liệu dinh dưỡng |
| 3 | `toString` | Không | `String` | Chuỗi mô tả dinh dưỡng |

- Lớp NutritionLog.Builder:
Builder tạo `NutritionLog`.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userID` | `int` | Phải thuộc user | Mã user |
| 2 | `logID` | `int` | Mặc định `-1` | Mã log |
| 3 | `productID` | `int` | Mặc định `-1` | Mã sản phẩm bắt buộc |
| 4 | `productName` | `String` | NOT NULL | Tên sản phẩm bắt buộc |
| 5 | `addTime` | `LocalDateTime` | Mặc định hiện tại | Thời điểm thêm |
| 6 | `quantity` | `int` | Mặc định `1` | Số lượng |
| 7 | `energy`, `protein`, `fat`, `carbohydrates` | `Double` | NULL | Chỉ số dinh dưỡng |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | Các setter fluent | Giá trị từng thuộc tính | `Builder` | Gán dữ liệu từng bước |
| 2 | `build` | Không | `NutritionLog` | Kiểm tra product bắt buộc và tạo log |

- Lớp LogCollection:
Wrapper gom workout log và nutrition log để đọc/ghi JSON.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutLogs` | `List<WorkoutLog>` | NOT NULL sau constructor | Danh sách log tập |
| 2 | `nutritionLogs` | `List<NutritionLog>` | NOT NULL sau constructor | Danh sách log dinh dưỡng |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `LogCollection` | `workoutLogs`, `nutritionLogs` | Constructor | Nếu input null thì tạo list rỗng |
| 2 | `getWorkoutLogs` | Không | `List<WorkoutLog>` | Lấy workout logs |
| 3 | `getNutritionLogs` | Không | `List<NutritionLog>` | Lấy nutrition logs |

- Lớp RecommendationResult:
Kết quả trả về của chiến lược gợi ý set tập tiếp theo.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `suggestedWeight` | `Double` | NULL | Mức tạ gợi ý |
| 2 | `suggestedReps` | `Integer` | NULL | Số reps gợi ý |
| 3 | `suggestedDistance` | `Double` | NULL | Quãng đường gợi ý |
| 4 | `suggestedTime` | `Double` | NULL | Thời gian gợi ý |
| 5 | `message` | `String` | NULL | Lời giải thích |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `RecommendationResult` | `weight`, `reps`, `distance`, `time`, `message` | Constructor | Đóng gói kết quả |
| 2 | Getters | Không | Tùy thuộc thuộc tính | Lấy giá trị gợi ý |
| 3 | `toString` | Không | `String` | Chuỗi mô tả kết quả |

- Interface ExerciseSuggestionStrategy:
Strategy interface cho gợi ý bài tập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface thuật toán |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Gợi ý danh sách bài tập |

- Lớp FitStrategy:
Strategy gợi ý bài tập cân bằng/duy trì.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Lọc/chọn bài tập phù hợp mục tiêu duy trì |

- Lớp ThinStrategy:
Strategy gợi ý bài tập cho người cần tăng cơ/cải thiện thể trạng gầy.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Gợi ý bài tập thiên về tăng cơ |

- Lớp FatStrategy:
Strategy gợi ý bài tập cho người cần giảm mỡ.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `suggest` | `User user`, `ExerciseLibrary lib` | `List<Exercise>` | Gợi ý bài tập thiên về cardio/giảm mỡ |

- Interface NextSetRecommendationStrategy:
Strategy interface cho gợi ý set tập tiếp theo.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface thuật toán |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `WorkoutLog currentLog`, `List<WorkoutLog> weeklyLogs` | `RecommendationResult` | Gợi ý set tiếp theo dựa trên log hiện tại và lịch sử tuần |
| 2 | `calculateNextSet` | `WorkoutLog currentLog` | `RecommendationResult` | Overload mặc định dùng lịch sử rỗng |
| 3 | `hasWeeklyHistory`, `isHighWeeklyLoad` | `weeklyLogs` | `boolean` | Helper mặc định đánh giá lịch sử tuần |

- Lớp LoseFatStrategy:
Strategy gợi ý set tập theo mục tiêu giảm mỡ.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Tính gợi ý phù hợp giảm mỡ, có xét tải tập tuần |

- Lớp MuscleGainStrategy:
Strategy gợi ý set tập theo mục tiêu tăng cơ.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Tính gợi ý phù hợp tăng cơ, có xét tải tập tuần |

- Lớp NoWeightStrategy:
Strategy gợi ý cho bài tập không dùng tạ hoặc không có `weight`.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Stateless strategy |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Gợi ý reps/time/distance cho bài không có tạ |

- Interface DataConnection<T>:
Interface persistence tổng quát.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `loadData` | Không | `T` | Đọc dữ liệu từ nguồn lưu trữ |
| 2 | `saveData` | `T data` | `boolean` | Ghi dữ liệu xuống nguồn lưu trữ |

- Lớp AJsonDatabase<T>:
Abstract class áp dụng Template Method cho đọc/ghi JSON.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `filePath` | `String` | NOT NULL | Đường dẫn file JSON |
| 2 | `gson` | `Gson` | NOT NULL | Bộ parse/serialize JSON |
| 3 | `typeOfT` | `Type` | NOT NULL | Kiểu dữ liệu generic cần parse |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `AJsonDatabase` | `String filePath`, `Type typeOfT` | Constructor | Khởi tạo đường dẫn, type và Gson |
| 2 | `createGson` | Không | `Gson` | Tạo Gson có pretty printing |
| 3 | `loadData` | Không | `T` | Đọc JSON, lỗi thì trả default |
| 4 | `saveData` | `T data` | `boolean` | Ghi JSON |
| 5 | `getDefaultValue` | Không | `T` | Hook method do subclass cài đặt |

- Lớp JsonUserDatabase:
Database JSON cho user.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `JsonUserDatabase` | Không | Constructor | Dùng file user JSON mặc định |
| 2 | `getDefaultValue` | Không | `List<User>` | Trả list user rỗng |
| 3 | `checkLogin` | `username`, `password` | `User` | Xác thực user |
| 4 | `isUsernameExist` | `username` | `boolean` | Kiểm tra trùng username |
| 5 | `addUser` | `User newUser` | `boolean` | Thêm user mới |
| 6 | `updateUser` | `User updatedUser` | `boolean` | Cập nhật thông tin user |

- Lớp JsonAdminDatabase:
Database JSON cho admin.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `JsonAdminDatabase` | Không | Constructor | Dùng file admin JSON mặc định |
| 2 | `checkLogin` | `username`, `password` | `Admin` | Xác thực admin |
| 3 | `isUsernameExist` | `username` | `boolean` | Kiểm tra username admin |
| 4 | `getDefaultValue` | Không | `List<Admin>` | Trả list admin mặc định/rỗng |

- Lớp JsonExerciseDatabase:
Database JSON cho thư viện bài tập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `JsonExerciseDatabase` | Không | Constructor | Dùng file thư viện bài tập mặc định |
| 2 | `getDefaultValue` | Không | `List<Exercise>` | Trả list bài tập rỗng |

- Lớp JsonCategoryDatabase:
Database JSON cho danh mục bài tập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Kế thừa từ `AJsonDatabase` | - | - | Dùng `filePath`, `gson`, `typeOfT` |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `JsonCategoryDatabase` | Không | Constructor | Dùng file category mặc định |
| 2 | `getDefaultValue` | Không | `List<ExerciseCategory>` | Trả list danh mục rỗng |

- Lớp JsonLogDatabase:
Database JSON riêng cho hai loại log.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutFile` | `String` | Hằng instance | File workout log |
| 2 | `nutritionFile` | `String` | Hằng instance | File nutrition log |
| 3 | `gson` | `Gson` | NOT NULL | Gson có adapter cho `LocalDateTime` |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `JsonLogDatabase` | Không | Constructor | Khởi tạo Gson cho log |
| 2 | `loadData` | Không | `LogCollection` | Đọc hai file log |
| 3 | `saveData` | `LogCollection data` | `boolean` | Ghi hai file log |
| 4 | `loadList` | `file`, `type` | `List<T>` | Đọc một list generic |
| 5 | `saveList` | `file`, `list` | `boolean` | Ghi một list generic |

- Interface INutrition:
Target interface trong Adapter Pattern.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Interface hành vi |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `getNutritionInfo` | `String productName` | `List<NutritionLog>` | Tra cứu dinh dưỡng theo tên sản phẩm |

- Lớp OpenFoodFactsAdapter:
Adapter chuyển dữ liệu OpenFoodFacts API thành `NutritionLog`.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `api` | `OpenFoodFactsAPI` | NOT NULL | Adaptee được adapter sử dụng |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `OpenFoodFactsAdapter` | Không hoặc `OpenFoodFactsAPI api` | Constructor | Khởi tạo adapter |
| 2 | `getNutritionInfo` | `String productName` | `List<NutritionLog>` | Gọi API và parse kết quả |
| 3 | `parseJsonObject` | `String jsonResponse` | `JsonObject` | Parse JSON lenient |
| 4 | `resolveProductName` | `JsonObject product`, `String fallbackName` | `String` | Lấy tên sản phẩm ưu tiên vi/en/default |
| 5 | `getSafeString`, `getSafeObject`, `getSafeDouble` | JSON object và key | Tùy kiểu | Đọc JSON an toàn |
| 6 | `getEnergyKcal`, `getFirstSafeDouble` | `JsonObject nutriments` / key list | `Double` | Lấy chỉ số dinh dưỡng, hỗ trợ fallback |

## 3. Package `com.group3.controller`

- Lớp AdminController:
Điều phối nghiệp vụ quản trị thư viện bài tập và xem user.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `exerciseLibrary` | `ExerciseLibrary` | NOT NULL | Thư viện bài tập |
| 2 | `admin` | `Admin` | NOT NULL | Admin hiện tại |
| 3 | `libraryDB` | `JsonExerciseDatabase` | NOT NULL | Database bài tập |
| 4 | `userDB` | `JsonUserDatabase` | NOT NULL | Database user |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `AdminController` | `library`, `admin`, `libraryDB`, `userDB` | Constructor | Khởi tạo controller admin |
| 2 | `addExercise` | `id`, `name`, `category`, `trackingType`, `targetMuscle` | `boolean` | Tạo bài qua factory và lưu |
| 3 | `updateExercise` | `oldExercise`, `newName`, `category`, `trackingType`, `targetMuscle` | `boolean` | Cập nhật bài tập |
| 4 | `deleteExercise` | `String exerciseName` | `boolean` | Xóa bài tập theo tên |
| 5 | `viewUserDetails` | Không | `List<User>` | Lấy danh sách user |

- Lớp LoginManager:
Xử lý đăng nhập user/admin.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userDatabase` | `JsonUserDatabase` | NOT NULL | Database user |
| 2 | `adminDatabase` | `JsonAdminDatabase` | NOT NULL | Database admin |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `LoginManager` | `userDatabase`, `adminDatabase` | Constructor | Khởi tạo manager |
| 2 | `login` | `username`, `password` | `IAccount` | Xác thực, trả về `Admin` hoặc `User` |

- Lớp RegisterManager:
Xử lý đăng ký user mới.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `userDatabase` | `JsonUserDatabase` | NOT NULL | Database user |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `RegisterManager` | `JsonUserDatabase userDatabase` | Constructor | Khởi tạo manager |
| 2 | `register` | `User newUser` | `boolean` | Kiểm tra trùng username và thêm user |

- Lớp WorkoutLogController:
Controller quản lý workout log và là `Subject`.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutDB` | `DataConnection<LogCollection>` | NOT NULL | Nguồn lưu log |
| 2 | `observers` | `List<Observer>` | NOT NULL, khởi tạo rỗng | View đăng ký cập nhật |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `WorkoutLogController` | `DataConnection<LogCollection> workoutDB` | Constructor | Khởi tạo controller |
| 2 | `add`, `remove`, `notifyObservers` | `Observer o` / Không | `void` | Cài đặt Subject |
| 3 | `addWorkoutLog` | `WorkoutLog newWorkoutLog` | `boolean` | Thêm log và notify |
| 4 | `removeWorkoutLog` | `int logID` | `boolean` | Xóa log và notify |
| 5 | `getAllLogs` | Không | `List<WorkoutLog>` | Lấy toàn bộ workout log |

- Lớp NutritionLogController:
Controller quản lý nutrition log, tra cứu dinh dưỡng và là `Subject`.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `nutritionDB` | `DataConnection<LogCollection>` | NOT NULL | Nguồn lưu log |
| 2 | `nutrition` | `INutrition` | NOT NULL | Adapter interface |
| 3 | `observers` | `List<Observer>` | NOT NULL, khởi tạo rỗng | View đăng ký cập nhật |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `NutritionLogController` | `nutritionDB`, `nutritionAPI` | Constructor | Khởi tạo controller |
| 2 | `addNutritionLog` | `NutritionLog newNutritionLog` | `boolean` | Thêm log và notify |
| 3 | `removeNutritionLog` | `int logID` | `boolean` | Xóa log và notify |
| 4 | `lookupNutrition` | `String productName` | `List<NutritionLog>` | Tra cứu dinh dưỡng |
| 5 | `getAllLogs` | Không | `List<NutritionLog>` | Lấy toàn bộ nutrition log |
| 6 | `add`, `remove`, `notifyObservers` | `Observer o` / Không | `void` | Cài đặt Subject |

- Lớp StatisticsPresenter:
Tổng hợp dữ liệu thống kê để View hiển thị chart/bảng.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `database` | `DataConnection<LogCollection>` | NOT NULL | Nguồn dữ liệu thống kê |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `StatisticsPresenter` | `DataConnection<LogCollection> database` | Constructor | Khởi tạo presenter |
| 2 | `dailyNutritionSummary` | `LocalDate targetDate`, `int userID` | `double[]` | Tổng hợp dinh dưỡng theo ngày |
| 3 | `dailyWorkoutSummary` | `LocalDate targetDate`, `int userID` | `double[]` | Tổng hợp tập luyện theo ngày |
| 4 | `getWorkoutChartData` | `int userID` | `Map<LocalDate, Map<ExerciseCategory, Double>>` | Dữ liệu chart tập luyện |
| 5 | `getNutritionChartData` | `int userID` | `Map<LocalDate, Double>` | Dữ liệu chart dinh dưỡng |
| 6 | `updateGoal` | `User user` | `boolean` | Cập nhật mục tiêu user |
| 7 | `getRecentWorkout` | `userID`, `limit` | `List<WorkoutLog>` | Lấy workout log gần đây |
| 8 | `getRecentNutrition` | `userID`, `limit` | `List<NutritionLog>` | Lấy nutrition log gần đây |

- Lớp WorkoutHandling:
Context của Strategy gợi ý set tập tiếp theo.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `noWeightStrategy` | `NoWeightStrategy` | Khởi tạo sẵn | Strategy mặc định cho bài không có tạ |
| 2 | `nextSetStrategy` | `NextSetRecommendationStrategy` | NULL trước khi set goal | Strategy hiện tại |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `calculateNextSet` | `WorkoutLog currentLog` | `RecommendationResult` | Gợi ý với lịch sử rỗng |
| 2 | `calculateNextSet` | `currentLog`, `weeklyLogs` | `RecommendationResult` | Gợi ý dựa trên lịch sử tuần |
| 3 | `setGoal` | `User user` | `void` | Chọn strategy theo mục tiêu |

- Lớp ExerciseSuggestionService:
Service điều phối Strategy gợi ý bài tập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có field lưu strategy | - | - | Strategy được tạo theo từng lần gọi |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `recommend` | `User newUser` | `WorkoutGoal` | Gợi ý mục tiêu theo BMI |
| 2 | `applyUserChoice` | `User user`, `WorkoutGoal chosenGoal` | `void` | Áp dụng mục tiêu người dùng chọn |
| 3 | `suggest` | `User user`, `ExerciseLibrary library` | `List<Exercise>` | Tạo strategy và gợi ý bài tập |
| 4 | `createStrategy` | `WorkoutGoal goal` | `ExerciseSuggestionStrategy` | Tạo strategy cụ thể |

## 4. Package `com.group3.util`

- Lớp OpenFoodFactsAPI:
Adaptee gọi OpenFoodFacts API.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `PREFIX_URL` | `String` | `static final` | URL gốc API |
| 2 | `SEARCH_OPTIONS` | `String` | `static final` | Tham số tìm kiếm |
| 3 | `VI_LOCALE` | `String` | `static final` | Locale tiếng Việt |
| 4 | `EN_LOCALE` | `String` | `static final` | Locale tiếng Anh fallback |
| 5 | `CLIENT` | `HttpClient` | `static final` | HTTP client timeout |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `fetchNutritionData` | `String productName` | `String` | Tìm tiếng Việt trước, fallback tiếng Anh |
| 2 | `fetchByLocale` | `encodedName`, `localeQuery` | `String` | Gửi request theo locale |
| 3 | `isEmptyResult` | `String responseBody` | `boolean` | Kiểm tra response rỗng |

- Lớp RandomAlgorithm:
Utility random bài tập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Không có | - | - | Utility class stateless |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `RandomAlgorithm` | Không | Constructor private | Ngăn tạo object utility |
| 2 | `pickRandom` | `List<Exercise> source`, `int count` | `List<Exercise>` | Shuffle và lấy tối đa `count` bài tập |

## 5. Package `com.group3.view`

- Lớp MainFrame:
Cửa sổ chính điều hướng giữa login, register, dashboard user và dashboard admin.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `cardLayout`, `mainContainer` | `CardLayout`, `JPanel` | NOT NULL | Điều hướng màn hình |
| 2 | `loginManager`, `registerManager` | Manager | NOT NULL | Xử lý auth |
| 3 | `libraryDB`, `userDB` | Database | NOT NULL | Database chính |
| 4 | `workoutCtrl`, `nutritionCtrl` | Controller | NOT NULL | Controller log |
| 5 | `workoutHandling`, `suggestionService`, `statPresenter` | Service/Presenter | NOT NULL | Logic nghiệp vụ |
| 6 | `library` | `ExerciseLibrary` | NOT NULL | Thư viện bài tập |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `MainFrame` | Các manager/database/controller/service | Constructor | Khởi tạo cửa sổ chính |
| 2 | `showLoginScreen` | Không | `void` | Chuyển sang login |
| 3 | `showRegisterScreen` | Không | `void` | Chuyển sang đăng ký |
| 4 | `showUserDashboard` | `User user` | `void` | Mở dashboard user |
| 5 | `showAdminDashboard` | `Admin admin` | `void` | Mở dashboard admin |

- Lớp LoginForm:
Màn hình đăng nhập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame` | `MainFrame` | NOT NULL | Điều hướng sau đăng nhập |
| 2 | `loginManager` | `LoginManager` | NOT NULL | Xử lý đăng nhập |
| 3 | Các component nhập liệu | `JTextField`, `JPasswordField`, `JButton` | NOT NULL sau init | Nhập username/password và thao tác |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `LoginForm` | `MainFrame`, `LoginManager` | Constructor | Khởi tạo form |
| 2 | `initComponents` | Không | `void` | Tạo giao diện |
| 3 | `handleLogin` | Không | `void` | Gọi `LoginManager` và điều hướng |

- Lớp RegisterForm:
Màn hình đăng ký tài khoản user.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame` | `MainFrame` | NOT NULL | Điều hướng |
| 2 | `registerManager` | `RegisterManager` | NOT NULL | Xử lý đăng ký |
| 3 | Các field nhập liệu | Swing components | NOT NULL sau init | Nhập thông tin user |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `RegisterForm` | `MainFrame`, `RegisterManager` | Constructor | Khởi tạo form |
| 2 | `initComponents` | Không | `void` | Tạo giao diện |
| 3 | `handleRegister` | Không | `void` | Tạo user và gọi register |

- Lớp DashboardUI:
Dashboard user và thanh điều hướng nội bộ.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame`, `user` | `MainFrame`, `User` | NOT NULL | Ngữ cảnh màn hình |
| 2 | `workoutCtrl`, `nutritionCtrl` | Controller | NOT NULL | Controller dữ liệu |
| 3 | `workoutHandling`, `statPresenter`, `suggestionService` | Service/Presenter | NOT NULL | Logic nghiệp vụ |
| 4 | `library` | `ExerciseLibrary` | NOT NULL | Thư viện bài tập |
| 5 | `cardPanel`, `cardLayout`, `exerciseUI` | Swing/View | NOT NULL sau init | Điều hướng màn hình con |
| 6 | Các nút nav | `JButton` | NOT NULL | Chuyển tab chức năng |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `DashboardUI` | Các dependency chính | Constructor | Khởi tạo dashboard |
| 2 | `navigateToExerciseInput` | `Exercise ex` | `void` | Mở màn hình nhập log bài tập |
| 3 | `showLibrary` | Không | `void` | Quay về thư viện |
| 4 | `buildNavBar`, `switchTo`, `setNavActive`, `createNavButton` | Tùy hàm | `JPanel` / `void` / `JButton` | Xử lý UI điều hướng |

- Lớp ExerciseLibraryUI:
Màn hình hiển thị, lọc, gợi ý và quản lý thư viện bài tập.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `library` | `ExerciseLibrary` | NOT NULL | Nguồn bài tập |
| 2 | `currentAccount` | `IAccount` | NOT NULL | User/admin hiện tại |
| 3 | `suggestionService`, `adminController` | Service/Controller | NULL theo role | Gợi ý/admin thao tác thư viện |
| 4 | `dashboardUI` | `DashboardUI` | NULL | Điều hướng sang `ExerciseUI` |
| 5 | `cardsContainer`, `filterBtns` | Swing components | NOT NULL sau init | Hiển thị card và bộ lọc |
| 6 | `activeFilter`, `rootCategories`, `isSuggestMode`, `currentSuggestions` | State | NULL | Trạng thái lọc/gợi ý |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseLibraryUI` | Dependency thư viện/account/service/controller | Constructor | Khởi tạo màn hình |
| 2 | `update` | Không | `void` | Cập nhật khi thư viện thay đổi |
| 3 | Các hàm render/filter | Tùy hàm | `void` / `JPanel` | Tạo card, lọc category, hiển thị gợi ý |
| 4 | Các handler admin | Tùy thao tác | `void` | Thêm/sửa/xóa bài tập |

- Lớp ExerciseUI:
Form nhập dữ liệu set tập và gợi ý set tiếp theo.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `logController` | `WorkoutLogController` | NOT NULL | Lưu workout log |
| 2 | `handling` | `WorkoutHandling` | NOT NULL | Gợi ý set tiếp theo |
| 3 | `user`, `dashboard` | `User`, `DashboardUI` | NOT NULL | Ngữ cảnh user và điều hướng |
| 4 | `currentExercise` | `Exercise` | NULL trước khi chọn | Bài tập hiện tại |
| 5 | Các label/text/button/panel | Swing components | NOT NULL sau init | Giao diện nhập liệu |
| 6 | `isHintEnabled` | `boolean` | Mặc định false | Bật/tắt gợi ý |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `ExerciseUI` | `logController`, `handling`, `user`, `dashboard` | Constructor | Khởi tạo form |
| 2 | `setSelectedExercise` | `Exercise ex` | `void` | Gán bài tập đang nhập |
| 3 | `updateFormVisibility` | `Exercise ex` | `void` | Hiện field theo `TrackingType` |
| 4 | `updateHints` | Không | `void` | Gợi ý set tiếp theo |
| 5 | `getDoubleValue`, `getIntValue` | `HintTextField txt` | `Double` / `Integer` | Đọc input/hint an toàn |
| 6 | Các hàm validate/history | Tùy hàm | `boolean` / `WorkoutLog` / `List<WorkoutLog>` | Kiểm tra input và lấy log tuần |

- Lớp ExerciseUI.HintTextField:
Text field có khả năng hiển thị hint.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `hint` | `String` | Mặc định rỗng | Nội dung hint gợi ý |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `HintTextField` | `int columns` | Constructor | Khởi tạo text field |
| 2 | `setHint` | `String hint` | `void` | Gán hint |
| 3 | `getHint` | Không | `String` | Lấy hint |
| 4 | `paintComponent` | `Graphics g` | `void` | Vẽ hint khi ô trống |

- Lớp NutritionUI:
Màn hình tra cứu dinh dưỡng và thêm món ăn vào log.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `nutritionController` | `NutritionLogController` | NOT NULL | Controller dinh dưỡng |
| 2 | `user` | `User` | NOT NULL | User hiện tại |
| 3 | `txtSearchFood`, `btnSearch`, `btnAddFood` | Swing components | NOT NULL sau init | Nhập/tìm/thêm món |
| 4 | `resultTable`, `tableModel` | `JTable`, `DefaultTableModel` | NOT NULL | Bảng kết quả |
| 5 | `currentListResults` | `List<NutritionLog>` | NULL | Kết quả tìm kiếm hiện tại |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `NutritionUI` | `nutritionController`, `user` | Constructor | Khởi tạo màn hình |
| 2 | `initComponents`, `setupEvents` | Không | `void` | Tạo UI và gắn sự kiện |
| 3 | `doSearch` | Không | `void` | Tìm dinh dưỡng bằng `SwingWorker` |
| 4 | `formatNutritionValue` | `Double value` | `String` | Hiển thị `-` nếu không có dữ liệu |

- Lớp ManageLogUI:
Màn hình xem/xóa workout log và nutrition log.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `workoutCtrl`, `nutritionCtrl` | Controller | NOT NULL | Quản lý log |
| 2 | `user` | `User` | NOT NULL | Chỉ hiển thị log của user |
| 3 | `workoutTable`, `nutritionTable` | `JTable` | NOT NULL sau init | Bảng log |
| 4 | `workoutModel`, `nutritionModel` | `DefaultTableModel` | NOT NULL | Model bảng |
| 5 | `displayedWorkoutLogs`, `displayedNutritionLogs` | `List` | NULL | Log đang hiển thị |
| 6 | `formatter` | `DateTimeFormatter` | NOT NULL | Format thời gian |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `ManageLogUI` | `workoutCtrl`, `nutritionCtrl`, `user` | Constructor | Khởi tạo và đăng ký observer |
| 2 | `update` | Không | `void` | Reload dữ liệu khi log thay đổi |
| 3 | `loadWorkoutData`, `loadNutritionData` | Không | `void` | Nạp log theo user |
| 4 | `deleteWorkoutLog`, `deleteNutritionLog` | Không | `void` | Xóa log đang chọn |
| 5 | Các hàm dựng UI | Tùy hàm | `JPanel` / `JButton` / `JTable` / `void` | Tạo tab, bảng, style |

- Lớp StatisticsUI:
Màn hình thống kê, chart và log gần đây.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `presenter` | `StatisticsPresenter` | NOT NULL | Nguồn dữ liệu thống kê |
| 2 | `user` | `User` | NOT NULL | User hiện tại |
| 3 | `workoutHandling` | `WorkoutHandling` | NOT NULL | Cập nhật strategy khi đổi goal |
| 4 | `workoutDataset`, `nutritionDataset` | `DefaultCategoryDataset` | NOT NULL sau init | Dataset chart |
| 5 | `recentWorkoutModel`, `recentNutritionModel` | `DefaultTableModel` | NOT NULL | Bảng log gần đây |
| 6 | `lblTotalVolume`, `lblTotalCalo`, `cbGoal`, `btnUpdateGoal` | Swing components | NOT NULL | Hiển thị/cập nhật thống kê |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `StatisticsUI` | `presenter`, `user`, `workoutHandling`, `wCtrl`, `nCtrl` | Constructor | Khởi tạo và đăng ký observer |
| 2 | `update` | Không | `void` | Refresh khi log thay đổi |
| 3 | `refreshData` | Không | `void` | Nạp chart và log gần đây |
| 4 | Các hàm dựng/style UI | Tùy hàm | `JPanel` / `JTable` / `void` | Tạo card, chart, table |

- Lớp ProfileUI:
Màn hình thông tin cá nhân và đăng xuất.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | Các hằng màu | `Color` | `static final` | Màu giao diện |
| 2 | User truyền vào constructor | `User` | NOT NULL | Dùng để render thông tin |
| 3 | `mainFrame` truyền vào constructor | `MainFrame` | NOT NULL | Điều hướng đăng xuất |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `ProfileUI` | `User user`, `MainFrame mainFrame` | Constructor | Tạo màn hình hồ sơ |
| 2 | `makeStatCard`, `makeInfoRow` | Chuỗi hiển thị | `JPanel` | Tạo card/row thông tin |
| 3 | `getInitials`, `goalLabel`, `bmi` | `User` / `String` | `String` / `double` | Tính nội dung hiển thị |

- Lớp AdminUI:
Màn hình làm việc của quản trị viên.

+ Bảng thuộc tính (attribute):

| STT | Tên thuộc tính | Kiểu dữ liệu | Ràng buộc | Ý nghĩa/Ghi chú |
|---|---|---|---|---|
| 1 | `mainFrame` | `MainFrame` | NOT NULL | Điều hướng |
| 2 | `controller` | `AdminController` | NOT NULL | Controller admin |
| 3 | `admin` | `Admin` | NOT NULL | Admin hiện tại |
| 4 | `library` | `ExerciseLibrary` | NOT NULL | Thư viện bài tập |
| 5 | `workoutCtrl` | `WorkoutLogController` | NOT NULL | Xem lịch sử tập user |
| 6 | `cardPanel`, `cardLayout`, `userListContainer`, `userDetailsContainer` | Swing components | NOT NULL sau init | Quản lý màn hình con |

+ Bảng phương thức (method):

| STT | Tên phương thức | Dữ liệu đầu vào | Dữ liệu đầu ra | Ghi chú/Ý nghĩa |
|---|---|---|---|---|
| 1 | `AdminUI` | `mainFrame`, `controller`, `admin`, `library`, `workoutCtrl` | Constructor | Khởi tạo admin UI |
| 2 | `refreshUserListPanel` | Không | `void` | Refresh danh sách user |
| 3 | `showUserDetails` | `User user` | `void` | Mở chi tiết user |
| 4 | `createUserListPanel`, `createAdminLibraryPanel`, `createWorkoutHistoryPanel` | Tùy hàm | `JPanel` | Tạo các panel chính |
| 5 | `createWorkoutLogRow`, `formatWorkoutResult` | `WorkoutLog log` | `JPanel` / `String` | Hiển thị lịch sử tập |
