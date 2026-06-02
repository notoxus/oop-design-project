package com.group3.view;

import javax.swing.*;
import java.awt.*;
import com.group3.controller.*;
import com.group3.model.*;

public class LoginForm extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private LoginManager loginManager;

	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnRegister;

	public LoginForm(MainFrame mainFrame, LoginManager loginManager) {
		this.mainFrame = mainFrame;
		this.loginManager = loginManager;
		initComponents();
		setupEvents();
	}

	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		mainPanel.add(lblTitle, gbc);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		mainPanel.add(new JLabel("Tên đăng nhập:"), gbc);
		txtUsername = new JTextField(15);
		gbc.gridx = 1;
		mainPanel.add(txtUsername, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(new JLabel("Mật khẩu:"), gbc);
		txtPassword = new JPasswordField(15);
		gbc.gridx = 1;
		mainPanel.add(txtPassword, gbc);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		btnLogin = new JButton("Đăng nhập");
		btnRegister = new JButton("Đăng ký");
		btnPanel.add(btnLogin);
		btnPanel.add(btnRegister);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		mainPanel.add(btnPanel, gbc);

		add(mainPanel);
	}

	private void setupEvents() {
		btnLogin.addActionListener(e -> handleLogin());
		txtPassword.addActionListener(e -> handleLogin());
		btnRegister.addActionListener(e -> mainFrame.showRegisterScreen());
	}

	private void handleLogin() {
		String username = txtUsername.getText().trim();
		String password = new String(txtPassword.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
			return;
		}

		IAccount account = loginManager.login(username, password);
		if (account instanceof Admin) {
			mainFrame.showAdminDashboard((Admin) account);

		} else if (account instanceof User) {
			mainFrame.showUserDashboard((User) account);

		} else {
			JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
		}
	}
}