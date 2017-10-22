package service;

import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    static int index = 0;
    User user;

    public static void main(String [] args) throws IOException {
        index ++;
        User user = new User("person"+index, "123");
        new ChatClient(user);
    }


    public ChatClient(User user) throws IOException {
        this.user = user;
        go();
    }

    /*
     * 建立客户端
     */
    public void go() throws IOException {
        Socket mysocket = new Socket(InetAddress.getLocalHost(), 5000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
        PrintWriter writer = new PrintWriter(mysocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Thread t = new Thread(new MessageHandler(reader));  // 建立线程来接收其他客户端发送的信息
        t.start();

        writer.println("..."+user.getName()+"上线了..."); // 上线提醒
        writer.flush();

        String message;
        while(true){        // 发送信息
            message = new String(in.readLine());
            writer.println(message);
            writer.flush();
        }
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
                    message = reader.readLine();
                    System.out.println(message);
                } catch (Exception e) {
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
