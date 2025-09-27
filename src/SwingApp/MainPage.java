package SwingApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HabitsPage extends JPanel {
    private JPanel habitsList;
    private MainPage mainPage;  // keep a reference if needed

    public HabitsPage(MainPage mainPage) {
        this.mainPage = mainPage;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        JLabel title = new JLabel("Habit Tracker", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        habitsList = new JPanel();
        habitsList.setLayout(new BoxLayout(habitsList, BoxLayout.Y_AXIS));
        habitsList.setBackground(new Color(245, 245, 250));
        habitsList.setBorder(new EmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(habitsList);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        addHabit("Morning Run");
        addHabit("Read 20 minutes");
        addHabit("Drink 2L Water");
    }

    public void addHabit(String habitName) {
        JPanel habitPanel = new JPanel(new BorderLayout());
        habitPanel.setPreferredSize(new Dimension(300, 60));
        habitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        habitPanel.setBackground(Color.WHITE);

        habitPanel.setBorder(new LineBorder(new Color(200, 200, 220), 1, true));
        habitPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel label = new JLabel(habitName);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton markDone = new JButton("Done");
        markDone.setFocusPainted(false);
        markDone.setBackground(new Color(70, 130, 180));
        markDone.setForeground(Color.WHITE);
        markDone.setFont(new Font("Segoe UI", Font.BOLD, 14));
        markDone.setBorder(new EmptyBorder(5, 15, 5, 15));
        markDone.setCursor(new Cursor(Cursor.HAND_CURSOR));

        habitPanel.add(label, BorderLayout.WEST);
        habitPanel.add(markDone, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 245, 250));
        wrapper.setBorder(new EmptyBorder(5, 0, 5, 0));
        wrapper.add(habitPanel, BorderLayout.CENTER);

        habitsList.add(wrapper);
        habitsList.revalidate();
        habitsList.repaint();
    }
}
