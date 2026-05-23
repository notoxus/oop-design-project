package com.group3.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.group3.model.Exercise;

public final class RandomAlgorithm { // Avoiding create new object
	
	private RandomAlgorithm() {
	}
	// Create a random list for each Exercise Suggestion Strategy
	// Avoiding invoke from others with static keyword
	public static List<Exercise> pickRandom(List<Exercise> source, int count) {
		if (source == null || source.isEmpty())
			return new ArrayList<>();
		List<Exercise> copy = new ArrayList<>(source);
		Collections.shuffle(copy);
		return copy.subList(0, Math.min(count, copy.size()));
	}
}
