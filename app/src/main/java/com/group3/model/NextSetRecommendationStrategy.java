package com.group3.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface NextSetRecommendationStrategy {
	int MIN_WEEKLY_LOGS = 3;
	int MIN_WEEKLY_DAYS = 2;

	default RecommendationResult calculateNextSet(WorkoutLog currentLog) {
		return calculateNextSet(currentLog, List.of());
	}

	RecommendationResult calculateNextSet(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs);

	default boolean hasWeeklyHistory(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
		if (weeklyLogs == null) {
			return false;
		}
		for (WorkoutLog log : weeklyLogs) {
			if (isWeeklyPreviousLog(currentLog, log)) {
				return true;
			}
		}
		return false;
	}

	default boolean hasEnoughWeeklyHistory(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
		if (!hasWeeklyHistory(currentLog, weeklyLogs)) {
			return false;
		}

		int weeklyLogCount = 0;
		Set<LocalDate> trainingDays = new HashSet<>();

		for (WorkoutLog log : weeklyLogs) {
			if (!isWeeklyPreviousLog(currentLog, log)) {
				continue;
			}
			weeklyLogCount++;
			trainingDays.add(log.getDate().toLocalDate());
		}

		return weeklyLogCount >= MIN_WEEKLY_LOGS || trainingDays.size() >= MIN_WEEKLY_DAYS;
	}

	default boolean isHighWeeklyLoad(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
		if (!hasEnoughWeeklyHistory(currentLog, weeklyLogs)) {
			return false;
		}

		int sameExerciseCount = 0;
		int sameCategoryCount = 0;
		Set<LocalDate> trainingDays = new HashSet<>();

		for (WorkoutLog log : weeklyLogs) {
			if (!isWeeklyPreviousLog(currentLog, log) || log.getExercise() == null) {
				continue;
			}
			trainingDays.add(log.getDate().toLocalDate());
			if (currentLog.getExercise() != null
					&& log.getExercise().getExerciseID() == currentLog.getExercise().getExerciseID()) {
				sameExerciseCount++;
			}
			if (currentLog.getExercise() != null && currentLog.getExercise().getCategory() != null
					&& currentLog.getExercise().getCategory().equals(log.getExercise().getCategory())) {
				sameCategoryCount++;
			}
		}

		return trainingDays.size() >= 5 || sameExerciseCount >= 6 || sameCategoryCount >= 10;
	}

	default boolean isWeeklyPreviousLog(WorkoutLog currentLog, WorkoutLog candidate) {
		if (!isPreviousLog(currentLog, candidate) || currentLog.getDate() == null || candidate.getDate() == null) {
			return false;
		}
		LocalDateTime start = currentLog.getDate().minusDays(7);
		return !candidate.getDate().isBefore(start) && !candidate.getDate().isAfter(currentLog.getDate());
	}

	default boolean isPreviousLog(WorkoutLog currentLog, WorkoutLog candidate) {
		if (currentLog == null || candidate == null) {
			return false;
		}
		if (candidate.getLogID() == currentLog.getLogID()) {
			return false;
		}
		if (currentLog.getDate() != null && candidate.getDate() != null && candidate.getDate().isAfter(currentLog.getDate())) {
			return false;
		}
		return true;
	}
}
