package view;

import dao.IsUser;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class LoginView {
    JFrame frame;         // 登录窗口
    JButton checkButton;  // 登录确认按钮
    JButton sign;         // 注册按钮
    JButton reset;        // 重置按钮
    JTextField userName;
    JPasswordField password;

    public static void main(String[] args){
        new LoginView();
    }


    /*
     * 绘制登录界面
     */
    public LoginView(){
        frame = new JFrame("登录验证");     // 建立窗口
        frame.setSize(600, 500);
        frame.setLocation(650, 250);

        JPanel panel = new JPanel();

        JLabel label_name = new JLabel("用户名：");   // 用户名标签
        label_name.setFont(new Font("微软雅黑", Font.PLAIN, 20));// 设置字体
        JLabel label_passwd = new JLabel("密码：");
        label_passwd.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        userName = new JTextField();  // 用户名输入框
        userName.setColumns(15);
        userName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        password = new JPasswordField(); // 密码输入框
        password.setColumns(15);
        password.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel label_1 = new JLabel("   "); // 布局协助组件
        JLabel label_2 = new JLabel("   "); // 填充空格用
        JLabel label_3 = new JLabel("   ");
        JLabel label_4 = new JLabel("   ");
        JLabel label_5 = new JLabel("   ");
        JLabel label_6 = new JLabel("   ");

        checkButton = new JButton("登录");
        checkButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        checkButton.addActionListener(new LoginAction());
        sign = new JButton("注册");
        sign.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        sign.addActionListener(new SignAction());
        reset = new JButton("重置");
        reset.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        reset.addActionListener(new ResetAction());

        GridBagLayout gbl = new GridBagLayout();// 网格布局管理器
        panel.setLayout(gbl);
        panel.add(label_1);
        panel.add(label_name);
        panel.add(userName);
        panel.add(label_2);
        panel.add(label_passwd);
        panel.add(password);
        panel.add(label_3);
        panel.add(label_4);
        panel.add(label_5);
        panel.add(label_6);
        panel.add(checkButton);
        panel.add(sign);
        panel.add(reset);

        GridBagConstraints s = new GridBagConstraints(); // 布局约束管理
        s.gridwidth = 6;
        s.weightx = 0.2;
        gbl.setConstraints(label_1, s);
        s.gridwidth = 1;
        s.weightx = 0.2;
        gbl.setConstraints(label_name, s);
        s.gridwidth = 0;
        s.weightx = 0.8;
        gbl.setConstraints(userName, s);
        s.gridwidth = 6;
        gbl.setConstraints(label_2, s);
        s.gridwidth = 1;
        gbl.setConstraints(label_passwd, s);
        s.gridwidth = 0;
        s.weightx = 1;
        gbl.setConstraints(password, s);
        s.gridwidth = 0;
        gbl.setConstraints(label_3, s);
        s.gridwidth = 0;
        gbl.setConstraints(label_4, s);
        s.gridwidth = 0;
        gbl.setConstraints(label_5, s);
        s.gridwidth = 4;
        gbl.setConstraints(label_6, s);
        s.gridwidth = 3;
        gbl.setConstraints(checkButton, s);
        s.gridwidth = 4;
        gbl.setConstraints(sign, s);
        s.gridwidth = 0;
        gbl.setConstraints(reset, s);
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /*
     * 登录事件监听
     */
    public class LoginAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = userName.getText();
            String passwd = password.getText();

            if(name.equals("")){
                JOptionPane.showMessageDialog(null, "用户名不能为空");
                return;
            }
            else if(passwd.equals("")){
                JOptionPane.showMessageDialog(null, "密码不能为空");
                return;
            }

            boolean ok = false;
            try {
                ok = IsUser.isUser(name, passwd);
                if(!ok) {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！");
                    return;
                }
                try {    // 登录成功，跳转至聊天窗口
                    User user = new User(name, passwd);
                    frame.dispose();
                    new ChatView(user);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*
     * 注册按钮监听
     */
    public class SignAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();  // 跳转至注册页面
            new SignUp();
        }
    }

    /*
     * 重置事件监听
     */
    public class ResetAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            userName.setText("");
            password.setText("");
        }
    }
}
