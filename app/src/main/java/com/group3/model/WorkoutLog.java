package com.group3.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkoutLog {
	private final int userID;
	private final int logID;
	private final LocalDateTime date;
	private final Exercise exercise;
	// Wrapper fields
	private final Double weight;
	private final Integer reps;
	private final Double distance;
	private final Double time;

	private WorkoutLog(WorkoutLogBuilder builder) {
		this.userID = builder.userID;
		this.logID = builder.logID;
		this.date = builder.date;
		this.exercise = builder.exercise;
		this.weight = builder.weight;
		this.reps = builder.reps;
		this.distance = builder.distance;
		this.time = builder.time;
	}

	public int getUserID() {
		return userID;
	}

	public int getLogID() {
		return logID;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public Double getWeight() {
		return weight;
	}

	public Integer getReps() {
		return reps;
	}

	public Double getDistance() {
		return distance;
	}

	public Double getTime() {
		return time;
	}

	public static class WorkoutLogBuilder {
		private int userID;
		private int logID = -1;
		private LocalDateTime date;
		private Exercise exercise;
		// Default assignment operator
		private Double weight = null;
		private Integer reps = null;
		private Double distance = null;
		private Double time = null;

		public WorkoutLogBuilder setUserID(int userID) {
			this.userID = userID;
			return this;
		}

		public WorkoutLogBuilder setLogID(int logID) {
			this.logID = logID;
			return this;
		}

		public WorkoutLogBuilder setDate(LocalDateTime date) {
			this.date = date;
			return this;
		}

		public WorkoutLogBuilder setExercise(Exercise exercise) {
			this.exercise = exercise;
			return this;
		}

		public WorkoutLogBuilder setWeight(Double weight) {
			this.weight = weight;
			return this;
		}

		public WorkoutLogBuilder setReps(Integer reps) {
			this.reps = reps;
			return this;
		}

		public WorkoutLogBuilder setDistance(Double distance) {
			this.distance = distance;
			return this;
		}

		public WorkoutLogBuilder setTime(Double time) {
			this.time = time;
			return this;
		}

		public WorkoutLog build() {
			if (this.logID == -1)
				throw new IllegalStateException("Cần có ID để lưu log!");
			if (this.date == null)
				throw new IllegalStateException("Cần có ngày tháng để lưu log!");
			if (this.exercise == null)
				throw new IllegalStateException("Cần có bài tập để lưu log!");
			return new WorkoutLog(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		sb.append("Bài tập: ").append(exercise.getExerciseName()).append(" [").append(date.format(formatter))
				.append("]\n");

		if (weight != null)
			sb.append("Mức tạ: ").append(weight).append(" kg\n");
		if (reps != null)
			sb.append("Số hiệp: ").append(reps).append("\n");
		if (distance != null)
			sb.append("Quãng đường: ").append(distance).append(" km\n");
		if (time != null)
			sb.append("Thời gian: ").append(time).append(" phút\n");
		return sb.toString();
	}

	public double paceCal() {
		return this.time / this.distance;
	}
}
