package com.group3.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

import com.group3.controller.WorkoutLogController;
import com.group3.controller.WorkoutHandling;
import com.group3.model.Exercise;
import com.group3.model.ExerciseLibrary;
import com.group3.model.TrackingType;
import com.group3.model.User;
import com.group3.model.WorkoutLog;
import com.group3.strategy.RecommendationResult;

public class ExerciseUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private WorkoutLogController logController;
	private WorkoutHandling handling;
	private ExerciseLibrary library;
	private User user;
    private DashboardUI dashboard;

	private Exercise currentExercise;
    private JLabel lblExName, lblExTarget;
    
	private JLabel lblWeight, lblReps, lblDistance, lblTime;
	private JTextField txtWeight, txtReps, txtDistance, txtTime;
	private JButton btnSave, btnBack;
	private JPanel formPanel;

	public ExerciseUI(WorkoutLogController logController, WorkoutHandling handling, ExerciseLibrary library,
			User user, DashboardUI dashboard) {
		this.logController = logController;
		this.handling = handling;
		this.library = library;
		this.user = user;
        this.dashboard = dashboard;

		initComponents();
		setupEvents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        btnBack = new JButton("⬅ Quay lại thư viện");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 12));
        btnBack.setFocusPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setForeground(new Color(33, 150, 243));
        
		JLabel lblTitle = new JLabel("NHẬT KÝ TẬP LUYỆN", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(Box.createRigidArea(new Dimension(130, 20)), BorderLayout.EAST); // Cân bằng với nút Back
		add(headerPanel, BorderLayout.NORTH);

		formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 8, 10, 8);

		gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        
        JPanel exerciseCard = new JPanel(new GridLayout(2, 1));
        exerciseCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(33, 150, 243), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        exerciseCard.setBackground(new Color(240, 248, 255));
        lblExName = new JLabel("Chưa chọn bài tập");
        lblExName.setFont(new Font("Arial", Font.BOLD, 16));
        lblExTarget = new JLabel("---");
        lblExTarget.setFont(new Font("Arial", Font.PLAIN, 13));
        lblExTarget.setForeground(Color.GRAY);
        exerciseCard.add(lblExName);
        exerciseCard.add(lblExTarget);
        
        formPanel.add(exerciseCard, gbc);

        gbc.gridwidth = 1;
		lblWeight = new JLabel("Mức tạ (kg):"); txtWeight = new JTextField(10);
		lblReps = new JLabel("Số hiệp (reps):"); txtReps = new JTextField(10);
		lblDistance = new JLabel("Quãng đường (km):"); txtDistance = new JTextField(10);
		lblTime = new JLabel("Thời gian (phút):"); txtTime = new JTextField(10);

		gbc.gridy = 1; gbc.gridx = 0; formPanel.add(lblWeight, gbc);
		gbc.gridx = 1; formPanel.add(txtWeight, gbc);
		gbc.gridy = 2; gbc.gridx = 0; formPanel.add(lblReps, gbc);
		gbc.gridx = 1; formPanel.add(txtReps, gbc);
		gbc.gridy = 3; gbc.gridx = 0; formPanel.add(lblDistance, gbc);
		gbc.gridx = 1; formPanel.add(txtDistance, gbc);
		gbc.gridy = 4; gbc.gridx = 0; formPanel.add(lblTime, gbc);
		gbc.gridx = 1; formPanel.add(txtTime, gbc);

		add(formPanel, BorderLayout.CENTER);

		btnSave = new JButton("Lưu & Xem Gợi Ý Set Tiếp Theo");
		btnSave.setBackground(new Color(0, 153, 76));
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("Arial", Font.BOLD, 14));
		btnSave.setPreferredSize(new Dimension(0, 40));
		add(btnSave, BorderLayout.SOUTH);
	}

	private void setupEvents() {
        btnBack.addActionListener(e -> dashboard.showLibrary());

		btnSave.addActionListener(e -> {
			try {
				if (currentExercise == null) {
					JOptionPane.showMessageDialog(this, "Bạn chưa chọn bài tập từ Thư viện!");
					return;
				}

				Double weight = txtWeight.isVisible() && !txtWeight.getText().isEmpty() ? Double.parseDouble(txtWeight.getText()) : null;
				Integer reps = txtReps.isVisible() && !txtReps.getText().isEmpty() ? Integer.parseInt(txtReps.getText()) : null;
				Double distance = txtDistance.isVisible() && !txtDistance.getText().isEmpty() ? Double.parseDouble(txtDistance.getText()) : null;
				Double time = txtTime.isVisible() && !txtTime.getText().isEmpty() ? Double.parseDouble(txtTime.getText()) : null;

				WorkoutLog log = new WorkoutLog.WorkoutLogBuilder().setUserID(user.getUserID())
						.setLogID((int) (System.currentTimeMillis() % 100000)).setDate(LocalDateTime.now())
						.setExercise(currentExercise).setWeight(weight).setReps(reps).setDistance(distance).setTime(time)
						.build();

				if (logController.addWorkoutLog(log)) {
					RecommendationResult recommendation = handling.calculateNextSet(log);
					JOptionPane.showMessageDialog(this,
							"Đã lưu thành công!\n\n" + recommendation.toString() + "\n💡 Lời khuyên: "
									+ recommendation.getMessage(),
							"Gợi ý từ Hệ thống", JOptionPane.INFORMATION_MESSAGE);
					txtWeight.setText(""); txtReps.setText("");
					txtDistance.setText(""); txtTime.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "Lỗi khi lưu file!");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Vui lòng chỉ nhập số hợp lệ vào các ô đo lường!");
			} catch (IllegalStateException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage());
			}
		});
	}

	public void setSelectedExercise(Exercise ex) {
		if (ex != null) {
            this.currentExercise = ex;
            lblExName.setText("🏋️ " + ex.getExerciseName());
            lblExTarget.setText("Nhóm cơ/Mục tiêu: " + ex.getTargetMuscle() + " (" + ex.getCategory() + ")");
			updateFormVisibility(ex);
		}
	}

	private void updateFormVisibility(Exercise ex) {
		TrackingType type = ex.getTrackingType();
		boolean showWeight = false, showReps = false, showDist = false, showTime = false;

		switch (type) {
		case WEIGHT_REP_TIME: showWeight = true; showReps = true; showTime = true; break;
		case DISTANCE_TIME: showDist = true; showTime = true; break;
		case TIME_ONLY: showTime = true; break;
		case WEIGHT_REP: showWeight = true; showReps = true; break;
		case REP_ONLY: showReps = true; break;
		}

		lblWeight.setVisible(showWeight); txtWeight.setVisible(showWeight);
		lblReps.setVisible(showReps); txtReps.setVisible(showReps);
		lblDistance.setVisible(showDist); txtDistance.setVisible(showDist);
		lblTime.setVisible(showTime); txtTime.setVisible(showTime);

		if (!showWeight) txtWeight.setText("");
		if (!showReps) txtReps.setText("");
		if (!showDist) txtDistance.setText("");
		if (!showTime) txtTime.setText("");

		formPanel.revalidate();
		formPanel.repaint();
	}
}