package com.group3.strategy;

import java.util.*;

import com.group3.model.*;
import com.group3.util.RandomAlgorithm;

public class FatStrategy implements ExerciseSuggestionStrategy {
	@Override
	public List<Exercise> suggest(User user, ExerciseLibrary lib) {
		List<Exercise> result = new ArrayList<>();
		// Exercise rate (card-iso-comp-flex): 4:1:1:1
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.CARDIO), 4));
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.ISOLATE), 1));
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.COMPOUND), 1));
		result.addAll(RandomAlgorithm.pickRandom(lib.getByCategory(ExerciseCategory.FLEXIBILITY), 1));
		return result;
	}
}
