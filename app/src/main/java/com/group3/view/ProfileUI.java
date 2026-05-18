package com.group3.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.group3.model.User;

public class ProfileUI extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final Color PRIMARY    = new Color(33, 150, 243);
    private static final Color WHITE      = Color.WHITE;
    private static final Color BG         = new Color(245, 245, 245);
    private static final Color DANGER     = new Color(229, 57, 53);
    private static final Color DANGER_D   = new Color(183, 28, 28);

    public ProfileUI(User currentUser, MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(28, 20, 24, 20));

        String initials = getInitials(currentUser.getName());
        JLabel lblAvatar = new JLabel(initials, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        lblAvatar.setFont(new Font("Arial", Font.BOLD, 22));
        lblAvatar.setForeground(WHITE);
        lblAvatar.setOpaque(false);
        lblAvatar.setPreferredSize(new Dimension(68, 68));
        lblAvatar.setMaximumSize(new Dimension(68, 68));
        lblAvatar.setMinimumSize(new Dimension(68, 68));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel(currentUser.getName() != null ? currentUser.getName() : currentUser.getUsername());
        lblName.setFont(new Font("Arial", Font.BOLD, 18));
        lblName.setForeground(WHITE);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("@" + currentUser.getUsername() + "  ·  " + goalLabel(currentUser));
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(new Color(255, 255, 255, 180));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblAvatar);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(lblName);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        headerPanel.add(lblSub);

        add(headerPanel, BorderLayout.NORTH);

        JPanel statsGrid = new JPanel(new GridLayout(1, 4, 8, 0));
        statsGrid.setBackground(BG);
        statsGrid.setBorder(new EmptyBorder(12, 12, 8, 12));

        statsGrid.add(makeStatCard("Chiều cao", currentUser.getHeight() + "\ncm"));
        statsGrid.add(makeStatCard("Cân nặng",  currentUser.getWeight() + "\nkg"));
        statsGrid.add(makeStatCard("Tuổi",       String.valueOf(currentUser.getAge())));
        statsGrid.add(makeStatCard("BMI",        String.format("%.1f", bmi(currentUser))));

        JPanel infoCard = new JPanel();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setBackground(WHITE);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        String[][] rows = {
            {"👤", "Họ và tên",       currentUser.getName() != null ? currentUser.getName() : "—"},
            {"🆔", "Tên đăng nhập",   currentUser.getUsername()},
            {"⚤",  "Giới tính",       currentUser.getGender() != null ? currentUser.getGender() : "—"},
            {"🎯", "Mục tiêu",        goalLabel(currentUser)},
        };
        for (String[] row : rows) infoCard.add(makeInfoRow(row[0], row[1], row[2]));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG);
        bottomPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        JButton btnLogout = new JButton("Đăng Xuất");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 15));
        btnLogout.setBackground(DANGER);
        btnLogout.setForeground(WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setOpaque(true);
        btnLogout.setPreferredSize(new Dimension(0, 46));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnLogout.setBackground(DANGER_D); }
            public void mouseExited(MouseEvent e)  { btnLogout.setBackground(DANGER); }
        });
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) mainFrame.showLoginScreen();
        });

        bottomPanel.add(btnLogout, BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(BG);
        center.add(statsGrid);
        center.add(Box.createRigidArea(new Dimension(0, 4)));
        center.add(infoCard);
        center.add(Box.createGlue());

        add(center,      BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel makeStatCard(String label, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            new EmptyBorder(10, 6, 10, 6)
        ));

        // Value may contain "\n" for unit on second line
        String[] parts = value.split("\n");
        JLabel lblVal = new JLabel(parts[0], SwingConstants.CENTER);
        lblVal.setFont(new Font("Arial", Font.BOLD, 16));
        lblVal.setForeground(new Color(33, 33, 33));
        lblVal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLbl = new JLabel(label, SwingConstants.CENTER);
        lblLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        lblLbl.setForeground(new Color(130, 130, 130));
        lblLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblVal);
        if (parts.length > 1) {
            JLabel unit = new JLabel(parts[1], SwingConstants.CENTER);
            unit.setFont(new Font("Arial", Font.PLAIN, 11));
            unit.setForeground(new Color(150, 150, 150));
            unit.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(unit);
        }
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(lblLbl);
        return card;
    }

    private JPanel makeInfoRow(String icon, String label, String value) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setBackground(WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
            new EmptyBorder(13, 16, 13, 16)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        JLabel lblKey = new JLabel(label);
        lblKey.setFont(new Font("Arial", Font.PLAIN, 14));
        lblKey.setForeground(new Color(80, 80, 80));

        JLabel lblVal = new JLabel(value);
        lblVal.setFont(new Font("Arial", Font.BOLD, 14));
        lblVal.setForeground(new Color(30, 30, 30));

        row.add(lblIcon, BorderLayout.WEST);
        row.add(lblKey,  BorderLayout.CENTER);
        row.add(lblVal,  BorderLayout.EAST);
        return row;
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "?";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        return ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
    }

    private String goalLabel(User u) {
        if (u.getGoal() == null) return "—";
        return switch (u.getGoal()) {
            case MUSCLE_GAIN -> "Tăng cơ bắp";
            case LOSE_FAT    -> "Giảm mỡ";
            case MAINTENANCE -> "Duy trì";
            default          -> u.getGoal().toString();
        };
    }

    private double bmi(User u) {
        if (u.getHeight() <= 0 || u.getWeight() <= 0) return 0;
        double hM = u.getHeight() > 10 ? u.getHeight() / 100.0 : u.getHeight();
        return u.getWeight() / (hM * hM);
    }
}