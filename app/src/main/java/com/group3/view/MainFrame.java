package com.group3.view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.group3.controller.*;
import com.group3.model.*;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
    private JPanel mainContainer;

    private LoginManager loginManager;
    private RegisterManager registerManager;
    private JsonExerciseDatabase libraryDB;
    private JsonUserDatabase userDB;
    private WorkoutLogController workoutCtrl;
    private WorkoutHandling workoutHandling;
    private ExerciseLibrary library;
    private NutritionLogController nutritionCtrl;
    private StatisticsPresenter statPresenter;
    private ExerciseSuggestionService suggestionService;

    public MainFrame(LoginManager loginManager, RegisterManager registerManager, 
                        JsonExerciseDatabase libraryDB, JsonUserDatabase userDB, 
                        WorkoutLogController workoutCtrl, WorkoutHandling workoutHandling, 
                        ExerciseLibrary library, NutritionLogController nutritionCtrl, 
                        StatisticsPresenter statPresenter, ExerciseSuggestionService suggestionService) {
        
        this.loginManager = loginManager;
        this.registerManager = registerManager;
        this.libraryDB = libraryDB;
        this.userDB = userDB;
        this.workoutCtrl = workoutCtrl;
        this.workoutHandling = workoutHandling;
        this.library = library;
        this.nutritionCtrl = nutritionCtrl;
        this.statPresenter = statPresenter;
        this.suggestionService = suggestionService;

        setTitle("Gym Tracking App");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        mainContainer.add(new LoginForm(this, loginManager), "LOGIN");
        mainContainer.add(new RegisterForm(this, registerManager), "REGISTER");

        add(mainContainer);
        showLoginScreen();
    }

    public void showLoginScreen() {
        cardLayout.show(mainContainer, "LOGIN");
    }

    public void showRegisterScreen() {
        cardLayout.show(mainContainer, "REGISTER");
    }

    public void showUserDashboard(User user) {
        workoutHandling.setGoal(user);
        DashboardUI userDashboard = new DashboardUI(
            this, user, workoutCtrl, workoutHandling, library, 
            nutritionCtrl, statPresenter, suggestionService
        );
        mainContainer.add(userDashboard, "USER_DASHBOARD");
        cardLayout.show(mainContainer, "USER_DASHBOARD");
    }

    public void showAdminDashboard(Admin admin) {
        AdminController adminController = new AdminController(library, admin, libraryDB, userDB);
        AdminUI adminDashboard = new AdminUI(this, adminController, admin, library);
        mainContainer.add(adminDashboard, "ADMIN_DASHBOARD");
        cardLayout.show(mainContainer, "ADMIN_DASHBOARD");
    }
}