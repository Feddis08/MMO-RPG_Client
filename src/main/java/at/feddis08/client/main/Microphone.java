package at.feddis08.client.main;

import at.feddis08.client.Start;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class Microphone {

    public static AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
    public static Integer data_size = 1024;
    public static Integer byte_count = 0;
    public static byte[] speaker_data = new byte[data_size];
    public static byte[] data3 = new byte[data_size];
    public static DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
    public static SourceDataLine speakers;
    public static void start() throws LineUnavailableException {
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speakers.open(format);
        speakers.start();
    }

    public static void do_output(){
            Thread th = new Thread(){
                int numBytesRead;
                @Override public void run(){
                    try {
                    TargetDataLine microphone;
                    SourceDataLine speakers;
                    microphone = AudioSystem.getTargetDataLine(format);

                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                    microphone = (TargetDataLine) AudioSystem.getLine(info);
                    microphone.open(format);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int CHUNK_SIZE = data_size;
                    byte[] mic_data = new byte[data_size];
                    microphone.start();

                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
                    speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                    speakers.open(format);
                    speakers.start();
                    while (true){

                        numBytesRead = microphone.read(mic_data, 0, CHUNK_SIZE);
                        // write the mic data to a stream for use later
                        out.write(mic_data, 0, numBytesRead);
                        Thread.sleep(15);
                        Start.client.sendMessage("audio_input_stream" + Start.spacing + Base64.getEncoder().encodeToString(mic_data));
                    }
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        th.start();
    }
    public static void do_input(byte[] data) {
        byte[] data2 = new byte[data_size];
        Integer index = byte_count;
        while (index < data_size){
            data2[index] = (byte) ((speaker_data[index] + data[index])>>1);
            index = index + 1;
        }
        speaker_data = data2;
        speakers.write(data2,0,data_size);
    }
}