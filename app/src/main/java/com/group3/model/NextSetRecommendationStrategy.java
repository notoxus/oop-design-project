package com.group3.model;

import java.util.List;

public interface NextSetRecommendationStrategy {
	default RecommendationResult calculateNextSet(WorkoutLog currentLog) {
		return calculateNextSet(currentLog, List.of());
	}

	RecommendationResult calculateNextSet(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs);

	default boolean hasWeeklyHistory(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
		if (currentLog == null || weeklyLogs == null) {
			return false;
		}
		for (WorkoutLog log : weeklyLogs) {
			if (log != null && log.getLogID() != currentLog.getLogID()) {
				return true;
			}
		}
		return false;
	}

	default boolean isHighWeeklyLoad(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
		if (!hasWeeklyHistory(currentLog, weeklyLogs)) {
			return false;
		}

		int totalLogs = 0;
		int sameExerciseCount = 0;
		int sameCategoryCount = 0;

		for (WorkoutLog log : weeklyLogs) {
			if (log == null || log.getExercise() == null) {
				continue;
			}
			totalLogs++;
			if (currentLog.getExercise() != null
					&& log.getExercise().getExerciseID() == currentLog.getExercise().getExerciseID()) {
				sameExerciseCount++;
			}
			if (currentLog.getExercise() != null && currentLog.getExercise().getCategory() != null
					&& currentLog.getExercise().getCategory().equals(log.getExercise().getCategory())) {
				sameCategoryCount++;
			}
		}

		return totalLogs >= 6 || sameExerciseCount >= 3 || sameCategoryCount >= 4;
	}
}
