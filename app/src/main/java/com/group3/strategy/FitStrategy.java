package com.group3.strategy;

import com.group3.model.*;
import com.group3.util.RandomAlgorithm;

import java.util.*;

public class FitStrategy implements ExerciseSuggestionStrategy {

	@Override
	public List<Exercise> suggest(User user, ExerciseLibrary lib) {
		List<Exercise> result = new ArrayList<>();
		// Exercise rate (card-iso-comp-flex): 2:1:2:2
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.CARDIO), 2));
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.ISOLATE), 1));
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.COMPOUND), 2));
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.FLEXIBILITY), 2));
		return result;
	}

}
