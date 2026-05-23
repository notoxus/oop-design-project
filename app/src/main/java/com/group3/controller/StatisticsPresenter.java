package com.group3.controller;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.group3.model.DataConnection;
import com.group3.model.ExerciseCategory;
import com.group3.model.JsonUserDatabase;
import com.group3.model.LogCollection;
import com.group3.model.NutritionLog;
import com.group3.model.User;
import com.group3.model.WorkoutLog;

public class StatisticsPresenter {
	private DataConnection<LogCollection> database;

	public StatisticsPresenter(DataConnection<LogCollection> database) {
		this.database = database;
	}

	public double[] dailyNutritionSummary(LocalDate targetDate, int userID) {
		double totalKcal = 0, totalProtein = 0, totalFat = 0, totalCarb = 0;
		LogCollection data = database.loadData();
		List<NutritionLog> nutritionLogs = data.getNutritionLogs();

		for (NutritionLog log : nutritionLogs) {
			if (log.getUserID() == userID && log.getAddTime().toLocalDate().equals(targetDate)) {
				int qty = log.getQuantity();
				if (log.getEnergy() != null)
					totalKcal += log.getEnergy() * qty;
				if (log.getProtein() != null)
					totalProtein += log.getProtein() * qty;
				if (log.getFat() != null)
					totalFat += log.getFat() * qty;
				if (log.getCarbohydrates() != null)
					totalCarb += log.getCarbohydrates() * qty;
			}
		}
		return new double[] { totalKcal, totalProtein, totalFat, totalCarb };
	}

	public double[] dailyWorkoutSummary(LocalDate targetDate, int userID) {
		double totalVolume = 0, totalDistance = 0;
		LogCollection data = database.loadData();
		List<WorkoutLog> workoutLogs = data.getWorkoutLogs();
		for (WorkoutLog log : workoutLogs) {
			if (log.getUserID() == userID && log.getDate().toLocalDate().equals(targetDate)) {
				if (log.getWeight() != null && log.getReps() != null) {
					totalVolume += (log.getWeight() * log.getReps());
				}
				if (log.getDistance() != null) {
					totalDistance += log.getDistance();
				}
			}
		}
		return new double[] { totalVolume, totalDistance };
	}

	public Map<LocalDate, Map<ExerciseCategory, Double>> getWorkoutVolumeChartData(int userID) {
	    LogCollection data = database.loadData();
	    Map<LocalDate, Map<ExerciseCategory, Double>> chartData = new TreeMap<>();

	    for (WorkoutLog log : data.getWorkoutLogs()) {
	        if (log.getUserID() != userID) continue;
	        LocalDate date = log.getDate().toLocalDate();
	        ExerciseCategory category = log.getExercise().getCategory();
	        double volume;
	        if (log.getWeight() != null && log.getReps() != null) {
	            volume = log.getWeight() * log.getReps();
	        } else if (log.getDistance() != null) {
	            volume = log.getDistance() * 10;
	        } else if (log.getTime() != null) {
	            volume = log.getTime();
	        } else {
	            volume = 1.0;
	        }

	        chartData.putIfAbsent(date, new EnumMap<>(ExerciseCategory.class));
	        chartData.get(date).merge(category, volume, Double::sum);
	    }
	    for (Map<ExerciseCategory, Double> dayData : chartData.values()) {
	        for (ExerciseCategory cat : ExerciseCategory.values()) {
	            dayData.putIfAbsent(cat, 0.0);
	        }
	    }

	    return chartData;
	}

	public Map<LocalDate, Double> getNutritionCaloriesChartData(int userID) {
		LogCollection data = database.loadData();
		Map<LocalDate, Double> chartData = new TreeMap<>();

		for (NutritionLog log : data.getNutritionLogs()) {
			if (log.getUserID() == userID) {
				LocalDate date = log.getAddTime().toLocalDate();
				if (log.getEnergy() != null) {
					double calories = log.getEnergy() * log.getQuantity();
					chartData.put(date, chartData.getOrDefault(date, 0.0) + calories);
				}
			}
		}
		return chartData;
	}

	public boolean updateUserGoal(User updatedUser) {
		return new JsonUserDatabase().updateUser(updatedUser);
	}

	public List<WorkoutLog> getRecentWorkoutLogs(int userID, int limit) {
		List<WorkoutLog> userLogs = database.loadData().getWorkoutLogs().stream()
				.filter(log -> log.getUserID() == userID).collect(Collectors.toList());

		int start = Math.max(0, userLogs.size() - limit);
		return userLogs.subList(start, userLogs.size());
	}

	public List<NutritionLog> getRecentNutritionLogs(int userID, int limit) {
		List<NutritionLog> userLogs = database.loadData().getNutritionLogs().stream()
				.filter(log -> log.getUserID() == userID).collect(Collectors.toList());

		int start = Math.max(0, userLogs.size() - limit);
		return userLogs.subList(start, userLogs.size());
	}
}