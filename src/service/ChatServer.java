package service;

import com.sun.corba.se.impl.io.IIOPOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private ArrayList<PrintWriter> clientWriters = new ArrayList<PrintWriter>();

    public static void main(String[] args){
        new ChatServer().go();
    }

    /*
     * 监听是否有其它的客户端链接进来了
     * 若有则建立新的线程进行处理
     */
    public void go(){
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5000);  // 建立服务，端口为5000

            while(true){
                Socket clientSocket = serverSocket.accept(); // 监听客户端连接

                Thread t = new Thread(new ClientHandler(clientSocket)); // 建立线程来管理客户端
                t.start();

                System.out.println("A new client gets connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 使用线程来管理客户端发送的信息
     */
    public class ClientHandler implements Runnable{
        private BufferedReader reader;
        private PrintWriter writer;
        private Socket socket;
        private String userName;

        /*
         * 构造方法
         */
        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            clientWriters.add(writer);

            System.out.println("new a thread");
        }

        /*
         * 接收客户端发送的信息
         */
        public void run(){
            String message;

            try {   // 第一次发送信息，有用户上线了
                userName = reader.readLine();
                tellEveryone("..."+userName+"上线了...", writer, "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(true){
                try {           // 有客户端发送信息时，告诉其他用户
                    message = reader.readLine();
                    System.out.println("get a message : " + message);
                    tellEveryone(message, writer, userName+"：");
                } catch (IOException e) {             // 当有客户端关闭时，连接重置，出现异常
                    deleteClient(writer);             // 此时将线程结束，将移除对应的输出流
                    tellEveryone("..."+userName+"下线了...", writer, "");
                    break;
                }
            }
        }

        /*
         * 将客户端发送的信息转发给每一个客户端
         */
        public void tellEveryone(String message, PrintWriter w, String userName){
            System.out.println("the number of clients : " + clientWriters.size());
            if(clientWriters.size() == 0) return;  // 没有连接

            for(PrintWriter writer : clientWriters){
                if(writer.equals(w)){   // 发给自己的信息
                    writer.println(message);
                    writer.flush();
                    continue;
                }
                writer.println(userName+message); // 发给其他用户的信息
                writer.flush();
            }
        }
    }


    /*
     * 将下线客户端对应的输出流移除
     */
    public void deleteClient(PrintWriter writer){
        clientWriters.remove(writer);
    }

}
