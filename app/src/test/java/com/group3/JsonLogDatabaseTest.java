package com.group3.model;

import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class JsonLogDatabaseTest {

    private static final String WORKOUT_FILE  = "workoutlogs.json";
    private static final String NUTRITION_FILE = "nutritionlogs.json";

    @AfterEach
    void cleanup() {
        new File(WORKOUT_FILE).delete();
        new File(NUTRITION_FILE).delete();
    }

    private WorkoutLog buildLog(int userID, int logID) {
        Exercise ex = mock(Exercise.class);
        when(ex.getExerciseName()).thenReturn("Deadlift");
        when(ex.getCategory()).thenReturn(ExerciseCategory.BACK);

        return new WorkoutLog.WorkoutLogBuilder()
                .setUserID(userID)
                .setLogID(logID)
                .setDate(LocalDateTime.of(2025, 6, 1, 7, 0))
                .setExercise(ex)
                .setWeight(120.0)
                .setReps(5)
                .build();
    }

    // =================================================================== //
    //  loadData – file chưa tồn tại
    // =================================================================== //
@Test
    void loadData_fileNotExist_returnsEmptyCollection() {
        JsonLogDatabase db = new JsonLogDatabase();
        LogCollection col = db.loadData();

        assertNotNull(col);
        assertTrue(col.getWorkoutLogs().isEmpty());
        assertTrue(col.getNutritionLogs().isEmpty());
    }

    // =================================================================== //
    //  saveData + loadData – round-trip
    // =================================================================== //
 @Test
    void saveAndLoad_singleWorkoutLog_roundTrip() {
        JsonLogDatabase db = new JsonLogDatabase();
        WorkoutLog log = buildLog(1, 1);

        LogCollection toSave = new LogCollection(List.of(log), List.of());
        assertTrue(db.saveData(toSave), "saveData phải trả về true");

        LogCollection loaded = db.loadData();
        assertEquals(1, loaded.getWorkoutLogs().size());
        WorkoutLog reloaded = loaded.getWorkoutLogs().get(0);

        assertEquals(1,     reloaded.getLogID());
        assertEquals(1,     reloaded.getUserID());
        assertEquals(120.0, reloaded.getWeight(), 0.001);
        assertEquals(5,     reloaded.getReps());
    }
 @Test
    void saveAndLoad_multipleWorkoutLogs_correctCount() {
        JsonLogDatabase db = new JsonLogDatabase();
        List<WorkoutLog> logs = List.of(buildLog(1,1), buildLog(1,2), buildLog(2,3));
        db.saveData(new LogCollection(logs, List.of()));

        assertEquals(3, db.loadData().getWorkoutLogs().size());
    }
  @Test
    void saveData_validData_returnsTrue() {
        JsonLogDatabase db = new JsonLogDatabase();
        assertTrue(db.saveData(new LogCollection(List.of(), List.of())));
    }
}
