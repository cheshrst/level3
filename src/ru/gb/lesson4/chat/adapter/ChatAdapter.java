package ru.gb.lesson4.chat.adapter;


import ru.gb.lesson4.chat.gui.ChatFrame;

public class ChatAdapter {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;

    private final ChatFrame frame;
    private final ChatConnector connector;


    public ChatAdapter() {
        this.connector = new ChatConnector(HOST,PORT);
        this.frame = new ChatFrame(connector::sendMessage);

        while (true){
            frame.OnReceive().accept(connector.receveMessage());
        }
    }
}
