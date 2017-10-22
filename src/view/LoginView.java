package view;

import dao.IsExist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginView extends JFrame{
    private JTextField userName;
    private JTextField password;

    public static void main(String[] args){
        new LoginView();
    }


    public LoginView(){
        init();
        setSize(500, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void init(){
        Label label1 = new Label("userName：");
        label1.setSize(1,1);
        Label label2 = new Label("password：");
        JTextField userName = new JTextField();
        userName.setColumns(20);
        JPasswordField password = new JPasswordField();
        password.setColumns(10);
        JButton button = new JButton("确定");

        Label label3 = new Label();
        Label label4 = new Label();
        Label label5 = new Label();
        Label label6 = new Label();
        Label label7 = new Label();
        Label label8 = new Label();

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        add(label7);
        add(label8);
        add(label3);
        add(label1);
        add(userName);
        add(label4);
        add(label5);
        add(label2);
        add(password);
        add(label6);
        add(button);

        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;

        s.gridwidth = 10;
        layout.setConstraints(label7, s);

        s.gridwidth = 0;
        layout.setConstraints(label8, s);

        s.gridwidth = 3;
        layout.setConstraints(label3, s);

        s.gridwidth = 1;
        layout.setConstraints(label1, s);

        s.gridwidth = 20;
        s.weightx = 1;
        layout.setConstraints(userName, s);

        s.gridwidth = 0;
        layout.setConstraints(label4, s);

        s.gridwidth = 3;
        layout.setConstraints(label5, s);

        s.gridwidth = 1;
        layout.setConstraints(label2, s);

        s.gridwidth = 5;
        s.weightx = 1;
        layout.setConstraints(password, s);

        s.gridwidth = 0;
        layout.setConstraints(label6, s);

        s.gridwidth = 2;
        layout.setConstraints(button, s);
    }

    public class CheckAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                boolean is;
                is = IsExist.isExist(userName.getText(), password.getText());
                if(is == true) System.out.println(true);
                else System.out.println(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }
}
