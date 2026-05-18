package com.group3.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.group3.controller.RegisterManager;
import com.group3.model.User;
import com.group3.model.WorkoutGoal;

public class RegisterForm extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private RegisterManager registerManager;

	private JTextField txtName, txtUsername;
	private JPasswordField txtPassword;
	private JTextField txtAge, txtHeight, txtWeight;
	private JRadioButton rdoMale, rdoFemale;
	private ButtonGroup bgGender;
	private JComboBox<WorkoutGoal> cbGoal;
	private JButton btnRegister, btnCancel;

	private static final Color PRIMARY = new Color(33, 150, 243);
	private static final Color PRIMARY_D = new Color(21, 101, 192);
	private static final Color WHITE = Color.WHITE;

	public RegisterForm(MainFrame mainFrame, RegisterManager registerManager) {
		this.mainFrame = mainFrame;
		this.registerManager = registerManager;
		initComponents();
		setupEvents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setBackground(WHITE);

		// [FIX] Top header bar with back button (mirrors Android pattern)
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(PRIMARY);
		headerPanel.setPreferredSize(new Dimension(400, 56));
		headerPanel.setBorder(new EmptyBorder(0, 12, 0, 12));

		JButton btnBack = new JButton("← Quay lại");
		btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
		btnBack.setForeground(WHITE);
		btnBack.setBackground(PRIMARY);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setOpaque(false);
		btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBack.addActionListener(e -> mainFrame.showLoginScreen());

		JLabel lblTitle = new JLabel("Tạo tài khoản", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 17));
		lblTitle.setForeground(WHITE);

		headerPanel.add(btnBack, BorderLayout.WEST);
		headerPanel.add(lblTitle, BorderLayout.CENTER);
		add(headerPanel, BorderLayout.NORTH);

		// [FIX] Scrollable form body
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBackground(WHITE);
		formPanel.setBorder(new EmptyBorder(20, 24, 20, 24));

		// Name
		formPanel.add(makeLabel("Họ và tên"));
		formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		txtName = makeField("Nguyễn Văn A");
		formPanel.add(txtName);

		formPanel.add(Box.createRigidArea(new Dimension(0, 14)));

		// Username
		formPanel.add(makeLabel("Tên đăng nhập"));
		formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		txtUsername = makeField("username");
		formPanel.add(txtUsername);

		formPanel.add(Box.createRigidArea(new Dimension(0, 14)));

		// Password
		formPanel.add(makeLabel("Mật khẩu"));
		formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		txtPassword = new JPasswordField();
		styleField(txtPassword);
		formPanel.add(txtPassword);

		formPanel.add(Box.createRigidArea(new Dimension(0, 14)));

		JPanel rowPanel = new JPanel(new GridLayout(1, 2, 12, 0));
		rowPanel.setOpaque(false);
		rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel agePanel = new JPanel();
		agePanel.setLayout(new BoxLayout(agePanel, BoxLayout.Y_AXIS));
		agePanel.setOpaque(false);
		agePanel.add(makeLabel("Tuổi"));
		agePanel.add(Box.createRigidArea(new Dimension(0, 5)));
		txtAge = new JTextField();
		styleField(txtAge);
		agePanel.add(txtAge);

		JPanel genderPanel = new JPanel();
		genderPanel.setLayout(new BoxLayout(genderPanel, BoxLayout.Y_AXIS));
		genderPanel.setOpaque(false);
		genderPanel.add(makeLabel("Giới tính"));
		genderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		JPanel rdoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		rdoPanel.setOpaque(false);
		rdoMale = new JRadioButton("Nam");
		rdoMale.setSelected(true);
		rdoMale.setOpaque(false);
		rdoFemale = new JRadioButton("Nữ");
		rdoFemale.setOpaque(false);
		rdoMale.setFont(new Font("Arial", Font.PLAIN, 14));
		rdoFemale.setFont(new Font("Arial", Font.PLAIN, 14));
		bgGender = new ButtonGroup();
		bgGender.add(rdoMale);
		bgGender.add(rdoFemale);
		rdoPanel.add(rdoMale);
		rdoPanel.add(rdoFemale);
		genderPanel.add(rdoPanel);

		rowPanel.add(agePanel);
		rowPanel.add(genderPanel);
		formPanel.add(rowPanel);

		formPanel.add(Box.createRigidArea(new Dimension(0, 14)));

		JPanel hwRow = new JPanel(new GridLayout(1, 2, 12, 0));
		hwRow.setOpaque(false);
		hwRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
		hwRow.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel heightPanel = new JPanel();
		heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.Y_AXIS));
		heightPanel.setOpaque(false);
		heightPanel.add(makeLabel("Chiều cao (cm)"));
		heightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		txtHeight = new JTextField();
		styleField(txtHeight);
		heightPanel.add(txtHeight);

		JPanel weightPanel = new JPanel();
		weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));
		weightPanel.setOpaque(false);
		weightPanel.add(makeLabel("Cân nặng (kg)"));
		weightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		txtWeight = new JTextField();
		styleField(txtWeight);
		weightPanel.add(txtWeight);

		hwRow.add(heightPanel);
		hwRow.add(weightPanel);
		formPanel.add(hwRow);

		formPanel.add(Box.createRigidArea(new Dimension(0, 14)));

		formPanel.add(makeLabel("Mục tiêu tập luyện"));
		formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		cbGoal = new JComboBox<>(WorkoutGoal.values());
		cbGoal.setFont(new Font("Arial", Font.PLAIN, 14));
		cbGoal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
		cbGoal.setAlignmentX(Component.LEFT_ALIGNMENT);
		formPanel.add(cbGoal);

		formPanel.add(Box.createRigidArea(new Dimension(0, 28)));

		btnRegister = new JButton("Xác nhận đăng ký");
		btnRegister.setFont(new Font("Arial", Font.BOLD, 15));
		btnRegister.setBackground(PRIMARY);
		btnRegister.setForeground(WHITE);
		btnRegister.setFocusPainted(false);
		btnRegister.setBorderPainted(false);
		btnRegister.setOpaque(true);
		btnRegister.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
		btnRegister.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRegister.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnRegister.setBackground(PRIMARY_D);
			}

			public void mouseExited(MouseEvent e) {
				btnRegister.setBackground(PRIMARY);
			}
		});
		formPanel.add(btnRegister);

		btnCancel = new JButton();

		JScrollPane scrollPane = new JScrollPane(formPanel);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane, BorderLayout.CENTER);
	}

	private JLabel makeLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font("Arial", Font.PLAIN, 13));
		lbl.setForeground(new Color(100, 100, 100));
		lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		return lbl;
	}

	private JTextField makeField(String placeholder) {
		JTextField f = new JTextField();
		styleField(f);
		return f;
	}

	private void styleField(JTextField f) {
		f.setFont(new Font("Arial", Font.PLAIN, 15));
		f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
		f.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true), new EmptyBorder(8, 12, 8, 12)));
		f.setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	private void setupEvents() {
		btnCancel.addActionListener(e -> mainFrame.showLoginScreen());
		btnRegister.addActionListener(e -> {
			try {
				String name = txtName.getText().trim();
				String username = txtUsername.getText().trim();
				String password = new String(txtPassword.getPassword());
				int age = Integer.parseInt(txtAge.getText().trim());
				double height = Double.parseDouble(txtHeight.getText().trim());
				double weight = Double.parseDouble(txtWeight.getText().trim());
				String gender = rdoMale.isSelected() ? "Nam" : "Nữ";
				WorkoutGoal goal = (WorkoutGoal) cbGoal.getSelectedItem();

				if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin bắt buộc!");
					return;
				}

				User newUser = new User(0, name, username, password, age, gender, height, weight, goal);
				boolean success = registerManager.register(newUser);
				if (success) {
					JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
					mainFrame.showLoginScreen();
				} else {
					JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Tuổi, chiều cao và cân nặng phải là số!", "Lỗi nhập liệu",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}