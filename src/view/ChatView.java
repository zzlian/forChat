package view;

import model.User;
import service.ChatClient;
import service.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatView {
    private JFrame frame;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JButton send;
    private ChatClient client;


    /*
     * 创建聊天窗口
     */
    public ChatView(User user) throws IOException {
        client = new ChatClient(user);  // 创建客户端
        Thread t = new Thread(new Chat()); // 建立线程管理聊天
        t.start();

        frame = new JFrame("多人聊天室");    // 绘制聊天窗口
        frame.setSize(800, 700);    // 设置窗口大小
        frame.setLocation(600, 100);

        client.rmessages = new JTextArea();     // 聊天信息框
        client.rmessages.setEditable(false);
        client.rmessages.setLineWrap(true);
        client.rmessages.setWrapStyleWord(true);
        client.rmessages.setBackground(Color.LIGHT_GRAY);
        client.rmessages.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        client.rmessages.setCaretPosition(client.rmessages.getText().length());
        scrollPane1 = new JScrollPane(client.rmessages); // 添加滚动条

        client.smessages = new JTextArea();    // 发送信息框
        client.smessages.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        client.smessages.setLineWrap(true);
        client.smessages.setWrapStyleWord(true);
        scrollPane2 = new JScrollPane(client.smessages); // 添加滚动条

        send = new JButton("发送");   // 发送按钮
        send.addActionListener(new SendMessageAction());

        GridBagLayout layout = new GridBagLayout(); // 布局管理器
        frame.setLayout(layout);

        frame.getContentPane().add(scrollPane1);
        frame.getContentPane().add(scrollPane2);
        frame.getContentPane().add(send);

        GridBagConstraints s = new GridBagConstraints(); // 布局约束
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0.8;
        layout.setConstraints(scrollPane1, s);
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0.195;
        layout.setConstraints(scrollPane2, s);
        s.gridwidth = 0;
        s.weighty = 0.005;
        layout.setConstraints(send, s);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /*
     * 建立线程管理聊天部分
     */
    public class Chat implements Runnable{
        @Override
        public void run() {
            try {
                client.go();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 添加发送信息事件监听
     */
    public class SendMessageAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String message;
            message = client.smessages.getText();
            if(message.equals("")) return;
            client.sendMessage(message);   // 发送信息给其它用户
            client.smessages.setText("");  // 将发送信息框置空
        }
    }
}
