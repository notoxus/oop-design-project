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
            if (highWeeklyLoad) {
                return new RecommendationResult(null, currentReps, null, null,
                        "Tuần này bạn đã vận động khá nhiều. Giữ reps hiện tại để cơ thể phục hồi tốt hơn.");
            }
            int nextReps = currentReps + 2; 
            return new RecommendationResult(null, nextReps, null, null, "Cố gắng vượt qua giới hạn thêm 2 reps nhé!");
        }
        
        // Case 2: Cardio
        if (currentDistance != null) {
            if (highWeeklyLoad) {
                return new RecommendationResult(null, null, currentDistance, currentTime,
                        "Tuần này tải tập đã cao. Giữ quãng đường hiện tại và ưu tiên nhịp độ ổn định.");
            }
            Double nextDistance = currentDistance + 0.5; 
            Double nextTime = (currentTime != null) ? currentTime : null; 
            
            return new RecommendationResult(null, null, nextDistance, nextTime, "Thử thách tăng thêm 500m để nâng cao sức bền tim mạch!");
        }

        // Case 3: Flexibility (just time: Plank, Yoga, Stretching...)
        if (currentTime != null) {
            if (highWeeklyLoad) {
                return new RecommendationResult(null, null, null, currentTime,
                        "Tuần này cơ thể đã hoạt động nhiều. Giữ thời gian hiện tại và tập trung vào phục hồi.");
            }
            Double nextTime = currentTime + 1.0; 
            return new RecommendationResult(null, null, null, nextTime, "Giữ tư thế lâu hơn một chút, tập trung vào nhịp thở và cảm nhận cơ thể.");
        }

        return new RecommendationResult(null, null, null, null, "Không đủ dữ liệu để gợi ý.");
    }
}
