package at.feddis08.client;


import at.feddis08.client.JFrames.LoginFrame;
import at.feddis08.client.socket.Client;

import java.io.IOException;

public class Start {
    public static String text = "ddd";

    public static String state = "connect to server";
    public static String id = "";
    public static Client client = new Client();
    public static String ip = "";
    public static String port = "";
    public static String spacing = "";


    public static void main(String[] args) throws IOException, InterruptedException {
        LoginFrame.start();
    }
    public static void log(String log){
        System.out.println(log);
    }
}