package com.group3.model;

public enum WorkoutGoal {
	LOSE_FAT("Giảm mỡ"),
    MUSCLE_GAIN("Tăng cơ"),
    MAINTENANCE("Giữ dáng");

    private String displayName;

    WorkoutGoal(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
