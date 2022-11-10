package at.feddis08.client.JFrames;

import at.feddis08.client.Start;
import at.feddis08.client.socket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginFrame extends Canvas{


    public static JFrame frame = new JFrame();
    public static JTextField tfName = new JTextField("Felix");
    public static JTextField tfServerName = new JTextField("feddis08.com:25566");
    public static JTextField tfToken = new JTextField("felix123");
    public static JButton b1 = new JButton();
    public static JButton b2 = new JButton();
    public static JButton b3 = new JButton();
    public static JLabel label1 = new JLabel();
    public static Boolean b1_on = false;
    public static Boolean b3_on = false;

    public static void start(){
        frame.setTitle("MMORPG - LOGIN");
        frame.setSize(256, 256);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
        frame.setIconImage(icon);

        tfName.setForeground(Color.BLUE);
        tfName.setBackground(Color.YELLOW);
        tfName.setSize(256, 32);
        tfName.setLocation(0,0);
        tfName.setVisible(true);
        frame.add(tfName);

        tfToken.setForeground(Color.BLUE);
        tfToken.setBackground(Color.YELLOW);
        tfToken.setSize(256, 32);
        tfToken.setLocation(0,32);
        tfToken.setVisible(true);
        frame.add(tfToken);

        tfServerName.setForeground(Color.BLUE);
        tfServerName.setBackground(Color.YELLOW);
        tfServerName.setSize(256, 32);
        tfServerName.setLocation(0,64);
        tfServerName.setVisible(true);
        frame.add(tfServerName);


        b1.setText("connect");
        b1.setSize(128, 32);
        b1.setBackground(Color.GREEN);
        b1.setLocation(0, 96);
        b1.setVisible(true);
        frame.add(b1);

        b2.setText("save");
        b2.setSize(128, 32);
        b2.setBackground(Color.GREEN);
        b2.setLocation(128, 96);
        b2.setVisible(true);
        frame.add(b2);

        b3.setText("try connection");
        b3.setSize(256, 32);
        b3.setBackground(Color.GREEN);
        b3.setLocation(0, 128);
        b3.setVisible(true);
        frame.add(b3);

        label1.setSize(256, 32);
        label1.setLocation(0, 160);
        label1.setText("-");
        label1.setVisible(true);
        frame.add(label1);

        frame.setLayout(null);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(b1_on)){
                    b1_on = true;
                    Start.log("b1");
                    try {
                        String[] str = tfServerName.getText().split(":");
                        Start.client.startConnection(str[0], Integer.parseInt(str[1]));
                        Thread.sleep(250);
                        Start.client.join_server(tfName.getText(), tfToken.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b1_on) {
                    Start.log("b2");
                }
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(b3_on)) {
                    Start.log("b3");
                    b3_on = true;
                    String[] str = tfServerName.getText().split(":");
                    try {
                        Start.client.startConnection(str[0], Integer.parseInt(str[1]));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        Start.client.try_server();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        Start.client.stopConnection();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        frame.setVisible(true);
    }
}