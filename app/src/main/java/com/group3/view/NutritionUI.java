package com.group3.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.group3.controller.NutritionLogController;
import com.group3.model.NutritionLog;
import com.group3.model.User;

public class NutritionUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final Color PRIMARY = new Color(33, 150, 243);
	private static final Color SUCCESS = new Color(56, 142, 60);
	private static final Color SUCCESS_D = new Color(27, 94, 32);
	private static final Color WHITE = Color.WHITE;
	private static final Color BG = new Color(245, 245, 245);

	private NutritionLogController nutritionController;
	private User user;

	private JTextField txtSearchFood;
	private JButton btnSearch, btnAddFood;
	private JTable resultTable;
	private DefaultTableModel tableModel;
	private List<NutritionLog> currentListResults;

	public NutritionUI(NutritionLogController nutritionController, User user) {
		this.nutritionController = nutritionController;
		this.user = user;
		initComponents();
		setupEvents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setBackground(BG);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(PRIMARY);
		header.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
		JLabel lblTitle = new JLabel("Tra Cứu Dinh Dưỡng");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitle.setForeground(WHITE);
		JLabel lblSub = new JLabel("Tìm kiếm & ghi nhật ký bữa ăn");
		lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
		lblSub.setForeground(new Color(255, 255, 255, 180));
		JPanel stack = new JPanel();
		stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
		stack.setOpaque(false);
		stack.add(lblTitle);
		stack.add(Box.createRigidArea(new Dimension(0, 2)));
		stack.add(lblSub);
		header.add(stack, BorderLayout.CENTER);
		add(header, BorderLayout.NORTH);

		JPanel searchBar = new JPanel(new BorderLayout(8, 0));
		searchBar.setBackground(WHITE);
		searchBar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(12, 16, 12, 16)));

		txtSearchFood = new JTextField();
		txtSearchFood.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSearchFood.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
						BorderFactory.createEmptyBorder(8, 12, 8, 12)));

		btnSearch = new JButton("Tìm");
		btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
		btnSearch.setBackground(PRIMARY);
		btnSearch.setForeground(WHITE);
		btnSearch.setFocusPainted(false);
		btnSearch.setBorderPainted(false);
		btnSearch.setOpaque(true);
		btnSearch.setPreferredSize(new Dimension(64, 40));
		btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

		searchBar.add(txtSearchFood, BorderLayout.CENTER);
		searchBar.add(btnSearch, BorderLayout.EAST);

		String[] columns = { "Tên sản phẩm", "Kcal", "Protein (g)", "Carbs (g)", "Fat (g)" };
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		resultTable = new JTable(tableModel);
		resultTable.setFont(new Font("Arial", Font.PLAIN, 13));
		resultTable.setRowHeight(38);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		resultTable.setShowVerticalLines(false);
		resultTable.setGridColor(new Color(240, 240, 240));
		resultTable.setSelectionBackground(new Color(227, 242, 253));
		resultTable.setSelectionForeground(new Color(30, 30, 30));
		resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		resultTable.getTableHeader().setBackground(new Color(250, 250, 250));
		resultTable.getTableHeader().setForeground(new Color(100, 100, 100));

		resultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row,
					int col) {
				Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
				if (!sel)
					c.setBackground(row % 2 == 0 ? WHITE : new Color(250, 250, 250));
				return c;
			}
		});

		btnAddFood = new JButton("Thêm vào nhật ký");
		btnAddFood.setFont(new Font("Arial", Font.BOLD, 14));
		btnAddFood.setBackground(SUCCESS);
		btnAddFood.setForeground(WHITE);
		btnAddFood.setFocusPainted(false);
		btnAddFood.setBorderPainted(false);
		btnAddFood.setOpaque(true);
		btnAddFood.setEnabled(false);
		btnAddFood.setPreferredSize(new Dimension(0, 46));
		btnAddFood.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddFood.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				if (btnAddFood.isEnabled())
					btnAddFood.setBackground(SUCCESS_D);
			}

			public void mouseExited(java.awt.event.MouseEvent e) {
				btnAddFood.setBackground(SUCCESS);
			}
		});

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(WHITE);
		bottomPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 16, 10, 16)));
		bottomPanel.add(btnAddFood, BorderLayout.CENTER);

		JPanel body = new JPanel(new BorderLayout());
		body.add(searchBar, BorderLayout.NORTH);
		body.add(new JScrollPane(resultTable), BorderLayout.CENTER);
		body.add(bottomPanel, BorderLayout.SOUTH);

		add(body, BorderLayout.CENTER);
	}

	private void setupEvents() {
		btnSearch.addActionListener(e -> doSearch());
		txtSearchFood.addActionListener(e -> doSearch());

		btnAddFood.addActionListener(e -> {
			int selectedRow = resultTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm trong danh sách!");
				return;
			}
			NutritionLog selectedFood = currentListResults.get(selectedRow);
            
			NutritionLog logToSave = new NutritionLog.Builder()
                    .setUserID(user.getUserID())
                    .setlogID((int) (System.currentTimeMillis() % 1000000))
					.setProductID(selectedFood.getProductID())
                    .setProductName(selectedFood.getProductName())
					.setQuantity(selectedFood.getQuantity())
                    .setEnergy(selectedFood.getEnergy())
					.setProtein(selectedFood.getProtein())
                    .setFat(selectedFood.getFat())
					.setCarbohydrates(selectedFood.getCarbohydrates())
                    .build();

			if (nutritionController.addNutritionLog(logToSave)) {
				JOptionPane.showMessageDialog(this, "Đã thêm \"" + selectedFood.getProductName() + "\" vào nhật ký!",
						"Thành công", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Lỗi khi lưu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void doSearch() {
		String keyword = txtSearchFood.getText().trim();
		if (keyword.isEmpty())
			return;

		tableModel.setRowCount(0);
		btnAddFood.setEnabled(false);

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		SwingWorker<List<NutritionLog>, Void> worker = new SwingWorker<>() {
			@Override
			protected List<NutritionLog> doInBackground() {
				return nutritionController.lookupNutrition(keyword);
			}

			@Override
			protected void done() {
				setCursor(Cursor.getDefaultCursor());
				try {
					currentListResults = get();
					if (currentListResults != null && !currentListResults.isEmpty()) {
						for (NutritionLog log : currentListResults) {
							tableModel.addRow(new Object[] { log.getProductName(),
									log.getEnergy() != null ? log.getEnergy() : "0",
									log.getProtein() != null ? log.getProtein() : "0",
									log.getCarbohydrates() != null ? log.getCarbohydrates() : "0",
									log.getFat() != null ? log.getFat() : "0", });
						}
						btnAddFood.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(NutritionUI.this, "Không tìm thấy dữ liệu phù hợp!", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(NutritionUI.this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		worker.execute();
	}
}