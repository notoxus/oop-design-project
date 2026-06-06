# Design Patterns Used

This document summarizes the main design patterns used in the Gym Tracking App project. The goal is to describe each pattern by its role in the system instead of repeating every attribute and method from the class diagram.

## MVC/MVP Architecture

The project follows a simple MVC/MVP-oriented structure:

- View: receives user actions and displays data.
- Controller/Presenter: coordinates business logic, calls model/service classes, and updates data.
- Model: stores domain data, handles domain structures, and provides data to controllers.

Observer, Strategy, and Adapter are used as supporting patterns inside the MVC flow. Views do not directly manage persisted data; main workflows are routed through controllers, presenters, or services.

## Pattern List

| Pattern | Role in the project | Related classes |
|---|---|---|
| Observer | Refresh related screens automatically when logs or the exercise library change | `Subject`, `Observer`, `WorkoutLogController`, `NutritionLogController`, `ExerciseLibrary`, `StatisticsUI`, `ManageLogUI`, `ExerciseLibraryUI` |
| Strategy | Suggest exercises based on user BMI and workout goal | `ExerciseSuggestionStrategy`, `ThinStrategy`, `FitStrategy`, `FatStrategy`, `ExerciseSuggestionService` |
| Strategy | Recommend the next workout set based on goal, current log, and recent workout history | `NextSetRecommendationStrategy`, `MuscleGainStrategy`, `LoseFatStrategy`, `NoWeightStrategy`, `WorkoutHandling`, `RecommendationResult` |
| Simple Factory + Builder | Create `Exercise` objects through a factory while using a builder for required data and validation | `ExerciseFactory`, `Exercise`, `ExerciseBuilder` |
| Builder | Build domain objects with multiple fields, optional values, and validation | `ExerciseBuilder`, `WorkoutLogBuilder`, `NutritionLogBuilder` |
| Adapter | Convert OpenFoodFacts API responses into `NutritionLog` objects used by the system | `INutrition`, `OpenFoodFactsAdapter`, `OpenFoodFactsAPI`, `NutritionLog` |
| Template Method | Define a shared JSON load/save workflow while subclasses provide default values | `AJsonDatabase<T>`, `JsonUserDatabase`, `JsonExerciseDatabase`, `JsonAdminDatabase`, `JsonCategoryDatabase`, `JsonLogDatabase` |
| Minimized Composite | Represent exercise categories and subcategories as a tree for compact JSON storage | `ExerciseCategory` |

## Presentation Notes

- `MVC` should be presented as a compound pattern: View receives actions, Controller/Presenter coordinates workflows, and Model/Service classes process data. Observer supports automatic UI refresh when data changes.
- `Composite` is implemented as a minimized version for the `ExerciseCategory` tree. It is not a full textbook Composite with a separate component/leaf/composite hierarchy.
- `Simple Factory` is combined with `Builder`, so the diagram may differ from the base example. The factory still decides how to create an `Exercise`, while the builder collects and validates input before object creation.
- The next-set `Strategy` should emphasize that the current logic considers recent workout history, not only a single workout log.
