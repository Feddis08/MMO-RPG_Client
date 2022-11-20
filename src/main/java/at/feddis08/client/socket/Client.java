package at.feddis08.client.socket;

import at.feddis08.client.JFrames.*;
import at.feddis08.client.Start;

import javax.sound.sampled.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import at.feddis08.client.main.Microphone;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Client extends Thread{
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<String> requests = new ArrayList<>();
    private Thread th = this;



    public void startConnection(String ip, int port) throws IOException, InterruptedException, LineUnavailableException {
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Boolean startup = true;

    public void parse_request() throws IOException, InterruptedException, LineUnavailableException {


        int index = 0;
        while (index < requests.size()){
            //System.out.println(requests.get(index));
            if (requests.get(index).split("SPACING").length > 1 ){
                Start.spacing = requests.get(index).split("SPACING")[1];
            }
            String str = requests.get(index);
            String[] command = str.split(Start.spacing);
            if (Objects.equals(command[0], "join_allowed")){
                Start.id = command[1];
                LoginFrame.label1.setText("connected! " + command[1]);
                update_own_user();
                sendMessage("get_server_info");
                sendMessage("ping");
                //get_list_files("/home/mc/");
                //get_file("/home/mc/MMORPG/");
                //Microphone.start();
            }
            if (Objects.equals(command[0], "join_not_allowed")){
                LoginFrame.label1.setText(command[1]);
            }
            if (Objects.equals(command[0], "send_server_info")){
                Start.server_info = new JSONObject(command[1]);
                System.out.println(Start.server_info);
            }
            if (Objects.equals(command[0], "try_join")){
                LoginFrame.label1.setText(command[1]);
            }
            if (Objects.equals(command[0], "pong")){
                sendMessage("ping");
            }
            if (Objects.equals(command[0], "start_setup")){
                SetupFrame.start();
            }
            if (Objects.equals(command[0], "chat_message")){
                Start.chat_messages.add(command[1]);
                if (Start.displayed_chat_messages.size() <= 16){
                    Start.displayed_chat_messages.add(command[1]);
                }else{
                    Start.displayed_chat_messages.clear();
                    Start.displayed_chat_messages.add(command[1]);
                    ChatFrame.enteredText.setText("");
                }
                ChatFrame.add_text_line(command[1]);
            }
            if (Objects.equals(command[0], "get_own_user")){
                Start.ownUser.id = command[1];
                Start.ownUser.first_name = command[2];
                Start.ownUser.last_name = command[3];
                Start.ownUser.time_created = command[4];
                Start.ownUser.data_json = command[5];
                Start.ownUser.update_json();

                if (startup) {
                    MainFrame.start();
                    if (Objects.equals(Start.ownUser.jsonObject.get("send_message_on_login").toString(), "true")) {
                        send_chat_message(Start.ownUser.jsonObject.get("login_message").toString());
                    }
                    startup = false;
                }

            }
            if (Objects.equals(command[0], "setup_account_passed")){
                SetupFrame.label1.setText("Setup passed!");
            }
            if (Objects.equals(command[0], "send_list_files")){
                String[] list_files = deserializeArray(command[1]);
                Start.list_files.add(list_files);
                System.out.println(Arrays.toString(Start.list_files.get(0)));
            }
            if (Objects.equals(command[0], "send_file")){
                System.out.println(Start.ownUser.jsonObject.getString("download_path") + " " + Arrays.toString(Base64.getDecoder().decode(command[2])));
                FileUtils.writeByteArrayToFile(new File(Start.ownUser.jsonObject.getString("download_path") + command[1]), Base64.getDecoder().decode(command[2]));
                ConsoleFrame.add_text_line("download: " + command[1] + " finished!");
            }
            if (Objects.equals(command[0], "post_file_finished")){
                ConsoleFrame.add_text_line("upload: " + command[1] + " finished!");
            }
            if (Objects.equals(command[0], "audio_output_stream")){
                System.out.println(Start.client.requests.size());
                byte[] data = Base64.getDecoder().decode(command[1]);
                //System.out.println(Arrays.toString(data));
                Microphone.do_input(data);
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
    public void setup_user(String first_name, String last_name, String email_address, String send_message_on_login, String message_on_login, String download_path) throws IOException {
        Start.ownUser.jsonObject.put("email_address", email_address);
        Start.ownUser.jsonObject.put("send_message_on_login", send_message_on_login);
        Start.ownUser.jsonObject.put("login_message", message_on_login);
        Start.ownUser.jsonObject.put("download_path", download_path);
        sendMessage("setup_account" + Start.spacing + first_name + Start.spacing + last_name + Start.spacing + Start.ownUser.jsonObject.toString());
    }
    public void update_own_user() throws IOException {
        sendMessage("get_own_user");
    }
    public void get_list_files(String path) throws IOException {
        sendMessage("get_list_files" + Start.spacing + path);
    }
    public void post_file(String path_name, byte[] data) throws IOException {
        sendMessage("post_file" + Start.spacing + path_name + Start.spacing + Base64.getEncoder().encodeToString(data));
    }
    public void get_file(String path) throws IOException {
        sendMessage("get_file" + Start.spacing + path);
    }
    public void send_chat_message(String message) throws IOException {
        sendMessage("send_chat_message" + Start.spacing + message);
    }
    public String serializeArray(final String[] data) {
        try (final ByteArrayOutputStream boas = new ByteArrayOutputStream();
             final ObjectOutputStream oos = new ObjectOutputStream(boas)) {
            oos.writeObject(data);
            return Base64.getEncoder().encodeToString(boas.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] deserializeArray(final String data) {
        try (final ByteArrayInputStream bias = new ByteArrayInputStream(Base64.getDecoder().decode(data));
             final ObjectInputStream ois = new ObjectInputStream(bias)) {
            return (String[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}