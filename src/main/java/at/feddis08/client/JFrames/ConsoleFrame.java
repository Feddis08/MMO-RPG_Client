package at.feddis08.client.JFrames;

import at.feddis08.client.Start;
import at.feddis08.client.main.ConsoleParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleFrame extends JFrame {
    public static JTextArea  enteredText = new JTextArea(10, 40);
    public static JFrame frame = new JFrame();
    public static JTextField input_chat = new JTextField("");
    public static JPanel text = new JPanel();
    public static JScrollPane scroll = new JScrollPane();
    public static JButton b1 = new JButton();
    public static JLabel label1 = new JLabel();
    public static void start() throws IOException, InterruptedException {
        frame.setTitle("MMORPG - CONSOLE");
        //frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(512, 256);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/res/icon.png");
        frame.setIconImage(icon);
        Container c = frame.getContentPane();
        frame.getContentPane().setLayout(new BorderLayout());



        enteredText.setEditable(false);
        enteredText.setBackground(Color.LIGHT_GRAY);
        enteredText.setLineWrap(true);
        enteredText.setWrapStyleWord(true);
        scroll = new JScrollPane(enteredText);
        enteredText.setVisible(true);
        scroll.setVisible(true);
        frame.add(scroll);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        input_chat.setForeground(Color.BLUE);
        input_chat.setBackground(Color.YELLOW);
        input_chat.setVisible(true);
        frame.add(input_chat);

        b1.setText("send");
        b1.setBackground(Color.GREEN);
        b1.setVisible(true);
        frame.add(b1);
        frame.setLayout(new BoxLayout (frame.getContentPane(), BoxLayout.Y_AXIS));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    add_text_line("");
                    add_text_line("> " + input_chat.getText());
                    ConsoleParser.parse(input_chat.getText());
                    input_chat.setText("");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.setVisible(true);
        Start.client.update_own_user();
        Start.client.sendMessage("go_in_console");
        Start.client.sendMessage("get_server_info");
        add_text_line("Console ...");
        add_text_line("Hi " + Start.ownUser.first_name + " " + Start.ownUser.last_name + ", you are connected with: " + Start.server_info.getString("server_name"));
        add_text_line("current stats: " + Start.server_info);
        //Microphone.do_output();
    }
    public static void add_text_line(String line){
        ConsoleFrame.enteredText.insert(line + "\n", ConsoleFrame.enteredText.getText().length());
        ConsoleFrame.enteredText.setCaretPosition(ConsoleFrame.enteredText.getText().length());
        scroll.scrollRectToVisible(frame.getBounds());
    }
}
