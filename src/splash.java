import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class splash extends JFrame {

    private static final long serialVersionUID = 1L;
    static JProgressBar progressBar;
    private JPanel contentPane;
    static JLabel label_1;

    public static void main(String[] args) {
        int x;
        splash frame = new splash();
        frame.setVisible(true);
        try {
            for (x = 0; x <= 100; x++) {
                splash.progressBar.setValue(x);
                Thread.sleep(50);
                splash.label_1.setText(Integer.toString(x) + " %");
                if(x == 100) {
                    frame.dispose();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public splash() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 581);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("");
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/loading2.gif"));
        label.setIcon(icon);
        label.setBounds(0, 0, 700, 450);
        contentPane.add(label);

        progressBar = new JProgressBar();
        progressBar.setForeground(new Color(64, 128, 128));
        progressBar.setBounds(10, 510, 780, 37);
        contentPane.add(progressBar);

        label_1 = new JLabel();
        label_1.setForeground(new Color(255, 255, 255));
        label_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        label_1.setBounds(380, 469, 80, 30);
        contentPane.add(label_1);
    }
}
