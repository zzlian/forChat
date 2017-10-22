package view;

import dao.IsUsingName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUp {
    JFrame frame;
    JTextField userName;      // 用户名框
    JPasswordField password;  // 密码框
    JPasswordField refirm;    // 密码确认框
    JButton sign;             // 确认注册按钮
    JButton back;             // 返回登录界面按钮
    JButton reset;            // 重置按钮


    /*
     * 绘制注册界面
     */
    public SignUp(){
        frame = new JFrame("用户注册");
        frame.setSize(600, 500);
        frame.setLocation(650, 250);

        JPanel panel = new JPanel();

        JLabel label_name = new JLabel("请输入用户名：");
        label_name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JLabel label_passwd = new JLabel("请输入密码：");
        label_passwd.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        JLabel label_refirm = new JLabel("确认密码：");
        label_refirm.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        userName = new JTextField(20);
        userName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        password = new JPasswordField(20);
        password.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        refirm = new JPasswordField(20);
        refirm.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        JLabel label_1 = new JLabel("   ");
        JLabel label_2 = new JLabel("   ");
        JLabel label_3 = new JLabel("   ");
        sign = new JButton("确定注册");
        sign.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        sign.addActionListener(new ConfirmAction());
        back = new JButton("返回登录");
        back.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        back.addActionListener(new BackAction());
        reset = new JButton("重置");
        reset.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        reset.addActionListener(new ResetAction());

        GridBagLayout gbl = new GridBagLayout(); // 网格布局管理器
        panel.setLayout(gbl);
        panel.add(label_name);
        panel.add(userName);
        panel.add(label_passwd);
        panel.add(password);
        panel.add(label_refirm);
        panel.add(refirm);
        panel.add(label_1);
        panel.add(label_2);
        panel.add(label_3);
        panel.add(sign);
        panel.add(back);
        panel.add(reset);

        GridBagConstraints s = new GridBagConstraints(); // 布局约束管理
        s.gridwidth = 1;
        gbl.setConstraints(label_name, s);
        s.gridwidth = 0;
        s.weightx = 1;
        gbl.setConstraints(userName, s);
        s.gridwidth = 1;
        gbl.setConstraints(label_passwd, s);
        s.gridwidth = 0;
        s.weightx = 1;
        gbl.setConstraints(password, s);
        s.gridwidth = 1;
        gbl.setConstraints(label_refirm, s);
        s.gridwidth = 0;
        s.weightx = 1;
        gbl.setConstraints(refirm, s);
        s.gridwidth = 0;
        gbl.setConstraints(label_1, s);
        s.gridwidth = 0;
        gbl.setConstraints(label_2, s);
        s.gridwidth = 0;
        gbl.setConstraints(label_3, s);
        s.gridwidth = 1;
        gbl.setConstraints(sign, s);
        s.gridwidth = 1;
        gbl.setConstraints(back, s);
        s.gridwidth = 0;
        gbl.setConstraints(reset, s);
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /*
     * 确认注册事件监听
     */
    public class ConfirmAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = userName.getText();
            String passwd = password.getText();
            boolean isUse;

            if(name.equals("")) {
                JOptionPane.showMessageDialog(null, "用户名不能为空！");
                return;
            }
            else if(passwd.equals("")){
                JOptionPane.showMessageDialog(null, "密码不能为空！");
                return;
            }
            else if(!passwd.equals(refirm.getText())){
                JOptionPane.showMessageDialog(null, "两次密码不一致！");
                return;
            }
            try {
                isUse = IsUsingName.isUsingName(name);  // 判断用户名是否存在
                if(isUse){
                    JOptionPane.showMessageDialog(null, "用户名已存在！");
                    return;
                }
                JOptionPane.showMessageDialog(null, "注册成功！");
                changeToLogin();  // 跳转至登录界面
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*
     * 返回事件监听
     */
    public class BackAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            changeToLogin();  // 跳转至登录界面
        }
    }

    /*
     * 跳转至登录界面
     */
    public void changeToLogin(){
        frame.dispose();
        new LoginView();
    }

    /*
     * 重置事件监听
     */
    public class ResetAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            userName.setText("");
            password.setText("");
            refirm.setText("");
        }
    }
}
