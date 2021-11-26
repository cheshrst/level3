package ru.gb.lesson3.gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ChattingFrame extends Component {
    private final JPanel frame;
    private final JTextArea messageArea;
    private final Consumer<String> onReceive;

    public ChattingFrame() {
        messageArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(messageArea);
        messageArea.setEditable(false);
        this.onReceive = message ->{
            messageArea.append(message);
            messageArea.append("\n");
};
        frame = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane);

    }

    public JPanel getFrame() {
        return frame;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public Consumer<String> getOnReceive() {
        return onReceive;
    }
}
