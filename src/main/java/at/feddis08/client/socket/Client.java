package at.feddis08.client.socket;

import at.feddis08.client.JFrames.LoginFrame;
import at.feddis08.client.JFrames.MainFrame;
import at.feddis08.client.JFrames.SetupFrame;
import at.feddis08.client.Start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Client extends Thread{
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<String> requests = new ArrayList<>();
    private Thread th = this;
    private ArrayList<String> sendLsBuffer = new ArrayList<>();

    public void startConnection(String ip, int port) throws IOException, InterruptedException {
        clientSocket = new Socket(ip, port);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        th.start();
    }
    public void run(){
        while (true){
            try {
                requests.add(listen());
                parse_request();
            } catch (IOException e) {
                e.printStackTrace();
                th.stop();
            }
        }
    }

    public void parse_request() throws IOException {


        int index = 0;
        while (index < requests.size()){
            System.out.println(requests.get(index));
            if (requests.get(index).split("SPACING").length > 1 ){
                Start.spacing = requests.get(index).split("SPACING")[1];
            }
            String str = requests.get(index);
            String[] command = str.split(Start.spacing);
            if (Objects.equals(command[0], "join_allowed")){
                Start.id = command[1];
                LoginFrame.label1.setText("connected! " + command[1]);
                MainFrame.start();
            }
            if (Objects.equals(command[0], "join_not_allowed")){
                LoginFrame.label1.setText(command[1]);
            }
            if (Objects.equals(command[0], "try_join")){
                LoginFrame.label1.setText(command[1]);
            }
            if (Objects.equals(command[0], "start_setup")){
                SetupFrame.start();
            }
            if (Objects.equals(command[0], "setup_account_passed")){
                SetupFrame.label1.setText("Setup passed!");
            }
            if (Objects.equals(command[0], "setup_account_not_passed")){
                SetupFrame.label1.setText("Setup did not pass! Please try again!");
            }
            requests.remove(index);
            index = index + 1;
        }

    }

    public void sendMessage(String msg) throws IOException {
        output.println(msg);
    }

    public void stopConnection() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
        th.stop();
    }
    public String listen() throws IOException {
        String str = input.readLine();
        if (str == null){
            System.exit(1);
        }
        return str;
    }
    public void join_server(String user_name, String token) throws IOException {
        sendMessage("join_server_with_u&t" + Start.spacing + user_name + Start.spacing +token);
    }
    public void try_server() throws IOException {
        sendMessage("try_server" + Start.spacing + "working!");
    }
    public void setup_user(String first_name, String last_name) throws IOException {
        sendMessage("setup_account" + Start.spacing + first_name + Start.spacing + last_name);
    }
}