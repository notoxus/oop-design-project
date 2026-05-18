package com.group3.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import com.group3.controller.AdminController;
import com.group3.model.Admin;
import com.group3.model.ExerciseLibrary;
import com.group3.model.User;

public class AdminUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private AdminController controller;
	private Admin admin;
	private ExerciseLibrary library;

	private JPanel cardPanel;
	private CardLayout cardLayout;
	private JPanel userDetailsContainer;

	public AdminUI(MainFrame mainFrame, AdminController controller, Admin currentAdmin, ExerciseLibrary library) {
		this.mainFrame = mainFrame;
		this.controller = controller;
		this.admin = currentAdmin;
		this.library = library;

		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		cardPanel.add(createAdminLibraryPanel(), "LIBRARY");
		cardPanel.add(createUserListPanel(), "USERS");

		userDetailsContainer = new JPanel(new BorderLayout());
		cardPanel.add(userDetailsContainer, "USER_DETAILS");

		add(cardPanel, BorderLayout.CENTER);

		// Admin navigation bar
		JPanel navBar = new JPanel(new GridLayout(1, 2));
		navBar.setPreferredSize(new Dimension(400, 60));
		navBar.setBackground(Color.WHITE);
		navBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

		JButton btnLibrary = createNavButton("📚", "Thư viện bài tập");
		JButton btnUsers = createNavButton("👥", "Chi tiết người dùng");

		navBar.add(btnLibrary);
		navBar.add(btnUsers);

		btnLibrary.addActionListener(e -> cardLayout.show(cardPanel, "LIBRARY"));
		btnUsers.addActionListener(e -> {
			cardPanel.add(createUserListPanel(), "USERS");
			cardLayout.show(cardPanel, "USERS");
		});

		add(navBar, BorderLayout.SOUTH);
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

		List<User> users = controller.getUserList();
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
				JLabel nameLabel = new JLabel(u.getName() + " (@" + u.getUsername() + ")");
				nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
				JLabel goalLabel = new JLabel("Mục tiêu: " + u.getGoal().toString());

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
		userDetailsContainer.removeAll(); // Remove old content

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Color.WHITE);
		JButton btnBack = new JButton("⬅ Quay lại");
		btnBack.addActionListener(e -> cardLayout.show(cardPanel, "USERS"));
		headerPanel.add(btnBack, BorderLayout.WEST);
		headerPanel.add(new JLabel("Chi tiết: " + user.getName(), SwingConstants.CENTER), BorderLayout.CENTER);

		JTabbedPane userTabs = new JTabbedPane();
		userTabs.addTab("Hồ sơ", new JLabel("Giao diện Profile của " + user.getName()));
		userTabs.addTab("Thống kê", new JLabel("Biểu đồ của " + user.getName()));
		userDetailsContainer.add(headerPanel, BorderLayout.NORTH);
		userDetailsContainer.add(userTabs, BorderLayout.CENTER);

		userDetailsContainer.revalidate();
		userDetailsContainer.repaint();

		cardLayout.show(cardPanel, "USER_DETAILS");
	}

	private JPanel createAdminLibraryPanel() {
		return new ExerciseLibraryUI(library, admin, null, controller, null);
	}
}