package service;

import model.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    public User user;
    public JTextArea rmessages;  // 接收信息框
    public JTextArea smessages;  // 发送信息框
    public PrintWriter writer;


    /*
     * 构造方法
     */
    public ChatClient(User user) throws IOException {
        this.user = user;
    }

    /*
     * 建立客户端
     */
    public void go() throws IOException {
        Socket mysocket = new Socket(InetAddress.getLocalHost(), 5000); // 连接服务器

        BufferedReader reader = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));// 输入流
        writer = new PrintWriter(mysocket.getOutputStream());   // 输出流
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));   // 键盘输入流

        Thread t = new Thread(new MessageHandler(reader));  // 建立线程来接收其他客户端发送的信息
        t.start();

        writer.println(user.getName()); // 上线提醒
        writer.flush();
    }

    /*
     * 发送信息
     */
    public void sendMessage(String message){
        writer.println(message);
        writer.flush();
    }

    /*
     * 建立线程来管理接收的信息
     */
    public class MessageHandler implements Runnable{
        BufferedReader reader;

        public MessageHandler(BufferedReader reader){
            this.reader = reader;
        }

        public void run(){
            String message;
            while(true){
                try {
                    message = reader.readLine();    // 接收客户端发送的信息
                    rmessages.append(message+"\n"); // 显示到文本框中
                } catch (Exception e) {             // 连接中断，发生异常，此时有用户下线了
                    System.out.println("有事下线了。。。");
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
