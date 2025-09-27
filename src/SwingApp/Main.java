package SwingApp;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(){
        initComponents();
    }

    public void initComponents(){
        setTitle("Hyperion");
        setSize(new Dimension(480,840));
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        new Main().setVisible(true);
    }
}
