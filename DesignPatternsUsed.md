# Design Patterns Used

## MVC Architecture

Most screens follow MVC/MVP-style separation:
- View: Swing UI classes render data and handle user interaction.
- Controller/Presenter: coordinates user actions, business logic, and model updates.
- Model: stores domain data and notifies observers when relevant data changes.

Observer is used to update views after model/controller data changes. Other workflows communicate through controllers.

| Pattern | Role | Classes |
|---|---|---|
| Observer | Update related UI components after logs or exercise library data changes | Subject, Observer, WorkoutLogController, NutritionLogController, ExerciseLibrary, StatisticsUI, ManageLogUI, ExerciseLibraryUI |
| Strategy | Suggest exercises based on user's BMI/goal | ExerciseSuggestionStrategy, ThinStrategy, FitStrategy, FatStrategy, ExerciseSuggestionService |
| Strategy | Recommend the next workout set based on goal and current log data | NextSetRecommendationStrategy, MuscleGainStrategy, LoseFatStrategy, NoWeightStrategy, WorkoutHandling, RecommendationResult |
| Simple Factory + Builder | Create Exercise objects while applying default tracking logic and validation | ExerciseFactory, Exercise.ExerciseBuilder, Exercise |
| Builder | Build domain objects with optional fields and validation | WorkoutLog.WorkoutLogBuilder, NutritionLog.Builder, Exercise.ExerciseBuilder |
| Adapter | Convert OpenFoodFacts API responses into nutrition data used by the app | INutrition, OpenFoodFactsAdapter, OpenFoodFactsAPI, NutritionLog |
| Template Method | Define a common JSON load/save workflow and let subclasses provide default values | AJsonDatabase<T>, JsonUserDatabase, JsonExerciseDatabase, JsonAdminDatabase, JsonCategoryDatabase |
| Minimized Composite | Represent exercise category and subcategory tree for easier JSON storage | ExerciseCategory |