package service;

import dao.AddLivingUser;
import dao.AddRecord;
import dao.GetRecord;
import dao.RemoveLeavingUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatServer {
    private ArrayList<PrintWriter> clientWriters = new ArrayList<PrintWriter>();
    private ArrayList<String> userNames = new ArrayList<String>();

    public static void main(String[] args){
        new ChatServer().go();
    }

    public synchronized ArrayList<PrintWriter> getClientWriters(){
        return this.clientWriters;
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
            writer = new PrintWriter(this.socket.getOutputStream());
            clientWriters.add(writer);
        }

        /*
         * 接收客户端发送的信息
         */
        public void run(){
            String message;

            try {   // 第一次发送信息，有用户上线了
                userName = reader.readLine();

                try {  // 添加上线用户到活跃列表
                    AddLivingUser.addLivingUser(userName);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                try {  // 用户上线时， 恢复上次聊天的信息
                    ArrayList<String> records = GetRecord.getRecord(userName);
                    for(String record : records){
                        writer.println(record);
                        writer.flush();
                    }
                    writer.println("以上信息为上次聊天记录");
                    writer.flush();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                userNames.add(userName);
                tellEveryone("..."+userName+"上线了...", writer, "", 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(true){
                try {           // 有客户端发送信息时，告诉其他用户
                    message = reader.readLine();

                    if(message.equals("...sending file now...")){ // 文件传输
                        sendFile(writer, reader);
                        continue;
                    }

                    System.out.println("get a message : " + message);
                    tellEveryone(message, writer, userName+"：", 1);
                } catch (IOException e) {             // 当有客户端关闭时，连接重置，出现异常
                    deleteClient(writer, userName);             // 此时将线程结束，将移除对应的输出流
                    tellEveryone("..."+userName+"下线了...", writer, "", 1);
                    break;
                }
            }
        }


        /*
         * 将客户端发送的信息转发给每一个客户端
         */
        public void tellEveryone(String message, PrintWriter w, String userName, int flag){
            ArrayList<PrintWriter> clientWriters = getClientWriters(); // 使用了同步策略，这样在发送文件时别的线程就取不到输出流

            System.out.println("the number of clients : " + clientWriters.size());
            if(clientWriters.size() == 0) return;  // 没有连接

            for(String name : userNames){
                try {
                    if(this.userName.equals(name) && flag==0) continue;
                    AddRecord.addRecord(message, name);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            for(PrintWriter writer : clientWriters){
                if(writer.equals(w)){   // 发给自己的信息
                    if(flag == 0) continue;
                    writer.println(message);
                    writer.flush();
                }
                else{
                    writer.println(userName+message); // 发给其他用户的信息
                    writer.flush();
                }
            }
        }
    }

    /*
     * 发送文件
     */
    public synchronized void sendFile(PrintWriter writer, BufferedReader reader){
        System.out.println("发送文件");
        try {
            String fileName = reader.readLine();
            for(PrintWriter w : clientWriters){   // 提醒用户发送的是文件并发送文件名
                if(w.equals(writer)) continue;
                w.println("...sending file...");
                w.flush();
                w.println(fileName);
                w.flush();
            }

            String line;
            while((line=reader.readLine()) != null){  // 发送文件信息
                for(PrintWriter w : clientWriters){
                    if(w.equals(writer)) continue;
                    w.println(line);
                    w.flush();
                }
                if(line.equals("...sending file ending...")) break;
            }
            System.out.println("发送文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 将下线客户端对应的输出流移除
     */
    public void deleteClient(PrintWriter writer, String userName){
        try {   // 将下线用户移出活跃列表
            RemoveLeavingUser.removeLeavingUser(userName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clientWriters.remove(writer);
        userNames.remove(userName);
    }

}
