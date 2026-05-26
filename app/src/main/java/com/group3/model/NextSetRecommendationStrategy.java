package com.group3.model;

public interface NextSetRecommendationStrategy {
	RecommendationResult calculateNextSet(WorkoutLog currentLog);
}
