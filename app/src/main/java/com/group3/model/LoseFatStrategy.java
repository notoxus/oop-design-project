package com.group3.model;

import java.util.List;

public class LoseFatStrategy implements NextSetRecommendationStrategy {

	@Override
    public RecommendationResult calculateNextSet(WorkoutLog currentLog, List<WorkoutLog> weeklyLogs) {
        Double currentWeight = currentLog.getWeight(); 
        Integer currentReps = currentLog.getReps();

        if (currentWeight == null || currentReps == null) {
            return new RecommendationResult(null, null, null, null, "Lỗi: Bài tập này không dùng tạ!");
        }

        boolean highWeeklyLoad = isHighWeeklyLoad(currentLog, weeklyLogs);
        double nextWeight = currentWeight;
        int nextReps = 15;
        String msg = "Tuyệt vời, giữ vững mức tạ và hoàn thành 15 reps để đốt tối đa calo!";

        if (currentReps >= 20) { // Too light
            nextWeight = highWeeklyLoad ? currentWeight : currentWeight + 2.5;
            nextReps = 15;
            msg = highWeeklyLoad
                    ? "Giữ mức tạ hiện tại và hoàn thành 15 reps thật đều để duy trì nhịp tập."
                    : "Bài này đã quá dễ, thêm chút tạ để kích thích tim mạch làm việc mạnh hơn.";
        } else if (currentReps < 12) { // Exhaustic
            nextWeight = Math.max(0, currentWeight - 2.5);
            nextReps = 15;
            msg = "Mục tiêu là sức bền! Hãy giảm tạ xuống để đẩy được nhiều reps hơn.";
        } else if (highWeeklyLoad) {
            if (currentReps <= 12) {
                nextWeight = Math.max(0, currentWeight - 2.5);
                nextReps = 15;
                msg = "Giảm nhẹ mức tạ và đưa reps về 15 để duy trì nhịp tập ổn định hơn.";
            } else {
                nextReps = currentReps - 1;
                msg = "Giữ mức tạ hiện tại và giảm 1 rep để nhịp tập ổn định hơn.";
            }
        } else {
            nextReps = currentReps + 1;
            msg = "Giữ mức tạ hiện tại và thử tăng thêm 1 rep để cải thiện sức bền.";
        }

        return new RecommendationResult(nextWeight, nextReps, null, null, msg);
    }
}
