# UI Screens and Event Handling

### Screen List

| No. | Screen | Description |
|---|---|---|
| 1 | `MainFrame` | Main window that manages navigation between panels |
| 2 | `DashboardUI` | User home screen |
| 3 | `AdminUI` | Admin screen |
| 4 | `ExerciseLibraryUI` | Reusable exercise library panel embedded in the user and admin screens |
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

The user home screen opens with the exercise library. It allows users to filter exercises, view suggested exercises, select an exercise for logging, and navigate to nutrition, logs, statistics, or profile screens.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `filterButtons` | ToggleButton/Chip | | Filter exercises by category |
| 2 | `btnSuggest` | Button | | Enable or disable suggested exercises |
| 3 | `cardsContainer` | Panel/List | ReadOnly | Display exercise cards |
| 4 | `exerciseCard` | Card/Panel | | Display exercise name, target muscle, and tracking type |
| 5 | `btnNutrition` | Button/Navigation | | Navigate to nutrition lookup |
| 6 | `btnLogs` | Button/Navigation | | Navigate to log management |
| 7 | `btnStats` | Button/Navigation | | Navigate to statistics overview |
| 8 | `btnProfile` | Button/Navigation | | Navigate to the user profile |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `filterButton_Click` | Update the active category and re-render the exercise list |
| 2 | `btnSuggest_Click` | Call `ExerciseSuggestionService.suggest(user, library)` to get suggested exercises |
| 3 | `exerciseCard_Click` | Move to the workout logging screen with the selected exercise |
| 4 | `btnNutrition_Click` | Call `switchTo("NUTRITION", btnNutrition)` to display `NutritionUI` |
| 5 | `btnLogs_Click` | Call `switchTo("LOGS", btnLogs)` to display `ManageLogUI` |
| 6 | `btnStats_Click` | Call `switchTo("STATS", btnStats)` to display `StatisticsUI` |
| 7 | `btnProfile_Click` | Call `switchTo("PROFILE", btnProfile)` to display `ProfileUI` |
| 8 | `library_Update` | Refresh the exercise list when `ExerciseLibrary.notifyObservers()` is called |

## Exercise Library Screen

### Interface

The exercise library panel is a reusable component. It is embedded in the user home screen for browsing/selecting exercises and in the admin screen for exercise management.

### UI Object Description

| No. | Name | Type | Constraint | Purpose |
|---|---|---|---|---|
| 1 | `filterButtons` | ToggleButton/Chip | | Filter exercises by category |
| 2 | `btnSuggest` | Button | | Enable or disable suggested exercises |
| 3 | `cardsContainer` | Panel/List | ReadOnly | Display exercise cards |
| 4 | `exerciseCard` | Card/Panel | | Display exercise name, target muscle, and icon |
| 5 | `currentAccount` | IAccount | NOT NULL | Determine whether the panel is rendered for a user or an admin |
| 6 | `dashboardUI` | DashboardUI | NULL for admin | Navigate user selection to workout logging |
| 7 | `adminController` | AdminController | NULL for user | Handle admin exercise management actions |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `filterButton_Click` | Update `activeFilter` and re-render the exercise list |
| 2 | `btnSuggest_Click` | Call `ExerciseSuggestionService.suggest(user, library)` to get suggested exercises |
| 3 | `exerciseCard_Click` | For users, call `DashboardUI.navigateToExerciseInput(ex)` |
| 4 | `renderForUser` | Show suggestion and selection controls for normal users |
| 5 | `renderForAdmin` | Show admin management controls when the current account is an admin |
| 6 | `library_Update` | Refresh the panel after `ExerciseLibrary.notifyObservers()` |

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
| 10 | `btnAddExercise` | Button | Admin only | Open the add-exercise dialog |
| 11 | `btnEditExercise` | Button | Admin only | Open the edit-exercise dialog |
| 12 | `btnDeleteExercise` | Button | Admin only | Delete an exercise |
| 13 | `txtName` | TextField | NOT NULL | Enter exercise name in the dialog |
| 14 | `txtMuscle` | TextField | NULL | Enter target muscle |
| 15 | `cbParentCategory` | ComboBox | NOT NULL | Select parent category |
| 16 | `cbSubCategory` | ComboBox | NOT NULL | Select subcategory |
| 17 | `cbTracking` | ComboBox | NOT NULL | Select tracking type |
| 18 | `btnSave` | Button | | Save added or updated exercise |
| 19 | `btnCancel` | Button | | Close the add/edit dialog |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `btnLibrary_Click` | Call `cardLayout.show(cardPanel, "LIBRARY")` |
| 2 | `btnUsers_Click` | Call `refreshUserListPanel()` and display the user list |
| 3 | `userCard_Click` | Call `showUserDetails(user)` |
| 4 | `btnBack_Click` | Return to the user list |
| 5 | `btnLogout_Click` | Ask for confirmation, then call `MainFrame.showLoginScreen()` |
| 6 | `refreshUserListPanel` | Call `AdminController.viewUserDetails()` |
| 7 | `btnAddExercise_Click` | Open the add-exercise dialog |
| 8 | `cbParentCategory_Change` | Refresh the matching subcategory list |
| 9 | `cbSubCategory_Change` | Refresh the valid tracking type list |
| 10 | `btnSaveAdd_Click` | Validate input and call `AdminController.addExercise()` |
| 11 | `btnEditExercise_Click` | Open the edit dialog for the selected exercise |
| 12 | `btnSaveEdit_Click` | Validate input and call `AdminController.updateExercise()` |
| 13 | `btnDeleteExercise_Click` | Ask for confirmation and call `AdminController.deleteExercise(exerciseName)` |
| 14 | `library_Update` | Refresh the admin library panel after `ExerciseLibrary.notifyObservers()` |

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

The log management screen lets users view saved workout logs and nutrition logs, switch between log types, and delete a selected log after confirmation.

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
| 10 | `emptyTableState` | Table state | ReadOnly | Display an empty list when the user has no logs |

### Event List and Handling

| No. | Event | Handling |
|---|---|---|
| 1 | `tabWorkout_Click` | Show the workout log panel and update tab style |
| 2 | `tabNutrition_Click` | Show the nutrition log panel and update tab style |
| 3 | `loadWorkoutData` | Load logs from `WorkoutLogController.getAllLogs()` and filter by current user |
| 4 | `loadNutritionData` | Load logs from `NutritionLogController.getAllLogs()` and filter by current user |
| 5 | `btnDeleteWorkoutLog_Click` | Ask for confirmation and call `WorkoutLogController.removeWorkoutLog(logID)` |
| 6 | `btnDeleteNutritionLog_Click` | Ask for confirmation and call `NutritionLogController.removeNutritionLog(logID)` |
| 7 | `deleteLog_NoSelection` | Show a message asking the user to select a log before deletion |
| 8 | `deleteLog_Cancel` | Close the confirmation dialog and keep the data unchanged |
| 9 | `deleteLog_Success` | Save data, call `notifyObservers()`, and refresh the displayed list |
| 10 | `deleteLog_Failed` | Show an error message and keep the current list unchanged |
| 11 | `noLog_State` | Display an empty table when the current user has no logs |
| 12 | `observer_Update` | Reload workout and nutrition lists when `ManageLogUI.update()` is called |

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
