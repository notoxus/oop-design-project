package com.group3.controller;

import com.group3.model.User;
import com.group3.model.WorkoutLog;
import com.group3.model.LoseFatStrategy;
import com.group3.model.MuscleGainStrategy;
import com.group3.model.NextSetRecommendationStrategy;
import com.group3.model.NoWeightStrategy;
import com.group3.model.RecommendationResult;

public class WorkoutHandling {

	private NextSetRecommendationStrategy nextSetStrategy;

	public RecommendationResult calculateNextSet(WorkoutLog currentLog) {
        if (this.nextSetStrategy == null) {
            throw new IllegalStateException("Chưa thiết lập chiến lược tập luyện (Strategy)!");
        }
        
        if (currentLog.getWeight() == null) {
            return new NoWeightStrategy().calculateNextSet(currentLog);
        }

        return this.nextSetStrategy.calculateNextSet(currentLog);
    }

	public void setGoal(User user) {
		if (user == null || user.getGoal() == null) {
			this.nextSetStrategy = new NoWeightStrategy();
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
			this.nextSetStrategy = new NoWeightStrategy();
			break;
		}
	}

}