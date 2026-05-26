package com.group3.model;

public class LoseFatStrategy implements NextSetRecommendationStrategy {

	@Override
    public RecommendationResult calculateNextSet(WorkoutLog currentLog) {
        Double currentWeight = currentLog.getWeight(); 
        Integer currentReps = currentLog.getReps();

        if (currentWeight == null || currentReps == null) {
            return new RecommendationResult(null, null, null, null, "Lỗi: Bài tập này không dùng tạ!");
        }

        double nextWeight = currentWeight;
        int nextReps = 15;
        String msg = "Tuyệt vời, giữ vững mức tạ và hoàn thành 15 reps để đốt tối đa calo!";

        if (currentReps >= 20) { // Too light
            nextWeight = currentWeight + 2.5;
            nextReps = 15;
            msg = "Bài này đã quá dễ, thêm chút tạ để kích thích tim mạch làm việc mạnh hơn.";
        } else if (currentReps < 12) { // Exhaustic
            nextWeight = Math.max(0, currentWeight - 2.5);
            nextReps = 15;
            msg = "Mục tiêu là sức bền! Hãy giảm tạ xuống để đẩy được nhiều reps hơn.";
        }

        return new RecommendationResult(nextWeight, nextReps, null, null, msg);
    }
}
