package com.group3.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.group3.controller.StatisticsPresenter;
import com.group3.model.*;

public class StatisticsUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private StatisticsPresenter presenter;
    private User user;
    
    // Các thành phần giao diện
    private DefaultTableModel workoutSummaryModel;
    private DefaultTableModel nutritionSummaryModel;
    private DefaultTableModel recentWorkoutModel;
    private DefaultTableModel recentNutritionModel;
    
    private JComboBox<WorkoutGoal> cbGoal;
    private JButton btnUpdateGoal;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");

    public StatisticsUI(StatisticsPresenter presenter, User user) {
        this.presenter = presenter;
        this.user = user;
        initComponents();
        refreshData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel scrollContainer = new JPanel();
        scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));
        scrollContainer.setBackground(new Color(245, 245, 245));
        scrollContainer.setBorder(BorderFactory.createEmptyBorder(15, 12, 15, 12));

        JPanel statsCard = createCardPanel("TỔNG QUAN KHỐI LƯỢNG & CALO");
        statsCard.setLayout(new GridLayout(2, 1, 0, 10));
        
        workoutSummaryModel = new DefaultTableModel(new String[]{"Ngày", "Nhóm cơ", "Khối lượng (kg)"}, 0);
        JTable tblWSummary = createCompactTable(workoutSummaryModel);
        statsCard.add(new JScrollPane(tblWSummary));

        nutritionSummaryModel = new DefaultTableModel(new String[]{"Ngày", "Năng lượng nạp (kcal)"}, 0);
        JTable tblNSummary = createCompactTable(nutritionSummaryModel);
        statsCard.add(new JScrollPane(tblNSummary));
        
        scrollContainer.add(statsCard);
        scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel workoutLogCard = createCardPanel("NHẬT KÝ TẬP LUYỆN GẦN ĐÂY");
        recentWorkoutModel = new DefaultTableModel(new String[]{"Ngày", "Bài tập", "Kết quả"}, 0);
        JTable tblRecentWorkout = createCompactTable(recentWorkoutModel);
        workoutLogCard.add(new JScrollPane(tblRecentWorkout), BorderLayout.CENTER);
        
        scrollContainer.add(workoutLogCard);
        scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel nutritionLogCard = createCardPanel("NHẬT KÝ DINH DƯỠNG GẦN ĐÂY");
        recentNutritionModel = new DefaultTableModel(new String[]{"Ngày", "Món ăn", "Calo"}, 0);
        JTable tblRecentNutrition = createCompactTable(recentNutritionModel);
        nutritionLogCard.add(new JScrollPane(tblRecentNutrition), BorderLayout.CENTER);
        
        scrollContainer.add(nutritionLogCard);
        scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel goalCard = createCardPanel("MỤC TIÊU");
        goalCard.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        cbGoal = new JComboBox<>(WorkoutGoal.values());
        cbGoal.setSelectedItem(user.getGoal());
        cbGoal.setFont(new Font("Arial", Font.PLAIN, 13));
        cbGoal.setPreferredSize(new Dimension(180, 35));
        
        btnUpdateGoal = new JButton("Cập nhật");
        btnUpdateGoal.setFont(new Font("Arial", Font.BOLD, 12));
        btnUpdateGoal.setBackground(new Color(33, 150, 243));
        btnUpdateGoal.setForeground(Color.WHITE);
        btnUpdateGoal.setFocusPainted(false);
        btnUpdateGoal.setPreferredSize(new Dimension(90, 35));
        
        goalCard.add(new JLabel("Mục tiêu hiện tại: "));
        goalCard.add(cbGoal);
        goalCard.add(btnUpdateGoal);
        
        scrollContainer.add(goalCard);

        JScrollPane mainScrollPane = new JScrollPane(scrollContainer);
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScrollPane, BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(Color.WHITE);
        bottomBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        
        JButton btnRefresh = new JButton("Làm mới toàn bộ dữ liệu");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 13));
        btnRefresh.setContentAreaFilled(false);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setForeground(new Color(33, 150, 243));
        btnRefresh.setPreferredSize(new Dimension(0, 40));
        
        bottomBar.add(btnRefresh, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> refreshData());

        btnUpdateGoal.addActionListener(e -> {
            WorkoutGoal selectedGoal = (WorkoutGoal) cbGoal.getSelectedItem();
            if (selectedGoal != null) {
                user.setGoal(selectedGoal);
                boolean success = new JsonUserDatabase().updateUser(user);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Đã cập nhật mục tiêu thành công sang: " + selectedGoal, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi hệ thống khi cập nhật mục tiêu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void refreshData() {
        workoutSummaryModel.setRowCount(0);
        nutritionSummaryModel.setRowCount(0);
        recentWorkoutModel.setRowCount(0);
        recentNutritionModel.setRowCount(0);

        int currentUserId = user.getUserID();

        Map<LocalDate, Map<ExerciseCategory, Double>> workoutData = presenter.getWorkoutVolumeChartData(currentUserId);
        for (Map.Entry<LocalDate, Map<ExerciseCategory, Double>> entry : workoutData.entrySet()) {
            String dateStr = entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM"));
            for (Map.Entry<ExerciseCategory, Double> catEntry : entry.getValue().entrySet()) {
                workoutSummaryModel.addRow(new Object[]{dateStr, catEntry.getKey().name(), String.format("%.1f", catEntry.getValue())});
            }
        }

        Map<LocalDate, Double> nutritionData = presenter.getNutritionCaloriesChartData(currentUserId);
        for (Map.Entry<LocalDate, Double> entry : nutritionData.entrySet()) {
            String dateStr = entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM"));
            nutritionSummaryModel.addRow(new Object[]{dateStr, String.format("%.1f", entry.getValue())});
        }

        List<WorkoutLog> allWorkoutLogs = new JsonLogDatabase().loadData().getWorkoutLogs();
        List<WorkoutLog> userWorkoutLogs = allWorkoutLogs.stream()
                .filter(log -> log.getUserID() == currentUserId)
                .collect(Collectors.toList());
        
        int wStart = Math.max(0, userWorkoutLogs.size() - 5);
        List<WorkoutLog> recentWLogs = userWorkoutLogs.subList(wStart, userWorkoutLogs.size());
        for (int i = recentWLogs.size() - 1; i >= 0; i--) {
            WorkoutLog log = recentWLogs.get(i);
            String resultStr = "—";
            if (log.getWeight() != null && log.getReps() != null) resultStr = log.getWeight() + "kg x " + log.getReps() + "r";
            else if (log.getDistance() != null && log.getTime() != null) resultStr = log.getDistance() + "km — " + log.getTime() + "m";
            else if (log.getReps() != null) resultStr = log.getReps() + " reps";
            else if (log.getTime() != null) resultStr = log.getTime() + " phút";

            recentWorkoutModel.addRow(new Object[]{
                log.getDate().format(dateFormatter),
                log.getExercise().getExerciseName(),
                resultStr
            });
        }

        List<NutritionLog> allNutritionLogs = new JsonLogDatabase().loadData().getNutritionLogs();
        List<NutritionLog> userNutritionLogs = allNutritionLogs.stream()
                .filter(log -> log.getUserID() == currentUserId)
                .collect(Collectors.toList());

        int nStart = Math.max(0, userNutritionLogs.size() - 5);
        List<NutritionLog> recentNLogs = userNutritionLogs.subList(nStart, userNutritionLogs.size());
        for (int i = recentNLogs.size() - 1; i >= 0; i--) {
            NutritionLog log = recentNLogs.get(i);
            recentNutritionModel.addRow(new Object[]{
                log.getAddTime().format(dateFormatter),
                log.getProductName(),
                log.getEnergy() != null ? String.format("%.1f", log.getEnergy() * log.getQuantity()) : "0"
            });
        }
    }

    private JPanel createCardPanel(String title) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true), 
            title, TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 13), new Color(30, 30, 30)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        return card;
    }

    private JTable createCompactTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(24);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(245, 245, 245));
        table.setEnabled(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setForeground(Color.GRAY);
        return table;
    }
}