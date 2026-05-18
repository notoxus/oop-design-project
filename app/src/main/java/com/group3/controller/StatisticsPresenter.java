package com.group3.controller;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.group3.model.DataConnection;
import com.group3.model.ExerciseCategory;
import com.group3.model.LogCollection;
import com.group3.model.NutritionLog;
import com.group3.model.WorkoutLog;

public class StatisticsPresenter {
	private DataConnection<LogCollection> database;
	public StatisticsPresenter(DataConnection<LogCollection> database) {
		this.database = database;
	}

	public void printDailyNutritionReport(LocalDate targetDate, int userID) {
		double totalKcal = 0, totalProtein = 0, totalFat = 0, totalCarb = 0;
		LogCollection data = database.loadData();
		List<NutritionLog> nutritionLogs = data.getNutritionLogs();
		for (NutritionLog log : nutritionLogs) {
			if (log.getAddTime().toLocalDate().equals(targetDate)) {

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

		System.out.println("=== THỐNG KÊ DINH DƯỠNG NGÀY " + targetDate + " ===");
		System.out.printf("- Tổng Calo nạp vào: %.1f kcal\n", totalKcal);
		System.out.printf("- Tổng Protein     : %.1f g\n", totalProtein);
		System.out.printf("- Tổng Fat         : %.1f g\n", totalFat);
		System.out.printf("- Tổng Carb        : %.1f g\n", totalCarb);
	}

	public void printDailyWorkoutReport(LocalDate targetDate, int userID) {
		double totalVolume = 0;
		double totalDistance = 0;
		LogCollection data = database.loadData();
		List<WorkoutLog> workoutLogs = data.getWorkoutLogs();
		for (WorkoutLog log : workoutLogs) {
			if (log.getDate().toLocalDate().equals(targetDate)) {
				if (log.getWeight() != null && log.getReps() != null) {
					totalVolume += (log.getWeight() * log.getReps());
				}
				if (log.getDistance() != null) {
					totalDistance += log.getDistance();
				}
			}
		}

		System.out.println("=== THỐNG KÊ TẬP LUYỆN NGÀY " + targetDate + " ===");
		System.out.printf("- Tổng khối lượng tạ đã nâng (Volume): %.1f kg\n", totalVolume);
		if (totalDistance > 0) {
			System.out.printf("- Tổng quãng đường Cardio chạy được  : %.1f km\n", totalDistance);
		}
	}

	public Map<LocalDate, Map<ExerciseCategory, Double>> getWorkoutVolumeChartData(int userID) {
		LogCollection data = database.loadData();
		Map<LocalDate, Map<ExerciseCategory, Double>> chartData = new TreeMap<>();

		for (WorkoutLog log : data.getWorkoutLogs()) {
			if (log.getUserID() == userID) {
				LocalDate date = log.getDate().toLocalDate();
				ExerciseCategory category = log.getExercise().getCategory();
				if (log.getWeight() != null && log.getReps() != null) {
					double volume = log.getWeight() * log.getReps();
					chartData.putIfAbsent(date, new EnumMap<>(ExerciseCategory.class));
					Map<ExerciseCategory, Double> dayData = chartData.get(date);
					dayData.put(category, dayData.getOrDefault(category, 0.0) + volume);
				}
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
}