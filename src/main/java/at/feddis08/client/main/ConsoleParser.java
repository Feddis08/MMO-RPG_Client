package at.feddis08.client.main;

import at.feddis08.client.JFrames.ChatFrame;
import at.feddis08.client.JFrames.ConsoleFrame;
import at.feddis08.client.Start;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class ConsoleParser {

    public static void get_and_draw_list_files(String path) throws IOException {
        Start.client.get_list_files(path);
        Boolean stop = false;
        Integer index = 0;
        while (!stop){
            if (Start.list_files.size() > 0) {stop = true;
                index = 0;
            }
            index = index + 1;
        };
        index = 0;
        Integer index2 = 0;
        ConsoleFrame.add_text_line("list files from: " + path);
        while (index < Start.list_files.size()){
            while (index2 < Start.list_files.get(0).length){
                ConsoleFrame.add_text_line(Start.list_files.get(index)[index2]);
                index2 = index2 + 1;
            }
            Start.list_files.remove(Integer.parseInt(String.valueOf(index)));
            index = index + 1;
        }
    }
    public static void parse(String str) throws IOException, InterruptedException {
        String[] command = str.split(" ");
        Boolean cmd_right = false;
        if (Objects.equals(command[0], "ls")){
            if (command.length == 1){
                get_and_draw_list_files(Start.current_dir);
                cmd_right = true;
            }
            if (command.length == 2){
                get_and_draw_list_files(command[1]);
                cmd_right = true;
            }
            if (!(cmd_right)){
                ConsoleFrame.add_text_line("Wrong usage: ls <path_name>");
            }
        }
        if (Objects.equals(command[0], "get")){
            if (command.length == 2){
                ConsoleFrame.add_text_line("try download file ...");
                Start.client.get_file(command[1]);
                cmd_right = true;
            }
            if (!(cmd_right)){
                ConsoleFrame.add_text_line("Wrong usage: get <path_name to file>");
            }
        }
        if (Objects.equals(command[0], "post")){
            if (command.length == 3){
                ConsoleFrame.add_text_line("try post file ...");
                File f = new File(command[1]);
                Start.client.post_file(command[2], Files.readAllBytes(f.toPath()));
                cmd_right = true;
            }
            if (!(cmd_right)){
                ConsoleFrame.add_text_line("Wrong usage: post <path_name to file> <path_name to dest file>");
            }
        }
    }
}
