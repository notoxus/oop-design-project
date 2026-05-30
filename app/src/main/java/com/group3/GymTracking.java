package com.group3;

import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import com.group3.controller.ExerciseSuggestionService;
import com.group3.controller.LoginManager;
import com.group3.controller.NutritionLogController;
import com.group3.controller.RegisterManager;
import com.group3.controller.StatisticsPresenter;
import com.group3.controller.WorkoutHandling;
import com.group3.controller.WorkoutLogController;
import com.group3.model.Exercise;
import com.group3.model.ExerciseCategory;
import com.group3.model.ExerciseLibrary;
import com.group3.model.JsonAdminDatabase;
import com.group3.model.JsonCategoryDatabase;
import com.group3.model.JsonExerciseDatabase;
import com.group3.model.JsonLogDatabase;
import com.group3.model.JsonUserDatabase;
import com.group3.model.OpenFoodFactsAdapter;
import com.group3.model.TrackingType;
import com.group3.view.MainFrame;

public class GymTracking {
    public static void main(String[] args) {
    	try {
            FlatLightLaf.setup();
            UIManager.put("defaultFont", new Font("Arial", Font.PLAIN, 14));
        } catch (Exception e) {
            System.err.println("Failed to initialize look and feel: " + e.getMessage());
        }
        JsonUserDatabase userDB = new JsonUserDatabase();
        JsonAdminDatabase adminDB = new JsonAdminDatabase();
        JsonLogDatabase logDB = new JsonLogDatabase();
        JsonExerciseDatabase libraryDB = new JsonExerciseDatabase();
        JsonCategoryDatabase catDB = new JsonCategoryDatabase();
        List<ExerciseCategory> categories = catDB.loadData();

        if (categories.isEmpty()) {
        	// Root category
            ExerciseCategory strengthCat = new ExerciseCategory(1, "Strength", null);
            ExerciseCategory cardioCat = new ExerciseCategory(2, "Cardio", null);
            ExerciseCategory flexCat = new ExerciseCategory(3, "Flexibility", null);
            // Sub category
            strengthCat.addSubCat(new ExerciseCategory(4, "COMPOUND", Arrays.asList(TrackingType.WEIGHT_REP, TrackingType.REP_ONLY, TrackingType.WEIGHT_REP_TIME)));
            strengthCat.addSubCat(new ExerciseCategory(5, "ISOLATE", Arrays.asList(TrackingType.WEIGHT_REP, TrackingType.REP_ONLY)));
            cardioCat.addSubCat(new ExerciseCategory(6, "Cường độ cao", Arrays.asList(TrackingType.DISTANCE_TIME, TrackingType.TIME_ONLY)));
            cardioCat.addSubCat(new ExerciseCategory(7, "Cường độ thấp", Arrays.asList(TrackingType.DISTANCE_TIME, TrackingType.TIME_ONLY)));
            flexCat.addSubCat(new ExerciseCategory(8, "Yoga", Arrays.asList(TrackingType.TIME_ONLY)));
            flexCat.addSubCat(new ExerciseCategory(9, "Stretching", Arrays.asList(TrackingType.TIME_ONLY)));
            // Add them to cat.json file
            categories.add(strengthCat);
            categories.add(cardioCat);
            categories.add(flexCat);
            catDB.saveData(categories);
        }
        ExerciseLibrary library = new ExerciseLibrary();
        List<Exercise> savedExercises = libraryDB.loadData();
        if (savedExercises != null) {
            for (Exercise ex : savedExercises) {
                library.addExercise(ex);
            }
        }
        OpenFoodFactsAdapter nutritionAPI = new OpenFoodFactsAdapter();
        
        LoginManager loginManager = new LoginManager(userDB, adminDB);
        RegisterManager registerManager = new RegisterManager(userDB);
        
        WorkoutLogController workoutCtrl = new WorkoutLogController(logDB);
        WorkoutHandling workoutHandling = new WorkoutHandling(); 
        
        NutritionLogController nutritionCtrl = new NutritionLogController(logDB, nutritionAPI);
        StatisticsPresenter statPresenter = new StatisticsPresenter(logDB);
        ExerciseSuggestionService suggestionService = new ExerciseSuggestionService();
        SwingUtilities.invokeLater(() -> {
            MainFrame appFrame = new MainFrame(
                loginManager, 
                registerManager, 
                libraryDB, 
                userDB, 
                workoutCtrl, 
                workoutHandling, 
                library, 
                nutritionCtrl, 
                statPresenter, 
                suggestionService
            );
            appFrame.setVisible(true);
        });
    }
}
