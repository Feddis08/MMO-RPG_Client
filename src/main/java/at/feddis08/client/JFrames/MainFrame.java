package at.feddis08.client.JFrames;

import at.feddis08.client.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.IOException;

public class MainFrame extends JFrame{
    public static JFrame frame = new JFrame();
    public static JButton b1 = new JButton();
    public static JButton b2 = new JButton();
    public static JButton b3 = new JButton();
    public static JButton b4 = new JButton();
    public static JLabel label1 = new JLabel();

    public static void start() {
        frame.setTitle("MMORPG - MAIN");
        frame.setSize(1024, 512);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
        frame.setIconImage(icon);

        Container c = frame.getContentPane();
        JLabel pictureLabel;
        pictureLabel = new JLabel();
        Dimension size = pictureLabel.getPreferredSize();
        ImageIcon icon2 = new ImageIcon( "res/wallpapers/main/1.png");
        pictureLabel.setIcon(icon2);
        pictureLabel.setBounds(0, 0, 1024, 512);
        pictureLabel.setSize(1024, 512);
        c.add(pictureLabel);
        frame.setVisible(true);
        c.setVisible(true);

        b1.setText("change name");
        b1.setSize(256, 32);
        b1.setBackground(Color.GREEN);
        b1.setLocation(16, 16);
        b1.setVisible(true);
        frame.add(b1);

        b2.setText("?");
        b2.setSize(256, 32);
        b2.setBackground(Color.GREEN);
        b2.setLocation(16, 80);
        b2.setVisible(true);
        frame.add(b2);

        b3.setText("?");
        b3.setSize(256, 32);
        b3.setBackground(Color.GREEN);
        b3.setLocation(16, 144);
        b3.setVisible(true);
        frame.add(b3);

        b4.setText("?");
        b4.setSize(256, 32);
        b4.setBackground(Color.GREEN);
        b4.setLocation(16, 208);
        b4.setVisible(true);
        frame.add(b4);

        label1.setSize(256, 32);
        label1.setLocation(0, 0);
        label1.setText("Your account is not set up yet!");
        label1.setVisible(true);
        frame.add(label1);

        frame.setLayout(null);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetupFrame.start();
            }
        });
        frame.setVisible(true);
    }
}
