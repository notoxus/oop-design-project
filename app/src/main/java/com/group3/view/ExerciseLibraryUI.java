package com.group3.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.group3.controller.AdminController;
import com.group3.controller.ExerciseSuggestionService;
import com.group3.model.Admin;
import com.group3.model.Exercise;
import com.group3.model.ExerciseCategory;
import com.group3.model.ExerciseLibrary;
import com.group3.model.IAccount;
import com.group3.model.JsonCategoryDatabase;
import com.group3.model.Observer;
import com.group3.model.TrackingType;
import com.group3.model.User;

public class ExerciseLibraryUI extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private static final Color PRIMARY = new Color(33, 150, 243);
	private static final Color PRIMARY_DARK = new Color(21, 101, 192);
	private static final Color SUGGEST_COLOR = new Color(0, 150, 136);
	private static final Color SUGGEST_COLOR_DARK = new Color(0, 121, 107);
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
	private String[] filterValues;
	private String activeFilter = "TẤT CẢ";
	private List<ExerciseCategory> rootCategories;
	private JButton btnSuggest;
	private boolean isSuggestMode = false;
	private List<Exercise> currentSuggestions;

	public ExerciseLibraryUI(ExerciseLibrary library, IAccount account, ExerciseSuggestionService suggestionService,
			AdminController adminController, DashboardUI dashboardUI) {
		this.library = library;
		this.currentAccount = account;
		this.suggestionService = suggestionService;
		this.adminController = adminController;
		this.dashboardUI = dashboardUI;
		this.library.add(this);
		initComponents();
		setupEvents();
		renderCards();
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
		rootCategories = new JsonCategoryDatabase().loadData();
		filterValues = new String[rootCategories.size() + 1];
		filterValues[0] = "TẤT CẢ";
		for (int i = 0; i < rootCategories.size(); i++) {
		    filterValues[i + 1] = rootCategories.get(i).getCatName().toUpperCase();
		}
		JPanel chipBar = new JPanel();
		chipBar.setLayout(new BoxLayout(chipBar, BoxLayout.X_AXIS));
		chipBar.setBackground(WHITE);
		chipBar.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

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
			if (i < filterValues.length - 1) {
				chipBar.add(Box.createRigidArea(new Dimension(8, 0)));
			}
		}
		styleChip(filterBtns[0], true);

		cardsContainer = new JPanel();
		cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
		cardsContainer.setBackground(BG_PAGE);
		JScrollPane tabScroll = new JScrollPane(chipBar);
		// Horizontal scroll bar
		tabScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		tabScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CARD_BORDER));
		tabScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		MouseAdapter dragListener = new MouseAdapter() {
			private Point origin;

			@Override
			public void mousePressed(MouseEvent e) {
				origin = e.getPoint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (origin != null) {
					JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, chipBar);
					if (viewPort != null) {
						int deltaX = origin.x - e.getX();
						Rectangle view = viewPort.getViewRect();
						view.x += deltaX;
						chipBar.scrollRectToVisible(view);
					}
				}
			}
		};
		chipBar.addMouseListener(dragListener);
		chipBar.addMouseMotionListener(dragListener);
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
		center.add(tabScroll, BorderLayout.NORTH);
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
					btnSuggest.setBackground(isSuggestMode ? SUGGEST_COLOR_DARK : PRIMARY_DARK);
				}

				public void mouseExited(MouseEvent e) {
					btnSuggest.setBackground(isSuggestMode ? SUGGEST_COLOR : PRIMARY);
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

	@Override
	public void update() {
		renderCards();
	}

	private void styleChip(JToggleButton btn, boolean active) {
		btn.setOpaque(true);
		btn.setBackground(WHITE);
		btn.setForeground(Color.BLACK);

		if (active) {
			btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(PRIMARY, 1, true),
					BorderFactory.createEmptyBorder(5, 13, 5, 13)));
		} else {
			btn.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
					BorderFactory.createEmptyBorder(5, 13, 5, 13)));
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
				renderCards();
			});
		}

		if (btnSuggest != null) {
			btnSuggest.addActionListener(e -> {
				isSuggestMode = !isSuggestMode;

				if (isSuggestMode) {
					btnSuggest.setText("Danh sách tất cả bài tập");
					btnSuggest.setBackground(SUGGEST_COLOR);
					currentSuggestions = suggestionService.suggest((User) currentAccount, library);
				} else {
					btnSuggest.setText("Danh sách bài tập đề xuất");
					btnSuggest.setBackground(PRIMARY);
					currentSuggestions = null;
				}
				renderCards();
			});
		}
	}
	
	private void renderCards() {
		cardsContainer.removeAll();

		if (isSuggestMode) {
			renderSuggestMode();
		} else {
			renderNormalMode();
		}

		cardsContainer.revalidate();
		cardsContainer.repaint();
	}

	private boolean isMatchFilter(Exercise ex, String filter) {
		if (filter.equals("TẤT CẢ"))
			return true;

		if (rootCategories != null) {
			for (ExerciseCategory root : rootCategories) {
				if (root.getCatName().toUpperCase().equals(filter)) {
					if (root.getSubCat() != null) {
						for (ExerciseCategory sub : root.getSubCat()) {
							if (sub.getCatID() == ex.getCategory().getCatID()) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private void renderNormalMode() {
		if (library.getLib() == null || library.getLib().isEmpty())
			return;

		cardsContainer.add(Box.createRigidArea(new Dimension(0, 8)));
		for (Exercise ex : library.getLib()) {
			if (isMatchFilter(ex, activeFilter)) {
			    cardsContainer.add(createExerciseCard(ex));
				cardsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
			}
		}
	}

	private void renderSuggestMode() {
		if (currentSuggestions == null || currentSuggestions.isEmpty()) {
			JLabel lblEmpty = new JLabel("Không có đủ bài tập để đề xuất lúc này!");
			lblEmpty.setAlignmentX(Component.CENTER_ALIGNMENT);
			cardsContainer.add(Box.createRigidArea(new Dimension(0, 20)));
			cardsContainer.add(lblEmpty);
			return;
		}

		cardsContainer.add(Box.createRigidArea(new Dimension(0, 15)));

		User u = (User) currentAccount;
		JLabel lblMsg = new JLabel(
				"<html><div style='text-align: center; width: 300px;'>Danh sách đề xuất theo thuật toán cho mục tiêu <b>"
						+ u.getGoal() + "</b>:</div></html>");
		lblMsg.setFont(new Font("Arial", Font.PLAIN, 14));
		lblMsg.setForeground(new Color(80, 80, 80));
		lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
		cardsContainer.add(lblMsg);
		cardsContainer.add(Box.createRigidArea(new Dimension(0, 20)));

		List<Exercise> filteredSuggestions = currentSuggestions.stream()
				.filter(ex -> isMatchFilter(ex, activeFilter))
				.collect(Collectors.toList());

		if (filteredSuggestions.isEmpty()) {
			JLabel lblEmpty2 = new JLabel(
					"Không có bài tập nào thuộc thể loại " + activeFilter + " trong danh sách đề xuất.");
			lblEmpty2.setAlignmentX(Component.CENTER_ALIGNMENT);
			cardsContainer.add(lblEmpty2);
			return;
		}

		Map<ExerciseCategory, List<Exercise>> groupedExercises = filteredSuggestions.stream()
				.collect(Collectors.groupingBy(Exercise::getCategory));

		for (ExerciseCategory cat : groupedExercises.keySet()) {
			List<Exercise> list = groupedExercises.get(cat);

			if (list != null && !list.isEmpty()) {
				JLabel lblHeader = new JLabel(cat.getCatName() + " (" + list.size() + " bài)");
				lblHeader.setFont(new Font("Arial", Font.BOLD, 14));
				lblHeader.setForeground(PRIMARY_DARK);

				JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
				headerPanel.setOpaque(false);
				headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
				headerPanel.add(lblHeader);

				cardsContainer.add(headerPanel);
				cardsContainer.add(Box.createRigidArea(new Dimension(0, 8)));

				for (Exercise ex : list) {
					cardsContainer.add(createExerciseCard(ex));
					cardsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
				}
				cardsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
			}
		}

		for (ExerciseCategory cat : groupedExercises.keySet()) {
			List<Exercise> list = groupedExercises.get(cat);
			if (list != null && !list.isEmpty()) {
				JLabel lblHeader = new JLabel(cat.getCatName());
				lblHeader.setFont(new Font("Arial", Font.BOLD, 14));
				lblHeader.setForeground(PRIMARY_DARK);

				JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
				headerPanel.setOpaque(false);
				headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
				headerPanel.add(lblHeader);

				cardsContainer.add(headerPanel);
				cardsContainer.add(Box.createRigidArea(new Dimension(0, 8)));

				for (Exercise ex : list) {
					cardsContainer.add(createExerciseCard(ex));
					cardsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
				}
				cardsContainer.add(Box.createRigidArea(new Dimension(0, 10)));
			}
		}
	}

	private JPanel createExerciseCard(Exercise ex) {
		JPanel card = new JPanel(new BorderLayout(12, 0));
		card.setBackground(WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(238, 238, 238)),
				BorderFactory.createEmptyBorder(14, 16, 14, 16)));
		card.setMaximumSize(new Dimension(370, 80));
		card.setPreferredSize(new Dimension(370, 80));
		card.setCursor(new Cursor(Cursor.HAND_CURSOR));

		String iconPath = "icons/muscle.png";
		Color iconBg = new Color(227, 242, 253);
		TrackingType type = ex.getTrackingType();
		if (type == TrackingType.DISTANCE_TIME) {
			iconPath = "icons/cardio.png";
			iconBg = new Color(232, 245, 233);
		} else if (type == TrackingType.TIME_ONLY) {
			iconPath = "icons/flex.png";
			iconBg = new Color(255, 235, 238);
		}
		JLabel lblIcon = new JLabel("", SwingConstants.CENTER);
		lblIcon.setOpaque(true);
		lblIcon.setBackground(iconBg);
		lblIcon.setPreferredSize(new Dimension(46, 46));
		lblIcon.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 15), 1, true));
		try {
			ImageIcon originalIcon = new ImageIcon(iconPath);
			Image scaledImage = originalIcon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
			lblIcon.setIcon(new ImageIcon(scaledImage));
		} catch (Exception e) {
		}
		card.add(lblIcon, BorderLayout.WEST);

		JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 3));
		infoPanel.setOpaque(false);

		JLabel lblName = new JLabel(ex.getExerciseName());
		lblName.setFont(new Font("Arial", Font.BOLD, 14));
		lblName.setForeground(new Color(30, 30, 30));

		JLabel lblTarget = new JLabel(ex.getTargetMuscle());
		lblTarget.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTarget.setForeground(new Color(120, 120, 120));

		infoPanel.add(lblName);
		infoPanel.add(lblTarget);
		card.add(infoPanel, BorderLayout.CENTER);

		if (currentAccount instanceof Admin) {
			JPanel actionPanel = new JPanel(new GridLayout(1, 2, 5, 0));
			actionPanel.setOpaque(false);
			actionPanel.setPreferredSize(new Dimension(100, 40));
			JButton btnEdit = new JButton("✏️");
			btnEdit.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
			btnEdit.setBorderPainted(false);
			btnEdit.setContentAreaFilled(false);
			btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnEdit.setToolTipText("Sửa bài tập");
			btnEdit.setMargin(new Insets(0, 0, 0, 0));
			JButton btnDel = new JButton("🗑");
			btnDel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
			btnDel.setBorderPainted(false);
			btnDel.setContentAreaFilled(false);
			btnDel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btnDel.setToolTipText("Xóa bài tập");
			btnDel.setMargin(new Insets(0, 0, 0, 0));
			btnEdit.addActionListener(e -> showEditExerciseDialog(ex));
			btnDel.addActionListener(e -> {
				int confirm = JOptionPane.showConfirmDialog(this, "Xóa bài tập: " + ex.getExerciseName() + "?",
						"Xác nhận", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION && adminController.deleteExercise(ex.getExerciseName())) {
					JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
					renderCards();
				}
			});

			actionPanel.add(btnEdit);
			actionPanel.add(btnDel);
			card.add(actionPanel, BorderLayout.EAST);
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

	private void showAddExerciseDialog() {
		JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Bài Tập Mới", true);
		dialog.setSize(400, 400);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JTextField txtName = new JTextField();
		JTextField txtMuscle = new JTextField();
		JComboBox<ExerciseCategory> cbParentCategory = new JComboBox<>();
		JComboBox<ExerciseCategory> cbSubCategory = new JComboBox<>();
		JComboBox<TrackingType> cbTracking = new JComboBox<>();

		List<ExerciseCategory> rootCategories = new JsonCategoryDatabase().loadData();
		if (rootCategories != null) {
			for (ExerciseCategory rootCat : rootCategories) {
				cbParentCategory.addItem(rootCat);
			}
		}

		cbParentCategory.addActionListener(e -> {
			ExerciseCategory selectedParent = (ExerciseCategory) cbParentCategory.getSelectedItem();
			cbSubCategory.removeAllItems();

			if (selectedParent != null && selectedParent.getSubCat() != null) {
				for (ExerciseCategory sub : selectedParent.getSubCat()) {
					cbSubCategory.addItem(sub);
				}
			}
		});

		if (cbParentCategory.getItemCount() > 0) {
			cbParentCategory.setSelectedIndex(0);
		}

		cbSubCategory.addActionListener(e -> {
			ExerciseCategory selectedSub = (ExerciseCategory) cbSubCategory.getSelectedItem();
			cbTracking.removeAllItems();
			if (selectedSub != null && selectedSub.getAllowedTrackingType() != null) {
				for (TrackingType type : selectedSub.getAllowedTrackingType()) {
					cbTracking.addItem(type);
				}
			}
		});

		formPanel.add(new JLabel("Tên bài tập:"));
		formPanel.add(txtName);
		formPanel.add(new JLabel("Nhóm cơ mục tiêu:"));
		formPanel.add(txtMuscle);
		formPanel.add(new JLabel("Thể loại:"));
		formPanel.add(cbParentCategory);
		formPanel.add(new JLabel("Hình thức tập:"));
		formPanel.add(cbSubCategory);
		formPanel.add(new JLabel("Đơn vị theo dõi:"));
		formPanel.add(cbTracking);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JButton btnCancel = new JButton("Hủy");
		JButton btnSave = new JButton("Lưu Bài Tập");
		btnCancel.setBackground(PRIMARY);
		btnCancel.setForeground(Color.BLACK);
		btnSave.setBackground(PRIMARY);
		btnSave.setForeground(Color.BLACK);

		btnCancel.addActionListener(e -> dialog.dispose());
		btnSave.addActionListener(e -> {
			String name = txtName.getText().trim();
			String muscle = txtMuscle.getText().trim();
			ExerciseCategory finalCategory = (ExerciseCategory) cbSubCategory.getSelectedItem();

			TrackingType selectedTracking = (TrackingType) cbTracking.getSelectedItem();

			if (name.isEmpty() || muscle.isEmpty()) {
				JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ Tên bài tập và Nhóm cơ!");
				return;
			}
			if (finalCategory == null) {
				JOptionPane.showMessageDialog(dialog, "Vui lòng chọn Hình thức tập cụ thể!");
				return;
			}
			int newId = (int) (System.currentTimeMillis() % 100000);

			adminController.addExercise(newId, name, finalCategory, selectedTracking, muscle);

			JOptionPane.showMessageDialog(dialog, "Đã thêm bài tập thành công!");
			dialog.dispose();
		});

		btnPanel.add(btnCancel);
		btnPanel.add(btnSave);
		dialog.add(formPanel, BorderLayout.CENTER);
		dialog.add(btnPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}
	private void showEditExerciseDialog(Exercise targetEx) {
		JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa Bài Tập", true);
		dialog.setSize(420, 400);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JTextField txtName = new JTextField(targetEx.getExerciseName());
		JTextField txtMuscle = new JTextField(targetEx.getTargetMuscle());
		JComboBox<ExerciseCategory> cbParentCategory = new JComboBox<>();
		JComboBox<ExerciseCategory> cbSubCategory = new JComboBox<>();
		JComboBox<TrackingType> cbTracking = new JComboBox<>();

		List<ExerciseCategory> rootCategories = new JsonCategoryDatabase().loadData();
		ExerciseCategory currentParent = null;

		if (rootCategories != null) {
			for (ExerciseCategory rootCat : rootCategories) {
				cbParentCategory.addItem(rootCat);
				if (rootCat.getSubCat() != null && rootCat.getSubCat().contains(targetEx.getCategory())) {
					currentParent = rootCat;
				}
			}
		}

		cbParentCategory.addActionListener(e -> {
			ExerciseCategory selectedParent = (ExerciseCategory) cbParentCategory.getSelectedItem();
			cbSubCategory.removeAllItems();
			if (selectedParent != null && selectedParent.getSubCat() != null) {
				for (ExerciseCategory sub : selectedParent.getSubCat()) {
					cbSubCategory.addItem(sub);
				}
			}
		});

		cbSubCategory.addActionListener(e -> {
			ExerciseCategory selectedSub = (ExerciseCategory) cbSubCategory.getSelectedItem();
			cbTracking.removeAllItems();
			if (selectedSub != null && selectedSub.getAllowedTrackingType() != null) {
				for (TrackingType type : selectedSub.getAllowedTrackingType()) {
					cbTracking.addItem(type);
				}
			}
		});

		if (currentParent != null) {
			cbParentCategory.setSelectedItem(currentParent);
			cbSubCategory.setSelectedItem(targetEx.getCategory());
			cbTracking.setSelectedItem(targetEx.getTrackingType());
		}

		formPanel.add(new JLabel("Tên bài tập:"));
		formPanel.add(txtName);
		formPanel.add(new JLabel("Nhóm cơ mục tiêu:"));
		formPanel.add(txtMuscle);
		formPanel.add(new JLabel("Thể loại:"));
		formPanel.add(cbParentCategory);
		formPanel.add(new JLabel("Hình thức tập:"));
		formPanel.add(cbSubCategory);
		formPanel.add(new JLabel("Đơn vị theo dõi:"));
		formPanel.add(cbTracking);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JButton btnCancel = new JButton("Hủy");
		JButton btnSave = new JButton("Cập Nhật");
		btnCancel.setBackground(PRIMARY); btnCancel.setForeground(Color.BLACK);
		btnSave.setBackground(PRIMARY); btnSave.setForeground(Color.BLACK);

		btnCancel.addActionListener(e -> dialog.dispose());
		btnSave.addActionListener(e -> {
			String name = txtName.getText().trim();
			String muscle = txtMuscle.getText().trim();
			ExerciseCategory finalCategory = (ExerciseCategory) cbSubCategory.getSelectedItem();
			TrackingType selectedTracking = (TrackingType) cbTracking.getSelectedItem();
			if (name.isEmpty() || muscle.isEmpty() || finalCategory == null) {
				JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin!");
				return;
			}
			if (adminController.updateExercise(targetEx, name, finalCategory, selectedTracking, muscle)) {
				JOptionPane.showMessageDialog(dialog, "Đã cập nhật bài tập thành công!");
				dialog.dispose();
			}
		});

		btnPanel.add(btnCancel);
		btnPanel.add(btnSave);
		dialog.add(formPanel, BorderLayout.CENTER);
		dialog.add(btnPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}
}