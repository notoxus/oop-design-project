package com.group3;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.group3.controller.ExerciseSuggestionService;
import com.group3.controller.LoginManager;
import com.group3.controller.NutritionLogController;
import com.group3.controller.RegisterManager;
import com.group3.controller.StatisticsPresenter;
import com.group3.controller.WorkoutHandling;
import com.group3.controller.WorkoutLogController;
import com.group3.model.Exercise;
import com.group3.model.ExerciseLibrary;
import com.group3.model.JsonAdminDatabase;
import com.group3.model.JsonLibraryDatabase;
import com.group3.model.JsonLogDatabase;
import com.group3.model.JsonUserDatabase;
import com.group3.model.OpenFoodFactsAdapter;
import com.group3.view.MainFrame;

public class GymTracking {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonUserDatabase userDB = new JsonUserDatabase();
        JsonAdminDatabase adminDB = new JsonAdminDatabase();
        JsonLogDatabase logDB = new JsonLogDatabase();
        JsonLibraryDatabase libraryDB = new JsonLibraryDatabase();
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