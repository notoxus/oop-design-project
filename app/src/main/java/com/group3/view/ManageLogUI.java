package com.group3.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.group3.controller.NutritionLogController;
import com.group3.controller.WorkoutLogController;
import com.group3.model.NutritionLog;
import com.group3.model.Observer;
import com.group3.model.User;
import com.group3.model.WorkoutLog;

public class ManageLogUI extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;
    private static final Color PRIMARY  = new Color(33, 150, 243);
    private static final Color DANGER   = new Color(229, 57, 53);
    private static final Color DANGER_D = new Color(183, 28, 28);
    private static final Color WHITE    = Color.WHITE;
    private static final Color BG       = new Color(245, 245, 245);

    private WorkoutLogController workoutCtrl;
    private NutritionLogController nutritionCtrl;
    private User user;

    private JTable workoutTable, nutritionTable;
    private DefaultTableModel workoutModel, nutritionModel;
    private List<WorkoutLog> displayedWorkoutLogs;
    private List<NutritionLog> displayedNutritionLogs;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");

    public ManageLogUI(WorkoutLogController workoutCtrl, NutritionLogController nutritionCtrl, User user) {
    	this.workoutCtrl = workoutCtrl;
        this.nutritionCtrl = nutritionCtrl;
        this.user = user;
        
    	this.workoutCtrl.add(this);
        this.nutritionCtrl.add(this);
        initComponents();
        loadWorkoutData();
        loadNutritionData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        JLabel lblTitle = new JLabel("Nhật Ký");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(WHITE);
        JLabel lblSub = new JLabel("Lịch sử tập luyện & dinh dưỡng");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(new Color(255, 255, 255, 180));
        JPanel stack = new JPanel(); stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS)); stack.setOpaque(false);
        stack.add(lblTitle); stack.add(Box.createRigidArea(new Dimension(0, 2))); stack.add(lblSub);
        header.add(stack, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel tabStrip = new JPanel(new GridLayout(1, 2));
        tabStrip.setBackground(WHITE);
        tabStrip.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)));
        tabStrip.setPreferredSize(new Dimension(0, 46));

        JButton tabWorkout   = makeTab("Tập luyện",  true);
        JButton tabNutrition = makeTab("Dinh dưỡng", false);

        tabStrip.add(tabWorkout);
        tabStrip.add(tabNutrition);

        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(BG);
        contentPanel.add(buildWorkoutPanel(), "WORKOUT");
        contentPanel.add(buildNutritionPanel(), "NUTRITION");
        CardLayout cl = (CardLayout) contentPanel.getLayout();

        tabWorkout.addActionListener(e -> {
            cl.show(contentPanel, "WORKOUT");
            setTabActive(tabWorkout, tabNutrition);
        });
        tabNutrition.addActionListener(e -> {
            cl.show(contentPanel, "NUTRITION");
            setTabActive(tabNutrition, tabWorkout);
        });

        JPanel body = new JPanel(new BorderLayout());
        body.add(tabStrip,     BorderLayout.NORTH);
        body.add(contentPanel, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }

    private JButton makeTab(String label, boolean active) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Arial", active ? Font.BOLD : Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBackground(WHITE);
        btn.setForeground(active ? new Color(33, 150, 243) : new Color(130, 130, 130));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (active) btn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(33, 150, 243)));
        else        btn.setBorder(BorderFactory.createEmptyBorder());
        return btn;
    }

    private void setTabActive(JButton active, JButton idle) {
        active.setForeground(new Color(33, 150, 243));
        active.setFont(new Font("Arial", Font.BOLD, 14));
        active.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(33, 150, 243)));
        idle.setForeground(new Color(130, 130, 130));
        idle.setFont(new Font("Arial", Font.PLAIN, 14));
        idle.setBorder(BorderFactory.createEmptyBorder());
    }

    private JPanel buildWorkoutPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(WHITE);

        String[] cols = {"Thời gian", "Bài tập", "Kết quả"};
        workoutModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        workoutTable = styleTable(workoutModel);

        JButton btnDel = makeDangerButton("Xóa log đã chọn");
        btnDel.addActionListener(e -> deleteWorkoutLog());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(WHITE);
        bottom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        bottom.add(btnDel, BorderLayout.CENTER);

        panel.add(new JScrollPane(workoutTable), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildNutritionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);

        String[] cols = {"Thời gian", "Sản phẩm", "Kcal", "Protein"};
        nutritionModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        nutritionTable = styleTable(nutritionModel);

        JButton btnDel = makeDangerButton("Xóa log đã chọn");
        btnDel.addActionListener(e -> deleteNutritionLog());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(WHITE);
        bottom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));
        bottom.add(btnDel, BorderLayout.CENTER);

        panel.add(new JScrollPane(nutritionTable), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private JTable styleTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(240, 240, 240));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setForeground(new Color(100, 100, 100));
        table.setSelectionBackground(new Color(227, 242, 253));
        table.setSelectionForeground(new Color(30, 30, 30));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) c.setBackground(row % 2 == 0 ? WHITE : new Color(250, 250, 250));
                return c;
            }
        });
        return table;
    }

    private JButton makeDangerButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(DANGER);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(0, 44));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(DANGER_D); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(DANGER); }
        });
        return btn;
    }
    @Override
    public void update() {
        loadWorkoutData();
        loadNutritionData();
    }
    private void loadWorkoutData() {
        workoutModel.setRowCount(0);
        List<WorkoutLog> logs = workoutCtrl.getAllLogs();
        displayedWorkoutLogs = new java.util.ArrayList<>();
        if (logs == null) return;
        for (WorkoutLog log : logs) {
            if (log.getUserID() != user.getUserID()) continue;
            displayedWorkoutLogs.add(log);
            String resultStr = "—";
            if (log.getDistance() != null && log.getTime() != null && log.getDistance() > 0) {
                resultStr = String.format("%.1f km — Pace: %.2f", log.getDistance(), log.paceCal());
            } 
            else if (log.getWeight() != null && log.getReps() != null) {
                resultStr = log.getWeight() + "kg x " + log.getReps() + "r";
            } else if (log.getReps() != null) {
                resultStr = log.getReps() + " reps";
            } else if (log.getTime() != null) {
                resultStr = log.getTime() + " phút";
            }
            workoutModel.addRow(new Object[]{
                log.getDate().format(formatter),
                log.getExercise().getExerciseName(),
                resultStr
            });
        }
    }

    private void loadNutritionData() {
        nutritionModel.setRowCount(0);
        List<NutritionLog> logs = nutritionCtrl.getAllLogs();
        displayedNutritionLogs = new java.util.ArrayList<>();
        if (logs == null) return;
        for (NutritionLog log : logs) {
            if (log.getUserID() != user.getUserID()) continue;
            displayedNutritionLogs.add(log);
            nutritionModel.addRow(new Object[]{
                log.getAddTime().format(formatter),
                log.getProductName(),
                log.getEnergy()  != null ? log.getEnergy()  : "—",
                log.getProtein() != null ? log.getProtein() : "—",
            });
        }
    }

    private void deleteWorkoutLog() {
        int row = workoutTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn log cần xóa!"); return; }
        if (displayedWorkoutLogs == null || row >= displayedWorkoutLogs.size()) return;
        int logID = displayedWorkoutLogs.get(row).getLogID();
        if (JOptionPane.showConfirmDialog(this, "Xóa log tập luyện này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (workoutCtrl.removeWorkoutLog(logID)) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                loadWorkoutData();
            }
        }
    }

    private void deleteNutritionLog() {
        int row = nutritionTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn log cần xóa!"); return; }
        if (displayedNutritionLogs == null || row >= displayedNutritionLogs.size()) return;
        int logID = displayedNutritionLogs.get(row).getLogID();
        if (JOptionPane.showConfirmDialog(this, "Xóa log dinh dưỡng này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (nutritionCtrl.removeNutritionLog(logID)) {
                JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                loadNutritionData();
            }
        }
    }
}
