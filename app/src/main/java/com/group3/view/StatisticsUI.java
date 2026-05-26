package com.group3.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.group3.controller.NutritionLogController;
import com.group3.controller.StatisticsPresenter;
import com.group3.controller.WorkoutHandling;
import com.group3.controller.WorkoutLogController;
import com.group3.model.*;

public class StatisticsUI extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	// Dependency
	private StatisticsPresenter presenter;
	private User user;
	private WorkoutHandling workoutHandling;
	// Main components
	private DefaultCategoryDataset workoutDataset;
	private DefaultCategoryDataset nutritionDataset;
	private DefaultTableModel recentWorkoutModel;
	private DefaultTableModel recentNutritionModel;
	private JLabel lblTotalVolume;
	private JLabel lblTotalCalo;
	private JComboBox<WorkoutGoal> cbGoal;
	private JButton btnUpdateGoal;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");

	public StatisticsUI(StatisticsPresenter presenter, User user, WorkoutHandling workoutHandling,
			WorkoutLogController wCtrl, NutritionLogController nCtrl) {
		this.presenter = presenter;
		this.user = user;
		this.workoutHandling = workoutHandling;
		wCtrl.addObserver(this);
		nCtrl.addObserver(this);
		initComponents();
		refreshData();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setBackground(new Color(245, 245, 245));

		JPanel scrollContainer = new JPanel();
		scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));
		scrollContainer.setBackground(new Color(245, 245, 245));
		scrollContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel quickStatsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		quickStatsPanel.setOpaque(false);
		quickStatsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

		lblTotalVolume = new JLabel("0 kg", SwingConstants.CENTER);
		lblTotalCalo = new JLabel("0 kcal", SwingConstants.CENTER);

		quickStatsPanel
				.add(createStatBox("Tổng Volume", lblTotalVolume, new Color(255, 243, 224), new Color(230, 81, 0)));
		quickStatsPanel
				.add(createStatBox("Năng lượng nạp", lblTotalCalo, new Color(227, 242, 253), new Color(21, 101, 192)));

		scrollContainer.add(quickStatsPanel);
		scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

		workoutDataset = new DefaultCategoryDataset();
		JFreeChart workoutChart = ChartFactory.createLineChart("", "Ngày", "Khối lượng (kg)", workoutDataset,
				PlotOrientation.VERTICAL, true, true, false);
		styleChart(workoutChart);

		ChartPanel workoutChartPanel = new ChartPanel(workoutChart);
		workoutChartPanel.setPreferredSize(new Dimension(350, 200));

		JPanel workoutChartCard = createCardPanel("BIỂU ĐỒ TẬP LUYỆN");
		workoutChartCard.add(workoutChartPanel, BorderLayout.CENTER);
		scrollContainer.add(workoutChartCard);
		scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

		nutritionDataset = new DefaultCategoryDataset();
		JFreeChart nutritionChart = ChartFactory.createLineChart("", "Ngày", "Kcal", nutritionDataset,
				PlotOrientation.VERTICAL, false, true, false);
		styleChart(nutritionChart);

		ChartPanel nutritionChartPanel = new ChartPanel(nutritionChart);
		nutritionChartPanel.setPreferredSize(new Dimension(350, 200));

		JPanel nutritionChartCard = createCardPanel("BIỂU ĐỒ DINH DƯỠNG");
		nutritionChartCard.add(nutritionChartPanel, BorderLayout.CENTER);
		scrollContainer.add(nutritionChartCard);
		scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

		JPanel workoutLogCard = createCardPanel("NHẬT KÝ TẬP LUYỆN GẦN ĐÂY");
		recentWorkoutModel = new DefaultTableModel(new String[] { "Ngày", "Bài tập", "Kết quả" }, 0);
		JTable tblRecentWorkout = createCompactTable(recentWorkoutModel);
		workoutLogCard.add(new JScrollPane(tblRecentWorkout), BorderLayout.CENTER);

		scrollContainer.add(workoutLogCard);
		scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

		JPanel nutritionLogCard = createCardPanel("NHẬT KÝ DINH DƯỠNG GẦN ĐÂY");
		recentNutritionModel = new DefaultTableModel(new String[] { "Ngày", "Món ăn", "Calo" }, 0);
		JTable tblRecentNutrition = createCompactTable(recentNutritionModel);
		nutritionLogCard.add(new JScrollPane(tblRecentNutrition), BorderLayout.CENTER);

		scrollContainer.add(nutritionLogCard);
		scrollContainer.add(Box.createRigidArea(new Dimension(0, 15)));

		JPanel goalCard = createCardPanel("MỤC TIÊU CÁ NHÂN");
		goalCard.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		cbGoal = new JComboBox<>(WorkoutGoal.values());
		cbGoal.setSelectedItem(user.getGoal());
		cbGoal.setFont(new Font("Arial", Font.PLAIN, 13));
		cbGoal.setPreferredSize(new Dimension(150, 35));

		btnUpdateGoal = new JButton("Lưu thay đổi");
		btnUpdateGoal.setFont(new Font("Arial", Font.BOLD, 12));
		btnUpdateGoal.setBackground(new Color(33, 150, 243));
		btnUpdateGoal.setForeground(Color.BLACK);
		btnUpdateGoal.setFocusPainted(false);
		btnUpdateGoal.setPreferredSize(new Dimension(120, 35));

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
		btnRefresh.setPreferredSize(new Dimension(0, 45));

		bottomBar.add(btnRefresh, BorderLayout.CENTER);
		add(bottomBar, BorderLayout.SOUTH);

		btnRefresh.addActionListener(e -> refreshData());

		btnUpdateGoal.addActionListener(e -> {
			WorkoutGoal selectedGoal = (WorkoutGoal) cbGoal.getSelectedItem();
			if (selectedGoal != null) {
				user.setGoal(selectedGoal);
				boolean success = presenter.updateUserGoal(user);

				if (success) {
					workoutHandling.setGoal(user);
					JOptionPane.showMessageDialog(this, "Đã cập nhật mục tiêu thành công sang: " + selectedGoal,
							"Thông báo", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Lỗi hệ thống khi cập nhật mục tiêu!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	@Override
	public void update() {
		refreshData();
	}

	public void refreshData() {
		workoutDataset.clear();
		nutritionDataset.clear();
		recentWorkoutModel.setRowCount(0);
		recentNutritionModel.setRowCount(0);

		int currentUserId = user.getUserID();
		double sumVolume = 0;
		double sumCalo = 0;

		Map<LocalDate, Map<ExerciseCategory, Double>> workoutData = presenter.getWorkoutVolumeChartData(currentUserId);
		for (Map.Entry<LocalDate, Map<ExerciseCategory, Double>> entry : workoutData.entrySet()) {
			String dateStr = entry.getKey().format(dateFormatter);
			for (Map.Entry<ExerciseCategory, Double> catEntry : entry.getValue().entrySet()) {
				workoutDataset.addValue(catEntry.getValue(), catEntry.getKey().getCatName(), dateStr);
				sumVolume += catEntry.getValue();
			}
		}

		Map<LocalDate, Double> nutritionData = presenter.getNutritionCaloriesChartData(currentUserId);
		for (Map.Entry<LocalDate, Double> entry : nutritionData.entrySet()) {
			String dateStr = entry.getKey().format(dateFormatter);
			nutritionDataset.addValue(entry.getValue(), "Calo", dateStr);
			sumCalo += entry.getValue();
		}

		lblTotalVolume.setText(String.format("%.1f kg", sumVolume));
		lblTotalCalo.setText(String.format("%.1f kcal", sumCalo));

		List<WorkoutLog> recentWLogs = presenter.getRecentWorkoutLogs(currentUserId, 5);
		for (int i = recentWLogs.size() - 1; i >= 0; i--) {
			WorkoutLog log = recentWLogs.get(i);
			String resultStr = "—";
			if (log.getWeight() != null && log.getReps() != null)
				resultStr = log.getWeight() + "kg x " + log.getReps() + "reps";
			else if (log.getDistance() != null && log.getTime() != null)
				resultStr = log.getDistance() + "km — " + log.getTime() + "phút";
			else if (log.getReps() != null)
				resultStr = log.getReps() + " reps";
			else if (log.getTime() != null)
				resultStr = log.getTime() + " phút";

			recentWorkoutModel.addRow(new Object[] { log.getDate().format(dateFormatter),
					log.getExercise().getExerciseName(), resultStr });
		}

		List<NutritionLog> recentNLogs = presenter.getRecentNutritionLogs(currentUserId, 5);
		for (int i = recentNLogs.size() - 1; i >= 0; i--) {
			NutritionLog log = recentNLogs.get(i);
			recentNutritionModel.addRow(new Object[] { log.getAddTime().format(dateFormatter), log.getProductName(),
					log.getEnergy() != null ? String.format("%.1f", log.getEnergy() * log.getQuantity()) : "0" });
		}
	}

	private JPanel createCardPanel(String title) {
		JPanel card = new JPanel(new BorderLayout(5, 5));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true), title, TitledBorder.LEFT,
				TitledBorder.TOP, new Font("Arial", Font.BOLD, 12), new Color(100, 100, 100)));
		return card;
	}

	private JPanel createStatBox(String title, JLabel lblValue, Color bgColor, Color fgColor) {
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		box.setBackground(bgColor);
		box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTitle.setForeground(new Color(100, 100, 100));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblValue.setFont(new Font("Arial", Font.BOLD, 16));
		lblValue.setForeground(fgColor);
		lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);

		box.add(lblTitle);
		box.add(Box.createRigidArea(new Dimension(0, 5)));
		box.add(lblValue);
		return box;
	}

	private void styleChart(JFreeChart chart) {
		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(new Color(230, 230, 230));
		plot.setOutlineVisible(false);
		LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true); // (lines, shapes)
		renderer.setDefaultStroke(new BasicStroke(2.0f));
		renderer.setDefaultShapesVisible(true);
		renderer.setDefaultShape(new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8)); // Hình tròn 8x8
		plot.setRenderer(renderer);
	}

	private JTable createCompactTable(DefaultTableModel model) {
		JTable table = new JTable(model);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		table.setRowHeight(28);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(240, 240, 240));
		table.setEnabled(false);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getTableHeader().setBackground(new Color(250, 250, 250));
		table.getTableHeader().setForeground(new Color(80, 80, 80));
		table.setPreferredScrollableViewportSize(new Dimension(table.getPreferredSize().width, 28 * 5));
		return table;
	}
}