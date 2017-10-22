package view;

import dao.IsUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginView {
    JFrame frame;
    JButton checkButton;
    JButton sign;
    JButton reset;
    JTextField userName;
    JPasswordField password;

    public static void main(String[] args){
        new LoginView();
    }

    public LoginView(){
        frame = new JFrame("登录验证");
        frame.setSize(500, 400);

        JPanel panel = new JPanel();

        JLabel label_name = new JLabel("用户名：");
        JLabel label_passwd = new JLabel("密码：");

        userName = new JTextField();
        userName.setColumns(20);
        password = new JPasswordField();
        password.setColumns(20);

        JLabel label_1 = new JLabel("   ");
        JLabel label_2 = new JLabel("   ");
        JLabel label_3 = new JLabel("   ");
        JLabel label_4 = new JLabel("   ");
        JLabel label_5 = new JLabel("   ");
        checkButton = new JButton("登录");
        checkButton.addActionListener(new LoginAction());
        sign = new JButton("注册");
        reset = new JButton("重置");
        reset.addActionListener(new ResetAction());

        GridBagLayout gbl = new GridBagLayout();
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
        panel.add(checkButton);
        panel.add(sign);
        panel.add(reset);

        GridBagConstraints s = new GridBagConstraints();
        //s.fill = GridBagConstraints.BOTH;

        s.gridwidth = 6;
        gbl.setConstraints(label_1, s);
        s.gridwidth = 1;
        gbl.setConstraints(label_name, s);
        s.gridwidth = 0;
        s.weightx = 1;
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
        s.gridwidth = 4;
        gbl.setConstraints(label_5, s);
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
                // SignUp
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    public class ResetAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            userName.setText("");
            password.setText("");
        }
    }
}
