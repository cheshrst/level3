package ru.gb.lesson4.chat.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChatBuffer{
    void doFileStreamDemo(String outboundMessage) {
        File file = new File("./src/ru/gb/lesson3/server/history.txt");
        doFileOutputStreamDemo(file, outboundMessage);
    }
    void doFileOutputStreamDemo(File file,String outboundMessage) {
        try (FileOutputStream fout = new FileOutputStream(file, true)) {
            fout.write(outboundMessage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
