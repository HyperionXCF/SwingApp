// File: ExpensePage.java
package SwingApp;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExpensePage extends JPanel {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> expenseList = new JList<>(listModel);
    private final List<Expense> expenses = new ArrayList<>();
    private final JLabel budgetLabel = new JLabel();
    private double budget = 7000;
    private final MainPage main;

    public ExpensePage(MainPage main) {
        this.main = main;
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Expenses", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(expenseList), BorderLayout.CENTER);

        budgetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateBudgetLabel();
        add(budgetLabel, BorderLayout.PAGE_END);

        JPanel btns = new JPanel();
        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton back = new JButton("Back");
        btns.add(add); btns.add(remove); btns.add(back);
        add(btns, BorderLayout.SOUTH);

        add.addActionListener(e -> addExpensePopup());
        remove.addActionListener(e -> {
            int i = expenseList.getSelectedIndex();
            if (i >= 0) {
                Expense ex = expenses.remove(i);
                budget += ex.getAmount();
                listModel.remove(i);
                updateBudgetLabel();
            }
        });
        back.addActionListener(e -> main.showPage("Home"));
    }

    private void addExpensePopup() {
        JTextField title = new JTextField();
        JTextField amount = new JTextField();
        JTextField category = new JTextField();
        Object[] msg = {"Title:", title, "Amount:", amount, "Category:", category};
        int opt = JOptionPane.showConfirmDialog(this, msg, "New Expense", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                double amt = Double.parseDouble(amount.getText().trim());
                Expense e = new Expense(title.getText().trim(), amt, category.getText().trim());
                expenses.add(e);
                listModel.addElement(format(e));
                budget -= amt;
                updateBudgetLabel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateBudgetLabel() {
        budgetLabel.setText("Available Budget: Rs. " + String.format("%.2f", budget));
    }

    private String format(Expense e) {
        return e.getTitle() + " - Rs. " + e.getAmount() + " [" + e.getCategory() + "]";
    }
}
