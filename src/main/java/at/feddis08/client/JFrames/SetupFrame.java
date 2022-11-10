package at.feddis08.client.JFrames;

import at.feddis08.client.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SetupFrame extends Canvas {
    public static JFrame frame = new JFrame();
    public static JTextField tfFirst_name = new JTextField("First Name");
    public static JTextField tfLast_name = new JTextField("Last Name");
    public static JButton b1 = new JButton();
    public static JLabel label1 = new JLabel();
    public static void start(){
        frame.setTitle("MMORPG - SETUP");
        frame.setSize(256, 256);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
        frame.setIconImage(icon);

        tfFirst_name.setForeground(Color.BLUE);
        tfFirst_name.setBackground(Color.YELLOW);
        tfFirst_name.setSize(256, 32);
        tfFirst_name.setLocation(0,32);
        tfFirst_name.setVisible(true);
        frame.add(tfFirst_name);

        tfLast_name.setForeground(Color.BLUE);
        tfLast_name.setBackground(Color.YELLOW);
        tfLast_name.setSize(256, 32);
        tfLast_name.setLocation(0,64);
        tfLast_name.setVisible(true);
        frame.add(tfLast_name);

        b1.setText("submit");
        b1.setSize(256, 32);
        b1.setBackground(Color.GREEN);
        b1.setLocation(0, 96);
        b1.setVisible(true);
        frame.add(b1);

        label1.setSize(256, 32);
        label1.setLocation(0, 0);
        label1.setText("Your account is not set up yet!");
        label1.setVisible(true);
        frame.add(label1);

        frame.setLayout(null);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Start.client.setup_user(tfFirst_name.getText(), tfLast_name.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        frame.setVisible(true);
    }
}
