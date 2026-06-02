package com.group3.model;

import java.util.List;

public class MuscleGainStrategy implements NextSetRecommendationStrategy {

	@Override
    public RecommendationResult calculateNextSet(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
        Double currentWeight = currentLog.getWeight(); 
        Integer currentReps = currentLog.getReps();

        if (currentWeight == null || currentReps == null) {
            return new RecommendationResult(null, null, null, null, "Lỗi: Bài tập này không dùng tạ!");
        }

        if (isHighWeeklyLoad(currentLog, weeklyLogs)) {
            int recoveryReps = Math.max(8, Math.min(currentReps, 10));
            return new RecommendationResult(currentWeight, recoveryReps, null, null,
                    "Tuần này bạn đã tập khá nhiều. Giữ mức tạ hiện tại và tập vừa sức để tránh quá tải.");
        }

        double nextWeight = currentWeight;
        int nextReps = 10;
        String msg = "Giữ nguyên mức tạ, tập trung cảm nhận cơ bắp!";

        if (currentReps >= 12) { // Too light
            nextWeight = currentWeight + 2.5;
            nextReps = 8;
            msg = "Bạn chỉ được đến đó thôi sao. Hãy tăng thêm 2.5kg và giảm số reps xuống 8 đi nào.";
        } else if (currentReps < 8) { // Exhaustic
            nextWeight = Math.max(0, currentWeight - 2.5);
            nextReps = 10;
            msg = "Cố quá là quá cố đấy bạn eyy, hãy giảm tạ một chút và đảm bảo đã tập chuẩn form.";
        }

        return new RecommendationResult(nextWeight, nextReps, null, null, msg);
    }
}
