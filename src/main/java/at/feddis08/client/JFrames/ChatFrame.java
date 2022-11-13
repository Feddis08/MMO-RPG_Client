package at.feddis08.client.JFrames;

import at.feddis08.client.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatFrame {
    public static JTextArea  enteredText = new JTextArea();
    public static JFrame frame = new JFrame();
    public static JTextField input_chat = new JTextField("");
    public static JButton b1 = new JButton();
    public static JLabel label1 = new JLabel();
    public static void start() throws IOException, InterruptedException {
        frame.setTitle("MMORPG - CHAT");
        frame.setSize(524, 512);
        //frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/res/icon.png");
        frame.setIconImage(icon);


        enteredText.setEditable(false);
        enteredText.setBackground(Color.LIGHT_GRAY);
        enteredText.setLocation(0,0);
        enteredText.setSize(512, 443);

        frame.add(enteredText);

        input_chat.setForeground(Color.BLUE);
        input_chat.setBackground(Color.YELLOW);
        input_chat.setSize(448, 32);
        input_chat.setLocation(0,443);
        input_chat.setVisible(true);
        frame.add(input_chat);

        b1.setText("send");
        b1.setSize(64, 32);
        b1.setBackground(Color.GREEN);
        b1.setLocation(448, 443);
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
                    Start.client.send_chat_message(input_chat.getText());
                    input_chat.setText("");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.setVisible(true);
    }
}
