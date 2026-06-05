package com.group3.model;

import java.util.List;

public class NoWeightStrategy implements NextSetRecommendationStrategy {

	@Override
    public RecommendationResult calculateNextSet(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
        Integer currentReps = currentLog.getReps();
        Double currentDistance = currentLog.getDistance();
        Double currentTime = currentLog.getTime();
        boolean highWeeklyLoad = isHighWeeklyLoad(currentLog, weeklyLogs);

        // Case 1: Bodyweight without weight
        if (currentReps != null) {
            int nextReps = currentReps + (highWeeklyLoad ? 1 : 2);
            String msg = highWeeklyLoad
                    ? "Tăng nhẹ thêm 1 rep và giữ form ổn định cho set tiếp theo."
                    : "Cố gắng vượt qua giới hạn thêm 2 reps nhé!";
            return new RecommendationResult(null, nextReps, null, null, msg);
        }
        
        // Case 2: Cardio
        if (currentDistance != null) {
            Double nextDistance = currentDistance + (highWeeklyLoad ? 0.25 : 0.5);
            Double nextTime = (currentTime != null) ? currentTime : null; 
            String msg = highWeeklyLoad
                    ? "Tăng nhẹ thêm 250m và ưu tiên nhịp độ ổn định."
                    : "Thử thách tăng thêm 500m để nâng cao sức bền tim mạch!";
            
            return new RecommendationResult(null, null, nextDistance, nextTime, msg);
        }

        // Case 3: Flexibility (just time: Plank, Yoga, Stretching...)
        if (currentTime != null) {
            Double nextTime = currentTime + (highWeeklyLoad ? 0.5 : 1.0);
            String msg = highWeeklyLoad
                    ? "Tăng nhẹ thêm 30 giây và tập trung vào nhịp thở ổn định."
                    : "Giữ tư thế lâu hơn một chút, tập trung vào nhịp thở và cảm nhận cơ thể.";
            return new RecommendationResult(null, null, null, nextTime, msg);
        }

        return new RecommendationResult(null, null, null, null, "Không đủ dữ liệu để gợi ý.");
    }
}
