package com.group3.controller;

import java.util.List;

import com.group3.model.User;
import com.group3.model.WorkoutLog;
import com.group3.model.LoseFatStrategy;
import com.group3.model.MuscleGainStrategy;
import com.group3.model.NextSetRecommendationStrategy;
import com.group3.model.NoWeightStrategy;
import com.group3.model.RecommendationResult;

public class WorkoutHandling {

	private final NoWeightStrategy noWeightStrategy = new NoWeightStrategy();
	private NextSetRecommendationStrategy nextSetStrategy;

	public RecommendationResult calculateNextSet(WorkoutLog currentLog) {
        return calculateNextSet(currentLog, List.of());
    }

	public RecommendationResult calculateNextSet(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
        if (this.nextSetStrategy == null) {
            throw new IllegalStateException("Chưa thiết lập chiến lược tập luyện (Strategy)!");
        }
        
        if (currentLog.getWeight() == null) {
            return noWeightStrategy.calculateNextSet(currentLog, weeklyLogs);
        }

        return this.nextSetStrategy.calculateNextSet(currentLog, weeklyLogs);
    }

	public void setGoal(User user) {
		if (user == null || user.getGoal() == null) {
			this.nextSetStrategy = noWeightStrategy;
			return;
		}

		switch (user.getGoal()) {
		case MUSCLE_GAIN:
			this.nextSetStrategy = new MuscleGainStrategy();
			break;
		case LOSE_FAT:
			this.nextSetStrategy = new LoseFatStrategy();
			break;
		case MAINTENANCE:
		default:
			this.nextSetStrategy = noWeightStrategy;
			break;
		}
	}

}
