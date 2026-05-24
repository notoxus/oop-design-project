package com.group3.controller;

import com.group3.model.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class StatisticsPresenterTest {
   // ------------------------------------------------------------------ //
    //  Helpers – tạo stub Exercise
    // ------------------------------------------------------------------ //
    private Exercise makeExercise(String name, ExerciseCategory category) {
        Exercise ex = mock(Exercise.class);
        when(ex.getExerciseName()).thenReturn(name);
        when(ex.getCategory()).thenReturn(category);
        return ex;
    }

    // ------------------------------------------------------------------ //
    //  Helper – tạo WorkoutLog (builder pattern)
    // ------------------------------------------------------------------ //
    private WorkoutLog makeWorkoutLog(int userID, int logID,
                                     LocalDateTime date,
                                     Exercise exercise,
                                     Double weight, Integer reps,
                                     Double distance, Double time) {
        WorkoutLog.WorkoutLogBuilder builder = new WorkoutLog.WorkoutLogBuilder()
                .setUserID(userID)
                .setLogID(logID)
                .setDate(date)
                .setExercise(exercise);
        if (weight   != null) builder.setWeight(weight);
        if (reps     != null) builder.setReps(reps);
        if (distance != null) builder.setDistance(distance);
        if (time     != null) builder.setTime(time);
        return builder.build();
    }

    // ------------------------------------------------------------------ //
    //  Helper – tạo NutritionLog stub
    // ------------------------------------------------------------------ //
    private NutritionLog makeNutritionLog(int userID, LocalDateTime date,
                                          double energy, double protein,
                                          double fat, double carb, int qty) {
        NutritionLog log = mock(NutritionLog.class);
        when(log.getUserID()).thenReturn(userID);
        when(log.getAddTime()).thenReturn(date);
        when(log.getEnergy()).thenReturn(energy);
        when(log.getProtein()).thenReturn(protein);
        when(log.getFat()).thenReturn(fat);
        when(log.getCarbohydrates()).thenReturn(carb);
        when(log.getQuantity()).thenReturn(qty);
        return log;
    }

    // =================================================================== //
    //  getWorkoutVolumeChartData
    // =================================================================== //
 @Test
    void getWorkoutVolumeChartData_noLogsForUser_returnsEmpty() {
        DataConnection<LogCollection> db = mock(DataConnection.class);
        LogCollection col = new LogCollection(Collections.emptyList(), Collections.emptyList());
        when(db.loadData()).thenReturn(col);

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        Map<LocalDate, Map<ExerciseCategory, Double>> result =
                presenter.getWorkoutVolumeChartData(99);

        assertTrue(result.isEmpty(), "Không có log → map phải rỗng");
    }
 @Test
    void getWorkoutVolumeChartData_singleLog_correctVolume() {
        Exercise ex = makeExercise("Bench Press", ExerciseCategory.CHEST);
        LocalDateTime dt = LocalDateTime.of(2025, 6, 1, 8, 0);
        WorkoutLog log = makeWorkoutLog(1, 1, dt, ex, 60.0, 10, null, null);

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(log), List.of()));

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        Map<LocalDate, Map<ExerciseCategory, Double>> result =
                presenter.getWorkoutVolumeChartData(1);

        assertEquals(600.0, result.get(dt.toLocalDate()).get(ExerciseCategory.CHEST),
                "Volume phải bằng 60 × 10 = 600");
    }
    @Test
    void getWorkoutVolumeChartData_multipleLogsSameDayCategory_summedCorrectly() {
        Exercise ex = makeExercise("Incline Press", ExerciseCategory.CHEST);
        LocalDateTime dt = LocalDateTime.of(2025, 6, 1, 9, 0);

        WorkoutLog log1 = makeWorkoutLog(1, 1, dt, ex, 50.0, 8, null, null);
        WorkoutLog log2 = makeWorkoutLog(1, 2, dt, ex, 70.0, 5, null, null);

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(log1, log2), List.of()));

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        Map<LocalDate, Map<ExerciseCategory, Double>> result =
                presenter.getWorkoutVolumeChartData(1);

        assertEquals(750.0, result.get(dt.toLocalDate()).get(ExerciseCategory.CHEST),
                "Volume phải bằng (50×8) + (70×5) = 750");
    }
   @Test
    void getWorkoutVolumeChartData_cardioLog_notIncluded() {
        Exercise ex = makeExercise("Chạy bộ", ExerciseCategory.CARDIO);
        LocalDateTime dt = LocalDateTime.of(2025, 6, 1, 7, 0);
        WorkoutLog log = makeWorkoutLog(1, 1, dt, ex, null, null, 5.0, 30.0);

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(log), List.of()));

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        Map<LocalDate, Map<ExerciseCategory, Double>> result =
                presenter.getWorkoutVolumeChartData(1);

        assertTrue(result.isEmpty(), "Log cardio không có weight/reps không được tính volume");
    }
 @Test
    void getWorkoutVolumeChartData_filtersByUserID() {
        Exercise ex = makeExercise("Squat", ExerciseCategory.LEGS);
        LocalDateTime dt = LocalDateTime.of(2025, 6, 1, 10, 0);

        WorkoutLog log1 = makeWorkoutLog(1, 1, dt, ex, 100.0, 5, null, null);
        WorkoutLog log2 = makeWorkoutLog(2, 2, dt, ex, 80.0,  5, null, null);

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(log1, log2), List.of()));

        StatisticsPresenter presenter = new StatisticsPresenter(db);

        Map<LocalDate, Map<ExerciseCategory, Double>> result1 = presenter.getWorkoutVolumeChartData(1);
        Map<LocalDate, Map<ExerciseCategory, Double>> result2 = presenter.getWorkoutVolumeChartData(2);

        assertEquals(500.0, result1.get(dt.toLocalDate()).get(ExerciseCategory.LEGS));
        assertEquals(400.0, result2.get(dt.toLocalDate()).get(ExerciseCategory.LEGS));
    }

    // =================================================================== //
    //  getNutritionCaloriesChartData
    // =================================================================== //
 @Test
    void getNutritionCaloriesChartData_noLogs_returnsEmpty() {
        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(), List.of()));

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        assertTrue(presenter.getNutritionCaloriesChartData(1).isEmpty());
    }
  @Test
    void getNutritionCaloriesChartData_singleLog_correctCalories() {
        LocalDateTime dt = LocalDateTime.of(2025, 6, 1, 12, 0);
        NutritionLog log = makeNutritionLog(1, dt, 200.0, 10.0, 5.0, 30.0, 2);

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(), List.of(log)));

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        Map<LocalDate, Double> result = presenter.getNutritionCaloriesChartData(1);

        assertEquals(400.0, result.get(dt.toLocalDate()), 0.001);
    }
 @Test
    void getNutritionCaloriesChartData_multipleLogsSameDay_summed() {
        LocalDateTime dt = LocalDateTime.of(2025, 6, 1, 8, 0);
        NutritionLog log1 = makeNutritionLog(1, dt, 100.0, 5.0, 2.0, 10.0, 1);
        NutritionLog log2 = makeNutritionLog(1, dt, 150.0, 8.0, 3.0, 15.0, 1);

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(), List.of(log1, log2)));

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        assertEquals(250.0, presenter.getNutritionCaloriesChartData(1).get(dt.toLocalDate()), 0.001);
    }
 @Test
    void getNutritionCaloriesChartData_nullEnergy_skipped() {
        LocalDateTime dt = LocalDateTime.of(2025, 6, 1, 8, 0);
        NutritionLog log = mock(NutritionLog.class);
        when(log.getUserID()).thenReturn(1);
        when(log.getAddTime()).thenReturn(dt);
        when(log.getEnergy()).thenReturn(null);

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(List.of(), List.of(log)));

        StatisticsPresenter presenter = new StatisticsPresenter(db);
        assertTrue(presenter.getNutritionCaloriesChartData(1).isEmpty(),
                "Log có energy null không được tính");
    }
}
