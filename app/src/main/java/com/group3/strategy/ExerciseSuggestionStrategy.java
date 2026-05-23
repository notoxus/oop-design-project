package com.group3.strategy;

import java.util.List;

import com.group3.model.Exercise;
import com.group3.model.ExerciseLibrary;
import com.group3.model.User;

public interface ExerciseSuggestionStrategy {
	public List<Exercise> suggest(User user, ExerciseLibrary lib);
}
