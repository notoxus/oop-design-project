package com.group3.model;

import java.util.ArrayList;
import java.util.List;

import com.group3.util.RandomAlgorithm;

public class ThinStrategy implements ExerciseSuggestionStrategy {
	@Override
	public List<Exercise> suggest(User user, ExerciseLibrary lib) {
		List<Exercise> result = new ArrayList<>();
		// Exercise rate (card-iso-comp-flex): 1:3:2:1 
		result.addAll(RandomAlgorithm.pickRandom(lib.getCategoryByName("CARDIO"), 1));
		result.addAll(RandomAlgorithm.pickRandom(lib.getCategoryByName("ISOLATE"), 3));
		result.addAll(RandomAlgorithm.pickRandom(lib.getCategoryByName("COMPOUND"), 2));
		result.addAll(RandomAlgorithm.pickRandom(lib.getCategoryByName("FLEXIBILITY"), 1));
		return result;
	}
}
