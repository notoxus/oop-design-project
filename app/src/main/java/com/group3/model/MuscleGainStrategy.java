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

        boolean highWeeklyLoad = isHighWeeklyLoad(currentLog, weeklyLogs);
        double nextWeight = currentWeight;
        int nextReps = 10;
        String msg = "Giữ nguyên mức tạ, tập trung cảm nhận cơ bắp!";

        if (currentReps >= 12) { // Too light
            nextWeight = highWeeklyLoad ? currentWeight : currentWeight + 2.5;
            nextReps = highWeeklyLoad ? 10 : 8;
            msg = highWeeklyLoad
                    ? "Giữ mức tạ hiện tại và giảm số reps xuống 10 để set tiếp theo vẫn kiểm soát tốt."
                    : "Bạn chỉ được đến đó thôi sao. Hãy tăng thêm 2.5kg và giảm số reps xuống 8 đi nào.";
        } else if (currentReps < 8) { // Exhaustic
            nextWeight = Math.max(0, currentWeight - 2.5);
            nextReps = 10;
            msg = "Cố quá là quá cố đấy bạn eyy, hãy giảm tạ một chút và đảm bảo đã tập chuẩn form.";
        } else if (highWeeklyLoad) {
            if (currentReps <= 8) {
                nextWeight = Math.max(0, currentWeight - 2.5);
                nextReps = 10;
                msg = "Giảm nhẹ mức tạ và đưa reps về 10 để set tiếp theo ổn định hơn.";
            } else {
                nextReps = currentReps - 1;
                msg = "Giữ nguyên mức tạ và giảm 1 rep để kiểm soát form tốt hơn.";
            }
        } else {
            nextReps = currentReps + 1;
            msg = "Giữ nguyên mức tạ và thử tăng thêm 1 rep cho set tiếp theo.";
        }

        return new RecommendationResult(nextWeight, nextReps, null, null, msg);
    }
}
