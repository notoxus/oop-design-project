package com.group3.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.List;

import com.group3.controller.WorkoutLogController;
import com.group3.controller.WorkoutHandling;
import com.group3.model.Exercise;
import com.group3.model.ExerciseLibrary;
import com.group3.model.TrackingType;
import com.group3.model.User;
import com.group3.model.WorkoutLog;
import com.group3.model.RecommendationResult;

public class ExerciseUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private WorkoutLogController logController;
    private WorkoutHandling handling;
    private ExerciseLibrary library;
    private User user;
    private DashboardUI dashboard;

    private Exercise currentExercise;
    private JLabel lblExName, lblExTarget;
    
    private JLabel lblWeight, lblReps, lblDistance, lblTime;
    private HintTextField txtWeight, txtReps, txtDistance, txtTime;
    
    private JButton btnSave, btnBack;
    private JLabel lblToggleHint;
    private JLabel lblHintMsg;
    private JPanel formPanel;
    
    private boolean isHintEnabled = false;

    public ExerciseUI(WorkoutLogController logController, WorkoutHandling handling, ExerciseLibrary library,
            User user, DashboardUI dashboard) {
        this.logController = logController;
        this.handling = handling;
        this.library = library;
        this.user = user;
        this.dashboard = dashboard;

        initComponents();
        setupEvents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        btnBack = new JButton("<html>↩ Quay lại</html>");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 12));
        btnBack.setFocusPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.setForeground(new Color(33, 150, 243));
        
        JLabel lblTitle = new JLabel("TẬP LUYỆN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(Box.createRigidArea(new Dimension(130, 20)), BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 8, 10, 8);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        
        JPanel exerciseCard = new JPanel(new GridLayout(2, 1));
        exerciseCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(33, 150, 243), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        exerciseCard.setBackground(new Color(240, 248, 255));
        lblExName = new JLabel("Chưa chọn bài tập");
        lblExName.setFont(new Font("Arial", Font.BOLD, 16));
        lblExTarget = new JLabel("---");
        lblExTarget.setFont(new Font("Arial", Font.PLAIN, 13));
        lblExTarget.setForeground(Color.GRAY);
        exerciseCard.add(lblExName);
        exerciseCard.add(lblExTarget);
        
        formPanel.add(exerciseCard, gbc);

        gbc.gridwidth = 1;
        lblWeight = new JLabel("Mức tạ (kg):"); txtWeight = new HintTextField(10);
        lblReps = new JLabel("Số hiệp (reps):"); txtReps = new HintTextField(10);
        lblDistance = new JLabel("Quãng đường (km):"); txtDistance = new HintTextField(10);
        lblTime = new JLabel("Thời gian (phút):"); txtTime = new HintTextField(10);

        gbc.gridy = 1; gbc.gridx = 0; formPanel.add(lblWeight, gbc);
        gbc.gridx = 1; formPanel.add(txtWeight, gbc);
        gbc.gridy = 2; gbc.gridx = 0; formPanel.add(lblReps, gbc);
        gbc.gridx = 1; formPanel.add(txtReps, gbc);
        gbc.gridy = 3; gbc.gridx = 0; formPanel.add(lblDistance, gbc);
        gbc.gridx = 1; formPanel.add(txtDistance, gbc);
        gbc.gridy = 4; gbc.gridx = 0; formPanel.add(lblTime, gbc);
        gbc.gridx = 1; formPanel.add(txtTime, gbc);

        lblHintMsg = new JLabel(" ");
        lblHintMsg.setFont(new Font("Arial", Font.ITALIC, 12));
        lblHintMsg.setForeground(new Color(33, 150, 243));
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        formPanel.add(lblHintMsg, gbc);

        lblToggleHint = new JLabel("<html><u>💡 Bật gợi ý tự động</u></html>");
        lblToggleHint.setFont(new Font("Arial", Font.ITALIC, 13));
        lblToggleHint.setForeground(new Color(150, 150, 150));
        lblToggleHint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblToggleHint.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        formPanel.add(lblToggleHint, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setBackground(Color.WHITE);

        // ĐÃ THAY ĐỔI: Đổi tên nút thành "Set tập tiếp theo"
        btnSave = new JButton("Set tập tiếp theo");
        btnSave.setBackground(new Color(0, 153, 76));
        btnSave.setForeground(Color.BLACK);
        btnSave.setFont(new Font("Arial", Font.BOLD, 15));
        btnSave.setPreferredSize(new Dimension(200, 45));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        btnBack.addActionListener(e -> dashboard.showLibrary());
        lblToggleHint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isHintEnabled = !isHintEnabled;
                updateHints();
            }
        });

        btnSave.addActionListener(e -> {
            try {
                if (currentExercise == null) {
                    JOptionPane.showMessageDialog(this, "Bạn chưa chọn bài tập từ Thư viện!");
                    return;
                }
                Double weight = getDoubleValue(txtWeight);
                Integer reps = getIntValue(txtReps);
                Double distance = getDoubleValue(txtDistance);
                Double time = getDoubleValue(txtTime);
                WorkoutLog log = new WorkoutLog.WorkoutLogBuilder().setUserID(user.getUserID())
                        .setLogID((int) (System.currentTimeMillis() % 100000)).setDate(LocalDateTime.now())
                        .setExercise(currentExercise).setWeight(weight).setReps(reps).setDistance(distance).setTime(time)
                        .build();

                if (logController.addWorkoutLog(log)) {
                    JOptionPane.showMessageDialog(this, "Đã lưu log thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    txtWeight.setText(""); txtReps.setText("");
                    txtDistance.setText(""); txtTime.setText("");
                    updateHints();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu file!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng chỉ nhập số hợp lệ vào các ô đo lường!");
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }

    private Double getDoubleValue(HintTextField txt) {
        if (!txt.isVisible()) return null;
        if (!txt.getText().isEmpty()) return Double.parseDouble(txt.getText());
        if (isHintEnabled && !txt.getHint().isEmpty()) return Double.parseDouble(txt.getHint());
        return null;
    }

    private Integer getIntValue(HintTextField txt) {
        if (!txt.isVisible()) return null;
        if (!txt.getText().isEmpty()) return Integer.parseInt(txt.getText());
        if (isHintEnabled && !txt.getHint().isEmpty()) return Integer.parseInt(txt.getHint());
        return null;
    }

    public void setSelectedExercise(Exercise ex) {
        if (ex != null) {
            this.currentExercise = ex;
            lblExName.setText(ex.getExerciseName());
            lblExTarget.setText("Nhóm cơ: " + ex.getTargetMuscle() + " (" + ex.getCategory() + ")");
            updateFormVisibility(ex);
            updateHints();
        }
    }

    private void updateFormVisibility(Exercise ex) {
        TrackingType type = ex.getTrackingType();
        boolean showWeight = false, showReps = false, showDist = false, showTime = false;

        switch (type) {
        case WEIGHT_REP_TIME: showWeight = true; showReps = true; showTime = true; break;
        case DISTANCE_TIME: showDist = true; showTime = true; break;
        case TIME_ONLY: showTime = true; break;
        case WEIGHT_REP: showWeight = true; showReps = true; break;
        case REP_ONLY: showReps = true; break;
        }

        lblWeight.setVisible(showWeight); txtWeight.setVisible(showWeight);
        lblReps.setVisible(showReps); txtReps.setVisible(showReps);
        lblDistance.setVisible(showDist); txtDistance.setVisible(showDist);
        lblTime.setVisible(showTime); txtTime.setVisible(showTime);

        if (!showWeight) txtWeight.setText("");
        if (!showReps) txtReps.setText("");
        if (!showDist) txtDistance.setText("");
        if (!showTime) txtTime.setText("");

        formPanel.revalidate();
        formPanel.repaint();
    }

    private void updateHints() {
        if (!isHintEnabled) {
            txtWeight.setHint(""); txtReps.setHint("");
            txtDistance.setHint(""); txtTime.setHint("");
            lblHintMsg.setText(" ");
            lblToggleHint.setText("<html><u>💡 Bật gợi ý tự động</u></html>");
            lblToggleHint.setForeground(new Color(150, 150, 150));
            return;
        }

        lblToggleHint.setText("<html><u>💡 Tắt gợi ý tự động</u></html>");
        lblToggleHint.setForeground(new Color(229, 57, 53));

        if (currentExercise == null) return;

        WorkoutLog lastLog = getLastLogOfCurrentExercise();
        if (lastLog == null) {
            lblHintMsg.setText("Chưa có dữ liệu lịch sử bài này để đưa ra gợi ý.");
            return;
        }

        RecommendationResult rec = handling.calculateNextSet(lastLog);
        
        txtWeight.setHint(rec.getSuggestedWeight() != null ? String.valueOf(rec.getSuggestedWeight()) : "");
        txtReps.setHint(rec.getSuggestedReps() != null ? String.valueOf(rec.getSuggestedReps()) : "");
        txtDistance.setHint(rec.getSuggestedDistance() != null ? String.valueOf(rec.getSuggestedDistance()) : "");
        txtTime.setHint(rec.getSuggestedTime() != null ? String.valueOf(rec.getSuggestedTime()) : "");
        
        if (rec.getMessage() != null && !rec.getMessage().isEmpty()) {
            lblHintMsg.setText("<html>💡 <i>" + rec.getMessage() + "</i></html>");
        }
    }

    private WorkoutLog getLastLogOfCurrentExercise() {
        List<WorkoutLog> logs = logController.getAllLogs();
        if (logs == null || logs.isEmpty()) return null;
        
        for (int i = logs.size() - 1; i >= 0; i--) {
            WorkoutLog log = logs.get(i);
            if (log.getUserID() == user.getUserID() && 
                log.getExercise().getExerciseID() == currentExercise.getExerciseID()) {
                return log;
            }
        }
        return null;
    }

    private class HintTextField extends JTextField {
        private static final long serialVersionUID = 1L;
        private String hint = "";

        public HintTextField(int columns) {
            super(columns);
        }

        public void setHint(String hint) {
            this.hint = hint;
            repaint();
        }
        public String getHint() {
            return this.hint;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (getText().isEmpty() && hint != null && !hint.isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(150, 150, 150));
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                
                FontMetrics fm = g2.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                
                g2.drawString(hint, getInsets().left, y);
                g2.dispose();
            }
        }
    }
}