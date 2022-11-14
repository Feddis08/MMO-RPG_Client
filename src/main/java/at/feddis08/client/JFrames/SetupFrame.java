package at.feddis08.client.JFrames;

import at.feddis08.client.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class SetupFrame extends Canvas {
    public static JFrame frame = new JFrame();
    public static JTextField tfFirst_name = new JTextField("First Name");
    public static JTextField tfLast_name = new JTextField("Last Name");
    public static JTextField login_message = new JTextField("");
    public static JTextField email_address = new JTextField("email_address");
    public static JButton b1 = new JButton();
    public static JCheckBox checkBox1 = new JCheckBox("send message on login");
    public static JLabel label1 = new JLabel();
    public static void start() throws IOException, InterruptedException {
        frame.setTitle("MMORPG - SETUP");
        frame.setSize(256, 256);
        //frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/res/icon.png");
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

        email_address.setForeground(Color.BLUE);
        email_address.setBackground(Color.YELLOW);
        email_address.setSize(256, 32);
        email_address.setLocation(0,96);
        email_address.setVisible(true);
        frame.add(email_address);

        login_message.setForeground(Color.BLUE);
        login_message.setBackground(Color.YELLOW);
        login_message.setSize(128, 32);
        login_message.setLocation(128,128);
        login_message.setVisible(true);
        frame.add(login_message);

        checkBox1.setForeground(Color.YELLOW);
        checkBox1.setBackground(Color.BLUE);
        checkBox1.setSize(128, 32);
        checkBox1.setLocation(0,128);
        checkBox1.setSelected(false);
        checkBox1.setVisible(true);
        frame.add(checkBox1);

        b1.setText("submit");
        b1.setSize(256, 32);
        b1.setBackground(Color.GREEN);
        b1.setLocation(0, 160);
        b1.setVisible(true);
        frame.add(b1);

        label1.setSize(256, 32);
        label1.setLocation(0, 0);
        label1.setText("");
        label1.setVisible(true);
        frame.add(label1);

        frame.setLayout(null);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Start.client.setup_user(tfFirst_name.getText(), tfLast_name.getText(), email_address.getText(), String.valueOf(checkBox1.isSelected()), login_message.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        frame.setVisible(true);
        update();
    }
    public static void update() throws IOException, InterruptedException {
        Start.client.update_own_user();
        Thread.sleep(250);
        tfFirst_name.setText(Start.ownUser.first_name);
        tfLast_name.setText(Start.ownUser.last_name);
        email_address.setText(Start.ownUser.jsonObject.get("email_address").toString());
        login_message.setText(Start.ownUser.jsonObject.get("login_message").toString());
        if (Objects.equals(Start.ownUser.jsonObject.get("send_message_on_login").toString(), "true")) checkBox1.setSelected(true);

    }
}
