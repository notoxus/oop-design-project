package com.group3.model;

import java.util.List;

public interface ExerciseSuggestionStrategy {
	public List<Exercise> suggest(User user, ExerciseLibrary lib);
}
