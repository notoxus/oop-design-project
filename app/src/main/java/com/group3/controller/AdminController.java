package com.group3.controller;

import java.util.List;

import com.group3.model.*;

public class AdminController {
	private ExerciseLibrary exerciseLibrary;
	private Admin admin;
	private JsonExerciseDatabase libraryDB;
    private JsonUserDatabase userDB;

	public AdminController(ExerciseLibrary exerciseLibrary, Admin admin, JsonExerciseDatabase libraryDB,
			JsonUserDatabase userDB) {
		if (admin == null) {
            throw new IllegalArgumentException("Không thể khởi tạo! Bạn không phải là Admin!");
        }
		this.exerciseLibrary = exerciseLibrary;
		this.admin = admin;
		this.libraryDB = libraryDB;
		this.userDB = userDB;
	}
	// They are Admin's functions
	// Add Exercise to Exercise Library
	public boolean addExercise(int id, String name, ExerciseCategory category, TrackingType trackingType, String targetMuscle) {
	    try {
	    	// Invoke Exercise Factory
	        Exercise newExercise = ExerciseFactory.createExercise(id, name, category, trackingType, targetMuscle);
	        exerciseLibrary.addExercise(newExercise);
	        libraryDB.saveData(exerciseLibrary.getLib());
	        System.out.println("Exercise added successfully: " + newExercise.getExerciseName());
	        return true;
	    } catch (IllegalStateException e) {
	        System.out.println("Failed to create exercise: " + e.getMessage());
	        return false;
	    }
	}
	public boolean updateExercise(Exercise oldExercise, String newName, ExerciseCategory category, TrackingType trackingType, String targetMuscle) {
		try {
			Exercise updatedExercise = ExerciseFactory.createExercise(oldExercise.getExerciseID(), newName, category, trackingType, targetMuscle);
			exerciseLibrary.removeExercise(oldExercise);
			exerciseLibrary.addExercise(updatedExercise);
			libraryDB.saveData(exerciseLibrary.getLib());
			System.out.println("Exercise updated successfully: " + newName);
			return true;
		} catch (Exception e) {
			System.out.println("Failed to update exercise: " + e.getMessage());
			return false;
		}
	}
	// Remove Exercise from Exercise Library
    public boolean deleteExercise(String exerciseName) {
        Exercise target = exerciseLibrary.searchExercise(exerciseName);
        if (target != null) {
            exerciseLibrary.removeExercise(target);
            boolean saved = libraryDB.saveData(exerciseLibrary.getLib());
            if (saved) {
                System.out.println("Exercise deleted successfully: " + exerciseName);
            }
            return saved;
        } else {
            System.out.println("Exercise not found for deletion.");
            return false;
        }
    }
    // View the User detail list
    public List<User> viewUserDetails() {
        return userDB.loadData();
    }

}
