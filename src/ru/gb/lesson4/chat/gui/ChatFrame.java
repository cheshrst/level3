package ru.gb.lesson4.chat.gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ChatFrame {

    private final JFrame mainFrame;
    private final ChattingFrame chattingFrame;
    private final SendingFrame sendingFrame;



    public ChatFrame(Consumer<String> onSubmit) {
        mainFrame = new JFrame();
        chattingFrame = new ChattingFrame();
        sendingFrame = new SendingFrame(onSubmit);
        init();
        }


        private void init(){
        mainFrame.setBounds(new Rectangle(500,700));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setTitle("Chat");



        mainFrame.add(sendingFrame.getFrame(), BorderLayout.SOUTH);
        mainFrame.add(chattingFrame.getFrame(),BorderLayout.CENTER);
        mainFrame.setVisible(true);
        }

    public Consumer<String> OnReceive() {
        return chattingFrame.getOnReceive();
    }
}
