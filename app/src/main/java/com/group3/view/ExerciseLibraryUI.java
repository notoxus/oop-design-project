package com.group3.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

import com.group3.controller.AdminController;
import com.group3.controller.ExerciseSuggestionService;
import com.group3.model.*;

public class ExerciseLibraryUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Color PRIMARY = new Color(33, 150, 243);
	private static final Color PRIMARY_DARK = new Color(21, 101, 192);
	private static final Color BG_PAGE = new Color(245, 245, 245);
	private static final Color WHITE = Color.WHITE;
	private static final Color CARD_BORDER = new Color(220, 220, 220);

	private ExerciseLibrary library;
	private IAccount currentAccount;
	private ExerciseSuggestionService suggestionService;
	private AdminController adminController;
	private DashboardUI dashboardUI;

	private JPanel cardsContainer;
	private JToggleButton[] filterBtns;
	private String[] filterValues = { "TẤT CẢ", "COMPOUND", "ISOLATE", "CARDIO", "FLEXIBILITY" };
	private String activeFilter = "TẤT CẢ";

	private JButton btnSuggest;

	public ExerciseLibraryUI(ExerciseLibrary library, IAccount account, ExerciseSuggestionService suggestionService,
			AdminController adminController, DashboardUI dashboardUI) {
		this.library = library;
		this.currentAccount = account;
		this.suggestionService = suggestionService;
		this.adminController = adminController;
		this.dashboardUI = dashboardUI;

		initComponents();
		setupEvents();
		loadCards("TẤT CẢ");
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setBackground(BG_PAGE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(PRIMARY);
		header.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

		JLabel lblTitle = new JLabel("Thư Viện Bài Tập");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitle.setForeground(WHITE);

		JLabel lblSub = new JLabel("Chọn bài tập để bắt đầu");
		lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
		lblSub.setForeground(new Color(255, 255, 255, 180));

		JPanel titleStack = new JPanel();
		titleStack.setLayout(new BoxLayout(titleStack, BoxLayout.Y_AXIS));
		titleStack.setOpaque(false);
		titleStack.add(lblTitle);
		titleStack.add(Box.createRigidArea(new Dimension(0, 2)));
		titleStack.add(lblSub);

		header.add(titleStack, BorderLayout.CENTER);
		add(header, BorderLayout.NORTH);

		JPanel chipBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
		chipBar.setBackground(WHITE);
		chipBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CARD_BORDER));

		filterBtns = new JToggleButton[filterValues.length];
		for (int i = 0; i < filterValues.length; i++) {
			JToggleButton btn = new JToggleButton(filterValues[i]);
			btn.setFont(new Font("Arial", Font.PLAIN, 13));
			btn.setFocusPainted(false);
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btn.setRolloverEnabled(false); 
            btn.setContentAreaFilled(false);
			styleChip(btn, false);
			filterBtns[i] = btn;
			chipBar.add(btn);
		}
		styleChip(filterBtns[0], true);

		cardsContainer = new JPanel();
		cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
		cardsContainer.setBackground(BG_PAGE);

		JScrollPane scrollPane = new JScrollPane(cardsContainer);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(WHITE);
		bottomPanel
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, CARD_BORDER),
						BorderFactory.createEmptyBorder(10, 16, 10, 16)));

		JPanel center = new JPanel(new BorderLayout());
		center.setOpaque(false);
		center.add(chipBar, BorderLayout.NORTH);
		center.add(scrollPane, BorderLayout.CENTER);

		add(center, BorderLayout.CENTER);

		if (currentAccount instanceof User) {
			btnSuggest = new JButton("Đề xuất bài tập phù hợp");
			btnSuggest.setFont(new Font("Arial", Font.BOLD, 14));
			btnSuggest.setBackground(PRIMARY);
			btnSuggest.setForeground(WHITE);
			btnSuggest.setFocusPainted(false);
			btnSuggest.setBorderPainted(false);
			btnSuggest.setOpaque(true);
			btnSuggest.setPreferredSize(new Dimension(0, 46));
			btnSuggest.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnSuggest.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btnSuggest.setBackground(PRIMARY_DARK);
				}

				public void mouseExited(MouseEvent e) {
					btnSuggest.setBackground(PRIMARY);
				}
			});
			bottomPanel.add(btnSuggest, BorderLayout.CENTER);
			add(bottomPanel, BorderLayout.SOUTH);
		} else if (currentAccount instanceof Admin) {
			JButton btnAdd = new JButton("Thêm bài tập mới");
			btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
			btnAdd.setBackground(new Color(0, 153, 76));
			btnAdd.setForeground(Color.BLACK);
			btnAdd.setFocusPainted(false);
			btnAdd.setPreferredSize(new Dimension(0, 46));
			btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));

			btnAdd.addActionListener(e -> showAddExerciseDialog());

			bottomPanel.add(btnAdd, BorderLayout.CENTER);
			add(bottomPanel, BorderLayout.SOUTH);
		}
	}

	private void showAddExerciseDialog() {
		JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Bài Tập Mới", true);
		dialog.setSize(400, 350);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JTextField txtName = new JTextField();
		JComboBox<ExerciseCategory> cbCategory = new JComboBox<>(ExerciseCategory.values());
		JComboBox<TrackingType> cbTracking = new JComboBox<>(TrackingType.values());
		JTextField txtMuscle = new JTextField();

		formPanel.add(new JLabel("Tên bài tập:"));
		formPanel.add(txtName);
		formPanel.add(new JLabel("Nhóm cơ mục tiêu:"));
		formPanel.add(txtMuscle);
		formPanel.add(new JLabel("Thể loại:"));
		formPanel.add(cbCategory);
		formPanel.add(new JLabel("Đơn vị theo dõi:"));
		formPanel.add(cbTracking);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JButton btnCancel = new JButton("Hủy");
		JButton btnSave = new JButton("Lưu Bài Tập");
		btnSave.setBackground(PRIMARY);
		btnSave.setForeground(WHITE);

		btnCancel.addActionListener(e -> dialog.dispose());
		btnSave.addActionListener(e -> {
			String name = txtName.getText().trim();
			String muscle = txtMuscle.getText().trim();
			if (name.isEmpty() || muscle.isEmpty()) {
				JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ Tên bài tập và Nhóm cơ!");
				return;
			}
			int newId = (int) (System.currentTimeMillis() % 100000);

			if (adminController.addExercise(newId, name, (ExerciseCategory) cbCategory.getSelectedItem(),
					(TrackingType) cbTracking.getSelectedItem(), muscle)) {
				JOptionPane.showMessageDialog(dialog, "Đã thêm bài tập thành công!");
				dialog.dispose();
				loadCards(activeFilter);
			}
		});

		btnPanel.add(btnCancel);
		btnPanel.add(btnSave);

		dialog.add(formPanel, BorderLayout.CENTER);
		dialog.add(btnPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void styleChip(JToggleButton btn, boolean active) {
        btn.setOpaque(true);
        btn.setBackground(WHITE);
        btn.setForeground(Color.BLACK);

        if (active) {
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1, true),
                BorderFactory.createEmptyBorder(5, 13, 5, 13)
            ));
        } else {
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                BorderFactory.createEmptyBorder(5, 13, 5, 13)
            ));
        }
    }

	private void setupEvents() {
		for (int i = 0; i < filterBtns.length; i++) {
			final String val = filterValues[i];
			final JToggleButton btn = filterBtns[i];
			btn.addActionListener(e -> {
				activeFilter = val;
				for (JToggleButton fb : filterBtns)
					styleChip(fb, false);
				styleChip(btn, true);
				loadCards(val);
			});
		}

		if (btnSuggest != null) {
			btnSuggest.addActionListener(e -> {
				if (!(currentAccount instanceof User))
					return;
				List<Exercise> suggested = suggestionService.suggest((User) currentAccount, library);
				if (suggested == null || suggested.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Không có đủ bài tập để đề xuất lúc này!");
					return;
				}
				StringBuilder sb = new StringBuilder(
						"Đề xuất cho mục tiêu " + ((User) currentAccount).getGoal() + ":\n\n");
				for (int i = 0; i < suggested.size(); i++) {
					sb.append(i + 1).append(". ").append(suggested.get(i).getExerciseName()).append("\n");
				}
				JOptionPane.showMessageDialog(this, sb.toString(), "Gợi ý bài tập", JOptionPane.INFORMATION_MESSAGE);
			});
		}
	}

	private void loadCards(String filter) {
		cardsContainer.removeAll();
		if (library.getLib() == null)
			return;

		cardsContainer.add(Box.createRigidArea(new Dimension(0, 8)));
		for (Exercise ex : library.getLib()) {
			if (filter.equals("TẤT CẢ") || ex.getCategory().name().equals(filter)) {
				cardsContainer.add(createExerciseCard(ex));
				cardsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
			}
		}

		cardsContainer.revalidate();
		cardsContainer.repaint();
	}

	private JPanel createExerciseCard(Exercise ex) {
		JPanel card = new JPanel(new BorderLayout(12, 0));
		card.setBackground(WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(238, 238, 238)),
				BorderFactory.createEmptyBorder(14, 16, 14, 16)));
		card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		card.setCursor(new Cursor(Cursor.HAND_CURSOR));

		String emoji = "🏋️";
		Color iconBg = new Color(227, 242, 253);
		if (ex.getCategory() == ExerciseCategory.CARDIO) {
			emoji = "🏃";
			iconBg = new Color(232, 245, 233);
		} else if (ex.getCategory() == ExerciseCategory.COMPOUND) {
			emoji = "🔥";
			iconBg = new Color(255, 243, 224);
		} else if (ex.getCategory() == ExerciseCategory.FLEXIBILITY) {
			emoji = "🧘";
			iconBg = new Color(255, 235, 238);
		}

		JLabel lblIcon = new JLabel(emoji, SwingConstants.CENTER);
		lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
		lblIcon.setOpaque(true);
		lblIcon.setBackground(iconBg);
		lblIcon.setPreferredSize(new Dimension(46, 46));
		lblIcon.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1, true));
		card.add(lblIcon, BorderLayout.WEST);

		JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 3));
		infoPanel.setOpaque(false);

		JLabel lblName = new JLabel(ex.getExerciseName());
		lblName.setFont(new Font("Arial", Font.BOLD, 14));
		lblName.setForeground(new Color(30, 30, 30));

		JLabel lblTarget = new JLabel(ex.getCategory() + "  ·  " + ex.getTargetMuscle());
		lblTarget.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTarget.setForeground(new Color(120, 120, 120));

		infoPanel.add(lblName);
		infoPanel.add(lblTarget);
		card.add(infoPanel, BorderLayout.CENTER);

		if (currentAccount instanceof Admin) {
			JButton btnDel = new JButton("🗑");
			btnDel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
			btnDel.setBorderPainted(false);
			btnDel.setContentAreaFilled(false);
			btnDel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnDel.setToolTipText("Xóa bài tập");
			btnDel.addActionListener(e -> {
				int confirm = JOptionPane.showConfirmDialog(this, "Xóa bài tập: " + ex.getExerciseName() + "?",
						"Xác nhận", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION && adminController.deleteExercise(ex.getExerciseName())) {
					JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
					loadCards(activeFilter);
				}
			});
			card.add(btnDel, BorderLayout.EAST);
		} else {
			JLabel chevron = new JLabel("›");
			chevron.setFont(new Font("Arial", Font.PLAIN, 22));
			chevron.setForeground(new Color(180, 180, 180));
			card.add(chevron, BorderLayout.EAST);
		}

		card.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				card.setBackground(new Color(250, 250, 250));
			}

			public void mouseExited(MouseEvent e) {
				card.setBackground(WHITE);
			}

			public void mouseClicked(MouseEvent e) {
				if (currentAccount instanceof User && dashboardUI != null) {
					dashboardUI.navigateToExerciseInput(ex);
				}
			}
		});

		return card;
	}
}