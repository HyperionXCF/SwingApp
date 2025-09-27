package SwingApp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mp = new MainPage();
            mp.setVisible(true);
        });
    }
}