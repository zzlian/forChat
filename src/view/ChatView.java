package view;

import service.ChatServer;

import javax.swing.*;
import java.awt.*;

public class ChatView {
    JFrame frame;
    JTextArea messages;
    JScrollPane scrollPane;

    public static void main(String[] args){
        new ChatView();
    }

    public ChatView(){
        frame = new JFrame("多人聊天室");
        frame.setSize(800, 900);

        messages = new JTextArea();
        messages.setBackground(Color.LIGHT_GRAY);
        messages.setFont(new Font("weiruanyahei", Font.PLAIN, 25));
        scrollPane = new JScrollPane(messages);


        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);

        frame.getContentPane().add(scrollPane);

        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 1;
        layout.setConstraints(scrollPane, s);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
