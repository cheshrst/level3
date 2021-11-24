package ru.gb.lesson2.adapter;


import ru.gb.lesson2.gui.ChatFrame;

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
