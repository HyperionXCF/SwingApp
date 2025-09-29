package SwingApp;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpensesPage extends JFrame {

    private JLabel budgetLabel;
    private double budget = 0;
    private DefaultPieDataset pieDataset;
    private DefaultListModel<Expense> expenseListModel;

    private final Color foodColor = Color.PINK;
    private final Color stationaryColor = Color.BLUE;
    private final Color healthColor = Color.GREEN;
    private final Color miscColor = Color.YELLOW;

    public ExpensesPage() {
        setTitle("Expense Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Top Header with Date ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel dateLabel = new JLabel(getFormattedDate(), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        dateLabel.setForeground(new Color(70, 70, 70));

        headerPanel.add(dateLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // --- Left Panel ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(300, getHeight()));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        leftPanel.setBackground(new Color(245,245,245));

        // --- Budget Panel ---
        JPanel budgetPanel = new JPanel(new BorderLayout());
        budgetPanel.setBackground(Color.WHITE);
        budgetPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        budgetLabel = new JLabel("₹ " + (int)budget, SwingConstants.CENTER);
        budgetLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        budgetLabel.setForeground(Color.GREEN);

        JButton increaseBudgetBtn = new JButton("Increase Budget");
        styleButton(increaseBudgetBtn, new Color(100,149,237));
        increaseBudgetBtn.addActionListener(this::increaseBudget);

        budgetPanel.add(budgetLabel, BorderLayout.CENTER);
        budgetPanel.add(increaseBudgetBtn, BorderLayout.SOUTH);

        // --- Pie Chart Panel ---
        pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Food", 0);
        pieDataset.setValue("Stationary", 0);
        pieDataset.setValue("Health", 0);
        pieDataset.setValue("Misc", 0);

        ChartPanel chartPanel = new ChartPanel(
                ChartFactory.createPieChart("Expenses", pieDataset, false, true, false)
        );

        leftPanel.add(budgetPanel, BorderLayout.NORTH);
        leftPanel.add(chartPanel, BorderLayout.CENTER);

        // --- Right Panel ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        rightPanel.setBackground(new Color(245,245,245));

        expenseListModel = new DefaultListModel<>();
        JList<Expense> expenseList = new JList<>(expenseListModel);
        expenseList.setFixedCellHeight(50);
        expenseList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        expenseList.setCellRenderer(new ExpenseRenderer());

        JScrollPane scrollPane = new JScrollPane(expenseList);

        JButton addExpenseBtn = new JButton("Add Expense");
        styleButton(addExpenseBtn, new Color(60,179,113));
        addExpenseBtn.addActionListener(this::addExpense);

        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(addExpenseBtn, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // --- Helper for button styling ---
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8,20,8,20));
        button.setOpaque(true);
    }

    // --- Format Date like: Monday 15th April 2025 ---
    private String getFormattedDate() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");

        String dayName = dayFormat.format(new Date());
        int day = Integer.parseInt(dateFormat.format(new Date()));
        String suffix = getDaySuffix(day);
        String monthYear = monthYearFormat.format(new Date());

        return dayName + " " + day + suffix + " " + monthYear;
    }

    // --- Helper for date suffix ---
    private String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) return "th";
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    private void increaseBudget(ActionEvent e) {
        String input = JOptionPane.showInputDialog(this, "Enter amount to add:");
        if (input != null && !input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                budget += amount;
                updateBudgetLabel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number");
            }
        }
    }

    private void addExpense(ActionEvent e) {
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Food","Stationary","Health","Misc"});
        JTextField noteField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new JLabel("Amount:")); panel.add(amountField);
        panel.add(new JLabel("Date:")); panel.add(dateField);
        panel.add(new JLabel("Category:")); panel.add(categoryBox);
        panel.add(new JLabel("Note:")); panel.add(noteField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Expense", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String date = dateField.getText();
                String category = (String) categoryBox.getSelectedItem();
                String note = noteField.getText();

                Expense exp = new Expense(amount, date, category, note);
                expenseListModel.addElement(exp);

                budget -= amount;
                updateBudgetLabel();

                // Update pie chart
                double current = pieDataset.getValue(category).doubleValue();
                pieDataset.setValue(category, current + amount);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    private void updateBudgetLabel() {
        budgetLabel.setText("₹ " + (int)budget);
        budgetLabel.setForeground(budget < 1000 ? Color.RED : Color.GREEN);
    }

    // --- Renderer ---
    private class ExpenseRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Expense exp) {
                switch (exp.getCategory()) {
                    case "Food" -> label.setBackground(foodColor);
                    case "Stationary" -> label.setBackground(stationaryColor);
                    case "Health" -> label.setBackground(healthColor);
                    case "Misc" -> label.setBackground(miscColor);
                }
                label.setOpaque(true);
            }
            return label;
        }
    }
}
