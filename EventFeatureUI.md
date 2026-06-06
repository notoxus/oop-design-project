# UI Screens and Event Handling

### Screen List

| No. | Screen | Description |
|---|---|---|
| 1 | `MainFrame` | Main window that manages navigation between panels |
| 2 | `DashboardUI` | User home screen |
| 3 | `AdminUI` | Admin screen |
| 4 | `ExerciseLibraryUI` | Exercise library screen |
| 5 | `ExerciseUI` | Workout logging screen |
| 6 | `LoginForm` | Login screen |
| 7 | `RegisterForm` | Registration screen |
| 8 | `ManageLogUI` | Log management screen |
| 9 | `NutritionUI` | Nutrition lookup screen |
| 10 | `ProfileUI` | User profile screen |
| 11 | `StatisticsUI` | Statistics overview screen |

## Login Screen

### Interface

The login screen allows users to enter their username and password. It also provides navigation to the registration screen for users who do not have an account.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `txtUsername` | TextField | NOT NULL | Enter username |
| 2 | `txtPassword` | PasswordField | NOT NULL | Enter password |
| 3 | `btnLogin` | Button | | Submit login request |
| 4 | `btnRegister` | Button/Link | | Navigate to the registration screen |
| 5 | `messageDialog` | Dialog | | Display login result or validation errors |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `btnLogin_Click` | Read username/password, call `LoginManager.login(username, password)`, and open the matching screen if the account is valid |
| 2 | `txtPassword_Enter` | Trigger the login flow when the user presses Enter in the password field |
| 3 | `btnRegister_Click` | Call `MainFrame.showRegisterScreen()` |
| 4 | `login_InvalidInput` | Show an error dialog if fields are empty or credentials are invalid |
| 5 | `login_Success` | Open `DashboardUI` for a normal user or `AdminUI` for an admin |

## Registration Screen

### Interface

The registration screen allows a new user to create an account by entering personal information, body metrics, and a workout goal.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `txtName` | TextField | NOT NULL | Enter full name |
| 2 | `txtUsername` | TextField | NOT NULL, UNIQUE | Enter username |
| 3 | `txtPassword` | PasswordField | NOT NULL | Enter password |
| 4 | `txtAge` | TextField | NOT NULL, Number | Enter age |
| 5 | `genderGroup` | RadioButton Group | NOT NULL | Select gender |
| 6 | `txtHeight` | TextField | NOT NULL, Number | Enter height |
| 7 | `txtWeight` | TextField | NOT NULL, Number | Enter weight |
| 8 | `cbGoal` | ComboBox | NOT NULL | Select workout goal |
| 9 | `btnRegister` | Button | | Submit registration data |
| 10 | `btnBack` / `btnCancel` | Button | | Return to the login screen |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `btnRegister_Click` | Read form data and validate required fields and numeric values |
| 2 | `register_ValidInput` | Create `User` and call `RegisterManager.register(newUser)` |
| 3 | `register_DuplicateUsername` | Show an error dialog if the username already exists |
| 4 | `register_InvalidNumber` | Show an error dialog if age, height, or weight has an invalid format |
| 5 | `register_Success` | Save the new account and move to the login flow |
| 6 | `btnBack_Click` | Call `MainFrame.showLoginScreen()` |

## User Home Screen

### Interface

The user home screen is the main navigation area after login. It contains access to the exercise library, nutrition lookup, log management, statistics, and profile screens.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `mainFrame` | MainFrame | NOT NULL | Manage screen navigation |
| 2 | `cardPanel` | JPanel/CardLayout | NOT NULL | Hold feature panels |
| 3 | `btnLibrary` | Button/Navigation | | Open the exercise library |
| 4 | `btnNutrition` | Button/Navigation | | Open nutrition lookup |
| 5 | `btnLogs` | Button/Navigation | | Open log management |
| 6 | `btnStats` | Button/Navigation | | Open statistics overview |
| 7 | `btnProfile` | Button/Navigation | | Open the user profile |
| 8 | `libraryUI` | Panel | | Display the exercise library |
| 9 | `exerciseUI` | Panel | | Display the workout logging form |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `btnLibrary_Click` | Call `switchTo("LIBRARY", btnLibrary)` to display `ExerciseLibraryUI` |
| 2 | `btnNutrition_Click` | Call `switchTo("NUTRITION", btnNutrition)` to display `NutritionUI` |
| 3 | `btnLogs_Click` | Call `switchTo("LOGS", btnLogs)` to display `ManageLogUI` |
| 4 | `btnStats_Click` | Call `switchTo("STATS", btnStats)` to display `StatisticsUI` |
| 5 | `btnProfile_Click` | Call `switchTo("PROFILE", btnProfile)` to display `ProfileUI` |
| 6 | `navigateToExerciseInput` | Receive the selected exercise, call `ExerciseUI.setSelectedExercise(ex)`, and move to the workout logging screen |
| 7 | `showLibrary` | Return to the exercise library screen |

## Exercise Library Screen

### Interface

The exercise library screen displays exercises by category and lets users choose an exercise for logging. For admins, it also supports adding, updating, and deleting exercises.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `filterButtons` | ToggleButton/Chip | | Filter exercises by category |
| 2 | `btnSuggest` | Button | | Enable or disable suggested exercises |
| 3 | `cardsContainer` | Panel/List | ReadOnly | Display exercise cards |
| 4 | `exerciseCard` | Card/Panel | | Display exercise name, target muscle, and icon |
| 5 | `btnAddExercise` | Button | Admin only | Open the add-exercise dialog |
| 6 | `btnEditExercise` | Button | Admin only | Open the edit-exercise dialog |
| 7 | `btnDeleteExercise` | Button | Admin only | Delete an exercise |
| 8 | `txtName` | TextField | NOT NULL | Enter exercise name in the dialog |
| 9 | `txtMuscle` | TextField | NULL | Enter target muscle |
| 10 | `cbParentCategory` | ComboBox | NOT NULL | Select parent category |
| 11 | `cbSubCategory` | ComboBox | NOT NULL | Select subcategory |
| 12 | `cbTracking` | ComboBox | NOT NULL | Select tracking type |
| 13 | `btnSave` | Button | | Save added or updated exercise |
| 14 | `btnCancel` | Button | | Close the add/edit dialog |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `filterButton_Click` | Update `activeFilter` and re-render the exercise list |
| 2 | `btnSuggest_Click` | Call `ExerciseSuggestionService.suggest(user, library)` to get suggested exercises |
| 3 | `exerciseCard_Click` | For normal users, open the workout logging screen for the selected exercise |
| 4 | `btnAddExercise_Click` | For admins, open the add-exercise dialog |
| 5 | `cbParentCategory_Change` | Refresh the matching subcategory list |
| 6 | `cbSubCategory_Change` | Refresh the valid tracking type list |
| 7 | `btnSaveAdd_Click` | Validate input and call `AdminController.addExercise()` |
| 8 | `btnEditExercise_Click` | Open the edit dialog for the selected exercise |
| 9 | `btnSaveEdit_Click` | Validate input and call `AdminController.updateExercise()` |
| 10 | `btnDeleteExercise_Click` | Ask for confirmation and call `AdminController.deleteExercise(exerciseName)` |
| 11 | `library_Update` | Refresh the UI after `ExerciseLibrary.notifyObservers()` |

## Admin Screen

### Interface

The admin screen lets administrators switch between exercise library management and user detail management.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `btnLibrary` | Button/Navigation | Admin only | Open exercise library management |
| 2 | `btnUsers` | Button/Navigation | Admin only | Open the user list |
| 3 | `btnLogout` | Button | Admin only | Sign out from the admin account |
| 4 | `cardPanel` | JPanel/CardLayout | NOT NULL | Hold admin sub-screens |
| 5 | `libraryPanel` | Panel | | Display the exercise library for admin management |
| 6 | `userListPanel` | Panel/List | ReadOnly | Display the user list |
| 7 | `userCard` | Card/Panel | | Display a user summary |
| 8 | `userDetailPanel` | Panel | ReadOnly | Display selected user details |
| 9 | `btnBack` | Button | | Return to the user list |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `btnLibrary_Click` | Call `cardLayout.show(cardPanel, "LIBRARY")` |
| 2 | `btnUsers_Click` | Call `refreshUserListPanel()` and display the user list |
| 3 | `userCard_Click` | Call `showUserDetails(user)` |
| 4 | `btnBack_Click` | Return to the user list |
| 5 | `btnLogout_Click` | Ask for confirmation, then call `MainFrame.showLoginScreen()` |
| 6 | `refreshUserListPanel` | Call `AdminController.viewUserDetails()` |

## Workout Logging Screen

### Interface

The workout logging screen lets users enter set data based on the selected exercise tracking type. It can also show automatic next-set recommendations.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `exerciseCard` | Card/Panel | NOT NULL | Display the selected exercise |
| 2 | `lblExName` | Label | ReadOnly | Display exercise name |
| 3 | `lblExTarget` | Label | ReadOnly | Display target muscle and category |
| 4 | `txtWeight` | TextField | Number, depends on tracking type | Enter weight |
| 5 | `txtReps` | TextField | Number, depends on tracking type | Enter repetitions |
| 6 | `txtDistance` | TextField | Number, depends on tracking type | Enter distance |
| 7 | `txtTime` | TextField | Number, depends on tracking type | Enter time |
| 8 | `lblToggleHint` | Link/Label | | Enable or disable next-set suggestion |
| 9 | `lblHintMsg` | Label | | Display recommendation message |
| 10 | `btnSave` | Button | | Save workout log |
| 11 | `btnBack` | Button | | Return to the exercise library |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `setSelectedExercise` | Set the current exercise and display inputs that match `TrackingType` |
| 2 | `lblToggleHint_Click` | Toggle suggestion mode and call `updateHints()` |
| 3 | `updateHints` | Read recent logs and call `WorkoutHandling.calculateNextSet()` |
| 4 | `btnSave_Click` | Validate input, create `WorkoutLog` with Builder, and call `WorkoutLogController.addWorkoutLog()` |
| 5 | `saveWorkoutLog_Success` | Save the log, clear input fields, and refresh the next-set suggestion |
| 6 | `saveWorkoutLog_InvalidInput` | Show an error dialog if required values are missing or invalid |
| 7 | `btnBack_Click` | Call `DashboardUI.showLibrary()` |
| 8 | `workoutLog_NotifyObservers` | Refresh related screens through `WorkoutLogController.notifyObservers()` |

## Log Management Screen

### Interface

The log management screen lets users view and delete saved workout logs or nutrition logs.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `tabWorkout` | Button/Tab | | Switch to workout logs |
| 2 | `tabNutrition` | Button/Tab | | Switch to nutrition logs |
| 3 | `workoutLogPanel` | Panel/List | ReadOnly | Display workout logs for the current user |
| 4 | `nutritionLogPanel` | Panel/List | ReadOnly | Display nutrition logs for the current user |
| 5 | `workoutLogCard` | Card/Panel | ReadOnly | Display one workout log |
| 6 | `nutritionLogCard` | Card/Panel | ReadOnly | Display one nutrition log |
| 7 | `btnDeleteWorkoutLog` | Button | Requires selected log | Delete workout log |
| 8 | `btnDeleteNutritionLog` | Button | Requires selected log | Delete nutrition log |
| 9 | `confirmDialog` | Dialog | | Confirm deletion |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `tabWorkout_Click` | Show the workout log panel and update tab style |
| 2 | `tabNutrition_Click` | Show the nutrition log panel and update tab style |
| 3 | `loadWorkoutData` | Load logs from `WorkoutLogController.getAllLogs()` and filter by current user |
| 4 | `loadNutritionData` | Load logs from `NutritionLogController.getAllLogs()` and filter by current user |
| 5 | `btnDeleteWorkoutLog_Click` | Ask for confirmation and call `WorkoutLogController.removeWorkoutLog(logID)` |
| 6 | `btnDeleteNutritionLog_Click` | Ask for confirmation and call `NutritionLogController.removeNutritionLog(logID)` |
| 7 | `deleteLog_Success` | Save data and call `notifyObservers()` after deletion |
| 8 | `observer_Update` | Reload the list when `ManageLogUI.update()` is called |

## Statistics Overview Screen

### Interface

The statistics overview screen displays workout data, nutrition data, recent logs, and the current workout goal.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `lblTotalVolume` | Label/StatBox | ReadOnly | Display total workout volume |
| 2 | `lblTotalCalo` | Label/StatBox | ReadOnly | Display total consumed calories |
| 3 | `workoutChart` | Chart/Panel | ReadOnly | Display workout statistics |
| 4 | `nutritionChart` | Chart/Panel | ReadOnly | Display nutrition statistics |
| 5 | `tblRecentWorkout` | Table | ReadOnly | Display recent workout logs |
| 6 | `tblRecentNutrition` | Table | ReadOnly | Display recent nutrition logs |
| 7 | `cbGoal` | ComboBox | NOT NULL | Select current workout goal |
| 8 | `btnUpdateGoal` | Button | | Save goal changes |
| 9 | `btnRefresh` | Button | | Refresh statistics data |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `refreshData` | Call `StatisticsPresenter` to get total volume, total calories, and recent logs |
| 2 | `btnRefresh_Click` | Reload all statistics data |
| 3 | `btnUpdateGoal_Click` | Update the user goal through `StatisticsPresenter.updateGoal(user)` |
| 4 | `updateGoal_Success` | Call `WorkoutHandling.setGoal(user)` to update the next-set strategy |
| 5 | `observer_Update` | Refresh statistics when workout or nutrition logs change |
| 6 | `noData_State` | Display default values or empty tables when no logs are available |

## Nutrition Lookup Screen

### Interface

The nutrition lookup screen lets users search for food products, review kcal/protein/carb/fat values, and add the selected result to the nutrition log.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `txtSearchFood` | TextField | NOT NULL | Enter food or product name |
| 2 | `btnSearch` | Button | | Submit nutrition lookup |
| 3 | `resultTable` | Table | ReadOnly | Display API search results |
| 4 | `tableModel` | TableModel | | Store result table data |
| 5 | `btnAddFood` | Button | Requires selected row | Add selected product to the nutrition log |
| 6 | `loadingState` | Label/Dialog | | Display searching or empty-result state |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `btnSearch_Click` | Call `doSearch()` |
| 2 | `txtSearchFood_Enter` | Start lookup when the user presses Enter |
| 3 | `doSearch` | Call `NutritionLogController.lookupNutrition(keyword)` |
| 4 | `lookupNutrition` | Call `INutrition.getNutritionInfo()` through `OpenFoodFactsAdapter` |
| 5 | `apiSearch_Done` | Fill `resultTable` with API results or show an empty-result message |
| 6 | `btnAddFood_Click` | Read selected row, create `NutritionLog` with Builder, and call `addNutritionLog()` |
| 7 | `addNutritionLog_Success` | Save the nutrition log and refresh observer screens |
| 8 | `addNutritionLog_NoSelection` | Show a message asking the user to select a row |

## User Profile Screen

### Interface

The user profile screen displays basic user information, body metrics, current workout goal, and the sign-out action.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `lblAvatar` | Label/Icon | ReadOnly | Display user initials or avatar text |
| 2 | `lblName` | Label | ReadOnly | Display full name |
| 3 | `lblSub` | Label | ReadOnly | Display username and goal |
| 4 | `profileStatCards` | Card/Panel | ReadOnly | Display age, height, weight, and BMI |
| 5 | `infoRows` | Label/Row | ReadOnly | Display detailed profile fields |
| 6 | `btnLogout` | Button | | Sign out |
| 7 | `confirmDialog` | Dialog | | Confirm sign-out |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `btnLogout_Click` | Show sign-out confirmation dialog |
| 2 | `logout_Confirm` | Call `MainFrame.showLoginScreen()` |
| 3 | `btnLogout_MouseEnter` | Change logout button color on hover |
| 4 | `btnLogout_MouseExit` | Restore logout button color after hover |
| 5 | `profile_Load` | Read current `User` data and display it in labels/cards |
