package at.feddis08.client;


import at.feddis08.client.JFrames.LoginFrame;
import at.feddis08.client.classes.UserObject;
import at.feddis08.client.socket.Client;

import java.io.IOException;
import java.util.ArrayList;

public class Start {
    public static String text = "ddd";

    public static String state = "connect to server";
    public static String id = "";
    public static Client client = new Client();
    public static String ip = "";
    public static String port = "";
    public static String spacing = "";
    public static UserObject ownUser = new UserObject();
    public static ArrayList<String> chat_messages = new ArrayList<String>();
    public static ArrayList<String> displayed_chat_messages = new ArrayList<String>();


    public static void main(String[] args) throws IOException, InterruptedException {
        LoginFrame.start();
    }
    public static void log(String log){
        System.out.println(log);
    }
}