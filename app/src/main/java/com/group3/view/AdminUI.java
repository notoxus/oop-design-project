package com.group3.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.group3.controller.AdminController;
import com.group3.controller.WorkoutLogController;
import com.group3.model.Admin;
import com.group3.model.ExerciseLibrary;
import com.group3.model.User;
import com.group3.model.WorkoutLog;

public class AdminUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private AdminController controller;
	private Admin admin;
	private ExerciseLibrary library;
	private WorkoutLogController workoutCtrl;

	private JPanel cardPanel;
	private CardLayout cardLayout;
	private JPanel userListContainer;
	private JPanel userDetailsContainer;

	public AdminUI(MainFrame mainFrame, AdminController controller, Admin currentAdmin, ExerciseLibrary library,
			WorkoutLogController workoutCtrl) {
		this.mainFrame = mainFrame;
		this.controller = controller;
		this.admin = currentAdmin;
		this.library = library;
		this.workoutCtrl = workoutCtrl;

		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		cardPanel.add(createAdminLibraryPanel(), "LIBRARY");
		userListContainer = createUserListPanel();
		cardPanel.add(userListContainer, "USERS");

		userDetailsContainer = new JPanel(new BorderLayout());
		cardPanel.add(userDetailsContainer, "USER_DETAILS");

		add(cardPanel, BorderLayout.CENTER);

		// Admin navigation bar
		JPanel navBar = new JPanel(new GridLayout(1, 3));
		navBar.setPreferredSize(new Dimension(400, 60));
		navBar.setBackground(Color.WHITE);
		navBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

		JButton btnLibrary = createNavButton("📚", "Thư viện bài tập");
		JButton btnUsers = createNavButton("👥", "Người dùng");
		JButton btnLogout = createNavButton("🚪", "Đăng xuất");

		navBar.add(btnLibrary);
		navBar.add(btnUsers);
		navBar.add(btnLogout);

		btnLibrary.addActionListener(e -> cardLayout.show(cardPanel, "LIBRARY"));
		btnUsers.addActionListener(e -> {
			refreshUserListPanel();
			cardLayout.show(cardPanel, "USERS");
		});
		btnLogout.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				mainFrame.showLoginScreen();
			}
		});
		add(navBar, BorderLayout.SOUTH);
	}

	private void refreshUserListPanel() {
		cardPanel.remove(userListContainer);
		userListContainer = createUserListPanel();
		cardPanel.add(userListContainer, "USERS");
		cardPanel.revalidate();
		cardPanel.repaint();
	}

	private JButton createNavButton(String icon, String text) {
		JButton btn = new JButton("<html><center><font size='5'>" + icon + "</font><br><font size='2'>" + text
				+ "</font></center></html>");
		btn.setFocusPainted(false);
		btn.setBackground(Color.WHITE);
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}

	private JPanel createUserListPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		JLabel lblTitle = new JLabel("Danh Sách Người Dùng", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		panel.add(lblTitle, BorderLayout.NORTH);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setBackground(Color.WHITE);

		List<User> users = controller.viewUserDetails();
		if (users != null) {
			for (User u : users) {
				JPanel card = new JPanel(new BorderLayout());
				card.setBackground(new Color(240, 248, 255));
				card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10),
						BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)));
				card.setMaximumSize(new Dimension(380, 70));
				card.setCursor(new Cursor(Cursor.HAND_CURSOR));

				JLabel lblIcon = new JLabel("  👤  ");
				lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
				JPanel info = new JPanel(new GridLayout(2, 1));
				info.setOpaque(false);
				String displayName = (u.getName() != null && !u.getName().isBlank()) ? u.getName() : u.getUsername();
				String goalText = u.getGoal() != null ? u.getGoal().toString() : "Chưa có";

				JLabel nameLabel = new JLabel(displayName + " (@" + u.getUsername() + ")");
				nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
				JLabel goalLabel = new JLabel("Mục tiêu: " + goalText);

				goalLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				info.add(nameLabel);
				info.add(goalLabel);

				card.add(lblIcon, BorderLayout.WEST);
				card.add(info, BorderLayout.CENTER);

				card.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						showUserDetails(u);
					}
				});
				listPanel.add(card);
			}
		}

		panel.add(new JScrollPane(listPanel), BorderLayout.CENTER);
		return panel;
	}

	private void showUserDetails(User user) {
		userDetailsContainer.removeAll();

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(new Color(33, 150, 243));
		headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JButton btnBack = new JButton("← Quay lại");
		btnBack.setFont(new Font("Arial", Font.BOLD, 14));
		btnBack.setForeground(Color.WHITE);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.addActionListener(e -> cardLayout.show(cardPanel, "USERS"));

		JLabel lblTitle = new JLabel("Hồ sơ: " + user.getName(), SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitle.setForeground(Color.WHITE);

		headerPanel.add(btnBack, BorderLayout.WEST);
		headerPanel.add(lblTitle, BorderLayout.CENTER);
		headerPanel.add(Box.createRigidArea(new Dimension(100, 20)), BorderLayout.EAST);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(Color.WHITE);

		JPanel infoPanel = new JPanel(new GridLayout(6, 1, 0, 10));
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		infoPanel.add(new JLabel("<html><b>Tên đăng nhập:</b> " + user.getUsername() + "</html>"));
		infoPanel.add(new JLabel("<html><b>Giới tính:</b> "
				+ (user.getGender() != null ? user.getGender() : "Chưa cập nhật") + "</html>"));
		infoPanel.add(new JLabel("<html><b>Tuổi:</b> " + user.getAge() + "</html>"));
		infoPanel.add(new JLabel("<html><b>Chiều cao:</b> " + user.getHeight() + " cm</html>"));
		infoPanel.add(new JLabel("<html><b>Cân nặng:</b> " + user.getWeight() + " kg</html>"));
		infoPanel.add(new JLabel("<html><b>Mục tiêu:</b> "
				+ (user.getGoal() != null ? user.getGoal().toString() : "Chưa có") + "</html>"));

		contentPanel.add(infoPanel);
		contentPanel.add(createWorkoutHistoryPanel(user));

		userDetailsContainer.add(headerPanel, BorderLayout.NORTH);
		userDetailsContainer.add(new JScrollPane(contentPanel), BorderLayout.CENTER);

		userDetailsContainer.revalidate();
		userDetailsContainer.repaint();

		cardLayout.show(cardPanel, "USER_DETAILS");
	}

	private JPanel createAdminLibraryPanel() {
		return new ExerciseLibraryUI(library, admin, null, controller, null);
	}

	private JPanel createWorkoutHistoryPanel(User user) {
		JPanel historyPanel = new JPanel();
		historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
		historyPanel.setBackground(Color.WHITE);
		historyPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

		JLabel lblHistory = new JLabel("Lịch sử tập luyện");
		lblHistory.setFont(new Font("Arial", Font.BOLD, 15));
		lblHistory.setAlignmentX(Component.LEFT_ALIGNMENT);
		historyPanel.add(lblHistory);
		historyPanel.add(Box.createRigidArea(new Dimension(0, 8)));

		List<WorkoutLog> logs = workoutCtrl.getAllLogs();
		boolean hasLog = false;
		if (logs != null) {
			for (WorkoutLog log : logs) {
				if (log.getUserID() != user.getUserID()) continue;
				historyPanel.add(createWorkoutLogRow(log));
				historyPanel.add(Box.createRigidArea(new Dimension(0, 6)));
				hasLog = true;
			}
		}

		if (!hasLog) {
			JLabel emptyLabel = new JLabel("Người dùng chưa có nhật ký tập luyện.");
			emptyLabel.setFont(new Font("Arial", Font.ITALIC, 13));
			emptyLabel.setForeground(Color.GRAY);
			emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			historyPanel.add(emptyLabel);
		}

		return historyPanel;
	}

	private JPanel createWorkoutLogRow(WorkoutLog log) {
		JPanel row = new JPanel(new GridLayout(2, 1, 0, 2));
		row.setBackground(new Color(250, 250, 250));
		row.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
				BorderFactory.createEmptyBorder(8, 10, 8, 10)));
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
		row.setAlignmentX(Component.LEFT_ALIGNMENT);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
		JLabel title = new JLabel(log.getDate().format(formatter) + " - " + log.getExercise().getExerciseName());
		title.setFont(new Font("Arial", Font.BOLD, 13));
		JLabel result = new JLabel(formatWorkoutResult(log));
		result.setFont(new Font("Arial", Font.PLAIN, 12));
		result.setForeground(new Color(90, 90, 90));

		row.add(title);
		row.add(result);
		return row;
	}

	private String formatWorkoutResult(WorkoutLog log) {
		if (log.getDistance() != null && log.getTime() != null && log.getDistance() > 0) {
			return String.format("%.1f km - Pace: %.2f", log.getDistance(), log.paceCal());
		}
		if (log.getWeight() != null && log.getReps() != null) {
			return log.getWeight() + "kg x " + log.getReps() + " reps";
		}
		if (log.getReps() != null) {
			return log.getReps() + " reps";
		}
		if (log.getTime() != null) {
			return log.getTime() + " phút";
		}
		return "Không có chỉ số";
	}
}
