package ru.gb.lesson4.chat.adapter;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatConnector {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;



    public ChatConnector(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex){
            throw new RuntimeException("Smth went wrong during a connection", ex);
        }

    }

    public void sendMessage(String outboundMessage){
        try {
            out.writeUTF(outboundMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String receveMessage(){
        try {
            String message = in.readUTF();
            return message;
        } catch (IOException ex) {
            throw new RuntimeException("Smth went wrong during receiving msg from the server", ex);
        }

    }

}
