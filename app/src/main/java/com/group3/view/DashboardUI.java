package com.group3.view;

import java.awt.*;
import javax.swing.*;

import com.group3.controller.*;
import com.group3.model.*;

public class DashboardUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Color PRIMARY = new Color(33, 150, 243);
	private static final Color NAV_ACTIVE = new Color(33, 150, 243);
	private static final Color NAV_IDLE = new Color(150, 150, 150);
	private static final Color NAV_BG = Color.WHITE;

	private MainFrame mainFrame;
	private User user;
	private WorkoutLogController workoutCtrl;
	private WorkoutHandling workoutHandling;
	private ExerciseLibrary library;
	private NutritionLogController nutritionCtrl;
	private StatisticsPresenter statPresenter;
	private ExerciseSuggestionService suggestionService;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private ExerciseUI exerciseUI;

	private JButton btnLibrary, btnNutrition, btnLogs, btnStats, btnProfile;

	public DashboardUI(MainFrame mainFrame, User user, WorkoutLogController workoutCtrl,
			WorkoutHandling workoutHandling, ExerciseLibrary library, NutritionLogController nutritionCtrl,
			StatisticsPresenter statPresenter, ExerciseSuggestionService suggestionService) {

		this.mainFrame = mainFrame;
		this.user = user;
		this.workoutCtrl = workoutCtrl;
		this.workoutHandling = workoutHandling;
		this.library = library;
		this.nutritionCtrl = nutritionCtrl;
		this.statPresenter = statPresenter;
		this.suggestionService = suggestionService;

		setLayout(new BorderLayout());

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		exerciseUI = new ExerciseUI(workoutCtrl, workoutHandling, library, user, this);
		cardPanel.add(new ExerciseLibraryUI(library, user, suggestionService, null, this), "LIBRARY");
		cardPanel.add(new NutritionUI(nutritionCtrl, user), "NUTRITION");
		cardPanel.add(new ManageLogUI(workoutCtrl, nutritionCtrl), "LOGS");
		cardPanel.add(new StatisticsUI(statPresenter, user, workoutHandling, workoutCtrl, nutritionCtrl), "STATS");
		cardPanel.add(new ProfileUI(user, mainFrame), "PROFILE");
		cardPanel.add(exerciseUI, "EXERCISE_INPUT");

		add(cardPanel, BorderLayout.CENTER);
		add(buildNavBar(), BorderLayout.SOUTH);
	}

	public void navigateToExerciseInput(Exercise ex) {
		exerciseUI.setSelectedExercise(ex);
		cardLayout.show(cardPanel, "EXERCISE_INPUT");
		setNavActive(null);
	}
    
    public void showLibrary() {
        switchTo("LIBRARY", btnLibrary);
    }

	private JPanel buildNavBar() {
		JPanel navBar = new JPanel(new GridLayout(1, 5));
		navBar.setPreferredSize(new Dimension(400, 70));
		navBar.setBackground(NAV_BG);
		navBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

		btnLibrary = createNavButton("🏠", "Trang chủ");
		btnNutrition = createNavButton("🔍", "Dinh dưỡng");
		btnLogs = createNavButton("📄", "Nhật ký");
		btnStats = createNavButton("📊", "Tiến độ");
		btnProfile = createNavButton("👤", "Hồ sơ");

		navBar.add(btnLibrary);
		navBar.add(btnNutrition);
		navBar.add(btnLogs);
		navBar.add(btnStats);
		navBar.add(btnProfile);

		btnLibrary.addActionListener(e -> switchTo("LIBRARY", btnLibrary));
		btnNutrition.addActionListener(e -> switchTo("NUTRITION", btnNutrition));
		btnLogs.addActionListener(e -> switchTo("LOGS", btnLogs));
		btnStats.addActionListener(e -> switchTo("STATS", btnStats));
		btnProfile.addActionListener(e -> switchTo("PROFILE", btnProfile));

		setNavActive(btnLibrary);
		return navBar;
	}

	private void switchTo(String card, JButton activeBtn) {
		cardLayout.show(cardPanel, card);
		setNavActive(activeBtn);
	}

	private void setNavActive(JButton activeBtn) {
		for (JButton btn : new JButton[] { btnLibrary, btnNutrition, btnLogs, btnStats, btnProfile }) {
			if (btn == null) continue;
			btn.setForeground(btn == activeBtn ? NAV_ACTIVE : NAV_IDLE);
		}
	}

	private JButton createNavButton(String icon, String label) {
		JButton btn = new JButton("<html><center><div style='font-size:20pt'>" + icon + "</div><div style='font-size:7pt'>" + label + "</div></center></html>");
		btn.setFocusPainted(false);
		btn.setBackground(NAV_BG);
		btn.setForeground(NAV_IDLE);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}