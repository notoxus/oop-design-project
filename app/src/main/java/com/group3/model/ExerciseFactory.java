package com.group3.model;

import java.util.List;

public class ExerciseFactory {
    public static Exercise createExercise(int id, String name, ExerciseCategory category, TrackingType trackingType, String targetMuscle) {
    	if (trackingType == null) {
            List<TrackingType> allowed = category.getAllowedTrackingType();
            if (allowed != null && !allowed.isEmpty()) {
                trackingType = allowed.get(0);
            } else {
                trackingType = TrackingType.WEIGHT_REP;
            }
        }
        return new Exercise.ExerciseBuilder()
                .setExerciseID(id)
                .setExerciseName(name)
                .setCategory(category)
                .setTrackingType(trackingType)
                .setTargetMuscle(targetMuscle)
                .build();
    }
}