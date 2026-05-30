package com.group3.controller;

import java.util.List;
import com.group3.model.Exercise;
import com.group3.model.ExerciseLibrary;
import com.group3.model.User;
import com.group3.model.WorkoutGoal;
import com.group3.model.ExerciseSuggestionStrategy;
import com.group3.model.FatStrategy;
import com.group3.model.FitStrategy;
import com.group3.model.ThinStrategy;

public class ExerciseSuggestionService {

	// For new user
	// Goal recommended rely on BMI index
	public WorkoutGoal recommend(User newUser) {
		double bmi = newUser.bmiCal();
		if (bmi < 18.5) {
			return WorkoutGoal.MUSCLE_GAIN;
		} else if (bmi >= 18.5 && bmi <= 24.9) {
			return WorkoutGoal.MAINTENANCE;
		} else {
			return WorkoutGoal.LOSE_FAT;
		}
	}

	// Assignment operator with properly Strategy after user choice goal
	public void applyUserChoice(User user, WorkoutGoal chosenGoal) {
		user.setGoal(chosenGoal);
	}

	// Logic for user choice
	// If they didnt choose goal we set default as recommended goal
	public List<Exercise> suggest(User user, ExerciseLibrary lib) {
		WorkoutGoal goal = user.getGoal() != null ? user.getGoal() : recommend(user);
		return createStrategy(goal).suggest(user, lib);
	}

	private ExerciseSuggestionStrategy createStrategy(WorkoutGoal goal) {
		switch (goal) {
		case MUSCLE_GAIN:
			return new ThinStrategy();
		case LOSE_FAT:
			return new FatStrategy();
		case MAINTENANCE:
		default:
			return new FitStrategy();
		}
	}
}
