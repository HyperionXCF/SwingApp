// File: TodoPage.java
package SwingApp;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TodoPage extends JPanel {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> todoList = new JList<>(listModel);
    private final List<Todo> todos = new ArrayList<>();
    private final MainPage main;

    public TodoPage(MainPage main) {
        this.main = main;
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Todo", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(todoList), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton add = new JButton("Add");
        JButton toggle = new JButton("Toggle Done");
        JButton remove = new JButton("Remove");
        JButton back = new JButton("Back");
        btns.add(add); btns.add(toggle); btns.add(remove); btns.add(back);
        add(btns, BorderLayout.SOUTH);

        add.addActionListener(e -> addTodoPopup());
        toggle.addActionListener(e -> {
            int i = todoList.getSelectedIndex();
            if (i >= 0) {
                Todo t = todos.get(i);
                t.toggleDone();
                listModel.set(i, format(t));
            }
        });
        remove.addActionListener(e -> {
            int i = todoList.getSelectedIndex();
            if (i >= 0) { todos.remove(i); listModel.remove(i); }
        });
        back.addActionListener(e -> main.showPage("Home"));
    }

    private void addTodoPopup() {
        JTextField task = new JTextField();
        Object[] msg = {"Task:", task};
        int opt = JOptionPane.showConfirmDialog(this, msg, "New Task", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            String t = task.getText().trim();
            if (t.isEmpty()) return;
            Todo todo = new Todo(t);
            todos.add(todo);
            listModel.addElement(format(todo));
        }
    }

    private String format(Todo t) {
        return (t.isDone() ? "[Done] " : "[ ] ") + t.getTask();
    }
}
