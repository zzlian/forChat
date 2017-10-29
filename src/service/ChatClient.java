package service;

import model.User;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private static final String DIRECTORY = "C:\\Users\\lenovo\\Desktop\\"; // 默认文件保存路径
    public User user;
    public JTextArea rmessages;  // 接收信息框
    public JTextArea smessages;  // 发送信息框
    public PrintWriter writer;   // 打印输出流


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
     * 发送文件
     */
    public void sendFile(){
        JFileChooser fileChooser = new JFileChooser();  // 文件选择窗口
        fileChooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory()); // 显示当前文件目录为桌面
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);  // 只选取文件
        fileChooser.showDialog(null, null); // 显示窗口
        File file = fileChooser.getSelectedFile();  // 获取选取的文件
        if(file == null) return;

        String fileName = file.getName();   // 获取文件名

        writer.println("...sending file now...");
        writer.flush();
        writer.println(fileName);
        writer.flush();

        try {   // 传输文件内容
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while((line=br.readLine()) != null){
                writer.println(line);
                writer.flush();
            }
            br.close();
            writer.println("...sending file ending...");
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 接收文件
     */
    public void receiveFile(BufferedReader reader){
        String[] choice = {"receive file", "refuse"};   // 选择是否接收文件
        Object selectedValue = JOptionPane.showInputDialog(null, "接收或拒绝", "接收文件",
                JOptionPane.INFORMATION_MESSAGE, null,
                choice, choice[0]);
        String line;
        if(selectedValue.equals("refuse")){ // 选择拒绝接收，将不会创建相应的文件
            while(true){
                try {
                    line = reader.readLine();
                    if(line.equals("...sending file ending...")) return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String fileName;
        try {
            fileName = reader.readLine();   // 读取文件名
            File file = new File(DIRECTORY + fileName); // 以文件名在默认目录建立相应文件
            if(!file.exists()) file.createNewFile();

            PrintWriter writer = new PrintWriter(file); // 文件打印流
            while(true){    // 将内容写入到文件中
                line = reader.readLine();
                if(line.equals("...sending file ending...")) break; // 文件接收完毕
                writer.println(line);
            }
            writer.flush();
            writer.close();
            JOptionPane.showMessageDialog(null, "成功接收文件");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * 建立线程来管理接收的信息
     */
    public class MessageHandler implements Runnable{
        private BufferedReader reader;

        public MessageHandler(BufferedReader reader){
            this.reader = reader;
        }

        public void run(){
            String message;
            while(true){
                try {
                    message = reader.readLine();    // 接收客户端发送的信息

                    if(message.equals("...sending file...")) {
                        receiveFile(reader);
                        continue;
                    }

                    rmessages.append(message+"\n"); // 显示到文本框中
                } catch (Exception e) {  // 连接中断，发生异常，此时有用户下线了
                    try {
                        System.out.println("有事下线了。。。");
                        reader.close();  // 关闭输入流
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
