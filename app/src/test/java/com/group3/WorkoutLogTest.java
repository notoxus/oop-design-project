package com.group3.model;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkoutLogTest {

    private Exercise mockExercise;

    @BeforeEach
    void setUp() {
        mockExercise = mock(Exercise.class);
        when(mockExercise.getExerciseName()).thenReturn("Squat");
    }

    // =================================================================== //
    //  WorkoutLogBuilder – validation
    // =================================================================== //
 @Test
    void builder_allRequiredFields_buildsSuccessfully() {
        WorkoutLog log = new WorkoutLog.WorkoutLogBuilder()
                .setUserID(1)
                .setLogID(1)
                .setDate(LocalDateTime.now())
                .setExercise(mockExercise)
                .build();
        assertNotNull(log);
    }
  @Test
    void builder_missingLogID_throwsException() {
        assertThrows(IllegalStateException.class, () ->
            new WorkoutLog.WorkoutLogBuilder()
                    .setUserID(1)
                    .setDate(LocalDateTime.now())
                    .setExercise(mockExercise)
                    .build()
        );
    }
   @Test
    void builder_missingDate_throwsException() {
        assertThrows(IllegalStateException.class, () ->
            new WorkoutLog.WorkoutLogBuilder()
                    .setUserID(1)
                    .setLogID(1)
                    .setExercise(mockExercise)
                    .build()
        );
    }
   @Test
    void builder_missingExercise_throwsException() {
        assertThrows(IllegalStateException.class, () ->
            new WorkoutLog.WorkoutLogBuilder()
                    .setUserID(1)
                    .setLogID(1)
                    .setDate(LocalDateTime.now())
                    .build()
        );
    }
    @Test
    void builder_optionalFieldsDefaultNull() {
        WorkoutLog log = new WorkoutLog.WorkoutLogBuilder()
                .setUserID(1)
                .setLogID(1)
                .setDate(LocalDateTime.now())
                .setExercise(mockExercise)
                .build();
        assertNull(log.getWeight());
        assertNull(log.getReps());
        assertNull(log.getDistance());
        assertNull(log.getTime());
    }

    // =================================================================== //
    //  paceCal
    // =================================================================== //
 @Test
    void paceCal_correctResult() {
        WorkoutLog log = new WorkoutLog.WorkoutLogBuilder()
                .setUserID(1)
                .setLogID(1)
                .setDate(LocalDateTime.now())
                .setExercise(mockExercise)
                .setDistance(5.0)
                .setTime(30.0)
                .build();
        assertEquals(6.0, log.paceCal(), 0.001);
    }
   @Test
    void paceCal_zeroDistance_returnsInfinity() {
        WorkoutLog log = new WorkoutLog.WorkoutLogBuilder()
                .setUserID(1)
                .setLogID(1)
                .setDate(LocalDateTime.now())
                .setExercise(mockExercise)
                .setDistance(0.0)
                .setTime(30.0)
                .build();
        assertTrue(Double.isInfinite(log.paceCal()),
                "Chia cho 0 (double) phải trả về Infinity");
    }
}
