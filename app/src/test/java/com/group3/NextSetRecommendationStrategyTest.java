package com.group3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.group3.model.Exercise;
import com.group3.model.ExerciseCategory;
import com.group3.model.LoseFatStrategy;
import com.group3.model.MuscleGainStrategy;
import com.group3.model.NoWeightStrategy;
import com.group3.model.RecommendationResult;
import com.group3.model.TrackingType;
import com.group3.model.WorkoutLog;

class NextSetRecommendationStrategyTest {
	private static final LocalDateTime NOW = LocalDateTime.of(2026, 6, 5, 17, 30);

	@Test
	void mixedLogsOnSameDayDoNotAutomaticallyCountAsHighWeeklyLoad() {
		Exercise currentExercise = exercise(1, "Bench Press", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, currentExercise, 50.0, 12);

		List<WorkoutLog> weeklyLogs = new ArrayList<>();
		weeklyLogs.add(currentLog);
		for (int i = 0; i < 6; i++) {
			weeklyLogs.add(workoutLog(200 + i, NOW.minusMinutes(i + 1),
					exercise(10 + i, "Exercise " + i, category(10 + i)), 20.0, 10));
		}

		RecommendationResult result = new MuscleGainStrategy().calculateNextSet(currentLog, weeklyLogs);

		assertEquals(52.5, result.getSuggestedWeight());
		assertEquals(8, result.getSuggestedReps());
	}

	@Test
	void oldHistoryOutsideCurrentWeekDoesNotBlockNormalProgression() {
		Exercise exercise = exercise(1, "Bench Press", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, exercise, 50.0, 12);
		List<WorkoutLog> historyLogs = new ArrayList<>();
		historyLogs.add(currentLog);
		for (int i = 0; i < 8; i++) {
			historyLogs.add(workoutLog(200 + i, NOW.minusDays(10 + i), exercise, 40.0, 10));
		}

		RecommendationResult result = new MuscleGainStrategy().calculateNextSet(currentLog, historyLogs);

		assertEquals(52.5, result.getSuggestedWeight());
		assertEquals(8, result.getSuggestedReps());
	}

	@Test
	void muscleGainMiddleRangeProgressesWhenWeeklyLoadIsNormal() {
		Exercise exercise = exercise(1, "Bench Press", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, exercise, 50.0, 10);

		RecommendationResult result = new MuscleGainStrategy().calculateNextSet(currentLog, List.of(currentLog));

		assertEquals(50.0, result.getSuggestedWeight());
		assertEquals(11, result.getSuggestedReps());
	}

	@Test
	void muscleGainMiddleRangeAdjustsWhenWeeklyLoadIsHigh() {
		Exercise exercise = exercise(1, "Bench Press", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, exercise, 50.0, 10);
		List<WorkoutLog> weeklyLogs = sameExerciseHistory(currentLog, exercise, 6);

		RecommendationResult result = new MuscleGainStrategy().calculateNextSet(currentLog, weeklyLogs);

		assertEquals(50.0, result.getSuggestedWeight());
		assertEquals(9, result.getSuggestedReps());
	}

	@Test
	void muscleGainHighWeeklyLoadKeepsActionableMessageWithoutWeeklyWarning() {
		Exercise exercise = exercise(1, "Bench Press", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, exercise, 50.0, 12);
		List<WorkoutLog> weeklyLogs = sameExerciseHistory(currentLog, exercise, 6);

		RecommendationResult result = new MuscleGainStrategy().calculateNextSet(currentLog, weeklyLogs);

		assertEquals(50.0, result.getSuggestedWeight());
		assertEquals(10, result.getSuggestedReps());
		assertFalse(result.getMessage().contains("Tu\u1ea7n"));
	}

	@Test
	void loseFatMiddleRangeProgressesWhenWeeklyLoadIsNormal() {
		Exercise exercise = exercise(1, "Goblet Squat", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, exercise, 30.0, 15);

		RecommendationResult result = new LoseFatStrategy().calculateNextSet(currentLog, List.of(currentLog));

		assertEquals(30.0, result.getSuggestedWeight());
		assertEquals(16, result.getSuggestedReps());
	}

	@Test
	void loseFatHighWeeklyLoadKeepsActionableMessageWithoutWeeklyWarning() {
		Exercise exercise = exercise(1, "Goblet Squat", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, exercise, 30.0, 20);
		List<WorkoutLog> weeklyLogs = sameExerciseHistory(currentLog, exercise, 6);

		RecommendationResult result = new LoseFatStrategy().calculateNextSet(currentLog, weeklyLogs);

		assertEquals(30.0, result.getSuggestedWeight());
		assertEquals(15, result.getSuggestedReps());
		assertFalse(result.getMessage().contains("Tu\u1ea7n"));
	}

	@Test
	void noWeightHighWeeklyLoadStillChangesRepsConservatively() {
		Exercise exercise = exercise(1, "Push Up", category(1));
		WorkoutLog currentLog = workoutLog(100, NOW, exercise, null, 15);
		List<WorkoutLog> weeklyLogs = sameExerciseHistory(currentLog, exercise, 6);

		RecommendationResult result = new NoWeightStrategy().calculateNextSet(currentLog, weeklyLogs);

		assertEquals(16, result.getSuggestedReps());
	}

	private static List<WorkoutLog> sameExerciseHistory(WorkoutLog currentLog, Exercise exercise, int count) {
		List<WorkoutLog> weeklyLogs = new ArrayList<>();
		weeklyLogs.add(currentLog);
		for (int i = 0; i < count; i++) {
			weeklyLogs.add(workoutLog(200 + i, NOW.minusDays(i + 1), exercise, 40.0, 10));
		}
		return weeklyLogs;
	}

	private static WorkoutLog workoutLog(int logID, LocalDateTime date, Exercise exercise, Double weight, Integer reps) {
		return new WorkoutLog.WorkoutLogBuilder()
				.setUserID(1)
				.setLogID(logID)
				.setDate(date)
				.setExercise(exercise)
				.setWeight(weight)
				.setReps(reps)
				.build();
	}

	private static Exercise exercise(int id, String name, ExerciseCategory category) {
		return new Exercise.ExerciseBuilder()
				.setExerciseID(id)
				.setExerciseName(name)
				.setCategory(category)
				.setTrackingType(TrackingType.WEIGHT_REP)
				.setTargetMuscle("Test")
				.build();
	}

	private static ExerciseCategory category(int id) {
		return new ExerciseCategory(id, "Category " + id, List.of(TrackingType.WEIGHT_REP));
	}
}
