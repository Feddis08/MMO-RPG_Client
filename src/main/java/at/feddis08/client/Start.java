package at.feddis08.client;


import at.feddis08.client.JFrames.LoginFrame;
import at.feddis08.client.classes.UserObject;
import at.feddis08.client.main.Microphone;
import at.feddis08.client.socket.Client;
import com.sun.tools.javac.Main;
import org.json.JSONObject;

import java.awt.*;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Start {
    private static final Start THEMAINCLASSNAMEGOESHERE = null;
    public static String text = "ddd";

    public static String state = "connect to server";
    public static String id = "";
    public static Client client = new Client();
    public static String ip = "";
    public static String port = "";
    public static String spacing = "";
    public static String current_dir = "/home/mc/";
    public static UserObject ownUser = new UserObject();
    public static ArrayList<String> chat_messages = new ArrayList<String>();
    public static ArrayList<String> displayed_chat_messages = new ArrayList<String>();
    public static ArrayList<String[]> list_files = new ArrayList<>();
    public static ArrayList<File> files = new ArrayList<>();
    public static JSONObject server_info = new JSONObject();

    public static void main(String[] args) throws IOException, InterruptedException {
        LoginFrame.start();
    }
    public static void log(String log){
        System.out.println(log);
    }
}