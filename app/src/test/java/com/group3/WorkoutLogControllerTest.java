package com.group3.controller;

import com.group3.model.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class WorkoutLogControllerTest {

    // ------------------------------------------------------------------ //
    //  Helper – tạo WorkoutLog hợp lệ
    // ------------------------------------------------------------------ //
    private WorkoutLog buildValidLog(int userID, int logID) {
        Exercise ex = mock(Exercise.class);
        when(ex.getExerciseName()).thenReturn("Bench Press");
        when(ex.getCategory()).thenReturn(ExerciseCategory.CHEST);

        return new WorkoutLog.WorkoutLogBuilder()
                .setUserID(userID)
                .setLogID(logID)
                .setDate(LocalDateTime.now())
                .setExercise(ex)
                .setWeight(80.0)
                .setReps(10)
                .build();
    }

    // =================================================================== //
    //  addWorkoutLog
    // =================================================================== //
@Test
    void addWorkoutLog_validLog_returnsTrue() {
        DataConnection<LogCollection> db = mock(DataConnection.class);
        LogCollection col = new LogCollection(new ArrayList<>(), new ArrayList<>());
        when(db.loadData()).thenReturn(col);
        when(db.saveData(any())).thenReturn(true);

        WorkoutLogController ctrl = new WorkoutLogController(db);
        assertTrue(ctrl.addWorkoutLog(buildValidLog(1, 1)));
    }
   @Test
    void addWorkoutLog_nullLog_returnsFalse() {
        DataConnection<LogCollection> db = mock(DataConnection.class);
        WorkoutLogController ctrl = new WorkoutLogController(db);
        assertFalse(ctrl.addWorkoutLog(null));
        verify(db, never()).loadData(); // không được chạm tới DB
    }
   @Test
    void addWorkoutLog_dbSaveFails_returnsFalse() {
        DataConnection<LogCollection> db = mock(DataConnection.class);
        LogCollection col = new LogCollection(new ArrayList<>(), new ArrayList<>());
        when(db.loadData()).thenReturn(col);
        when(db.saveData(any())).thenReturn(false);

        WorkoutLogController ctrl = new WorkoutLogController(db);
        assertFalse(ctrl.addWorkoutLog(buildValidLog(1, 1)));
    }
   @Test
    void addWorkoutLog_logIsAppendedToCollection() {
        DataConnection<LogCollection> db = mock(DataConnection.class);
        List<WorkoutLog> list = new ArrayList<>();
        LogCollection col = new LogCollection(list, new ArrayList<>());
        when(db.loadData()).thenReturn(col);
        when(db.saveData(any())).thenReturn(true);

        WorkoutLog newLog = buildValidLog(1, 42);
        WorkoutLogController ctrl = new WorkoutLogController(db);
        ctrl.addWorkoutLog(newLog);

        // Kiểm tra collection đã chứa log trước khi saveData được gọi
        ArgumentCaptor<LogCollection> captor = ArgumentCaptor.forClass(LogCollection.class);
        verify(db).saveData(captor.capture());
        assertTrue(captor.getValue().getWorkoutLogs().contains(newLog));
    }

    // =================================================================== //
    //  deleteWorkoutLog
    // =================================================================== //
 @Test
    void deleteWorkoutLog_existingID_returnsTrue() {
        WorkoutLog log = buildValidLog(1, 10);
        List<WorkoutLog> list = new ArrayList<>(List.of(log));
        LogCollection col = new LogCollection(list, new ArrayList<>());

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(col);
        when(db.saveData(any())).thenReturn(true);

        WorkoutLogController ctrl = new WorkoutLogController(db);
        assertTrue(ctrl.deleteWorkoutLog(10));
    }
   @Test
    void deleteWorkoutLog_nonExistingID_returnsFalse() {
        WorkoutLog log = buildValidLog(1, 10);
        List<WorkoutLog> list = new ArrayList<>(List.of(log));
        LogCollection col = new LogCollection(list, new ArrayList<>());

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(col);

        WorkoutLogController ctrl = new WorkoutLogController(db);
        assertFalse(ctrl.deleteWorkoutLog(999));
    }
 @Test
    void deleteWorkoutLog_removesFromCollection() {
        WorkoutLog log = buildValidLog(1, 10);
        List<WorkoutLog> list = new ArrayList<>(List.of(log));
        LogCollection col = new LogCollection(list, new ArrayList<>());

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(col);
        when(db.saveData(any())).thenReturn(true);

        WorkoutLogController ctrl = new WorkoutLogController(db);
        ctrl.deleteWorkoutLog(10);

        ArgumentCaptor<LogCollection> captor = ArgumentCaptor.forClass(LogCollection.class);
        verify(db).saveData(captor.capture());
        assertTrue(captor.getValue().getWorkoutLogs().isEmpty(),
                "Sau khi xóa, danh sách phải rỗng");
    }
   @Test
    void deleteWorkoutLog_correctLogRemovedAmongMultiple() {
        WorkoutLog log1 = buildValidLog(1, 1);
        WorkoutLog log2 = buildValidLog(1, 2);
        WorkoutLog log3 = buildValidLog(1, 3);
        List<WorkoutLog> list = new ArrayList<>(List.of(log1, log2, log3));
        LogCollection col = new LogCollection(list, new ArrayList<>());

        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(col);
        when(db.saveData(any())).thenReturn(true);

        WorkoutLogController ctrl = new WorkoutLogController(db);
        ctrl.deleteWorkoutLog(2);

        ArgumentCaptor<LogCollection> captor = ArgumentCaptor.forClass(LogCollection.class);
        verify(db).saveData(captor.capture());
        List<WorkoutLog> saved = captor.getValue().getWorkoutLogs();
        assertEquals(2, saved.size());
        assertTrue(saved.stream().noneMatch(l -> l.getLogID() == 2));
    }
  // =================================================================== //
    //  getAllLogs
    // =================================================================== //
 @Test
    void getAllLogs_emptyDB_returnsEmptyList() {
        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(new ArrayList<>(), new ArrayList<>()));

        WorkoutLogController ctrl = new WorkoutLogController(db);
        List<WorkoutLog> result = ctrl.getAllLogs();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
   @Test
    void getAllLogs_dbHasLogs_returnsAll() {
        List<WorkoutLog> list = List.of(
                buildValidLog(1, 1),
                buildValidLog(1, 2),
                buildValidLog(2, 3)
        );
        DataConnection<LogCollection> db = mock(DataConnection.class);
        when(db.loadData()).thenReturn(new LogCollection(new ArrayList<>(list), new ArrayList<>()));

        WorkoutLogController ctrl = new WorkoutLogController(db);
        assertEquals(3, ctrl.getAllLogs().size());
    }
}
