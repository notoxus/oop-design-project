package com.group3.controller;

import java.util.List;

import com.group3.model.Exercise;
import com.group3.model.ExerciseLibrary;
import com.group3.model.User;
import com.group3.model.WorkoutGoal;
import com.group3.strategy.ExerciseSuggestionStrategy;
import com.group3.strategy.FatStrategy;
import com.group3.strategy.FitStrategy;
import com.group3.strategy.ThinStrategy;

public class ExerciseSuggestionService {
	private ExerciseSuggestionStrategy suggestionStrategy;

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
		switch (chosenGoal) {
		case MUSCLE_GAIN:
			this.suggestionStrategy = new ThinStrategy();
			break;
		case LOSE_FAT:
			this.suggestionStrategy = new FatStrategy();
			break;
		case MAINTENANCE:
		default:
			this.suggestionStrategy = new FitStrategy();
			break;
		}
	}

	// Logic for user choice
	// If they didnt choose goal we set default as recommended goal
	public List<Exercise> suggest(User user, ExerciseLibrary lib) {
		if (this.suggestionStrategy == null) {
			applyUserChoice(user, recommend(user));
		}
		return this.suggestionStrategy.suggest(user, lib);
	}
}
